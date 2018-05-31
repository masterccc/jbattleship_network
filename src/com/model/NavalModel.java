package com.model;
import com.view.SecteurState;

import java.util.Random;
import java.util.Vector;

/**
 * Model
 */
public class NavalModel extends AbstractModel  {


    SecteurState grilleGauche[][] = new SecteurState[10][10];
    SecteurState grilleDroite[][] = new SecteurState[10][10];

    Vector<Player> players = new Vector<Player>();

    private int[] bateaux = new int[] { 5,4,3,3,2};

    Random rand = new Random();

    public NavalModel() {
        super();
        for(int i = 0 ; i < 2 ; i++ ) players.add(new Player());
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grilleGauche[i][j] = SecteurState.FREE ;
                grilleDroite[i][j] = SecteurState.FREE ;
                notifyObserver("update left " + i + " " + j + " " + grilleGauche[i][j].toString());
                notifyObserver("update right " + i + " " + j + " " + SecteurState.FREE);
            }
        }
    }

    /**
     * Initialise les données du jeu (les bateaux donc)
     */
    public void initGame() {
        placeBateaux();
    }

    /**
     * Signale que notre bombe a loupé l'ennemi, notifie la vue
     * @param p position de la bombe
     */
    public void missed(Position p) {
        grilleDroite[p.getY()][p.getX()] = SecteurState.MISSED ;
        notifyObserver("update right " + p.getY() + " "+ p.getX() +" " + grilleDroite[p.getY()][p.getX()].toString());
    }

    /**
     * L'ennemi  a été touché, notifie la vue
     * @param p Position de la bombe qui a touché l'ennemi
     */
    public void touchedEnemy(Position p) {
        players.get(1).decSLeft();
        notifyScore();
        grilleDroite[p.getY()][p.getX()] = SecteurState.BOMBARBED_ENEMY ;
        notifyObserver("update right " + p.getY() + " "+ p.getX() +" " + grilleDroite[p.getY()][p.getX()].toString());
        if(players.get(1).getsLeft() <= 0) notifyObserver("win");
    }


    /**
     * L'ennemi nous attaque
     * @param x position de la bombe (abcisse)
     * @param y position de la bombe (ordonnée)
     * @return renvoie vrai si l'enemi nous a touché
     */
    public boolean enemyHit(int x, int y) {

        boolean touched = false ;

        if( grilleGauche[y][x] == SecteurState.FRIEND_SHIP){
            grilleGauche[y][x] = SecteurState.BOMBARDED_FRIEND ;
            touched = true ;
            players.get(0).decSLeft();
            notifyScore();
        }
        else if( grilleGauche[y][x] == SecteurState.FREE){
            grilleGauche[y][x] = SecteurState.MISSED ;
        }

        notifyObserver("update left " + y + " "+ x +" " + grilleGauche[y][x].toString());
        if(players.get(0).getsLeft() <= 0) notifyObserver("lose");
        return touched ;
    }

    /**
     * Met à jour les scores dans la vue
     */
    public void notifyScore(){
        notifyObserver("scorestats "+ players.get(0).getsLeft() + " " + players.get(1).getsLeft());
    }

    /**
     * Renvoie la liste des joueurs
     * @return liste des joueurs
     */
    public Vector<Player> getPlayers(){
        return players ;
    }


    /**
     * Place les bateaux aléatoirement sur le terrain
     */
    public void placeBateaux(){
            for(int bateau : bateaux){

                boolean col = (rand.nextInt(10) % 2) == 0 ;
                if(col){
                    int colonne = rand.nextInt(9);
                    int ligne = rand.nextInt(9 - bateau);
                    boolean placed = false ;
                    do {
                        boolean canPlace = true;
                        for(int i = ligne; i <  (ligne + bateau);i++ ){
                            if(grilleGauche[i][colonne] != SecteurState.FREE){
                                colonne = ( colonne + 1 ) % 10 ;
                                canPlace = false ;
                                break;
                            }
                        }
                        if(canPlace) {
                            for (int i = ligne; i < (ligne + bateau); i++) {
                                grilleGauche[i][colonne] = SecteurState.FRIEND_SHIP;
                                notifyObserver("update left " + i + " " + colonne + " " + grilleGauche[i][colonne].toString());
                                placed = true;
                            }
                        }

                    } while(!placed);
                }
                if(!col){
                    int colonne = rand.nextInt(9 - bateau);
                    int ligne = rand.nextInt(9 );
                    boolean placed = false ;

                    do {
                        boolean canPlace = true;
                        for(int i = colonne; i <  (colonne + bateau);i++ ){
                            if(grilleGauche[ligne][i] != SecteurState.FREE){
                                ligne = ( ligne + 1 ) % 10 ;
                                canPlace = false ;
                                break;
                            }
                        }
                        if(canPlace) {
                            for (int i = colonne; i < (colonne + bateau); i++) {
                                grilleGauche[ligne][i] = SecteurState.FRIEND_SHIP;
                                notifyObserver("update left " + ligne + " " + i + " " + grilleGauche[ligne][i].toString());
                                placed = true;
                            }
                        }
                    } while(!placed);

                }
            }
    }

    /**
     * Signale la fin de jeu à la vue
     */
    public void notifyEnd() {
        notifyObserver("end");
    }

    /**
     * Signale à la vue qu'un joueur a quitté la partie
     */
    public void adQuit() {
        notifyObserver("ragequit");
    }
}
