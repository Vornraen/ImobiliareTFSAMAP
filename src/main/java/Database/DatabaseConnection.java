package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        String connectionUrl =
                "jdbc:sqlserver://DESKTOP-1M67APK;"
                        + "database=Imobiliare;"
                        + "user=sa;"
                        + "password=xd;"
                        + "encrypt=true;"
                        + "trustServerCertificate=true;"
                        + "loginTimeout=30;";
        Connection connection = DriverManager.getConnection(connectionUrl);
        return connection;
    }
}
