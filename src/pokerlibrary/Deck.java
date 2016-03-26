/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerlibrary;

import java.util.Collections;
import java.util.LinkedList;

/** Η κλάση αυτή υλοποεί την έννοια της τράπουλας απο φύλλα, χώρις το Joker
 *
 * @author Αθανάσιος Ροίδης
 * @version 1.13.1
 */
public class Deck {
    /**
     * Τα φύλλα απο τα οποία αποτελείται η τράπουλα
     */
    private LinkedList<Card> cards;
    
    
    public Deck(){
        cards = new LinkedList<>();
        for(Suit suit : Suit.values()){
            for(Rank rank : Rank.values()){
                cards.add(new Card(suit,rank));
            }
        }
          
    }
    
    /**
     * 
     * @return Το πρώτο φύλλο της τράπουλας 
     */
    
    public Card draw(){
        return cards.removeFirst();
    }
    
    /**
     * Ανακατεύει την τράπουλα
     */
    public void shuffle(){
        Collections.shuffle(cards);
    }
    
    
    /**
     * Ελέγχει αν τα φύλλα της τράπουλας τελείωσαν
     * @return Το αν η τράπουκα είναι άδεια
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }
    
}
