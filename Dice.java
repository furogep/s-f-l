import java.util.Random;
import java.util.Scanner;

public class Dice {

    private Scanner scanner;
    private Player player;
    private Random random;

    public Dice(Scanner scanner, Player player) {
        this.scanner = scanner;
        this.player = player;
        this.random = new Random();
    }

    public void play() {
        AsciiArt.printDiceBanner();
        System.out.println("  Tippeld meg, hogy két kockával milyen tartomány jön ki!");
        System.out.println("  A kockák összege 2 és 12 között lehet.");
        System.out.println();

        double bet = InputHelper.getBet(scanner, player);
        if (bet == 0) return;

        System.out.println();
        System.out.println("  Fogadás típusa:");
        System.out.println("  [1] Kicsi (2-6)      – 1x");
        System.out.println("  [2] Hetes pontosan   – 4x");
        System.out.println("  [3] Nagy (8-12)      – 1x");
        System.out.println("  [4] Pontos összeg    – 5x");
        System.out.print("  > ");
        String choice = scanner.nextLine().trim();

        int exactGuess = -1;
        if (choice.equals("4")) {
            exactGuess = InputHelper.readInt(scanner, "  Melyik összegre tippelsz (2-12)? ", 2, 12);
            if (exactGuess == Integer.MIN_VALUE) return;
        }

        System.out.println();
        AsciiArt.animateDice();

        int d1 = random.nextInt(6) + 1;
        int d2 = random.nextInt(6) + 1;
        int sum = d1 + d2;
        System.out.println("  Dobás eredménye: " + Colors.BR_YELLOW + diceFace(d1) + " " + diceFace(d2) + Colors.RESET + "  = " + Colors.BOLD + sum + Colors.RESET);

        boolean won = false;
        double multiplier = 0;

        switch (choice) {
            case "1":
                won = sum >= 2 && sum <= 6;
                multiplier = 1;
                break;
            case "2":
                won = sum == 7;
                multiplier = 4;
                break;
            case "3":
                won = sum >= 8 && sum <= 12;
                multiplier = 1;
                break;
            case "4":
                won = sum == exactGuess;
                multiplier = 5;
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

    private String diceFace(int value) {
        String[] faces = { "⚀", "⚁", "⚂", "⚃", "⚄", "⚅" };
        return faces[value - 1];
    }
}
