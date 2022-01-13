public class BankAccount {

    protected double bankAccount = 0;

    protected double getAmount() {

        return bankAccount;
    }

    public void put(double amountToPut) {

        if (amountToPut >= 0) {
            bankAccount += amountToPut;
        }
    }

    protected void take(double amountToTake) {

        if (amountToTake <= bankAccount) {
            bankAccount -= amountToTake;
        }
    }
}
