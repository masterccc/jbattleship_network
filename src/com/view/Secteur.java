package com.view;

import com.controler.NavalControler;
import com.model.Position;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Représente un carré de jeu sur lequel se trouve une partie de bateau
 */
public class Secteur extends JButton implements ActionListener {

    private NavalControler controler;
    private SecteurState etat ;
    private Position pos = new Position() ;
    private BufferedImage feu ;
    private BufferedImage splash ;

    public Secteur(Position pos, NavalControler controler){
        this.controler = controler;
        this.pos.setY(pos.getY());
        this.pos.setX(pos.getX());
        this.setOpaque(false);
        this.setSize(120,120);
        this.setBackground(new Color(10,120,200));
        this.etat = SecteurState.FREE ;
        this.addActionListener(this);
        try {
            URL url = Secteur.class.getResource("/ressources/flamme.png");
            feu = ImageIO.read(url);
            url = Secteur.class.getResource("/ressources/splash.png");
            splash = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Paint la case selon son état (touched, missed, bateau)
     * @param g
     */
    public void paintComponent(Graphics g){
            super.paintComponent(g);
            if(etat == SecteurState.FREE){
                return ;
            }
            else if(etat == SecteurState.FRIEND_SHIP){
                g.setColor(Color.GREEN);
                g.fillRect(2,2,this.getWidth()-2, this.getHeight()-2);
            }
            else if(etat == SecteurState.MISSED){
                g.drawImage(splash,0,0, 40,40,null);
            }
            else if(etat == SecteurState.BOMBARDED_FRIEND){
                    g.drawImage(feu,0,0, 40,40,null);
            }
            else if(etat == SecteurState.BOMBARBED_ENEMY){
                    g.drawImage(feu,0,0, 40,40,null);
            }
    }



    public void setState(SecteurState state) {
        this.etat = state ;
        this.setEnabled(false);
        this.repaint();
    }

    public Position getPos() {
        return pos;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof Secteur){
           controler.bombarde( this.getPos());
        }
    }
}
