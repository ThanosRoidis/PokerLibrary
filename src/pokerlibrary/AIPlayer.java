/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerlibrary;

import java.util.List;

/**
 * Η κλάση αυτή υλοποιεί την βασική έννοια του παίκτη με Τεχνητη Νοημοσύνη
 * Οποιαδίποτε κλάση που υλοποιει την έννοια του παίκτη με Τεχνητή Νοημοσύνη, 
 * πρέπει να επεκτείνει αυτή τη κλάση.
 * @author Αθανάσιος Ροίδης
 * @version 1.13.1
 */
public abstract class AIPlayer extends Player{
   
    
    public AIPlayer(String name,int stack){
        super(name,stack);
    }
    

}
