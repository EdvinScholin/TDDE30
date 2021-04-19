package se.liu.password_manager;

import javax.swing.*;
import java.awt.*;

public class PasswordManagerComponent extends JComponent//temp namn? Implements listener
{
    private JList jList;
    private JScrollPane scrollPane;
    private JFrame frame;

    public PasswordManagerComponent(final DefaultListModel listModel, final JFrame frame) {
        this.jList = new JList(listModel);
        this.scrollPane = new JScrollPane(jList);
        this.frame = frame;

    }

    @Override protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;
        frame.add(scrollPane);
    }
}
