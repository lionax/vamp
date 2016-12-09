package de.tudarmstadt.gdi1.project.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import de.tudarmstadt.gdi1.project.alph.ImplAlphabet;
import de.tudarmstadt.gdi1.project.alphabet.Alphabet;

/**
 * A set of utilities for special tasks
 * @author vamp
 * @version FINAL
 */
public class ImplUtils implements Utils {

	@Override
	public String toDisplay(String ciphertext) {
		StringBuilder sb = new StringBuilder(ciphertext);
		int counter = 0;
		for(int i = 1; i <= ciphertext.length(); i++){
			if(i % 60 == 0){
				sb.insert(i + counter, System.lineSeparator());
				counter += 2;
			}
			else{
				if(i % 10 == 0){
					sb.insert(i + counter, " ");
					counter += 1;
				}
			}
		}
		return sb.toString();
	}

	@Override
	public Map<Integer, List<String>> ngramize(String text, int... lengths) {
		HashMap<Integer, List<String>> res = new HashMap<Integer, List<String>>();
		ArrayList<String> ngram;
		
		for(int len: lengths) {
			ngram = new ArrayList<String>();
			for(int i = 0; i <= (text.length()-len); i++) {
				ngram.add(text.substring(i, (i+len)));
			}
			res.put(len, ngram);
		}
		
		return res;
	}

	@Override
	public Alphabet shiftAlphabet(Alphabet alphabet, int shift) {
		char[] oldChars = alphabet.asCharArray();
		char[] shiftedChars = new char[alphabet.size()];
		
		shift = shift % alphabet.size();
		 
		if (shift < 0) {
			shift = alphabet.size() + shift;
		}
		
		int j;
		for(int i = 0; i < alphabet.size(); i++) {
			
			j = i - shift;
			if(j < 0) {
				j += alphabet.size();
			}
			
			shiftedChars[j] = oldChars[i];
		}

		return new ImplAlphabet(shiftedChars);
	}

	@Override
	public Alphabet reverseAlphabet(Alphabet alphabet) {
		char[] oldChars = alphabet.asCharArray();
		char[] resChars = new char[alphabet.size()];
		
		for(int i = 0; i < alphabet.size(); i++) {
			resChars[(alphabet.size()-1)-i] = oldChars[i];
		}
		
		return new ImplAlphabet(resChars);
	}

	@Override
	public boolean containsSameCharacters(Alphabet alphabet1, Alphabet alphabet2) {
		String alph2AsString = ((ImplAlphabet) alphabet2).toString();
		String alph1AsString = ((ImplAlphabet) alphabet1).toString();
		// Check weather alph2 contains all elements of alph1
		String check1 = alphabet1.normalize(alph2AsString);
		String check2 = alphabet2.normalize(alph1AsString);
		
		boolean res = (alph2AsString.equals(check1) && alph1AsString.equals(check2));
		
		return res;
	}

	@Override
	public Alphabet randomizeAlphabet(Alphabet alphabet) {
		SecureRandom generator = new SecureRandom();
		char[] resChars = new char[alphabet.size()];
		ArrayList<Character> tempList = new ArrayList<Character>();
		char[] alphabetChars = alphabet.asCharArray();
		int randomValue;
		
		for(char chr: alphabetChars) {
			tempList.add(chr);
		}
		
		for(int i = 0; i < alphabet.size(); i++) {
			randomValue = generator.nextInt(tempList.size());
			resChars[i] = tempList.get(randomValue);
			tempList.remove(randomValue);
		}
		
		return new ImplAlphabet(resChars);
	}
	
	/**
	 * Kills all doubles that occur in a String. Only first occurrence is kept.
	 * @param word is the string from which the doubles get killed
	 * @return the cleaned up string without doubles
	 */
	public String killDoubles(String word) {
		StringBuilder sb = new StringBuilder();
		// the LinkedHashSet keeps order of added elements
		// also has the trait that no doubles are granted in this type of set
		// so conversation into this set already kills all doubles
		LinkedHashSet<Character> tempChars = new LinkedHashSet<Character>();	
		
		if (word == null) {
			return null;
		}
		
		for(int i = 0; i < word.length(); i++) {
			tempChars.add(word.charAt(i));
		}
		
		for(Character chr: tempChars) {
			sb.append(chr.toString());
		}
		
		return sb.toString();
	}
	
	/**
	 * Combines a key value and an alphabet in that way, that the keyword get's combined with the reverse of
	 * an alphabet and the result get's shortened of doubles.
	 * @param a is the appended alphabet
	 * @param keyword the specific keyword
	 * @return a alphabet with the above mentioned properties
	 */
	public Alphabet buildAlphabetWithKey(Alphabet a, String keyword) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(killDoubles(keyword));
		sb.append(reverseAlphabet(a).asCharArray());
		
		return new ImplAlphabet(killDoubles(sb.toString()).toCharArray());
	}
}