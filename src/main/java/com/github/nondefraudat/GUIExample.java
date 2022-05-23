package com.github.nondefraudat;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIExample extends JFrame {
    private JButton button = new JButton("Press");
    private JTextField input = new JTextField("", 5);
    private JLabel label = new JLabel("Input:");
    private JRadioButton radio1 = new JRadioButton("Radio1:");
    private JRadioButton radio2 = new JRadioButton("Radio2:");
    private JCheckBox check = new JCheckBox("Check", false);

    public GUIExample() {
        super("Simple Example");
        this.setBounds(100, 100, 250, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3, 2, 2, 2));
        container.add(label);
        container.add(input);

        ButtonGroup group = new ButtonGroup();
        group.add(radio1);
        group.add(radio2);

        container.add(radio1);
        radio1.setSelected(true);
        container.add(radio2);
        container.add(check);
        button.addActionListener(new NDListener());
        container.add(button);
    }

    class NDListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            String message = "";
            message += "Button pressed\n";
            message += "Input content: " + input.getText() + "\n";
            message += (radio1.isSelected() ? "Radio1" : "Radio2") + " selected\n";
            message += "CheckBox status: " + (check.isSelected() ? "true" : "false") + "\n";
            JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
