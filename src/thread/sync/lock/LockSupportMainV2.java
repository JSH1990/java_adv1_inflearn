package thread.sync.lock;

import java.util.concurrent.locks.LockSupport;

import static thread.control.TreadUtils.sleep;
import static util.MyLogger.log;

public class LockSupportMainV2 {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new ParkTest(), "Tread1-1");
        thread1.start();

        //잠시 대기하여 Thread-1이 park 상태에 바질 시간을 준다.
        sleep(100);
        log("Tread-1 state: " + thread1.getState());

    }

    static class  ParkTest implements Runnable{
        @Override
        public void run() {
            log("park 시작, 2초 대기");
            LockSupport.parkNanos(2000_000000); //2초 뒤에 깨어남
            log("park 종료, state: " + Thread.currentThread().getState());
            log("인터럽트 상태 : " + Thread.currentThread().isInterrupted());
        }
    }
}
