/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerlibrary;

import java.util.List;

/**Αυτή η κλάση υλοποιέι την έννοια του παιχνιδιού Πόκερ Five Card Draw, της κατηγορίας draw
 * Επεκτέινει την κλάση PokerGame
 *
 * @author Αθανάσιος Ροίδης 
 * @version 1.13.1
 * @see PokerGame
 */
public class FiveCardDraw extends PokerGame{
    
    /** Καλέι τον κατασκέυαστη της υπερκλάσης PokerGame
     *  με τιμές 2 και true
     *@see PokerGame
     */
    
    public FiveCardDraw(){
        super(2,true);
    }

    /** Ξεκινάει την τρέχουσα γύρα του παιχνιδιού στο τραπέζι.
     *  Την πρώτη γύρα μοιράζει σε ολους τους ενεργόυς παίκτες απο 5 κλειστά φύλλα,απο την τράπουλα του τραπεζιού
     *  Tην δεύτερη γύρα μοιράζει σε όλους τους ενεργόυς παίκτες φύλλα, μέχρι ο αριθμός των καρτών τους να γίνει 5
     * @param table Το τραπέζι πάνω στο οποιό θα αρχίσει η γύρα
     * @see Table
     * @see Player
     */
    
    @Override
    public void startRound(Table table) {
        List<Player> players = table.getPlayers();
        Player firstPlayer = table.getFirstPlayer();
        Player currentPlayer = firstPlayer;
        
        switch (getRoundCounter()){
                case 1:
                    for(int i=0;i<5;i++){
                        firstPlayer.addToFaceDownCards(table.dealCard());
                    }
                    do{
                        currentPlayer = table.getNextPlayer(currentPlayer);
                        for(int i=0;i<5;i++){
                            currentPlayer.addToFaceDownCards(table.dealCard());
                        }
                    
                    }while(!table.getNextPlayer(currentPlayer).equals(firstPlayer));
                    break;
                case 2:
                    if(!firstPlayer.isFolded()){
                        while(firstPlayer.getFaceDownCards().size() < 5){
                            firstPlayer.addToFaceDownCards(table.dealCard());
                        }
                    }
                    do{
                        currentPlayer = table.getNextPlayer(currentPlayer);
                        if(!currentPlayer.isFolded()){
                            while(currentPlayer.getFaceDownCards().size() < 5){
                                currentPlayer.addToFaceDownCards(table.dealCard());
                            }
                        }
                    }while(!table.getNextPlayer(currentPlayer).equals(firstPlayer));
                    break;
                default:
                    break;
        }
    }

    @Override
    public void endRound(Table table) {
        super.endRound(table);
    }
    
    @Override
    public boolean allowsCardsChange(){
        if(super.allowsCardsChange()){
            if(getRoundCounter() == 2){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
}
