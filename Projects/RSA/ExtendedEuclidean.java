package RSA;

import java.math.BigInteger;

/*
 *Extended Euclidean Algorithm for computing the private key d
 */
public class ExtendedEuclidean {
	private static DefineRSA def = new DefineRSA();

	public ExtendedEuclidean() {
	}

	/*
	 * main algorithm, returns private key d
	 */
	public static BigInteger extendedEuclidean(BigInteger e, BigInteger m) {
		if (!(gcd(e, m).equals(1))) {

			BigInteger mtmp = m;
			BigInteger d1 = m;
			BigInteger d2 = e;
					
			BigInteger v1 = def.ZERO;
			BigInteger v2 = def.ONE;

			if (d1.equals(def.ONE)) {
				return def.ZERO;
			}

			while (d2.compareTo(def.ONE) > 0) {
				BigInteger q = d2.divide(d1);
				BigInteger t = d1;

				d1 = d2.mod(d1);
				d2 = t;
				t = v1;
				v1 = v2.subtract(q.multiply(v1));
				v2 = t;
			}

			if (v2.compareTo(def.ZERO) < 0) {
				v2 = v2.add(mtmp);
			}

			return v2;

		} else {
			System.out.println("No inverse exists!");
			return def.ZERO;
		}
	}

	/*
	 * returns greatest common divisor for values d2 and d1
	 */
	private static BigInteger gcd(BigInteger d2, BigInteger d1) {
		if (!d1.equals(def.ZERO)) {
			return gcd(d1, d2.mod(d1));
		} else {
			return d2;
		}

	}
	
	public static void main(String[] args) {
		
		BigInteger i = extendedEuclidean(BigInteger.valueOf(479), BigInteger.valueOf(30492));
		System.out.println(i);
	}

}
