package de.tudarmstadt.gdi1.project.test.ana.caesar;

import org.junit.*;

import de.tudarmstadt.gdi1.project.ana.caesar.*;
import de.tudarmstadt.gdi1.project.ciph.subst.mono.*;
import de.tudarmstadt.gdi1.project.alphabet.*;
import de.tudarmstadt.gdi1.project.alph.*;
import de.tudarmstadt.gdi1.project.test.*;

/**
 * tests the caesar cryptanalysis also with the second and third character solution
 * @author vamp
 * @version FINAL
 */
public class TestCaesarCryptAna {
	
	CaesarCipher cipher;
	CaesarCipher cipher2, cipher3;
	CaesarCryptAna decryption;
	String german = "eeeeeeeeeeeeeeeeennnnnnnnnniiiiiiiisssssssrrrrrrraaaaaaattttttdddddhhhhhuuuulllcccgggmmmooobbwwffkzpv";
	String distrStr2 = "aab";
	String distrStr = "aaaaaaaaabbbbbbbbccd";
	String text1 = "aaabbbb"; //contains one b more than a would therefore not be decrypted correctly
	String text2 = "aaaabbbc";
	String text3 = "aaa";
	String encr1, encr2, encr3, encr4, encr5;
	String message = "diesisteinenachrichtderdeutschensprache";
	ImplDistribution distr, distr2, distrGerman;
	Alphabet alph = TemplateTestUtils.getDefaultAlphabet();
	ImplAlphabet alph4 = new ImplAlphabet(new char[]{'a', 'b', 'c', 'd'});
	ImplAlphabet alph2= new ImplAlphabet(new char[]{'a', 'b'});
	
	@Before
	public void init(){
		cipher = new CaesarCipher(alph4, 2);
		cipher2 = new CaesarCipher(alph2, 0);
		cipher3 = new CaesarCipher(alph, 13);
		decryption = new CaesarCryptAna();
		encr1 = cipher.encrypt(text1);
		encr2 = cipher.encrypt(text2);
		encr3 = cipher2.encrypt(text1);
		encr4 = cipher2.encrypt(text3);
		encr5 = cipher3.encrypt(message);
		distr = new ImplDistribution(alph4, distrStr, 1);
		distr2 = new ImplDistribution(alph2, distrStr2, 1);
		distrGerman = new ImplDistribution(alph, german, 1);
		
	}
	
	@Test
	public void testKnownCiphertext(){
		int key1 = decryption.knownCiphertextAttack(encr1, distr);
		Assert.assertEquals(2, key1);
		
		int key2 = decryption.knownCiphertextAttack(encr2, distr);
		Assert.assertEquals(2, key2);
		
		int key3 = decryption.knownCiphertextAttack(encr3, distr2);
		int key4 = decryption.knownCiphertextAttack(encr4, distr2);
		Assert.assertEquals(1, key3);
		Assert.assertEquals(0, key4);
		
		int key5 = decryption.knownCiphertextAttack(encr5, distrGerman);
		//System.out.println(new CaesarCipher(alph, key5).decrypt(encr5));
		Assert.assertEquals(13, key5);
		
		
	}
	
	@Test
	public void testKnownPlaintext(){
		int key1 = decryption.knownPlaintextAttack(encr1, text1, alph4);
		Assert.assertEquals(2, key1);
		
		int key2 = decryption.knownPlaintextAttack(encr2, text2, alph4);
		Assert.assertEquals(2, key2);
			
	}
	
	@Test
	public void testMinIndex(){
		Assert.assertEquals(0, decryption.minIndex(new double[]{0, 0, 0}));
		Assert.assertEquals(1, decryption.minIndex(new double[]{3, 1, 2, 4, 5}));
		Assert.assertEquals(2, decryption.minIndex(new double[]{0.1, 42.4224, -3.14159}));
	}

}
