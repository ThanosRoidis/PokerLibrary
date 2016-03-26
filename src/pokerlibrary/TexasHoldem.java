/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerlibrary;

import java.util.List;

/**Αυτή η κλάση υλοποιέι την έννοια του Παινχιδιού Πόκερ Texas Hold'em, της κατηγορίας community card.
 * Επεκτείνει την abstract κλάση PokerGame
 * @author Αθανάσιος Ροίδης
 * @version  1.13.1
 * @see PokerGame
 */
public class TexasHoldem extends PokerGame{

    /** Καλέι τον κατασκέυαστη της υπερκλάσης PokerGame
     *  με τιμές 6 και false
     * @see PokerGame
     */
    public TexasHoldem(){
        super(6,false);
    }
    
    /** Ξεκινάει την τρέχουσα γύρα του παιχνιδιού στο τραπέζι.
     *  Την πρώτη γύρα μοιράζει σε ολους τους ενεργόυς παίκτες απο 2 κλειστά φύλλα,απο την τράπουλα του τραπεζιού
     *  Όλες τις υπόλοιπες μοιράζει απο 1 ανοικτό φύλλο στα κοινά φύλλα(communityCards) του τραπεζιόυ
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
                    firstPlayer.addToFaceDownCards(table.dealCard());
                    firstPlayer.addToFaceDownCards(table.dealCard());
                    do{
                        currentPlayer = table.getNextPlayer(currentPlayer);
                        currentPlayer.addToFaceDownCards(table.dealCard());
                        currentPlayer.addToFaceDownCards(table.dealCard());
                    
                    }while(!table.getNextPlayer(currentPlayer).equals(firstPlayer));
                    break;
                case 2:
                    Card card = table.dealCard();
                    table.addToCommunityCards(card);
                    for(Player player:players){
                        if(player.isActive() && !player.isFolded()){
                            player.addToAvailableCards(card);
                        }
                    }
                    break;
                case 3:
                    card = table.dealCard();
                    table.addToCommunityCards(card);
                    for(Player player:players){
                        if(player.isActive() && !player.isFolded()){
                            player.addToAvailableCards(card);
                        }
                    }
                    break;
                case 4:
                    card = table.dealCard();
                    table.addToCommunityCards(card);
                    for(Player player:players){
                        if(player.isActive() && !player.isFolded()){
                            player.addToAvailableCards(card);
                        }
                    }
                    break;
                case 5:
                    card = table.dealCard();
                    table.addToCommunityCards(card);
                    for(Player player:players){
                        if(player.isActive() && !player.isFolded()){
                            player.addToAvailableCards(card);
                        }
                    }
                    break;
                case 6:
                    card = table.dealCard();
                    table.addToCommunityCards(card);
                    for(Player player:players){
                        if(player.isActive() && !player.isFolded()){
                            player.addToAvailableCards(card);
                        }
                    }
                    break;
                default:
                    break;
        }       
    }

  
    
}
