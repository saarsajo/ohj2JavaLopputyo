package testiJaTarkistus;

/**
 * @author Santeri Saarinen
 * @version 19.3.2019
 */
public class AlkoholProsenttiTarkistus {
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
         *    alkoholiProsTarkistusMerkki("121212-222")    === '';
         *    alkoholiProsTarkistusMerkki("121")   === '';
         *    alkoholiProsTarkistusMerkki("12")   === '12';
         *    alkoholiProsTarkistusMerkki("gsdsdgs")   === '';
         *    alkoholiProsTarkistusMerkki("0") === '0';
         *    alkoholiProsTarkistusMerkki("100") === '100';
         * </pre>
         */
        public String alkoholiProsTarkistusMerkki(String alkoholipros) {
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
            System.out.print(onkoNumero + " ei ole numero väliltä 0-100");
            return "";
        }      
    }