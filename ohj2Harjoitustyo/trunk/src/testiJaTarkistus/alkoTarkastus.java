package testiJaTarkistus;

/**
 * @author Santeri Saarinen
 * @version 19.3.2019
 */
public class alkoTarkastus {
        /** Alkoholiprosenttiin kelpaavat tarkistusmerkit järjestyksessä */
        //                                            0123456789012345678901234567890
        public static final String TARKISTUSMERKIT = "0123456789";
        double num;

        
        /**
         * Testaa onko alkoholiprosentti numero 0 ja 100 valilta
         * @param alkoholipros tutkittava syote
         * @return hetun tarkistusmerkki
         * @example
         * <pre name="test">
         * alkoTarkastus tarkastus = new alkoTarkastus();
         *    tarkastus.alkoTark("121212-222")    === "Ei ole numero väliltä 0-100";
         *    tarkastus.alkoTark("121")   === "Ei ole numero väliltä 0-100";
         *    tarkastus.alkoTark("5")   === "5";
         *    tarkastus.alkoTark("gsdsdgs")   === "Ei ole numero väliltä 0-100";
         *    tarkastus.alkoTark("0") === "0";
         *    tarkastus.alkoTark("100") === "100";
         * </pre>
         */
        public String alkoTark(String alkoholipros) {
            boolean onkoNumero = true;
            try {
                num = Double.parseDouble(alkoholipros);
            } catch (NumberFormatException e) {
                onkoNumero = false;
            }

            if (onkoNumero == true) {
                if(num >= 0 && num <= 100) {
                    return alkoholipros;
                }
            }   
            return "Ei ole numero väliltä 0-100";
        }      
    }