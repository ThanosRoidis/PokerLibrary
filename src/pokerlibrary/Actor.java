/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerlibrary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Η κλάση αυτή υλοποιεί την βασική έννοια του παίκτη ελεγχόμενου απο τον χρήστη
 * @author Αθανάσιος Ροίδης
 * @version 1.13.1
 */
public class Actor extends Player{
    /**
     * Τα φύλλα τα οποία ο χρήστης έχει αποφασίσει να διώξει απο τα κλειστά του φύλλα
     */
    private List<Card> cardsToChange;
  
    public Actor(String name,int stack){
        super(name,stack);
        cardsToChange = new ArrayList<>();
    }

    /**
     * Διώχνει απο τα κλειστά φύλλα του παίκτη όσα φυλλα είναι για αλλαγή
     * @see Actor#cardsToChange
     */
    @Override
    public void changeCards() {
        
        Iterator it = cardsToChange.iterator();
        List<Card> faceDCards = super.getFaceDownCards();
        List<Card> availCards = super.getAvailableCards();
        while(it.hasNext()){
            Card card = (Card)it.next();
            
            faceDCards.remove(card);
            availCards.remove(card);
            it.remove();
        }
        super.updateHand();
    }
    
    
    /**
     * Προσθέτει στα φύλλα για αλλαγή ένα φύλλο
     * @param card Το φύλλο για αλλαγή
     * @see  Actor#cardsToChange
     */
    public void addToCardsToChange(Card card){
        cardsToChange.add(card);
    }

    /**
     * Βρίσκει μία προτεινόμενη ενέργεια, με βάση τις διαθέσιμες ενέργειες και
     * και την κατάσταση του τραπεζιού(Δεν έχει ολοκληρωθεί ακόμα)
     * @param availableActions Οι διαθέσιμες ενέργειες
     * @param table Το τραπέζι στο οποίο κάθετε ο παίκτης
     */
    @Override
    public void think(List<Action> availableActions,Table table) {
        
    }
}
