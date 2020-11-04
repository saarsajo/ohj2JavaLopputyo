package fxOlutKanta;
import java.util.Optional;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;


/**
 * @author Santeri Saarinen
 * @version 12.2.2019
 *On vastuussa aloitus ikkunan toiminnasta
 */
public class OlutKantaNimiController implements ModalControllerInterface<String> {

        @FXML private TextField textVastaus;
        private String vastaus = null;
        
        
        @FXML private void handleUusiKanta() {
            TextInputDialog dialog = new TextInputDialog("Kaljakannut");
            dialog.setHeaderText(null);
            dialog.setTitle("Vastaa");
            dialog.setContentText("Uuden olutkannan nimi:");
            Optional<String> answer = dialog.showAndWait();
            System.out.println(answer.isPresent() ?
               answer.get() : "Ei ollut vastausta");
        }
        
        
        @FXML private void handleOK() {
            vastaus = textVastaus.getText();
            ModalController.closeStage(textVastaus);
        }
        
        
        @FXML private void handleCancel() {
            ModalController.closeStage(textVastaus);
        }
        
        
        @Override
        public String getResult() {
            return vastaus;
        }
        
        
        @Override
        public void setDefault(String oletus) {
            textVastaus.setText(oletus);
        }
        
        
        @Override
        public void handleShown() {
            textVastaus.requestFocus();
        }
        
        
        /**
         * Luodaan nimenkysymisdialogi ja palautetaan siihen kirjoitettu nimi tai null
         * @param modalityStage mille ollaan modaalisia, null = sovellukselle
         * @param oletus mitä nimeä näytetään oletuksena
         * @return null jos painetaan Cancel, muuten kirjoitettu nimi
         */
        public static String kysyNimi(Stage modalityStage, String oletus) {
            return ModalController.showModal(
                    OlutKantaNimiController.class.getResource("OlutKantaNimiView.fxml"),
                    "kannannimi",
                    modalityStage, oletus);
        }
    }
    