package kanta.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import kanta.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2019.05.21 05:41:37 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class OluttyyppiTest {



  // Generated by ComTest BEGIN
  /** testGetTyyppi84 */
  @Test
  public void testGetTyyppi84() {    // Oluttyyppi: 84
    Oluttyyppi lager = new Oluttyyppi(); 
    lager.vastaaLager(); 
    { String _l_=lager.getTyyppi(),_r_="Lager .*"; if ( !_l_.matches(_r_) ) fail("From: Oluttyyppi line: 87" + " does not match: ["+ _l_ + "] != [" + _r_ + "]");}; 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAseta116 */
  @Test
  public void testAseta116() {    // Oluttyyppi: 116
    Oluttyyppi oluttyyppi = new Oluttyyppi(); 
    assertEquals("From: Oluttyyppi line: 118", 1, oluttyyppi.aseta(0,"1")); 
    assertEquals("From: Oluttyyppi line: 119", 50, oluttyyppi.aseta(1,"50")); 
    assertEquals("From: Oluttyyppi line: 120", null, oluttyyppi.aseta(1,"FAasfsa")); 
    assertEquals("From: Oluttyyppi line: 121", 4, oluttyyppi.aseta(2,"4")); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testRekisteroi186 */
  @Test
  public void testRekisteroi186() {    // Oluttyyppi: 186
    Oluttyyppi Lager = new Oluttyyppi(); 
    assertEquals("From: Oluttyyppi line: 188", 0, Lager.getTyyppiID()); 
    Lager.rekisteroi(); 
    Oluttyyppi Ale = new Oluttyyppi(); 
    Ale.rekisteroi(); 
    int n1 = Lager.getTyyppiID(); 
    int n2 = Ale.getTyyppiID(); 
    assertEquals("From: Oluttyyppi line: 194", n2-1, n1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testParse237 */
  @Test
  public void testParse237() {    // Oluttyyppi: 237
    Oluttyyppi oluttyyppi = new Oluttyyppi(); 
    oluttyyppi.parse(" 001   |  5   |  Lager "); 
    assertEquals("From: Oluttyyppi line: 240", 5, oluttyyppi.getTyyppiID()); 
    assertEquals("From: Oluttyyppi line: 241", "001|5|Lager", oluttyyppi.toString()); 
    oluttyyppi.rekisteroi(); 
    oluttyyppi.parse(""+(5)); 
    oluttyyppi.rekisteroi(); 
    assertEquals("From: Oluttyyppi line: 246", 5, oluttyyppi.getTyyppiID()); 
    assertEquals("From: Oluttyyppi line: 247", "" + (5) + "|001|5|Lager|", oluttyyppi.toString()); 
  } // Generated by ComTest END
}