import java.util.Scanner;

public class SAFUL {

    private Scanner scanner;
    private Player player;

    public SAFUL() {
        scanner = new Scanner(System.in);
        double savedBalance = BalanceManager.load();
        player = new Player("Vendég", savedBalance);
    }

    public static void main(String[] args) {
        SAFUL app = new SAFUL();
        app.run();
    }

    private void run() {
        clearScreen();
        AsciiArt.printLogo();
        System.out.print("  Add meg a játékosneved: ");
        String name = scanner.nextLine().trim();
        if (name.length() > 0) {
            player = new Player(name, player.getBalance());
        }

        boolean running = true;
        while (running) {
            clearScreen();
            printMainMenu();
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1":
                    new Blackjack(scanner, player).play();
                    InputHelper.pause(scanner);
                    break;
                case "2":
                    new Roulette(scanner, player).play();
                    InputHelper.pause(scanner);
                    break;
                case "3":
                    new Crash(scanner, player).play();
                    InputHelper.pause(scanner);
                    break;
                case "4":
                    new Mines(scanner, player).play();
                    InputHelper.pause(scanner);
                    break;
                case "5":
                    new Tower(scanner, player).play();
                    InputHelper.pause(scanner);
                    break;
                case "6":
                    new Dice(scanner, player).play();
                    InputHelper.pause(scanner);
                    break;
                case "r":
                    resetBalance();
                    InputHelper.pause(scanner);
                    break;
                case "q":
                    running = false;
                    break;
                default:
                    System.out.println(Colors.RED + "  Nincs ilyen menüpont." + Colors.RESET);
                    InputHelper.sleep(700);
            }
        }

        BalanceManager.save(player.getBalance());
        System.out.println();
        System.out.println(Colors.BR_YELLOW + "  Köszi, hogy játszottál a SÁFÜL kaszinóban!" + Colors.RESET);
        scanner.close();
    }

    private void printMainMenu() {
        AsciiArt.printLogo();
        System.out.println("  Játékos: " + Colors.CYAN + player.getName() + Colors.RESET);
        System.out.println("  Egyenleg: " + Colors.BR_GREEN + player.formattedBalance() + " Ft" + Colors.RESET);
        AsciiArt.printDivider();
        System.out.println("  [1] Blackjack");
        System.out.println("  [2] Roulette");
        System.out.println("  [3] Crash");
        System.out.println("  [4] Mines");
        System.out.println("  [5] Tower");
        System.out.println("  [6] Dice");
        System.out.println();
        System.out.println("  [r] Egyenleg reset");
        System.out.println("  [q] Kilépés");
        AsciiArt.printDivider();
        System.out.print("  Választás > ");
    }

    private void resetBalance() {
        System.out.print("  Biztosan visszaállítod az egyenleget 1000 Ft-ra? (i/n): ");
        String answer = scanner.nextLine().trim().toLowerCase();
        if (answer.equals("i")) {
            BalanceManager.reset();
            player.setBalance(BalanceManager.load());
            System.out.println(Colors.BR_GREEN + "  Egyenleg visszaállítva." + Colors.RESET);
        } else {
            System.out.println(Colors.YELLOW + "  Reset megszakítva." + Colors.RESET);
        }
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
