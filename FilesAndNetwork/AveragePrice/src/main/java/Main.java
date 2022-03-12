import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        String url = "jdbc:mysql://localhost:3306/skillbox";
        String user = "root";
        String pass = "Alex1975";

        Connection connection = DriverManager.getConnection(url, user, pass);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT course_name, COUNT(student_name) / MAX(MONTH(subscription_date)) " +
                                                     "as c FROM purchaselist WHERE YEAR(subscription_date) = 2018 GROUP BY course_name");
        while (resultSet.next() ) {

            System.out.println(resultSet.getString("course_name") + "        " + resultSet.getString("c"));
        }
        resultSet.close();
        statement.close();
        connection.close();
    }
}
