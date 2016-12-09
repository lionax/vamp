package de.tudarmstadt.gdi1.project.test.ciph.subst;

import static org.mockito.Matchers.anyChar;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import de.tudarmstadt.gdi1.project.cipher.substitution.SubstitutionCipher;
import de.tudarmstadt.gdi1.project.test.TemplateTestCore;

/**
 * custom mock tests
 * @author vamp
 * @version FINAL
 */
public class MockTest {
		
	@Test
	public void Test1(){

		Class<? extends SubstitutionCipher> clazz = TemplateTestCore.getFactory().getAbstractSubstitutionCipherClass();		
		SubstitutionCipher mockedCipher = mock(clazz);
		
		when(mockedCipher.translate(anyChar(), anyInt())).thenReturn('b').thenReturn('a');

		when(mockedCipher.reverseTranslate('b', 0)).thenReturn('a');
		when(mockedCipher.reverseTranslate('a', 1)).thenReturn('b');
	
		
		Assert.assertEquals("ab", mockedCipher.decrypt(mockedCipher.encrypt("ab")));
	}
	
	@Test
	public void Test2(){

		Class<? extends SubstitutionCipher> clazz = TemplateTestCore.getFactory().getAbstractSubstitutionCipherClass();		
		SubstitutionCipher mockedCipher = mock(clazz);
		
		String s = "Das ist ein String der eine potentielle Nachricht darstellt, die verschluesselt werden soll";
		mockedCipher.encrypt(s);
		
		verify(mockedCipher, times(s.length())).translate(anyChar(), anyInt());
		verify(mockedCipher, times(0)).reverseTranslate(anyChar(), anyInt());
		
		mockedCipher.decrypt(s);
		verify(mockedCipher, times(s.length())).reverseTranslate(anyChar(), anyInt());
		
		
	}
	
	
	
	

}
