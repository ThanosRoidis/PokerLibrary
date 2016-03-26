/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerlibrary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Το παιχνίδι Πόκερ με διεπαφή μέσω της γραμμής εντολών, με υποστήριξη 3-8 παικτών
 * @author Αθανάσιος Ροίδης
 * @version 1.13.1
 */
public class PokerGameCLI {
    Scanner input;
    /**
     * Το τραπεζι στο οποίο εξελίζεται το παιχνίδι
     */
    Table table;
    
    
    public PokerGameCLI(){
        input = new Scanner(System.in);
        
        String actorsName;
        System.out.print("Choose Name:");
        actorsName = input.nextLine();
       
        int startingStack;
        System.out.print("\nStarting Stack(100 to 250):");
        startingStack = input.nextInt();
        if(startingStack < 100){
            startingStack = 100;
        }
        else if(startingStack > 250){
            startingStack = 250;
        }
        Actor actor = new Actor(actorsName,startingStack);
        
        
        int numberOfAIPlayers;
        System.out.print("\nNumber Of AI Players (2 to 7):");
        numberOfAIPlayers = input.nextInt();
        
        if(numberOfAIPlayers < 2){
            numberOfAIPlayers = 2;
        }
        else
        if(numberOfAIPlayers > 7){
            numberOfAIPlayers = 7;
        }
        
        
        int difficultyChoice;
        System.out.print("\n\n\n\n\n\n-AI DIFFICULTY \n\n");
        System.out.print("1.Easy.\n\n");
        System.out.print("\nChoose AI difficulty: ");
        difficultyChoice = input.nextInt();
        
        int pokerGameChoice;
        System.out.print("\n\n\n\n\n\n-POKER GAME\n\n");
        System.out.print("1.Texas Holdem.\n\n");
        System.out.print("2.Seven Card Stud.\n\n");
        System.out.print("3.Five Card Draw.\n\n");
        System.out.print("Choose a Poker Game:");
        pokerGameChoice = input.nextInt();
        
        PokerGame pokerGame;
        
        switch(pokerGameChoice){
            case 1:
                pokerGame = new TexasHoldem();
                break;
            case 2:
                pokerGame = new SevenCardStud();
                break;
            case 3:
                pokerGame = new FiveCardDraw();
                break;
            default:
                pokerGame = new TexasHoldem();
                break;               
        }     
        
        
        int bettingStructureChoice;
        System.out.print("\n\n\n\n\n\n-BETTING STRUCTURE\n\n");
        System.out.print("1.Fixed Limit.\n\n");
        System.out.print("Choose a Betting Structure:");
        bettingStructureChoice = input.nextInt();
        input.nextLine();
        
        BettingStructure bettingStructure;
        
        switch (bettingStructureChoice){
            case 1:
                bettingStructure = new FixedLimit();
                break;
            default:
                bettingStructure = new FixedLimit();
                break;
        }
        
        
        
        List<Player> players = new ArrayList<>();
        
        ArrayList<String> aiNames = new ArrayList<>();
        aiNames.add("Bob");
        aiNames.add("James");
        aiNames.add("John");
        aiNames.add("George");
        aiNames.add("Luke");
        aiNames.add("Alex");
        aiNames.add("Bill");
        Collections.shuffle(aiNames);
        int aiNameCounter = 0;
        
        
        Random random = new Random();
        int actorPosition = random.nextInt(numberOfAIPlayers + 1);
        
        
        
        switch (difficultyChoice){
            case 1:
                for(int i = 0;i<numberOfAIPlayers + 1;i++){
                    if(i == actorPosition){
                        players.add(actor);
                    }
                    else{
                        players.add(new EasyAIPlayer(aiNames.get(aiNameCounter++),startingStack));
                    }
                }
                break;
            default:
                break;
        }
        
        table = new Table(players, bettingStructure, pokerGame);
        for(int i = 0;i<60;i++){
            System.out.print("\n");
        }
        startGame();
    }
    
    /**
     * Η συνάρτηση μέσα στην οποία εξελίζεται το παιχνίδι
     */
    public void startGame(){
        PokerGame pokerGame = table.getPokerGame();
        BettingStructure bettingStructure = table.getBettingStructure();
        List<Player> players = table.getPlayers();
        /**
         * Ο τρέχων παίκτης
         */
        Player currentPlayer;
        while(!table.gameIsOver()){
            pokerGame.resetRoundCounter();
            while(pokerGame.getRoundCounter()<=pokerGame.getNumberOfRounds()){
               currentPlayer = table.getFirstPlayer();
               pokerGame.startRound(table);
               bettingStructure.startBettingRound(players, currentPlayer);
               
               //Oσοι παικτες έκαναν blind προσπερνουνται
               
               if(bettingStructure.getBettingRoundCounter() == 1){
                   for(int i = 0;i<bettingStructure.getNumberOfBlinds();i++){
                       currentPlayer = table.getNextPlayer(currentPlayer);
                   }
               } 
               update();
               while(!bettingStructure.bettingRoundIsOver(table, currentPlayer)){
                   if(!currentPlayer.isFolded()){
                        currentPlayer.think(bettingStructure.getAvailableActions(currentPlayer), table);
                        if(currentPlayer instanceof Actor){
                            showAvailableActions(bettingStructure.getAvailableActions(currentPlayer),currentPlayer);
                        }

                        bettingStructure.executeAction(currentPlayer);
                        System.out.println(currentPlayer.getName() + " " + currentPlayer.getAction().getDescritpion());
                        currentPlayer = table.getNextPlayer(currentPlayer);
                   }
               }
               
               bettingStructure.endBettingRound(players);
               pokerGame.endRound(table);
               System.out.print("\nBetting round is over!!!\nType anything and press \'Enter\' to continue\n");
               input.nextLine();
               update();
               if(pokerGame.allowsCardsChange()){
                   for(Player player : players){
                       if(player instanceof Actor){
                           showCardsToChange((Actor)player);
                       }
                       currentPlayer.changeCards();
                   }
               }
               if(pokerGame.getRoundCounter() > pokerGame.getNumberOfRounds()){
                   pokerGame.endRound(table);
                   table.getCommunityCards().clear();
                   System.out.print("Poker Game is over!!\n");
                   Player bestHandPlayer = currentPlayer;
                   for(Player player:players){
                        if(player.getHand().getValue()> bestHandPlayer.getHandValue()){
                                bestHandPlayer = player;
                        }
                   }
                   System.out.print("Best Hand:" + bestHandPlayer.getHand().toString() + '(' + bestHandPlayer.getName() + ')' );
                   System.out.print("\nType anything and press \'Enter\' to continue\n");
                   table.prepareForNextPokerRound();
                   input.nextLine();
                   break;
               }
               System.out.println("\n\n\n\n\n\n\n\n\n");
           }
           bettingStructure.distributePots();
        }
        Player winner = players.get(0);
        for(Player player : players){
            if(player.getStack() > winner.getStack()){
                winner = player;
            }
        }
        System.out.print("GAME OVER!!!" + '\n' + "Winner:" + winner.getName() + "\n" + "Type anything and press \'Enter\' to exit...");
        input.nextLine();
    }
    
    
    
    
    /**
     * Εμφανίζει την κατάσταση όλων των παικτών
     */
    public void update(){
        for(Player player:table.getPlayers()){
            System.out.println("Name:" + player.getName());
            System.out.println("Stack:" + player.getStack());
            System.out.println("Wager:" + player.getWager());
            System.out.println("Action:" + player.getAction().getDescritpion());
            System.out.print("Cards:");
            if(player instanceof Actor){
                for(Card card: player.getFaceDownCards()){
                    System.out.print(card.getDescription() + ',');
                }
                for(Card card: player.getFaceUpCards()){
                    System.out.print(card.getDescription() + ',');
                }
            }else if(player instanceof AIPlayer){
                for(Card card: player.getFaceUpCards()){
                    System.out.print(card.getDescription() + ',');
                }
            }
            System.out.println("\n");
        }
        if(!table.getCommunityCards().isEmpty()){
            System.out.print("Community Cards:");
            for(Card card :table.getCommunityCards()){
                System.out.print(card.getDescription() + ',');
            }
        }
        System.out.println("\n\n\n");
    }

    private void showAvailableActions(List<Action> availableActions, Player currentPlayer) {
        int i=1;
        System.out.print("\n\n\nAvailable Actions:\n\n");
        for(Action action :availableActions){
            System.out.print(i++ + "." + action.toString() + "\n\n");
        }
        System.out.print("Choose an Action:");
        String actionString = input.nextLine();
        char c = actionString.charAt(0);
        int action = Character.getNumericValue(c);
        if(action<1){
            action = 1;
        }else if(action > availableActions.size()){
            action = availableActions.size();
        }
        currentPlayer.setAction(availableActions.get(action - 1));
        System.out.println();
        return;
    }

    private void showCardsToChange(Actor actor){
        
       List<Card> faceDownCards = actor.getFaceDownCards();
       while(faceDownCards.size() > 0){
           int i=0; 
           System.out.print("\n\n\n");
           for(Card card: faceDownCards){
                System.out.println((++i) + "." + card.getDescription());
           }
           System.out.print("\n\nChoose a card to change(type \"OK\" to stop):");
           String cardInput = input.nextLine();
           if(cardInput.toLowerCase().equals("ok")){
                break;
           }else{
                char c = cardInput.charAt(0);
                int cardToChange = Character.getNumericValue(c);
                if(cardToChange <= i && i>0){
                    actor.getAvailableCards().remove(actor.getFaceDownCards().remove(cardToChange - 1));
                    actor.updateHand();
                }
           }
           update();
       }
    }
}
