/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerlibrary;

import java.util.List;

/**Αυτή η κλάση υλοποιεί την βασική έννοια του παινχιδιού Πόκερ.
 * Οποίαδίποτε κλάση παινχιδιου Πόκερ πρέπει να επεκτίνει αυτή την κλάση
 *
 * @author Αθανάσιοσ Ροίδης
 * @version  1.13.1
 */
public abstract class PokerGame {
    
    private int numberOfRounds;
    private int roundCounter;
    private boolean allowsCardsChange;
    
    /**
     *
     * @param numberOfRounds Μετά απο πόσες γύρες τελιώνει ενα παιχνίδι Πόκερ
     * @param allowsCardsChange Αν επιτρέπεται η αλλάγη καρτών μετά απο το τέλος μιας γύρας Πόκερ
     */
    public PokerGame(int numberOfRounds,boolean allowsCardsChange){
        this.numberOfRounds = numberOfRounds;
        this.allowsCardsChange = allowsCardsChange;
        roundCounter = 1;
    }
    
    /**Ξεκινάει την τρέχουσα γύρα Πόκερ στο τραπέζι
     *
     * @param table Το τραπέζι πάνω στο οποίο θα αρχίσει η τρέχουσα γύρα του Πόκερ
     */
    public abstract void startRound(Table table);
    
    /**Τελίωνει την τρέχουσα παρτίδα στο τραπέζι,αυξάνωντας τον μετρητή γύρας 
     * Άν είναι η τελευταίος γύρος αυτου του παιχνιδιου Πόκερ , καλείται και η BettingStructure.distributePots του τραπεζιού
     *@param table Το τραπέζι πάνω στο οποίο θα τελιώσει η τρέχουσα γύρα του Πόκερ
     * @see BettingStructure#distributePots() 
     */
    public  void endRound(Table table){
        if(getRoundCounter() <= getNumberOfRounds()){
            increaseRoundCounter();
        }
        else{
            table.getCommunityCards().clear();
            table.getBettingStructure().distributePots();
            resetRoundCounter();
        }
    };
    
    /**
     *
     * @return Το πλήθος των γύρων που διαρκεί μια παρτίδα Πόκερ
     */
    public int getNumberOfRounds(){
        return numberOfRounds;
    }
    
    /**
     *
     * @return Ο τρέχων γύρος του παιχνιδιού
     */
    public int getRoundCounter(){
        return roundCounter;
    }
    
    /** Αυξάνει τον μετρητή γύρας
     *
     */
    protected void increaseRoundCounter(){
        roundCounter++;
    }
    
    /** Θέτει τον μετρήτη γύρας στο 1
     *
     */
    protected void resetRoundCounter(){
        roundCounter = 1;
    }
    
    
    public boolean allowsCardsChange(){
        return allowsCardsChange;
    }
}
