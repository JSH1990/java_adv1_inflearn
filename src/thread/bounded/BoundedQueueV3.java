package thread.bounded;

import java.util.ArrayDeque;
import java.util.Queue;

import static thread.control.TreadUtils.sleep;
import static util.MyLogger.log;

/*
BoundedQueueV2 문제점
무한 대기
임계영역에서 lock 가지고 들어가 있어,
lock가지고 다른 스레드가 lock가지고 임계영역에 들어갈수없다.
 */


public class BoundedQueueV3 implements BoundedQueue {
    private final Queue<String> queue = new ArrayDeque<>(); //데이터를 중간에 보관하는 버퍼로 큐를 사용한다. 구현체로는 ArrayDeque를 사용한다.

    private final int max;


    /*
    int max : 한정된(Bounded) 버퍼 이므로, 버퍼에 저장 할 수 있는 최대 크기를 지정한다.
     */
    public BoundedQueueV3(int max) {
        this.max = max;
    }

    /*
    put : 큐에 데이터를 저장한다. 큐가 가득 찬 경우, 버퍼에 저장할 수 있는 최대 크기를 지정한다.
     */
    @Override
    public synchronized void put(String data) {
        while (queue.size() == max) {
            log("[put] 큐가 가득 참, 생산자 대기");
            try {
                wait(); //RUNNABLE -> WAITING, 락 반납
                log("[put] 생산자 깨어남");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        queue.offer(data);
        log("[put] 생산자 데이터 저장, notify() 호출");
        notify();//대기 스레드, WAIT -> BLOCKED
    }

    /*
    take : 큐에 데이터를 가져간다. 큐에 데이터가 없는 경우 null을 반환한다.
     */
    @Override
    public synchronized String take() {
        while (queue.isEmpty()) {
            log("[take] 큐에 데이터가 없음, 소비자 대기");
            try {
                wait(); //RUNNABLE -> WAITING, 락 반납
                log("[take] 소비자 깨어남");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        String data = queue.poll();
        log("[take] 소비자 데이터 획득, notify() 호출");
        notify(); //대기 스레드, WAIT -> BLOCKED
        return data;
    }

    @Override
    public String toString() {
       return queue.toString();
    }

}
