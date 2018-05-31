package com.view;

import com.controler.NavalControler;
import com.model.Position;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Représente le terrain de jeu sur lequel se trouvent les bateaux
 */
public class Terrain extends JPanel{

    private Secteur[][] grille = new Secteur[10][10];
    private NavalControler controler ;
    BufferedImage bakcground ;

    public Terrain(NavalControler controler, boolean clic){
        this.setBackground(new Color(10,120,200));
        this.controler = controler;
        this.setSize(400,400);
        this.setMaximumSize(new Dimension(400,400));
        this.setLayout(new GridLayout(10,10));
        for(int i = 0 ; i < 10 ; i++){
            for(int j = 0 ; j < 10 ; j++){
                Secteur s = new Secteur(new Position(j,i), controler);
                if(!clic) s.setEnabled(false);
                this.add(s);
                grille[i][j] = s ;
            }
        }
        try {
            URL url = Secteur.class.getResource("/ressources/terrain_bg.png");
            bakcground = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setVisible(true);
    }

    /**
     * Met à jour une case de la grille de jeu
     * @param ligne ligne à modifier
     * @param col colonne à modifier
     * @param etat nouvel état de la case
     */
    public void updateSecteur(int ligne, int col, SecteurState etat) {
        grille[ligne][col].setState(etat);
    }

    /**
     * Paint l'arrière plan des grilles de jeu
     * @param g
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
            g.drawImage(bakcground,0,0, 400,400,null);
    }

}
