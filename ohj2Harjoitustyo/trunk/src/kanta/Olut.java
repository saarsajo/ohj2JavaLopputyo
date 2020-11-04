package kanta;

import java.io.*;
import java.util.Random;

import fi.jyu.mit.ohj2.Mjonot;
import testiJaTarkistus.alkoTarkastus;

/**
 * @author Santeri Saarinen
 * @version 28.2.2019
 * Olut luokka joka pitää sisällään oluiden tiedot
 */
public class Olut {
    private int merkkiID = seuraavaNro;
    private String merkki = "";
    private double alkoholi = 0;
    private int tyypinID = 1;
    private String maa = "";
    private double arvio = 0;
    private String lisatietoja = "";
    static Random rand = new Random();
    private static int seuraavaNro = 1;


    /**
     * Alustetaan olut
     */
    public Olut() {
        // viela ei tarvetta
    }


    /**
     * @param tyyppiID oluen tyypin id
     */
    public Olut(int tyyppiID) {
        this.tyypinID = tyyppiID;
    }
    
    /**
     * @param tyyppiID oluen tyypin id
     * @param merkki f
     * @param alkoholi g
     * @param maa j
     * @param arvio h
     * @param lisatietoja k
     */
    public Olut(int tyyppiID, String merkki, double alkoholi, String maa, double arvio, String lisatietoja) {
        this.merkki = merkki;
        this.alkoholi = alkoholi;
        this.tyypinID = tyyppiID;
        this.maa = maa;
        this.arvio = arvio;
        this.lisatietoja = lisatietoja;
        rekisteroi();
    }

    
    /**
     * @return oluiden kenttien lukumaara
     */
    public int getKenttia() {
        return 7;
    }


    /**
     * @return ensimmainen kayttajan syotettavan kentan indeksi
     */
    public int ekaKentta() {
        return 1;
    }
    

    /**
     * @param k minka kentan kysymys halutaan
     * @return valitun kentan kysymysteksti
     */
    public String getKysymys(int k) {
        switch (k) {
            case 0:
                return "MerkkiID";
            case 1:
                return "Merkki";
            case 2:
                return "Alkoholi %";
            case 3:
                return "Tyyppi";
            case 4:
                return "Maa";
            case 5:
                return "Arvio";
            case 6:
                return "Lisätietoja";
            default:
                return "???";
        }
    }


    /**
     * @param k Minka kentan sisalta halutaan
     * @return valitun kentan sisalto
     * @example
     * <pre name="test">
     *   Olut koff = new Olut();
     *   koff.parse("   200 |   koff   |  5  |  20   | Suomi | 3 | Nam ");
     *   koff.anna(0) === "200";   
     *   koff.anna(1) === "koff";   
     *   koff.anna(2) === "5";   
     *   koff.anna(3) === "20";   
     *   koff.anna(4) === "Suomi";   
     *   koff.anna(5) === "3";   
     *   koff.anna(6) === "Nam";   
     *   
     * </pre>
     */
    public String anna(int k) {
        switch (k) {
            case 0:
                return "" + merkkiID;
            case 1:
                return "" + merkki;
            case 2:
                return "" + alkoholi;
            case 3:
                return "" + tyypinID;
            case 4:
                return "" + maa;
            case 5:
                return "" + arvio;
            case 6:
                return "" + lisatietoja;
            default:
                return "???";
        }
    }
    
    
    /**
     * Asetetaan valitun kentan sisalto.  Mikali asettaminen onnistuu,
     * palautetaan null, muutoin virheteksti.
     * @param k minka kentan sisalto asetetaan
     * @param s asetettava sisalto merkkijonona
     * @return null jos ok, muuten virheteksti
     * @example
     * <pre name="test">
     *   Olut koff = new Olut();
     *   koff.aseta(2,"koira") === "Alkoholiprosentin kentässä vääriä merkkejä = \"kissa\"";
     *   koff.aseta(1,"koff")  === null;
     *   koff.aseta(5,"5")    === null;
     *   
     * </pre>
     */
    public String aseta(int k, String s) {
        seuraavaNro++;
        String st = s.trim();
        StringBuffer sb = new StringBuffer(st);
        switch (k) {
            case 0:
                setMerkkiID(Mjonot.erota(sb, '$', getMerkkiID()));
                return null;
            case 1:
                merkki = Mjonot.erota(sb, '$', merkki);
                return null;
            case 2:
                alkoTarkastus tarkastaja = new alkoTarkastus();
                String virhe = tarkastaja.alkoTark(st);
                        if (virhe != null ) {
                            return virhe;
                        }
                alkoholi = Double.parseDouble(st);
                return null;
                
                
                /*
                try {
                    alkoholi = Mjonot.erotaEx(sb, '§', alkoholi);
                } catch (NumberFormatException ex) {
                    return "Alkoholiprosentin kentässä vääriä merkkejä " + ex.getMessage();
                }
                return null;
                */
            case 3:
                setTyyppiID(Mjonot.erota(sb, '$', getTyyppiID()));
                return null;

            case 4:
                maa = Mjonot.erota(sb, '$', maa);
                return null;
            case 5:
                try {
                    arvio = Mjonot.erotaEx(sb, '§', arvio);
                } catch (NumberFormatException ex) {
                    return "Arvio kentässä vääriä merkkejä " + ex.getMessage();
                }
                return null;
            case 6:
                lisatietoja = Mjonot.erota(sb, '$', lisatietoja);
                return null;
              

            default:
                return "Väärä kentän indeksi";
                
        }
    }
    
    
    
    /**
     * Tehdaan identtinen klooni oluesta
     * @return Object kloonattu olut
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Olut koff = new Olut();
     *   koff.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   Olut kopio = koff.clone();
     *   kopio.toString() === koff.toString();
     *   koff.parse("   1   |  11  |   Uinti  | 1949 | 22 t ");
     *   kopio.toString().equals(koff.toString()) === false;
     * </pre>
     */
    @Override
    public Olut clone() throws CloneNotSupportedException { 
        return (Olut)super.clone();
    }
    

    /**
    * Apumetodi, jolla saadaan taytettya testiarvot oluelle.
    * @param tyyppiID tyypin id
    */
    public void vastaaKoff(int tyyppiID) {
        merkkiID = rekisteroi();
        merkki = "Koff III";
        alkoholi = 4.5;
        tyypinID = tyyppiID;
        maa = "Suomi";
        arvio = 2.5;
        lisatietoja = "Maistuu tosi hyvalle kymmenennen jalkeen";
    }


    /**
     * Tulostetaan oluen tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(merkkiID + merkki + "  "
                + alkoholi + " " + tyypinID + "  " + maa + " " + arvio + " "
                + lisatietoja);
    }


    /**
     * Tulostetaan oluen tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }


    /**
     * Antaa oluelle seuraavan merkki id:n.
     * @return oluen uusi merkki id
     * @example
     * <pre name="test">
     *   Olut koff = new Olut();
     *   koff.getMerkkiID() === 0;
     *   koff.rekisteroi();
     *   Olut karjala = new Olut();
     *   karjala.rekisteroi();
     *   int n1 = koff.getMerkkiID();
     *   int n2 = karjala.getMerkkiID();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        merkkiID = seuraavaNro;
        seuraavaNro++;
        return merkkiID;
    }
    
    
    /**
     * @return Oluen merkki
     * @example
     * <pre name="test">
     *   Olut koff = new Olut();
     *   koff.vastaaKoff(1);
     *   koff.getMerkki() =R= "Koff .*";
     * </pre>
     */
    public String getMerkki() {
        return merkki;
    }
    

    /**
     * Tulee ehkä muuttumaan
     * @return tyyppi
     */
    public String getTyyppi() {
        String tyyppiString = Double.toString(tyypinID);
        return tyyppiString;
    }


    /**
     * @return alkoholi prosentti
     */
    public String getAlkoholi() {
        String alkoString = Double.toString(alkoholi);
        return alkoString;
    }


    /**
     * @return oluen pano maan
     */
    public String getMaa() {
        return maa;
    }


    /**
     * @return arvio
     */
    public String getArvio() {
        String arvioString = Double.toString(arvio);
        return arvioString;
    }


    /**
     * @return lisatietoja
     */
    public String getLisatietoja() {
        return lisatietoja;
    }


    /**Haetaan merkin ID
     * @return merkin ID
     */
    public int getMerkkiID() {
        return merkkiID;
    }

    
    /**
     * Asettaa merkin ID:n
     * @param nr
     */
    private void setMerkkiID(int nr) {
        merkkiID = nr;
        if (merkkiID >= seuraavaNro) {
            seuraavaNro = merkkiID + 1;
        }
    }
    

    /**
     * Asettaa merkin 
     * @param merk kayttajan syote kohtaan merkki
     * @return merkki
     */
    public String setMerkki(String merk) {
        merkki = merk;
        return merkki;
    }	
    
    
    /**
     * @param alk alkoholiprosentti
     * @return alkoholiprosentin
     */
    public double setAlkoholi(String alk) {
		int alklukuna = Integer.parseInt(alk);
		alkoholi = alklukuna;
        return alkoholi;
	}


	/**
	 * @param s kayttajan syote kohtaan tyyppi
	 * @return tyyppi
	 */
	public int setTyyppi(String s) {
		int tyypID = Integer.parseInt(s);
		tyypinID = tyypID;
        return tyypinID;
	}

	
    /**
     * Asettaa tyypin ID:n
     * @param nr
     */
    private void setTyyppiID(int nr) {
        tyypinID = nr;
    }
	

	/**
	 * @param m kayttajan syote kohtaan maa
	 * @return maa
	 */
	public String setMaa(String m) {
        maa = m;
        return maa;
	}


	/**
	 * @param arv asettaa arvion
	 * @return arvio
	 */
	public double setArvio(String arv) {
		int arvlukuna = Integer.parseInt(arv);
        arvio = arvlukuna;
        return arvio;
	}


	/**
	 * @param listiet asettaa lisatietoja
	 * @return lisatiedot
	 */
	public String setLisatieto(String listiet) {
        lisatietoja = listiet;
        return lisatietoja;
	}
    
    

    /**Haetaan merkin ID
     * @return merkin ID
     */
    public int getTyyppiID() {
        return tyypinID;
    }

    
    /**
     * Palauttaa oluen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return olut tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Olut koff = new Olut();
     *   koff.parse("   5   |  koff  |  4.5  | 20 | Suomi | 3.0 | Oli nannaa ");
     *   koff.toString()    === "5|koff|4.5|20|Suomi|3.0|Oli nannaa";
     * </pre>
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("");
        String erotin = "";
        for (int k = 0; k < getKenttia(); k++) {
            sb.append(erotin);
            sb.append(anna(k));
            erotin = "|";
        }
        return sb.toString();
    }
    

    /**
     * Selvitetaan oluen tiedot | erotellusta merkkijonosta.
     * Pitaa huolen etta seuraavaNro on suurempi kuin tuleva merkkiID.
     * @param rivi josta oluen tiedot otetaan
     * @example
     * <pre name="test">
     *   Olut koff = new Olut();
     *   koff.parse("   5   |  koff  |  4.5  | 20 | Suomi | 3.0 | Oli nannaa ");
     *   koff.getTyyppiID() === 20;
     *   koff.toString()    === "5|koff|4.5|20|Suomi|3.0|Oli nannaa";
     *   
     *   koff.rekisteroi();
     *   int n = koff.getMerkkiID();
     *   koff.parse(""+(n+10));
     *   koff.rekisteroi();
     *   koff.getMerkkiID() === n+10+1;
     *   koff.toString()     === "" + (n+10+1) + "|koff|4.5|20|Suomi|3.0|Oli nannaa";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        for (int k = 0; k < getKenttia(); k++)
            aseta(k, Mjonot.erota(sb, '|'));
    }


    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) return false;
        return this.toString().equals(obj.toString());
    }
    

    @Override
    public int hashCode() {
        return merkkiID;
    }


    /** Kutsutaan testioluen aliohjelmia
     * @param args ei k�yt�ss�
     */
    public static void main(String[] args) {
        Olut koff = new Olut();
        koff.vastaaKoff(1);
        koff.tulosta(System.out);
    }
}