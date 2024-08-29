package thread.control;

import thread.start.HelloRunnable;

import static util.MyLogger.log;

public class ThreadInfoMain {
    public static void main(String[] args) {
        //main 스레드
        Thread mainThread = Thread.currentThread();
        log("main thread: " + mainThread);
        log("main threadID: " + mainThread.threadId());
        log("main threadGetName: " + mainThread.getName());
        log("main threadGetPriority: " + mainThread.getPriority());
        log("main threadGetThreadGroup: " + mainThread.getThreadGroup());
        log("main threadGetState: " + mainThread.getState());

        System.out.println();
        //myThread 스레드
        Thread myThread = new Thread(new HelloRunnable(), "myThread");
        log("myThread thread: " + myThread);
        log("myThread threadID: " + myThread.threadId());
        log("myThread threadGetName: " + myThread.getName());
        log("main threadGetPriority: " + myThread.getPriority());
        log("myThread threadGetThreadGroup: " + myThread.getThreadGroup());
        log("myThread threadGetState: " + myThread.getState());

    }
}
