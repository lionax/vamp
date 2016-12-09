/**
 * 
 */
package de.tudarmstadt.gdi1.project.ana.vigenere;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.tudarmstadt.gdi1.project.alph.ImplDistribution;
import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.Dictionary;
import de.tudarmstadt.gdi1.project.alphabet.Distribution;
import de.tudarmstadt.gdi1.project.analysis.vigenere.VigenereCryptanalysis;
import de.tudarmstadt.gdi1.project.ciph.subst.poly.ImplVigenere;
import de.tudarmstadt.gdi1.project.exception.InvalidAlphabetException;
import de.tudarmstadt.gdi1.project.utils.ImplUtils;

/**
 * @author vamp
 * @version FINAL
 */
public class ImplVigenereCryptAnalysis implements VigenereCryptanalysis {

private static ImplUtils util = new ImplUtils();

	
	/**
	 * gets all dividers of one number
	 * @param i an integer
	 * @return a sorted list (ascending) of all divisors of the number
	 */
	public ArrayList<Integer> getDivider(int i){
		ArrayList<Integer> array = new ArrayList<Integer>();
		
		for (int j = 1; j <= i ; j++){
			if (i % j == 0) {
				array.add(j);
			}
		}
		
		return array;
	}
	
	
	
	
	/**
	 * evaluates the greatest common divisor of a list of integers
	 * @param array an arraylist of integers
	 * @return the gcd
	 */
	public int ggT(ArrayList<Integer> array){
		if (array.size() < 2){
			if (array.size() == 0){
				throw new IllegalArgumentException("Cannot compute GCD of empty array");
			}
			return array.get(0);
		}
		int twoResult = ggTRecur(array.get(0), array.get(1));
		if (array.size() == 2){
			return twoResult;
		} else{
			array.remove(0);
			array.remove(1);
			array.add(twoResult);
			return ggT(array);
		}
	}
	
	
	
	/**
	 * evaluates the greatest common divisor of two (positive) integers
	 * @param a an integer
	 * @param b an integer
	 * @return the gcd of the two numbers
	 */
	public int ggTRecur(int a, int b){
		if (b == 0){
			return a;
		}
		if(a == 0){
			return b;
		}
		if(a > b){
			return ggTRecur(b, a % b);
		}
		else {
			return ggTRecur(a, b % a);
		}
		
	}
	
	
	
	/**
	 * gets all differences of indices of duplicates in the list
	 * if one element occurs more than twice, only the differences to the next one is returned
	 * @param strings a list of strings
	 * @return a list of all differences of indices of duplicates in the list
	 */
	
	public List<Integer> getDuplicateDifferences(List<String> strings){
		ArrayList<Integer> result = new ArrayList<Integer>();
		List<String> copyString = new ArrayList<String>(strings);
		
		for(String s: strings){			
			copyString.remove(0);
			
			//checks, whether this element is still in the list, if so the next index is added to the result
			if(-1 != copyString.indexOf(s)){
				result.add(copyString.indexOf(s) +1 );
			}
		}
		
		return result;
	}
	
	
	/**
	 * returns all duplicates in a text
	 * @param text the text where one wants to look for duplicates
	 * @param minLength the minimal length of the duplicates
	 * @return all distances of duplicates in the text
	 */
	public List<Integer> getAllDistances(String text, int minLength){
		List<Integer> result = new ArrayList<Integer>();
		//an array of all lengths we want to check (contains numbers from minLength to text.length()/2)

		int[] list = new int[text.length()/2 - minLength + 1];
		for(int i = 0; i < list.length; i++){
			list[i] = i + minLength;
		}
		
		//splits the text up into ngrams
		Map<Integer, List<String>> ngramizedText = util.ngramize(text, list);
		
		//gets all duplicates for any ngram size
		for(int i = minLength; i <= text.length() / 2; i++){
			result.addAll(getDuplicateDifferences(ngramizedText.get(i)));
		}
		
		return result;
	}

	/**
	 * Gets all possible key lengths following the kasiski test
	 * @param text
	 * @return
	 */
	public List<Integer> getAllKeyLengths(String text){
		if(getAllDistances(text, 3).size() == 0){
			return getAllDistances(text, 3);
		}
		return getDivider(ggT((ArrayList<Integer>) getAllDistances(text, 3)));
	}
	
	/* (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.analysis.vigenere.VigenereCryptanalysis#knownCiphertextAttack(java.lang.String)
	 */
	@Override
	public List<Integer> knownCiphertextAttack(String ciphertext) {
		return getAllKeyLengths(ciphertext);
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.analysis.vigenere.VigenereCryptanalysis#knownPlaintextAttack(java.lang.String, java.lang.String, de.tudarmstadt.gdi1.project.alphabet.Alphabet)
	 */
	@Override
	public String knownPlaintextAttack(String ciphertext, String plaintext,
			Alphabet alphabet) {
		
		StringBuilder sb = new StringBuilder();
		int alphSize = alphabet.size();
		
		
		if(alphSize == 0){
			throw new InvalidAlphabetException("The alphabet does not contain any characters");
		}	
		if(ciphertext.equals("")){
			throw new IllegalArgumentException("Cannot determine a key to decrypt the empty string");
		}
		if(! alphabet.allows(ciphertext)){
			throw new IllegalArgumentException("The Ciphertext and the alphabet does not match");
		}	
		if( plaintext.length() != ciphertext.length()){
			throw new IllegalArgumentException("Plain- & Ciphertext are of different length");
		}
		
		
		//Cesarlike decryption on every char
		for (int i = 0; i < ciphertext.length(); i++){
			char ithCiph = ciphertext.toCharArray()[i];
			char ithPlain = plaintext.toCharArray()[i];
			sb.append(alphabet.getChar((alphabet.getIndex(ithCiph) - alphabet.getIndex(ithPlain) + alphSize) % alphSize));
		}
		
		List<Integer> keyList = getAllKeyLengths(sb.toString());
		
		//if there are no duplicates in the possible string, return whole string
		if (keyList.size() == 0){
			return sb.toString();
		}
		return sb.toString().substring(0, keyList.get(keyList.size()-1));
	}




	@Override
	public Object knownPlaintextAttack(String ciphertext, String plaintext,
			Distribution distribution) {
		return knownPlaintextAttack(ciphertext, plaintext, distribution.getAlphabet());
	}




	@Override
	public Object knownPlaintextAttack(String ciphertext, String plaintext,
			Distribution distribution, Dictionary dictionary) {
		return knownPlaintextAttack(ciphertext, plaintext, distribution.getAlphabet());
	}




	/* (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.analysis.vigenere.VigenereCryptanalysis#knownCiphertextAttack(java.lang.String, de.tudarmstadt.gdi1.project.alphabet.Distribution, java.util.List)
	 */
	@Override
	public String knownCiphertextAttack(String ciphertext,
			Distribution distribution, List<String> cribs) {
		
		StringBuilder res = new StringBuilder("");
		String cipherString;
		if(distribution == null || distribution.getAlphabet() == null){
			throw new InvalidAlphabetException("A proper distribution with an alphabet which is different from null is required");
		}

		Alphabet alph = distribution.getAlphabet();
		int alphSize = alph.size();
		
		if(alphSize == 0){
			throw new InvalidAlphabetException("The alphabet does not contain any characters");
		}	
		if(ciphertext.equals("")){
			throw new IllegalArgumentException("Cannot determine a key to decrypt the empty string");
		}
		if(! alph.allows(ciphertext)){
			throw new IllegalArgumentException("The Ciphertext and the alphabet does not match");
		}	
			
		
		List<Integer> keyList = getAllKeyLengths(ciphertext);
		
		
		//Procedure has to be done for all possible keylengths
		for (int i = 0; i < keyList.size(); i++){
			String[] stringArray = new String[keyList.get(i)];
			Distribution[] distr = new Distribution[keyList.get(i)];
			cipherString = ciphertext;
			
			//String is divided into substrings from every n-th char to 1st char (reverse order), a distribution for each is computed
			for (int j = 0; j < keyList.get(i); j++){
				stringArray[j] = extract(cipherString, keyList.get(i)-j);
				cipherString = deleteNth(cipherString, keyList.get(i)-j);
				distr[j] = new ImplDistribution(alph, stringArray[j], 1);
			}
			
			StringBuilder sb = new StringBuilder();
			
			//Maps the most frequent char from the alphabet to the most frequent one of each substring, such creating a key. See Cesarchiffre
			for(int k = 0; k < stringArray.length; k++){				
				sb.append(alph.getChar(
							 (alph.getIndex(distr[k].getSorted(1).get(0).toCharArray()[0])
							- alph.getIndex(distribution.getSorted(1).get(0).toCharArray()[0])
							+ alph.size()
							% alph.size())));
			}
			
			//Because of the reversed order of substrings, the key is reversed again
			sb = sb.reverse();
			
			//Using this key, the plaintext is generated
			String plain = new ImplVigenere(sb.toString(), alph).decrypt(ciphertext);
			
			//Plaintext is searched for every string of the criblist
			int cribCounter = 0;
			for (int l = 0; l < cribs.size(); l++){
				if (plain.contains(cribs.get(l))){
					cribCounter += 1;
				}
			}
			//If the potential plaintext contains every Crib, it is considered as the final result
			if (cribCounter == cribs.size()){
				res = sb;
			}
		}
		return res.toString();
	}
		
	/**
	 * deletes every nth character of a given string
	 * @param string given string
	 * @param n	position of characters to delete (every nth)
	 * @return string without nth characters
	 */
	private String deleteNth(String string, int n){
		StringBuilder sb = new StringBuilder();
		char[] charArray = string.toCharArray();
		for (int i = 0; i < string.length(); i++) {
			if ((i + 1) % n != 0){
				sb.append(charArray[i]);
			}
		}
		return sb.toString();
	}
	
	/**
	 * extracts every nth character
	 * @param string to extract from
	 * @param n position of the chars
	 * @return string of the nth chars
	 */
	private String extract(String string, int n){
		StringBuilder sb = new StringBuilder();
		char[] charArray = string.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if ((i+1)  % n == 0){
				sb.append(charArray[i]);
			}
		}
		return sb.toString();
	}
}