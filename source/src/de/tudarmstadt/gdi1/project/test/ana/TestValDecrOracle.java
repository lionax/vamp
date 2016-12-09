package de.tudarmstadt.gdi1.project.test.ana;

import de.tudarmstadt.gdi1.project.ana.*;
import de.tudarmstadt.gdi1.project.analysis.*;
import de.tudarmstadt.gdi1.project.alph.*;
import de.tudarmstadt.gdi1.project.alphabet.Alphabet;

import org.junit.*;

/**
 * tests our own Oracle
 * @author vamp
 * @version FINAL
 */
public class TestValDecrOracle {

	@Test
	public void TestOracle(){
		Alphabet source = new ImplAlphabet(new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r'});
		String plain = "k pofirdrrghoghiqfpqohg ge n mbcmg dcdiq qrekr hrgr ofnbq pnqfmfj mng bqmkgee niqgmidl kioplna qmdjr pkjhlimbmhm bnmldkph ciepmnibnnm nmcb bgbmce opl doiai j b cnidpmomjlnropo m imhro ekdf ffj h llnfaanmc j pekppbg c nibrjkmgl kojbabbdga cm e p jl n gnajkbg p gbhkmrf c e h j nl e bdi hgqqo clcilodr eek aidqqgklaf iod qccllkcfrmoeiniiarmcnag nhhog fdmmpoqekeab cflig r o qkdmdbd balpcifp bfqme mqgoeaqb hfi lgkjhqbarcgrfble ohjdbgbahjqmgq qko jrajdl dc e lhganljfo b lhgfckb jadiad khdfk nqed rkdebk";
		ImplDictionary dict = new ImplDictionary(source, plain);
		ValidateDecryptionOracle OrakelVonDelphi = new ImplValDecrOracle(null, dict);
		
		Assert.assertTrue(OrakelVonDelphi.isCorrect(source.normalize(plain)));
		Assert.assertFalse(OrakelVonDelphi.isCorrect(source.normalize(plain)+ "aaaaaaaa"));
		
	}
}
