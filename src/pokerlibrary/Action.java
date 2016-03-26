/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerlibrary;

/**
 * Υλοποιεί την έννοια της ενέργειας πονταρίσματος
 * 
 * @author Αθανάσιος Ροίδης
 * @version 1.13.1
 */
public enum Action {
    NOACTION("NOACTION"),BET("Bet"),CHECK("Checked"),CALL("Called"),FIXEDRAISE("Raised"),FOLD("Folded"),OPEN("Opened");
    
    /**
     * Μια τιμή που μπορέι να χρησιμοποιη8εί στην δομή πονταρίσματος (BettingStructure)
     * @see BettingStructure
     */
    private int amount;
    private String description;
    
    Action(String des){
        description = des;
    }
    
    public void setAmount(int amount)
    {
        this.amount = amount;
    }
    
    public int getAmount()
    {
        return amount;
    }
        
    public String getDescritpion(){
        return description;
    }

}