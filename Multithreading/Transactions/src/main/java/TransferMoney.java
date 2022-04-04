

public class TransferMoney extends Thread {
    String fromAccountNum, toAccountNum;
    long amount = 0;
    private Bank bank;

    public TransferMoney(Bank bank, String fromAccountNum, String toAccountNum, long amount) {
        this.fromAccountNum = fromAccountNum;
        this.toAccountNum = toAccountNum;
        this.amount = amount;
        this.bank = bank;
    }

    @Override
    public void run() {
        try {

            bank.transfer(fromAccountNum, toAccountNum, amount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
