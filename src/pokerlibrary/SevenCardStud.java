/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerlibrary;

import java.util.List;

/**Αυτή η κλάση υλοποιέι την έννοια του Παινχιδιού Πόκερ Seven Card Stud, της κατηγορίας stud.
 * Επεκτείνει την abstract κλάση PokerGame
 *
 * @author Αθανάσιος Ροίδης
 * @version 1.13.1
 */
public class SevenCardStud extends PokerGame{
    
    /** Καλέι τον κατασκέυαστη της υπερκλάσης PokerGame
     *  με τιμές 6 και false
     *@see PokerGame
     */
    public SevenCardStud(){
        super(6,false);
    }
    /** Ξεκινάει την τρέχουσα γύρα του παιχνιδιού στο τραπέζι.
     *  Την πρώτη γύρα μοιράζει σε ολους τους ενεργόυς παίκτες απο 2 κλειστά φύλλα,απο την τράπουλα του τραπεζιού
     *  Την τελευταία γύρα(6) μοιράζει σε ολους τους παίκτες απο 1 φύλλο
     *  Όλες τις υπόλοιπες μοιράζει απο 1 ανοικτό φύλλο στους πάικτες
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
                    if(!firstPlayer.isFolded()){
                        firstPlayer.addToFaceUpCards(table.dealCard());
                    }
                    do{
                        currentPlayer = table.getNextPlayer(currentPlayer);
                        if(!currentPlayer.isFolded()){
                            currentPlayer.addToFaceUpCards(table.dealCard());
                        }
                    }while(!table.getNextPlayer(currentPlayer).equals(firstPlayer));
                    break;
                case 3:
                    if(!firstPlayer.isFolded()){
                        firstPlayer.addToFaceUpCards(table.dealCard());
                    }
                    do{
                        currentPlayer = table.getNextPlayer(currentPlayer);
                        if(!currentPlayer.isFolded()){
                            currentPlayer.addToFaceUpCards(table.dealCard());
                        }
                    }while(!table.getNextPlayer(currentPlayer).equals(firstPlayer));
                    break;
                case 4:
                    if(!firstPlayer.isFolded()){
                        firstPlayer.addToFaceUpCards(table.dealCard());
                    }
                    do{
                        currentPlayer = table.getNextPlayer(currentPlayer);
                        if(!currentPlayer.isFolded()){
                            currentPlayer.addToFaceUpCards(table.dealCard());
                        }
                    }while(!table.getNextPlayer(currentPlayer).equals(firstPlayer));
                    break;
                case 5:
                    if(!firstPlayer.isFolded()){
                        firstPlayer.addToFaceUpCards(table.dealCard());
                    }
                    do{
                        currentPlayer = table.getNextPlayer(currentPlayer);
                        if(!currentPlayer.isFolded()){
                            currentPlayer.addToFaceUpCards(table.dealCard());
                        }
                    }while(!table.getNextPlayer(currentPlayer).equals(firstPlayer));
                    break;
                case 6:
                    if(!firstPlayer.isFolded()){
                        firstPlayer.addToFaceUpCards(table.dealCard());
                    }
                    do{
                        currentPlayer = table.getNextPlayer(currentPlayer);
                        if(!currentPlayer.isFolded()){
                            currentPlayer.addToFaceDownCards(table.dealCard());
                        }
                    }while(!table.getNextPlayer(currentPlayer).equals(firstPlayer));
                    break;
                default:
                    break;
                    
        }     
    }

 
    
}
