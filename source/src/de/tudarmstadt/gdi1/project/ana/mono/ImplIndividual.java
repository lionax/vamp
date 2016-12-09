package de.tudarmstadt.gdi1.project.ana.mono;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.analysis.monoalphabetic.Individual;

/**
 * Implementation of Individual which represents an individual key alphabet built by a genetic algorithm.
 * @author vamp
 * @version FINAL
 * 
 */
public class ImplIndividual implements Individual {

	private Alphabet alphabet;
	private double fitness;
	
	/**
	 * @param alphabet individual's key alphabet
	 * @param fitness individual's fitness rating
	 */
	public ImplIndividual(Alphabet alphabet, double fitness){
		this.alphabet = alphabet;
		this.fitness = fitness;
	}
	
	/**
	 * Constructor without fitness value, which will be set to 0.
	 * @param alphabet individual's key alphabet
	 */
	public ImplIndividual(Alphabet alphabet){
		this(alphabet, 0);
	}
	
	/* (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.analysis.monoalphabetic.Individual#getAlphabet()
	 */
	@Override
	public Alphabet getAlphabet() {
		return alphabet;
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.analysis.monoalphabetic.Individual#getFitness()
	 */
	@Override
	public double getFitness() {
		return fitness;
	}

	/** 
	 * @param fitness fitness double value
	 * @see de.tudarmstadt.gdi1.project.analysis.monoalphabetic.Individual#setFitness(double)
	 */
	@Override
	public void setFitness(double fitness) {
		this.fitness = fitness;
		
	}

}
