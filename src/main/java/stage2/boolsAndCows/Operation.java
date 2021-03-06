package stage2.boolsAndCows;

import stage2.demo.ConnectionProg;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Operation implements Closeable {
    String operationName = "";
    static Connection connection;
    protected final Statement statament = connection.createStatement();

    static {
        try {
            connection = ConnectionProg.createConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Operation(String operationName) throws SQLException {
        this.operationName = operationName;
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            statament.close();
        } catch (SQLException ex) {
            System.out.println("Ошибка закрытия SQL соединения!");
        }
    }
}
