package de.tudarmstadt.gdi1.project.test.alph;

import de.tudarmstadt.gdi1.project.alph.*;
import de.tudarmstadt.gdi1.project.alphabet.*;
import de.tudarmstadt.gdi1.project.test.*;
import org.junit.*;

/**
 * tests the ImplAlphabet methods
 * @author vamp
 * @version FINAL
 */
public class TestImplAlphabet {
	private ImplAlphabet alph;
	private Alphabet alph2;
	
	@Before
	public void init(){
		alph = new ImplAlphabet( new char[]{'a', 'b', 'd'});
		alph2 = TemplateTestUtils.getDefaultAlphabet();
	}
	
	
	@Test
	public void testRest(){
		Assert.assertEquals(3, alph2.getIndex('d'));
		Assert.assertEquals('d', alph.getChar(2));
		Assert.assertEquals(26, alph2.size());
		
		Assert.assertTrue(alph2.contains('c'));
		Assert.assertFalse(alph.contains('c'));
		
		Assert.assertFalse(alph.allows("abcd"));
		Assert.assertEquals("abd", alph.normalize("abcde"));
		Assert.assertTrue(alph.allows(alph.normalize("abcdefgh")));
		
		Assert.assertEquals("", alph2.normalize("LAUTER GROSSBUCHSTABEN"));
	}
	
	

}
