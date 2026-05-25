import java.util.Random;
import java.util.Scanner;

public class Tower {

    private Scanner scanner;
    private Player player;
    private Random random;
    private int[] traps;

    public Tower(Scanner scanner, Player player) {
        this.scanner = scanner;
        this.player = player;
        this.random = new Random();
    }

    public void play() {
        AsciiArt.printTowerBanner();
        System.out.println("  Mássz fel a toronyban! Minden szinten 3 ajtó van, de az egyik csapda.");
        System.out.println("  Minél magasabbra jutsz, annál nagyobb a szorzó.");
        System.out.println();

        double bet = InputHelper.getBet(scanner, player);
        if (bet == 0) return;

        setupTower();
        int level = 0;
        boolean alive = true;

        while (alive && level < 6) {
            printTower(level, false, -1);
            double multiplier = getMultiplier(level);
            System.out.printf("  Szint: %d/6 | Szorzó: " + Colors.BR_YELLOW + "%.2fx" + Colors.RESET + "%n", level, multiplier);
            System.out.println("  [1] bal ajtó   [2] középső   [3] jobb ajtó   [c] cashout");
            System.out.print("  > ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("c")) {
                if (level == 0) {
                    System.out.println(Colors.YELLOW + "  Előbb juss fel legalább egy szintet." + Colors.RESET);
                    continue;
                }
                double profit = bet * (multiplier - 1.0);
                player.add(profit);
                AsciiArt.printWin(profit);
                return;
            }

            int door;
            try {
                door = Integer.parseInt(input) - 1;
            } catch (NumberFormatException e) {
                System.out.println(Colors.RED + "  Érvénytelen ajtó." + Colors.RESET);
                continue;
            }

            if (door < 0 || door > 2) {
                System.out.println(Colors.RED + "  1, 2 vagy 3 közül válassz." + Colors.RESET);
                continue;
            }

            System.out.println();
            System.out.print("  Ajtó nyílik");
            for (int i = 0; i < 3; i++) {
                System.out.print(".");
                InputHelper.sleep(250);
            }
            System.out.println();

            if (door == traps[level]) {
                printTower(level, true, door);
                System.out.println(Colors.BR_RED + "  Csapda! Lezuhantál a toronyból." + Colors.RESET);
                player.subtract(bet);
                AsciiArt.printLose(bet);
                alive = false;
            } else {
                level++;
                System.out.println(Colors.BR_GREEN + "  Biztonságos ajtó! Feljebb jutottál." + Colors.RESET);
                InputHelper.sleep(400);
            }
        }

        if (alive && level == 6) {
            printTower(level, true, -1);
            double profit = bet * (getMultiplier(level) - 1.0);
            System.out.println(Colors.BR_YELLOW + "  Felértél a torony tetejére!" + Colors.RESET);
            player.add(profit);
            AsciiArt.printWin(profit);
        }

        BalanceManager.save(player.getBalance());
    }

    private void setupTower() {
        traps = new int[6];
        for (int i = 0; i < traps.length; i++) {
            traps[i] = random.nextInt(3);
        }
    }

    private double getMultiplier(int level) {
        double[] multipliers = { 1.0, 1.35, 1.85, 2.55, 3.55, 5.0, 7.25 };
        return multipliers[level];
    }

    private void printTower(int currentLevel, boolean reveal, int chosenDoor) {
        System.out.println();
        for (int level = 5; level >= 0; level--) {
            System.out.print("  ");
            if (level < currentLevel) {
                System.out.print(Colors.BR_GREEN + "  [✓] [✓] [✓]" + Colors.RESET);
            } else if (reveal && level == currentLevel) {
                for (int door = 0; door < 3; door++) {
                    if (door == traps[level]) System.out.print(Colors.BR_RED + " [X]" + Colors.RESET);
                    else if (door == chosenDoor) System.out.print(Colors.YELLOW + " [?]" + Colors.RESET);
                    else System.out.print(Colors.GREEN + " [O]" + Colors.RESET);
                }
            } else {
                System.out.print(Colors.CYAN + "  [1] [2] [3]" + Colors.RESET);
            }
            System.out.println("  " + Colors.DIM + "szint " + (level + 1) + Colors.RESET);
        }
        System.out.println();
    }
}
