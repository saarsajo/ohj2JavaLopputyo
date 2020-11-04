package kanta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import fi.jyu.mit.fxgui.Dialogs;


/**
 * @author Santeri Saarinen
 * @version 1.3.2019
 * Käsittelee oluiden lukemisen ja tallentamisen tiedostosta
 */
public class Oluet implements Iterable<Olut>{
        private String tiedostonPerusNimi = "";
        private boolean muutettu = false;
        private final Collection<Olut> alkiot = new ArrayList<Olut>();

        
        /**
         * Oletusmuodostaja
         */
        public Oluet() {
            // Attribuuttien oma alustus riittaa
        }
        
        
        /** Lisätään haluttu olut listaan
         * @param olu lisaättävä olut
         */
        public void lisaa(Olut olu) {
            alkiot.add(olu);
            muutettu = true;
        }

        
        /**
         * Lukee oluita tiedostosta.  Kesken.
         * @throws SailoException jos lukeminen epaonnistuu
         */
        public void lueTiedostosta() throws SailoException {
        	 lueTiedostosta(getTiedostonPerusNimi());
        }

        
        /**
         * Lukee oluet tiedostosta.
         * @param tied tiedoston nimen alkuosa
         * @throws SailoException jos lukeminen ep�onnistuu
         * 
         * @example
         * <pre name="test">
         * #THROWS SailoException 
         * #import java.io.File;
         *  Oluet uudetOluet = new Oluet();
         *  Olut koff = new Olut(); koff.vastaaKoff(2);
         *  Olut karjala = new Olut(); karjala.vastaaKoff(1);
         *  Olut karhu = new Olut(); karhu.vastaaKoff(2); 
         *  String tiedNimi = "merkit";
         *  File ftied = new File(tiedNimi+".dat");
         *  ftied.delete();
         *  uudetOluet.lueTiedostosta(tiedNimi); #THROWS SailoException
         *  uudetOluet.lisaa(koff);
         *  uudetOluet.lisaa(karjala);
         *  uudetOluet.lisaa(karhu);
         *  uudetOluet.talleta();
         *  uudetOluet = new Oluet();
         *  uudetOluet.lueTiedostosta(tiedNimi);
         *  Iterator<Olut> i = uudetOluet.iterator();
         *  i.next().toString() === koff.toString();
         *  i.next().toString() === karjala.toString();
         *  i.next().toString() === karhu.toString();
         *  i.hasNext() === false;
         *  uudetOluet.lisaa(karhu);
         *  uudetOluet.talleta();
         *  ftied.delete() === true;
         *  File fbak = new File(tiedNimi+".bak");
         *  fbak.delete() === true;
         * </pre>
         */
        public void lueTiedostosta(String tied) throws SailoException {
            setTiedostonPerusNimi(tied);
            try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {

                String rivi;
                while ( (rivi = fi.readLine()) != null ) {
                    rivi = rivi.trim();
                    if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                    Olut uusiOlut = new Olut();
                    uusiOlut.parse(rivi); // voisi olla virhek�sittely
                    lisaa(uusiOlut);
                }
                muutettu = false;

            } catch ( FileNotFoundException e ) {
                throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
            } catch ( IOException e ) {
                throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
            }
        }
        
        
        /**
         * Tallentaa oluet tiedostoon.  Kesken.
         * @throws SailoException jos talletus epaonnistuu
         */
        public void talleta() throws SailoException {
            if ( !muutettu ) return;

            File tiedVaralla = new File(getBakNimi());
            File merkkiTiedosto = new File(getTiedostonNimi());
            tiedVaralla.delete(); //  if ... System.err.println("Ei voi tuhota");
            merkkiTiedosto.renameTo(tiedVaralla); //  if ... System.err.println("Ei voi nimet�");

            try ( PrintWriter fo = new PrintWriter(new FileWriter(merkkiTiedosto.getCanonicalPath())) ) {
                for (Olut uusiOlut : this) {
                    fo.println(uusiOlut.toString());                   
                }
                Dialogs.showMessageDialog("Oluiden muutosten tallennus onnistui");
            } catch ( FileNotFoundException ex ) {
                throw new SailoException("Tiedosto " + merkkiTiedosto.getName() + " ei aukea");
            } catch ( IOException ex ) {
                throw new SailoException("Tiedoston " + merkkiTiedosto.getName() + " kirjoittamisessa ongelmia");
            }
            muutettu = false;
        }  
        
        
        /**
         * Palauttaa Oluiden lukumaaran
         * @return Oluiden lukumaara
         */
        public int getLkm() {
            return alkiot.size();
        }
        

        /**
         * Asettaa tiedoston perusnimen ilan tarkenninta
         * @param tied tallennustiedoston perusnimi
         */
        public void setTiedostonPerusNimi(String tied) {
            tiedostonPerusNimi = tied;
        }


        /**
         * Palauttaa tiedoston nimen, jota k�ytet��n tallennukseen
         * @return tallennustiedoston nimi
         */
        public String getTiedostonPerusNimi() {
            return tiedostonPerusNimi;
        }


        /**
         * Palauttaa tiedoston nimen, jota k�ytet��n tallennukseen
         * @return tallennustiedoston nimi
         */
        public String getTiedostonNimi() {
            return tiedostonPerusNimi + ".dat";
        }        

        
        /**
         * Palauttaa varakopiotiedoston nimen
         * @return varakopiotiedoston nimi
         */
        public String getBakNimi() {
            return tiedostonPerusNimi + ".bak";
        }
        
        
        
        /**
         * Iteraattori kaikkien oluiden lapikayntiin
         * @return olutiteraattori
         * 
         * @example
         * <pre name="test">
         * #PACKAGEIMPORT
         * #import java.util.*;
         * 
         *  Oluet uudetOluet = new Oluet();

         *  Olut koff = new Olut(2); uudetOluet.lisaa(koff);
         *  Olut karjala = new Olut(1); uudetOluet.lisaa(karjala);
         *  Olut karhu = new Olut(2); uudetOluet.lisaa(karhu);
         * 
         *  Iterator<Olut> i2=uudetOluet.iterator();

         *  i2.next() === koff;
         *  i2.next() === karjala;
         *  i2.next() === karhu;
         *  i2.next() === karjala;  #THROWS NoSuchElementException  
         *  
         *  int n = 0;
         *  int tyypID[] = {2,1,2};
         *  
         *  for ( Olut olu:uudetOluet ) { 
         *    olu.getTyyppiID() === tyypID[n]; n++;  
         *  }
         *  
         *  n === 5;
         *  
         * </pre>
         */
        @Override
        public Iterator<Olut> iterator() {
            return alkiot.iterator();
        }
        
        
        /**
         * Korvaa oluen tietorakenteessa.  Ottaa oluen omistukseensa.
         * Etsitaan samalla tunnusnumerolla oleva olut.  Jos ei loydy,
         * niin lisataan uutena oluena.
         * @param olut lisattavan oluen viite.  Huom tietorakenne muuttuu omistajaksi
         * @throws SailoException jos tietorakenne taynna
         * <pre name="test">
         * #THROWS SailoException,CloneNotSupportedException
         * #PACKAGEIMPORT
         * Oluet oluet = new Oluet();
         * Olut koff = new Olut(), karjala = new Olut();
         * koff.rekisteroi(); karjala.rekisteroi();
         * oluet.getLkm() === 0;
         * oluet.korvaaTaiLisaa(koff); oluet.getLkm() === 1;
         * oluet.korvaaTaiLisaa(karjala); oluet.getLkm() === 2;
         * Olut korvausOlut = koff.clone();
         * korvausOlut.aseta(3,"kkk");
         * Iterator<Olut> it = oluet.iterator();
         * it.next() == koff === true;
         * oluet.korvaaTaiLisaa(korvausOlut); oluet.getLkm() === 2;
         * it = oluet.iterator();
         * Olut j0 = it.next();
         * j0 === korvausOlut;
         * j0 == korvausOlut === true;
         * j0 == koff === false;
         * </pre>
         */
        public void korvaaTaiLisaa(Olut olut) throws SailoException {
            int id = olut.getMerkkiID();
            for (int i = 0; i < alkiot.size(); i++) {
                if ( ((ArrayList<Olut>) alkiot).get(i).getMerkkiID() == id ) {
                    ((ArrayList<Olut>) alkiot).set(i, olut);
                    muutettu = true;
                    return;
                }
            }
            lisaa(olut);
        }
        
        
        /**
         * Poistaa valitun oluen
         * @param poistettavaOlut poistettava olut
         * @return tosi jos loytyi poistettava tietue 
         * @example
         * <pre name="test">
         * #THROWS SailoException 
         * #import java.io.File;
         *  Oluet oluet = new Oluet();
         *  Olut koff = new Olut(); koff.vastaaKoff(2);
         *  Olut karjala = new Olut(); karjala.vastaaKoff(1);
         *  Olut heineken = new Olut(); heineken.vastaaKoff(2); 
         *  Olut karhu = new Olut(); karhu.vastaaKoff(1); 
         *  Olut sandels = new Olut(); sandels.vastaaKoff(2); 
         *  oluet.lisaa(koff);
         *  oluet.lisaa(karjala);
         *  oluet.lisaa(heineken);
         *  oluet.lisaa(karhu);
         *  oluet.poista(sandels) === false ; oluet.getLkm() === 4;
         *  oluet.poista(karjala) === true;   oluet.getLkm() === 3;
         * </pre>
         */
        public boolean poista(Olut poistettavaOlut) {
            boolean ret = alkiot.remove(poistettavaOlut);
            if (ret) muutettu = true;
            return ret;
        }

        
        /**
         * Poistaa kaikki tietyn tietyn j�senen harrastukset
         * @param tyypID viite siihen, mihin liittyv�t tietueet poistetaan
         * @return montako poistettiin 
         * @example
         * <pre name="test">
         *  Oluet oluet = new Oluet();
         *  Olut koff = new Olut(); koff.vastaaKoff(2);
         *  Olut karjala = new Olut(); karjala.vastaaKoff(1);
         *  Olut heineken = new Olut(); heineken.vastaaKoff(2); 
         *  Olut karhu = new Olut(); karhu.vastaaKoff(1); 
         *  Olut sandels = new Olut(); sandels.vastaaKoff(2); 
         *  oluet.lisaa(koff);
         *  oluet.lisaa(karjala);
         *  oluet.lisaa(heineken);
         *  oluet.lisaa(karhu);
         *  oluet.poista(koff) === 3;  oluet.getLkm() === 2;
         *  oluet.poista(karjala) === 0;  oluet.getLkm() === 2;
         *  List<Olut> h = oluet.annaOluet(2);
         *  h.size() === 0; 
         *  h = oluet.annaOluet(1);
         *  h.get(0) === koff;
         *  h.get(1) === heineken;
         * </pre>
         */
        public int poistaOluttyypinOluet(int tyypID) {
            int n = 0;
            for (Iterator<Olut> it = alkiot.iterator(); it.hasNext();) {
                Olut olu = it.next();
                if ( olu.getTyyppiID() == tyypID ) {
                    it.remove();
                    n++;
                }
            }
            if (n > 0) muutettu = true;
            return n;
        }
        
        
        
        /**
         * Haetaan kaikki oluttyypin oluet TESTI KESKEN
         * @param tyyppiID oluttyypin id jolle harrastuksia haetaan
         * @return tietorakenne jossa viiteet l�ydetteyihin harrastuksiin
         * @example
         * <pre name="test">
         * #import java.util.*;
         * 
         *  Oluet uudetOluet = new Oluet();
         *  Olut pitsi21 = new Olut(2); uudetOluet.lisaa(pitsi21);
         *  Olut pitsi11 = new Olut(1); uudetOluet.lisaa(pitsi11);
         *  Olut pitsi22 = new Olut(2); uudetOluet.lisaa(pitsi22);
         *  Olut pitsi12 = new Olut(1); uudetOluet.lisaa(pitsi12);
         *  Olut pitsi23 = new Olut(2); uudetOluet.lisaa(pitsi23);
         *  Olut pitsi51 = new Olut(5); uudetOluet.lisaa(pitsi51);
         *  
         *  List<Olut> loytyneet;
         *  loytyneet = uudetOluet.annaOluet(3);
         *  loytyneet.size() === 0; 
         *  loytyneet = uudetOluet.annaOluet(1);
         *  loytyneet.size() === 2; 
         *  loytyneet.get(0) == pitsi11 === true;
         *  loytyneet.get(1) == pitsi12 === true;
         *  loytyneet = uudetOluet.annaOluet(5);
         *  loytyneet.size() === 1; 
         *  loytyneet.get(0) == pitsi51 === true;
         * </pre> 
         */
        public List<Olut> annaOluet(int tyyppiID) {
            List<Olut> loydetyt = new ArrayList<Olut>();
            for (Olut uusiOlut : alkiot)
                if ( uusiOlut.getTyyppiID() == tyyppiID ) loydetyt.add(uusiOlut);
            return loydetyt;
        }
        
        
        /**
         * Testiohjelma oluille
         * @param args ei kaytossa
         */
        public static void main(String args[]) {
            Oluet oluet = new Oluet();
            Olut koff = new Olut();
            koff.vastaaKoff(2);
            Olut karhu = new Olut();
            karhu.vastaaKoff(1);
            Olut lappari = new Olut();
            lappari.vastaaKoff(2);


            oluet.lisaa(koff);
            oluet.lisaa(karhu);
            oluet.lisaa(lappari);

            System.out.println("============= Harrastukset testi =================");

            List<Olut> uudetOluet = oluet.annaOluet(2);

            for (Olut uusiOlut : uudetOluet) {
                System.out.print(uusiOlut.getTyyppiID() + " ");
                uusiOlut.tulosta(System.out);
            }
        }
}