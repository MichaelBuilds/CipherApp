package crpto3;
import java.util.Random;

public class BlefGenerator {
    private Random rand = new Random();
    private String symbols = "!@#$%^&*()-_=+[]{}<>?/";

    public String generateBlefBlock() {
        StringBuilder blef = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int idx = rand.nextInt(symbols.length());
            blef.append(symbols.charAt(idx));
        }
        return blef.toString();
    }
}
