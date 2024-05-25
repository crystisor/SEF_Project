package org.project.Forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardForm extends JDialog{
    private JLabel cardLabel;
    private JLabel codeLabel;
    private JLabel nameLabel;
    private JLabel expLabel;
    private JLabel secLabel;
    private JLabel amountLabel;
    private JPanel cardPanel;
    private JButton btnConfirm;
    private JButton btnCancel;
    private JPasswordField pfCVV;
    private JTextField tfOwner;
    private JTextField tfNumber;
    private JTextField tfExp;
    private JTextField tfAmount;


    public CardForm(JDialog parent) {
        super(parent);
        setTitle("Card Form");
        setContentPane(cardPanel);
        setMinimumSize(new Dimension(650,250));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setContentPane(cardPanel);

        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        CardForm cf = new CardForm(null);
    }

}
