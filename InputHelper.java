import java.util.Scanner;

public class InputHelper {

    public static double getBet(Scanner scanner, Player player) {
        System.out.printf("  Egyenleged: " + Colors.BR_GREEN + "%.2f Ft" + Colors.RESET + "%n", player.getBalance());
        System.out.print("  Téted (0 = vissza): ");

        try {
            double bet = Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
            if (bet == 0) return 0;
            if (bet < 0 || !player.canAfford(bet)) {
                System.out.println(Colors.RED + "  Érvénytelen összeg." + Colors.RESET);
                return 0;
            }
            return bet;
        } catch (NumberFormatException e) {
            System.out.println(Colors.RED + "  Érvénytelen bemenet." + Colors.RESET);
            return 0;
        }
    }

    public static int readInt(Scanner scanner, String prompt, int min, int max) {
        System.out.print(prompt);
        try {
            int value = Integer.parseInt(scanner.nextLine().trim());
            if (value < min || value > max) {
                System.out.println(Colors.RED + "  A számnak " + min + " és " + max + " között kell lennie." + Colors.RESET);
                return Integer.MIN_VALUE;
            }
            return value;
        } catch (NumberFormatException e) {
            System.out.println(Colors.RED + "  Érvénytelen szám." + Colors.RESET);
            return Integer.MIN_VALUE;
        }
    }

    public static void pause(Scanner scanner) {
        System.out.println();
        System.out.print(Colors.DIM + "  Enter a folytatáshoz..." + Colors.RESET);
        scanner.nextLine();
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
