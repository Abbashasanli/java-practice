package homeworks;

import org.postgresql.Driver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class JdbcApp {

    private static String allData = "select * from users";
    private static String insertData = "insert into users (name, email, phoneNUmber) values ('Anar', 'an@gmail.com', 0503235500);";
    private static String updateData = "update users set email =? where name=?";

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {

        Class postgresDriverClass = Class.forName("org.postgresql.Driver");
        Constructor postgresDriverClassConstructor = postgresDriverClass.getDeclaredConstructor();
        Driver postgresDriverObject = (Driver) postgresDriverClassConstructor.newInstance();
        DriverManager.registerDriver(postgresDriverObject);
        Connection dbConnection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/lesson29",
                "postgres", "mysecretpassword");
        getAllData(dbConnection);
        insertData(dbConnection);
        updateData(dbConnection, "Anar", "abcd@gmail.com");
        getAllData((dbConnection));

    }

    private static void getAllData(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(allData);
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            int number = resultSet.getInt("phoneNumber");

            System.out.println("\nId:" + id + "\nName:" + name + "\nEmail:" + email + "\nPhoneNUmber:" + number);

        }
    }

    private static void insertData(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(insertData);
    }

    private static void updateData(Connection connection, String name, String email) throws SQLException {
        PreparedStatement query = connection.prepareStatement(updateData);
        query.setString(1, email);
        query.setString(2, name);
        query.executeUpdate();
    }
}





