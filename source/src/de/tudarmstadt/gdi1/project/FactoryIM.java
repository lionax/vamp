package de.tudarmstadt.gdi1.project;

import java.util.Collection;
import java.util.List;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.Dictionary;
import de.tudarmstadt.gdi1.project.alphabet.Distribution;
import de.tudarmstadt.gdi1.project.ana.ImplValDecrOracle;
import de.tudarmstadt.gdi1.project.ana.mono.ImplIndividual;
import de.tudarmstadt.gdi1.project.ana.mono.MonoCpaNpaCryptanalysis;
import de.tudarmstadt.gdi1.project.ana.caesar.CaesarCryptAna;
import de.tudarmstadt.gdi1.project.ana.vigenere.ImplVigenereCryptAnalysis;
import de.tudarmstadt.gdi1.project.analysis.ValidateDecryptionOracle;
import de.tudarmstadt.gdi1.project.analysis.caeser.CaesarCryptanalysis;
import de.tudarmstadt.gdi1.project.analysis.monoalphabetic.Individual;
import de.tudarmstadt.gdi1.project.analysis.monoalphabetic.MonoalphabeticCpaNpaCryptanalysis;
import de.tudarmstadt.gdi1.project.analysis.monoalphabetic.MonoalphabeticCribCryptanalysis;
import de.tudarmstadt.gdi1.project.analysis.monoalphabetic.MonoalphabeticKnownCiphertextCryptanalysis;
import de.tudarmstadt.gdi1.project.analysis.vigenere.VigenereCryptanalysis;
import de.tudarmstadt.gdi1.project.ciph.subst.AbstrSubsCipher;
import de.tudarmstadt.gdi1.project.ciph.subst.mono.CaesarCipher;
import de.tudarmstadt.gdi1.project.ciph.subst.mono.KeyMonoCiph;
import de.tudarmstadt.gdi1.project.ciph.subst.mono.MonoAlphCiph;
import de.tudarmstadt.gdi1.project.ciph.subst.poly.ImplPolyalphabeticCipher;
import de.tudarmstadt.gdi1.project.ciph.subst.poly.ImplVigenere;
import de.tudarmstadt.gdi1.project.cipher.enigma.Enigma;
import de.tudarmstadt.gdi1.project.cipher.enigma.PinBoard;
import de.tudarmstadt.gdi1.project.cipher.enigma.ReverseRotor;
import de.tudarmstadt.gdi1.project.cipher.enigma.Rotor;
import de.tudarmstadt.gdi1.project.cipher.substitution.SubstitutionCipher;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.Caesar;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.KeywordMonoalphabeticCipher;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.MonoalphabeticCipher;
import de.tudarmstadt.gdi1.project.cipher.substitution.polyalphabetic.PolyalphabeticCipher;
import de.tudarmstadt.gdi1.project.cipher.substitution.polyalphabetic.Vigenere;
import de.tudarmstadt.gdi1.project.utils.ImplUtils;
import de.tudarmstadt.gdi1.project.utils.Utils;
import de.tudarmstadt.gdi1.project.alph.*;
import de.tudarmstadt.gdi1.project.ciph.enig.*;
import de.tudarmstadt.gdi1.project.ana.mono.MonoCribCryptanalysisBT;
import de.tudarmstadt.gdi1.project.ana.mono.ImplGeneticAnalysis;

/**
 * 
 * @author vamp
 * @version FINAL
 */
public class FactoryIM implements Factory {

	@Override
	public Distribution getDistributionInstance(Alphabet source, String text,
			int ngramsize) {
		return new ImplDistribution(source, text, ngramsize);
	}

	@Override
	public Alphabet getAlphabetInstance(Collection<Character> characters) {
		return new ImplAlphabet(characters);
	}

	@Override
	public Dictionary getDictionaryInstance(Alphabet alphabet, String text) {
		return new ImplDictionary(alphabet, text);
	}

	@Override
	public MonoalphabeticCipher getMonoalphabeticCipherInstance(
			Alphabet source, Alphabet dest) {
		return new MonoAlphCiph(source, dest);
	}

	@Override
	public Caesar getCaesarInstance(int key, Alphabet alphabet) {
		return new CaesarCipher(alphabet, key);
	}

	@Override
	public KeywordMonoalphabeticCipher getKeywordMonoalphabeticCipherInstance(
			String key, Alphabet alphabet) {
		return new KeyMonoCiph(alphabet, key);
	}

	@Override
	public PolyalphabeticCipher getPolyalphabeticCipherInstance(
			Alphabet source, Alphabet... dest) {
		return new ImplPolyalphabeticCipher(source, dest);
	}

	@Override
	public Vigenere getVigenereCipherInstance(String key, Alphabet alphabet) {
		return new ImplVigenere(key, alphabet);
	}

	@Override
	public CaesarCryptanalysis getCaesarCryptanalysisInstance() {
		return new CaesarCryptAna();
	}

	@Override
	public MonoalphabeticCpaNpaCryptanalysis getMonoalphabeticCpaNpaCryptanalysis() {
		return new MonoCpaNpaCryptanalysis();
	}

	@Override
	public MonoalphabeticCribCryptanalysis getMonoalphabeticCribCryptanalysisInstance() {
		return new MonoCribCryptanalysisBT();
	}

	@Override
	public MonoalphabeticKnownCiphertextCryptanalysis getMonoalphabeticKnownCiphertextCryptanalysisInstance() {
		return new ImplGeneticAnalysis();
	}

	@Override
	public VigenereCryptanalysis getVigenereCryptanalysisInstance() {
		return new ImplVigenereCryptAnalysis();
	}

	@Override
	public Utils getUtilsInstance() {
		return new ImplUtils();
	}

	@Override
	public Enigma getEnigmaInstance(List<Rotor> rotors, PinBoard pinboard,
			ReverseRotor reverseRotor) {
		return new ImplEnigma(rotors, pinboard, reverseRotor);
	}

	@Override
	public PinBoard getPinBoardInstance(Alphabet source, Alphabet destination) {
		return new ImplPinBoard(source, destination);
	}

	@Override
	public Rotor getRotorInstance(Alphabet entryAlph, Alphabet exitAlph,
			int startPosition) {
		return new ImplRotor(entryAlph, exitAlph, startPosition);
	}

	@Override
	public ReverseRotor getReverseRotatorInstance(Alphabet entryAlph,
			Alphabet exitAlph) {
		return new ImplReverseRotor(entryAlph, exitAlph);
	}

	@Override
	public Class<? extends SubstitutionCipher> getAbstractSubstitutionCipherClass() {
		return AbstrSubsCipher.class;
	}

	@Override
	public ValidateDecryptionOracle getValidateDecryptionOracle(
			Distribution distribution, Dictionary dictionary) {
		return new ImplValDecrOracle(distribution, dictionary);
	}

	@Override
	public Individual getIndividualInstance(Alphabet alphabet, double fitness) {
		return new ImplIndividual(alphabet, fitness);
	}

}
