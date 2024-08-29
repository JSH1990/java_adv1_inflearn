package thread.control.printer;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import static thread.control.TreadUtils.sleep;
import static util.MyLogger.log;

/*
main 스레드 : 사용자의 입력을 받아서 Printer 인스턴스의 jobQueue에 담는다.
printer 스레드 : jobQueue 가 있는지 확인한다.
- jobQueue에 내용이 있으면 poll() 이용해서 꺼낸 다음에 출력한다.
. 출력하는데는 약 3초의 시간이 걸린다. 여기서는 sleep(3000)을 사용해서 출력 시간을 가상으로 구현했다.
. 출력을 완료하면 while문을 다시 반복한다.

- 만약 jobQueue가 비었다면 continue를 사용해서 다시 while 문을 반복한다.
- 이렇게해서 jobQueue에 출력할 내용이 들어올때까지 계속 확인한다.
 */
public class MyPrinterV2 {
    public static void main(String[] args) {
        Printer printer = new Printer();
        Thread printerThread = new Thread(printer, "printer");
        printerThread.start();

        Scanner userInput = new Scanner(System.in);
        while (true){
            log("프린터할 문서를 입력하세요. 종료 (q): ");
            String input = userInput.nextLine();
            if(input.equals("q")){
                printer.work = false;
                printerThread.interrupt();
                break;
            }
            printer.addJob(input);
        }
    }

    static class Printer implements Runnable {
        volatile boolean work = true; //여러 스레드가 동시에 접근하는 변수에는 volatile 키워드를 붙여줘야 안전하다.
        //여러 스레드가 동시에 접근하는 경우 동시성을 지원하는 동시성 컬렉션을 사용해야 한다.
        Queue<String> jopQueue = new ConcurrentLinkedQueue<>();

        @Override
        public void run() {
            while (work){
                if(jopQueue.isEmpty()){
                    continue;
                }

                try {
                    String job = jopQueue.poll();
                    log("출력시작 :" + job + ", 대기문서: " + jopQueue);
                    Thread.sleep(3000);
                    log("출력 완료");
                } catch (InterruptedException e) {
                    log("인터럽트!");
                    break;
                }
            }
            log("프린터 종료");
        }

        public void addJob(String input){
            jopQueue.offer(input);
        }
    }
}
