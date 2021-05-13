package RSA;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/*
 * Test cases for RSA 
 */
public class TestRSA {
	protected RabinMiller rm;
	protected BigInteger prime;
	protected DefineRSA def;
	protected RSA rsa;
	private int base;

	@Before
	public void initialize() {
		rm = new RabinMiller();
		def = new DefineRSA();
		rsa = new RSA();
		base = 20;
		System.out.println("_____________________________");
	}

	/*
	 * Test for Miller Rabin primality test
	 */
	@Test
	public final void is_Miller_Rabin_Prime() {
		System.out.println("\nTest Miller Rabin primality test: \n");
		
		System.out.println("Checking numbers isPrime..");
		assertTrue(RabinMiller.isPrime(BigInteger.valueOf(5), base));
		assertTrue(RabinMiller.isPrime(new BigInteger("643808006803554439230129854961492699151386107534013432918073439524138264842370630061369715394739134090922937332590384720397133335969549256322620979036686633213903952966175107096769180017646161851573147596390153"), base));
		assertFalse(RabinMiller.isPrime(new BigInteger("743808006803554439230129854961492699151386107534013432918073439524138264842370630061369715394739134090922937332590384720397133335969549256322620979036686633213903952966175107096769180017646161851573147596390153"), base));
		assertFalse(RabinMiller.isPrime(BigInteger.valueOf(123456789),base));

		System.out.println("Creating list of primes under 1000..");
		ArrayList<Integer> primes = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			if (RabinMiller.isPrime(BigInteger.valueOf(i),base)) 
				primes.add(i);
		}
		System.out.println("Asserting list size = 168");
		System.out.println(primes.size());
		assertEquals(168, primes.size());
		System.out.println("10 highest primes under 1000: "+primes.subList(primes.size() - 10, primes.size()));
		System.out.println("\n\t is_Miller_Rabin_Prime granted");
	}

	/*
	 * Test for RSA encryption and decryption
	 */
	public final void test_RSA_encryption_decryption(BigInteger N, BigInteger e, BigInteger d) {
		System.out.println("\nTest RSA encryption/decryption: \n");

		BigInteger plaintext = RSA.uniformRandom(def.TWO, N.subtract(def.ONE));
		System.out.println("Value of plaintext: " + plaintext);

		BigInteger ciphertext = plaintext.modPow(e, N);
		System.out.println("Value of ciphertext: " + ciphertext);

		BigInteger decrypted = ciphertext.modPow(d, N);
		System.out.println("Value of decryption: " + decrypted+"\n");

		assertEquals(plaintext, decrypted);
		System.out.println("\ttest_RSA_encryption_decryption granted");
	}

	/*
	 * Test generating prime numbers with bitlength 512
	 */
	@Test
	public final void test_RSA_prime_gen() {
		System.out.println("Test generate prime numbers: \n");
		int bitlength = 512;
		System.out.println("Generating " + bitlength + " -bit primes..\n");
		assertEquals(rsa.genPrimes(bitlength, 1).get(0).bitLength(), bitlength);
		System.out.println("\ttest_RSA_prime_gen granted");
	}

	/*
	 * Test for generating RSA parameters and running RSA test
	 */
	@Test
	public final void test_RSA_parameters() {
		System.out.println("Test generate RSA parameters: \n");
		
		ArrayList<BigInteger> pm = new ArrayList<BigInteger>();
		pm = rsa.genRsaParameters();

		BigInteger p = pm.get(0);
		BigInteger q = pm.get(1);
		BigInteger N = pm.get(2);
		BigInteger e = pm.get(3);
		BigInteger d = pm.get(4);
		
		System.out.println("p: "+p);
		System.out.println("q: "+q);
		System.out.println("N: "+N);
		System.out.println("e: "+e);
		System.out.println("d: "+d);

		assertNotEquals(p, q);
		assertNotEquals(d, 0);

		test_RSA_encryption_decryption(N, e, d);
		
		System.out.println("\ttest_RSA_parameters granted");
	}
}
