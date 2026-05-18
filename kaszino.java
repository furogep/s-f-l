import java.util.Scanner;

public class kaszino {
    public static void main(String[] args) {
        System.out.println("Üdv a világ legkomolyabb kaszinojában!");
        
        while (true) {
            System.out.println();
            System.out.println("Válassz min szeretnél ma milliomos lenni:");
            System.out.println("1. Blackjack");
            System.out.println("2. Dice");
            System.out.println("3. Crash");
            System.out.println("4. Roulette");
            System.out.println("5. Tower");
            System.out.println("6. Mines");  

            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();

            if (input.equals("1")) {
                System.out.println("itt majd lesz valami");
            }
            else if (input.equals("2")) {
                System.out.println("itt majd lesz valami");
            }
            else if (input.equals("3")) {
                System.out.println("itt majd lesz valami");
            }
            else if (input.equals("4")) {
                System.out.println("itt majd lesz valami");
            }
            else if (input.equals("5")) {
                System.out.println("itt majd lesz valami");
            }
            else if (input.equals("6")) {
                System.out.println("itt majd lesz valami");
            }
            else {
                System.out.println("elbasztad");
            }
            
        }
        

    }
}