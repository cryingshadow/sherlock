package sherlock;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public MainFrame(final Engine engine) {
        this.setTitle("Sherlock");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.getRootPane().registerKeyboardAction(
            e -> System.exit(0),
            KeyStroke.getKeyStroke("ESCAPE"),
            JComponent.WHEN_IN_FOCUSED_WINDOW
        );
        final JButton lastSeen = new JButton(String.format("Wann haben Sie %s zuletzt gesehen?", Game.VICTIM));
        lastSeen.setFont(new Font("Arial", Font.BOLD, 32));
        lastSeen.setBorder(new EmptyBorder(10, 10, 10, 10));
        lastSeen.addActionListener(
            new ActionListener() {

                @Override
                public void actionPerformed(final ActionEvent e) {
                    engine.askedForGeorge();
                }

            }
        );
        final JTextArea messages = new JTextArea();
        engine.registerMessageListener(message -> messages.setText(message));
        final Container content = this.getContentPane();
        content.setLayout(new GridBagLayout());
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        content.add(lastSeen, constraints);
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.gridwidth = 1;
        constraints.gridy = 1;
        content.add(new SuspectsPanel(engine), constraints);
        constraints.gridx = 1;
        content.add(new TimesPanel(engine), constraints);
        constraints.weightx = 1;
        constraints.gridx = 2;
        constraints.fill = GridBagConstraints.BOTH;
        content.add(new RoomsPanel(engine), constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 3;
        content.add(messages, constraints);
    }

}
