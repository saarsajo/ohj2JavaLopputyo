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
public class OtsikkoTest {



  // Generated by ComTest BEGIN
  /** testParse95 */
  @Test
  public void testParse95() {    // Otsikko: 95
    Otsikko otsikko = new Otsikko(); 
    otsikko.parse("   2   |  Oluttyyppi    "); 
    assertEquals("From: Otsikko line: 98", 10, otsikko.getOtsikkoID()); 
    assertEquals("From: Otsikko line: 99", "2|Oluttyyppi", otsikko.toString()); 
    otsikko.rekisteroi(); 
    int n = otsikko.getOtsikkoID(); 
    otsikko.parse(""+(n+20)); 
    otsikko.rekisteroi(); 
    assertEquals("From: Otsikko line: 105", n+20+1, otsikko.getOtsikkoID()); 
    assertEquals("From: Otsikko line: 106", "" + (n+20+1) + "|Oluttyyppi", otsikko.toString()); 
  } // Generated by ComTest END
}