package gui;

import model.Event;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// Linear Progression Training Program Graphical User Interface
public class LinearProgressionTrainingProgram {
    private Workout workout;

    private static final String FILE = "./data/LinearProgressionTrainingProgram.json";

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JFrame frame;

    private ButtonListener buttonListener;

    private JButton nextButton;
    private JButton createButton;

    private JTextField squatMaxField;
    private JTextField benchMaxField;
    private JTextField deadliftMaxField;
    private JComboBox<String> setsBox;
    private JComboBox<String> repsBox;

    // EFFECTS: Runs Linear Progression Training Program application, initializing variables
    public LinearProgressionTrainingProgram() {
        jsonWriter = new JsonWriter(FILE);
        jsonReader = new JsonReader(FILE);

        buttonListener = new ButtonListener();

        ImageIcon icon = new ImageIcon("data/icon.png");

        frame = new JFrame();
        frame.setTitle("Linear Progression Training Program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new CloseListener());
        frame.setResizable(false);
        frame.setIconImage(icon.getImage());
        frame.setVisible(true);

        menu();
    }


    // EFFECTS: Displays menu with title splash, start new, and load buttons
    private void menu() {
        JButton newButton = new ActionButton("New Program", "new");

        JButton loadButton = new ActionButton("Load Saved Program", "load");

        JLabel logoLabel = new JLabel(new ImageIcon("data/logo.png"));

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(10, 0, 0, 0);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.red);
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.add(newButton, gridBagConstraints);
        buttonPanel.add(loadButton, gridBagConstraints);

        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(Color.red);
        menuPanel.setLayout(new GridLayout(2, 0));
        menuPanel.add(logoLabel);
        menuPanel.add(buttonPanel);
        menuPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        setPanel(menuPanel);
    }

    // EFFECTS: Changes the panel in the frame to the specified panel
    // MODIFIES: frame
    private void setPanel(JPanel panel) {
        frame.setContentPane(panel);
        frame.invalidate();
        frame.validate();
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    // EFFECTS: Displays fields and dropdown menus for user to input new program preferences
    @SuppressWarnings("methodlength")
    private void createNew() {
        JLabel squatMaxLabel = new JLabel("Squat Training Max: ", SwingConstants.RIGHT);
        squatMaxLabel.setForeground(Color.white);
        JLabel benchMaxLabel = new JLabel("Bench Press Training Max: ", SwingConstants.RIGHT);
        benchMaxLabel.setForeground(Color.white);
        JLabel deadliftMaxLabel = new JLabel("Deadlift Training Max: ", SwingConstants.RIGHT);
        deadliftMaxLabel.setForeground(Color.white);

        FieldListener fieldListener = new FieldListener();
        squatMaxField = new JTextField();
        squatMaxField.addKeyListener(fieldListener);
        benchMaxField = new JTextField();
        benchMaxField.addKeyListener(fieldListener);
        deadliftMaxField = new JTextField();
        deadliftMaxField.addKeyListener(fieldListener);

        JLabel setsLabel = new JLabel("Sets: ", SwingConstants.RIGHT);
        setsLabel.setForeground(Color.white);
        JLabel repsLabel = new JLabel("Reps: ", SwingConstants.RIGHT);
        repsLabel.setForeground(Color.white);

        setsBox = new JComboBox<>(new String[]{"", "1", "2", "3", "4", "5", "6"});
        setsBox.addActionListener(fieldListener);
        repsBox = new JComboBox<>(new String[]{"", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"});
        repsBox.addActionListener(fieldListener);

        JButton menuButton = new ActionButton("Menu", "menu");
        createButton = new ActionButton("Create Program", "create");
        createButton.setEnabled(false);

        JPanel creatorPanel = new JPanel();
        creatorPanel.setBackground(Color.red);
        creatorPanel.setLayout(new GridLayout(0, 2, 10, 10));
        creatorPanel.add(squatMaxLabel);
        creatorPanel.add(squatMaxField);
        creatorPanel.add(benchMaxLabel);
        creatorPanel.add(benchMaxField);
        creatorPanel.add(deadliftMaxLabel);
        creatorPanel.add(deadliftMaxField);
        creatorPanel.add(setsLabel);
        creatorPanel.add(setsBox);
        creatorPanel.add(repsLabel);
        creatorPanel.add(repsBox);
        creatorPanel.add(menuButton);
        creatorPanel.add(createButton);
        creatorPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        setPanel(creatorPanel);
    }

    // EFFECTS: Sets workout to workout loaded from json file, displays an error message if exception occurs
    // MODIFIES: workout
    private void loadSaved() {
        try {
            workout = jsonReader.read();
            logWorkout();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading save file " + FILE,
                    "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    // EFFECTS: Reads input from fields and dropdown menus and adds reps, sets, and exercises to a newly created workout
    private void generateWorkout() {
        int maxSquat = Integer.parseInt(squatMaxField.getText());
        int maxBench = Integer.parseInt(benchMaxField.getText());
        int maxDeadlift = Integer.parseInt(benchMaxField.getText());

        int setsPerExercise = Integer.parseInt((String) setsBox.getSelectedItem());
        int repsPerSet = Integer.parseInt((String) repsBox.getSelectedItem());

        int initialSquat = (int) (5 * (Math.round(maxSquat * 0.5) / 5));
        int initialBenchPress = (int) (5 * (Math.round(maxBench * 0.5) / 5));
        int initialDeadlift = (int) (5 * (Math.round(maxDeadlift * 0.5) / 5));

        workout = new Workout("Workout A");

        workout.addExercise("s", "Competition squat", initialSquat);
        workout.addExercise("b", "Competition bench press", initialBenchPress);
        workout.addExercise("d", "Competition deadlift", initialDeadlift);

        for (Exercise exercise : workout.getExercises()) {
            for (int i = 0; i < setsPerExercise; i++) {
                exercise.addSet(i + 1);
            }
            for (Set set : exercise.getSets()) {
                for (int i = 0; i < repsPerSet; i++) {
                    set.addRep(i + 1);
                }
            }
        }
    }

    // EFFECTS: Displays screen with complete workout information and buttons to log workout with. along with menu,
    //          save, and next options
    @SuppressWarnings("methodlength")
    private void logWorkout() {
        int columns = workout.getExercises().get(0).getSets().size();

        JPanel loggerButtonPanel = new JPanel();
        loggerButtonPanel.setBackground(Color.red);

        loggerButtonPanel.setLayout(new GridLayout(0, columns, 10, 10));
        LogButtonListener logButtonListener;

        JPanel labelPanel = new JPanel();
        labelPanel.setBackground(Color.red);
        labelPanel.setLayout(new GridLayout(0, 1, 10, 10));
        labelPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        for (Exercise exercise : workout.getExercises()) {
            JLabel exerciseLabel = new JLabel(exercise.getDescription() + ": "
                    + (exercise.getWeight()) + " lbs", SwingConstants.RIGHT);
            exerciseLabel.setForeground(Color.white);
            labelPanel.add(exerciseLabel);
            for (Set set : exercise.getSets()) {
                JButton logButton = new JButton(logButtonText(set));
                logButtonListener = new LogButtonListener(logButton, set);
                logButton.addActionListener(logButtonListener);
                logButton.setFocusable(false);
                loggerButtonPanel.add(logButton);
            }
        }

        JButton menuButton = new ActionButton("Menu", "menu");
        JButton saveButton = new ActionButton("Save Progress", "save");
        nextButton = new ActionButton("Next Workout", "next");
        updateNextButton();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.red);
        buttonPanel.setLayout(new GridLayout(0, 3, 10, 10));
        buttonPanel.add(menuButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(nextButton);
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel loggerPanel = new JPanel();
        loggerPanel.setBackground(Color.red);
        loggerPanel.setLayout(new BorderLayout());
        loggerPanel.add(buttonPanel, BorderLayout.SOUTH);
        loggerPanel.add(loggerButtonPanel, BorderLayout.CENTER);
        loggerPanel.add(labelPanel, BorderLayout.WEST);
        loggerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        setPanel(loggerPanel);
    }

    // EFFECTS: Checks that all the reps in all the exercises have been marked to enable the next button, otherwise
    //          disables it
    // MODIFIES: nextButton
    private void updateNextButton() {
        boolean marked = true;
        for (Exercise exercise : workout.getExercises()) {
            for (Set set : exercise.getSets()) {
                for (Rep rep : set.getReps()) {
                    if (rep.getStatus() == 0) {
                        marked = false;
                        break;
                    }
                }
            }
        }
        nextButton.setEnabled(marked);
    }

    // EFFECTS: returns a sequence of text symbols corresponding to the status of each rep in a set
    private String logButtonText(Set set) {
        String logButtonText = "";
        for (Rep rep : set.getReps()) {
            if (rep.getStatus() == 1) {
                logButtonText += "✔ ";
            } else if (rep.getStatus() == -1) {
                logButtonText += "❌";
            } else {
                logButtonText += " _  ";
            }
        }
        return logButtonText;
    }

    // Represents a button listener that has a corresponding button and set
    private class LogButtonListener implements ActionListener {
        JButton button;
        Set set;

        // EFFECTS: Constructs a log button listener with a button and set
        public LogButtonListener(JButton button, Set set) {
            this.button = button;
            this.set = set;
        }

        // EFFECTS: When button is pressed, calls appropriate method depending on statuses of reps in set
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(button)) {
                if (repsUnmarked(set)) {
                    markReps(set);
                } else if (completeCount(set) != 0) {
                    addMiss(set);
                } else {
                    resetSet(set);
                }
                button.setText(logButtonText(set));
                updateNextButton();
            }
        }
    }

    // EFFECTS: Resets all rep statuses in a set
    // MODIFIES: set
    private void resetSet(Set set) {
        for (Rep rep : set.getReps()) {
            rep.reset();
        }
    }

    // EFFECTS: Marks all reps in a set as complete
    // MODIFIES: set
    private void markReps(Set set) {
        for (Rep rep : set.getReps()) {
            rep.complete();
        }
    }

    // EFFECTS: If all reps in a set are unmarked, returns true, else false
    private boolean repsUnmarked(Set set) {
        boolean repsUnmarked = true;
        for (Rep rep : set.getReps()) {
            if (rep.getStatus() == 1 || rep.getStatus() == -1) {
                repsUnmarked = false;
                break;
            }
        }
        return repsUnmarked;
    }

    // EFFECTS: Returns the number of reps in a set that are marked complete
    private int completeCount(Set set) {
        int complete = 0;
        for (Rep rep : set.getReps()) {
            if (rep.getStatus() == 1) {
                complete++;
            }
        }
        return complete;
    }

    // EFFECTS: Finds and changes to missed one rep that is marked complete
    // REQUIRES: At least one rep in set is marked complete
    // MODIFIES: set
    private void addMiss(Set set) {
        for (int i = set.getReps().size() - 1; i > -1; i--) {
            Rep rep = set.getReps().get(i);
            if (rep.getStatus() == 1) {
                rep.miss();
                break;
            }
        }
    }

    // This method references code from this CPSC210/JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: tries to write currentWorkout into json file, otherwise displays error message
    private void saveWorkout() {
        try {
            jsonWriter.open();
            jsonWriter.write(workout);
            jsonWriter.close();
            JOptionPane.showMessageDialog(frame, "Progress saved to " + FILE, "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error writing save file " + FILE,
                    "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Represents a window listener
    private class CloseListener implements WindowListener {

        @Override
        public void windowOpened(WindowEvent e) {
        }

        @Override
        public void windowClosing(WindowEvent e) {
            EventLog eventLog = EventLog.getInstance();
            for (Event event : eventLog) {
                System.out.println(event.toString());
            }
        }

        @Override
        public void windowClosed(WindowEvent e) {
        }

        @Override
        public void windowIconified(WindowEvent e) {
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
        }

        @Override
        public void windowActivated(WindowEvent e) {
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
        }
    }

    // Represents a field listener
    private class FieldListener implements KeyListener, ActionListener {

        // EFFECTS: Whenever a key is typed, calls check integer method
        @Override
        public void keyTyped(KeyEvent e) {
            checkForInteger();
        }

        // EFFECTS: Whenever a key is pressed, calls check integer method
        @Override
        public void keyPressed(KeyEvent e) {
            checkForInteger();
        }

        // EFFECTS: Whenever a key is released, calls check integer method
        @Override
        public void keyReleased(KeyEvent e) {
            checkForInteger();
        }

        // EFFECTS: Whenever an action is performed, calls check integer method
        @Override
        public void actionPerformed(ActionEvent e) {
            checkForInteger();
        }

        // EFFECTS: checks that all fields and dropdown menus in create screen can be parsed into integers, and enables
        //          create button, otherwise disables create button if exception is thrown
        // MODIFIES: createButton
        private void checkForInteger() {
            try {
                Integer.parseInt(squatMaxField.getText());
                Integer.parseInt(benchMaxField.getText());
                Integer.parseInt(deadliftMaxField.getText());
                Integer.parseInt((String) setsBox.getSelectedItem());
                Integer.parseInt((String) repsBox.getSelectedItem());
                createButton.setEnabled(true);
            } catch (Exception x) {
                createButton.setEnabled(false);
            }
        }
    }

    // Represents a button listener
    private class ButtonListener implements ActionListener {

        // EFFECTS: checks the action command of a button when an action is performed and calls corresponding method
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "new":
                    createNew();
                    break;
                case "load":
                    loadSaved();
                    break;
                case "create":
                    generateWorkout();
                    logWorkout();
                    break;
                case "save":
                    saveWorkout();
                    break;
                case "next":
                    workout.regenerate();
                    logWorkout();
                    break;
                case "menu":
                    menu();
                default:
                    break;
            }
        }
    }

    // Represents a button with an action command
    private class ActionButton extends JButton {

        // EFFECTS: Constructs a button with text, and adds action command and button listener, and sets focusable
        //          to false
        public ActionButton(String text, String action) {
            super(text);
            this.addActionListener(buttonListener);
            this.setActionCommand(action);
            this.setFocusable(false);
        }
    }
}
