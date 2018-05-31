package com.model;

/**
 * Représente un joueur et le nombre de parcelles de bateau qu'il lui reste
 */
public class Player {


    private int sLeft ;

    public Player(){
        sLeft = 17 ;
    }

    /**
     * Récupérer le nombre de parcelles restantes
     * @return le nombre de parcelles
     */
   public int getsLeft(){
       return sLeft;
   }

   public void decSLeft(){
       sLeft--;
   }

}
