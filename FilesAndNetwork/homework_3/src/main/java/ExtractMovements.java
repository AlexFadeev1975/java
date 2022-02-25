public class ExtractMovements {
    String descriptionTransaction;
    double debit, credit;

    public ExtractMovements() {
        descriptionTransaction = "";
        debit = 0.00;
        credit = 0.00;
    }

    public void setDescriptionTransaction(String descriptionTransaction) {
        this.descriptionTransaction = descriptionTransaction;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public String getDescriptionTransaction() {
        return descriptionTransaction;
    }

    public double getDebit() {
        return debit;
    }

    public double getCredit() {
        return credit;
    }
}
