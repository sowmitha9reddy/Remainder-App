
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class RemainderApp {

    private JFrame frame;
    private JComboBox<String> dayComboBox;
    private JSpinner timeSpinner;
    private JComboBox<String> activityComboBox;
    private JButton setReminderButton;
    private JButton stopAlarmButton;
    private Timer timer;
    private Clip clip;
    private Timer backgroundFlasher;

    public RemainderApp() {
        frame = new JFrame("Colorful Reminder App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        // Setting the background color for the frame
        frame.getContentPane().setBackground(new Color(245, 245, 220)); // Beige background

        // Day selection
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        dayComboBox = new JComboBox<>(days);
        dayComboBox.setForeground(Color.WHITE);
        dayComboBox.setBackground(new Color(70, 130, 180)); // SteelBlue color
        dayComboBox.setFont(new Font("Arial", Font.BOLD, 14));

        // Time selection
        SpinnerDateModel model = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.HOUR_OF_DAY);
        timeSpinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(editor);
        timeSpinner.setForeground(Color.WHITE);
        timeSpinner.setBackground(new Color(100, 149, 237)); // CornflowerBlue color
        timeSpinner.setFont(new Font("Arial", Font.BOLD, 14));

        // Activity selection
        String[] activities = {"Wake up", "Go to gym", "Breakfast", "Meetings", "Lunch", "Quick nap", "Go to library", "Dinner", "Go to sleep"};
        activityComboBox = new JComboBox<>(activities);
        activityComboBox.setForeground(Color.WHITE);
        activityComboBox.setBackground(new Color(60, 179, 113)); // MediumSeaGreen color
        activityComboBox.setFont(new Font("Arial", Font.BOLD, 14));

        // Set reminder button with color and font
        setReminderButton = new JButton("Set Reminder");
        setReminderButton.setForeground(Color.WHITE);
        setReminderButton.setBackground(new Color(255, 99, 71)); // Tomato color
        setReminderButton.setFont(new Font("Arial", Font.BOLD, 14));
        setReminderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setReminder();
            }
        });

        // Stop alarm button with color and font
        stopAlarmButton = new JButton("Stop Alarm");
        stopAlarmButton.setForeground(Color.WHITE);
        stopAlarmButton.setBackground(new Color(178, 34, 34)); // Firebrick color
        stopAlarmButton.setFont(new Font("Arial", Font.BOLD, 14));
        stopAlarmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopAlarm();
            }
        });

        // Add components to the frame
        frame.add(new JLabel("Select Day:"));
        frame.add(dayComboBox);
        frame.add(new JLabel("Select Time:"));
        frame.add(timeSpinner);
        frame.add(new JLabel("Select Activity:"));
        frame.add(activityComboBox);
        frame.add(setReminderButton);
        frame.add(stopAlarmButton);

        frame.setSize(400, 250);
        frame.setVisible(true);
    }

    // Method to set reminder
    private void setReminder() {
        String day = (String) dayComboBox.getSelectedItem();
        String time = timeSpinner.getValue().toString();
        String activity = (String) activityComboBox.getSelectedItem();

        // Icon for the dialog box
        ImageIcon icon = new ImageIcon("path_to_your_icon.png"); // Replace with your icon file path
        JOptionPane.showMessageDialog(frame, "Reminder set for " + activity + " on " + day + " at " + time, "Reminder Set", JOptionPane.INFORMATION_MESSAGE, icon);

        // Timer to simulate reminder
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                playSound();
                flashBackground();
            }
        }, 5000); // Example: reminder after 5 seconds (for testing)
    }

    // Method to play a notification sound
    private void playSound() {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File("C:\\Users\\Reddemma\\eclipse-workspace\\RemainderAPPP\\Ninnu_Kanna_Kanulae.wav"))); // Replace with your sound file path
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to flash background
    private void flashBackground() {
        backgroundFlasher = new Timer();
        backgroundFlasher.schedule(new TimerTask() {
            private boolean toggle = false;

            @Override
            public void run() {
                if (toggle) {
                    frame.getContentPane().setBackground(new Color(255, 69, 0)); // Red
                } else {
                    frame.getContentPane().setBackground(new Color(245, 245, 220)); // Beige background
                }
                toggle = !toggle;
            }
        }, 0, 500); // Flash every 500ms
    }

    // Method to stop the alarm and background flashing
    private void stopAlarm() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
        if (backgroundFlasher != null) {
            backgroundFlasher.cancel();
            frame.getContentPane().setBackground(new Color(245, 245, 220)); // Reset to original color
        }
    }

    public static void main(String[] args) {
        new RemainderApp();
    }
}
