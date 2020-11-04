package kanta.test;
// Generated by ComTest BEGIN
import java.io.File;
import kanta.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2019.05.21 05:50:27 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class OluttyypitTest {



  // Generated by ComTest BEGIN
  /** 
   * testLisaa52 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa52() throws SailoException {    // Oluttyypit: 52
    Oluttyypit oluttyypit = new Oluttyypit(); 
    Oluttyyppi lager = new Oluttyyppi(), pils = new Oluttyyppi(); 
    assertEquals("From: Oluttyypit line: 56", 0, oluttyypit.getLkm()); 
    oluttyypit.lisaa(lager); assertEquals("From: Oluttyypit line: 57", 1, oluttyypit.getLkm()); 
    oluttyypit.lisaa(pils); assertEquals("From: Oluttyypit line: 58", 2, oluttyypit.getLkm()); 
    oluttyypit.lisaa(lager); assertEquals("From: Oluttyypit line: 59", 3, oluttyypit.getLkm()); 
    Iterator<Oluttyyppi> it = oluttyypit.iterator(); 
    assertEquals("From: Oluttyypit line: 61", lager, it.next()); 
    assertEquals("From: Oluttyypit line: 62", pils, it.next()); 
    assertEquals("From: Oluttyypit line: 63", lager, it.next()); 
    oluttyypit.lisaa(lager); assertEquals("From: Oluttyypit line: 64", 4, oluttyypit.getLkm()); 
    oluttyypit.lisaa(lager); assertEquals("From: Oluttyypit line: 65", 5, oluttyypit.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testPoista83 
   * @throws SailoException when error
   */
  @Test
  public void testPoista83() throws SailoException {    // Oluttyypit: 83
    Oluttyypit oluttyypit = new Oluttyypit(); 
    Oluttyyppi lager = new Oluttyyppi(), stout = new Oluttyyppi(); 
    lager.rekisteroi(); stout.rekisteroi(); 
    int id1 = lager.getTyyppiID(); 
    oluttyypit.lisaa(lager); oluttyypit.lisaa(stout); 
    assertEquals("From: Oluttyypit line: 90", 1, oluttyypit.poista(id1+1)); 
    assertEquals("From: Oluttyypit line: 91", null, oluttyypit.annaId(id1+1)); assertEquals("From: Oluttyypit line: 91", 2, oluttyypit.getLkm()); 
    assertEquals("From: Oluttyypit line: 92", 1, oluttyypit.poista(id1)); assertEquals("From: Oluttyypit line: 92", 1, oluttyypit.getLkm()); 
    assertEquals("From: Oluttyypit line: 93", 0, oluttyypit.poista(id1+3)); assertEquals("From: Oluttyypit line: 93", 1, oluttyypit.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta136 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta136() throws SailoException {    // Oluttyypit: 136
    Oluttyypit oluttyypit = new Oluttyypit(); 
    Oluttyyppi koff = new Oluttyyppi(), karhu = new Oluttyyppi(); 
    koff.vastaaLager(); 
    karhu.vastaaLager(); 
    String hakemisto = "testikelmit"; 
    String tiedNimi = hakemisto+"/tyypit"; 
    File ftied = new File(tiedNimi+".dat"); 
    File dir = new File(hakemisto); 
    dir.mkdir(); 
    ftied.delete(); 
    try {
    oluttyypit.lueTiedostosta(tiedNimi); 
    fail("Oluttyypit: 150 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    oluttyypit.lisaa(koff); 
    oluttyypit.lisaa(karhu); 
    oluttyypit.talleta(); 
    oluttyypit = new Oluttyypit();  // Poistetaan vanhat luomalla uusi
    oluttyypit.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
    Iterator<Oluttyyppi> i = oluttyypit.iterator(); 
    assertEquals("From: Oluttyypit line: 157", koff, i.next()); 
    assertEquals("From: Oluttyypit line: 158", karhu, i.next()); 
    assertEquals("From: Oluttyypit line: 159", false, i.hasNext()); 
    oluttyypit.lisaa(karhu); 
    oluttyypit.talleta(); 
    assertEquals("From: Oluttyypit line: 162", true, ftied.delete()); 
    File fbak = new File(tiedNimi+".bak"); 
    assertEquals("From: Oluttyypit line: 164", true, fbak.delete()); 
    assertEquals("From: Oluttyypit line: 165", true, dir.delete()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testOluttyypitIterator282 
   * @throws SailoException when error
   */
  @Test
  public void testOluttyypitIterator282() throws SailoException {    // Oluttyypit: 282
    Oluttyypit oluttyypit = new Oluttyypit(); 
    Oluttyyppi lager = new Oluttyyppi(), ale = new Oluttyyppi(); 
    lager.rekisteroi(); ale.rekisteroi(); 
    oluttyypit.lisaa(lager); 
    oluttyypit.lisaa(ale); 
    oluttyypit.lisaa(lager); 
    StringBuffer ids = new StringBuffer(30); 
    for (Oluttyyppi oluttyyppi:oluttyypit) // Kokeillaan for-silmukan toimintaa
    ids.append(" "+oluttyyppi.getTyyppiID()); 
    String tulos = " " + lager.getTyyppiID() + " " + ale.getTyyppiID() + " " + lager.getTyyppiID(); 
    assertEquals("From: Oluttyypit line: 301", tulos, ids.toString()); 
    ids = new StringBuffer(30); 
    for (Iterator<Oluttyyppi>  i=oluttyypit.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
    Oluttyyppi oluttyyppi = i.next(); 
    ids.append(" "+oluttyyppi.getTyyppiID()); 
    }
    assertEquals("From: Oluttyypit line: 309", tulos, ids.toString()); 
    Iterator<Oluttyyppi>  i=oluttyypit.iterator(); 
    assertEquals("From: Oluttyypit line: 312", true, i.next() == lager); 
    assertEquals("From: Oluttyypit line: 313", true, i.next() == ale); 
    assertEquals("From: Oluttyypit line: 314", true, i.next() == lager); 
    try {
    i.next(); 
    fail("Oluttyypit: 316 Did not throw NoSuchElementException");
    } catch(NoSuchElementException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testEtsi374 
   * @throws SailoException when error
   */
  @Test
  public void testEtsi374() throws SailoException {    // Oluttyypit: 374
    Oluttyypit oluttyypit = new Oluttyypit(); 
    Oluttyyppi oluttyyppi1 = new Oluttyyppi(); oluttyyppi1.parse("1|1|Lager"); 
    Oluttyyppi oluttyyppi2 = new Oluttyyppi(); oluttyyppi2.parse("2|1|Lager"); 
    Oluttyyppi oluttyyppi3 = new Oluttyyppi(); oluttyyppi3.parse("3|1|Lager"); 
    Oluttyyppi oluttyyppi4 = new Oluttyyppi(); oluttyyppi4.parse("4|1|Lager"); 
    Oluttyyppi oluttyyppi5 = new Oluttyyppi(); oluttyyppi5.parse("5|1|Lager"); 
    oluttyypit.lisaa(oluttyyppi1); oluttyypit.lisaa(oluttyyppi2); oluttyypit.lisaa(oluttyyppi3); oluttyypit.lisaa(oluttyyppi4); oluttyypit.lisaa(oluttyyppi5); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testAnnaId404 
   * @throws SailoException when error
   */
  @Test
  public void testAnnaId404() throws SailoException {    // Oluttyypit: 404
    Oluttyypit lager = new Oluttyypit(); 
    Oluttyyppi koff = new Oluttyyppi(), karjala = new Oluttyyppi(), karhu = new Oluttyyppi(); 
    koff.rekisteroi(); karjala.rekisteroi(); karhu.rekisteroi(); 
    int id1 = koff.getTyyppiID(); 
    lager.lisaa(koff); lager.lisaa(karjala); lager.lisaa(karhu); 
    assertEquals("From: Oluttyypit line: 411", true, lager.annaId(id1  ) == koff); 
    assertEquals("From: Oluttyypit line: 412", true, lager.annaId(id1+1) == karjala); 
    assertEquals("From: Oluttyypit line: 413", true, lager.annaId(id1+2) == karhu); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testEtsiId428 
   * @throws SailoException when error
   */
  @Test
  public void testEtsiId428() throws SailoException {    // Oluttyypit: 428
    Oluttyypit oluttyypit = new Oluttyypit(); 
    Oluttyyppi koff = new Oluttyyppi(), karjala = new Oluttyyppi(), karhu = new Oluttyyppi(); 
    koff.rekisteroi(); karjala.rekisteroi(); karhu.rekisteroi(); 
    int id1 = koff.getTyyppiID(); 
    oluttyypit.lisaa(koff); oluttyypit.lisaa(karjala); oluttyypit.lisaa(karhu); 
    assertEquals("From: Oluttyypit line: 435", 1, oluttyypit.etsiId(id1+1)); 
    assertEquals("From: Oluttyypit line: 436", 2, oluttyypit.etsiId(id1+2)); 
  } // Generated by ComTest END
}