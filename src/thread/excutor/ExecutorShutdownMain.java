package thread.excutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static util.MyLogger.log;

public class ExecutorShutdownMain {
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(2);
        es.execute(new RunnableTask("taskA")); //작업 처리에 필요한 시간 : taskA, taskB, taskC 1초
        es.execute(new RunnableTask("taskB"));
        es.execute(new RunnableTask("taskC"));
        es.execute(new RunnableTask("longTask", 100_000)); //100초 대기
        log("== shutdown 시작");
        shutdownAndAwaitTermination(es);
        log("== shutdown 종료");
    }

    private static void shutdownAndAwaitTermination(ExecutorService es) {
        es.shutdown(); //non - blocking, 새로운 작업을 받지 않는다. 처리 중이거나, 큐에 이미 대기중인 작업은 처리한다. 이후에 풀의 스레드에 종료한다.

        try {
            //이미 대기중인 작업들을 모두 완료 할때 까지 10초 기다린다.
            if (!es.awaitTermination(10, TimeUnit.SECONDS)) {//작업이 완료 되면 true 반환한다.
                //정상 종료가 너무 오래 걸리면..
                log("서비스 정상 종료 실패 -> 강제 종료 시도");
                es.shutdownNow();
                //작업이 취소될때 까지 대기한다.
                /*
                마지막에 강제 종료인 es.shutdownNow() 를 호출한 다음에 10초간 또 기다리는 이유는 shutdownNow() 가 작업중인 스레드에 인터럽트를 호출하는 것은 맞다.
                인터럽트를 호출하더라도, 여러가지 이유로 작업에 시간이 걸릴수도 있다. 인터럽트 이후에 자원을 정리하는 어떤 간단한 작업을 수행할수도 있다.
                이런 시간을 기다리는 것이다.
                 */
                if (!es.awaitTermination(10, TimeUnit.SECONDS)) { //false 반환한다.
                    log("서비스가 종료되지 않았습니다.");
                }
            }
        } catch (InterruptedException e) {
            //awaitTermination() 으로 대기중인 현재 스레드가 인터럽트 될 수 있다.
        }
    }
}

