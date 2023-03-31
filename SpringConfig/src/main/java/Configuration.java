public class Configuration {

    private String url = "jdbc:postgresql://localhost:5432/dbname?useSSL=false&requireSSL=false";

    private String username = "username";

    private String password = "password";

    private String driverClassName = "org.postgresql.Driver";

    public Configuration() {};

    public String getDriverClassName() {
        return driverClassName;
    }
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
