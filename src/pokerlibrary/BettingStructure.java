/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerlibrary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** Η κλάση αυτή υλοποιεί την βασική έννοια της Δομής Πονταρίσματος
 *  Οποιαδίποτε Δομή Πονταρίσματος θα πρέπει να επεκτείνει αυτή τη κλάση
 * @author Αθανάσιος Ροίδης
 * @version 1.13.1
 */
public abstract class BettingStructure {

    /**
     * Το υψηλότερο ποντάρισμα που έχει γίνει αυτή τη γύρα στο παιχνίδι
     */
    protected int highestWager;

    /**
     * Η λίστα με όλα τα Pots που έχουν δημιουργηθει στο παιχνίδι
     * @see Pot
      */

    protected List<Pot> pots;

    /**
     * Ο πάικτης που ανέβασε τελευτάιος το απαιτούμενο χρηματικό ποσο
     */
    private Player lastOneWhoRaised;

    /**
     * Ο αριθμός των blinds που έχει αυτή η Δομή Πονταρίσματος
     */
    private int numberOfBlinds;

    /*
     * Ο μετρητής της τρέχουσας γύρας στη Δομή Πονταρίσματος
     */
    private int bettingRoundCounter;

    /**
     *
     * @param numberOfBlinds αριθμός των blinds που έχει αυτή η Δομή Πονταρίσματος
     */
    public BettingStructure(int numberOfBlinds){
        highestWager = 0;
        pots = new ArrayList<>();
        this.numberOfBlinds = numberOfBlinds;
        bettingRoundCounter = 1;
    }

    /**
     * Εκτελεί την ενέργεια ενος παίκτη
     * @param player Ο παίκτης του οποίου η ενέργεια εκτελείται
     */
    public abstract void executeAction(Player player);

    /** Υπολογίζει ποιές είναι οι επιτρέπτές ενέργεις ενός παίκτη με βάση την κατάσταση της δομής πονταρίσματος
     *  και επιστρέφει με την μορφή Λίστας(List(
     * @param player Ο παίκτης για τον οποίο υπολογίζονται οι επιτρεπτές ενέργεις
     * @return η Λίστα(List) με τις επιτρεπτές ενέργειες
     * @see Player
     * @see List
     * @see Action
     */
    public abstract List<Action> getAvailableActions(Player player);

    
     /**Τελειώνει την γύρα πονταρίσματος για μια List απο Players
      * Μαζεύει όλα τα λεφτά που έχει ποντάρει μπροστά του ο κάθε παίκτης (wager) σε ένα τραπέζι, διαμορφώνοντας τα pots,
      * και αυξάνει τον μετρητή της τρέχουσας γύρας
      * @param players Οι παίκτεσ απο τους οποίους μαζεύει τα wager
      * @see Player
      */
    
    public void endBettingRound(List<Player> players) {

        setLastOneWhoRaised(null);
        Player playersArray[];
        
        //Ένας αλγόριθμος για να δημιουργούνται τα pots
        
        int arraySize = 0;
        for(Player player:players){
            if(player.isActive() && !player.isFolded() ){
                arraySize++;
            }
        }
        playersArray = new Player[arraySize];
        int i = 0;
        for(Player player:players){
            if(player.isActive() && !player.isFolded() ){
                playersArray[i++] = player;
            }
        }

        // Bubble Sort με βάση το Wager του κάθε παίκτη
        for(i=0; i < playersArray.length; i++){
            for(int j = playersArray.length - 1; j > i ; j--){
                if(playersArray[j-1].getWager() > playersArray[j].getWager()){
                    Player temp = playersArray[j-1];
                    playersArray[j-1] = playersArray[j];
                    playersArray[j] = temp;
                }
            }
        }
        for(i = 0;i<playersArray.length;i++){
            if(i == 0){
                Pot lastPot = pots.get(pots.size() - 1);
                boolean flag = false;
                for(int k=0;k<playersArray.length;k++){
                    if(lastPot.contains(playersArray[k])){
                        flag = true;
                    }
                    else{
                        flag = false;
                        break;
                    }
                }
                if(flag == true && playersArray.length == lastPot.getNumberOfPlayers()){
                    lastPot.addToAmount(playersArray.length*playersArray[0].getWager());
                    int smallestWager = playersArray[0].getWager();
                    for(int j = 0;j < playersArray.length;j++){
                        playersArray[j].setWager(playersArray[j].getWager() - smallestWager);
                    }
                }
                else{
                   Pot newPot = new Pot();
                   newPot.addToAmount(playersArray[0].getWager()*playersArray.length);
                   int smallestWager = playersArray[0].getWager();
                   for(int j=0;j<playersArray.length;j++){
                       newPot.addPlayer(playersArray[j]);
                       playersArray[j].setWager(playersArray[j].getWager() - smallestWager);
                   }
                   pots.add(newPot);
                }
            }
            else{
                if(playersArray[i].getWager() > 0){
                   Pot newPot = new Pot();
                   newPot.addToAmount(playersArray[i].getWager()*(playersArray.length - i));
                   int smallestWager = playersArray[i].getWager();
                   for(int j=i;j<playersArray.length;j++){
                       newPot.addPlayer(playersArray[j]);
                       playersArray[j].setWager(playersArray[j].getWager() - smallestWager);
                   }
                   pots.add(newPot);
                }
            }
        }
        
        
        increaseBettingRoundCounter();
        for(Player player : players){
                if(player.isActive()){
                    if(!player.isFolded())
                        player.setAction(Action.NOACTION);
                }
        }

    }

    /**
     * Ξεκινάει την γύρα πονταρίσματος για μια Λίστα(List) απο παίκτες(Player), ξεκινώντας απο τον πρώτο παίκτη
     * 
     * @param Players Η λίστα απο παίκτες για τους οποίους αρχίζει ο γύρος πονταρίσματος
     * @param currentPlayer Ο πρώτος παίκτης
     */
    public abstract void startBettingRound(List<Player> Players,Player currentPlayer);

  
    public int getHighestWager(){
        return highestWager;
    }

    protected void setLastOneWhoRaised(Player player){
        lastOneWhoRaised = player;
    }

    public Player getLastOneWhoRaised(){
        return lastOneWhoRaised;
    }

    /** Ελέγχει άν η γύρα πρέπει να τελιώσει σε ένα τραπέζι, με βάση την κατάσταση της
     *  δομής πονταρίσματος και του τρέχων παίκτη
     *  Ένας γύρος τελιώνει εάν:
     *  - Ο τρέχων παίκτης έιναι και ο τελευταίος που έκανε raise
     *  - Εάν όλοι οι παίκτες έχουν κάνει check ή raise
     *  - Εάν όλοι πο ενεργοί πάικτες εκτός απο 2 έχουν κάνει fold
     *
     * @param table Το τραπέζι το οποίο ελεγχεται για το αν τελίωσε η γύρα πονταρίσματος
     * @param currentPlayer Ο τρέχων πάικτης
     * @see Action
     * @see Player
     * @see Table
     */
     public boolean bettingRoundIsOver(Table table, Player currentPlayer) {
        List<Player> players = table.getPlayers();
        if(currentPlayer.equals(getLastOneWhoRaised()) && !currentPlayer.getAction().equals(Action.NOACTION)){
            return true;
        }
        int counter = 0;
        for(Player player:players){
            if(player.getAction().equals(Action.FOLD) || !player.isActive()){
                counter ++ ;
            }
        }
        int roundActivePlayers = players.size() - counter;
        if(roundActivePlayers < 3){
            return true;
        }
        counter = 0;
        for(Player player: players){
            if(player.getAction().equals(Action.CHECK)){
                counter++;
            }
        }
        if(roundActivePlayers == counter){
            return true;
        }

        return false;
    }


    public int getNumberOfBlinds(){
        return numberOfBlinds;
    }


    public int getBettingRoundCounter(){
        return bettingRoundCounter;
    }

    protected void increaseBettingRoundCounter(){
        bettingRoundCounter++;
    };

    protected void resetBettingRoundCounter(){
        bettingRoundCounter = 1;
    }

    
     /**
     *
     * Διανέμει τα ποσά τα οποία περιέχει το κάθε Pot που υπάρχει στην
     * List<Pot> pots
     * 
     * @see Pot
     */
    public void distributePots(){
        Iterator<Pot> it = pots.iterator();
        while(it.hasNext()){
            Pot pot = (Pot)it.next();
            pot.distribute();
            it.remove();
        }
    };
}
