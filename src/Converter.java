import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.Date;


public class Converter {

    private static HashMap<String, Double> exchangeRates = new HashMap<>();

    public static void main(String[] args) {

        exchangeRates.put("EUR", 1.0);

        exchangeRates.put("GBP", 1.15);

        exchangeRates.put("RUB", 0.0098);

        exchangeRates.put("CAD", 0.67);

        exchangeRates.put("JPY", 0.0063);

        boolean repeat = true;

        Scanner scanner = new Scanner(System.in);

        while (repeat) {

            printMenu();

            int userChoice = inputMenuChoice(scanner);

            switch (userChoice) {

                case 1:

                    printExchangeRates();

                    break;

                case 2:

                    addNewCurrency(scanner);

                    break;

                case 3:

                    convertCurrency(scanner);

                    break;

                case 4:

                    System.out.println("До свидания!");

                    repeat = false;

                    break;

                default:

                    System.out.println("Некорректный выбор. Попробуйте снова.\n");

                    break;

            }

        }

    }

    public static void printMenu() {
        System.out.println("\n═-═-═-═-═-═ Currency Converter ＄€￡ ═-═-═-═-═-═");

        System.out.println();

        System.out.println("==========  〚 МЕНЮ 〛 ==========");

        System.out.println("1. Просмотреть список доступных валют");

        System.out.println("2. Добавить новую валюту");

        System.out.println("3. Конвертер валют");

        System.out.println("4. Выход");

        System.out.println();

        System.out.print("\tВведите номер пункта меню: ");

    }

    public static int inputMenuChoice(Scanner scanner) {

        int choice = 0;

        boolean validInput = false;

        while (!validInput) {

            try {

                choice = scanner.nextInt();

                validInput = true;

            } catch (InputMismatchException e) {

                System.out.println("Некорректный выбор. Попробуйте снова.");

                System.out.print("\t Введите номер пункта меню: ");

                scanner.nextLine();

            }

        }

        return choice;

    }

    public static void printExchangeRates() {

        DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
        String date = dateFormat.format(new Date());
        System.out.println("Список доступных валют на " + date + ":");

        for (Map.Entry<String, Double> entry : exchangeRates.entrySet()) {

            System.out.println(entry.getKey() + " (" + entry.getValue() + ")");

        }

        System.out.println();

    }

    public static void addNewCurrency(Scanner scanner) {

        System.out.print("\n\tВведите код новой валюты: ");

        String newCurrency = scanner.next().toUpperCase();

        // Проверяем, что вводимый код валюты состоит только из букв и имеет длину 3 символа

        while (!isValidCurrencyCode(newCurrency)) {

            System.out.println("Некорректный код валюты. Попробуйте снова.");

            System.out.print("\tВведите код новой валюты: ");

            newCurrency = scanner.next().toUpperCase();

        }

        if (exchangeRates.containsKey(newCurrency)) {

            System.out.println("Валюта с таким кодом уже существует.");

        } else {

            System.out.print("Введите курс новой валюты: ");

            Double newRate = inputDoublePositiveNumber(scanner);

            exchangeRates.put(newCurrency, newRate);

            System.out.println("Валюта " + newCurrency + " добавлена в список.");

        }

    }

    public static boolean isValidCurrencyCode(String currencyCode) {

        // Проверяем, что в строке только буквы и длина равна 3 (стандартный код валюты)

        return currencyCode.matches("[A-Z]{3}");

    }

    public static void convertCurrency(Scanner scanner) {

        System.out.println("\nСписок доступных валют:");

        for (Map.Entry<String, Double> entry : exchangeRates.entrySet()) {

            System.out.print(entry.getKey() + " (" + entry.getValue() + ") ");
        }

        System.out.println();

        boolean validInput = false;

        while (!validInput) {

            System.out.print("Введите код первой валюты: ");

            String currency1 = scanner.next().toUpperCase();

            while (!isValidCurrencyCode(currency1)) {
                System.out.println("Некорректный код валюты.");
                currency1 = scanner.next().toUpperCase();
            }
            System.out.print("\nВведите код Второй валюты: ");


            String currency2 = scanner.next().toUpperCase();
            while (!isValidCurrencyCode(currency2)) {
                System.out.println("Некорректный код валюты.");
                currency2 = scanner.next().toUpperCase();
            }
            if (!exchangeRates.containsKey(currency1) || !exchangeRates.containsKey(currency2)) {
                System.out.println("Одна из указанных валют отсутствует в списке доступных.");
                continue;
            }

            System.out.print("Введите сумму для конвертации: ");

            double amount = inputDoublePositiveNumber(scanner);

            double rate1 = exchangeRates.get(currency1);

            double rate2 = exchangeRates.get(currency2);

            double result = amount * (rate1 / rate2);

            double comision = result / 100 * 2;

            System.out.printf(amount + " " + currency1 + " = " + result + " " + currency2 + " Comision %.2f", comision);
            System.out.println();
            System.out.println();

            validInput = true;

        }

    }

    public static Double inputDoublePositiveNumber(Scanner scanner) {

        double n = 0.0;

        boolean validInput = false;

        while (!validInput) {

            try {

                n = scanner.nextDouble();

                if (n > 0) {

                    validInput = true;

                } else {

                    System.out.println("Введите число больше нуля:");

                }

            } catch (InputMismatchException e) {

                System.out.println("Некорректный ввод. Введите число:");

                scanner.nextLine();

            }

        }

        return n;

    }

}