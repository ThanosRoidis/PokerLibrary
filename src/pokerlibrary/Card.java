/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerlibrary;

/** Η κλάση αυτή υλοποιεί την έννοια του φύλλου μιας τράπουλας
 * 
 * @author Αθανάσιος Ροίδης
 * @version 1.13.1
 */


public class Card {
    
    /**
     * Το χρώμα του φυλου
     */
    private Suit suit;

    /**
     * Ο Αριθμός του φύλλου
     */
    private Rank rank;
    
    /**
     *
     * @param suit Το χρώμα του φύλλου
     * @param rank Ο αριθμός του φύλλου
     */
    public Card(Suit suit,Rank rank){
        this.suit = suit;
        this.rank = rank;
    }
    
    public Suit getSuit(){
        return suit;
    }
    
    public Rank getRank(){
        return rank;
    }
    

    public String getDescription(){
        return rank.toString() + " of " + suit.toString() ;
    }
    
    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj instanceof Card){
            Card card = (Card)obj;
            if(this.suit.equals(card.suit) && this.rank.equals(card.rank)){
                return true;
            }
        }
        return false;
    }
    
}
