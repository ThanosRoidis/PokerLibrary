/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerlibrary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Η κλάση αυτή υλοποεί την βασική έννοια του Πάικτη σε ένα παιχνίδι Πόκερ
 * Οποιαδίποτε κλάση που υλοποιει την έννοια του παίκτη, ελεγχόμενου απο τον χρήστη και μή, πρέπει να επεκτέινει αυτή την κλάση
 * @author Αθανάσιος Ροίδης
 * @version 1.13.1
 * @see PokerGame
 * @see BettingStructure
 */
public abstract class Player {
    
    private String name;
   
     /**
      * Ένας ξεχωριστός ακέραιος αριθμός που αντιστοιχεί σε κάθε πάικτη
      */
    private final int id;
    private static int idCounter = 0;
    
    /**
     * Τα συνολικά λεφτά του παίκτη
     */
    private int stack;
    /**
     * Τα λεφτά που έχει ποντάρει
     */
    private int wager;
    /**
     * Η κατάσταση του παίκτη
     */
    private boolean active;
    
    /**
     * Η ενέργεια του παίκτη
     */
    private Action action;
    
    private boolean folded; 
    
    /**
     * Το καλυτερο φυλλο που μπορεί να έχει με τα φύλλα που του είναι διαθέσιμα
     */
    private Hand bestHand;
    
    /**
     * Τα φύλλα που έχει στην διάθεση του ο παίκτης
     */
    private List<Card> availableCards;
    /**
     * Τα κλειστά φύλλα του παίκτη
     */
    private List<Card> faceDownCards;
    /**
     * Τα ανοικτά φύλλα του παίκτη
     */
    private List<Card> faceUpCards;
    
    
    /**
     *
     * @param name Το όνομα του παίκτη
     * @param stack Τα λεφτά με τα οποία ξεκινάει
     */
    public Player(String name,int stack){
        this.name = name;
        id = idCounter++;
        this.stack = stack;
        wager = 0;
        active = true;
        action = Action.NOACTION;
        folded = false;
        availableCards = new ArrayList<>();
        faceDownCards = new ArrayList<>();
        faceUpCards = new ArrayList();
    }
    
    
    /**
     * Ο παίκτης αποφασίζει ποιά θα έιναι η επόμενη του ενέργεια.με βάση την αξία του χεριού του
     * και την κατάσταση του τραπεζιού στο οποίο κάθεται
     * @param availableActions Οι ενέργειες που έχει στην διάθεση του
     * @param table Το τραπέζι στο οποίο παίζει ο παίκτης
     * @see Table
     */
    
    
    public abstract void think(List<Action> availableActions,Table table);
    
    protected Hand getHand(){
        return bestHand;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setStack(int stack)
    {
        this.stack = stack;
    }
    
    public int getStack()
    {
        return stack;
    }
    
    
    public void setWager(int wager)
    {
        this.wager = wager;
    }
    
    public int getWager()
    {
        return wager;
    }
    

    public boolean isFolded()
    {
        return folded;
    }
    

    public void unfold(){
        folded = false;
    }
    

    public void fold(){
        folded = true;
        removeAllCards();
        updateHand();
    }
    

    /**
     *
     * @return Το αν ο παίκτης είναι ενεργός(παίζει ακόμα) ή όχι(έχει χάσει)
     */
    public boolean isActive()
    {
        return active;
    }
    
    /**
     *
     */
    public void deactivate()
    {
        active = false;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (!(obj instanceof Player)){
             return false;
        }
        Player player = (Player) obj;
        if (id == player.id ){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id;
        return hash;
    }

    public void setAction(Action action) {
        this.action = action;
    }
    
    /**
     * @return Η τελευταία ενέργεια του παίκτη
     */
    public Action getAction(){
        return action;
    }
    

    /**
     * 
     * @return Η αξία του χεριού του παίκτη 
     */
    public double getHandValue(){
        return bestHand.getValue();
    }
    
    /**
     * Προσθέτει ένα φύλλο στα ανοικτά και στα διαθέσιμα φύλλα του παίκτη και καλεί την Player.updateHand
     * @param card Το φύλλο το οποίο προστίθεται
     */
    public void addToFaceUpCards(Card card){
        faceUpCards.add(card);
        availableCards.add(card);
        updateHand();
    }
    
    /**
     * Προσθέτει ένα φύλλο στα κλειστα και στα διαθέσιμα φύλλα του παίκτη και καλεί την Player.updateHand()
     * @param card Το φύλλο το οποίο προστίθεται
     */
    public void addToFaceDownCards(Card card){
        faceDownCards.add(card);
        availableCards.add(card);
        updateHand();
    }
    
    /**
     * Προσθέτει ένα φύλλο στα διαθέσιμα φύλλα του παίκτη και καλεί την  Player.updateHand()
     * @param card Το φύλλο το οποίο προστίθεται
     * @see Player#updateHand() 
     */
    public void addToAvailableCards(Card card){
        availableCards.add(card);
        updateHand();
    }
    
    public List<Card> getFaceUpCards(){
        return faceUpCards;
    }
    
    public List<Card> getFaceDownCards(){
        return faceDownCards;
    }
    
    public List<Card> getAvailableCards(){
        return availableCards;
    }
    
    
    public abstract void changeCards();
    
    
    /**
     * Αδειάζει όλα τα φύλλα απο τον παίκτη και καλει την Player.updateHand()
     *@see Player#updateHand() 
     */
    public void removeAllCards(){
        availableCards.clear();
        faceDownCards.clear();
        faceUpCards.clear();
        updateHand();
    }
    
    /**
     * Βρίσκει όλα τα πιθανά χέρια που μπορούν να σχηματοστούν με τα διαθέσιμα φύλλα του παίκτη
     * και κρατάει αυτό με την μεγαλύτερη αξία
     */
    
    protected void updateHand(){
        if(availableCards.size()<6){
            Card cards[] = new Card[availableCards.size()];
            for(int i=0;i<availableCards.size();i++){
                cards[i] = availableCards.get(i);
            }
            bestHand = new Hand(cards);
        }
        else if(availableCards.size() == 6){
            for(int leftOutCardPosition = 0;leftOutCardPosition<6;leftOutCardPosition++){
                Card cards[] = new Card[5];
                int nextCardPosition = 0;
                for(int cardToAdd = 0;cardToAdd<6;cardToAdd++){
                    if(cardToAdd!=leftOutCardPosition){
                        cards[nextCardPosition++] = availableCards.get(cardToAdd);     
                    }
                }
                Hand tempHand = new Hand(cards);
                if(tempHand.getValue() > bestHand.getValue()){
                    bestHand = tempHand;
                }
            }
        }
        else if(availableCards.size() == 7){
            for(int i=0;i<availableCards.size()-1;i++){
                for(int j=i+1;j<availableCards.size();j++){
                    Card cards[] = new Card[5];
                    int nextCardPosition = 0;
                    for(int k=0;k<availableCards.size();k++){
                        if(k!=j && k!=i){
                            cards[nextCardPosition++]= availableCards.get(k);
                        }
                    }
                    Hand tempHand = new Hand(cards);
                    if(tempHand.getValue() > bestHand.getValue()){
                        bestHand = tempHand;
                    }
                }
            }
        }
    }
    
    
}
