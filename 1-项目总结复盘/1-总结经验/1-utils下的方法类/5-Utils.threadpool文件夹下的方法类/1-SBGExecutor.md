// 线程池的创建调用工具类


@Slf4j
public class SBGExecutor {
private static final int MIN_SIZE = Runtime.getRuntime().availableProcessors() * 2;
private static final int MAX_SIZE = MIN_SIZE * 2;
private static final int BLOCK_SIZE = MIN_SIZE;

    private static BaseThreadPoolExecutor EXECUTOR =
            new BaseThreadPoolExecutor(MIN_SIZE, MAX_SIZE,
                    60L, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(BLOCK_SIZE),
                    new CustomizableThreadFactory("SBGExecutor.EXE"));

    /**
     * 运行execute
     * @param runnable
     */
    public static void execute(SBGRunnable runnable) {
        try {
            EXECUTOR.execute(runnable);
        } catch (Throwable throwable) {
            log.error(Log.msg(throwable));
        }
    }

    /**
     * 线程数量
     * @return 线程数量
     */
    public static int poolSize() {
        return EXECUTOR.getPoolSize();
    }

    /**
     * 活动数量
     * @return 活动数量
     */
    public static int activeCount() {
        return EXECUTOR.getActiveCount();
    }

    /**
     * 线程池详细信息
     * @return 详细信息
     */
    public static String threadPoolInfo() {
        String[] msg = EXECUTOR.toString().split("\\[");
        return msg[1].replace("\\]", "");
    }



    //-在《阿里巴巴java开发手册》中指出了线程资源必须通过线程池提供，不允许在应用中自行显示的创建线程，这样一方面是线程的创建更加规范，可以合理控制开辟线程的数量；另一方面线程的细节管理交给线程池处理，优化了资源的开销。
    //-而线程池不允许使用Executors去创建，而要通过ThreadPoolExecutor方式，这一方面是由于jdk中Executor框架虽然提供了如newFixedThreadPool()、newSingleThreadExecutor()、newCachedThreadPool()等创建线程池的方法，
    //-但都有其局限性，不够灵活；另外由于前面几种方法内部也是通过ThreadPoolExecutor方式实现，使用ThreadPoolExecutor有助于大家明确线程池的运行规则，创建符合自己的业务场景需要的线程池，避免资源耗尽的风险
        // ..extends ThreadPoolExecutor -> extends AbstractExecutorService -> implements ExecutorService -> extends Executor
    // 继承ThreadPoolExecutor创建线程池
    private static class BaseThreadPoolExecutor extends ThreadPoolExecutor  {
        private int poolSize;
        private int activeSize;

        public int getActiveSize() {
            return activeSize;
        }

        @Override
        public int getPoolSize() {
            return poolSize;
        }

        public BaseThreadPoolExecutor(int corePoolSize,
                                      int maximumPoolSize,
                                      long keepAliveTime,
                                      TimeUnit unit,
                                      BlockingQueue<Runnable> workQueue,
                                      ThreadFactory threadFactory) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            this.poolSize = this.getPoolSize();
            this.activeSize = this.getActiveCount();
        }
    }
}
