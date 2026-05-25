public class Player {
    private String name;
    private double balance;

    public Player(String name, double balance) {
        this.name    = name;
        this.balance = balance;
    }

    public String getName()    { return name; }
    public double getBalance() { return balance; }

    public void setBalance(double amount) { this.balance = amount; }
    public void add(double amount)        { this.balance += amount; }
    public void subtract(double amount)   { this.balance -= amount; }

    public boolean canAfford(double amount) { return this.balance >= amount; }

    public String formattedBalance() {
        return String.format("%.2f", balance);
    }
}
