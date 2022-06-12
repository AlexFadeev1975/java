public class Main {
    public static void main(String[] args) throws InterruptedException {

        StorageUsers storageUsers = new StorageUsers();
        storageUsers.init();

        while (true) {
            storageUsers.listUsers();

        }
    }
}
