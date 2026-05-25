import java.util.Random;
import java.util.Scanner;

public class Crash {

    private Scanner scanner;
    private Player player;
    private Random random;

    public Crash(Scanner scanner, Player player) {
        this.scanner = scanner;
        this.player  = player;
        this.random  = new Random();
    }

    public void play() {
        AsciiArt.printCrashBanner();

        System.out.println("  A szorzó elkezd nőni. Döntsd el, mikor szállsz ki!");
        System.out.println("  Minél magasabb, annál nagyobb a nyeremény – de bármikor crashelhet.");
        System.out.println();

        double bet = getBet();
        if (bet == 0) return;

        double crashPoint = generateCrashPoint();

        System.out.print("  Add meg, melyik szorzónál szállsz ki (pl. 2.5): ");
        double cashoutAt;
        try {
            cashoutAt = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(Colors.RED + "  Érvénytelen szám." + Colors.RESET);
            return;
        }

        if (cashoutAt < 1.0) {
            System.out.println(Colors.RED + "  Legalább 1.0x-nél kell kiszállni." + Colors.RESET);
            return;
        }

        System.out.println();
        AsciiArt.animateCrash(crashPoint);

        if (cashoutAt <= crashPoint) {
            double winnings = bet * (cashoutAt - 1.0);
            player.add(winnings);
            System.out.printf("  Kiszálltál @ " + Colors.BR_GREEN + "%.2fx" + Colors.RESET + "%n", cashoutAt);
            AsciiArt.printWin(winnings);
        } else {
            System.out.printf("  Crashelt @ " + Colors.BR_RED + "%.2fx" + Colors.RESET + " – te meg " + Colors.RED + "%.2fx" + Colors.RESET + "-nél akartál kiszállni.%n", crashPoint, cashoutAt);
            player.subtract(bet);
            AsciiArt.printLose(bet);
        }

        BalanceManager.save(player.getBalance());
    }

    private double generateCrashPoint() {
        double r = random.nextDouble();
        if (r < 0.05) return 1.0;
        double raw = 1.0 / (1.0 - r);
        return Math.round(raw * 100.0) / 100.0;
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
