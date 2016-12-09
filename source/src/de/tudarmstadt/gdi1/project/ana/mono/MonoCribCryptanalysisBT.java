package de.tudarmstadt.gdi1.project.ana.mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import de.tudarmstadt.gdi1.project.alph.ImplAlphabet;
import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.Dictionary;
import de.tudarmstadt.gdi1.project.alphabet.Distribution;
import de.tudarmstadt.gdi1.project.analysis.ValidateDecryptionOracle;
import de.tudarmstadt.gdi1.project.analysis.monoalphabetic.BacktrackingAnalysis;
import de.tudarmstadt.gdi1.project.analysis.monoalphabetic.MonoalphabeticCribCryptanalysis;
import de.tudarmstadt.gdi1.project.ciph.subst.mono.MonoAlphCiph;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.MonoalphabeticCipher;
import de.tudarmstadt.gdi1.project.exception.*;
import de.tudarmstadt.gdi1.project.utils.*;

/**
 * Analysis of monoalphabetical encryption with backtracking and cribs
 * More information on Cribs: {@link https://de.wikipedia.org/wiki/Crib}
 * More information on Backtracking: {@link https://de.wikipedia.org/wiki/Backtracking}
 * @author Alexander Wellbrock - 2319951
 * @version 28.02.2014
 */
public class MonoCribCryptanalysisBT implements
		MonoalphabeticCribCryptanalysis, BacktrackingAnalysis {

	private Map<Character, Character> currentSolution;
	private String currSolAsString = "";
	private String pathAsString = "";
	private MonoalphabeticCipher monoCipher;
	private Alphabet destination;
	private Integer iterations;
	private Integer btDepth;
	private ImplUtils utils;
	private Collection<Character> path;
	
	/**
	 * Class Constructor
	 */
	public MonoCribCryptanalysisBT() {
		utils = new ImplUtils();
		currentSolution = new LinkedHashMap<Character, Character>();
		path = new ArrayList<Character>();
	}
	
	@Override
	public Map<Character, Character> reconstructKey(
			Map<Character, Character> key, String ciphertext,
			Alphabet alphabet, Distribution distribution,
			Dictionary dictionary, List<String> cribs,
			ValidateDecryptionOracle validateDecryptionOracle) {
		
		// declare variables for this instance
		char nextChar;
		Collection<Character> potentialAssignments;
		Alphabet currentDestination;
		Alphabet currentSource;
		
		// Initialize variables for this instance
		iterations++;
		currentSource = new ImplAlphabet(key.keySet());
		currentDestination = new ImplAlphabet(key.values());
		currSolAsString = key.values().toString();
		pathAsString = path.toString();
		
		// if the key is correct return it and stop backtracking
		if(utils.containsSameCharacters(destination, currentDestination)) {
			monoCipher = new MonoAlphCiph(currentSource, currentDestination);
			if(validateDecryptionOracle.isCorrect(monoCipher.decrypt(ciphertext))) {
				return key;
			}
			else {
				// if the key is not valid throw a exception
				throw new InvalidKeyException("Computed key is not correct!");
			}
		}
		
		// compute next char and potential assignments for this instance
		nextChar = getNextSourceChar(key, alphabet, distribution, dictionary, cribs);
		potentialAssignments = getPotentialAssignments(nextChar, key, ciphertext, alphabet, distribution, dictionary);
		
		// for each potential assignment try to reconstruct the key
		for(char chr: potentialAssignments) {
			try {
				key.put(nextChar, chr);		// put the key for next instance
				path.add(nextChar);			// add the current char to the path for getState()
				btDepth++;					// increase the current depth for getState()
				return reconstructKey(key, ciphertext, alphabet, distribution, dictionary, cribs, validateDecryptionOracle);
			} catch(InvalidKeyException e) {
				key.remove(nextChar);
				path.remove(nextChar);
				btDepth--;
			} catch(AlphabetCharMappingException e) {
				
			}
		}
		
		// Throw this exception if all potential assignments returned an exception
		// try next valid character in an earlier instance
		throw new InvalidKeyException("Computed key is not correct!");
	}

	@Override
	public Collection<Character> getPotentialAssignments(
			Character targetCharacter, Map<Character, Character> key,
			String ciphertext, Alphabet alphabet, Distribution distribution,
			Dictionary dictionary) {
		
		ArrayList<Character> resList = new ArrayList<Character>();
		
		for(char entry: alphabet.asCharArray()) {
			if(!key.containsValue(entry)) {
				resList.add(entry);
			}
		}
		
		return resList;
	}

	@Override
	public Character getNextSourceChar(Map<Character, Character> key,
			Alphabet alphabet, Distribution distribution,
			Dictionary dictionary, List<String> cribs) {
		
		for(char entry: alphabet.asCharArray()) {
			if(!key.containsKey(entry)) {
				return entry;
			}
		}
		
		throw new AlphabetCharMappingException("None unmapped source char left.");
	}

	@Override
	public boolean isPromisingPath(Alphabet alphabet, String ciphertext,
			Map<Character, Character> key, Distribution distribution,
			Dictionary dictionary, Collection<String> cribs) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public char[] knownCiphertextAttack(String ciphertext,
			Distribution distribution, Dictionary dictionary, List<String> cribs) {

		return knownCiphertextAttack(ciphertext, distribution, dictionary, cribs);
	}

	@Override
	public char[] knownCiphertextAttack(String ciphertext,
			Distribution distribution, Dictionary dictionary,
			List<String> cribs,
			ValidateDecryptionOracle validateDecryptionOracle) {

		char[] resAsArray;
		Map<Character, Character> resAsMap;
		
		destination = dictionary.getAlphabet();
		currentSolution.clear();
		iterations = 0;
		btDepth = 0;
		path.clear();
		
		resAsMap = reconstructKey(currentSolution, ciphertext, distribution.getAlphabet(), 
						distribution, dictionary, cribs, validateDecryptionOracle);
		
		int i = 0;
		resAsArray = new char[resAsMap.size()];
		for(char chr: resAsMap.values()) {
			resAsArray[i] = (char) chr;
			i++;
		}
		
		return resAsArray;
	}

	@Override
	public String getState(Alphabet sourceAlphabet, Alphabet targetKey) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(((ImplAlphabet) sourceAlphabet).toString());
		sb.append(" - source alphabet \n");
		sb.append(((ImplAlphabet) targetKey).toString());
		sb.append(" - target key \n");
		sb.append(currSolAsString);
		sb.append(" - current solution \n");
		sb.append(pathAsString);
		sb.append(" - path \n");
		sb.append("iterations: ");
		sb.append(iterations);
		sb.append("\n bt depth: ");
		sb.append(btDepth);
		sb.append("\n");
		
		return sb.toString();
	}

}
