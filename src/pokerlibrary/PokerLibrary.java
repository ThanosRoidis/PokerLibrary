/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerlibrary;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Αθανάσιος Ροίδης
 * @version 1.13.1
 */
public class PokerLibrary {
    public static void main(String[] args) {
        
        final JFrame frame = new JFrame();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new GridLayout(0,1));
        JLabel label1 = new JLabel("     Choose a User Interface:");
        label1.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
        JButton button1 = new JButton("Graphical User Interface");
        JButton button2 = new JButton("Command Line InterFace");
        button1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                PokerGameGUI app = new PokerGameGUI();
            }
        });
        button2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                PokerGameCLI app = new PokerGameCLI();
            }
        });
        frame.add(label1);
        frame.add(button1);
        frame.add(button2);
        frame.pack();
        frame.setVisible(true);
    }
    
}
