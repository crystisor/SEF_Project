package org.project.Entities;

import javax.swing.*;
import java.awt.*;

public class BookListCellRenderer extends JPanel implements ListCellRenderer<Book>
{
    private JLabel lblImage = new JLabel();
    private JLabel lblText = new JLabel();

    public BookListCellRenderer()
    {
        setLayout(new BorderLayout(5, 5));
        add(lblImage, BorderLayout.WEST);
        add(lblText, BorderLayout.CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Book> list, Book book, int index,
                                                  boolean isSelected, boolean cellHasFocus)
    {
        lblText.setText(book.toString());

        // Load the image
        if (book.getImage_url() != null)
        {
            ImageIcon imageIcon = new ImageIcon(book.getImage_url());
            Image image = imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            lblImage.setIcon(new ImageIcon(image));
        } else
        {
            lblImage.setIcon(null); // No image available
        }

        // Highlight selection
        if (isSelected)
        {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else
        {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}
