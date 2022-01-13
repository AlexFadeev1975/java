public class CardAccount extends BankAccount {

    static final double FEEINCASH = 0.01;

    @Override
    protected void take(double amountToTake) {

        super.take(amountToTake + amountToTake * FEEINCASH);
    }
}

