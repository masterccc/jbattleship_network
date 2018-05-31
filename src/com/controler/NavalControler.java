package com.controler;

import com.model.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class NavalControler extends AbstractControler {

    protected NavalModel model;

    private ServerSocket _ss ;
    private Socket adversaire ;

    private PrintWriter sock_in ;
    private BufferedReader sock_out ;

    String lastResult ;
    boolean firstBlood = true ;
    boolean iAmTheServer = false ;


    public NavalControler(NavalModel model) {
        super(model);
        this.model = model;
    }

    @Override
    void control() {

    }

    /**
     * Demande au model de charger les données de jeu
     */
    public void initGame() {
        model.initGame();
    }

    /**
     * Bombarbe le joueur adverse et reçoit sa réponse
     * @param p Position du bombardement
     * @return return vrai en cas de victoire, faux sinon
     */
    public boolean bombarde(Position p){

        if (firstBlood && !iAmTheServer) {
            firstBlood = false;
            sock_in.println("BOMB " + p.getX() + " " + p.getY());
        } else {
            sock_in.println(lastResult + " BOMB " + p.getX() + " " + p.getY());
        }
        sock_in.flush();
        receiveBomb(p);

        if(checkVictory()){
            if(iAmTheServer) kick();
            return true ;
        }
        else{
            return false ;
        }
    }

    /**
     * Reçoit une bombe de l'adversaire et enregistre la réussite (MISSED/TOUCHED)
     * @param p position de la derniere bombe envoyée afin de savoir si elle a touché ou non l'adversaire
     */
    public void receiveBomb(Position p){

            String result = null ;

        try {
            result = sock_out.readLine();
            if(result == null){
                model.adQuit();
                return;
            }
                // TOUCHED BOMB 1 2
                String res = result.split(" ")[0] ;
                int x = Integer.parseInt(result.split(" ")[2]);
                int y = Integer.parseInt(result.split(" ")[3]);

                // MISSED BOMB 4 5
                if(res.startsWith("MISSED")){

                    model.missed(p);
                }
                else if(res.startsWith("TOUCHED")) {
                    model.touchedEnemy(p);
                }

                if( model.enemyHit(x,y) ) lastResult = "TOUCHED";
                else lastResult = "MISSED" ;

            } catch (IOException e) {

            kick();
                model.adQuit();
              //  e.printStackTrace();
            }


    }

    /**
     * Reçoit la première bombe dont le format est différent des autres bombes (pas de MISSED/TOUCHED)
     */
    public void receiveFirstBomb(){
        try {
            String result = sock_out.readLine();
            int x = Integer.parseInt(result.split(" ")[1]);
            int y = Integer.parseInt(result.split(" ")[2]);
            if( model.enemyHit(x,y) ) lastResult = "TOUCHED";
            else lastResult = "MISSED" ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Place le serveur en écoute et attend la connexion d'un client
     * @param port port d'écoute
     * @return Renvoie vrai un un client est connecté avec succès
     */
    public boolean tryListen(int port) {

        try {
            iAmTheServer = true ;
            _ss = new ServerSocket(port,1);
            adversaire = _ss.accept();
            sock_out = new BufferedReader(new InputStreamReader(adversaire.getInputStream()));
            sock_in = new PrintWriter(adversaire.getOutputStream());

            String rcv = sock_out.readLine();

            if( rcv.equals("PLAY")){
                sock_in.println("GO");
                sock_in.flush();
                return true ;
            }
            else {
                sock_in.println("Protocole inconnu - deconnexion");
                kick();
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false ;
        }
    }

    /**
     * Termine la connexion avec l'adversaire
     */
    public void kick(){
        sock_in.close();
        try {
            sock_out.close();
            adversaire.close();
        } catch (IOException e) {}

    }

    /**
     * Permet de connecter le client au serveur
     * @param rAddr Adresse ip du serveur
     * @param port port de connexion du serveur
     * @return retourne vrai si le client s'est connecté avec succès
     */
    public boolean connectToServer(String rAddr, int port) {
        IpValidator ipv = new IpValidator();
        if( ipv.validate(rAddr) && port > 1024 && port < 65565){
            try {
                adversaire = new Socket(rAddr, port);
                sock_out = new BufferedReader(new InputStreamReader(adversaire.getInputStream()));
                sock_in = new PrintWriter(adversaire.getOutputStream());

                sock_in.println("PLAY");
                sock_in.flush();

                String rcv = sock_out.readLine();

                if( rcv.equals("GO")){
                    return true ;
                }
                else {
                    sock_in.println("You are not a battleship server !");
                    sock_out.close();
                    sock_in.close();
                    adversaire.close();
                    return false;
                }

            } catch (IOException e) {
                System.err.println("Information de connexion erronées");
            }
        }
        return false ;
    }

    /**
     * Vérifie si un joueur a gagné la partie
     * @return retourne vrai en cas de victoire
     */
    public boolean checkVictory(){
        for( Player p : model.getPlayers()){
            if(p.getsLeft()<=0){
                kick();
                model.notifyEnd();
                return true ;
            }
        }
        return false ;
    }
}