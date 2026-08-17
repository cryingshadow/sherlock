package sherlock;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

public class TimesPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static JButton timeButton(final String caption, final Engine engine) {
        final JButton result = new JButton(caption);
        result.setFont(new Font("Arial", Font.PLAIN, 24));
        result.addActionListener(
            new ActionListener() {

                @Override
                public void actionPerformed(final ActionEvent e) {
                    engine.timeClicked(caption);
                }

            }
        );
        return result;
    }

    public TimesPanel(final Engine engine) {
        this.setBackground(Color.WHITE);
        this.setLayout(new GridBagLayout());
        final GridBagConstraints constraints = new GridBagConstraints();
        final JPanel times = new JPanel();
        times.setLayout(new GridLayout(3, 3));
        for (int time = 1; time < 10; time++) {
            times.add(TimesPanel.timeButton(String.valueOf(time), engine));
        }
        final JLabel title = new JLabel("Wo waren Sie zu dieser Zeit?");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.NONE;
        this.add(title, constraints);
        constraints.gridy = 1;
        this.add(new JLabel(" "), constraints);
        constraints.gridy = 2;
        this.add(times, constraints);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

}
