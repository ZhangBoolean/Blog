一、登录方法

1、Contrller层，

    @PostMapping("/login")
    @Operation(description = "登录")
    @OperationLog(type = OpType.LOGIN, value = "user_login")
    public Response<String> login(@RequestBody LoginUser loginUser) {
        return this.ok(this.userService.login(loginUser));
    }

2、Service层
    
    private final StringRedisTemplate redis;

    public String login(LoginUser loginUser) {
        if (!this.redis.hasKey(loginUser.getKey())) {
            throw BusinessErrorException.msg("err.login.rsakey");
        }
        String pk = Utils.toString(this.redis.opsForHash().get(loginUser.getKey(), Const.Session.PRIVATE));
        loginUser.setUserNo(RsaUtils.decrypt(loginUser.getUserNo(), pk));
        loginUser.setPassword(RsaUtils.decrypt(loginUser.getPassword(), pk));
        loginUser.setCaptcha(RsaUtils.decrypt(loginUser.getCaptcha(), pk));
        if (!this.redis.hasKey(Const.REDIS_CAPTCHA_KEY_PREFIX + loginUser.getCaptcha())) {
            throw BusinessErrorException.msg("err.login.captcha");
        } else {
            this.redis.delete(Const.REDIS_CAPTCHA_KEY_PREFIX + loginUser.getCaptcha());
        }
        TsUser tuser = this.tsUserDao.findByUserNo(loginUser.getUserNo());
        if (null == tuser) {
            throw BusinessErrorException.msg("err.login.username");
        }
        if (!Const.YES.equals(tuser.getIsActive())) {
            throw BusinessErrorException.msg("err.login.inactive");
        }
        if (0 == this.tsUserRoleDao.countByUserId(tuser.getId())) {
            throw BusinessErrorException.msg("err.login.norole");
        }
        if (Const.YES.equals(tuser.getIsLock())) {
            if (null == tuser.getLastLockTime() || tuser.getLastLockTime().before(new Date())) {
                tuser.setIsLock(Const.NO);
                tuser.setErrortimes(0);
                this.tsUserDao.save(tuser);
            } else {
                throw BusinessErrorException.msg("err.login.locked");
            }
        }
        if (!this.validationService.validateLogin(tuser)) {
            this.tsUserDao.save(tuser);
            throw BusinessErrorException.msg("err.login.error_time");
        }
        if (!DigestUtils.md5Hex(this.createPassword(loginUser.getPassword())).equals(tuser.getPassword())) {
                tuser.setErrortimes(tuser.getErrortimes() + 1);
                if (!this.validationService.validateLogin(tuser)) {
                    this.tsUserDao.save(tuser);
                    throw BusinessErrorException.msg("err.login.error_time");
                }
                this.tsUserDao.save(tuser);
                throw BusinessErrorException.msg("err.login.username");
            }
        User user = new User();
        user.setId(tuser.getId());
        user.setType(tuser.getUserType());
        user.setLoginName(tuser.getUserNo());
        user.setSuppCode(tuser.getUserCompany());
        user.setEnv(tuser.getEnv());
        user.setEmail(tuser.getEmail());
        user.setTel(tuser.getTel());
        user.setMobile(tuser.getMobile());
        user.setPlants(this.tsUserPlantDao.getPlantNoListByUserId(tuser.getId()));
        user.setRoles(this.tsRoleDao.findRoleCodeListByUserId(tuser.getId()));
        if (null != tuser.getUserCompany()) {
            CustomerExt cust = this.tsUserDao.queryUserCompanyInfo(tuser.getUserCompany());
            if (null != cust) {
                user.setSuppType(cust.getType());
            }
        }
        this.redis.delete(loginUser.getKey());
        log.info("user [{}] login success", user.getLoginName());
        this.logLoginTime(tuser);
        this.setLanguage(tuser);
        this.setApiResource(user);
        this.setPrilegeCode(user);
        this.tsUserDao.updateUserErrortimes(user.getId());
        RequestContextHandler.setUserInfo(user);
        return TokenUtils.generateToken(user);
    }
