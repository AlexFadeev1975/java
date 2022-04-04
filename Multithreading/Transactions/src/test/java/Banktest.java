import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Banktest extends TestCase {
    private Map<String, Account> accounts = new HashMap<String, Account>();
    List<String> accountList = new ArrayList<>();
    Bank bank = new Bank(accounts);
    int core = Runtime.getRuntime().availableProcessors();

    @Override
    protected void setUp() {

        for (int i = 1; i < 1000; i++) {
            String accountNumber = "12345" + ((Math.random() * 4000) + 1000);
            accounts.put("Клиент" + i, new Account(accountNumber, ((long) (Math.random() * 49000) + 1000)));
            accountList.add(accountNumber);

        }
    }

    public void testTransfer() throws InterruptedException {

        long expected = bank.getSumAllAccounts();
        int x = 0;
        while (x < 30) {
            Thread[] threads = new Thread[core];
            for (int i = 0; i < core; i++) {
                String luckyAccountFrom = accountList.get((int) (Math.random() * accountList.size()));
                String luckyAccountTo = accountList.get((int) (Math.random() * accountList.size()));
                long luckyamount = (long) (Math.random() * 60000);

                threads[i] = new TransferMoney(bank, luckyAccountFrom, luckyAccountTo, luckyamount);
            }
            for (Thread item : threads) {
                item.start();
                System.out.println(item.getName());
                item.join();
            }
            x++;
        }
        long actual = bank.getSumAllAccounts();


        assertEquals(expected, actual);

    }
}

