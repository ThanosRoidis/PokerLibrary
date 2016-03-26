/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerlibrary;

import java.util.ArrayList;
import java.util.List;

/** Η κλάση αυτή υλοποιεί την έννοια της fixed limit δομής πονταρίσματος(δηλαδή επιτρέπετε να γίνει μόνο ένα συγκεκριμένο
 * ποσό raise) και επεκτείνει την κλάση abstract κλάση BettingStructure
 *
 * @author Αθανάσιος Ροίδης
 * @version  1.13.1
 * @see BettingStructure
 */
public class FixedLimit extends BettingStructure{

    private int smallBlind;
    private int bigBlind;
    private int ante;
    private boolean nooneHasBet;
    private int smallRaise;
    private int bigRaise;
    private int raisesLimit;
    private int raisesCounter;


    /** Καλεί τον κατασκευαστή της υπερκλάσης BettingStructure με παράμετρο 2(2 blinds)
     *
     *
     * @param smallBlind Το ποσό στο οποίο θα τεθέι το ποντάρισμα του πρώτου πάικτη στην πρώτη γύρα της Δομής Πονταρίσματος
     * @param bigBlind Το ποσό στο οποίο θα τεθέι το ποντάρισμα του δέυτερου πάικτη στην πρώτη γύρα της Δομής Πονταρίσματος
     * @param ante Ενα αναγκαστικο ποσό που πληρώνει ο κάθε παίκτης στην αρχή της πρώτης γύρας της Δομής Πονταρίσματος
     * @param smallRaise Το ποσό το οποίο μπορούν να κάνουν raise οι παίκτες μέχρι και την δεύτερη γύρα της Δομής Πονταρίσματος
     * @param bigRaise Το ποσό το οποίο μπορούν να κάνουν raise οι παίκτες μετά την δεύτερη γύρα της Δομής Πονταρίσματος
     * @param raisesLimit Ο αριθμός των raises που μπορούν να γίνουν σε κάθε γύρα πονταρίσματος
     * @see BettingStructure
     */


    public FixedLimit(int smallBlind,int bigBlind,int ante,int smallRaise,int bigRaise,int raisesLimit){
        super(2);
        this.smallBlind = smallBlind;
        this.bigBlind = bigBlind;
        this.ante = ante;
        nooneHasBet = true;
        this.smallRaise = smallRaise;
        this.bigRaise = bigRaise;
        this.raisesLimit = raisesLimit;
        Action.FIXEDRAISE.setAmount(smallRaise);
        raisesCounter = 0;
    }

    /**
     * Καλεί τον κατασκευαστή FixedLimit(int smallBlind,int bigBlind,int ante,int smallRaise,int bigRaise,int raisesLimit)
     * για τιμές :
     * smallBlind = 2
     * bigBlind = 4
     * ante = 2
     * smallRaise = 2
     * bigRaise = 4
     * raisesLimit = 4
     * @see FixedLimit#FixedLimit(int, int, int, int, int, int)
     */

    public FixedLimit(){
        this(2,4,2,2,4,4);

    }
    /** Πρέπει να καλέιται μόνο την πρώτη γύρα της Δομής Πονταρίσματος
     * Θέτει σε όλους τους παίκτες σε μια λίστα(List) απο παίκτες(Player) σαν αρχικό wager την τιμή ante, και
     * επιπρόσθετα προσθέτει στο wager του πρώτου παίκτη  την τιμή του smallBlind
     * και στον δεύτερο την τιμή του bigBlind
     *
     *
     * @param players
     * @param currentPlayer Ο πρώτος παίκτης
     * @see Player
     */

    private void initialize(List<Player> players,Player currentPlayer) {
        pots.add(new Pot());
        raisesCounter = 0;
        nooneHasBet = false;
        Action.FIXEDRAISE.setAmount(smallRaise);
        for(Player player:players){
            if(player.isActive() == true){
                if(player.getStack() < ante){
                    pots.get(0).addToAmount(player.getStack());
                    player.setStack(0);
                    player.deactivate();
                }
                else{
                    pots.get(0).addPlayer(player);
                    player.setStack(player.getStack() - ante);
                    player.setWager(ante);
                }
            }
        }

        int firstPlayerPosition = players.indexOf(currentPlayer);
        if(currentPlayer.getStack() > smallBlind){
            currentPlayer.setWager(currentPlayer.getWager() + smallBlind);
            currentPlayer.setStack(currentPlayer.getStack() - smallBlind);

        }
        else{
            pots.get(0).addToAmount(currentPlayer.getStack());
            currentPlayer.setStack(0);
            currentPlayer.setWager(0);
            currentPlayer.deactivate();
        }

        int secondPlayerPosition = firstPlayerPosition ;
        do{
            secondPlayerPosition++;
            if(secondPlayerPosition == players.size()){
                secondPlayerPosition = 0 ;
            }
        }
        while(players.get(secondPlayerPosition).isActive() == false);
        Player secondPlayer = players.get(secondPlayerPosition);

        if(secondPlayer.getStack() > bigBlind){
            secondPlayer.setStack(secondPlayer.getStack() - bigBlind);
            highestWager = bigBlind + secondPlayer.getWager();
            secondPlayer.setWager(secondPlayer.getWager() + bigBlind);
            secondPlayer.setAction(Action.BET);
            setLastOneWhoRaised(secondPlayer);
        }
        else{
            pots.get(0).addToAmount(secondPlayer.getStack());
            secondPlayer.deactivate();
        }

        int nextPlayerPosition = secondPlayerPosition;
        do{
            nextPlayerPosition++;
            if(nextPlayerPosition == players.size()){
                nextPlayerPosition = 0 ;
            }
        }
        while(players.get(nextPlayerPosition).isActive() == false);
        currentPlayer = players.get(nextPlayerPosition);
    }

    @Override
    public void executeAction(Player player) {
        Action action = player.getAction();
        switch (action){
            case CHECK:
                player.setWager(0);
                break;
            case CALL:
                if(player.getStack() >= highestWager - player.getWager()){
                    player.setStack(player.getStack() + player.getWager() - highestWager );
                    player.setWager(highestWager);
                }
                else{
                    player.setWager(player.getStack());
                    player.setStack(0);
                }
                break;
            case BET:
                raisesCounter = 1;
                player.setStack(player.getStack() - Action.FIXEDRAISE.getAmount());
                player.setWager(Action.FIXEDRAISE.getAmount());
                highestWager = player.getWager();
                setLastOneWhoRaised(player);
                nooneHasBet = false;
                break;
            case FIXEDRAISE:
                raisesCounter++;
                player.setStack(player.getStack() + player.getWager() - highestWager - Action.FIXEDRAISE.getAmount());
                player.setWager(highestWager + Action.FIXEDRAISE.getAmount());
                highestWager += Action.FIXEDRAISE.getAmount();
                setLastOneWhoRaised(player);
                break;
            case FOLD:
                try{
                    pots.get(pots.size()-1).addToAmount(player.getWager());
                }catch(Exception e){
                    pots.add(new Pot());
                    pots.get(pots.size()-1).addToAmount(player.getWager());
                }
                player.fold();
                player.setWager(0);
                break;
            default :
                break;
        }
    }


    @Override
    public List<Action> getAvailableActions(Player player) {
        ArrayList<Action> availableActions = new ArrayList<>();
        if(player.getStack() > 0){
            if(nooneHasBet){
                availableActions.add(Action.CHECK);
                
                if(player.getStack() > Action.FIXEDRAISE.getAmount()){
                    availableActions.add(Action.BET);
                }
            }
            else
            {
                availableActions.add(Action.CALL);
                if(player.getStack() >= highestWager - player.getWager() + Action.FIXEDRAISE.getAmount()){
                    if(raisesCounter < raisesLimit){ 
                        availableActions.add(Action.FIXEDRAISE);
                    }
                }
            }
            availableActions.add(Action.FOLD);
        }
        return availableActions;
    }


  

    @Override
    public void startBettingRound(List<Player> players,Player firstPlayer) {
        if(getBettingRoundCounter() == 1){
            initialize(players,firstPlayer);
        }
        else{
            raisesCounter = 0;
            highestWager = 0;
            nooneHasBet = true;
            setLastOneWhoRaised(firstPlayer);
            increaseBettingRoundCounter();
            if(getBettingRoundCounter() > 2 ){
                Action.FIXEDRAISE.setAmount(bigRaise);
            }
        }
    }

    @Override
    public void distributePots() {
        super.distributePots();
        resetBettingRoundCounter();
    }

    



}
