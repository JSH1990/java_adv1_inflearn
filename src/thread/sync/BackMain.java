package thread.sync;

import static thread.control.TreadUtils.sleep;
import static util.MyLogger.log;

public class BackMain {
    public static void main(String[] args) throws InterruptedException {
        BankAccountV6 account = new BankAccountV6(1000);

        /*
        스레드를 생성할때, WithdrawTask클래스로 만들고, WithdrawTask클래스 생성자로 만든 통장(account)와 금액 800을 넣고, 스레드이름은 "t1"로 정한다.
         */
        Thread t1 = new Thread(new WithdrawTask(account, 800), "t1");
        Thread t2 = new Thread(new WithdrawTask(account, 800), "t2");

        t1.start();
        t2.start();

        sleep(500); //검증 완료까지 잠시 대기
        log("t1 state: " + t1.getState());
        log("t2 state: " + t2.getState());

        t1.join();
        t2.join();

        log("최종 잔액: " + account.getBalance());
    }
}
