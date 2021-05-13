package RSA;

import java.math.BigInteger;
import java.util.Random;

/*
 *Rabin Miller algorithm for prime primality test
 */
public class RabinMiller {
	private static DefineRSA def = new DefineRSA();

	public RabinMiller() {
	}

	public static boolean isPrime(BigInteger N, int nbases) {

		// base cases
		if (N.equals(def.ZERO) || N.equals(def.ONE))
			return false;

		if (N.equals(def.TWO) || N.equals(def.THREE))
			return true;

		// even case
		if (N.mod(def.TWO) == def.ZERO) {
			return false;
		}

		// primality test
		BigInteger s = N.subtract(def.ONE);
		BigInteger r = def.ZERO;

		while (s.mod(def.TWO).equals(def.ZERO)) {
			r = r.add(def.ONE);
			s = s.divide(def.TWO);
		}

		if (!MillerTest(N, s, r, nbases))
			return false;
		return true;

	}

	private static boolean MillerTest(BigInteger N, BigInteger s, BigInteger r, int nbases) {
		BigInteger[] bases = new BigInteger[nbases];

		for (int i = 0; i < bases.length; i++) {
			bases[i] = RSA.uniformRandom(def.TWO, N.subtract(def.TWO));
		}

		loop: for (int i = 0; i < bases.length; i++) {

			BigInteger a = bases[i];
			BigInteger x = a.modPow(s, N);

			if (x.equals(def.ONE) || x.equals(N.subtract(def.ONE)))
				continue loop;

			for (BigInteger j = def.ONE; j.compareTo(r.subtract(def.ONE)) <= 0; j = j.add(def.ONE)) {

				// x = a.modPow((def.TWO.pow(j.intValue())).multiply(s), N);
				x = x.modPow(def.TWO, N);

				if (x.equals(def.ONE))
					return false;
				if (x.equals(N.subtract(def.ONE)))
					continue loop;
			}

			return false;
		}
		return true;
	}

}
