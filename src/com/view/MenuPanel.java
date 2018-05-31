package com.view;

import com.observer.Observer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Menu du jeu permettant de choisir les options de jeu (client/serveur), adresse ip, port
 */

public class MenuPanel extends JPanel implements ActionListener, Observer {

    private Fenetre mainF ;
    private ButtonGroup radioGrp ;

    private JRadioButton beServer ;
    private JRadioButton beClient ;

    private JTextField txtListenPort ;

    private JTextField txt_remoteAddr ;
    private JTextField txt_remotePort ;

    private JButton btnPlay;

    private JLabel infos = new JLabel();

    private com.controler.NavalControler controler ;

    /**
     * Construit le menu
     * @param controler controlleur
     * @param mainF fenêtre principale
     */
    public MenuPanel(com.controler.NavalControler controler, Fenetre mainF){

        this.controler = controler ;
        this.mainF = mainF ;

        JPanel jppdpanel = new JPanel();
        jppdpanel.setLayout(new BoxLayout(jppdpanel, BoxLayout.PAGE_AXIS));


        radioGrp = new ButtonGroup();

        beServer = new JRadioButton();
        beClient = new JRadioButton();;
        beServer.addActionListener(this);
        beClient.addActionListener(this);
        radioGrp.add(beServer);
        radioGrp.add(beClient);

        txtListenPort = new JTextField("6666");

        txt_remoteAddr = new JTextField("127.0.0.1");
        txt_remotePort = new JTextField("6666");

        beClient.setSelected(true);
        txt_remoteAddr.setEnabled(true);
        txt_remotePort.setEnabled(true);
        txtListenPort.setEnabled(false);


        btnPlay = new JButton("Jouer");
        btnPlay.addActionListener(this);

        jppdpanel.add(new JLabel("Jouer en tant que client "));
        jppdpanel.add(beClient);
        jppdpanel.add(txt_remoteAddr);
        jppdpanel.add(txt_remotePort);

        jppdpanel.add(new JLabel("Jouer en tant que serveur"));
        jppdpanel.add(beServer);
        jppdpanel.add(new JLabel(("Ecouter sur le port :")));
        jppdpanel.add(txtListenPort);

        jppdpanel.add(btnPlay);
        jppdpanel.add(infos);

        this.add(jppdpanel);
        this.setVisible(true);

    }

    /**
     * Récupére et envoie les options au controler
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == beServer){

            txt_remoteAddr.setEnabled(false);
            txt_remotePort.setEnabled(false);
            txtListenPort.setEnabled(true);
        }
        else if(e.getSource() == beClient){
            txt_remoteAddr.setEnabled(true);
            txt_remotePort.setEnabled(true);
            txtListenPort.setEnabled(false);
        }

        else if(e.getSource() == this.btnPlay){
            if(beServer.isSelected()){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

                try {
                    int port = Integer.parseInt(txtListenPort.getText());
                    if(controler.tryListen(port)){
                        mainF.setGameBoard();
                        mainF.getJeuPanel().init();
                        controler.receiveFirstBomb();
                    }
                    else {
                        javax.swing.JOptionPane.showMessageDialog(null,"Port occupé,  reesayez plus tard, ou changez de port");
                        infos.setText("Erreur de connexion");
                    }
                } catch(NumberFormatException ex) {
                        ex.printStackTrace();
                }
            }
            else {
                txt_remoteAddr = new JTextField("127.0.0.1");
                int port = Integer.parseInt(txt_remotePort.getText());

                if(controler.connectToServer(txt_remoteAddr.getText(), port)){
                    mainF.setGameBoard();
                    mainF.getJeuPanel().init();

                }
                else javax.swing.JOptionPane.showMessageDialog(null,
                        "Erreur de connexion, serveur occupé ou informations de connexion erronées");
            }
        }
    }

    @Override
    public void update(String str) {

    }

}
