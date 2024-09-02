package thread.bounded;

import java.util.ArrayDeque;
import java.util.Queue;

import static util.MyLogger.log;

/*
BoundedQueueV1 문제점
- 생산자 스레드 먼저 실행의 경우 p3가 보관하는 data3은 버려지고, c3는 데이터를 받지 못한다. (null를 받는다)
- 소비자 스레드 먼저 실행의 경우 c1, c2, c3는 데이터를 받지못한다.(null을 받는다.) 그리고 p3가 보관하는 data3은 버려진다.

레스토랑에 손님은 계속 찾아오고, 음료 공장은 계속해서 음료를 만들어낸다. 쇼핑몰 이라면 고객은 계속해서 주문을 한다.
 */


public class BoundedQueueV1 implements BoundedQueue {
    private final Queue<String> queue = new ArrayDeque<>(); //데이터를 중간에 보관하는 버퍼로 큐를 사용한다. 구현체로는 ArrayDeque를 사용한다.

    private final int max;


    /*
    int max : 한정된(Bounded) 버퍼 이므로, 버퍼에 저장 할 수 있는 최대 크기를 지정한다.
     */
    public BoundedQueueV1(int max) {
        this.max = max;
    }

    /*
    put : 큐에 데이터를 저장한다. 큐가 가득 찬 경우, 버퍼에 저장할 수 있는 최대 크기를 지정한다.
     */
    @Override
    public synchronized void put(String data) {
        if (queue.size() == max) {
            log("[put] 큐가 가득 참, 버림 : " + data);
            return;
        }
        queue.offer(data);
    }

    /*
    take : 큐에 데이터를 가져간다. 큐에 데이터가 없는 경우 null을 반환한다.
     */
    @Override
    public synchronized String take() {
        if (queue.isEmpty()) {
            return null;
        }

        return queue.poll();
    }

    @Override
    public String toString() {
       return queue.toString();
    }

}
