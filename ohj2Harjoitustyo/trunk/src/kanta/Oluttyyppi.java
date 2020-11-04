package kanta;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Comparator;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Santeri Saarinen
 * @version 18.3.2019
 * Oluttyyppi luokka hoitaa oluttyypin tietoja
 */
public class Oluttyyppi {

    int tyyppiID;
    private int otsikkoID;
    private String tyyppi = "";
    private static int seuraavaNro = 1;
    
    
    /**
     * Alustetaan oluttyyppi
     */
    public Oluttyyppi() {
        //Tyhja
    }
    
    /** 
     * Oluttyyppien vertailija 
     */ 
    public static class Vertailija implements Comparator<Oluttyyppi> { 
        private int k;  
         
        @SuppressWarnings("javadoc") 
        public Vertailija(int k) { 
            this.k = k; 
        } 
         
        @Override 
        public int compare(Oluttyyppi oluttyyppi1, Oluttyyppi oluttyyppi2) { 
            return oluttyyppi1.getAvain(k).compareToIgnoreCase(oluttyyppi2.getAvain(k)); 
        } 
    } 
    
    
   /** 
    * Antaa k:n kentan sisallon merkkijonona 
    * @param k monenenko kentan sisalto palautetaan 
    * @return kentan sisalto merkkijonona 
    */ 
   public String getAvain(int k) { 
       switch ( k ) { 
       case 0: return "" + tyyppiID; 
       case 1: return "" + otsikkoID; 
       case 2: return "" + tyyppi;
       default: return "���li�"; 
       } 
   }  
    
   
   /**
    * Palauttaa oluttyyppien kenttien lukumaaran
    * @return kenttien lukum��r�
    */
   public int getKenttia() {
       return 3;
   }
   
   
   /**
    * Eka potentiaalinen kentta joka halutaan kysya
    * @return eknn kent�n indeksi
    */
   public int ekaKentta() {
       return 0;
   }
   
    
    /**
     * @return oluttyyppi
     * @example
     * <pre name="test">
     *   Oluttyyppi lager = new Oluttyyppi();
     *   lager.vastaaLager();
     *   lager.getTyyppi() =R= "Lager .*";
     * </pre>
     */
    public String getTyyppi() {
        return tyyppi;
    }
    
   
    /**
     * Antaa k:n kentan sisallon merkkijonona
     * @param k monenenko kentan sisalto palautetaan
     * @return kentan sisalto merkkijonona
     */
    public String anna(int k) {
        switch ( k ) {
        case 0: return "" + tyyppiID;
        case 1: return "" + otsikkoID;
        case 2: return "" + tyyppi;
        default: return "���li�";
        }
    }
    
    
    /**
     * Asettaa k:n kent�n arvoksi parametrina tuodun merkkijonon arvon
     * @param k kuinka monennen kent�n arvo asetetaan
     * @param jono jonoa joka asetetaan kent�n arvoksi
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus.
     * @example
     * <pre name="test">
     *   Oluttyyppi oluttyyppi = new Oluttyyppi();
     *   oluttyyppi.aseta(0,"1") === 1;
     *   oluttyyppi.aseta(1,"50") === 50;
     *   oluttyyppi.aseta(1,"FAasfsa") === null; 
     *   oluttyyppi.aseta(2,"4") === 4; 
     * </pre>
     */
    public String aseta(int k, String jono) {
        String tjono = jono.trim();
        StringBuffer sb = new StringBuffer(tjono);
        switch ( k ) {
        case 0:
            setTyyppiID(Mjonot.erota(sb, '�', getTyyppiID()));
            return null;
        case 1:
            otsikkoID = Integer.parseInt(tjono);       
            return null;
        case 2:
            tyyppi = tjono;
            return null;
        default:
            return "��li�";
        }
    }
    
    
     /**
     * Apumetodi, jolla saadaan taytettya testiarvot oluttyypille
     */
     public void vastaaLager() {
    	 tyyppi = "Lager";
    	 otsikkoID = 5;
     }
     
     
     /**
     * Apumetodi, jolla saadaan taytettya testiarvot oluttyypille
     * @param tyyp Kayttajan syottama oluttyyppi
     */
     public void vastaaTyyppi(String tyyp) {
         tyyppi = tyyp;
         otsikkoID = 5;
     }


    /**
     * Tulostetaan oluttyypin tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tyyppiID , 3) + " " + otsikkoID +  " " + tyyppi);
    }
    
    
    /**
     * Tulostetaan oluttyypin tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Antaa oluttyypille seuraavan id:n.
     * @return oluttyyypin id
     * @example
     * <pre name="test">
     *   Oluttyyppi Lager = new Oluttyyppi();
     *   Lager.getTyyppiID() === 0;
     *   Lager.rekisteroi();
     *   Oluttyyppi Ale = new Oluttyyppi();
     *   Ale.rekisteroi();
     *   int n1 = Lager.getTyyppiID();
     *   int n2 = Ale.getTyyppiID();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tyyppiID = seuraavaNro;
        seuraavaNro++;
        return tyyppiID;
    }
    
    
	/** Haetaan oluttyypin ID
	 * @return oluttyypin id
	 */
	public int getTyyppiID() {
		return tyyppiID;
	}
	
	
    /**
     * Asettaa tunnusnumeron ja samalla varmistaa ettq
     * seuraava numero on aina suurempi kuin tahan mennessa suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTyyppiID(int nr) {
        tyyppiID = nr;
        if ( tyyppiID >= seuraavaNro ) seuraavaNro = tyyppiID + 1;
    }
       
    
    @Override
    public String toString() {
        return "" +
        	getTyyppiID() + "|" +
        	otsikkoID + "|" +
        	tyyppi;
    }
    
	
    /**
     * Selvitaa oluttyyppien tiedot | erotellusta merkkijonosta.
     * Pitaa huolen etta seuraavaNro on suurempi kuin tuleva tunnusnro.
     * @param rivi josta oluttyyppien tiedot otetaan
     * @example
     * <pre name="test">
     *   Oluttyyppi oluttyyppi = new Oluttyyppi();
     *   oluttyyppi.parse(" 001   |  5   |  Lager ");
     *   oluttyyppi.getTyyppiID() === 5;
     *   oluttyyppi.toString()    === "001|5|Lager";
     *   
     *   oluttyyppi.rekisteroi();
     *   oluttyyppi.parse(""+(5));
     *   oluttyyppi.rekisteroi();
     *   oluttyyppi.getTyyppiID() === 5;
     *   oluttyyppi.toString()     === "" + (5) + "|001|5|Lager|";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTyyppiID(Mjonot.erota(sb, '|', getTyyppiID()));
///        tyyppiID = Mjonot.erota(sb, '|', tyyppiID);
        otsikkoID = Mjonot.erota(sb, '|', otsikkoID);
        tyyppi = Mjonot.erota(sb, '|', tyyppi);
    }
	
    
    /** Kutsutaan testialiohjelmia
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Oluttyyppi lager = new Oluttyyppi(), pils = new Oluttyyppi();
        lager.vastaaLager();
        lager.rekisteroi();
        lager.tulosta(System.out);
        pils.vastaaLager();
        pils.rekisteroi();
        pils.tulosta(System.out);
    }
}
