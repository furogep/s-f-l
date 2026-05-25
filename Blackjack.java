import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Blackjack {

    private Scanner scanner;
    private Player player;
    private ArrayList<String> deck;

    private static final String[] SUITS  = { "♠", "♥", "♦", "♣" };
    private static final String[] VALUES = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };

    public Blackjack(Scanner scanner, Player player) {
        this.scanner = scanner;
        this.player  = player;
    }

    public void play() {
        AsciiArt.printBlackjackBanner();

        double bet = getBet();
        if (bet == 0) return;

        buildDeck();

        ArrayList<String> playerHand  = new ArrayList<>();
        ArrayList<String> dealerHand  = new ArrayList<>();

        playerHand.add(drawCard());
        dealerHand.add(drawCard());
        playerHand.add(drawCard());
        dealerHand.add(drawCard());

        System.out.println();
        printHand("Osztó keze", dealerHand, true);
        printHand("A te lapjaid", playerHand, false);

        if (handValue(playerHand) == 21) {
            System.out.println(Colors.BR_YELLOW + "  ★ BLACKJACK! ★" + Colors.RESET);
            double winnings = bet * 1.5;
            player.add(winnings);
            AsciiArt.printWin(winnings);
            BalanceManager.save(player.getBalance());
            return;
        }

        boolean playerBust = playerTurn(playerHand, dealerHand, bet);

        if (!playerBust) {
            dealerTurn(dealerHand);
            printHand("Osztó végső keze", dealerHand, false);
            printHand("A te végső lapjaid", playerHand, false);
            evaluateResult(playerHand, dealerHand, bet);
        }

        BalanceManager.save(player.getBalance());
    }

    private boolean playerTurn(ArrayList<String> playerHand, ArrayList<String> dealerHand, double bet) {
        while (true) {
            int val = handValue(playerHand);
            if (val > 21) {
                System.out.println(Colors.BR_RED + "  Bust! Túlmentél 21-en." + Colors.RESET);
                player.subtract(bet);
                AsciiArt.printLose(bet);
                return true;
            }

            System.out.print("  [h] Lapot kérek  [s] Megállok  > ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("h")) {
                playerHand.add(drawCard());
                printHand("A te lapjaid", playerHand, false);
            } else if (choice.equals("s")) {
                break;
            } else {
                System.out.println("  Érvénytelen bemenet.");
            }
        }
        return false;
    }

    private void dealerTurn(ArrayList<String> dealerHand) {
        System.out.println();
        System.out.println("  Osztó húz...");
        while (handValue(dealerHand) < 17) {
            dealerHand.add(drawCard());
            try { Thread.sleep(400); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }

    private void evaluateResult(ArrayList<String> playerHand, ArrayList<String> dealerHand, double bet) {
        int pVal = handValue(playerHand);
        int dVal = handValue(dealerHand);

        System.out.printf("  Te: %d  |  Osztó: %d%n", pVal, dVal);
        System.out.println();

        if (dVal > 21 || pVal > dVal) {
            player.add(bet);
            AsciiArt.printWin(bet);
        } else if (pVal == dVal) {
            System.out.println(Colors.YELLOW + "  Döntetlen – visszakapod a tétet." + Colors.RESET);
        } else {
            player.subtract(bet);
            AsciiArt.printLose(bet);
        }
    }

    private void printHand(String label, ArrayList<String> hand, boolean hideFirst) {
        System.out.print("  " + Colors.BOLD + label + ": " + Colors.RESET);
        if (hideFirst) {
            System.out.print(Colors.DIM + "[??] " + Colors.RESET);
            for (int i = 1; i < hand.size(); i++) System.out.print(Colors.CYAN + hand.get(i) + " " + Colors.RESET);
        } else {
            for (String card : hand) System.out.print(Colors.CYAN + card + " " + Colors.RESET);
            System.out.print(Colors.YELLOW + "(" + handValue(hand) + ")" + Colors.RESET);
        }
        System.out.println();
    }

    private int handValue(ArrayList<String> hand) {
        int total = 0;
        int aces  = 0;
        for (String card : hand) {
            String val = card.replaceAll("[♠♥♦♣]", "");
            if (val.equals("A"))                         { total += 11; aces++; }
            else if (val.equals("J") || val.equals("Q") || val.equals("K")) total += 10;
            else total += Integer.parseInt(val);
        }
        while (total > 21 && aces > 0) { total -= 10; aces--; }
        return total;
    }

    private void buildDeck() {
        deck = new ArrayList<>();
        for (String suit : SUITS)
            for (String value : VALUES)
                deck.add(value + suit);
        Collections.shuffle(deck);
    }

    private String drawCard() {
        return deck.remove(0);
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
