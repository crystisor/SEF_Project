package org.project.Forms;

import javax.swing.*;
import java.awt.*;

public class CardForm extends JDialog{
    private JTextField tfNumber;
    private JTextField tfOwner;
    private JTextField tfExp;
    private JTextField tfCVV;
    private JLabel cardLabel;
    private JLabel codeLabel;
    private JLabel nameLabel;
    private JLabel expLabel;
    private JLabel secLabel;
    private JTextField tfAmount;
    private JLabel amountLabel;
    private JPanel cardPanel;


    public CardForm(JDialog parent) {
        super(parent);
        setTitle("Card Form");
        setContentPane(cardPanel);
        setMinimumSize(new Dimension(650,200));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setContentPane(cardPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        CardForm cf = new CardForm(null);
    }
}
