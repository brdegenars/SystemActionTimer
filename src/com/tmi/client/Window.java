package com.tmi.client;

import javax.swing.*;
import java.awt.*;

/**
 * Created by brdegenaars on 2/9/14.
 */
public class Window extends JFrame {

    private int width = 320, height = 100;

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
        this.setSize(width, height);
//        this.pack();
        this.setVisible(true);
    }

    public void setWindowSize(int width, int height){
        this.width = width;
        this.height = height;
    }
}
