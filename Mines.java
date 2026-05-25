import java.util.Random;
import java.util.Scanner;

public class Mines {

    private Scanner scanner;
    private Player player;
    private Random random;
    private boolean[] mines;
    private boolean[] opened;

    public Mines(Scanner scanner, Player player) {
        this.scanner = scanner;
        this.player = player;
        this.random = new Random();
    }

    public void play() {
        AsciiArt.printMinesBanner();
        System.out.println("  Nyiss mezőket, gyűjts szorzót, de kerüld el a bombákat!");
        System.out.println("  3x3-as pálya, 2 bomba. Bármikor ki tudsz szállni.");
        System.out.println();

        double bet = InputHelper.getBet(scanner, player);
        if (bet == 0) return;

        setupBoard();
        int safeOpened = 0;
        boolean running = true;

        while (running) {
            printBoard(false);
            double multiplier = getMultiplier(safeOpened);
            System.out.printf("  Jelenlegi szorzó: " + Colors.BR_YELLOW + "%.2fx" + Colors.RESET + "%n", multiplier);
            System.out.println("  [1-9] mező nyitása   [c] cashout   [q] feladás");
            System.out.print("  > ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("c")) {
                if (safeOpened == 0) {
                    System.out.println(Colors.YELLOW + "  Előbb legalább egy mezőt nyiss ki." + Colors.RESET);
                    continue;
                }
                double profit = bet * (multiplier - 1.0);
                player.add(profit);
                AsciiArt.printWin(profit);
                running = false;
            } else if (input.equals("q")) {
                player.subtract(bet);
                AsciiArt.printLose(bet);
                running = false;
            } else {
                int pos;
                try {
                    pos = Integer.parseInt(input) - 1;
                } catch (NumberFormatException e) {
                    System.out.println(Colors.RED + "  Érvénytelen mező." + Colors.RESET);
                    continue;
                }

                if (pos < 0 || pos >= 9 || opened[pos]) {
                    System.out.println(Colors.RED + "  Ezt a mezőt nem tudod megnyitni." + Colors.RESET);
                    continue;
                }

                opened[pos] = true;
                if (mines[pos]) {
                    printBoard(true);
                    System.out.println(Colors.BR_RED + "  BOOM! Bombát találtál." + Colors.RESET);
                    player.subtract(bet);
                    AsciiArt.printLose(bet);
                    running = false;
                } else {
                    safeOpened++;
                    System.out.println(Colors.BR_GREEN + "  Szép! Ez gyémánt volt." + Colors.RESET);
                    InputHelper.sleep(350);

                    if (safeOpened == 7) {
                        double profit = bet * (getMultiplier(safeOpened) - 1.0);
                        printBoard(true);
                        System.out.println(Colors.BR_YELLOW + "  Tökéletes kör! Az összes biztonságos mezőt kinyitottad." + Colors.RESET);
                        player.add(profit);
                        AsciiArt.printWin(profit);
                        running = false;
                    }
                }
            }
        }

        BalanceManager.save(player.getBalance());
    }

    private void setupBoard() {
        mines = new boolean[9];
        opened = new boolean[9];
        int placed = 0;
        while (placed < 2) {
            int index = random.nextInt(9);
            if (!mines[index]) {
                mines[index] = true;
                placed++;
            }
        }
    }

    private double getMultiplier(int safeOpened) {
        return 1.0 + safeOpened * 0.35 + safeOpened * safeOpened * 0.08;
    }

    private void printBoard(boolean reveal) {
        System.out.println();
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) System.out.print("  ");
            System.out.print(cellText(i, reveal));
            if (i % 3 == 2) System.out.println();
        }
        System.out.println();
    }

    private String cellText(int i, boolean reveal) {
        if (opened[i] && mines[i]) return Colors.BR_RED + "[💣]" + Colors.RESET;
        if (opened[i]) return Colors.BR_GREEN + "[💎]" + Colors.RESET;
        if (reveal && mines[i]) return Colors.RED + "[💣]" + Colors.RESET;
        if (reveal) return Colors.GREEN + "[💎]" + Colors.RESET;
        return Colors.CYAN + "[" + (i + 1) + "]" + Colors.RESET;
    }
}
