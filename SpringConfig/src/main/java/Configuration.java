public class Configuration {

    public String url = "jdbc:postgresql://localhost:5432/dbname?useSSL=false&requireSSL=false";

    public String username = "username";

    public String password = "password";

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
