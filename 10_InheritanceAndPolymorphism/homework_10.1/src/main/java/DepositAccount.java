import java.time.LocalDate;

public class DepositAccount extends BankAccount {

    LocalDate lastIncome = LocalDate.now();

    @Override
    public void put(double amountToPut) {
        super.put(amountToPut);
        lastIncome = lastIncome.plusMonths(1);

    }

    @Override
    protected void take(double amountToTake) {

        if (LocalDate.now().isAfter(lastIncome)) {
            super.take(amountToTake);
        }
    }
}
