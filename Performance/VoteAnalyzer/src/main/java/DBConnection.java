import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection;

    private static String dbName = "&useUnicode=true&serverTimezone=UTC&useSSL=false&max_allowed_packet=128M";
    private static String dbUser = "root";
    private static String dbPass = "Alex1975";
    private static String dbN = "skillbox";

    public static Connection getConnection() {

        if (connection == null) {

            try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + dbN +
                                "?user=" + dbUser + "&password=" + dbPass + dbName);
                connection.createStatement().execute("DROP TABLE IF EXISTS voting");
                connection.createStatement().execute("CREATE TABLE voting (" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "name TINYTEXT NOT NULL, " +
                        "birthDate TINYTEXT NOT NULL, " +
                        "`station` INT NOT NULL, " +
                        "times TINYTEXT NOT NULL, " +
                        "PRIMARY KEY(id))");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void countVoter(String name, String birthDay) throws SQLException {
        birthDay = birthDay.replace('.', '-');
        String sql =
                "SELECT id FROM voter_count WHERE birthDate='" + birthDay + "' AND name='" + name + "'";
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        if (!rs.next()) {
            DBConnection.getConnection().createStatement()
                    .execute("INSERT INTO voter_count(name, birthDate, `count`) VALUES('" +
                            name + "', '" + birthDay + "', 1)");
        } else {
            Integer id = rs.getInt("id");
            DBConnection.getConnection().createStatement()
                    .execute("UPDATE voter_count SET `count`=`count`+1 WHERE id=" + id);
        }
        rs.close();
    }

    public static void printVoterCounts() throws SQLException {
        String sql = "SELECT name, birthDate, `count` FROM voter_count WHERE `count` > 1";
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        while (rs.next()) {
            System.out.println("\t" + rs.getString("name") + " (" +
                    rs.getString("birthDate") + ") - " + rs.getInt("count"));
        }
    }

    public static void sqlFileLoader(String textValues) {
        String sqlString = "INSERT INTO voting (name,birthDate,station,times) VALUES" +
                textValues + ";";

        try {
            DBConnection.getConnection().createStatement()
                    .execute(sqlString);
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }
}
