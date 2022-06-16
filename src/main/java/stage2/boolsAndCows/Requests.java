package stage2.boolsAndCows;

import stage2.demo.PrintTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Requests extends Operation implements Table{

    public Requests() throws SQLException {
        super("statistic");
    }

    @Override
    public void createTable() throws SQLException {
        statament.execute("CREATE TABLE IF NOT EXISTS statistic(" +
                "ID BIGINT PRIMARY KEY AUTO_INCREMENT," +
                "NAME VARCHAR(255) NOT NULL," +
                "TIME INT," +
                "STEPS INT)");
    }

    public static void selectBy(int choose) throws SQLException{ // choose = 1 - для сортировки по времени, 2 - по шагам
        try {
            int count = 0;
            ResultSet resultSet;
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            switch (choose) {
                case 1:
                    System.out.println("Сортировка по времени");
                    resultSet = statement.executeQuery("SELECT * FROM Statistic ORDER BY TIME");
                    while (resultSet.next()) {
                        count++;
                    }
                    resultSet.beforeFirst();
                    String[][] table = new String[count + 1][];
                    table[0] = new String[]{"Имя", "Время(с)", "Количество шагов"};

                    int i = 1;
                    while (resultSet.next()) {
                        table[i] = new String[]{resultSet.getString("name"),
                                resultSet.getString("time"),
                                resultSet.getString("steps")};
                        i++;
                    }
                    PrintTable printTable = new PrintTable();
                    printTable.print(table);
                    break;
                case 2:
                    System.out.println("Сортировка по шагам");
                    resultSet = statement.executeQuery("SELECT * FROM Statistic ORDER BY STEPS");
                    while (resultSet.next()) {
                        count++;
                    }
                    resultSet.beforeFirst();
                    String[][] table2 = new String[count + 1][];
                    table2[0] = new String[]{"Имя", "Время(с)", "Количество шагов"};

                    int j = 1;
                    while (resultSet.next()) {
                        table2[j] = new String[]{resultSet.getString("name"),
                                resultSet.getString("time"),
                                resultSet.getString("steps")};
                        j++;
                    }
                    PrintTable printTable2 = new PrintTable();
                    printTable2.print(table2);
                    break;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void selectByName() throws SQLException{
        try {
            int count = 0;
            System.out.println("Введите имя");
            Scanner in = new Scanner(System.in);
            String userName = in.nextLine();
            while (userName.equals("")) {
                System.out.println("Имя не может быть пустым. Введите имя заново: ");
                in = new Scanner(System.in);
                userName = in.nextLine();
            }
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);;
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Statistic WHERE Name = '" + userName + "'");
            while (resultSet.next()) {
                count++;
            }
            resultSet.beforeFirst();
            String[][] table = new String[count + 1][];
            table[0] = new String[]{"Имя", "Время(с)", "Количество шагов"};

            int i = 1;
            while (resultSet.next()) {
                table[i] = new String[]{resultSet.getString("name"),
                        resultSet.getString("time"),
                        resultSet.getString("steps")};
                i++;
            }
            PrintTable printTable = new PrintTable();
            printTable.print(table);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void saveIntoBd(String name, int time, int steps) throws SQLException{
        try {
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate("INSERT INTO Statistic (NAME, TIME, STEPS) values ('" + name + "','" + time + "','" + steps + "');");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
