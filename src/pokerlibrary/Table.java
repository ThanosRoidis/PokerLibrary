/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerlibrary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/** Η κλάση αυτή υλοποιεί την έννοια του τραπεζιού Πόκερ
 *
 * @author Αθανάσιος Ροίδης
 * @version 1.13.1
 */
public class Table {
    /**
     * Οι παίκτες που κάθονται στο τραπέζι
     */
    private List<Player> players;
    /**
     * Ο παίκτης που παίζει πρώτος σε κάθε γύρα Πόκερ
     */
    private Player firstPlayer;
    /**
     * Τα φύλλα που είναι πάνς στο τραπέζι κοινά για όλους
     */
    private List<Card> communityCards;
    /**
     * Η δομή πονταρίσματος που εφαρμόζεται στο Πόκερ
     */
    private BettingStructure bettingStructure;
    /**
     * Η παραλλαγη του Πόκερ που παίζεται στο τραπέζι
     */
    private PokerGame pokerGame;
    /**
     * Η τράπουλα που χρησιμοποιείται στο τραπέζι
     */
    private Deck deck;
    
    
    
    /**
     *
     * @param players Οι παίκτες που θα κάθονται στο τραπέζι
     * @param bettingStructure Η δομή πονταρίσματος που εφαρμόζεται στο Πόκερ
     * @param pokerGame  Η παραλλαγη του Πόκερ που παίζεται στο τραπέζι
     */
    public Table(List<Player> players,BettingStructure bettingStructure,PokerGame pokerGame){
        this.players = players;
        this.bettingStructure = bettingStructure;
        this.pokerGame = pokerGame;
        this.communityCards = new ArrayList<>();
        deck = new Deck();
        deck.shuffle();
        Random random = new Random();
        int firstPlayerPosition = random.nextInt(players.size());
        firstPlayer = players.get(firstPlayerPosition);
        
    }
    
    /**
     * Προσθέτει στα κοινά φύλλα του τραπεζιού ένα φύλλο
     * @param card το φύλλο που πρστίθεται στα κοινα φύλλα του τραπεζιού
     */
    public void addToCommunityCards(Card card){
        communityCards.add(card);
    }
    
    /**
     *
     * @return Τους παίκτες που κάθονται στο τραπέζι
     */
    
    public List<Player> getPlayers(){
        return players;
    }
    

    
    public void setFirstPlayer(Player player){
        firstPlayer = player;
    }
    
    public Player getFirstPlayer(){
        return firstPlayer;
    }
    
    public Deck getDeck(){
        return deck;
    }
    
    public List<Card> getCommunityCards(){
        return communityCards;
    }
    
    /**
     * Μαζεύει τα φύλλα απο το τραπέζι
     */
    
    public void emptyCommunityCards(){
        communityCards.clear();
    }
    
    /**
     * Επιστρέφει τον αριθμό των παικτών που δεν έχουν χάσει ακόμα
     */
    
    public int getNumberOfActivePlayers(){
        int counter = 0;
        for(Player player : players){
            if(player.isActive()){
                counter++;
            }
        }
        return counter;
    }
    
    /** Επιστρέφει τον αμέσως επόμενο παίκτη απο έναν παικτη που δεν έχει χάσει
     * @param currentPlayer Ο παίκτης απο τον οποίο θέλουμε τον επόμενο παίκτη
     * @return Τον επόμενο ενεργό παίκτη απο τον currentPlayer
     */
    public Player getNextPlayer(Player currentPlayer){
        int currentPlayerPosition = players.indexOf(currentPlayer);
        int nextPlayerPosition;
        do{
            if(currentPlayerPosition == players.size() - 1){
                nextPlayerPosition = 0;
            }
            else{
                nextPlayerPosition = currentPlayerPosition + 1;
            }
        }while(!players.get(nextPlayerPosition).isActive());
        return players.get(nextPlayerPosition);
    }
    
    /**
     * Βγάζει απο την κατάσταση fold όλους τους παίκτες που είναι ενεργοί
     * @see Player
     */
    public void unfoldAllPlayers(){
        for(Player player:players){
            if(player.isActive()){
                player.unfold();
            }
        }
    }

    PokerGame getPokerGame() {
        return pokerGame;
    }

    BettingStructure getBettingStructure() {
        return bettingStructure;
    }
    
    /** Αν δεν είναι άδεια, επιστρέφει την πρώτη κάρτα. Αλλιώς δημιουργεί καινούργια τράπουλα,
     * την ανακατεύει, και γυρνά το πρώτο φύλο
     * 
     * @return Την επόμενη κάρτα 
     */
    public Card dealCard(){
        if(deck.isEmpty()){
            deck = new Deck();
            deck.shuffle();
        }
        return deck.draw();
    }
   
    
    /**
     * Προετοιμάζει το τραπέζι για την επόμενη γύρα του Πόκερ
     * 
     */
    public void prepareForNextPokerRound(){
        for(Player player : players){
            if(player.getStack() == 0){
                player.deactivate();  
            }
            if(player.isActive()){
                player.unfold();
            }
            player.setAction(Action.NOACTION);
            player.removeAllCards();
        }
        deck = new Deck();
        deck.shuffle();
        firstPlayer = getNextPlayer(firstPlayer);
    }

    /**
     * Γίνετε έλεγχος για το αν τελείωσε το παιχνίδι, 
     * το οποίο συμβαίνει όταν έχουν μείνει 2 παίκτες στο τραπέζι
     * @return 
     */
    boolean gameIsOver() {
        if(getNumberOfActivePlayers() == 2){
            return true;
        }
        return false;
    }
    
}
