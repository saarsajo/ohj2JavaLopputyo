package kanta;

import java.io.File;
import java.util.Collection;
import java.util.List;


/**hakee oluet ja oluttyypit. Oluttyyppien väliotsikot jäivät tekemättä.
 * @author Santeri Saarinen
 * @version 1.3.2019
 * 
 * Testien alustus
 * @example
 * <pre name="testJAVA">
 * #import kanta.SailoException;
 *  private Olutkanta olutkanta;
 *  private Oluttyyppi lager;
 *  private Oluttyyppi pils;
 *  private int luku1;
 *  private int luku2;
 *  private Olut koff;
 *  private Olut karjala;
 *  private Olut karhu; 

 *  
 *  public void alustaKanta() {
 *    olutkanta = new Olutkanta();
 *    lager = new Oluttyyppi(); lager.vastaaLager(); lager.rekisteroi();
 *    pils = new Oluttyyppi(); pils.vastaaLager(); pils.rekisteroi();
 *    luku1 = lager.getTyyppiID();
 *    luku2 = pils.getTyyppiID();
 *    koff = new Olut(luku2); koff.vastaaKoff(luku2);
 *    karjala = new Olut(luku1); karjala.vastaaKoff(luku1);
 *    karhu = new Olut(luku2); karhu.vastaaKoff(luku2); 

 *    try {
 *    olutkanta.lisaa(lager);
 *    olutkanta.lisaa(pils);
 *    olutkanta.lisaa(koff);
 *    olutkanta.lisaa(karjala);
 *    olutkanta.lisaa(karhu);
 *    } catch ( Exception e) {
 *       System.err.println(e.getMessage());
 *    }
 *  }
 * </pre>
*/
public class Olutkanta {
    private Oluet oluet = new Oluet();
    private Oluttyypit oluttyypit = new Oluttyypit();
    ///private Otsikot otsikot = new Otsikot();
    
    
    /**
     * Poistaa oluttyyppin ja sen oluet 
     * @param poistettavaTyyppi poistettava oluttyyppi
     * @return montako oluttyyppia poistettiin
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   alustaKanta();
     *   lager = new Oluttyyppi();
     *   olutkanta.etsi("*",0).size() === 2;
    *    ((Collection<Oluttyyppi>) olutkanta.annaOluttyyppi(lager.getTyyppiID())).size(); assertEquals("From: Olutkanta line: 67", 2 ); 
     *   olutkanta.poista(lager.getTyyppiID()) === 1;
     *   olutkanta.etsi("*",0).size() === 1;
     *   olutkanta.annaOluet(lager).size() === 0;
     * </pre>
     */
    public int poista(Oluttyyppi poistettavaTyyppi) {
        if ( poistettavaTyyppi == null ) return 0;
        int ret = oluttyypit.poista(poistettavaTyyppi.getTyyppiID()); 
        oluet.poistaOluttyypinOluet(poistettavaTyyppi.getTyyppiID()); 
        return ret; 
    }
    
    
    /** 
     * Poistaa valitun oluen
     * @param olut poistettava olut 
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   alustaKanta();
     *   olutkanta.annaOluet(lager).size() === 2;
     *   olutkanta.annaOluttyyppi(koff.getTyyppiID());
     *   olutkanta.annaOluet(lager).size() === 1;
     */ 
    public void poistaOlut(Olut olut) { 
        oluet.poista(olut); 
    } 
    
    
    /**
     * Palautaa olutkannan oluiden maaran
     * @return oluiden lukumaara
     */
    public int getOluet() {
        return oluet.getLkm();
    }
    
    
    /**
     * Palautaa olutkannan oluttyyppien maaran
     * @return oluttyyppien lukumaara
     */
    public int getOluttyypit() {
        return oluttyypit.getLkm();
    }
    
    
    /**
     * Poistaa oluista ja tyypeist� ne joilla on nro. Kesken.
     * @param nro viitenumero, jonka mukaan poistetaan
     * @return montako olutta poistettiin
     */
    public int poista(@SuppressWarnings("unused") int nro) {
        return 0;
    }
    
    
    /**
     * @param oluttyyppi lisataan uusi oluttyyppi olutkantaan
     * @throws SailoException virhe
     */         
    public void lisaa (Oluttyyppi oluttyyppi) throws SailoException {
        oluttyypit.lisaa(oluttyyppi);
    }
    
    
    /**
     * Lisaa kantaan uusi olut
     * @param olut lisattava olut
     * @throws SailoException jos lisaysta ei voida tehda
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Olutkanta olutkanta = new Olutkanta();
     * Olut koff = new Olut(), karhu = new Olut();
     * koff.rekisteroi(); karhu.rekisteroi();
     * olutkanta.getOluet() === 0;
     * Oluttyyppi lager = new Oluttyyppi();
     * olutkanta.lisaa(koff); olutkanta.getOluet() === 1;
     * olutkanta.lisaa(karhu); olutkanta.getOluet() === 2;
     * olutkanta.lisaa(koff); olutkanta.getOluet() === 3;
     * olutkanta.getOluet() === 3;
     * olutkanta.annaOluet(lager) === koff;
     * olutkanta.annaOluet(lager) === karhu;
     * olutkanta.annaOluet(lager) === koff;
     * olutkanta.annaOluet(lager) === koff; #THROWS IndexOutOfBoundsException
     * olutkanta.lisaa(koff); olutkanta.getOluet() === 4;
     * olutkanta.lisaa(koff); olutkanta.getOluet() === 5;
     * olutkanta.lisaa(koff);            #THROWS SailoException
     * </pre>
     */     
    public void lisaa(Olut olut) throws SailoException{
        oluet.lisaa(olut);            
    }

    
    /** 
     * Korvaa oluen tietorakenteessa.  Ottaa oluen omistukseensa. 
     * Etsitaan samalla tunnusnumerolla oleva olut.  Jos ei loydy, 
     * niin lisataan uutena jasenena. 
     * @param olut lisattavan oluen viite.
     * @throws SailoException jos tietorakenne on jo taynna 
     * @example
     * <pre name="test">
     * #THROWS SailoException  
     *  alustaKanta();
     *  olutkanta.etsi("*",0).size() === 2;
     *  olutkanta.korvaaTaiLisaa(koff);
     *  olutkanta.etsi("*",0).size() === 2;
     * </pre>
     */ 
    public void korvaaTaiLisaa(Olut olut) throws SailoException { 
        oluet.korvaaTaiLisaa(olut); 
    } 
    
    
    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien oluttyyppien viitteet 
     * @param hakuehto hakuehto  
     * @param k etsittavan kentan indeksi  
     * @return tietorakenteen loytyneista oluttyypeista 
     * @throws SailoException Jos jotakin menee vaarin
     */ 
    public Collection<Oluttyyppi> etsi(String hakuehto, int k) throws SailoException { 
        return oluttyypit.etsi(hakuehto, k); 
    } 
    
    
    /** Palauttaa kaikki oluttyypit
     * @return kaikki oluttyypit
     */
    public Collection<Oluttyyppi> haeKaikki() { 
        return oluttyypit.haeKaikki(); 
    } 
    
    
      /**
     * @param i viite i:teen oluttyyppiin
     * @return viite oluttyyppiin
     * @throws IndexOutOfBoundsException virhe
     */
    public Oluttyyppi annaOluttyyppi(int i) throws IndexOutOfBoundsException {
            return oluttyypit.anna(i);
        }


     /** Haetaan kaikki oluttyypin alaiset oluet
     * @param oluttyyppi käsiteltävä oluttyyppi
     * @return oluttyypin sisaltamat oluet
     */
      public List<Olut> annaOluet(Oluttyyppi oluttyyppi) {
    	 return oluet.annaOluet(oluttyyppi.getTyyppiID());
      }	
   
    
      /**
       * Lukee kerhon tiedot tiedostosta
       * @param kannanNimi jota kaytetaan lukemisessa
       * @throws SailoException jos lukeminen ep�onnistuu
       * 
       * @example
       * <pre name="test">
       * #THROWS SailoException 
       * #import java.io.*;
       * #import java.util.*;
       * 
       *   
       *  String hakemisto = "testikanta";
       *  File dir = new File(hakemisto);
       *  File ftied  = new File(hakemisto+"/tyypit.dat");
       *  File fhtied = new File(hakemisto+"/merkit.dat");
       *  dir.mkdir();  
       *  ftied.delete();
       *  fhtied.delete();
       *  olutkanta = new Olutkanta(); // tiedostoja ei ole, tulee poikkeus 
       *  olutkanta.lueTiedostosta(hakemisto); #THROWS SailoException
       *  alustaKanta();
       *  olutkanta.setTiedosto(hakemisto); // nimi annettava koska uusi poisti sen
       *  olutkanta.talleta(); 
       *  olutkanta = new Olutkanta();
       *  olutkanta.lueTiedostosta(hakemisto);
       *  Collection<Oluttyyppi> kaikki = olutkanta.etsi("",-1); 
       *  Iterator<Oluttyyppi> it = kaikki.iterator();
       *  it.next() === lager;
       *  it.next() === pils;
       *  it.hasNext() === false;
       *  List<Olut> loytyneet = olutkanta.annaOluet(lager);
       *  Iterator<Olut> ih = loytyneet.iterator();
       *  ih.next() === koff;
       *  ih.next() === karjala;
       *  ih.hasNext() === false;
       *  loytyneet = olutkanta.annaOluet(pils);
       *  ih = loytyneet.iterator();
       *  ih.next() === koff;
       *  ih.next() === karjala;
       *  ih.next() === karhu;
       *  ih.hasNext() === false;
       *  olutkanta.lisaa(pils);
       *  olutkanta.lisaa(karhu);
       *  olutkanta.talleta(); // tekee molemmista .bak
       *  ftied.delete()  === true;
       *  fhtied.delete() === true;
       *  File fbak = new File(hakemisto+"/tyypit.bak");
       *  File fhbak = new File(hakemisto+"/merkit.bak");
       *  fbak.delete() === true;
       *  fhbak.delete() === true;
       *  dir.delete() === true;
       * </pre>
       */
    public void lueTiedostosta(String kannanNimi) throws SailoException {
        oluttyypit = new Oluttyypit();
        oluet = new Oluet();
        
        setTiedosto(kannanNimi);
        oluttyypit.lueTiedostosta();
        oluet.lueTiedostosta();
    }
    
    
    /**
     * Tallettaa olutkannan tiedot tiedostoon
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void talleta() throws SailoException {
        String virhe = "";
        try {
            oluttyypit.talleta();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }

        try {
            oluet.talleta();
        } catch ( SailoException ex ) {
            virhe += ex.getMessage();
        }
        if ( !"".equals(virhe) ) throw new SailoException(virhe);
    }
    
    
    /**
     * Asettaa tiedostojen perusnimet
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        File tiedostonSijainti = new File(nimi);
        tiedostonSijainti.mkdirs();
        String hakemistonNimi = "";
        if ( !nimi.isEmpty() ) hakemistonNimi = nimi +"/";
        oluttyypit.setTiedostonPerusNimi(hakemistonNimi + "tyypit");
      ///  otsikot.setTiedostonPerusNimi(hakemistonNimi + "otsikot");
        oluet.setTiedostonPerusNimi(hakemistonNimi + "merkit");
    }
    
    
    /**
     * Testiohjelma olutkannasta
     * @param args ei käytössä
     */
	public static void main(String args[]) {   
	    Olutkanta olutkanta = new Olutkanta();
	    
	    try {
    		Oluttyyppi lager = new Oluttyyppi(), ale = new Oluttyyppi();
    		lager.rekisteroi();
    		lager.vastaaLager();
    		ale.rekisteroi();
    		ale.vastaaLager();
    		
    		olutkanta.lisaa(lager);
    		olutkanta.lisaa(ale);
    		
    		int id1 = lager.getTyyppiID();
    		int id2 = ale.getTyyppiID();
    		
    		
    	    Olut koff = new Olut(); koff.rekisteroi(); koff.vastaaKoff(id1); olutkanta.lisaa(koff);
    		Olut lapinkulta = new Olut(); lapinkulta.rekisteroi(); lapinkulta.vastaaKoff(id2); olutkanta.lisaa(lapinkulta);
            Olut karhu = new Olut(); karhu.rekisteroi(); karhu.vastaaKoff(id1); olutkanta.lisaa(karhu);
            Olut carlsberg = new Olut(); carlsberg.rekisteroi(); carlsberg.vastaaKoff(id2); olutkanta.lisaa(carlsberg);
    		
    		System.out.println("============= Oluttyypin testi =================");
    	
    		for (int i = 0; i < olutkanta.getOluttyypit(); i++) {
    		    Oluttyyppi oluttyyppi = olutkanta.annaOluttyyppi(i);
    	        System.out.println("Oluttyyppi paikassa: " + i);
    	        oluttyyppi.tulosta(System.out);
    	        
    	        List<Olut> loytyneet = olutkanta.annaOluet(oluttyyppi);
                for (Olut olut : loytyneet) {
                    olut.tulosta(System.out);
                }
    		}
	    }
	    catch (SailoException ex) {
	        System.out.println(ex.getMessage());
	    }
	}
}