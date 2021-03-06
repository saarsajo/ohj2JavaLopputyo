package kanta.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import kanta.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2019.05.21 04:41:59 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class OtsikotTest {



  // Generated by ComTest BEGIN
  /** 
   * testLisaa37 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa37() throws SailoException {    // Otsikot: 37
    Otsikot otsikot = new Otsikot(); 
    Otsikko koff = new Otsikko(), karhu = new Otsikko(); 
    assertEquals("From: Otsikot line: 41", 0, otsikot.getLkm()); 
    otsikot.lisaa(koff); assertEquals("From: Otsikot line: 42", 1, otsikot.getLkm()); 
    otsikot.lisaa(karhu); assertEquals("From: Otsikot line: 43", 2, otsikot.getLkm()); 
    otsikot.lisaa(koff); assertEquals("From: Otsikot line: 44", 3, otsikot.getLkm()); 
    assertEquals("From: Otsikot line: 45", koff, otsikot.anna(0)); 
    assertEquals("From: Otsikot line: 46", karhu, otsikot.anna(1)); 
    assertEquals("From: Otsikot line: 47", koff, otsikot.anna(2)); 
    try {
    assertEquals("From: Otsikot line: 48", koff, otsikot.anna(3)); 
    fail("Otsikot: 48 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    otsikot.lisaa(koff); assertEquals("From: Otsikot line: 49", 4, otsikot.getLkm()); 
    otsikot.lisaa(koff); assertEquals("From: Otsikot line: 50", 5, otsikot.getLkm()); 
    try {
    otsikot.lisaa(koff); 
    fail("Otsikot: 51 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END
}