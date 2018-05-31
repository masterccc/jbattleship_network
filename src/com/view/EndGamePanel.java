package com.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panneau d'affichage de fin de jeu
 */

public class EndGamePanel extends JPanel implements ActionListener {

    JLabel txt = new JLabel("Vous avez perdu ! Fin de la partie !");
    JButton btnQuit = new JButton("Quitter");

    public EndGamePanel(){
        this.setVisible(true);
        this.add(txt);
        btnQuit.addActionListener(this);
        this.add(btnQuit);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnQuit){
            System.exit(0);
        }
    }

    /**
     * Choisit l'affichage du panneau, en fonction de la victoire ou de la defaite du joueur
     * @param msg chaie de caractere contenant le resultat de la partie
     */
    public void setMsg(String msg) {

        if(msg.equals("win")){
            txt.setText("Vous avez gagné");
        }
        else {
            txt.setText("Vous avez perdu");
        }
    }
}
