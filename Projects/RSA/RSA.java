package RSA;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/*
 * RSA main functionality
 */
public class RSA {
	private static DefineRSA def = new DefineRSA();
	private static ExtendedEuclidean eu = new ExtendedEuclidean();

	public RSA() {
	}

	/*
	 * returns a random uniformed prime number between interval [bottom, top]
	 */
	protected static BigInteger uniformRandom(BigInteger bottom, BigInteger top) {
		Random rnd = new Random();
		BigInteger res;
		do {
			res = new BigInteger(top.bitLength(), rnd);
		} while (res.compareTo(bottom) < 0 || res.compareTo(top) > 0);
		return res;
	}

	/*
	 * returns a list of generated primes with certain bitlength of size limit
	 */
	protected static List<BigInteger> genPrimes(int bitlength, int limit) {
		List<BigInteger> list = new ArrayList<BigInteger>();
		Random rand = new Random(Long.MAX_VALUE);

		for (int i = 0; i < limit; i++) {
			BigInteger gen = new BigInteger("1");

			while (!RabinMiller.isPrime(gen, 20) && !list.contains(gen))
				gen = new BigInteger(bitlength, rand);
			list.add(gen);
		}
		return list;
	}

	/*
	 * returns a list of generated parameters for the RSA algorithm
	 */
	protected ArrayList<BigInteger> genRsaParameters() {
		List<BigInteger> primes = genPrimes(512, 2);
		List<BigInteger> parameters = new ArrayList<>();
		BigInteger p, q, e, N, m, d;
		p = primes.get(0);
		q = primes.get(1);
		N = p.multiply(q);
		m = p.subtract(def.ONE).multiply(q.subtract(def.ONE));
		e = new BigInteger("65537"); // currently the "best" choice
		d = eu.extendedEuclidean(e, m);

		parameters.addAll(Arrays.asList(p, q, N, e, d));
		return (ArrayList<BigInteger>) parameters;
	}

	public static void main(String[] args) {
		//System.out.println(op_time(100));
		//System.out.println(gen_primes_time(1024,100));
	}

	/*
	 * Computation time for generating 100 primes with bits
	 */
	private static double gen_primes_time(int bits,int limit) {
		double t0 = System.currentTimeMillis();
		System.out.println(genPrimes(bits, limit));
		double t1 = System.currentTimeMillis();
		double time = (t1 - t0);

		return time;
	}

	/*
	 * Computation time for OP in RabinMiller
	 */
	private static double op_time(int iterations) {
		ArrayList<Double> timearr = new ArrayList<>();
		double t0, t1, time;
		
		for (int i = 0; i < iterations; i++) {
			t0 = System.nanoTime();
			System.out.println(RabinMiller.isPrime(uniformRandom(def.ONE, BigInteger.valueOf(Long.MAX_VALUE)),20));
			t1 = System.nanoTime();
			timearr.add(t1-t0);
		}
		
		time = (timearr.stream().mapToDouble(Double::intValue).sum())/iterations;

			return time;

	}
}
