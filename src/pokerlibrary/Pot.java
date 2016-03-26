/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerlibrary;

import java.util.ArrayList;
import java.util.List;


/** Υλοποιεί την έννοια ενός Pot του παιχνιδιου Πόκερ
 *
 * @author Αθανάσιος Ροίδης
 * @version  1.13.1
 */
public class Pot {
    
    /**
     * Τα λεφτά που περιέχει το Pot
     */
    private int amount;
    
    /**
     * Οι παίκτες που δικαιούνται μερίδιο απο αυτο το Pot
     */
    private List<Player> potPlayers;
    

    
    /**
     *
     */
    public Pot()
    {
        amount = 0;
        potPlayers = new ArrayList<>();
    }
    
    /**
     * Προσθέτει έναν παίκτη στους παίκτες που δικαιούνται μερίδιο απο το Pot
     * @param player Ο παίκτης που προστίθεται
     */
    public void addPlayer(Player player)
    {
        potPlayers.add(player);
    }
    
    /**
     * Προσθέτει στο ποσό του Pot ενα ακόμα ποσό
     * @param x το ποσό που ποστίθεται
     */
    public void addToAmount(int x){
        amount += x;
    }
    
    /**
     *
     * @return Το ποσό του Pot
     */
    public int getPotAmount(){
        return amount;
    }
    
    
    /**
     * Μοιράζει το ποσό του Pot σε όλους τους παίκτες με βάση την αξία του χεριού τους
     * @see Player
     * @see Hand
     */
    public void distribute()
    {
        double bestHandValue = 0;
        for(Player player:potPlayers){
            if(!player.isFolded()){
                if(player.getHandValue() > bestHandValue){
                     bestHandValue = player.getHandValue(); 
                }
            }
        }
        int counter = 0;
        for(Player player:potPlayers){
            if(!player.isFolded()){
                if(player.getHandValue() == bestHandValue){
                     counter++; 
                }
            }
        }
        for(Player player:potPlayers){
            if(!player.isFolded()){
                if(player.getHandValue() == bestHandValue){
                     player.setStack(player.getStack() + amount/counter); 
                }
            }
        }
    }

    /**Ελέγχει το αν ένας παίκτης δικαιούται μερίδιο απο το Pot
     * @param player Ο παίκτης που ελεγχεται
     * @return Το αν ο παίκτης δικαιούται μερίδιο απο το Pot
     */
    public boolean contains(Player player) {
        return potPlayers.contains(player);
    }
    
    /**
     *
     * @return Τον αριθμό τον παικτών που δικαιούνται λεφτά απο το Pot
     */
    public int getNumberOfPlayers(){
        return potPlayers.size();
    }
   
}
