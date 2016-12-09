package de.tudarmstadt.gdi1.project.ana.mono;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.Dictionary;
import de.tudarmstadt.gdi1.project.alphabet.Distribution;
import de.tudarmstadt.gdi1.project.analysis.ValidateDecryptionOracle;
import de.tudarmstadt.gdi1.project.analysis.monoalphabetic.GeneticAnalysis;
import de.tudarmstadt.gdi1.project.analysis.monoalphabetic.Individual;
import de.tudarmstadt.gdi1.project.analysis.monoalphabetic.MonoalphabeticKnownCiphertextCryptanalysis;
import de.tudarmstadt.gdi1.project.ana.mono.ImplIndividual;
import de.tudarmstadt.gdi1.project.alph.ImplAlphabet;
import de.tudarmstadt.gdi1.project.alph.ImplDistribution;
import de.tudarmstadt.gdi1.project.utils.ImplUtils;
import de.tudarmstadt.gdi1.project.ciph.subst.mono.MonoAlphCiph;
import de.tudarmstadt.gdi1.project.exception.InvalidAlphabetException;

/**
 * Known ciphertext attack against monoalphabetic cipher using a genetic algorithm based on random mutation.
 * @author vamp
 * @versoin FINAL
 * 
 */
public class ImplGeneticAnalysis implements GeneticAnalysis, MonoalphabeticKnownCiphertextCryptanalysis {

	// new instance of utils for shifting alphabet
	private static ImplUtils utils = new ImplUtils();

	// variables for the status report (getState)
	private int nrOfGenerations = 0;
	private int stable = 0;
	private Individual bestCurrentIndividual;
	private String ciphertext;

	
	
	
	
	/* (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.analysis.monoalphabetic.GeneticAnalysis#prepareInitialGeneration(java.lang.String, de.tudarmstadt.gdi1.project.alphabet.Alphabet, de.tudarmstadt.gdi1.project.alphabet.Distribution, int)
	 */
	@Override
	public List<Individual> prepareInitialGeneration(String ciphertext, Alphabet alphabet, Distribution distribution, int populationSize) {
		
		ArrayList<Character> exitAlphabet = new ArrayList<Character>();
		
		ImplDistribution cipherDistribution = new ImplDistribution(alphabet, ciphertext, 1);
		List<String> sorted = distribution.getSorted(1);
		
		// the characters of the exitAlphabet are added according to their frequency in the ciphertext
		// (the most common letter of the 'normal' alphabet, probably e, will correspond the most common letter of the ciphertext)
		for (Iterator<Character> iter = alphabet.iterator(); iter.hasNext(); ){
			try {
				exitAlphabet.add(cipherDistribution.getByRank(1, 1 + sorted.indexOf(iter.next().toString())).toCharArray()[0]);
			} catch (IndexOutOfBoundsException e) {}
		}
		
		// all missing characters are added 
		for (Iterator<Character> iter = alphabet.iterator(); iter.hasNext(); ){
			char c = iter.next();
			if (! exitAlphabet.contains(c)){
				exitAlphabet.add(c);
			}
		}
		
		// new individuals are created with their alphabets shifted 1 position each
		List<Individual> initialGeneration = new ArrayList<Individual>();
		for (int i = 0; i < populationSize; i++){
			initialGeneration.add(new ImplIndividual(utils.shiftAlphabet(new ImplAlphabet(exitAlphabet), i)));
		}
		return initialGeneration;
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.analysis.monoalphabetic.GeneticAnalysis#generateNextGeneration(java.util.List, int, java.util.Random, de.tudarmstadt.gdi1.project.alphabet.Alphabet, de.tudarmstadt.gdi1.project.alphabet.Distribution, de.tudarmstadt.gdi1.project.alphabet.Dictionary)
	 */
	@Override
	public List<Individual> generateNextGeneration(List<Individual> survivors,
			int populationSize, Random random, Alphabet alphabet,
			Distribution distribution, Dictionary dictionary) {
		List<Individual> nextGeneration = new ArrayList<Individual>();
		
		// populationSize many new individuals are generated
		for (int i = 0; i < populationSize; i++){
			Alphabet survivorsAlphabet = survivors.get(i % survivors.size()).getAlphabet();
			
			// on average every third character will be swaped with another random character 
			for (int j = 0; j < survivorsAlphabet.size(); j++){
				if (random.nextInt(3) == 0){
					survivorsAlphabet = swapCharacters(survivorsAlphabet, j, random.nextInt(survivorsAlphabet.size()));
				}
			}
			nextGeneration.add(new ImplIndividual(survivorsAlphabet));
		}
		return nextGeneration;
	}

	
	
	
	/**
	 * @param alphabet alphabet's chars at pos0 and pos1 will be swapped
	 * @param pos0
	 * @param pos1
	 * @return alphabet with swapped chars
	 */
	private Alphabet swapCharacters(Alphabet alphabet, int pos0, int pos1){
		char[] charArray = alphabet.asCharArray();
		char c;
		c = charArray[pos0];
		charArray[pos0] = charArray[pos1];
		charArray[pos1] = c;
		return new ImplAlphabet(charArray);
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.analysis.monoalphabetic.GeneticAnalysis#computeSurvivors(java.lang.String, de.tudarmstadt.gdi1.project.alphabet.Alphabet, java.util.List, de.tudarmstadt.gdi1.project.alphabet.Distribution, de.tudarmstadt.gdi1.project.alphabet.Dictionary, int)
	 */
	@Override
	public List<Individual> computeSurvivors(String ciphertext,
			Alphabet alphabet, List<Individual> generation,
			Distribution distribution, Dictionary dictionary, int nrOfSurvivors) {
		
		// for every individual the fitness is calculated and set
		for (Individual individual: generation){
			individual.setFitness(computeFitness(individual, ciphertext, alphabet, distribution, dictionary));
		}
		
		// the least fittest individuals are removed
		int size = generation.size();
		for (int i = 0; i < size - nrOfSurvivors; i++){
			removeLeastFittest(generation);
		}
		return generation;
	}
	
	
	
	
	/**
	 * @param individuals List of Individuals; the least fittest Individual will be removed.
	 */
	private void removeLeastFittest(List<Individual> individuals){
		Individual leastFittest = individuals.get(0);
		for (Individual individual: individuals){
			if (individual.getFitness() < leastFittest.getFitness())
				leastFittest = individual;
		}
		individuals.remove(leastFittest);
	}

	
	
	
	/* (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.analysis.monoalphabetic.GeneticAnalysis#computeFitness(de.tudarmstadt.gdi1.project.analysis.monoalphabetic.Individual, java.lang.String, de.tudarmstadt.gdi1.project.alphabet.Alphabet, de.tudarmstadt.gdi1.project.alphabet.Distribution, de.tudarmstadt.gdi1.project.alphabet.Dictionary)
	 */
	@Override
	public double computeFitness(Individual individual, String ciphertext, Alphabet alphabet, Distribution distribution, Dictionary dictionary) {
		
		MonoAlphCiph ciph = new MonoAlphCiph(alphabet, individual.getAlphabet());
		String plaintext = ciph.decrypt(ciphertext);
		Distribution cipherDistribution = new ImplDistribution(individual.getAlphabet(), plaintext, 10);
		
		List<List<String>> cipherList = new ArrayList<List<String>>();
		List<List<String>> plainList = new ArrayList<List<String>>();
		
		// collecting n-grams up to 5 letters in reference text and ciphertext 
		for (int i = 0; i < 5; i++){
			cipherList.add(cipherDistribution.getSorted(i+1));
			plainList.add(distribution.getSorted(i+1));
		}
		
		double fitness = 0.0;
		
		// fitness ratings for common n-grams up to 5 letters are calculated 
		for (List<String> list: cipherList){
			for (String ngram: list){	
				if (plainList.get(ngram.length()-1).contains(ngram)){
					double x = (distribution.getFrequency(ngram) - Math.abs(distribution.getFrequency(ngram) - cipherDistribution.getFrequency(ngram)));
					fitness += ngram.length() * (x + Math.abs(x))*20.0;
				}
			}
		}
		
		// collecting additional n-grams in ciphertext
		for (int i = 5; i < 15; i++){
			cipherList.add(cipherDistribution.getSorted(i+1));
		}
		
		// fitness ratings for actual words up to 15 letters are calculated
		for (List<String> list: cipherList){
			for (String ngram: list){	
				if (dictionary.contains(ngram)){
					fitness += ngram.length() * ngram.length() / 10.0;
				}
			}
		}
		return fitness;
	}


	
	
	/*
	 * (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.analysis.monoalphabetic.MonoalphabeticKnownCiphertextCryptanalysis#knownCiphertextAttack(java.lang.String, de.tudarmstadt.gdi1.project.alphabet.Distribution, de.tudarmstadt.gdi1.project.alphabet.Dictionary, de.tudarmstadt.gdi1.project.analysis.ValidateDecryptionOracle)
	 */
	@Override
	public char[] knownCiphertextAttack(String ciphertext,
			Distribution distribution, Dictionary dictionary,
			ValidateDecryptionOracle validateDecryptionOracle) {
		
		//Initialize  variables for status report
		this.ciphertext = ciphertext;
		nrOfGenerations = 0;
		stable = 0;
		
		//Initiallize the first generation
		Alphabet alphabet = distribution.getAlphabet();
		List<Individual> generation = prepareInitialGeneration(ciphertext, alphabet, distribution, 10);
		
		List<Individual> survivors;
		
		bestCurrentIndividual = generation.get(0);
		MonoAlphCiph ciph = new MonoAlphCiph(alphabet, bestCurrentIndividual.getAlphabet());
		
		
		//computes new generations until a solution is found
		while (! validateDecryptionOracle.isCorrect(ciph.decrypt(ciphertext))){

			nrOfGenerations += 1;
			stable += 1;
			
			// computes survivors
			survivors = computeSurvivors(ciphertext, alphabet, generation, distribution, dictionary, 3);
			
			//looks for a the new best individual
			bestCurrentIndividual = getBest(survivors, bestCurrentIndividual);			
			if (stable == 100) {
				// brute force attempt
				bestCurrentIndividual = bruteForce(bestCurrentIndividual, ciphertext, alphabet, distribution, dictionary);
				survivors.add(bestCurrentIndividual);
				stable = 0;
			}			
			ciph = new MonoAlphCiph(alphabet, bestCurrentIndividual.getAlphabet());
			
			//generates a new geration
			generation = generateNextGeneration(survivors, 10, new Random(), alphabet, distribution, dictionary);
		}
		
		return bestCurrentIndividual.getAlphabet().asCharArray();
	}

	
	
	
	
	
	
	/**
	 * evaluates the currently best individual 
	 * @param individuals a list of individuals (typically the current generation)
	 * @param currentFittest an extra individual to compare with
	 * @return the best individual of all
	 */
	private Individual getBest(List<Individual> individuals, Individual currentFittest){
		Individual fittest = currentFittest;
		for (Individual individual: individuals){
			if (individual.getFitness() > fittest.getFitness()) {
				stable = 0;
				fittest = individual;
			}
		}
		return fittest;
	}
	
	
	
	
	
	/**
	 * brute force attack on a given individual, tries all possible swaps of characters in the individual's alphabet and returns the fittest one  
	 * @param individual an individual
	 * @param ciphertext the ciphertext
	 * @param alph the underlying alphabet
	 * @param distribution the typical distribution of the language
	 * @param dictionary the dictionary of the language
	 * @return the fittest individual 
	 */
	private Individual bruteForce(Individual individual, String ciphertext, Alphabet alph, Distribution distribution, Dictionary dictionary){
		Alphabet bestAlphabet = individual.getAlphabet();
		
		Individual bestIndividual = individual;
		
		for (int i = 0; i < bestAlphabet.size() - 1; i++){
			for (int j = i+1; j < bestAlphabet.size(); j++){
				Individual paul = new ImplIndividual(swapCharacters(bestAlphabet, i, j));
				paul.setFitness(computeFitness(paul, ciphertext, alph, distribution, dictionary));
				if (paul.getFitness() > bestIndividual.getFitness()){
					bestIndividual = paul;
				}
			}
		}
		return bestIndividual;
	}
	
	
	
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.analysis.monoalphabetic.MonoalphabeticKnownCiphertextCryptanalysis#getState(de.tudarmstadt.gdi1.project.alphabet.Alphabet, de.tudarmstadt.gdi1.project.alphabet.Alphabet)
	 */
	@Override
	public String getState(Alphabet sourceAlphabet, Alphabet targetKey) {
		
		if(sourceAlphabet.size() != targetKey.size()){
			throw new InvalidAlphabetException("The length of the two alphabets does not match");
		}
		
		StringBuilder sb = new StringBuilder();
		if (bestCurrentIndividual != null){
			
			// counter for correct characters
			int correct = 0;
			for (int i = 0; i < sourceAlphabet.size(); i++){
				if (targetKey.getChar(i) == bestCurrentIndividual.getAlphabet().getChar(i)){
					correct += 1;
				}
			}
			// state output, contains source alphabet, target alphabet, best current alphabet, current number of generations, current best fitness, current correct letters and the decryption of the ciphertext with the current key 
			sb.append("source: ").append(sourceAlphabet.asCharArray()).append("\ntarget: ").append(targetKey.asCharArray()).append("\ncurkey: ").append(bestCurrentIndividual.getAlphabet().asCharArray()).append("\ngenerations: ").append(nrOfGenerations).append("\nfitness: ").append(bestCurrentIndividual.getFitness()).append("\ncorrect: ").append(correct).append("\ndecrypted text: ").append(new MonoAlphCiph(sourceAlphabet, bestCurrentIndividual.getAlphabet()).decrypt(ciphertext)).append("\n");
			return sb.toString();
		}
		return "bestCurrentIndividual not initialized.";
	}
	
	
	
	
	
	

	/**
	 * Not usable in this implementation.
	 * @see de.tudarmstadt.gdi1.project.analysis.KnownCiphertextAnalysis#knownCiphertextAttack(java.lang.String, de.tudarmstadt.gdi1.project.alphabet.Distribution)
	 */
	@Override
	public Object knownCiphertextAttack(String ciphertext,
			Distribution distribution) {
		return null;
	}
	
	/**
	 * Not usable in this implementation.
	 * @see de.tudarmstadt.gdi1.project.analysis.KnownCiphertextAnalysis#knownCiphertextAttack(java.lang.String, de.tudarmstadt.gdi1.project.alphabet.Dictionary)
	 */
	@Override
	public Object knownCiphertextAttack(String ciphertext, Dictionary dictionary) {
		return null;
	}

	/**
	 * Not usable in this implementation.
	 * @see de.tudarmstadt.gdi1.project.analysis.monoalphabetic.MonoalphabeticKnownCiphertextCryptanalysis#knownCiphertextAttack(java.lang.String, de.tudarmstadt.gdi1.project.alphabet.Distribution, de.tudarmstadt.gdi1.project.alphabet.Dictionary)
	 */
	@Override
	public char[] knownCiphertextAttack(String ciphertext,
			Distribution distribution, Dictionary dictionary) {
		return null;
	}

}
