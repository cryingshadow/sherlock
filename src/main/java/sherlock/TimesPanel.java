package sherlock;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

public class TimesPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static JButton timeButton(final String caption) {
        final JButton result = new JButton(caption);
        result.setFont(new Font("Arial", Font.PLAIN, 24));
        return result;
    }

    public TimesPanel() {
        this.setBackground(Color.WHITE);
        this.setLayout(new GridBagLayout());
        final GridBagConstraints constraints = new GridBagConstraints();
        final JPanel times = new JPanel();
        times.setLayout(new GridLayout(3, 3));
        times.add(TimesPanel.timeButton("1"));
        times.add(TimesPanel.timeButton("2"));
        times.add(TimesPanel.timeButton("3"));
        times.add(TimesPanel.timeButton("4"));
        times.add(TimesPanel.timeButton("5"));
        times.add(TimesPanel.timeButton("6"));
        times.add(TimesPanel.timeButton("7"));
        times.add(TimesPanel.timeButton("8"));
        times.add(TimesPanel.timeButton("9"));
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
