package volatile1;

import static thread.control.TreadUtils.sleep;
import static util.MyLogger.log;

public class VolatileFlagMain {
    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread t = new Thread(task, "work");
        log("runFlag = " + task.runFlag);
        t.start();

        sleep(1000);
        log("runFlag를 false로 변경 시도");
        task.runFlag = false;
        log("runFlag = " + task.runFlag);
        log("main 종료");
    }

    static class MyTask implements Runnable {
//        boolean runFlag = true;

        volatile boolean runFlag = true; //캐시메모리에 접근하지 않고, 직접 메인메모리에 접근한다.

        @Override
        public void run() {
            log("task 시작");
            while (runFlag) {
                //runFlag가 false로 변하면 탈출
            }
            log("task 종료");
        }
    }
}
