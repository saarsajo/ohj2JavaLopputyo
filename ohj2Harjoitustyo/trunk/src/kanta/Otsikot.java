package kanta;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;


/**
 * @author Santeri Saarinen
 * @version 1.3.2019
 * Tama jai toteuttamatta
 */
public class Otsikot{
        private static final int MAX_MAARA = 20;
        private int  lkm = 0;
        private String otsikkoNimi = "";
        private String tiedostonNimi = "";
        private Otsikko otsikkoAlkiot[] = new Otsikko[MAX_MAARA];
        private String tiedostonPerusNimi = "otsikot";
        
        
        /**
         * Oletusmuodostaja
         */
        public Otsikot() {
            // Attribuuttien oma alustus riittaa
        }
        
        
        /**
         * Lisaa uuden otsikon tietorakenteeseen.  Ottaa otsikon omistukseensa.
         * @param otsikko on otsikko
         * @throws SailoException jos tietorakenne on jo taynna
         * @example
         * <pre name="test">
         * #THROWS SailoException
         * Otsikot otsikot = new Otsikot();
         * Otsikko koff = new Otsikko(), karhu = new Otsikko();
         * otsikot.getLkm() === 0;
         * otsikot.lisaa(koff); otsikot.getLkm() === 1;
         * otsikot.lisaa(karhu); otsikot.getLkm() === 2;
         * otsikot.lisaa(koff); otsikot.getLkm() === 3;
         * otsikot.anna(0) === koff;
         * otsikot.anna(1) === karhu;
         * otsikot.anna(2) === koff;
         * otsikot.anna(3) === koff; #THROWS IndexOutOfBoundsException
         * otsikot.lisaa(koff); otsikot.getLkm() === 4;
         * otsikot.lisaa(koff); otsikot.getLkm() === 5;
         * otsikot.lisaa(koff);  #THROWS SailoException
         * </pre>
         */            
        public void lisaa(Otsikko otsikko) throws SailoException {
            if (lkm >= otsikkoAlkiot.length) {
            	otsikkoAlkiot = Arrays.copyOf(otsikkoAlkiot, lkm+20); 
            }
            otsikkoAlkiot[lkm] = otsikko;
            lkm++;
        }
        
        
        /**
         * Palauttaa viitteen i:teen otsikkoon.
         * @param i monennenko otsikko viite halutaan
         * @return viite otsikkoon, jonka indeksi on i
         * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella 
         */
        public Otsikko anna(int i) throws IndexOutOfBoundsException {
            if (i < 0 || lkm <= i)
                throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
            return otsikkoAlkiot[i];
        }
        
        
        /**
         * Luetaan tiedostosta
         * @throws SailoException virhe
         */
        public void lueTiedostosta() throws SailoException {
            lueTiedostosta(getTiedostonPerusNimi());
        }

        
        /**
         * @param tied luettavan tiedoston nimi
         * @throws SailoException virhe ilmoitus
         */
        public void lueTiedostosta(String tied) throws SailoException {
            setTiedostonPerusNimi(tied);
            try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            	otsikkoNimi = fi.readLine();
                if ( otsikkoNimi == null ) throw new SailoException("Kannan nimi puuttuu");
                String rivi = fi.readLine();
                if ( rivi == null ) throw new SailoException("Maksimikoko puuttuu");
                // int maxKoko = Mjonot.erotaInt(rivi,10); // tehdaan jotakin

                while ( (rivi = fi.readLine()) != null ) {
                    rivi = rivi.trim();
                    if ( "".equals(rivi) || rivi.charAt(0) == ';' ) {
                    	continue;
                    }
                    Otsikko otsikko = new Otsikko();
                    otsikko.parse(rivi); // voisi olla virhekasittely
                    lisaa(otsikko);
                }
            } catch ( FileNotFoundException e ) {
                throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
            } catch ( IOException e ) {
                throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
            }
        }
        
        
        /**
         * Asettaa tiedoston perusnimen ilman tarkenninta
         * @param nimi tallennustiedoston perusnimi
         */
        public void setTiedostonPerusNimi(String nimi) {
            tiedostonPerusNimi = nimi;
        }
        
        
        /**
         * Palauttaa tiedoston nimen, jota kaytetaan tallennukseen
         * @return tallennustiedoston nimi
         */
        public String getTiedostonPerusNimi() {
            return tiedostonPerusNimi;
        }
                
        
        /**
         * Palauttaa tiedoston nimen, jota kaytetaan tallennukseen
         * @return tallennustiedoston nimi
         */
        public String getTiedostonNimi() {
            return getTiedostonPerusNimi() + ".dat";
        }


        /**
         * Palauttaa varakopiotiedoston nimen
         * @return varakopiotiedoston nimi
         */
        public String getBakNimi() {
            return tiedostonPerusNimi + ".bak";
        }
        
        
        /**
         * Tallentaa otsikon tiedostoon.  Kesken.
         * @throws SailoException jos talletus epäonnistuu
         */
        public void talleta() throws SailoException {
            throw new SailoException("Ei osata viela tallettaa tiedostoa " + tiedostonNimi);
        }
        
        
        /**
         * Palauttaa kannan otsikoiden lukumaaran
         * @return oluttyyppien lukumaara
         */
        public int getLkm() {
            return lkm;
        }
        
        
        /**
         * Testiohjelma oluille
         * @param args ei kaytossa
         */
        public static void main(String args[]) {
        	 Otsikot otsikot = new Otsikot();
             Otsikko lager = new Otsikko(), pils = new Otsikko();
             lager.rekisteroi();
             lager.vastaaPohjahiiva();
             pils.rekisteroi();
             pils.vastaaPohjahiiva();

             try {
                 otsikot.lisaa(lager);
                 otsikot.lisaa(pils);

                 System.out.println("============= Oluttyypin testi =================");

                 for (int i = 0; i < otsikot.getLkm(); i++) {
                     Otsikko otsikko = otsikot.anna(i);
                     System.out.println("Otsikon nro: " + i);
                     otsikko.tulosta(System.out);
                 }

             } catch (SailoException ex) {
                 System.out.println(ex.getMessage());
             }
        }
}