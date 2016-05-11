
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class IntFactorization {
    private BigInteger zero = new BigInteger("0");
    private BigInteger one = new BigInteger("1");
    private BigInteger divisor = new BigInteger("2");
    private ArrayList<BigInteger> factors = new ArrayList<BigInteger>();
    ArrayList<BigInteger> calcPrimeFactors(BigInteger num) throws UnknownHostException {
        if (num.compareTo(one) == 0) {
            return factors;
        }
        while (num.remainder(divisor).compareTo(zero) != 0) {
            divisor = divisor.add(one);
        }
        factors.add(divisor);
        InetAddress ip = InetAddress.getLocalHost();
        System.err.println("My IP is: "+ip);

		

        return calcPrimeFactors(num.divide(divisor));
    }

    public static void main (String argv []) throws UnknownHostException {
        IntFactorization obj = new IntFactorization();
        ArrayList<BigInteger> calc = obj.calcPrimeFactors(BigInteger.valueOf(Integer.valueOf(argv[0])));
        System.out.println(calc.toString());
    }
}

