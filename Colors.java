public class Colors {
    public static final String RESET   = "\033[0m";
    public static final String BOLD    = "\033[1m";
    public static final String RED     = "\033[31m";
    public static final String GREEN   = "\033[32m";
    public static final String YELLOW  = "\033[33m";
    public static final String BLUE    = "\033[34m";
    public static final String PURPLE  = "\033[35m";
    public static final String CYAN    = "\033[36m";
    public static final String WHITE   = "\033[37m";
    public static final String DIM     = "\033[2m";
    public static final String BR_RED    = "\033[91m";
    public static final String BR_GREEN  = "\033[92m";
    public static final String BR_YELLOW = "\033[93m";
    public static final String BR_CYAN   = "\033[96m";

    public static String c(String text, String color) {
        return color + text + RESET;
    }
}
