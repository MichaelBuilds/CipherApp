
package crpto3;
import java.util.Random;

public class LetterGenerator {
    private String symbols = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,@,A,B,C,D,//,=-,-,!!,?";
    private Random rand = new Random();

    public String generateLetter() {
        StringBuilder sb = new StringBuilder();

        int index = rand.nextInt(symbols.length());
        sb.append(symbols.charAt(index));
        return sb.toString();
    }
}
