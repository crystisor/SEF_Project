package org.project.Forms.User;

import org.project.DbContext.Interfaces.IUserRepo;
import org.project.DbContext.Repos.UserRepo;
import org.project.Entities.CardInfo;

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
    IUserRepo _userRepo;

    public CardForm(JDialog parent, String userId, IUserRepo userRepo) {
        super(parent);
        setTitle("Card Form");
        setContentPane(cardPanel);
        setMinimumSize(new Dimension(650,250));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        _userRepo = userRepo;

        setContentPane(cardPanel);

        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                CardInfo card = new CardInfo( tfNumber.getText(), tfOwner.getText(), tfExp.getText(), String.valueOf(pfCVV.getPassword()), tfAmount.getText());
                if( card.validateCreditCardInfo() )
                        if( !_userRepo.updateUserFunds(userId, card.getAmount() ) )
                            JOptionPane.showMessageDialog(CardForm.this, "It seems there was an error retrieving user info!");
                        else {
                            JOptionPane.showMessageDialog(CardForm.this, "Payment succesful!");
                            dispose();
                        }
                else
                    JOptionPane.showMessageDialog(CardForm.this, "Invalid credit card information!");
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
        CardForm cf = new CardForm(null, null, new UserRepo());
    }

}
