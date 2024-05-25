package org.project.Services;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.File;

public class ImageRenderer extends JLabel implements TableCellRenderer {


    public ImageRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value != null && value instanceof String) {
            String path = (String) value;
            ImageIcon imageIcon = new ImageIcon(path);
            if (imageIcon.getIconWidth() == -1 || imageIcon.getIconHeight() == -1) {
                setText("Image not found");
                setIcon(null);
            } else {
                setIcon(imageIcon);
                setText("");
            }
        } else {
            setText("No Image");
            setIcon(null);
        }

        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }

        return this;
    }
}
