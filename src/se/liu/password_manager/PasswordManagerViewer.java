package se.liu.password_manager;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class PasswordManagerViewer
{
    private JFrame frame = new JFrame("Password Manager");
    private PasswordManagerComponent passwordManagerComponent;
    private JList jList;
    private JScrollPane jScrollPane;

    public PasswordManagerViewer(final DefaultListModel listModel) {
        this.jList = new JList(listModel);
        this.jScrollPane = new JScrollPane(jList);
    }

    public void initializeFrame() {
        frame.setLayout(new MigLayout("", "[grow][grow][grow]", ""));
        frame.setSize(500, 500);

        //createScrollablePane();
        createButtons();

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
    }

    private void createScrollablePane() {
        JPanel panel = new JPanel(new GridLayout());
        jList.setBounds(100,100, 75,300);
        panel.add(jList);
        Box passwordPanel = new Box(BoxLayout.Y_AXIS);
        passwordPanel.add(panel);


        frame.add(panel);

    }

    private void createButtons() {
        frame.add(new JButton("Add account"));
        frame.add(new JButton("Remove account"));
        frame.add(new JButton("Edit exisiting account"));

    }




}
