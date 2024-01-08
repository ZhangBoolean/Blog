

public abstract class SBGRunnable implements Runnable {

    @Override
    public final void run() {
        runBiz();
    }

    public abstract void runBiz();

}
