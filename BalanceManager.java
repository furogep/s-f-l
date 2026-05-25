import java.io.RandomAccessFile;
import java.io.File;
import java.io.IOException;

public class BalanceManager {

    private static final String FILE_NAME       = "balance.dat";
    private static final double STARTING_BALANCE = 1000.0;

    public static void save(double balance) {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "rw")) {
            raf.seek(0);
            raf.writeDouble(balance);
        } catch (IOException e) {
            System.out.println(Colors.RED + "Mentesi hiba: " + e.getMessage() + Colors.RESET);
        }
    }

    public static double load() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return STARTING_BALANCE;

        try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "r")) {
            raf.seek(0);
            return raf.readDouble();
        } catch (IOException e) {
            System.out.println(Colors.RED + "Beolvasasi hiba: " + e.getMessage() + Colors.RESET);
            return STARTING_BALANCE;
        }
    }

    public static void reset() {
        save(STARTING_BALANCE);
    }
}
