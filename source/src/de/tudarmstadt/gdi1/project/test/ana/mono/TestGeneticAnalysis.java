package de.tudarmstadt.gdi1.project.test.ana.mono;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.Assert;
import org.junit.Test;
import de.tudarmstadt.gdi1.project.alph.ImplAlphabet;
import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.Dictionary;
import de.tudarmstadt.gdi1.project.alphabet.Distribution;
import de.tudarmstadt.gdi1.project.ana.ImplValDecrOracle;
import de.tudarmstadt.gdi1.project.analysis.monoalphabetic.MonoalphabeticKnownCiphertextCryptanalysis;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.MonoalphabeticCipher;
import de.tudarmstadt.gdi1.project.test.TemplateTestCore;
import de.tudarmstadt.gdi1.project.test.TemplateTestUtils;


/**
 * tests the genetic algorithm using our own oracle
 * @author vamp
 * @version FINAL
 *
 */
public class TestGeneticAnalysis {
	
	/**
	 * generates a random text with whitespaces
	 * @param alph the alphabet
	 * @param len the number of charcters
	 * @return a random text
	 */
	public String generateText(Alphabet alph, int len){
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < len; i++){
			if ((random.nextInt(6) == 0) && (! sb.toString().endsWith(" "))){
				sb.append(" ");
			} else {
				sb.append(alph.getChar(random.nextInt(alph.size())));
			}
		}
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	
	/**
	 * generates a new random text which gets encrypted to be broken
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@Test 
	public void testFullAnalysisRandom() throws IOException, InterruptedException, ExecutionException {
		Alphabet source = new ImplAlphabet(new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r'});
		Alphabet target = new ImplAlphabet(new char[]{'b','f','o','d','p','e','l','n','c','a','g','h','i','j','k','q','m','r'});
		
		String text = generateText(source, 500);

		final Distribution distribution = TemplateTestCore.getFactory().getDistributionInstance(source, text, 3);
		final Dictionary dictionary = TemplateTestCore.getFactory().getDictionaryInstance(source, text);

		final String plaintext = source.normalize(text);
		MonoalphabeticCipher cipher = TemplateTestCore.getFactory().getMonoalphabeticCipherInstance(source, target);
		final String ciphertext = cipher.encrypt(plaintext);
		final MonoalphabeticKnownCiphertextCryptanalysis ca = TemplateTestCore.getFactory()
				.getMonoalphabeticKnownCiphertextCryptanalysisInstance();

		/* run break in thread */
		Callable<char[]> task = new Callable<char[]>() {

			@Override
			public char[] call() throws Exception {
				char[] reconstructedKey = ca.knownCiphertextAttack(ciphertext, distribution, dictionary
						, new ImplValDecrOracle(distribution, dictionary)
						);
				return reconstructedKey;
			}
		};
		ExecutorService service = Executors.newSingleThreadExecutor();
		Future<char[]> future = service.submit(task);

		long t = System.currentTimeMillis();
		while (!future.isDone()) {
			Thread.sleep(5000);
			System.out.println(ca.getState(distribution.getAlphabet(), target));
		}

		char[] reconstructedKey = future.get();

		System.out.println("-- reconstruction finished in " + (System.currentTimeMillis() - t) + "ms");
		System.out.println(reconstructedKey);
		cipher = TemplateTestCore.getFactory().getMonoalphabeticCipherInstance(source, TemplateTestUtils.getAlphabetFrom(reconstructedKey));
		String plaintextPrime = cipher.decrypt(ciphertext);

		if (!plaintextPrime.equals(plaintext))
			Assert.assertEquals(0, TemplateTestUtils.countDifferences(target.asCharArray(), reconstructedKey));
	}
		

	
	
	/**
	 * uses the given test case but runs with our own oracle
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@Test
	public void testFullAnalysisAlice() throws IOException, InterruptedException, ExecutionException {

		Alphabet source = TemplateTestUtils.getDefaultAlphabet();
		Alphabet target = TemplateTestUtils.getMixedDefaultAlphabet();

		/* generate distribution */
		final Distribution distribution = TemplateTestCore.getFactory().getDistributionInstance(source, TemplateTestUtils.ALICE, 3);
		final Dictionary dictionary = TemplateTestCore.getFactory().getDictionaryInstance(source, TemplateTestUtils.ALICE);

		/* get ciphertext */
		final String plaintext = source.normalize(TemplateTestUtils.ALICE_PLAIN);
		MonoalphabeticCipher cipher = TemplateTestCore.getFactory().getMonoalphabeticCipherInstance(source, target);
		final String ciphertext = cipher.encrypt(plaintext);
		
		final MonoalphabeticKnownCiphertextCryptanalysis ca = TemplateTestCore.getFactory()
				.getMonoalphabeticKnownCiphertextCryptanalysisInstance();

		/* run break in thread */
		Callable<char[]> task = new Callable<char[]>() {

			@Override
			public char[] call() throws Exception {
				char[] reconstructedKey = ca.knownCiphertextAttack(ciphertext, distribution, dictionary
						, new ImplValDecrOracle(distribution, dictionary)
						);
				return reconstructedKey;
			}
		};
		ExecutorService service = Executors.newSingleThreadExecutor();
		Future<char[]> future = service.submit(task);

		long t = System.currentTimeMillis();
		while (!future.isDone()) {
			Thread.sleep(5000);
			System.out.println(ca.getState(distribution.getAlphabet(), target));
		}

		char[] reconstructedKey = future.get();

		System.out.println("-- reconstruction finished in " + (System.currentTimeMillis() - t) + "ms");
		System.out.println(reconstructedKey);
		cipher = TemplateTestCore.getFactory().getMonoalphabeticCipherInstance(source, TemplateTestUtils.getAlphabetFrom(reconstructedKey));
		String plaintextPrime = cipher.decrypt(ciphertext);

		if (!plaintextPrime.equals(plaintext))
			Assert.assertEquals(0, TemplateTestUtils.countDifferences(target.asCharArray(), reconstructedKey));
	}
}
