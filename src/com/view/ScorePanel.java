package com.view;

import com.controler.NavalControler;
import com.observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel qui affiche les informations sur la partie
 */
public class ScorePanel extends JPanel implements Observer, ActionListener {

    Fenetre mainFrame ;
    JButton btnQuit = new JButton("Quitter");
    JLabel info = new JLabel();
    JLabel info2 = new JLabel();
    private NavalControler controler ;


    public ScorePanel(Fenetre mainFrame, com.controler.NavalControler controler){
        this.mainFrame = mainFrame ;
        this.controler = controler ;
        this.setMaximumSize(new Dimension(200,500));
        this.setPreferredSize(new Dimension(200,500));
        this.setBackground(new Color(10,120,200));

        btnQuit.addActionListener(this);

        info.setForeground(Color.green);
        info2.setForeground(Color.green);

        this.add(info);
        this.add(info2);
        this.add(btnQuit);

        this.setVisible(true);
    }


    /**
     * Met à jour l'affichage en fonction des données reçues par le model
     */
    @Override
    public void update(String str) {
        if(str.startsWith("scorestats")){
            int p1sl = Integer.parseInt(str.split(" ")[1]);
            int p2sl = Integer.parseInt(str.split(" ")[2]);

            info.setText("Toi : " + p1sl + " parcelles");
            info2.setText("Adversaire : " + p2sl + " parcelles");
        }
    }


    /**
     * récupére le clic sur le bouton quitter
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource() == btnQuit){
           controler.kick();
            System.exit(0);
        }
    }

}
