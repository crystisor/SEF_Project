package org.project.Services;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class MultilineCellRenderer extends JTextArea implements TableCellRenderer {
    public MultilineCellRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }

        setFont(table.getFont());
        setText((value == null) ? "" : value.toString());
        setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);

        return this;
    }
}
