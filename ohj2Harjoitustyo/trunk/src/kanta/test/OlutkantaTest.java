package kanta.test;
// Generated by ComTest BEGIN
import kanta.SailoException;
import kanta.*;
// Generated by ComTest END
import java.io.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;

/**
 * Test class made by ComTest
 * @version 2019.05.21 05:33:16 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class OlutkantaTest {


  // Generated by ComTest BEGIN  // Olutkanta: 18
   private Olutkanta olutkanta; 
   private Oluttyyppi lager; 
   private Oluttyyppi pils; 
   private int luku1; 
   private int luku2; 
   private Olut koff; 
   private Olut karjala; 
   private Olut karhu; 


   public void alustaKanta() {
     olutkanta = new Olutkanta(); 
     lager = new Oluttyyppi(); lager.vastaaLager(); lager.rekisteroi(); 
     pils = new Oluttyyppi(); pils.vastaaLager(); pils.rekisteroi(); 
     luku1 = lager.getTyyppiID(); 
     luku2 = pils.getTyyppiID(); 
     koff = new Olut(luku2); koff.vastaaKoff(luku2); 
     karjala = new Olut(luku1); karjala.vastaaKoff(luku1); 
     karhu = new Olut(luku2); karhu.vastaaKoff(luku2); 

     try {
     olutkanta.lisaa(lager); 
     olutkanta.lisaa(pils); 
     olutkanta.lisaa(koff); 
     olutkanta.lisaa(karjala); 
     olutkanta.lisaa(karhu); 
     } catch ( Exception e) {
        System.err.println(e.getMessage()); 
     }
   }
  // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testPoista64 
   * @throws Exception when error
   */
  @Test
  public void testPoista64() throws Exception {    // Olutkanta: 64
    alustaKanta(); 
    lager = new Oluttyyppi(); 
    assertEquals("From: Olutkanta line: 68", 2, olutkanta.etsi("*",0).size()); 
    ((Collection<Oluttyyppi>) olutkanta.annaOluttyyppi(lager.getTyyppiID())).size(); assertEquals("From: Olutkanta line: 67", 2 ); 
    assertEquals("From: Olutkanta line: 70", 1, olutkanta.poista(lager.getTyyppiID())); 
    assertEquals("From: Olutkanta line: 71", 1, olutkanta.etsi("*",0).size()); 
    assertEquals("From: Olutkanta line: 72", 0, olutkanta.annaOluet(lager).size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testPoistaOlut87 
   * @throws Exception when error
   */
  @Test
  public void testPoistaOlut87() throws Exception {    // Olutkanta: 87
    alustaKanta(); 
    assertEquals("From: Olutkanta line: 90", 2, olutkanta.annaOluet(lager).size()); 
    olutkanta.annaOluttyyppi(koff.getTyyppiID()); 
    assertEquals("From: Olutkanta line: 92", 1, olutkanta.annaOluet(lager).size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLisaa141 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa141() throws SailoException {    // Olutkanta: 141
    Olutkanta olutkanta = new Olutkanta(); 
    Olut koff = new Olut(), karhu = new Olut(); 
    koff.rekisteroi(); karhu.rekisteroi(); 
    assertEquals("From: Olutkanta line: 146", 0, olutkanta.getOluet()); 
    Oluttyyppi lager = new Oluttyyppi(); 
    olutkanta.lisaa(koff); assertEquals("From: Olutkanta line: 148", 1, olutkanta.getOluet()); 
    olutkanta.lisaa(karhu); assertEquals("From: Olutkanta line: 149", 2, olutkanta.getOluet()); 
    olutkanta.lisaa(koff); assertEquals("From: Olutkanta line: 150", 3, olutkanta.getOluet()); 
    assertEquals("From: Olutkanta line: 151", 3, olutkanta.getOluet()); 
    assertEquals("From: Olutkanta line: 152", koff, olutkanta.annaOluet(lager)); 
    assertEquals("From: Olutkanta line: 153", karhu, olutkanta.annaOluet(lager)); 
    assertEquals("From: Olutkanta line: 154", koff, olutkanta.annaOluet(lager)); 
    try {
    assertEquals("From: Olutkanta line: 155", koff, olutkanta.annaOluet(lager)); 
    fail("Olutkanta: 155 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    olutkanta.lisaa(koff); assertEquals("From: Olutkanta line: 156", 4, olutkanta.getOluet()); 
    olutkanta.lisaa(koff); assertEquals("From: Olutkanta line: 157", 5, olutkanta.getOluet()); 
    try {
    olutkanta.lisaa(koff); 
    fail("Olutkanta: 158 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testKorvaaTaiLisaa173 
   * @throws SailoException when error
   */
  @Test
  public void testKorvaaTaiLisaa173() throws SailoException {    // Olutkanta: 173
    alustaKanta(); 
    assertEquals("From: Olutkanta line: 176", 2, olutkanta.etsi("*",0).size()); 
    olutkanta.korvaaTaiLisaa(koff); 
    assertEquals("From: Olutkanta line: 178", 2, olutkanta.etsi("*",0).size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta231 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta231() throws SailoException {    // Olutkanta: 231
    String hakemisto = "testikanta"; 
    File dir = new File(hakemisto); 
    File ftied  = new File(hakemisto+"/tyypit.dat"); 
    File fhtied = new File(hakemisto+"/merkit.dat"); 
    dir.mkdir(); 
    ftied.delete(); 
    fhtied.delete(); 
    olutkanta = new Olutkanta();  // tiedostoja ei ole, tulee poikkeus 
    try {
    olutkanta.lueTiedostosta(hakemisto); 
    fail("Olutkanta: 245 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    alustaKanta(); 
    olutkanta.setTiedosto(hakemisto);  // nimi annettava koska uusi poisti sen
    olutkanta.talleta(); 
    olutkanta = new Olutkanta(); 
    olutkanta.lueTiedostosta(hakemisto); 
    Collection<Oluttyyppi> kaikki = olutkanta.etsi("",-1); 
    Iterator<Oluttyyppi> it = kaikki.iterator(); 
    assertEquals("From: Olutkanta line: 253", lager, it.next()); 
    assertEquals("From: Olutkanta line: 254", pils, it.next()); 
    assertEquals("From: Olutkanta line: 255", false, it.hasNext()); 
    List<Olut> loytyneet = olutkanta.annaOluet(lager); 
    Iterator<Olut> ih = loytyneet.iterator(); 
    assertEquals("From: Olutkanta line: 258", koff, ih.next()); 
    assertEquals("From: Olutkanta line: 259", karjala, ih.next()); 
    assertEquals("From: Olutkanta line: 260", false, ih.hasNext()); 
    loytyneet = olutkanta.annaOluet(pils); 
    ih = loytyneet.iterator(); 
    assertEquals("From: Olutkanta line: 263", koff, ih.next()); 
    assertEquals("From: Olutkanta line: 264", karjala, ih.next()); 
    assertEquals("From: Olutkanta line: 265", karhu, ih.next()); 
    assertEquals("From: Olutkanta line: 266", false, ih.hasNext()); 
    olutkanta.lisaa(pils); 
    olutkanta.lisaa(karhu); 
    olutkanta.talleta();  // tekee molemmista .bak
    assertEquals("From: Olutkanta line: 270", true, ftied.delete()); 
    assertEquals("From: Olutkanta line: 271", true, fhtied.delete()); 
    File fbak = new File(hakemisto+"/tyypit.bak"); 
    File fhbak = new File(hakemisto+"/merkit.bak"); 
    assertEquals("From: Olutkanta line: 274", true, fbak.delete()); 
    assertEquals("From: Olutkanta line: 275", true, fhbak.delete()); 
    assertEquals("From: Olutkanta line: 276", true, dir.delete()); 
  } // Generated by ComTest END
}