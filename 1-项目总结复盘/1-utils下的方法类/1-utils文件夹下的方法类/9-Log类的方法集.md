public class Log {

    /**
     * <p>Field task: task logs</p>
     */
    private static Logger task = LogManager.getLogger("task");
    /**
     * <p>Field debug: debug level log</p>
     */
    private static Logger debug = LogManager.getLogger("debug");
    /**
     * <p>Field info: info level log</p>
     */
    private static Logger info = LogManager.getLogger("info");
    /**
     * <p>Field error: error level logs</p>
     */
    private static Logger error = LogManager.getLogger("error");

    /**
     * <p>Description: private constructor</p>
     */
    private Log() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * <p>Description: get exception stack trace info</p>
     * @param e exception
     * @return message
     */
    public static String exception(Throwable e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString();
        } catch (Exception ex) {
            Log.error(ex.getMessage());
        } finally {
            FileUtil.close(pw);
            FileUtil.close(sw);
        }
        return null;
    }

    /**
     * <p>Description: get Exception message</p>
     * @param e exception
     * @return error info
     */
    public static String exceptionMsg(Throwable e) {
        return e.toString() + ":" + e.getMessage();
    }

    /**
     * <p>Description: task info log</p>
     * @param msg message
     */
    public static void taskInfo(Object msg) {
        task.info(tryLogCaller(msg));
    }

    /**
     * <p>Description: task error log</p>
     * @param msg message
     */
    public static void taskError(Object msg) {
        task.error(tryLogCaller(msg));
    }

    /**
     * <p>Description: debug log</p>
     * @param msg message
     */
    public static void debug(Object msg) {
        debug.debug(tryLogCaller(msg));
    }

    /**
     * <p>Description: info log</p>
     * @param msg message
     */
    public static void info(Object msg) {
        info.info(tryLogCaller(msg));
    }

    /**
     * <p>Description: error log</p>
     * @param msg message
     */
    public static void error(Object msg) {
        error.error(tryLogCaller(msg));
    }

    /**
     * <p>Description: error log</p>
     * @param e exception
     */
    public static void error(Throwable e) {
        String msg = exception(e);
        error.error(tryLogCaller(msg));
    }

    /**
     * <p>Description: get exception message</p>
     * @param e exception
     * @return result
     */
    public static String msg(Throwable e) {
        String msg = null;
        if (null != e) {
            if (null != e.getCause()) {
                msg = getRootMsg(e);
            } else {
                msg = e.getMessage();
                if (null == msg) {
                    msg = e.toString();
                }
            }
        }
        return msg;
    }

    /**
     * <p>Description: 取得ROOT消息</p>
     * @param e Throwable
     * @return 结果
     */
    private static String getRootMsg(Throwable e) {
        if (null != e.getCause()) {
            if (e.getClass().equals(e.getCause().getClass())) {
                return e.getCause().toString() + e.getMessage();
            }
            return getRootMsg(e.getCause());
        }
        return e.toString();
    }

    /**
     * <p>Description: log with caller's info</p>
     * @param msg message
     * @return result
     */
    private static String tryLogCaller(Object msg) {
        return getCaller() + msg;
    }

    /**
     * <p>Description: get caller</p>
     * @return class info
     */
    private static String getCaller() {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        if (null == stacks) {
            return null;
        }

        StackTraceElement caller = null;
        String logClass = Log.class.getName();
        boolean isLogClass = false;

        for (StackTraceElement stack : stacks) {
            if (logClass.equals(stack.getClassName())) {
                isLogClass = true;
            }
            if (isLogClass) {
                if (!logClass.equals(stack.getClassName())) {
                    isLogClass = false;
                    caller = stack;
                    break;
                }
            }
        }
        return "[" + caller.toString() + "] - ";
    }
}
