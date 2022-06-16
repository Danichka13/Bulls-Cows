package stage2.boolsAndCows;

import java.sql.SQLException;

public interface Table {
    void createTable() throws SQLException;

    static void selectBy(int choose) throws SQLException {

    }

    static void selectByName() throws SQLException {

    }

    static void saveIntoBd(String name, int time, int steps) throws SQLException {

    }
}
