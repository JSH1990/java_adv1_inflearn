package thread.sync;

public class WithdrawTask implements Runnable{
    private BackAccount account; //출금하려면 BackAccount 클래스 필요
    private int amount; //얼마만큼 출금할지?

    public WithdrawTask(BackAccount account, int amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        account.withdraw(amount); //WithdrawTask 객체를 만들때, account 통장으로 얼마를 인출할지 정함
    }
}
