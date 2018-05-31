import com.controler.NavalControler;
import com.model.NavalModel;
import com.view.Fenetre;

/**
 * Classe principale
 */
public class Main {

    public static void main(String[] args){

        NavalModel model = new NavalModel();
        NavalControler controler = new NavalControler(model);
        Fenetre vue = new Fenetre(controler);
        model.addObserver(vue);
        model.addObserver(vue.getJeuPanel());
        model.addObserver(vue.getScorePanel());
        model.addObserver(vue.getMenu());
    }
}
