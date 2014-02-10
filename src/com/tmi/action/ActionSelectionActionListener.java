package com.tmi.action;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by brdegenaars on 2/9/14.
 */
public class ActionSelectionActionListener implements ActionListener {

    private Component[] componentsToAlter;

    @Override
    @SuppressWarnings("unchecked")
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        System.out.println("Action Selection Command : " + command);
        String selectedAction = "No Change";

        if (command.equals("comboBoxChanged")){
            Object source = e.getSource();
            JComboBox<String> comboBox = null;
            if (source.getClass() == JComboBox.class){
                comboBox = (JComboBox<String>) source;
            }

            selectedAction = (comboBox != null ? comboBox.getSelectedItem().toString() : selectedAction);
            if (selectedAction != null){
                if (selectedAction.equals("Shutdown")){
                    setComponentText("Shutdown in : ");
                } else if (selectedAction.equals("Sleep")){
                    setComponentText("Sleep in : ");
                }
            }
        }
        System.out.println("Selected Action : " + selectedAction);
    }

    public void setComponentsToAlter(Component[] componentsToDisable){
        this.componentsToAlter = new Component[componentsToDisable.length];
        System.arraycopy(componentsToDisable, 0, this.componentsToAlter, 0, componentsToDisable.length);
    }

    private void setComponentText(String text){
        for (Component component : componentsToAlter){
            if (component.getClass() == JLabel.class){
                JLabel label = (JLabel) component;
                label.setText(text);
            }
        }
    }
}
