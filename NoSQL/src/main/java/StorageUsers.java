import org.redisson.Redisson;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class StorageUsers {
    private RScoredSortedSet<Integer> setUsers;

    public void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        try {
            RedissonClient redisson = Redisson.create(config);
            setUsers = redisson.getScoredSortedSet("Users");
            for (int i = 1; i < 21; i++) {

                setUsers.add(getTs(), i);
            }
        } catch (RedisConnectionException exc) {
            System.out.println("Не подключился");
        }
    }

    private double getTs() {
        return new Date().getTime() / 1000;
    }

    public void listUsers() throws InterruptedException {
        Collection<Integer> listUsers = setUsers.valueRange(0, 20);
        int firstRandomPerson = setUsers.random();
        int secondRandomPerson = setUsers.random();
        int iPaid = firstRandomPerson;
        int eventCount = 0;
        List<Integer> whoPaid = new ArrayList<>();
        int randomEvent = (int) (Math.random() * 10);
        for (Integer i : listUsers) {
            if (whoPaid.contains(i)) {
                continue;
            }
            if ((i == randomEvent) & (eventCount < 2)) {
                System.out.println("A User " + iPaid + " " + "has paid priced services");
                System.out.println("On the front page to show the User " + iPaid);
                Thread.sleep(1000);
                do {
                    randomEvent = (int) (Math.random() * 10 + 10);
                } while (!(listUsers.contains(randomEvent)) & (randomEvent > i));
                whoPaid.add(iPaid);
                iPaid = secondRandomPerson;
                eventCount++;
            }
            if (!whoPaid.contains(i)) {
                System.out.println("On the front page to show the User " + i);
                Thread.sleep(100);
                setUsers.addScore(i, getTs());
            }

        }
    }

}




