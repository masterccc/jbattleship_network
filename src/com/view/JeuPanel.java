package com.view;

import com.controler.NavalControler;
import com.observer.Observer;
import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Plateau de jeu
 */
public class JeuPanel extends JPanel implements Observer{


    private NavalControler controler ;
    private Vector<Terrain> terrains = new Vector<Terrain>();

    public JeuPanel(com.controler.NavalControler controler){
        this.controler = controler ;
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        terrains.add(new Terrain(controler, false));
        terrains.add(new Terrain(controler, true));

        JPanel sep = new JPanel();
        sep.setMaximumSize(new Dimension(30,400));
        sep.setSize(30,400);
        this.add(terrains.get(0));
        this.add(sep);
        this.add(terrains.get(1));

        this.setBackground(new Color(10,120,200));
       this.setVisible(true);
    }

    @Override
    public void update(String str) {
        // reception bombe sur notre bateau
        if(str.startsWith("update ")){
            String side = str.split(" ")[1];
            int ligne = Integer.parseInt(str.split(" ")[2]);
            int col = Integer.parseInt(str.split(" ")[3]);
            SecteurState state = SecteurState.valueOf(str.split(" ")[4]);
            if(side.equals("left")) terrains.get(0).updateSecteur(ligne, col, state);
            else if(side.equals("right")) terrains.get(1).updateSecteur(ligne, col, state);
        }
    }

    /**
     * Demande au controleur de démarrer la partie
     */
    public void init(){
        controler.initGame();
    }

}


