package kanta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.ohj2.WildChars;


/** Oluttyyppien luokka, joka hoitaa tiedostojen lukemiset, tallentamiset jne.
 * @author Santeri Saarinen
 * @version 1.3.2019
 *
 */
public class Oluttyypit implements Iterable<Oluttyyppi> {
        private static final int MAX_MAARA = 20;
        private int  lkm = 0;
        private String tyypinNimi = "";
        private String kokoNimi = "";
        private Oluttyyppi tyyppiAlkiot[] = new Oluttyyppi[MAX_MAARA];
        private String tiedostonPerusNimi = "tyypit";
        private boolean muutettu = false;

        
        /**
         * Oletusmuodostaja
         */
        public Oluttyypit() {
            // Attribuuttien oma alustus riittaa
        }
        
        
        /**
         * Lisaa uuden oluttyypin tietorakenteeseen.  Ottaa oluttyypin omistukseensa.
         * @param oluttyyppi lis�t��v�n j�senen viite.  Huom tietorakenne muuttuu omistajaksi
         * @throws SailoException jos tietorakenne on jo t�ynn�
         * @example
         * <pre name="test">
         * #THROWS SailoException 
         * Oluttyypit oluttyypit = new Oluttyypit();
         * Oluttyyppi lager = new Oluttyyppi(), pils = new Oluttyyppi();
         * oluttyypit.getLkm() === 0;
         * oluttyypit.lisaa(lager); oluttyypit.getLkm() === 1;
         * oluttyypit.lisaa(pils); oluttyypit.getLkm() === 2;
         * oluttyypit.lisaa(lager); oluttyypit.getLkm() === 3;
         * Iterator<Oluttyyppi> it = oluttyypit.iterator(); 
         * it.next() === lager;
         * it.next() === pils; 
         * it.next() === lager;  
         * oluttyypit.lisaa(lager); oluttyypit.getLkm() === 4;
         * oluttyypit.lisaa(lager); oluttyypit.getLkm() === 5;
         * </pre>
         */        
        public void lisaa(Oluttyyppi oluttyyppi) throws SailoException {
            if (lkm >= tyyppiAlkiot.length) {
            	tyyppiAlkiot = Arrays.copyOf(tyyppiAlkiot, lkm + 20); 
            }
            tyyppiAlkiot[lkm] = oluttyyppi;
            lkm++;
            muutettu = true;
        }
        
        
        /** 
         * Poistaa oluttyypin jolla on valittu tunnusnumero  
         * @param tyyppiID poistettavan oluttyypin tunnusnumero 
         * @return 1 jos poistettiin, 0 jos ei loydy 
         * @example 
         * <pre name="test"> 
         * #THROWS SailoException  
         * Oluttyypit oluttyypit = new Oluttyypit(); 
         * Oluttyyppi lager = new Oluttyyppi(), stout = new Oluttyyppi(); 
         * lager.rekisteroi(); stout.rekisteroi(); 
         * int id1 = lager.getTyyppiID(); 
         * oluttyypit.lisaa(lager); oluttyypit.lisaa(stout); 
         * oluttyypit.poista(id1+1) === 1; 
         * oluttyypit.annaId(id1+1) === null; oluttyypit.getLkm() === 2; 
         * oluttyypit.poista(id1) === 1; oluttyypit.getLkm() === 1; 
         * oluttyypit.poista(id1+3) === 0; oluttyypit.getLkm() === 1; 
         * </pre> 
         *  
         */ 
        public int poista(int tyyppiID) {
            int ind = etsiId(tyyppiID); 
            if (ind < 0) return 0; 
            lkm--; 
            for (int i = ind; i < lkm; i++) 
                tyyppiAlkiot[i] = tyyppiAlkiot[i + 1]; 
            tyyppiAlkiot[lkm] = null; 
            muutettu = true; 
            return 1;}
        
        
        /**
         * Palauttaa viitteen i:teen oluttyyppiin.
         * @param i monennenko oluttyypin viite halutaan
         * @return viite oluttyypiin, jonka indeksi on i
         * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella 
         */
        public Oluttyyppi anna(int i) throws IndexOutOfBoundsException {
            if (i < 0 || tyyppiAlkiot.length <= i)
                throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
            return tyyppiAlkiot[i];
        }
        
        
        /**
         * Luetaan tiedostosta
         * @throws SailoException virhe
         */
        public void lueTiedostosta() throws SailoException {
            lueTiedostosta(getTiedostonPerusNimi());
        }
        
        
        /**
         * Lukee oluttyypit tiedostosta. 
         * @param tied tiedoston perusnimi
         * @throws SailoException jos lukeminen epaonnistuu
         * 
         * @example
         * <pre name="test">
         * #THROWS SailoException 
         * #import java.io.File;
         * 
         *  Oluttyypit oluttyypit = new Oluttyypit();
         *  Oluttyyppi koff = new Oluttyyppi(), karhu = new Oluttyyppi();
         *  koff.vastaaLager();
         *  karhu.vastaaLager();
         *  String hakemisto = "olutkanta";
         *  String tiedNimi = hakemisto+"/tyypit";
         *  File ftied = new File(tiedNimi+".dat");
         *  File dir = new File(hakemisto);
         *  dir.mkdir();
         *  ftied.delete();
         *  oluttyypit.lueTiedostosta(tiedNimi); #THROWS SailoException
         *  oluttyypit.lisaa(koff);
         *  oluttyypit.lisaa(karhu);
         *  oluttyypit.talleta();
         *  oluttyypit = new Oluttyypit();            // Poistetaan vanhat luomalla uusi
         *  oluttyypit.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
         *  Iterator<Oluttyyppi> i = oluttyypit.iterator();
         *  i.next() === koff;
         *  i.next() === karhu;
         *  i.hasNext() === false;
         *  oluttyypit.lisaa(karhu);
         *  oluttyypit.talleta();
         *  ftied.delete() === true;
         *  File fbak = new File(tiedNimi+".bak");
         *  fbak.delete() === true;
         *  dir.delete() === true;
         * </pre>
         */
        public void lueTiedostosta(String tied) throws SailoException {
            setTiedostonPerusNimi(tied);
            try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
                tyypinNimi = fi.readLine();
                if ( tyypinNimi == null ) {
                    throw new SailoException("Kannan nimi puuttuu");
                }
                String rivi = fi.readLine();
                if ( rivi == null ) {
                    throw new SailoException("Maksimikoko puuttuu");
                }

                while ( (rivi = fi.readLine()) != null ) {
                    rivi = rivi.trim();
                    if ( "".equals(rivi) || rivi.charAt(0) == ';' ) {
                    	continue;
                    }
                    Oluttyyppi oluttyyppi = new Oluttyyppi();
                    oluttyyppi.parse(rivi); // voisi olla virhekasittely
                    lisaa(oluttyyppi);
                }
                muutettu = false;
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
         * Palauttaa Kannan kokonimen
         * @return Kannan kokonimi merkkijonona
         */
        public String getKokoNimi() {
            return kokoNimi;
        }
        
        
        /**
         * Palauttaa kannan oluttyyppien lukumaaran
         * @return oluttyyppien lukumaara
         */
        public int getLkm() {
            return lkm;
        }
        
        
        /**
         * Tallentaa oluttyypit tiedostoon.  Kesken.
         * @throws SailoException jos talletus epäonnistuu
         */
        public void talleta() throws SailoException {
            if ( !muutettu ) return;

            File fbak = new File(getBakNimi());
            File ftied = new File(getTiedostonNimi());
            fbak.delete(); // if .. System.err.println("Ei voi tuhota");
            ftied.renameTo(fbak); // if .. System.err.println("Ei voi nimeta");

            try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
                fo.println(getKokoNimi());
                fo.println(tyyppiAlkiot.length);
                for (Oluttyyppi oluttyyppi : this) {
                    fo.println(oluttyyppi.toString());
                }
                Dialogs.showMessageDialog("Oluttyyppien muutosten tallennus onnistui");
                //} catch ( IOException e ) { // ei heit� poikkeusta
                //  throw new SailoException("Tallettamisessa ongelmia: " + e.getMessage());
            } catch ( FileNotFoundException ex ) {
                throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
            } catch ( IOException ex ) {
                throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
            }

            muutettu = false;        }

        
        /**
         * Luokka oluttyyppien iteroimiseksi.
         * @example
         * <pre name="test">
         * #THROWS SailoException 
         * #PACKAGEIMPORT
         * #import java.util.*;
         * 
         * Oluttyypit oluttyypit = new Oluttyypit();
         * Oluttyyppi lager = new Oluttyyppi(), ale = new Oluttyyppi();
         * lager.rekisteroi(); ale.rekisteroi();
         *
         * oluttyypit.lisaa(lager); 
         * oluttyypit.lisaa(ale); 
         * oluttyypit.lisaa(lager); 
         * 
         * StringBuffer ids = new StringBuffer(30);
         * for (Oluttyyppi oluttyyppi:oluttyypit)   // Kokeillaan for-silmukan toimintaa
         *   ids.append(" "+oluttyyppi.getTyyppiID());           
         * 
         * String tulos = " " + lager.getTyyppiID() + " " + ale.getTyyppiID() + " " + lager.getTyyppiID();
         * 
         * ids.toString() === tulos; 
         * 
         * ids = new StringBuffer(30);
         * for (Iterator<Oluttyyppi>  i=oluttyypit.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
         *   Oluttyyppi oluttyyppi = i.next();
         *   ids.append(" "+oluttyyppi.getTyyppiID());           
         * }
         * 
         * ids.toString() === tulos;
         * 
         * Iterator<Oluttyyppi>  i=oluttyypit.iterator();
         * i.next() == lager  === true;
         * i.next() == ale  === true;
         * i.next() == lager  === true;
         * 
         * i.next();  #THROWS NoSuchElementException
         *  
         * </pre>
         */
        public class OluttyypitIterator implements Iterator<Oluttyyppi> {
            private int kohdalla = 0;


            /**
             * Onko olemassa viela seuraavaa oluttyyppia
             * @see java.util.Iterator#hasNext()
             * @return true jos on viela oluttyyppeja
             */
            @Override
            public boolean hasNext() {
                return kohdalla < getLkm();
            }


            /**
             * Annetaan seuraava oluttyyppi
             * @return seuraava oluttyyppi
             * @throws NoSuchElementException jos seuraava alkiota ei enaa ole
             * @see java.util.Iterator#next()
             */
            @Override
            public Oluttyyppi next() throws NoSuchElementException {
                if ( !hasNext() ) throw new NoSuchElementException("Ei ole");
                return anna(kohdalla++);
            }

            
            /**
             * Tuhoamista ei ole toteutettu
             * @throws UnsupportedOperationException aina
             */
            @Override
            public void remove() throws UnsupportedOperationException {
                throw new UnsupportedOperationException("Me ei poisteta");
            }
        }
        
        /**
         * Palautetaan iteraattori oluttyypeistaan.
         * @return oluttyypit iteraattori
         */
        @Override
        public Iterator<Oluttyyppi> iterator() {
            return new OluttyypitIterator();
        }
        

        /** 
         * Palauttaa "taulukossa" hakuehtoon vastaavien oluttyyppien viitteet 
         * @param hakuehto hakuehto 
         * @param k etsittavan kentan indeksi  
         * @return tietorakenteen loytyneista jasenista 
         * @example 
         * <pre name="test"> 
         * #THROWS SailoException  
         *   Oluttyypit oluttyypit = new Oluttyypit(); 
         *   Oluttyyppi oluttyyppi1 = new Oluttyyppi(); oluttyyppi1.parse("1|1|Lager"); 
         *   Oluttyyppi oluttyyppi2 = new Oluttyyppi(); oluttyyppi2.parse("2|1|Lager"); 
         *   Oluttyyppi oluttyyppi3 = new Oluttyyppi(); oluttyyppi3.parse("3|1|Lager"); 
         *   Oluttyyppi oluttyyppi4 = new Oluttyyppi(); oluttyyppi4.parse("4|1|Lager"); 
         *   Oluttyyppi oluttyyppi5 = new Oluttyyppi(); oluttyyppi5.parse("5|1|Lager"); 
         *   oluttyypit.lisaa(oluttyyppi1); oluttyypit.lisaa(oluttyyppi2); oluttyypit.lisaa(oluttyyppi3); oluttyypit.lisaa(oluttyyppi4); oluttyypit.lisaa(oluttyyppi5);
         * </pre> 
         */ 
        @SuppressWarnings("unused")
        public Collection<Oluttyyppi> etsi(String hakuehto, int k) { 
            String ehto = "*"; 
            if ( hakuehto != null && hakuehto.length() > 0 ) ehto = hakuehto; 
            int hk = k; 
            if ( hk < 0 ) hk = 0; // jotta etsii id:n mukaan 
            List<Oluttyyppi> loytyneet = new ArrayList<Oluttyyppi>(); 
            for (Oluttyyppi oluttyyppi : this) { 
                if (WildChars.onkoSamat(oluttyyppi.anna(hk), ehto)) loytyneet.add(oluttyyppi);   
            } 
            Collections.sort(loytyneet, new Oluttyyppi.Vertailija(hk)); 
            return loytyneet; 
        }
        
        
        /** 
         * Etsii oluttyypin id:n perusteella 
         * @param id tunnusnumero, jonka mukaan etsitaan 
         * @return oluttyyppi jolla on etsittava id tai null 
         * <pre name="test"> 
         * #THROWS SailoException  
         * Oluttyypit lager = new Oluttyypit(); 
         * Oluttyyppi koff = new Oluttyyppi(), karjala = new Oluttyyppi(), karhu = new Oluttyyppi(); 
         * koff.rekisteroi(); karjala.rekisteroi(); karhu.rekisteroi(); 
         * int id1 = koff.getTyyppiID(); 
         * lager.lisaa(koff); lager.lisaa(karjala); lager.lisaa(karhu); 
         * lager.annaId(id1  ) == koff === true; 
         * lager.annaId(id1+1) == karjala === true; 
         * lager.annaId(id1+2) == karhu === true; 
         * </pre> 
         */ 
        public Oluttyyppi annaId(int id) { 
            for (Oluttyyppi oluttyyppi : this) { 
                if (id == oluttyyppi.getTyyppiID()) return oluttyyppi; 
            } 
            return null; 
        } 


        /** 
         * Etsii oluttyypin id:n perusteella
         * @param id tunnusnumero, jonka mukaan etsitaan 
         * @return loytyneen oluttyypin indeksi tai -1 jos ei loydy 
         * <pre name="test"> 
         * #THROWS SailoException  
         * Oluttyypit oluttyypit = new Oluttyypit(); 
         * Oluttyyppi koff = new Oluttyyppi(), karjala = new Oluttyyppi(), karhu = new Oluttyyppi(); 
         * koff.rekisteroi(); karjala.rekisteroi(); karhu.rekisteroi(); 
         * int id1 = koff.getTyyppiID(); 
         * oluttyypit.lisaa(koff); oluttyypit.lisaa(karjala); oluttyypit.lisaa(karhu); 
         * oluttyypit.etsiId(id1+1) === 1; 
         * oluttyypit.etsiId(id1+2) === 2; 
         * </pre> 
         */ 
        public int etsiId(int id) { 
            for (int i = 0; i < lkm; i++) 
                if (id == tyyppiAlkiot[i].getTyyppiID()) return i; 
            return -1; 
        } 
        
        
        /**
         * Testiohjelma oluttyypeille
         * @param args ei kaytossa
         */
        public static void main(String args[]) {
        	 Oluttyypit oluttyypit = new Oluttyypit();
             Oluttyyppi lager = new Oluttyyppi(), pils = new Oluttyyppi();
             lager.rekisteroi();
             lager.vastaaLager();
             pils.rekisteroi();
             pils.vastaaLager();

             try {
             	oluttyypit.lisaa(lager);
             	oluttyypit.lisaa(pils);

                 System.out.println("============= Oluttyypin testi =================");

                 for (int i = 0; i < oluttyypit.getLkm(); i++) {
                     Oluttyyppi oluttyyppi = oluttyypit.anna(i);
                     System.out.println("Oluttyypin nro: " + i);
                     oluttyyppi.tulosta(System.out);
                 }

             } catch (SailoException ex) {
                 System.out.println(ex.getMessage());
             }
        }


        /**
         * @return Hakee kaikki oluttyypit
         */
        public Collection<Oluttyyppi> haeKaikki() {
            Collection<Oluttyyppi> kaikkiOlutTyypit = Arrays.asList(tyyppiAlkiot);
            return kaikkiOlutTyypit;
        }
}