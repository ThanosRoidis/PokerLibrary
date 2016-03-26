/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerlibrary;

import images.Resource;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/** Μια γραφική διεπαφή για το παιχνίδι Πόκερ,που υποστηρίζει 3-8 παίκτες
 *
 * @author Αθανάσιος Ροίδης
 * @version 1.13.1
 */
public class PokerGameGUI extends JFrame{
    
    /**
     * Τα playerPanels των παίκτων του τραπεζιού
     * @see PlayerPanel
     */
    List<PlayerPanel> playerPanels;
    
    JDialog newGameFrame;
    MenuItem newGame;
    
    
    
     
    JPanel actionPanel;
    /**
     * Η τρέχουσα κατάσταση του παιχνδιού
     */
    JLabel gameFeed;
    /**
     * Οι θέσεις των παικτών 1,2
     */
    JPanel leftPanel;
    /**
     * Οι θέσεις των παικτών 3-6
     */
    JPanel topPanel;
    /**
     * Οι θέσεις των παικτών 7,8
     */
    JPanel rightPanel;
    
    
    /**
     * Το Panel στο οποίο φαίνονται τα κοινά φύλλα(communityCards) του τραπεζιου
     * @see Table#communityCards
     * @see CommunityCardsPanel
     */
    CommunityCardsPanel communityCardsPanel;
    
    JButton continueButton;
    
    JButton raiseButton;
    
    JButton callButton;
    
    JButton checkButton;
    
    JButton betButton;
    
    JButton foldButton;
    
    JButton startGameButton;
 
    
    /**
     * Το τραπεζι στο οποίο εξελίζεται το παιχνίδι
     */
    Table table;
    
    /**
     * Ο τρέχων παίκτης του παιχνιδιού
     */
    Player currentPlayer;
    
   
    
    /**
     *
     */
    public PokerGameGUI(){
        initialize();
    }
    
    /**
     * Αρχικοποιεί το frame
     */
    private void initialize(){
        setTitle("PokerApp");
        setPreferredSize(new Dimension(622,550));
        playerPanels = new ArrayList<>();
       
        setSize(622,550);
       
        
        getContentPane().setBackground(new Color(0,128,0));
        setLocationRelativeTo(null);
        setResizable(false);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        BorderLayout basicLayout = new BorderLayout();
        setLayout(basicLayout);       
        
        
        
        leftPanel = new JPanel();
        GridLayout leftPanelLayout = new GridLayout(2,1);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setVgap(40);
        leftPanel.setOpaque(false);
        add(leftPanel,BorderLayout.WEST);
       
       
        
        topPanel = new JPanel();
        GridLayout topPanelLayout = new GridLayout(1,4);
        topPanelLayout.setHgap(40);
        topPanel.setOpaque(false);
        topPanel.setLayout(topPanelLayout);
        add(topPanel,BorderLayout.NORTH);
        
        
        
        rightPanel = new JPanel();
        GridLayout rightPanelLayout = new GridLayout(2,1);
        rightPanelLayout.setVgap(40);
        rightPanel.setOpaque(false);
        rightPanel.setLayout(rightPanelLayout);
        add(rightPanel,BorderLayout.EAST);
        
     
        
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(100,100));
        bottomPanel.setBackground(new Color(0,128,0));
        gameFeed = new JLabel();
        bottomPanel.add(gameFeed,BorderLayout.NORTH);
        actionPanel = new JPanel();
        actionPanel.setBorder(BorderFactory.createBevelBorder(1));
        actionPanel.setLayout(new FlowLayout());
        actionPanel.setBackground(new Color(0,128,0));
        bottomPanel.add(actionPanel,BorderLayout.SOUTH);
        add(bottomPanel,BorderLayout.SOUTH);
        
        continueButton = new JButton("Continue");
        continueButton.setPreferredSize(new Dimension(100,50));
        continueButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                do{
                    currentPlayer = table.getNextPlayer(currentPlayer);
                }
                while(currentPlayer.isFolded());
                gameLogic();    
            }
        });
        
        startGameButton = new JButton("Start Game");
        startGameButton.setPreferredSize(new Dimension(100,50));
        startGameButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                communityCardsPanel.empty();
                currentPlayer = table.getFirstPlayer();
                table.getPokerGame().startRound(table);
                table.getBettingStructure().startBettingRound(table.getPlayers(), currentPlayer);
                for(int i=0 ;i < table.getBettingStructure().getNumberOfBlinds();i++){
                    do{
                            currentPlayer = table.getNextPlayer(currentPlayer);
                    }
                    while(currentPlayer.isFolded());
                }
               
                for(PlayerPanel panel:playerPanels){
                    panel.update();
                }
                gameLogic();
            }
        });
        
        
        
        raiseButton = new JButton("Raise");
        raiseButton.setPreferredSize(new Dimension(100,50));
        raiseButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                currentPlayer.setAction(Action.FIXEDRAISE);
                table.getBettingStructure().executeAction(currentPlayer);
                actionPanel.removeAll();
                actionPanel.revalidate();
                actionPanel.repaint();
                for(PlayerPanel playerPanel:playerPanels){
                    playerPanel.update();
                }
                gameFeed.setText(currentPlayer.getName() + ' ' + currentPlayer.getAction().getDescritpion());
                actionPanel.add(continueButton);
                
            }
        });
        
        callButton = new JButton("Call");
        callButton.setPreferredSize(new Dimension(100,50));
        callButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                currentPlayer.setAction(Action.CALL);
                table.getBettingStructure().executeAction(currentPlayer);
                for(PlayerPanel panel:playerPanels){
                    panel.update();
                }
                actionPanel.removeAll();
                actionPanel.revalidate();
                actionPanel.repaint();
                for(PlayerPanel playerPanel:playerPanels){
                    playerPanel.update();
                }
                gameFeed.setText(currentPlayer.getName() + ' ' + currentPlayer.getAction().getDescritpion());
                actionPanel.add(continueButton);
                
            }
        });
        
        checkButton= new JButton("Check");
        checkButton.setPreferredSize(new Dimension(100,50));
        checkButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                currentPlayer.setAction(Action.CHECK);
                table.getBettingStructure().executeAction(currentPlayer);
                actionPanel.removeAll();
                actionPanel.revalidate();
                actionPanel.repaint();
                for(PlayerPanel playerPanel:playerPanels){
                    playerPanel.update();
                }
                gameFeed.setText(currentPlayer.getName() + ' ' + currentPlayer.getAction().getDescritpion());
                actionPanel.add(continueButton);
                
            }
        });
        
        
        betButton= new JButton("Bet");
        betButton.setPreferredSize(new Dimension(100,50));
        betButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                currentPlayer.setAction(Action.BET);
                table.getBettingStructure().executeAction(currentPlayer);
                actionPanel.removeAll();
                actionPanel.revalidate();
                actionPanel.repaint();
                for(PlayerPanel playerPanel:playerPanels){
                    playerPanel.update();
                }
                gameFeed.setText(currentPlayer.getName() + ' ' + currentPlayer.getAction().getDescritpion());
                actionPanel.add(continueButton);
                
            }
        });
        
        foldButton = new JButton("Fold");
        foldButton.setPreferredSize(new Dimension(100,50));
        foldButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                currentPlayer.setAction(Action.FOLD);
                table.getBettingStructure().executeAction(currentPlayer);
                actionPanel.removeAll();
                actionPanel.revalidate();
                actionPanel.repaint();
                for(PlayerPanel playerPanel:playerPanels){
                    playerPanel.update();
                }
                gameFeed.setText(currentPlayer.getName() + ' ' + currentPlayer.getAction().getDescritpion());
                actionPanel.add(continueButton);
            }
        });
        
        
        
        //------------MENUBAR-----------------------------
        
        MenuBar menuBar = new MenuBar();
        Menu file = new Menu("File");
        menuBar.add(file);
        newGame = new MenuItem("New Game");
        newGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                newGame();
                
            }
        });
       
        MenuItem quit = new MenuItem("Quit");
        quit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
                
            }
        });
         
        file.add(newGame);
        file.add(quit);
        setMenuBar(menuBar);
        
        
        setVisible(true);
    }
    
    
    
    
    /**
     * Τοποθετεί όλους τους παίκτες μια Λιστας(List) στο JFrame
     * @param players οι παίκτες οι τοποθετούνται
     */
    public void populate(List<Player> players){
        leftPanel.removeAll();
        topPanel.removeAll();
        rightPanel.removeAll();
        revalidate();
        repaint();
        
        PlayerPanel panel1 = new PlayerPanel(players.get(1));
        playerPanels.add(panel1);
        
        
        panel1.setBorder(BorderFactory.createMatteBorder(40,0,0,0,new Color(0,128,0)));
        
        
        PlayerPanel panel0 = new PlayerPanel(players.get(0));
        panel0.setBorder(BorderFactory.createMatteBorder(0,0,40,0,new Color(0,128,0)));
        playerPanels.add(panel0);
        leftPanel.add(panel1);
        leftPanel.add(panel0);
        int i=2;
        for(i=2;i < players.size();i++){
            PlayerPanel panel = new PlayerPanel(players.get(i));
            playerPanels.add(panel);
            if(i<=5){
                topPanel.add(panel);
            }
            else if(i<=7){
                if(i==6){
                    panel.setBorder(BorderFactory.createMatteBorder(40,0,0,0,new Color(0,128,0)));
                }
                if(i==7){
                    panel.setBorder(BorderFactory.createMatteBorder(0,0,40,0,new Color(0,128,0)));
                }
                rightPanel.add(panel);
            }
        }
        for(int j=i;j<8;j++){
            
            if(j<6){ 
                JPanel panel = new JPanel();
                JLabel empty = new JLabel("EMPTY");
                Font font = new Font("Verdana", Font.BOLD, 20);
                empty.setFont(font);
                panel.add(empty);
                panel.setBackground(new Color(0,128,0));
                panel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
                topPanel.add(panel);
                
            }
            else if(j<8){
                JPanel panel = new JPanel(new BorderLayout());
                JLabel empty = new JLabel("   EMPTY");
                empty.setPreferredSize(new Dimension(124,134));
                empty.setBorder(BorderFactory.createLineBorder(Color.black, 2)); 
                Font font = new Font("Verdana", Font.BOLD, 20);
                empty.setFont(font);
                
                panel.add(empty);
                
                panel.setBackground(new Color(0,128,0));
                if(j==6){
                    panel.setBorder(BorderFactory.createMatteBorder(40,0,0,0,new Color(0,128,0)));
                }
                if(j==7){
                    panel.setBorder(BorderFactory.createMatteBorder(0,0,40,0,new Color(0,128,0)));
                }
                rightPanel.add(panel);
            }
        }
       revalidate();
       repaint();
       pack();
        
    }
    
    
    
    /**
     *
     */
    public void newGame(){
        
        newGameFrame = new JDialog(this);
        newGameFrame.setModal(true);
        newGameFrame.setLocationRelativeTo(null);
        newGameFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        newGameFrame.setResizable(false);
        JPanel choicesPanel = new JPanel(new BorderLayout());
        newGameFrame.setLayout(new BorderLayout());
        
            JPanel panel1 = new JPanel();
            GridLayout gridLayout1 = new GridLayout(1,2);
            gridLayout1.setHgap(10);
            panel1.setLayout(gridLayout1);
            
                JPanel nameChoice = new JPanel();
                nameChoice.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                BorderLayout borderLayout1 = new BorderLayout();
                nameChoice.setLayout(borderLayout1);
                    JLabel label1 = new JLabel("Player's Name:");
                    final JTextField nameTextField = new JTextField(6);
                nameChoice.add(label1,BorderLayout.NORTH);
                nameChoice.add(nameTextField,BorderLayout.SOUTH);
                
                JPanel stackChoice = new JPanel();
                stackChoice.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                BorderLayout borderLayout2 = new BorderLayout();
                stackChoice.setLayout(borderLayout2);
                    JLabel label2 = new JLabel("Starting stack:");
                    final JSlider stackSlider = new JSlider(SwingConstants.HORIZONTAL, 50, 250, 100);
                    stackSlider.setMajorTickSpacing(50);
                    stackSlider.setMinorTickSpacing(10);
                    stackSlider.setPaintTicks(true);
                    stackSlider.setPaintLabels(true);
  

                stackChoice.add(label2,BorderLayout.NORTH);
                stackChoice.add(stackSlider,BorderLayout.SOUTH);
                
            panel1.add(nameChoice,BorderLayout.EAST);
            panel1.add(stackChoice,BorderLayout.WEST);
                
            ////////////////////////////////////////
            JPanel panel2 = new JPanel();
            GridLayout gridLayout2 = new GridLayout(1,2);
            gridLayout2.setHgap(10);
            panel2.setLayout(gridLayout2);
            
                JPanel numberOfAIChoice = new JPanel();
                numberOfAIChoice.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                BorderLayout borderLayout3 = new BorderLayout();
                numberOfAIChoice.setLayout(borderLayout3);
                    JLabel label3 = new JLabel("Number of AI Players:   ");
                    Integer number[] = {2,3,4,5,6,7};
                    final JComboBox comboBox = new JComboBox(number);
                numberOfAIChoice.add(label3,BorderLayout.WEST);
                numberOfAIChoice.add(comboBox,BorderLayout.EAST);
                
                
                JPanel AIDifficultyChoice = new JPanel();
                AIDifficultyChoice.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                BorderLayout borderLayout4 = new BorderLayout();
                AIDifficultyChoice.setLayout(borderLayout4);
                    JLabel label4 = new JLabel("Choose AI difficulty:");
                    AIDifficultyChoice.add(label4,BorderLayout.NORTH);
                    
                    JPanel group1 = new JPanel();
                    GridLayout groupLayout1 = new GridLayout(0,1);
                    group1.setLayout(groupLayout1);
                    
                    
                    JRadioButton easy = new JRadioButton("Easy");
                    
                    easy.setSelected(true);
                    ButtonGroup difficulties = new ButtonGroup();
                    difficulties.add(easy);
                    
                    group1.add(easy);
                    
                    AIDifficultyChoice.add(group1,BorderLayout.SOUTH);
                    
            
            
                    
            panel2.add(numberOfAIChoice); 
            panel2.add(AIDifficultyChoice);
            
            ////////////////////////////////////
            JPanel panel3 = new JPanel();
            GridLayout gridLayout3 = new GridLayout(1,2);
            gridLayout3.setHgap(10);
            panel3.setLayout(gridLayout3);
            
                JPanel pokerGameChoice = new JPanel();
                pokerGameChoice.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                BorderLayout borderLayout5 = new BorderLayout();
                pokerGameChoice.setLayout(borderLayout5);
                    JLabel label5 = new JLabel("Choose Poker Game:");
                    pokerGameChoice.add(label5,BorderLayout.NORTH);
                    
                    JPanel group2 = new JPanel();
                    GridLayout groupLayout2 = new GridLayout(0,1);
                    group2.setLayout(groupLayout2);
                    
                    
                    final JRadioButton texas = new JRadioButton("Texas Hold'em");
                    texas.setSelected(true);
                    
                    final JRadioButton stud7 = new JRadioButton("7 Card Stud");
                    
                    
                    final JRadioButton draw5 = new JRadioButton("5 Card Draw");
                    
                    
                    group2.add(texas);
                    group2.add(stud7);
                    group2.add(draw5);
                    
                    ButtonGroup pokerGames = new ButtonGroup();
                    pokerGames.add(texas);
                    pokerGames.add(stud7);
                    pokerGames.add(draw5);
            
                    pokerGameChoice.add(group2,BorderLayout.CENTER);
                    
                    
                JPanel bettingStructureChoice  = new JPanel(new BorderLayout());
                bettingStructureChoice.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    JLabel label6 = new JLabel("Choose a BettingStructure:");
                    bettingStructureChoice.add(label6,BorderLayout.NORTH);
                    
                    JPanel group3 = new JPanel(new GridLayout(0,1));
                    
                   
                    final JRadioButton fixedLimit = new JRadioButton("Fixed Limit");
                    
                    
                    fixedLimit.setSelected(true);
                    group3.add(fixedLimit);
                    
                    
                    ButtonGroup bettingStructures = new ButtonGroup();
                    bettingStructures.add(fixedLimit);
                    
                    
                    bettingStructureChoice.add(group3,BorderLayout.LINE_START);
                    
        panel3.add(pokerGameChoice);
        panel3.add(bettingStructureChoice);
        
        panel1.setBorder(BorderFactory.createTitledBorder("Player"));
        panel2.setBorder(BorderFactory.createTitledBorder("Artificial Intelligence"));
        panel3.setBorder(BorderFactory.createTitledBorder("Game"));
        
        newGameFrame.add(choicesPanel,BorderLayout.NORTH);
        choicesPanel.add(panel1,BorderLayout.NORTH);
        choicesPanel.add(panel2,BorderLayout.CENTER);         
        choicesPanel.add(panel3,BorderLayout.SOUTH);
        
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                ArrayList<String> aiNames = new ArrayList<>();
                aiNames.add("Bob");
                aiNames.add("James");
                aiNames.add("John");
                aiNames.add("George");
                aiNames.add("Luke");
                aiNames.add("Alex");
                aiNames.add("Bill");
                Collections.shuffle(aiNames);
                
                String actorName = nameTextField.getText();
                int startingStack = stackSlider.getValue();
                int numberOfAiPlayers = (int)comboBox.getSelectedItem();
                PokerGame pokerGame;
                BettingStructure bettingStructure;
 
                pokerGame = new TexasHoldem();
                
                
                if(stud7.isSelected()){
                    pokerGame = new SevenCardStud();
                    FixedLimit a = new FixedLimit();
                  
                }
                if(draw5.isSelected()){
                    pokerGame = new FiveCardDraw();
                   
                }
                
                
                if(fixedLimit.isSelected()){
                    bettingStructure = new FixedLimit();
                }
                else{
                    bettingStructure = new FixedLimit();
                }
                
                Actor actor = new Actor(actorName,startingStack);
                List<Player> players = new ArrayList<>();
                
                Random random = new Random();
                int actorPosition = random.nextInt(numberOfAiPlayers);
                
                int nextAiName = 0;
                for(int i=0;i<numberOfAiPlayers + 1;i++){
                    if(i == actorPosition){
                        players.add(actor);
                    }
                    else{
                        String aiName = aiNames.get(nextAiName++);
                        players.add(new EasyAIPlayer(aiName,startingStack));
                    }
                }
                table = new Table(players,bettingStructure,pokerGame);
                newGameFrame.dispose();
                startNewGame();  
                
                
            }
        });
        
        newGameFrame.add(okButton,BorderLayout.AFTER_LINE_ENDS);
        newGameFrame.pack();
        newGameFrame.setVisible(true);
    }
    
    
     public void startNewGame(){
        if(communityCardsPanel!=null){
            communityCardsPanel.empty();
        }
        communityCardsPanel = new CommunityCardsPanel(table.getCommunityCards());
        gameFeed.setText("");
        add(communityCardsPanel,BorderLayout.CENTER);
        populate(table.getPlayers());
        actionPanel.removeAll();
        actionPanel.revalidate();
        actionPanel.repaint();
        actionPanel.add(startGameButton);
    }
    
    /**
     * Αυτή η συνάρτηση είναι ο βασικός κορμός του παιχνιδιού, μέσω της  οποίας εξελίζεται  το παιχνίδι
     */
     
     public void gameLogic(){
         actionPanel.removeAll();
         actionPanel.revalidate();
         actionPanel.repaint();
         boolean pokerGameIsOver = false;
         PokerGame pokerGame = table.getPokerGame();
         BettingStructure bettingStructure = table.getBettingStructure();
         List<Player> players = table.getPlayers();
        
         if(!table.gameIsOver()){
           
            if(bettingStructure.bettingRoundIsOver(table, currentPlayer)){ 
                    JOptionPane.showMessageDialog(this, "Betting Round Is Over!!!","End of Round",JOptionPane.INFORMATION_MESSAGE);
                    
                    bettingStructure.endBettingRound(table.getPlayers()); 
                    pokerGame.endRound(table);
                    
                    for(PlayerPanel playerPanel : playerPanels){
                       playerPanel.update();
                    }
                    
                    if(pokerGame.getRoundCounter() > pokerGame.getNumberOfRounds()){
                        pokerGameIsOver = true;
                        
                        Player bestHandPlayer = currentPlayer;
                        for(Player player:players){
                            if(player.getHand().getValue()> bestHandPlayer.getHandValue()){
                                bestHandPlayer = player;
                            }
                        }
                        JOptionPane.showMessageDialog(this, "Poker Game Is Over!!!" + '\n' + "Best Hand:" + bestHandPlayer.getHand().toString() + '(' + bestHandPlayer.getName() + ')',"End of Poker Game",JOptionPane.INFORMATION_MESSAGE);
                        pokerGame.endRound(table);
                        communityCardsPanel.empty();
                        table.emptyCommunityCards();
                        table.prepareForNextPokerRound();
                        actionPanel.add(startGameButton);
                        for(PlayerPanel panel : playerPanels){
                            panel.update();
                        }
                    }else {
                        
                        if(pokerGame.allowsCardsChange()){
                           
                            for(Player player: players){
                                {
                                    if(player.isActive() && !player.isFolded()){
                                        if(player instanceof Actor){
                                            changeCardsDialog((Actor)player);
                                        }
                                        player.changeCards();
                                    }
                                }
                            }
                        }
                    }
                    currentPlayer = table.getFirstPlayer();
                     if(pokerGameIsOver){
                        actionPanel.add(startGameButton);
                    }else{
                        pokerGame.startRound(table);
                        gameFeed.setText("");
                        communityCardsPanel.update();
                        bettingStructure.startBettingRound(players, currentPlayer); 

                        do{
                            currentPlayer = table.getNextPlayer(currentPlayer);
                        }while(!table.getNextPlayer(currentPlayer).equals(table.getFirstPlayer()));
                    
                        actionPanel.add(continueButton);
                    }
                   
            }else{
                gameFeed.setText(currentPlayer.getName() + "'s Turn") ;
                if(currentPlayer instanceof Actor){
                    addActionButtons(bettingStructure.getAvailableActions(currentPlayer));
                }
                else{
                    currentPlayer.think(table.getBettingStructure().getAvailableActions(currentPlayer),table);
                    bettingStructure.executeAction(currentPlayer);
                    for(PlayerPanel playerPanel : playerPanels){
                        playerPanel.update();
                    }
                    actionPanel.add(continueButton);
                    gameFeed.setText(currentPlayer.getName() + ' ' + currentPlayer.getAction().getDescritpion());
                    
                }
                
            }
         }
         else{
             Player winner = players.get(0);
             for(Player player:players){
                 if(player.getStack() > winner.getStack()){
                     winner = player;
                 }
             }
             JOptionPane.showMessageDialog(this,"GAME OVER" + "\n" + "Winner :" + winner.getName(),"Game Over",JOptionPane.INFORMATION_MESSAGE);
             actionPanel.removeAll();
             actionPanel.revalidate();
             actionPanel.repaint();
         }
         
     }
     
     
    /**
     * Προσθέτει όλα τα JButton στο actionPanel, βάση των επιτρεπώμενων ενεργειών του τρέχοντα παίκτη
     * @param availableActions Οι επιτρεπόμενες ενέργειες
     */ 
     
    public void addActionButtons(List<Action> availableActions){
        if(availableActions.contains(Action.CHECK)){
            actionPanel.add(checkButton);
        }
        if(availableActions.contains(Action.CALL)){
           actionPanel.add(callButton);
        }
        if(availableActions.contains(Action.BET)){
            actionPanel.add(betButton);
        }
        if(availableActions.contains(Action.FIXEDRAISE)){
            actionPanel.add(raiseButton);
        }
        if(availableActions.contains(Action.FOLD)){
            actionPanel.add(foldButton);
        }
        actionPanel.revalidate();
        actionPanel.repaint();
    }
    
    
    
    /**
     * Η συνάρτηση μέσω της οποίας ο χρήστης διαλέγει το ποιά φύλλα θέλει να αλλάξει
     * @param actor Ο χρήστης
     */
    
    public void changeCardsDialog(final Actor actor){
        final JDialog changeCardsDialog = new JDialog(this);
        changeCardsDialog.setModal(true);
        changeCardsDialog.setResizable(false);
        changeCardsDialog.setLocationRelativeTo(null);
        changeCardsDialog.setLayout(new BorderLayout());
        changeCardsDialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        final List<Card> faceDownCards = actor.getFaceDownCards();
        JPanel mainPanel = new JPanel(new GridLayout(1,0));
        
        final ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
        for(Card card : faceDownCards){
            JPanel panel = new JPanel(new BorderLayout());
            String filename = getCardFilename(card);
            URL url = Resource.getURL(filename);
            ImageIcon imageIcon = new ImageIcon(url);
            JLabel cardLabel = new JLabel(imageIcon);
            panel.add(cardLabel,BorderLayout.NORTH);
            JCheckBox checkBox = new JCheckBox();
            checkBoxes.add(checkBox);
            panel.add(checkBox,BorderLayout.SOUTH);
            mainPanel.add(panel);
        }
       
        JButton button = new JButton("Discard selected Cards");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<checkBoxes.size();i++){
                    if(checkBoxes.get(i).isSelected()){
                        actor.addToCardsToChange(faceDownCards.get(i));
                       
                    }
                }
                changeCardsDialog.dispose();
            }
        });
        
        changeCardsDialog.add(mainPanel,BorderLayout.NORTH);
        changeCardsDialog.add(button,BorderLayout.AFTER_LINE_ENDS);
        changeCardsDialog.pack();
        changeCardsDialog.setVisible(true);
    }
    
   /**
    * Η συνάρτηση αυτή βρίσκει και επιστρέφει το όνομα αρχείου της εικόνας ενός φύλλου
    * @param card Το φύλλο για το οποίο βρίσκει το όνομα αρχείου
    * @return Το όνομα αρχείου της εικόνας του φύλλου
    */
    
    public String getCardFilename(Card card){
            Suit suit = card.getSuit();
            int rank = card.getRank().ordinal() + 2;
            StringBuilder filenameSB = new StringBuilder();
            switch (rank){
                case 10:
                    filenameSB.append('t');
                    break;
                case 11:
                    filenameSB.append('j');
                    break;
                case 12:
                    filenameSB.append('q');
                    break;
                case 13:
                    filenameSB.append('k');
                    break;
                case 14:
                    filenameSB.append('a');
                    break;
                default:
                   filenameSB.append(Integer.toString(rank));
                   break;
            }
            switch (suit){
                case HEARTS:
                    filenameSB.append('h');
                    break;
                case DIAMONDS:
                    filenameSB.append('d');
                    break;
                case CLUBS:
                    filenameSB.append('c');
                    break;
                case SPADES:
                    filenameSB.append('s');
                    break;
                default:
                    break;
            }
            filenameSB.append(".gif");
            return filenameSB.toString();    
    }
    
   
    
    
    
    
    /**
     * Ένα JPanel το οποίο κρατάει και εμφανίζει τις εικόνες απο μια λίστα απο φύλλα
     * @see JPanel
     */
    class CommunityCardsPanel extends JPanel{
        List<Card> communityCards;
        List<JLabel> cardLabels;
        
        /**
         * 
         * @param communityCards Η λίστα απο φύλλα με τα οποία συνδέεται το αντικείμενο 
         */
        public CommunityCardsPanel(List<Card> communityCards){
            this.communityCards = communityCards;
            cardLabels = new ArrayList<>();
            GridLayout layout = new GridLayout(1,5);
            setLayout(layout);
            setBackground(new Color(0,128,0));
        }
        
        /**
         * Ανανέωση των φύλλων που εμφανίζονται στην οθόνη
         */ 
        public void update(){
            if(communityCards.size() > cardLabels.size()){
                while(communityCards.size() > cardLabels.size()){
                    addCard(communityCards.get(cardLabels.size()));
                    
                }
            }
            else if(cardLabels.size() > communityCards.size()){
                while(cardLabels.size() > communityCards.size()){
                    remove(cardLabels.get(cardLabels.size()-1));
                    cardLabels.remove(cardLabels.size()-1);
                    
                }
            } 
        }
        

        
        private void addCard(Card card){
            String filename = getCardFilename(card);
            URL imageUrl;
            ImageIcon icon;  
            JLabel label = new JLabel(); 
            try{
                imageUrl = Resource.getURL(filename);   
                icon = new ImageIcon(imageUrl);
                label.setIcon(icon);
            }catch(Exception exc2){
                JOptionPane.showMessageDialog(actionPanel, "ERROR!!!" + "\nCant find file \"" + filename + "\" \n Aborting operation!!!", "ERROR",JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
                
            
            
            this.add(label);
            cardLabels.add(label);
            this.revalidate();
            this.repaint();
        }
            
        /**
         * Αδειάζει όλες τις κάρτες και όλες τις εικόνες απο το JPanel
         */
        public void empty(){
            Iterator it = cardLabels.iterator();
            while(it.hasNext()){
                JLabel label = (JLabel)it.next();
                this.remove(label);
                it.remove();
            }
            revalidate();
            repaint();
        }                            
    }
    
    
    
    
    
    
    
    
    /**
     * Ένα JPanel το οποίο κρατάει και εμφανίζει ι τα στοιχεία ενός παίκτη
     * @see JPanel
     * @see Player
     */
    class PlayerPanel extends JPanel{
        Player player;
        JLabel nameLabel;
        JLabel stackLabel;
        JLabel wagerLabel;
        JLabel actionLabel;
        JButton showCards;
        
        /**
         * 
         * @param player Ο παίκτης με τον οποίο συνδέεται το PlayerPanel 
         */
        public PlayerPanel(final Player player){
            this.player = player;
            
            BorderLayout basicLayout = new BorderLayout();
            setLayout(basicLayout);
            
            
            JPanel infoPanel = new JPanel();
            infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
            GridLayout infoLayout = new GridLayout(4,2);
            infoPanel.setLayout(infoLayout);
            infoPanel.setOpaque(true);
            infoPanel.setBackground(new Color(0,128,0));
            
            JLabel label1 = new JLabel("Name:");
            label1.setPreferredSize(new Dimension(60,16));
            label1.setOpaque(false);
            StringBuilder name = new StringBuilder(player.getName());
            name.setLength(7);
            
            nameLabel = new JLabel(name.toString());
            nameLabel.setForeground(Color.BLACK);
            nameLabel.setOpaque(false);
            infoPanel.add(label1);
            infoPanel.add(nameLabel);
            
            
            JLabel label2 = new JLabel("Stack:");
            label2.setOpaque(false);
            stackLabel = new JLabel(Integer.toString(player.getStack()));
            stackLabel.setOpaque(false);
            infoPanel.add(label2);
            infoPanel.add(stackLabel);
            
            JLabel label3 = new JLabel("Wager:");
            label3.setOpaque(false);
            wagerLabel = new JLabel(Integer.toString(player.getWager()));
            wagerLabel.setOpaque(false);
            infoPanel.add(label3);
            infoPanel.add(wagerLabel);
            
            JLabel label4 = new JLabel("Action:");
            label4.setOpaque(false);
            actionLabel = new JLabel(player.getAction().getDescritpion());
            actionLabel.setOpaque(false);
            infoPanel.add(label4);
            infoPanel.add(actionLabel);
            
            add( infoPanel, BorderLayout.NORTH);
            
            showCards = new JButton("Show Cards");
            add(showCards,BorderLayout.SOUTH);
            showCards.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame showCardsFrame = new JFrame();
                    
                    showCardsFrame.setLocationRelativeTo(showCards);
                    
                    showCardsFrame.setResizable(false);  
                    showCardsFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    
                    
                    GridLayout showCardsLayout = new GridLayout(1,0);
                    showCardsFrame.setLayout(showCardsLayout);
                    
                    List<Card> availableCards = player.getAvailableCards();
                    List<Card> faceUpCards = player.getFaceUpCards();
                    List<Card> faceDownCards = player.getFaceDownCards();
                    
                    Iterator<Card> it = availableCards.iterator();
                    while(it.hasNext()){
                        Card card = (Card)it.next();
                        JLabel cardLabel = new JLabel();
                        String filename;
                        if(faceUpCards.contains(card) || faceDownCards.contains(card)){
                            if(faceDownCards.contains(card) && !(player instanceof Actor)){
                                filename = "b.gif";
                            }
                            else{
                                filename = getCardFilename(card);
                            } 
                            URL imageUrl = Resource.getURL(filename);
                            try{
                                ImageIcon icon = new ImageIcon(imageUrl);
                                cardLabel.setIcon(icon);
                            }
                            catch(Exception exc1){
                                JOptionPane.showMessageDialog(actionPanel, "ERROR!!!" + "\nCant find file \"" + filename + "\" \n Aborting operation!!!", "ERROR",JOptionPane.ERROR_MESSAGE);
                                System.exit(1);
                            }
                            showCardsFrame.add(cardLabel);
                        }  
                    }                    
                    showCardsFrame.pack();
                    showCardsFrame.setVisible(true);    
                }      
            });
            
            
        }
        
        /**
         * Ανανεώνει τα στοιχεία του παίκτη που εμφανίζονται
         */
        public void update(){
            stackLabel.setText(Integer.toString(player.getStack()));
            wagerLabel.setText(Integer.toString(player.getWager()));
            if(player.isActive()){
                actionLabel.setText(player.getAction().getDescritpion());   
        
            }else{
                actionLabel.setText("LOST");
            }
        }
    }
    
}
