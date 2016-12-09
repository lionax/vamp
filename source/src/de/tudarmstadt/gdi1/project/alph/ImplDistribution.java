package de.tudarmstadt.gdi1.project.alph;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.Distribution;
import de.tudarmstadt.gdi1.project.utils.ImplUtils;

/**
 * represents a distribution
 * @author vamp
 * @version FINAL
 */
public class ImplDistribution implements Distribution {

	private ImplUtils utils = new ImplUtils();
	private Map<String, Integer> distr;
	private double textLength;
	private Alphabet alphabet;
	
	/**
	 * Constructor for the Distribution, splits the text up and counts the occurrences of the Characters
	 * @param source the underlying alphabet
	 * @param text the text
	 * @param ngramsize ?
	 */
	public ImplDistribution(Alphabet source, String text, int ngramsize){
		// kills all characters that are not in the alphabet
		String normalizedText = source.normalize(text);
		int[] ngramSizes = new int[ngramsize];
		HashMap<Integer, List<String>> ngrams;
		ArrayList<String> ngram;
		
		// initialize object wide variables
		textLength = normalizedText.length();
		distr = new HashMap<String, Integer>();
		alphabet = source;
		
		// get ngram sizes from 1 to ngramsize
		for(int i = 1; i <= ngramsize; i++) {
			ngramSizes[i-1] = i;
		}
		
		// get ngrames mapped by ngram length
		ngrams = (HashMap<Integer, List<String>>) utils.ngramize(normalizedText, ngramSizes);
		
		// compute the distribution for each ngram size
		for(int size: ngrams.keySet()) {
			// extract the ngrams as arraylist
			ngram = (ArrayList<String>) ngrams.get(size);
			// compute distribution
			for(String str: ngram) {
				if(distr.containsKey(str)) {
					distr.put(str, distr.get(str) + 1);
				}
				else {
					distr.put(str, 1);
				}
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.alphabet.Distribution#getSorted(int)
	 */
	@Override
	public List<String> getSorted(int length) {
		List<String> res = new ArrayList<String>();
		Map<String, Integer> cleanDistr = new HashMap<String, Integer>();
		List<Map.Entry<String, Integer>> tempDistr;
		
		// filter distr to get only ngrams with correct length
		for(String str: distr.keySet()) {
			if(str.length() == length) {
				cleanDistr.put(str, distr.get(str));
			}
		}
		
		// convert the shortened distr into a list of 'Map.Entry' objects for the next step
		tempDistr = new ArrayList<Map.Entry<String, Integer>>(cleanDistr.entrySet());
		// sort this list of Key-Value-Pairs with the comparator described at the bottom of this file
		Collections.sort(tempDistr, new ByValueInDescendingOrder<String, Integer>());
		
		// convert the list of Map.Entry Objects into a list of type String
		// therefore extract the keys and add them to the list
		for(Map.Entry<String, Integer> entry: tempDistr) {
			res.add(entry.getKey());
		}
		
		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.alphabet.Distribution#getFrequency(java.lang.String)
	 */
	@Override
	public double getFrequency(String key) {
		int keyLength = key.length();
		double result;
		
		if(!distr.containsKey(key)) {
			result = 0;
		}
		else {
			result = distr.get(key) / (textLength - (keyLength - 1));
		}
		return result;
	}

	@Override
	public Alphabet getAlphabet() {
		return alphabet;
	}

	@Override
	public String getByRank(int length, int rank) {
		String res;
		List<String> ngrams = getSorted(length);
		
		if(ngrams.size() < (rank - 1)) {
			res = "";
		}
		else {
			res = ngrams.get(rank - 1);
		}
		
		return res;
	}
	
	/**
	 * This comparator orders a Mapping (converted into a List of 'Map.Entry's) of comparable objects at first by it's values
	 * in descending order, then by it's keys in normal total order.
	 * @author Alex
	 * @version 25.02.2014
	 *
	 * @param <K> The key, that is ordered secondarily 
	 * @param <V> The Value, that is ordered first in descending order
	 */
	public class ByValueInDescendingOrder<K extends Comparable<? super K>, V extends Comparable<? super V>> implements Comparator<Map.Entry<K, V>> {
		
		@Override
		public int compare(Map.Entry<K, V> first, Map.Entry<K, V> second) {
			int res;
			// First compare the values of both entries and sort by descending order
			int compareValue = first.getValue().compareTo(second.getValue());
			
			if(compareValue != 0) {
				// sort by value
				res = -compareValue;
			}
			else {
				// sort by key in normal total order
				res = first.getKey().compareTo(second.getKey());
			}
			
			return res;
		}
	}
}