package thread.bounded;

import java.util.ArrayList;
import java.util.Queue;

import static thread.control.TreadUtils.sleep;
import static util.MyLogger.log;

public class BoundedMain {
    public static void main(String[] args) {
        //1. BoundedQueue 선택
        /*
        버퍼의 크기는 2를 사용한다. 따라서 버퍼에는 데이터를 2개까지만 보관할수 있다.
        - 만약 생산자가 2개를 넘어서는 데이터를 추가로 저장하려고 하면 문제가 발생한다.
        - 반대로 버퍼에 데이터가 없는데, 소비자가 데이터를 가져갈 때도 문제가 발생한다.
         */
        BoundedQueue queue = new BoundedQueueV6_1(2);

        //2. 생산자, 소비자 실행 순서 선택 , 반드시 하나만 선택!
        /*
        - 이 두 코드중에 하나만 선택해서 실행해야 한다. 그렇지 않으면 예상치 못한 오류가 발생할 수 있다.
        - 생산자가 먼저 실행되는 경우, 소비자가 먼저 실행되는 경우를 나누어서 다양한 예를 보여주기 위함
        - 생산자 먼저인 producerFirst를 호출하면
        -> producer1 -> 2 -> 3 -> consumer1 -> 2 -> 3 순서로 실행된다.
        여기서는 이해를 돕기위해 순서대로 실행됨. 실제로는 동시에 실행된다.
         */
//        producerFirst(queue); //생산자 먼저 실행
        consumerFirst(queue); //소비자 먼저 실행
    }

    private static void producerFirst(BoundedQueue queue) {
        log("== [생산자 먼저 실행] 시작, " + queue.getClass().getSimpleName() + " ==");
        ArrayList<Thread> threads = new ArrayList<>(); //threads - 스레드의 결과 상태를 한꺼번에 출력하기 위해 생성한 스레드를 보관해둠
        startProducer(queue, threads); //생산자 스레드를 3개 만들어서 실행한다. 이해를 돕기위해 0.1초 간격으로 sleep를 주면서 순차적으로 실행(producer1 -> 2 > 3)
        printAllState(queue, threads); //모든 스레드의 상태를 출력한다.
        startConsumer(queue, threads); //소비자 스레드를 3개 만들어서 실행
        printAllState(queue, threads);
        log("== [생산자 먼저 실행] 종료, " + queue.getClass().getSimpleName() + " ==");
    }

    private static void consumerFirst(BoundedQueue queue) {
        log("== [소비자 먼저 실행] 시작, " + queue.getClass().getSimpleName() + " ==");
        ArrayList<Thread> threads = new ArrayList<>();
        startConsumer(queue, threads);
        printAllState(queue, threads);
        startProducer(queue, threads);
        printAllState(queue, threads);
        log("== [소비자 먼저 실행] 종료, " + queue.getClass().getSimpleName() + " ==");
    }

    private static void startConsumer(BoundedQueue queue, ArrayList<Thread> threads) {
        System.out.println();
        log("소비자 시작");
        for (int i = 1; i <= 3; i++) {
            Thread thread = new Thread(new ConsumerTask(queue), "consumer" + i);
            threads.add(thread);
            thread.start();
            sleep(100);
        }
    }

    private static void printAllState(BoundedQueue queue, ArrayList<Thread> threads) {
        System.out.println();
        System.out.println("현재 상태 출력, 큐 데이터: " + queue);
        for (Thread thread : threads) {
            log(thread.getName() + ": " + thread.getState());
        }
    }

    private static void startProducer(BoundedQueue queue, ArrayList<Thread> threads) {
        System.out.println();
        log("생산자 시작");
        for (int i = 1; i <= 3; i++) {
            Thread producer = new Thread(new ProducerTask(queue, "data" + i), "producer" + i);
            threads.add(producer);
            producer.start();
            sleep(100);
        }
    }
}
