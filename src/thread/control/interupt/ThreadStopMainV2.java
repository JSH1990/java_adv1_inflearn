package thread.control.interupt;

import static thread.control.TreadUtils.sleep;
import static util.MyLogger.log;

public class ThreadStopMainV2 {
    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread thread = new Thread(task, "work");
        thread.start();

        sleep(4000);
        log("작업 중단 지시 thread.interrupt");
        thread.interrupt();
        log("work 스레드 인터럽트 상태1 = " + thread.isInterrupted());
    }

    static class MyTask implements Runnable {
        volatile boolean runFlag = true;


        @Override
        public void run() {
                try {
                    while (true) {
                        log("작업 중..");
                        Thread.sleep(3000);
                    }
                } catch (InterruptedException e) {
                    log("work 스레드 인터럽트 상태2= " + Thread.currentThread().isInterrupted());
                    log("interrupt message=" + Thread.currentThread().getState());
                    log("state=" + Thread.currentThread().getState());
                }
                log("자원 정리");
                log("자원 종료");
            }


        }
    }

