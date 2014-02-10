package com.tmi.action;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by brdegenaars on 2/9/14.
 */
public class SleepTimerActionListener implements ActionListener {
    @Override
    @SuppressWarnings("unchecked")
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        System.out.println("Sleep Timer Command : " + command);
        String selectedSleepTimeInterval = "No Change";

        if (command.equals("comboBoxChanged")){
            Object source = e.getSource();
            JComboBox<String> comboBox = null;
            if (source.getClass() == JComboBox.class){
                comboBox = (JComboBox<String>) source;
            }

            selectedSleepTimeInterval = (comboBox != null ? comboBox.getSelectedItem().toString() : null);
        }
        System.out.println("Selected Sleep Interval : " + selectedSleepTimeInterval);
    }
}
