public class AsciiArt {

    public static void printLogo() {
        System.out.println(Colors.BR_YELLOW + Colors.BOLD);
        System.out.println("  ███████╗ █████╗ ███████╗██╗   ██╗██╗     ");
        System.out.println("  ██╔════╝██╔══██╗██╔════╝██║   ██║██║     ");
        System.out.println("  ███████╗███████║█████╗  ██║   ██║██║     ");
        System.out.println("  ╚════██║██╔══██║██╔══╝  ██║   ██║██║     ");
        System.out.println("  ███████║██║  ██║██║     ╚██████╔╝███████╗");
        System.out.println("  ╚══════╝╚═╝  ╚═╝╚═╝      ╚═════╝ ╚══════╝");
        System.out.println(Colors.RESET);
        System.out.println(Colors.YELLOW + "         ♠  ♥  ♣  ♦   K A S Z I N O   ♦  ♣  ♥  ♠" + Colors.RESET);
        System.out.println();
    }

    public static void printBlackjackBanner() {
        System.out.println(Colors.CYAN + Colors.BOLD);
        System.out.println("  ╔══════════════════════════════╗");
        System.out.println("  ║   ♠  B L A C K J A C K  ♠   ║");
        System.out.println("  ╚══════════════════════════════╝");
        System.out.println(Colors.RESET);
    }

    public static void printRouletteBanner() {
        System.out.println(Colors.BR_RED + Colors.BOLD);
        System.out.println("  ╔══════════════════════════════╗");
        System.out.println("  ║    ◉   R O U L E T T E  ◉   ║");
        System.out.println("  ╚══════════════════════════════╝");
        System.out.println(Colors.RESET);
    }

    public static void printCrashBanner() {
        System.out.println(Colors.BR_YELLOW + Colors.BOLD);
        System.out.println("  ╔══════════════════════════════╗");
        System.out.println("  ║   📈   C R A S H   💥        ║");
        System.out.println("  ╚══════════════════════════════╝");
        System.out.println(Colors.RESET);
    }

    public static void printMinesBanner() {
        System.out.println(Colors.BR_GREEN + Colors.BOLD);
        System.out.println("  ╔══════════════════════════════╗");
        System.out.println("  ║    💎   M I N E S   💣        ║");
        System.out.println("  ╚══════════════════════════════╝");
        System.out.println(Colors.RESET);
    }

    public static void printTowerBanner() {
        System.out.println(Colors.PURPLE + Colors.BOLD);
        System.out.println("  ╔══════════════════════════════╗");
        System.out.println("  ║    🏰   T O W E R   🏰        ║");
        System.out.println("  ╚══════════════════════════════╝");
        System.out.println(Colors.RESET);
    }

    public static void printDiceBanner() {
        System.out.println(Colors.BR_CYAN + Colors.BOLD);
        System.out.println("  ╔══════════════════════════════╗");
        System.out.println("  ║    🎲    D I C E    🎲         ║");
        System.out.println("  ╚══════════════════════════════╝");
        System.out.println(Colors.RESET);
    }

    public static void printDivider() {
        System.out.println(Colors.YELLOW + "  ══════════════════════════════════" + Colors.RESET);
    }

    public static void printWin(double amount) {
        System.out.println();
        System.out.println(Colors.BR_GREEN + Colors.BOLD + "  ╔══════════════════════════╗");
        System.out.println("  ║   🎉  NYERTEL!  🎉        ║");
        System.out.printf("  ║   + %.2f Ft              ║%n", amount);
        System.out.println("  ╚══════════════════════════╝" + Colors.RESET);
        System.out.println();
    }

    public static void printLose(double amount) {
        System.out.println();
        System.out.println(Colors.BR_RED + Colors.BOLD + "  ╔══════════════════════════╗");
        System.out.println("  ║   💀  VESZTETTEL!  💀     ║");
        System.out.printf("  ║   - %.2f Ft              ║%n", amount);
        System.out.println("  ╚══════════════════════════╝" + Colors.RESET);
        System.out.println();
    }

    public static void animateCrash(double crashPoint) {
        System.out.println();
        double multiplier = 1.0;
        double step = 0.1;

        while (multiplier < crashPoint) {
            System.out.printf("\r  " + Colors.BR_YELLOW + Colors.BOLD + "📈  %.2fx" + Colors.RESET + "   ", multiplier);
            System.out.flush();
            multiplier += step;
            if (multiplier > 2.0)  step = 0.2;
            if (multiplier > 5.0)  step = 0.5;

            try { Thread.sleep(120); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }

        System.out.printf("\r  " + Colors.BR_RED + Colors.BOLD + "💥  CRASH @ %.2fx!" + Colors.RESET + "   %n", crashPoint);
    }

    public static void animateRoulette() {
        String[] symbols = { "◉", "▶", "◉", "◀", "◉", "▶", "◉" };
        System.out.print("  Forgatas... ");
        for (int i = 0; i < 20; i++) {
            System.out.print("\r  Forgatas... " + Colors.BR_YELLOW + symbols[i % symbols.length] + Colors.RESET + " ");
            System.out.flush();
            try { Thread.sleep(80); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        System.out.println();
    }

    public static void animateDice() {
        String[] faces = { "⚀", "⚁", "⚂", "⚃", "⚄", "⚅" };
        System.out.print("  Dobas... ");
        for (int i = 0; i < 12; i++) {
            System.out.print("\r  Dobas... " + Colors.BR_YELLOW + faces[i % 6] + "  " + faces[(i + 2) % 6] + Colors.RESET + "   ");
            System.out.flush();
            try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        System.out.println();
    }
}
