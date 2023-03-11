public class Transaction {
    String uid;
    Long sum;
    Account account;

    public Transaction(String uid, Long sum, Account account) {
        this.uid = uid;
        this.sum = sum;
        this.account = account;
    }



    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }



}
