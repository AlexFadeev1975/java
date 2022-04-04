import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Bank {

    private Map<String, Account> accounts = new HashMap<>();
    private final Random random = new Random();
    List<String> blockedList = new ArrayList<>();

    public Bank(Map<String, Account> accounts) {
        this.accounts = accounts;
    }

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
            throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами. Если сумма транзакции > 50000,
     * то после совершения транзакции, она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка счетов (как – на ваше
     * усмотрение)
     */
    //
    public synchronized void transfer(String fromAccountNum, String toAccountNum, long amount) throws InterruptedException {

        String accountKeyFrom = "";
        String accountKeyTo = "";
        for (Map.Entry<String, Account> item : accounts.entrySet()) {

            Account account = item.getValue();
            if (account.getAccNumber().equals(fromAccountNum)) {
                accountKeyFrom = item.getKey();
            }
            if (account.getAccNumber().equals(toAccountNum)) {
                accountKeyTo = item.getKey();
            }
        }

        if (Objects.equals(accountKeyFrom, "") || (Objects.equals(accountKeyTo, ""))) {
            throw new IllegalArgumentException("Account is absent");
        }

        if ((accounts.get(accountKeyFrom).getMoney() >= amount)
                && !blockedList.contains(accountKeyFrom) && !blockedList.contains(accountKeyTo)) {

            accounts.replace(accountKeyFrom, new Account(fromAccountNum, accounts.get(accountKeyFrom).getMoney() - amount));
            accounts.replace(accountKeyTo, new Account(toAccountNum, accounts.get(accountKeyTo).getMoney() + amount));
        }

        if ((amount > 50000)) {
            if (isFraud(fromAccountNum, toAccountNum, amount)) {
                blockedList.add(accountKeyFrom);
                blockedList.add(accountKeyTo);

            }
        }
    }


    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum) {
        String accountKey = "";
        for (Map.Entry<String, Account> item : accounts.entrySet()) {
            Account account = item.getValue();
            if (account.getAccNumber().equals(accountNum)) {
                accountKey = item.getKey();
            }
        }
        return accounts.get(accountKey).getMoney();
    }

    public long getSumAllAccounts() {
        AtomicLong sumAccounts = new AtomicLong();
        Collection<Account> accountList = accounts.values();
        accountList.forEach(x -> sumAccounts.addAndGet(x.getMoney()));
        return sumAccounts.get();
    }


}



