package thread.control;

import static thread.control.TreadUtils.sleep;

public class CheckedExceptionMain {
    public static void main(String[] args) throws Exception {
        throw new Exception();
    }

    static class CheckedRunnable implements Runnable {
        @Override
        public void run() /* throws Exception */ {
            sleep(1000);
            //throw new Exception(); //주석풀면 예외 발생
        }
    }
}
