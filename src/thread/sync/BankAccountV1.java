package thread.sync;

import static thread.control.TreadUtils.sleep;
import static util.MyLogger.log;

public class BankAccountV1 implements BackAccount{
    volatile private int balance;

    //int initialBalance - 계좌의 처음 들어오는 금액
    public BankAccountV1(int initialBalance) {
        this.balance = initialBalance;
    }

    @Override
    public boolean withdraw(int amount) {
        log("거래 시작 : " + getClass().getSimpleName());
        //잔고가 출금액 보다 적으면, 진행하면 안됨

        log("[검증 시작] 출금액: " + amount + ", 잔액:" + balance);
        if(balance < amount){
            log("[검증 실패] 출금액: " + amount + ", 잔액:" + balance);
            return false;
        }

        //잔고가 출금액 보다 많으면, 진행
        log("[검증 완료] 출금액: " + amount + ", 잔액:" + balance);
        sleep(1000); //출금에 걸리는 시간으로 가정
        balance = balance - amount; //현재 남은금액 = 기존 잔액 - 인출액
        log("[출금 완료] 출금액: " + amount + ", 잔액:" + balance);
        log("거래 종료");
        return true; //정상적으로 거래완료시 true 반환
    }

    @Override
    public int getBalance() {
        return balance;
    }
}
