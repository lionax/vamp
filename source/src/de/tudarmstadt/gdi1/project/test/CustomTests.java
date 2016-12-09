package de.tudarmstadt.gdi1.project.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.tudarmstadt.gdi1.project.test.alph.*;
import de.tudarmstadt.gdi1.project.test.ana.*;
import de.tudarmstadt.gdi1.project.test.ana.caesar.*;
import de.tudarmstadt.gdi1.project.test.ana.mono.*;
import de.tudarmstadt.gdi1.project.test.ciph.subst.*;
import de.tudarmstadt.gdi1.project.test.subst.mono.*;
import de.tudarmstadt.gdi1.project.test.utils.*;

@RunWith(Suite.class)
@SuiteClasses({
	/* alphabet and co */
	TestImplAlphabet.class,
	ImplDistributionTests.class,
	
	/* ciphers */
	MockTest.class,
	TestMonoAlphCiph.class,
	
	/* analysis */
	TestCaesarCryptAna.class,
	TestValDecrOracle.class,
	TestGeneticAnalysis.class,
	
	/* utils */
	UtilsTests.class

	})
/**
 * calls all custom test cases
 * @author vamp
 * @version FINAL
 */
public class CustomTests {
	

}
