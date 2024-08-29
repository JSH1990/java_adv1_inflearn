package thread.start;

public class DaemonThreadMain {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " :main() start");

        DaemonThread daemonThread = new DaemonThread();
        daemonThread.setDaemon(true); // 데몬 스레드 여부
        /*
        true가 기본값이고, 프로그램 실행하면 10초 기다리지 않고 바로 실행종료된다. (데몬 스레드라 그렇다.)
        false면 데몬스레드로 인식하지않기때문에, 10초 기다리고 실행종료된다.
         */
        daemonThread.start();

        System.out.println(Thread.currentThread().getName() + " :main() end");
    }

    static class DaemonThread extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " :run()");
            try {
                Thread.sleep(10000); //예외 밖으로 던지지못한다.
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " :run() end");
        }

    }
}
