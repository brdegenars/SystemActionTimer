package com.tmi.loader;

import com.tmi.action.ActionSelectionActionListener;
import com.tmi.action.SleepTimerActionListener;
import com.tmi.client.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.Timer;

/**
 * Created by brdegenaars on 2/9/14.
 */
public class SAT {

    private static final int CONFIRMED = 0;
    private static final int OS_X_WIDTH = 400;
    private static final int OS_X_HEIGHT = 100;

    private ArrayList<Component> components;
    private JComboBox<String> actionSelection, actionTimerSelection;
    private Window window;

    public SAT() {
        String operatingSystem = System.getProperty("os.name");
        this.components = new ArrayList<Component>();

        Window window = new Window("SAT - System Action Timer");
        if (operatingSystem.equals("Mac OS X")){
            window.setWindowSize(OS_X_WIDTH, OS_X_HEIGHT);
        }

        String[] actionOptions = new String[]{"Sleep", "Shutdown"};
        actionSelection = new JComboBox<String>(actionOptions);
        actionSelection.setSelectedIndex(0);
        ActionSelectionActionListener actionSelectionActionListener = new ActionSelectionActionListener();
        JLabel actionSelectionLabel = new JLabel("Action : ");

        String[] actionTimerOptions = new String[]{"Now","1min", "30min", "60min", "90min", "120min"};
        actionTimerSelection = new JComboBox<String>(actionTimerOptions);
        actionTimerSelection.setSelectedIndex(0);
        actionTimerSelection.addActionListener(new SleepTimerActionListener());
        JLabel actionTimerSelectionLabel = new JLabel("Sleep in : ");

        actionSelectionActionListener.setComponentsToAlter(new Component[]{actionTimerSelectionLabel});

        actionSelection.addActionListener(actionSelectionActionListener);

        addComponent(actionSelectionLabel);
        addComponent(actionSelection);

        addComponent(actionTimerSelectionLabel);
        addComponent(actionTimerSelection);

        JButton executeButton = new JButton("GO!");
        executeButton.addActionListener(new ExecuteButtonActionListener());
        addComponent(executeButton);

        window.addComponent(getComponents());
        this.window = window;
    }

    private Component[] getComponents(){
        Component[] returnComponents = new Component[this.components.size()];
        for (int i = 0; i < this.components.size(); i++){
            returnComponents[i] = this.components.get(i);
        }
        return returnComponents;
    }

    private String getAction(){
        return actionSelection.getSelectedItem().toString();
    }

    private String getActionInterval(){
        return actionTimerSelection.getSelectedItem().toString();
    }

    private void addComponent(Component component){
        components.add(component);
    }

    public static void main(String[] args) {
        new SAT();
    }

    public class ExecuteButtonActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String action = getAction();
            String actionTimerValue = getActionInterval();

            if (action.equals("Shutdown")){
                try {
                    shutdownIn(actionTimerValue);
                } catch (IOException e1) {
                    System.out.println("Unable to execute shutdown command!");
                }
            } else if (action.equals("Sleep")){
                sleepFor(actionTimerValue);
            }
        }

        private void sleepFor(String sleepTimerValue){

            long length = 0, time = 0;
            int confirm;

            if (!sleepTimerValue.equals("Now")){
                length = Integer.valueOf(sleepTimerValue.replace("min", ""));
                time = length * 60;
            }

            confirm = JOptionPane.showConfirmDialog(window, "Go to sleep " + (length == 1 ? "in 1 minute?" : (length == 0 ? "now?" : "in " + length + "minutes?")));

            final String sleepCommand;
            String operatingSystem = System.getProperty("os.name");

            sleepCommand = determineSystemSleepCommand(operatingSystem);

            if (confirm == CONFIRMED){
                executeTimedCommand(time, sleepCommand);
            } else {
                System.out.println("Sleep aborted!");
            }
        }

        private String determineSystemSleepCommand(String operatingSystem) {
            String sleepCommand;
            if (operatingSystem.contains(("Linux")) || operatingSystem.contains(("Mac"))) {
                sleepCommand = "pmset sleepnow";
            }
            else if ("Windows 7".equals(operatingSystem)) {
                sleepCommand = "rundll32.exe powrprof.dll,SetSuspendState 0,1,0";
            }
            else {
                throw new RuntimeException("Unsupported operating system.");
            }
            return sleepCommand;
        }

        private void shutdownIn(String shutdownTimerValue) throws IOException{

            long length = 0, time = 0;

            if (!shutdownTimerValue.equals("Now")){
                length = Integer.valueOf(shutdownTimerValue.replace("min", ""));
                time = length * 60;
            }

            final String shutdownCommand;
            String operatingSystem = System.getProperty("os.name");

            shutdownCommand = determineSystemShutdownCommand(operatingSystem);

            String confirmation  = ("Shutdown " + (length == 1 ? "in 1 minute?" : (length == 0 ? "now?" : "in " + length + " minutes?")));
            int confirm = JOptionPane.showConfirmDialog(window, confirmation);

            if (confirm == CONFIRMED){
                executeTimedCommand(time, shutdownCommand);
            } else {
                System.out.println("Shutdown aborted!");
            }
        }

        private String determineSystemShutdownCommand(String operatingSystem) {
            String shutdownCommand;
            if (operatingSystem.contains(("Linux")) || operatingSystem.contains(("Mac"))) {
                shutdownCommand = "shutdown -h now";
            }
            else if ("Windows 7".equals(operatingSystem)) {
                shutdownCommand = "shutdown.exe -s -t 0";
            }
            else {
                throw new RuntimeException("Unsupported operating system.");
            }
            return shutdownCommand;
        }

        private void executeTimedCommand(long time, final String systemCommand) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    Runtime runtime = Runtime.getRuntime();
                    try {
                        runtime.exec(systemCommand);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }, time * 1000);
        }
    }
}
