package thread.start;

public class HelloThreadMain {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " :main() start");
        HelloThread helloThread = new HelloThread();
        System.out.println(Thread.currentThread().getName() + " :start() 호출 전");

        helloThread.start(); //Thread-0: run() 메인과 다른스레드가 실행

        //main 스레드는 helloThread.start() 지시하고, 호출을 기다리지않고, 다음줄로 내려간다.

        System.out.println(Thread.currentThread().getName() + " :start() 호출후");
        System.out.println(Thread.currentThread().getName() + " :main() end");
    }
}
