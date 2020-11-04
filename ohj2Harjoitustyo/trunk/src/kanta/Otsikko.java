package kanta;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Santeri Saarinen
 * @version 18.3.2019
 * Otsikko luokka pitaa sisallaan tiedon oluttyypin otsikosta. Tama jai tekematta.
 * Tämä jää ehkä toteuttamatta
 */
public class Otsikko {

    private int otsikkoID;
    private String otsikko = "";
    private static int seuraavaNro = 1;
    static Random rand = new Random();
    

    /**
     * @return Oluttyypin otsikko
     */
    public String getOtsikko() {
        return otsikkoID + " " +  otsikko;
    }
    

     /**
     * Asetetaan testivastauksen arvot
     */
    public void vastaaPohjahiiva() {
    	 otsikko = "Pohjahiivaoluet";
    	 otsikkoID = 5;
     }


    /**
     * Tulostetaan otsikon tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", otsikkoID , 3) + " " + otsikko);
    }
    
    
    /**
     * Tulostetaan otsikon tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /** Antaa otsikon seuraavan otsikko ID:n
     * @return otsikon ID
     */
    public int rekisteroi() {
    	otsikkoID = seuraavaNro;
        seuraavaNro++;
        return otsikkoID;
    }
    
    
	/** Haetaan otsikon ID
	 * @return otsikon id
	 */
	public int getOtsikkoID() {
		return otsikkoID;
	}
       
	
	private void setOtsikkoID(int nr) {
	    otsikkoID = nr;
	    if (otsikkoID >= seuraavaNro) seuraavaNro = otsikkoID + 1;
	}
	
    
    @Override
    public String toString() {
        return "" +
        	getOtsikkoID() + "|" +
        	otsikko;
    }
    
	
    /**
     * Selvitaa otsikoiden tiedot | erotellusta merkkijonosta.
     * Pitaa huolen etta seuraavaNro on suurempi kuin tuleva tunnusnro.
     * @param rivi josta oluiden tiedot otetaan
     * @example
     * <pre name="test">
     *   Otsikko otsikko = new Otsikko();
     *   otsikko.parse("   2   |  Oluttyyppi    ");
     *   otsikko.getOtsikkoID() === 10;
     *   otsikko.toString()    === "2|Oluttyyppi";
     *   
     *   otsikko.rekisteroi();
     *   int n = otsikko.getOtsikkoID();
     *   otsikko.parse(""+(n+20));
     *   otsikko.rekisteroi();
     *   otsikko.getOtsikkoID() === n+20+1;
     *   otsikko.toString()     === "" + (n+20+1) + "|Oluttyyppi";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setOtsikkoID(Mjonot.erota(sb, '|', getOtsikkoID()));
        otsikko = Mjonot.erota(sb, '|', otsikko);
    }
	
    
    /** Kutsutaan testialiohjelmia
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Otsikko VehnaOluet = new Otsikko(), Pohjahiivaoluet = new Otsikko();
        VehnaOluet.vastaaPohjahiiva();
        VehnaOluet.rekisteroi();
        VehnaOluet.tulosta(System.out);
        Pohjahiivaoluet.vastaaPohjahiiva();
        Pohjahiivaoluet.rekisteroi();
        Pohjahiivaoluet.tulosta(System.out);
    }

}
