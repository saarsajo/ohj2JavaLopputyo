package fxOlutKanta;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI; 
import java.net.URISyntaxException; 
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import fxOlutKanta.OlutKantaGUIController;
import javafx.application.Platform;
import javafx.fxml.FXML; 
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import kanta.Olut;
import kanta.Olutkanta;
import kanta.Oluttyyppi;
import kanta.Otsikko;
import kanta.SailoException;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

/**
 * @author Santeri Saarinen
 * @version 9.1.2019
 *Luokka käyttöliittymän tapahtumien hoitoon
 */
public class OlutKantaGUIController implements Initializable { 
    @FXML private TextField hakuehto; 
    @FXML private Label labelVirhe; 
    ///@FXML private ScrollPane panelOluttyyppi;
    @FXML private ListChooser<Oluttyyppi> chooserOluttyypit;
    @FXML private ComboBoxChooser<String> cbKentat;
    @FXML private StringGrid<Olut> tableOluet;
    
    @FXML private TextField editMerkki;
    @FXML private TextField tyyppiEhto; 
    @FXML private TextField editAlkoholi;
    @FXML private TextField editMaa;
    @FXML private TextField editArvio;   
    @FXML private TextField editLisatietoja;   

    private String kannannimi = "olutkanta";

///LUENTO 21 JÄI KESKEN
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }


    @FXML private void handleHakuehto() {
        if ( tyyppiKohdalla != null ) {
            hae(tyyppiKohdalla.getTyyppiID());
        }
    }
    
    
    @FXML private void handleTallenna() {
        tallennaKanta();
    }
    
    
    @FXML private void handleAvaa() {
        avaaKanta();
        tallennaKanta();
    }
    
            
    @FXML private void handleLopeta () {
        tallennaKanta();
        Platform.exit();
    } 
        
        
    @FXML private void handleUusiKanta() {
        uusiKanta();
    }


    @FXML private void handleUusiOlut() throws SailoException {
		uusiOlut();
    }
    
    
    @FXML private void handleMuokkaaOlut() {
        muokkaaOlut();
    }
    
    
    @FXML private void handleUusiOluttyyppi() {
        uusiOluttyyppi();  
    }


    @FXML private void handlePoistaOlut() {
        poistaOlut();
    }
    

    @FXML private void handlePoistaOluttyyppi() {
        poistaOluttyyppi();
    }
    
    
    @FXML private void handleApua() {
        apua();
    }

private Olutkanta olutkanta;
    private Oluttyyppi tyyppiKohdalla;
    private Olut olutKohdalla = new Olut(); ;
    private TextArea areaTyyppi = new TextArea();
    private static Oluttyyppi apuOluttyyppi = new Oluttyyppi(); 
    //private static Olut apuOlut = new Olut(); 
    
    /**
     * Tekee loput alustukset 
     */
    protected void alusta() {
        chooserOluttyypit.clear();
        chooserOluttyypit.addSelectionListener(e -> naytaOluttyyppi());

        int eka = olutKohdalla.ekaKentta(); 
        int lkm = olutKohdalla.getKenttia(); 
        String[] headings = new String[lkm-eka]; 
        for (int i=0, k=eka; k<lkm; i++, k++) headings[i] = olutKohdalla.getKysymys(k); 
        tableOluet.initTable(headings);
        tableOluet.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
        tableOluet.setEditable(false); 
        tableOluet.setPlaceholder(new Label("Ei viela oluita")); 
         
        tableOluet.setColumnSortOrderNumber(1); 
        tableOluet.setColumnSortOrderNumber(2); 
        tableOluet.setColumnWidth(1, 60); 
        
        tableOluet.setOnMouseClicked( e -> { if ( e.getClickCount() > 1 ) muokkaaOlut(); } );
        tableOluet.setOnKeyPressed( e -> {if ( e.getCode() == KeyCode.F2 ) muokkaaOlut();}); 
    }
    
    
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    

    private void setTitle(String title) {
        ModalController.getStage(hakuehto).setTitle(title);
    }
    

    /**
     * @param olutkanta Olutkanta jota kaytetaan tassa kayttoliittymassa
     */
    public void setOlutkanta(Olutkanta olutkanta) {
        this.olutkanta = olutkanta;
        naytaOluttyyppi();
    }
    
    
    /**
     * Nayttaa listasta valitun oluttyypin 
     */
    private void naytaOluttyyppi() {
        
        tyyppiKohdalla = chooserOluttyypit.getSelectedObject();
        if (tyyppiKohdalla == null) return;
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaTyyppi)){
            tulosta(os, tyyppiKohdalla);
            }
        naytaOluet(tyyppiKohdalla);
    }
    
    
    private void naytaOluet(Oluttyyppi oluttyyppi) {
        tableOluet.clear();
        if ( oluttyyppi == null ) return;
        
        List<Olut> oluet = olutkanta.annaOluet(oluttyyppi);
        if ( oluet.size() == 0 ) return;
        for (Olut olu: oluet)
            naytaOlut(olu); 
    }

    
    private void naytaOlut(Olut olut) {
        int kenttia = olut.getKenttia(); 
        String[] rivi = new String[kenttia-olut.ekaKentta()]; 
        for (int i=0, k=olut.ekaKentta(); k < kenttia; i++, k++) 
            rivi[i] = olut.anna(k); 
        tableOluet.add(olut,rivi);
    }

    
    
     /**
      * Alustaa olutkannan lukemalla sen valitun nimisestä tiedostosta
      * @param nimi tiedosto josta kannan tiedot luetaan
     * @return virhe tai null
      */
     protected String lueTiedosto(String nimi) { 
         kannannimi = nimi;
         setTitle("Olutkanta - " + kannannimi);
         try { 
             olutkanta.lueTiedostosta(nimi); 
             hae(0); 
             return null; 
         } catch (SailoException e) { 
             hae(0); 
             String virhe = e.getMessage();  
             if ( virhe != null ) naytaVirhe(String.format("Virhe tiedostoa lukiessa"));
             return virhe; 
         } 
      } 

     
     /**
      * Luodaan uusi olutkanta
      */
     private void uusiKanta() {
         TextInputDialog dialog = new TextInputDialog("Kaljakannut");
         dialog.setHeaderText(null);
         dialog.setTitle("Vastaa");
         dialog.setContentText("Uuden olutkannan nimi:");
         Optional<String> answer = dialog.showAndWait();
         System.out.println(answer.isPresent() ?
            answer.get() : "Ei ollut vastausta");
     }
     
    
     /**
      * Kysytään tiedoston nimi ja luetaan se
      * @return true jos onnistui, false jos ei
      */
     public boolean avaaKanta() {
         String uusinimi = OlutKantaNimiController.kysyNimi(null, kannannimi);
         if (uusinimi == null) return false;
         lueTiedosto(uusinimi);
         return true;
     }      
     
     
     /**
      * Tietojen tallennus
      */
     private String tallennaKanta() {
        try { 
            olutkanta.talleta(); 
            return null; 
        } 
        catch (SailoException ex) { 
            naytaVirhe(String.format("Virhe kantaa tallentaessa"));
            return ex.getMessage(); 
        }
     }     
     
     
    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkea sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallennaKanta();
        return true;
    }
     
     
     /**
      * Luo uuden oluttyypin
      */
     public void uusiOluttyyppi() {
         Oluttyyppi uusiOluttyyppi = new Oluttyyppi();
         uusiOluttyyppi.rekisteroi();

         TextInputDialog dialog = new TextInputDialog("Oluttyyppi");
         dialog.setHeaderText(null);
         dialog.setTitle("Uusi oluttyyppi");
         dialog.setContentText("Uuden oluttyypin nimi:");
         String uudenOluttyypinNimi = dialog.showAndWait().orElse("n/a");
         
         dialog.setHeaderText(null);
         dialog.setTitle("Vastaa 2/2");
         dialog.setContentText("Uuden oluttyypin yläotsikko:");
         uusiOluttyyppi.vastaaTyyppi(uudenOluttyypinNimi);
         
         Otsikko uusiOtsikko = new Otsikko();
         uusiOtsikko.rekisteroi();

         
         try {
             olutkanta.lisaa(uusiOluttyyppi);
         } catch (SailoException e) {
             naytaVirhe(String.format("Virhe oluttyyppia luodessa tallentaessa"));
         }
          hae(uusiOluttyyppi.getTyyppiID());   
     }
     
     /*
      * Poistetaan listalta valittu j�sen
      */
     private void poistaOluttyyppi() {
         Oluttyyppi poistettavaTyyppi = tyyppiKohdalla;
         if ( poistettavaTyyppi == null ) return;
         if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko j�sen: " + poistettavaTyyppi.getTyyppi(), "Kyll�", "Ei") )
             return;
         olutkanta.poista(poistettavaTyyppi);
         int index = chooserOluttyypit.getSelectedIndex();
         hae(0);
         chooserOluttyypit.setSelectedIndex(index);
     }
     
     
     /**
      * Luo uuden oluen
     * @throws SailoException virhe
      */
     public void uusiOlut() throws SailoException { 	 
         if ( tyyppiKohdalla == null ) {
             naytaVirhe(String.format("Uuden oluen luominen ei onnistunut"));
             return; 
         }
         Olut uusiOlut = OlutDialogController.kysyOlut(null, olutKohdalla, 1, olutkanta, false);   
         if (uusiOlut == null) {
             return;
         }
         uusiOlut.rekisteroi();
         naytaOluet(tyyppiKohdalla); 
         tableOluet.selectRow(1000);
     }
     
     
     private void muokkaaOlut() {
         
         /* if ( tyyppiKohdalla == null ) return; 
         naytaOluet(tyyppiKohdalla); 

         
         olutKohdalla = OlutDialogController.kysyOlut(null, olutKohdalla, 1, olutkanta, true);
         
         ///TÄSSÄ KOHTAA TULEE HAKEA OLUTKOHDALLA KATSO MALLIA MITEN HAETAAN OLUTTYYPI KOHDALTA
 
         tableOluet.selectRow(1000);*/

         
         /*
         TÄHÄN MENNESSÄ TOIMIVIN!!!!!!!!!!!
         
         int r = tableOluet.getRowNr();
         if ( r < 0 ) return; // klikattu ehka otsikkorivi�
         olutKohdalla = tableOluet.getObject(r);
         if ( olutKohdalla == null ) {
             return;
         }
         System.out.println("Olutkohdalla sisältää : " + olutKohdalla);
         int j = tableOluet.getColumnNr()+olutKohdalla.ekaKentta();
         try {
             OlutDialogController.kysyOlut(null, olutKohdalla.clone(), j, olutkanta, true);
             if ( olutKohdalla == null ) return;
             olutkanta.korvaaTaiLisaa(olutKohdalla); 
             naytaOluet(tyyppiKohdalla); 
             tableOluet.selectRow(r);  // j�rjestet��n sama rivi takaisin valituksi
         } catch (CloneNotSupportedException  e) { // clone on tehty   
         } catch (SailoException e) {
             Dialogs.showMessageDialog("Ongelmia lisaamisessa: " + e.getMessage());
         }
         */
         
         int r = tableOluet.getRowNr();
         if ( r < 0 ) return; // klikattu ehka otsikkorivi�
         olutKohdalla = tableOluet.getObject(r);
         if ( olutKohdalla == null ) {
             return;
         }
         labelVirhe.setText("Olut sisältää :" + olutKohdalla + ", mutta ei jostain syystä saa vielä tietoja dialogcontrolleriin");
         int j = tableOluet.getColumnNr()+olutKohdalla.ekaKentta();
         try {    ///TÄMÄ EI KOSKAAN ONNISTU MISSA VIKA?????????????????????????????????????????????
             Olut muokattavaOlut = olutKohdalla.clone();
             OlutDialogController.kysyOlut(null, muokattavaOlut.clone(), j, olutkanta, true);
             System.out.println("Olutkohdalla 2 sisältää : " + muokattavaOlut);
             if ( olutKohdalla == null ) {
                 return;
             }
             olutkanta.korvaaTaiLisaa(olutKohdalla); 
             naytaOluet(tyyppiKohdalla); 
             tableOluet.selectRow(r);  // j�rjestet��n sama rivi takaisin valituksi
         } catch (CloneNotSupportedException  e) { // clone on tehty   
         } catch (SailoException e) {
             labelVirhe.setText("Oluen muokkaamisessa ongelma");
         }
     }   
     
     
     /**
      * Poistetaan olut valitulta kohdalta. 
      */
     private void poistaOlut() {
         int rivi = tableOluet.getRowNr();
         if ( rivi < 0 ) return;
         Olut poistettavaOlut = tableOluet.getObject();
         if ( poistettavaOlut == null ) return;
         olutkanta.poistaOlut(poistettavaOlut);
         naytaOluet(tyyppiKohdalla);
         int harrastuksia = tableOluet.getItems().size(); 
         if ( rivi >= harrastuksia ) rivi = harrastuksia -1;
         tableOluet.getFocusModel().focus(rivi);
         tableOluet.getSelectionModel().select(rivi);
     }

    
    /**
     * Hakee oluttyypin tiedot listaan
     * @param tyyppinro tyypin numero, joka aktivoidaan haun jälkeen
     */
    protected void hae(int tyyppinro) {
        int tyypID = tyyppinro; // tyyppinro tyyppi ID, joka aktivoidaan haun jalkeen 
        if ( tyypID <= 0 ) { 
            Oluttyyppi kohdalla = tyyppiKohdalla; 
            if ( kohdalla != null ) tyypID = kohdalla.getTyyppiID(); 
        }
        
        int k = cbKentat.getSelectionModel().getSelectedIndex() + apuOluttyyppi.ekaKentta(); 
        String ehto = hakuehto.getText(); 
        if (ehto.indexOf('*') < 0) ehto = "*" + ehto + "*"; 
        
        chooserOluttyypit.clear();

        int index = 0;
        Collection<Oluttyyppi> oluttyypit;
        try {
            oluttyypit = olutkanta.etsi(ehto, k);
            int i = 0;
            for (Oluttyyppi oluttyyppi:oluttyypit) {
                if (oluttyyppi.getTyyppiID() == tyypID) index = i;
                chooserOluttyypit.add(oluttyyppi.getTyyppi(), oluttyyppi);
                i++;
            }
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Oluttyypin hakemisessa ongelmia! " + ex.getMessage());
        }
        chooserOluttyypit.setSelectedIndex(index); // tasta tulee muutosviesti joka nayttaa tyypin
    }
    
    
	 /**
     * Opastetaan paihdeavustuksen sivuille
     */
    private void apua() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://paihdelinkki.fi/mist%C3%A4-apua");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }
	
	
    /** Tulostetaan oluet ja tyyppi
     * @param os tietovirta johon tulostetaan
     * @param oluttyyppi oluen tyyppi
     */
    public void tulosta(PrintStream os, final Oluttyyppi oluttyyppi) {
        os.println("----------------------------------------------");
        oluttyyppi.tulosta(os);
        os.println("----------------------------------------------");
        List<Olut> oluet = olutkanta.annaOluet(oluttyyppi);   
        for (Olut olut:oluet) {
            olut.tulosta(os);  
        }
    }
    
    
    /**
     * Tulostaa listassa olevat oluttyypit tekstialueeseen
     * @param text alue johon tulostetaan
     */
    public void tulostaValitut(TextArea text) {
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
            os.println("Tulostetaan kaikki oluttyypit");
            Collection<Oluttyyppi> oluttyypit = olutkanta.etsi("", -1); 
            for (Oluttyyppi oluttyyppi:oluttyypit) { 
                tulosta(os, oluttyyppi);
                os.println("\n\n");
            }
        } catch (SailoException ex) { 
            Dialogs.showMessageDialog("Oluttyypin hakemisessa ongelmia! " + ex.getMessage()); 
        }
    }
}