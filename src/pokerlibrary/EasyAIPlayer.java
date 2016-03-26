/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerlibrary;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Roy
 */
public class EasyAIPlayer extends AIPlayer{
   

    public EasyAIPlayer(String name,int stack){
        super(name,stack);
    }
    /** Ο παίκτης αποφασίζει ποιά θα είναι η επόμενη του ενέργεια μόνο με βάση 
     *  την αξία του χεριού του, αγνοώντας την κατάσταση του τραπεζιού
     * 
     * @param availableActions Οι διαθέσιμες ενέργειες του παικτη
     * @param table Το τραπέζι στο οποίο κάθεται ο παίκτης
     */
    @Override         
    public void think(List<Action> availableActions,Table table){
       
        switch (getAvailableCards().size()){
            case 1:
                if(getHand().getValue()<0.03){
                    if(availableActions.contains(Action.FOLD)){
                        setAction(Action.FOLD);
                    }
                }
                else if(getHand().getValue() < 0.10){
                    if(availableActions.contains(Action.CHECK)){
                        setAction(Action.CHECK);
                    }
                    if(availableActions.contains(Action.CALL)){
                        setAction(Action.CALL);
                    }
                }
                else{
                    if(availableActions.contains(Action.FIXEDRAISE)){
                        setAction(Action.FIXEDRAISE);
                    }else{
                        if(availableActions.contains(Action.CHECK)){
                            setAction(Action.CHECK);
                        }
                         if(availableActions.contains(Action.CALL)){
                            setAction(Action.CALL);
                        }
                    }
                }
            case 2:
                if(getHand().getValue()<0.07){
                    if(availableActions.contains(Action.FOLD)){
                        setAction(Action.FOLD);
                    }
                }
                else if(getHand().getValue()< 1 ){
                    if(availableActions.contains(Action.CHECK)){
                        setAction(Action.CHECK);
                    }
                    if(availableActions.contains(Action.CALL)){
                        setAction(Action.CALL);
                    }
                }
                else{
                    if(availableActions.contains(Action.FIXEDRAISE)){
                        setAction(Action.FIXEDRAISE);
                    }else{
                        if(availableActions.contains(Action.CHECK)){
                            setAction(Action.CHECK);
                        }
                         if(availableActions.contains(Action.CALL)){
                            setAction(Action.CALL);
                        }
                    }
                }
                break;
            case 3:
                if(getHand().getValue()<0.12){
                    if(availableActions.contains(Action.FOLD)){
                        setAction(Action.FOLD);
                    }
                }
                else if(getHand().getValue() < 1){
                    if(availableActions.contains(Action.CHECK)){
                        setAction(Action.CHECK);
                    }
                    if(availableActions.contains(Action.CALL)){
                        setAction(Action.CALL);
                    }
                }
                else{
                    if(availableActions.contains(Action.FIXEDRAISE)){
                        setAction(Action.FIXEDRAISE);
                    }else{
                        if(availableActions.contains(Action.CHECK)){
                            setAction(Action.CHECK);
                        }
                         if(availableActions.contains(Action.CALL)){
                            setAction(Action.CALL);
                        }
                    }
                }
                break;
            case 4:
                if(getHand().getValue()<1){
                    if(availableActions.contains(Action.FOLD)){
                        setAction(Action.FOLD);
                    }
                }
                else if(getHand().getValue() < 1.07){
                    if(availableActions.contains(Action.CHECK)){
                        setAction(Action.CHECK);
                    }
                    if(availableActions.contains(Action.CALL)){
                        setAction(Action.CALL);
                    }
                }
                else{
                    if(availableActions.contains(Action.FIXEDRAISE)){
                        setAction(Action.FIXEDRAISE);
                    }else{
                        if(availableActions.contains(Action.CHECK)){
                            setAction(Action.CHECK);
                        }
                         if(availableActions.contains(Action.CALL)){
                            setAction(Action.CALL);
                        }
                    }
                }
                break;
            case 5:
                if(getHand().getValue()<2){
                    if(availableActions.contains(Action.FOLD)){
                        setAction(Action.FOLD);
                    }
                }
                else if(getHand().getValue() < 3){
                    if(availableActions.contains(Action.CHECK)){
                        setAction(Action.CHECK);
                    }
                    if(availableActions.contains(Action.CALL)){
                        setAction(Action.CALL);
                    }
                }
                else{
                    if(availableActions.contains(Action.FIXEDRAISE)){
                        setAction(Action.FIXEDRAISE);
                    }else{
                        if(availableActions.contains(Action.CHECK)){
                            setAction(Action.CHECK);
                        }
                         if(availableActions.contains(Action.CALL)){
                            setAction(Action.CALL);
                        }
                    }
                }
                break;
            case 6:
                if(getHand().getValue()<2.10){
                    if(availableActions.contains(Action.FOLD)){
                        setAction(Action.FOLD);
                    }
                }
                else if(getHand().getValue() < 3.11){
                    if(availableActions.contains(Action.CHECK)){
                        setAction(Action.CHECK);
                    }
                    if(availableActions.contains(Action.CALL)){
                        setAction(Action.CALL);
                    }
                }
                else{
                    if(availableActions.contains(Action.FIXEDRAISE)){
                        setAction(Action.FIXEDRAISE);
                    }else{
                        if(availableActions.contains(Action.CHECK)){
                            setAction(Action.CHECK);
                        }
                         if(availableActions.contains(Action.CALL)){
                            setAction(Action.CALL);
                        }
                    }
                }
                break;
            case 7:
                if(getHand().getValue()<3){
                    if(availableActions.contains(Action.FOLD)){
                        setAction(Action.FOLD);
                    }
                }
                else if(getHand().getValue() < 4){
                    if(availableActions.contains(Action.CHECK)){
                        setAction(Action.CHECK);
                    }
                    if(availableActions.contains(Action.CALL)){
                        setAction(Action.CALL);
                    }
                }
                else{
                    if(availableActions.contains(Action.FIXEDRAISE)){
                        setAction(Action.FIXEDRAISE);
                    }else{
                        if(availableActions.contains(Action.CHECK)){
                            setAction(Action.CHECK);
                        }
                         if(availableActions.contains(Action.CALL)){
                            setAction(Action.CALL);
                        }
                    }
                }break;
            default:
                setAction(Action.FOLD);
                break;
        }
    }
    /**
     * Ο παίκτης διώχνει απο τα κλειστά του φύλλα όσα φύλλα δεν συμβάλουν στην 
     * αξία του χεριόυ του, και είναι μικροτερα του 9
     */

    @Override
    public void changeCards() {
        List<Card> faceDCards = getFaceDownCards();
        List<Card> availCards = getAvailableCards();
        Iterator it = faceDCards.iterator();
        for(int i = 0 ;i<faceDCards.size();i++){
            Card card = faceDCards.get(i);
            double value = getHand().getValue();
            value = value * 100;
            int num = (int)value;
            if(card.getRank().ordinal() < 7 && card.getRank().ordinal()!= value - 2){
                faceDCards.remove(card);
                availCards.remove(card);
            }
        }
        updateHand();
    }
}
