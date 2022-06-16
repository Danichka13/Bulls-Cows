package stage2.demo;

import stage2.boolsAndCows.Requests;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Scanner;

public class App {
    static Requests requests;

    public App() throws SQLException {
        requests = new Requests();
    }

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        App app = new App();
        app.createTables();
        int result = 1;
        while (result != 0) {
            result = menu();
        }
        app.driversDelete();
    }

    public static int menu() throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        System.out.println("1.Играть");
        System.out.println("2.Статистика");
        System.out.println("0.Выход");
        System.out.println("<");
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        switch (num) {
            case 1:
                guessNum(enterName(), generateNum());
                return num;
            case 2:
                System.out.println("1.Рейтинг по времени");
                System.out.println("2.Рейтинг по шагам");
                System.out.println("3.Статистка конкретного пользователя");
                System.out.println("4.Назад");
                System.out.println("0.Выход");
                in = new Scanner(System.in);
                num = in.nextInt();
                switch (num) {
                    case 1:
                        Requests.selectBy(1);
                        return num;
                    case 2:
                        Requests.selectBy(2);
                        return num;
                    case 3:
                        Requests.selectByName();
                        return num;
                    case 4:
                        return num;
                    case 0:
                        return num;
                    default: return 1;
                }
            case 0:
                return num;
            default: return 1;
        }

    }

    public static void guessNum(String name, int[] startArr) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException { //метод, в котором пользователь угадывает число
        int bulls = 0;
        int cows = 0;
        int count = 0; // число шагов
        int timeSpent = 0; //время работы
        boolean check = true;
        long startTime = System.currentTimeMillis();// начало отсчета времени
        while (check) {
            System.out.println("\nВведите предпологаемое число: ");
            count++;
            try {
                Scanner in = new Scanner(System.in);
                String enter = in.nextLine();
                String strArr[] = enter.split(""); // делим строку посимвольно, после чего передаем цифры в целочисленный массив
                int usersArr[] = new int[startArr.length];
                for (int i = 0; i < strArr.length; i++) {
                    usersArr[i] = Integer.parseInt(strArr[i]);
                }
                for (int i = 0; i < startArr.length; i++) {
                    for (int j = 0; j < usersArr.length; j++) {
                        if (usersArr[j] == startArr[i]) { // если элеменент массива пользователя равен какому-то элементу массива компьютера
                            if (i == j) { // и при этом они находятся на одной позиции
                                bulls++; //тогда увеличиваем количество быков
                                break;
                            }
                            cows++; //иначе увеличиваем количество коров
                        }
                    }
                }
            } catch (NumberFormatException ex) {
                System.out.println("Нужно вводить число!");
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("Число должно быть четырехзначным!");
            }
            System.out.println("коров: " + cows + " быков: " + bulls);

            if (bulls == 4) {
                check = false;
                System.out.println();
                System.out.println("Вы угадали слово");
                timeSpent = (int) ((System.currentTimeMillis() - startTime) / 1000);
                System.out.println("Прошло времени, с: " + timeSpent);
                System.out.println("Количество шагов: " + count);
            }
            cows = 0;
            bulls = 0;
        }
        Requests.saveIntoBd(name, timeSpent, count);
    }

    public static String enterName() throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException { //метод ввода имени и сохранения его в бд
        System.out.println("Введите Ваше имя: ");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        while (name.equals("")) {
            System.out.println("Имя не может быть пустым. Введите имя заново: ");
            in = new Scanner(System.in);
            name = in.nextLine();
        }
        return name;
    }

    public static int[] generateNum() { //метод генерации числа компьютером
        int[] arr = new int[4];
        int j;
        int check, count = 0;
        boolean arrCheck = true;
        while (arrCheck) { // генерация элементов массива, чтобы каждый элемент был уникальным
            check = 0;
            int key = (int) (Math.random() * 10);
            for (j = 0; j < count; j++) {
                if (key == arr[j]) {
                    check++;
                }
            }
            arr[count] = key;
            if (check == 0) {
                count++;
            }
            if (count == 4)
                arrCheck = false;
        }
//        System.out.print("Загаданное число: ");
//        for (int i = 0; i < arr.length; i++) {
//            System.out.print(arr[i]);
//        }
        return arr;
    }

    public void createTables() throws SQLException {
        requests.createTable();
    }

    public void driversDelete() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}