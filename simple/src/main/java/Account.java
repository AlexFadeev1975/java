
public class Account {

    String number;
    Long balance;

    public Account(String number, Long balance) {
        this.number = number;
        this.balance = balance;
    }



    public void setNumber(String number) {
        this.number = number;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    public Long getSum() {
        return balance;
    }
}
