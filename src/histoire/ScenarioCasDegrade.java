package histoire;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
    public static void main(String[] args) {
        Etal etal = new Etal();
        etal.libererEtal();
        etal.acheterProduit(5, null);
        System.out.println("Fin du test");
    }
}
