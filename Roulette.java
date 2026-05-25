import java.util.Random;
import java.util.Scanner;

public class Roulette {

    private Scanner scanner;
    private Player player;
    private Random random;

    private static final int[] RED_NUMBERS = {
        1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36
    };

    public Roulette(Scanner scanner, Player player) {
        this.scanner = scanner;
        this.player  = player;
        this.random  = new Random();
    }

    public void play() {
        AsciiArt.printRouletteBanner();
        printRouletteTable();

        double bet = getBet();
        if (bet == 0) return;

        System.out.println();
        System.out.println("  Fogadás típusa:");
        System.out.println("  [1] Szám (0-36)    – 35x");
        System.out.println("  [2] Piros / Fekete – 1x");
        System.out.println("  [3] Páros / Páratlan – 1x");
        System.out.println("  [4] 1-18 / 19-36   – 1x");
        System.out.print("  > ");

        String typeChoice = scanner.nextLine().trim();
        String guess = askGuess(typeChoice);
        if (guess == null) return;

        System.out.println();
        AsciiArt.animateRoulette();
        int result = random.nextInt(37);
        boolean isRed = isRed(result);

        System.out.println();
        printResult(result, isRed);
        System.out.println();

        boolean won = false;
        double multiplier = 0;

        switch (typeChoice) {
            case "1":
                won = handleNumberBet(result, guess);
                multiplier = 35;
                break;
            case "2":
                won = handleColorBet(result, isRed, guess);
                multiplier = 1;
                break;
            case "3":
                won = handleParityBet(result, guess);
                multiplier = 1;
                break;
            case "4":
                won = handleRangeBet(result, guess);
                multiplier = 1;
                break;
            default:
                System.out.println(Colors.RED + "  Érvénytelen választás." + Colors.RESET);
                return;
        }

        if (won) {
            double winnings = bet * multiplier;
            player.add(winnings);
            AsciiArt.printWin(winnings);
        } else {
            player.subtract(bet);
            AsciiArt.printLose(bet);
        }

        BalanceManager.save(player.getBalance());
    }

    private String askGuess(String typeChoice) {
        switch (typeChoice) {
            case "1":
                System.out.print("  Tipped (0-36): ");
                String numberGuess = scanner.nextLine().trim();
                try {
                    int guess = Integer.parseInt(numberGuess);
                    if (guess < 0 || guess > 36) {
                        System.out.println(Colors.RED + "  Érvénytelen szám." + Colors.RESET);
                        return null;
                    }
                    return numberGuess;
                } catch (NumberFormatException e) {
                    System.out.println(Colors.RED + "  Érvénytelen bemenet." + Colors.RESET);
                    return null;
                }
            case "2":
                System.out.print("  [p] Piros  [f] Fekete: ");
                String color = scanner.nextLine().trim().toLowerCase();
                if (color.equals("p") || color.equals("f")) return color;
                System.out.println(Colors.RED + "  Érvénytelen." + Colors.RESET);
                return null;
            case "3":
                System.out.print("  [e] Páros  [o] Páratlan: ");
                String parity = scanner.nextLine().trim().toLowerCase();
                if (parity.equals("e") || parity.equals("o")) return parity;
                System.out.println(Colors.RED + "  Érvénytelen." + Colors.RESET);
                return null;
            case "4":
                System.out.print("  [a] 1-18  [b] 19-36: ");
                String range = scanner.nextLine().trim().toLowerCase();
                if (range.equals("a") || range.equals("b")) return range;
                System.out.println(Colors.RED + "  Érvénytelen." + Colors.RESET);
                return null;
            default:
                System.out.println(Colors.RED + "  Érvénytelen választás." + Colors.RESET);
                return null;
        }
    }

    private boolean handleNumberBet(int result, String guessText) {
        return Integer.parseInt(guessText) == result;
    }

    private boolean handleColorBet(int result, boolean isRed, String choice) {
        if (result == 0) {
            System.out.println(Colors.YELLOW + "  Zöld (0) – sajnos veszítettél." + Colors.RESET);
            return false;
        }
        if (choice.equals("p")) return isRed;
        if (choice.equals("f")) return !isRed;
        return false;
    }

    private boolean handleParityBet(int result, String choice) {
        if (result == 0) {
            System.out.println(Colors.YELLOW + "  Zöld (0) – sajnos veszítettél." + Colors.RESET);
            return false;
        }
        if (choice.equals("e")) return result % 2 == 0;
        if (choice.equals("o")) return result % 2 != 0;
        return false;
    }

    private boolean handleRangeBet(int result, String choice) {
        if (result == 0) {
            System.out.println(Colors.YELLOW + "  Zöld (0) – sajnos veszítettél." + Colors.RESET);
            return false;
        }
        if (choice.equals("a")) return result >= 1 && result <= 18;
        if (choice.equals("b")) return result >= 19 && result <= 36;
        return false;
    }

    private void printResult(int result, boolean isRed) {
        String colorTag = (result == 0) ? Colors.BR_GREEN : (isRed ? Colors.BR_RED : Colors.WHITE);
        String colorName = (result == 0) ? "ZÖLD" : (isRed ? "PIROS" : "FEKETE");
        System.out.println("  Eredmény: " + colorTag + Colors.BOLD + result + " (" + colorName + ")" + Colors.RESET);
    }

    private void printRouletteTable() {
        System.out.println();
        System.out.println("  " + Colors.BR_GREEN + "  0  " + Colors.RESET);
        System.out.print("  ");
        for (int i = 1; i <= 36; i++) {
            String col = isRed(i) ? Colors.BR_RED : Colors.WHITE;
            System.out.printf("%s%3d%s", col, i, Colors.RESET);
            if (i % 12 == 0) System.out.print("\n  ");
        }
        System.out.println();
    }

    private boolean isRed(int number) {
        for (int n : RED_NUMBERS)
            if (n == number) return true;
        return false;
    }

    private double getBet() {
        System.out.printf("  Egyenleged: " + Colors.BR_GREEN + "%.2f Ft" + Colors.RESET + "%n", player.getBalance());
        System.out.print("  Téted (0 = vissza): ");
        try {
            double bet = Double.parseDouble(scanner.nextLine().trim());
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
}
