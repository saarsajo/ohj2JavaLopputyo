package testiJaTarkistus;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import testiJaTarkistus.test.alkoTarkastusTest;

/**
 * Testit olutkantaohjelmalle
 * @author Santeri Saarinen
 * @version 7.5.2019
 */
@RunWith(Suite.class)
@SuiteClasses({
    alkoTarkastusTest.class,
    kanta.test.OluttyyppiTest.class,
    kanta.test.OluttyypitTest.class,
    kanta.test.OlutTest.class,
    kanta.test.OluetTest.class,
    kanta.test.OlutkantaTest.class
    })
public class AllTests {
 //
}
