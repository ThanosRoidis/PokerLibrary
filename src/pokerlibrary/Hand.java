/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerlibrary;

import java.lang.Math;

/** Η κλάση αυτή υλοποιεί την έννοια του 'χεριού' στο Πόκερ, δηλαδή μία ομάδα που αποτελείται μέχρι και απο 5 φύλλα(Card) 
 *
 * @author Αθανάσιος Ροίδης
 * @version 1.13.1
 * @see Card
 */
public class Hand {
    
    private Card[] cards;
    
    /**
     * Αντιπροσωπεύει την αξία του χεριού
     */
    private double value;
    
    /**
     * Πίνακας που τα στοιχέια του δείχνουν πόσα φύλλα του κάθε rank έχει το χέρι 
     */
    private int numberOfRanks[]; 
    
    /**
     * Τα φύλλα του χεριού αρχικοποιούνται, και παράλληλα βρίσκει πια είναι η αξία του χεριού
     * 
     * @param cards Ο πίνακας απο Card που αποτελούν το χέρι 
     * @see Card
     */
    
    public Hand(Card cards[]){
        if(cards.length <= 5){
            this.cards = cards;
            numberOfRanks = new int[13];
            for(int i=0;i<numberOfRanks.length;i++){
                numberOfRanks[i]=0;
            }
            for(Card card : this.cards){ 
                int a = card.getRank().ordinal();
                numberOfRanks[a]++;
            }
          evaluateHand();
        }
    }
    
    /** Ελέγχει αν το χέρι είναι 'High Card' ή αλλιώς και 'No Pair', και παράλληλα ανανεώνει την αξία του χεριού
     * 
     * @return το αν ειναι 'High Card'
     */
    
    private boolean isNoPair(){
        if(!isStraight()){
            int i;
            for(i=0;i<numberOfRanks.length;i++){
                if(numberOfRanks[i]>1){
                    return false;
                }
            }
            value = 0;
            int power = 0;
            for(i=numberOfRanks.length-1;i>=0;i--){
                if(numberOfRanks[i] == 1){
                    power+=2;
                    value+=(i+2)*java.lang.Math.pow(10,-power);                
                }
            }
            return true;
        }
        return false;
    }
    
    /**
     * Ελέγχει αν το χέρι είναι 'Ένα Ζευγάρι' ή αλλιώς 'One Pair', και παράλληλα ανανεώνει την αξία του χεριού
     * @return το αν ειναι 'One Pair'
     */
    
    private boolean isOnePair(){
        int numberOfPairs = 0;
        int pairPosition = 0;
        for(int i = 0 ;i<numberOfRanks.length;i++){
            if(numberOfRanks[i]==2){
                pairPosition = i;
                numberOfPairs++;
            }
        }
        if(numberOfPairs != 1 ){
            return false;
        }
        value = 1;
        value+=(pairPosition+2)*0.01;
        int power = 2;
        for(int i = numberOfRanks.length-1;i>=0;i--){
            if(numberOfRanks[i]==1){
                power+=2;
                value+=(i+2)*java.lang.Math.pow(10,-power); 
            }
        }
        return true;
    }
    
    /**
     * Ελέγχει αν το χέρι είναι 'Δύο Ζευγάρι' ή αλλιώς 'Two Pair', και παράλληλα ανανεώνει την αξία του χεριού
     * @return το αν ειναι 'Two Pair'
     */
    
    private boolean isTwoPair(){ 
        int numberOfPairs = 0;
        int firstPairPosition = -1;
        int secondPairPosition = -1;
        int highCardPosition = -1;
        for(int i = 0 ;i<numberOfRanks.length;i++){
            if(numberOfRanks[i]==2){
                if(firstPairPosition == -1){
                    firstPairPosition = i;
                }
                else{
                    secondPairPosition = i;
                }
                numberOfPairs++;
            }
            if(numberOfRanks[i] == 1){
                highCardPosition = i;
            }
        }
        if(numberOfPairs!=2){
            return false;
        }
        value = 2;
        value += (secondPairPosition+2)*0.01;
        value += (firstPairPosition+2)*0.0001;
        value += (highCardPosition+2)*0.000001;
        return true;
    }
    
    /**Ελέγχει αν το χέρι είναι 'Τρία Όμοια' ή αλλιώς 'Three-of-a-kind', και παράλληλα ανανεώνει την αξία του χεριού
     * @return το αν ειναι 'Three-of-a-kind'
     */
    
    
    private boolean isThreeOfAKind(){
        int threeOfAKindPosition = -1;
        int firstHighCardPosition = -1;
        int secondHighCardPosition = -1;
        for(int i=0;i<numberOfRanks.length;i++){
            if(numberOfRanks[i]==3){
                threeOfAKindPosition = i;
            }
            if(numberOfRanks[i]==1){
                if(firstHighCardPosition == -1){
                    firstHighCardPosition = i;
                }
                else{
                    secondHighCardPosition = i;
                }
            }
        }
        if(threeOfAKindPosition == -1){
            return false;
        }
        value = 3;
        value+=(threeOfAKindPosition + 2)*0.01;
        value+=(secondHighCardPosition + 2)*0.0001;
        value+=(firstHighCardPosition + 2)*0.000001;
        return true;
    }
    /*
     * Ελέγχει αν το χέρι είναι 'Straight', και παράλληλα ανανεώνει την αξία του χεριού
     * @return το αν ειναι 'Straight'
     */
    
    private boolean isStraight(){
         boolean flag = false;
         int i;
         for(i=0;i<=numberOfRanks.length-5;i++){
            if(numberOfRanks[i] == 1){
                for(int j=0;j<5;j++){
                    if(numberOfRanks[i+j] == 1){
                        flag = true;
                    }
                    else{
                        flag = false;
                        break;
                    }     
                }    
            }
            if(flag == true){
                break;
            }
        }
        if(numberOfRanks[0] == 1 && numberOfRanks[1] == 1 && numberOfRanks[2] == 1 && numberOfRanks[3] == 1 && numberOfRanks[12] == 1){
            value = 4.05;
            return true;
        }
        if(flag == false){
            return false;
        }
        value = 4;
        value +=(i+2+4)*0.01;
        return true;
    }
    
    /*
     * Ελέγχει αν το χέρι είναι 'Flush', και παράλληλα ανανεώνει την αξία του χεριού
     * @return το αν ειναι 'Flush'
     */
    
    
    private boolean isFlush(){
        boolean flag = false;
        Suit suit = cards[0].getSuit();
        int count = 1;
        for(int i=0;i<cards.length;i++){
            if(suit.equals(cards[i].getSuit())){
                flag = true;
                count++;
            }
            else{
                flag = false;
                break;
            }
        }
        if(flag == false || count < 5){
            return false;
        }
        value = 5;
        int power = 0;
        for(int i = numberOfRanks.length-1;i>=0;i--){
            if(numberOfRanks[i]!=0){
                power+=2;
                value+=(i+2)*java.lang.Math.pow(10,-power);
            }
        }
        return true;
    }
    
    /*
     * Ελέγχει αν το χέρι είναι 'Full House', και παράλληλα ανανεώνει την αξία του χεριού
     * @return το αν ειναι 'Full House'
     */
    
    private boolean isFullHouse(){
      int threeOfAKindPosition = -1;
      int pairPosition = -1;
      for(int i=0;i<numberOfRanks.length;i++){
          if(numberOfRanks[i] == 3){
              threeOfAKindPosition = i;
          }
          if(numberOfRanks[i] == 2){
              pairPosition = i;
          }
      }
      if(threeOfAKindPosition == -1 || pairPosition == -1){
          return false;
      }
      value = 6;
      value+=(threeOfAKindPosition + 2)*0.01;
      value+=(pairPosition + 2)*0.0001;
      return true;
    }
    
    /*
     * Ελέγχει αν το χέρι είναι 'Four-of-a-kind' ή αλλιώς 'One Pair', και παράλληλα ανανεώνει την αξία του χεριού
     * @return το αν ειναι 'Four-of-a-kindr'
     */
    
    private boolean isFourOfAKind(){
        int fourOfAkindPosition = -1;
        int kickerPosition = -1;
        for(int i=0;i<numberOfRanks.length;i++){
            if(numberOfRanks[i] == 4){
                fourOfAkindPosition = i;
            }
            if(numberOfRanks[i] == 1){
                kickerPosition = i;
            }
        }
        if(fourOfAkindPosition == -1 || kickerPosition ==-1){
            return false;
        }
        value = 7;
        value+=(fourOfAkindPosition + 2)*0.01;
        value+=(kickerPosition + 2)*0.0001;
        return true;
    }
    
    /*
     * Ελέγχει αν το χέρι είναι 'Straight Flush', και παράλληλα ανανεώνει την αξία του χεριού
     * @return το αν ειναι 'One Pair'
     */
    private boolean isStraightFlush(){
        if(isFlush() && isStraight()){
            value+=4;
            return true;
        }
        return false;
    }
    
  /**
   * Βρίσκει μεγαλύερη αξία του χεριού και την ανανεώνει
   */
    
    private void evaluateHand(){
        if(cards.length == 0){
            value = 0;
            return;
        }
        if(isStraightFlush()) return;
        if(isFourOfAKind()) return;
        if(isFullHouse()) return;
        if(isFlush()) return;
        if(isStraight()) return;
        if(isThreeOfAKind()) return;
        if(isTwoPair()) return;
        if(isOnePair()) return;
        if(isNoPair()) return;
    }
    
    
    
    /**
     * Επιστρέφει την αξία του χεριού
     * @return αξία του χεριού
     */
    
    public double getValue() {
        return value;
    }
    /**
     * Βρίσκει το String  της αξίας του χεριού
     * @return Το String της αξίας του χεριού 
     */
    
    @Override
    public String toString(){
        StringBuilder description = new StringBuilder();
        switch ((int)value){
                case 0:
                    description.append("High Card ");
                    double num1 = value;
                    num1 -= (int)value;
                    num1  = num1*100;
                    int num2 = (int)num1;
                    if(num2 == 14){
                        description.append("A");
                    }
                    else if(num2 == 13){
                        description.append("K");
                    }
                    else if(num2 == 12){
                         description.append("Q");
                    }
                    else if( num2 == 11){
                         description.append("J");
                    }
                    else{
                        description.append(Integer.toString(num2));
                    }
                    break;
                    
                case 1:
                    description.append("One Pair of ");
                    num1 = value;
                    num1 -= (int)value;
                    num1  = num1*100;
                    num2 = (int)num1;
                    if(num2 == 14){
                        description.append("A's");
                    }
                    else if(num2 == 13){
                        description.append("K's");
                    }
                    else if(num2 == 12){
                         description.append("Q's");
                    }
                    else if( num2 == 11){
                         description.append("J's");
                    }
                    else{
                        description.append(Integer.toString(num2)).append("'s");
                    }
                    break;
                    
                case 2:
                    description.append("Two Pair of ");
                    num1 = value;
                    num1 -= (int)value;
                    num1 = num1*100;
                    num2 = (int)num1;
                    if(num2 == 14){
                        description.append("A's");
                    }
                    else if(num2 == 13){
                        description.append("K's");
                    }
                    else if(num2 == 12){
                         description.append("Q's");
                    }
                    else if( num2 == 11){
                         description.append("J's");
                    }
                    else{
                        description.append(Integer.toString(num2)).append("'s");
                    }
                    
                    description.append(" and ");
                    num1 -= (int)num1;
                    num1 = num1*100;
                    num2 = (int)num1;
                    if(num2 == 14){
                        description.append("A's");
                    }
                    else if(num2 == 13){
                        description.append("K's");
                    }
                    else if(num2 == 12){
                         description.append("Q's");
                    }
                    else if( num2 == 11){
                         description.append("J's");
                    }
                    else{
                        description.append(Integer.toString(num2)).append("'s");
                    }
                    break;
                    
                case 3:
                    description.append("Three of a kind of ");
                    num1 = value;
                    num1 -= (int)value;
                    num1 = num1*100;
                    num2 = (int)num1;
                    if(num2 == 14){
                        description.append("A's");
                    }
                    else if(num2 == 13){
                        description.append("K's");
                    }
                    else if(num2 == 12){
                         description.append("Q's");
                    }
                    else if( num2 == 11){
                         description.append("J's");
                    }
                    else{
                        description.append(Integer.toString(num2)).append("'s");
                    }
                    break;
                    
                case 4:
                    description.append("Straight to ");
                    num1 = value;
                    num1 -= (int)value;
                    num1 = num1*100;
                    num2 = (int)num1;
                    if(num2 == 14){
                        description.append("A");
                    }
                    else if(num2 == 13){
                        description.append("K");
                    }
                    else if(num2 == 12){
                         description.append("Q");
                    }
                    else if( num2 == 11){
                         description.append("J");
                    }
                    else{
                        description.append(Integer.toString(num2));
                    }
                    break;
                    
                case 5:
                    description.append("Flush of ");
                    num1 = value;
                    num1 -= (int)value;
                    num1 = num1*100;
                    num2 = (int)num1;
                    if(num2 == 14){
                        description.append("A's");
                    }
                    else if(num2 == 13){
                        description.append("K's");
                    }
                    else if(num2 == 12){
                         description.append("Q's");
                    }
                    else if( num2 == 11){
                         description.append("J's");
                    }
                    else{
                        description.append(Integer.toString(num2)).append("'s");
                    }
                    break;
                    
                case 6:
                    description.append("Full house of ");
                    num1 = value;
                    num1 -= (int)value;
                    num1 = num1*100;
                    num2 = (int)num1;
                    if(num2 == 14){
                        description.append("A's");
                    }
                    else if(num2 == 13){
                        description.append("K's");
                    }
                    else if(num2 == 12){
                         description.append("Q's");
                    }
                    else if( num2 == 11){
                         description.append("J's");
                    }
                    else{
                        description.append(Integer.toString(num2)).append("'s");
                    }
                    
                    
                    description.append(" and ");
                    num1 -= (int)num1;
                    num1 = num1*100;
                    num2 = (int)num1;
                    if(num2 == 14){
                        description.append("A's");
                    }
                    else if(num2 == 13){
                        description.append("K's");
                    }
                    else if(num2 == 12){
                         description.append("Q's");
                    }
                    else if( num2 == 11){
                         description.append("J's");
                    }
                    else{
                        description.append(Integer.toString(num2)).append("'s");
                    }
                    break;
                    
                case 7:
                    description.append("Four of a kind of ");
                    num1 = value;
                    num1 -= (int)value;
                    num1 = num1*100;
                    num2 = (int)num1;
                    if(num2 == 14){
                        description.append("A's");
                    }
                    else if(num2 == 13){
                        description.append("K's");
                    }
                    else if(num2 == 12){
                         description.append("Q's");
                    }
                    else if( num2 == 11){
                         description.append("J's");
                    }
                    else{
                        description.append(Integer.toString(num2)).append("'s");
                    }
                    break;
                    
                case 8:
                    description.append("Straight flush to ");
                    num1 = value;
                    num1 -= (int)value;
                    num1 = num1*100;
                    num2 = (int)num1;
                    if(num2 == 14){
                        description.append("A");
                    }
                    else if(num2 == 13){
                        description.append("K");
                    }
                    else if(num2 == 12){
                         description.append("Q");
                    }
                    else if( num2 == 11){
                         description.append("J");
                    }
                    else{
                        description.append(Integer.toString(num2));
                    }
                    break;
                default:
                    break;
        }
        
        return description.toString();
    }
   
}
