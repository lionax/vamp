package de.tudarmstadt.gdi1.project.test.custom;

import java.util.Arrays;

import de.tudarmstadt.gdi1.project.Factory;
import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.test.TemplateTestCore;

/**
 * Some utils for the custom test cases
 * @author vamp
 * @version FINAL
 */
public class TestUtils {
	
	private static Factory f = TemplateTestCore.getFactory();
	
	public static String getDefaultKeywordAsStringWithoutDoubles()  {
		return "gdipraktum";
	}
	
	public static String getDefaultKeywordAsString() {
		return "gdipraktikum";
	}
	
	public static Alphabet getDefaultKeywordWithReversedMinimalAlphabet() {
		return f.getAlphabetInstance(Arrays.asList('g', 'd', 'i', 'p', 'r', 'a', 'k', 't', 'u', 'm', 'c', 'b'));
	}
}