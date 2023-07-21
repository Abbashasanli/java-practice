package InClassPractice.jdbc;

import org.postgresql.Driver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        Class postgresDriverClass = Class.forName("org.postgresql.Driver");
        Constructor postgreDriverClassconstructor = postgresDriverClass.getDeclaredConstructor();
        Driver postgresDriverObject = (Driver) postgreDriverClassconstructor.newInstance();

        DriverManager.registerDriver(postgresDriverObject);
        Connection connectionDb = DriverManager.getConnection("jdbc:postgresql://localhost:5432/lesson28", "postgres", "mysecretpassword");
        Statement statement = connectionDb.createStatement();
        ResultSet resultSet = statement.executeQuery(" select c.first_name, p.name, p.price, od.quantity, p.price * od.quantity as total_price\n" +
                "from orders o\n" +
                "         join order_details od on o.order_id = od.orderid\n" +
                "         join products p on od.productid = p.product_id\n" +
                "         join customers c on o.customer_id = c.customer_id\n" +
                "where c.first_name = 'John';");
        while (resultSet.next()) {
            String first_name = resultSet.getString("first_name");
            String name = resultSet.getString("name");
            double price = resultSet.getDouble("price");
            int quantity = resultSet.getInt("quantity");
            double totalPrice = resultSet.getDouble("total_price");
            System.out.println("\nfirst_name:" + first_name +
                    "\nname:" + name + "\nprice:" + price +
                    "\nquantity:" + quantity +
                    "\ntotal_price:" + totalPrice);

        }
        resultSet.next();
        String myResult = resultSet.getString(1);
        System.out.println(myResult);
        resultSet.close();
        statement.close();
        connectionDb.close();


    }
}
