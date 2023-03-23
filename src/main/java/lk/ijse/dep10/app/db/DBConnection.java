package lk.ijse.dep10.app.db;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.Properties;

public class DBConnection {
    private static DBConnection dbConnection;
    private final Connection connection;

    private DBConnection() {
        File configFile = new File("application.properties");
        Properties configurations = new Properties();
        try {
            FileReader fr = new FileReader(configFile);
            configurations.load(fr);
            fr.close();

            String host = configurations.getProperty("team.project2.host", "localhost");
            String port = configurations.getProperty("team.project2.port", "3306");
            String database = configurations.getProperty("team.project2.database", "dep10_git");
            String username = configurations.getProperty("team.project2.username", "root");
            String password = configurations.getProperty("team.project2.password", "");

            String url = "jdbc:mysql" + host + ":" + port + "/" + database + "?createDatabaseIfNotExist=true";
            connection = DriverManager.getConnection(url, username, password);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to read configurations, try again").showAndWait();
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to obtain the database connection, try again").showAndWait();
            throw new RuntimeException(e);
        }
    }

    public static DBConnection getInstance() {
        return (dbConnection == null)? dbConnection =new DBConnection() : dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }

}
