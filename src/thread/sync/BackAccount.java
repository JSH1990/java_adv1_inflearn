package thread.sync;

public interface BackAccount {
    /*
    boolean withdraw(int amount) - 계좌의 돈을 출금한다. 출금할 금액을 매개변수로 받는다.
    - 계좌의 잔액이 출금할 금액보다 많다면 출금에 성공하고, true를 반환한다.
    - 계좌의 잔액이 출금할 금액보다 적다면 출금에 실패하고, false를 반환한다.

    getBalance() - 계좌의 잔액을 반환한다.
     */
    boolean withdraw(int amount);

    int getBalance();
}
