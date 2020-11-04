package fxOlutKanta;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kanta.Olut;
import kanta.Olutkanta;
import kanta.Oluttyyppi;
import kanta.SailoException;

/**
 * Kysytään oluen tiedot luomalla sille uusi dialogi
 *
 * @author Santeri Saarinen
 * @version 27.4.2019
 *
 */
public class OlutDialogController implements ModalControllerInterface<Olut>,Initializable  {
    @FXML private TextField editMerkki;
    @FXML private TextField tyyppiEhto; 
    @FXML private TextField hakuehto;
    @FXML private ComboBoxChooser<Oluttyyppi> editTyyppi;
    @FXML private TextField editAlkoholi;
    @FXML private TextField editMaa;
    @FXML private TextField editArvio;   
    @FXML private TextField editLisatietoja;   
    @FXML private Label labelVirhe;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta(); 
    }
    
   
    @FXML private void handleTallenna() {
        if ( olutKohdalla != null && olutKohdalla.anna(olutKohdalla.ekaKentta()).trim().equals("") ) {
            naytaVirhe("Ei saa olla tyhj�");
            return;
        }
        try {
            tallenna();
        } catch (SailoException e) {
            e.printStackTrace();
        }
        ModalController.closeStage(getLabelVirhe());
    }
    
    
    @FXML private void handleLopeta() {
        olutKohdalla = null;
        ModalController.closeStage(getLabelVirhe());
    }

    
// ========================================================   
    private Olut olutKohdalla = new Olut();
    private Boolean onMuokattavaOlut;
    private TextField edits[];
    private Olutkanta olutkanta;
    private int kentta = 0;
    

    /**
     * Tyhjentään tekstikentät
     * @param edits tauluko jossa tyhjennettäviä tektsikenttiä
     */
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit : edits)
            if ( edit != null ) {
                edit.setText(""); 
            }
    }

    
    /**
     * Palautetaan komponentin id:sta saatava luku
     * @param obj tutkittava komponentti
     * @param oletus mika arvo jos id ei ole kunnollinen
     * @return komponentin id lukuna 
     */
    public static int getKenttaID(Object obj, int oletus) {
        if ( !( obj instanceof Node)) return oletus;
        Node node = (Node)obj;
        return Mjonot.erotaInt(node.getId().substring(1),oletus);
    }
        
        
    /**
     * Tekee tarvittavat muut alustukset.
     */
    protected void alusta() {
        
        edits = new TextField[]{editMerkki, editAlkoholi, editMaa, editArvio, editLisatietoja};
        for (TextField edit : edits) {
        	edit.setOnKeyReleased( e -> kasitteleMuutosOlueen((TextField)(e.getSource())));
        }
        
    }
   
   
    /**
     * @param edit tekstikentan muutos
     */
    protected void kasitteleMuutosOlueen(TextField edit) {
        if (olutKohdalla == null) return;
        int g = getKenttaID(edit,olutKohdalla.ekaKentta());
        String s = edit.getText();
        String virhe = null;
        virhe = olutKohdalla.aseta(g,s); 
        if (virhe == null) {
            Dialogs.setToolTipText(edit,"");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit,virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }	
	}
    
    
    private void tallenna()  throws SailoException {
        if ( olutKohdalla != null && olutKohdalla.getMerkki().trim().equals("") ) {
            naytaVirhe("Nimi ei saa olla tyhjä");
            return;
        }
        
        if (!onMuokattavaOlut) {
            Olut uusiOlut = new Olut(
                    editTyyppi.getSelectedObject().getTyyppiID(),
                    editMerkki.getText(),
                    Double.parseDouble(editAlkoholi.getText()),
                    editMaa.getText(), Double.parseDouble(editArvio.getText()),
                    editLisatietoja.getText());
            olutkanta.lisaa(uusiOlut);
        }
        else {
            olutKohdalla.setMerkki(editMerkki.toString());
            olutKohdalla.setAlkoholi(editAlkoholi.toString());
            olutKohdalla.setTyyppi(editTyyppi.toString());
            olutKohdalla.setMaa(editMaa.toString());
            olutKohdalla.setArvio(editArvio.toString());
            olutKohdalla.setLisatieto(editLisatietoja.toString());  
            olutkanta.lisaa(olutKohdalla);
        }
        
        ModalController.closeStage(getLabelVirhe());        
    }


	@Override
    public void setDefault(Olut oletus) {
        olutKohdalla = oletus;
        alusta();
        naytaOlut(edits, olutKohdalla);
    }

   
    @Override
    public Olut getResult() {
        return olutKohdalla;
    }
   
    
    private void setKentta(int kentta) {
        this.kentta = kentta;
    }
    
    
    /**
     * Mitä tehdaan kun dialogi on naytetty
     */
    @Override
    public void handleShown() {
        if (olutKohdalla != null) {
            kentta = Math.max(olutKohdalla.ekaKentta(), Math.min(kentta, olutKohdalla.getKenttia()-1));
            edits[kentta].requestFocus();    
        }
    }
   
    
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            getLabelVirhe().setText("");
            getLabelVirhe().getStyleClass().removeAll("virhe");
            return;
        }
        getLabelVirhe().setText(virhe);
        getLabelVirhe().getStyleClass().add("virhe");
    }
    
    
    /**
     * Hakee oluttyypin tiedot listaan
     */
    protected void haeKaikki() {
        Collection<Oluttyyppi> oluttyypit;
        oluttyypit = olutkanta.haeKaikki();
        for (Oluttyyppi oluttyyppi:oluttyypit) {
            if(oluttyyppi != null)
            editTyyppi.add(oluttyyppi);
        }
    }
    
    
    /**
     * Naytetaan oluen tiedot TextField komponentteihin
     * @param edits1 taulukko jossa tekstikenttia
     * @param olut naytettava olut
     */
    public void naytaOlut(TextField[] edits1, Olut olut) {
        if (olut == null) return;
        editMerkki.setText(olut.getMerkki());
        edits1[1].setText(olut.getTyyppi());
        editAlkoholi.setText(olut.getAlkoholi());
        editMaa.setText(olut.getMaa());
        editArvio.setText(olut.getArvio());
        editLisatietoja.setText(olut.getLisatietoja());
    }
    
   
    private void setOlutkanta(Olutkanta olutkanta) {
        this.olutkanta = olutkanta;

        haeKaikki();
    }
    
    
    private void setonMuokattavaOlut(Boolean onMuokattavaOlut) {
        this.onMuokattavaOlut = onMuokattavaOlut;
    }


    /**
     * @return virhelabelin
     */
    public Label getLabelVirhe() {
        return labelVirhe;
    }


    /**
     * @param labelVirhe virhe labeli
     */
    public void setLabelVirhe(Label labelVirhe) {
        this.labelVirhe = labelVirhe;
    }
    
    
    /**
     * Luodaan Oluiden kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mita dataan naytetaan oletuksena
     * @param kentta mika kentta saa fokuksen kun naytetaan
     * @param olutkanta kasiteltava olutkanta
     * @param onMuokattavaOlut onko muokattava olut
     * @return null jos painetaan Cancel, muuten täytetty tietue
     */
    public static Olut kysyOlut(Stage modalityStage, Olut oletus, int kentta, Olutkanta olutkanta, boolean onMuokattavaOlut) {
        return ModalController.<Olut, OlutDialogController>showModal(
                    OlutDialogController.class.getResource("OlutDialogView.fxml"),
                    "Olutkanta",
                    modalityStage, oletus,
                    ctrl -> { ctrl.setKentta(kentta); ctrl.setOlutkanta(olutkanta);ctrl.setonMuokattavaOlut(onMuokattavaOlut);}
                );
    }
}