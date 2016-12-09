package de.tudarmstadt.gdi1.project.alph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.Dictionary;

/**
 * Represents a dictionary for cryptanalysis
 * @author vamp
 * @version 28.02.2014
 *
 */
public class ImplDictionary implements Dictionary {

	private List<String> dict;
	private Alphabet alphabet;
	
	/**
	 * Constructs a Dictionary of a given text and an alphabet
	 * @param alph with valid characters of the text
	 * @param text to build the dict from
	 */
	public ImplDictionary(Alphabet alph, String text) {
		Pattern regex = Pattern.compile("[\\s,!?.]");
		dict = new ArrayList<String>();
		alphabet = alph;
		String[] words;
		
		words = regex.split(text);
		
		for(String word: words) {
			if(alphabet.allows(word) && !dict.contains(word) && !word.isEmpty()) {
				dict.add(alph.normalize(word));
			}
		}
		
		Collections.sort(dict);
	}
	
	@Override
	public Iterator<String> iterator() {
		
		return dict.iterator();
	}

	@Override
	public boolean contains(String word) {

		return dict.contains(word);
	}

	@Override
	public Alphabet getAlphabet() {
		
		return alphabet;
	}

	@Override
	public int size() {
		
		return dict.size();
	}

	@Override
	public String get(int index) {
		
		return dict.get(index);
	}

}
