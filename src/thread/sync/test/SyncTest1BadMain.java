package thread.sync.test;




/*
다음 코드의 결과는 20000이 되어야 한다.
이 코드의 문제점을 찾아서 해결하라.
이 코드에서 다른 부분은 변경하면 안되고, Counter 클래스 내부만 수정해야한다.
 */
public class SyncTest1BadMain {
    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();

        Runnable task = new Runnable(){
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    counter.increment();
                }
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("결과: " + counter.getCount());
    }

    static class Counter{
        private int count = 0;

        public synchronized void increment(){
            count = count + 1;
        }

        public synchronized int getCount() {
            return count;
        }
    }
}
