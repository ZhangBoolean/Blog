1、-- 解决不能向自增字段插入值

    set identity_insert "TM_JIS_LINE_STATION(表名)" on; 
    -- insert into ... 插入语句
    set identity_insert "TM_JIS_LINE_STATION(表名)" off;

2、-- 插入语句中有未存在的关联ID

    a、确认生产数据库表中关联ID的最大值，在插入关联ID的语句中进行顺延，将ID的值固定进行插入
    b、按照条件进行查询获得关联ID进行插入操作