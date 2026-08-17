package sherlock;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class SuspectsPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public SuspectsPanel(final Engine engine) {
        this.setBackground(Color.WHITE);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        final JList<String> suspects =
            new JList<String>(new String[]{"Alice", "Bob", "Charlie", "Doris", "Edward", "Fiona"});
        ((DefaultListCellRenderer)suspects.getCellRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        suspects.setAlignmentX(Component.CENTER_ALIGNMENT);
        suspects.setFont(new Font("Arial", Font.PLAIN, 24));
        suspects.setSelectedIndex(0);
        suspects.addListSelectionListener(
            new ListSelectionListener() {

                @Override
                public void valueChanged(final ListSelectionEvent e) {
                    engine.suspectSelected(suspects.getSelectedValue());
                }

            }
        );
        final JLabel title = new JLabel("Verdächtige:");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        this.add(title);
        this.add(new JLabel(" "));
        this.add(suspects);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

}
