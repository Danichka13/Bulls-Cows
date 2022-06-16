package stage2.demo;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class ConnectionProg {
    private static final Properties properties = new Properties();
    private static final String DATABASE_URL;
    private static final String URL_PROPERTIES = "src\\main\\resources\\database.properties"; //"src\\main\\resources\\database.properties"

    static {
//        Scanner scan = new Scanner(System.in);
//        System.out.println("Введите путь к характеристикам: ");
//        URL_PROPERTIES = scan.nextLine();
        try (FileReader fileReader = new FileReader(URL_PROPERTIES)){
            properties.load(fileReader);
            String driverName = (String) properties.get("db.driver");
            Class.forName(driverName);
        } catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }
        DATABASE_URL = (String) properties.get("db.url");
    }

    private ConnectionProg() {
    }

    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, properties);
    }
}