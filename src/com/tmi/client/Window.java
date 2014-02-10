package com.tmi.client;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

/**
 * Created by brdegenaars on 2/9/14.
 */
public class Window extends JFrame {

    public Window(String title) throws HeadlessException {
        super(title);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
    }

    public void addComponent(Component[] components){
        Container contentPane = this.getContentPane();

        for (Component component : components){
            contentPane.add(component);
        }
        this.setSize(320,100);
//        this.pack();
        this.setVisible(true);
    }
}
