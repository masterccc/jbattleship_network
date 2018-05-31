package com.view;

import com.controler.NavalControler;
import com.observer.Observer;

import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre dans laquelle s'ouvre le jeu
 */
public class Fenetre extends JFrame implements Observer {


    JPanel win = new JPanel();


    private MenuPanel menu ;
    private JeuPanel jp;
    private ScorePanel sc ;
    private EndGamePanel gep ;

    private NavalControler controler ;

    public Fenetre(com.controler.NavalControler controler){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(1000,450);
        this.setResizable(false);
        this.controler = controler;

        menu= new MenuPanel(controler, this);
        jp = new JeuPanel(controler);
        sc = new ScorePanel(this, controler);
        gep = new EndGamePanel();

        this.setBackground(new Color(10,120,200));

        win.setLayout(new BoxLayout(win, BoxLayout.LINE_AXIS));
        win.add(jp);
        win.add(sc);

        this.add(menu);
        this.setVisible(true);
    }

    public void update(String str) {
        if(str.equals("win")){

            setEnd("win");
            controler.kick();
        }
        else if(str.equals("lose")){

            setEnd("lose");
            controler.kick();
        }
        else if(str.equals("ragequit")){
            javax.swing.JOptionPane.showMessageDialog(null,"Vous avez gagné!");
            controler.kick();
        }
    }



    /**
     * Passe du menu au plateau de jeu
     */
    public void setGameBoard(){

        this.setContentPane(win);
        this.revalidate();
    }

    public void setEnd(String str){

       gep.setMsg(str);
        this.setContentPane(gep);
        this.revalidate();
    }

    /**
     * Retourne le JPanel contenant le score
     * @return ScorePanel
     */
    public ScorePanel getScorePanel(){ return sc; }


    /**
     * Retourne le plateau de jeu
     * @return JeuPanel
     */
    public JeuPanel getJeuPanel(){
        return jp;
    }

    /**
     * Retourne le Jpanel du menu
     * @return JPanel
     */
    public MenuPanel getMenu(){
        return menu ;
    }

    /**
     * Affiche le menu
     */
    public void displayMenu(){
        this.setContentPane(menu);
        this.revalidate();
    }

}
