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
        final JList<Suspect> suspects = new JList<Suspect>(Suspect.values());
        final DefaultListCellRenderer renderer =
            new DefaultListCellRenderer() {

                private static final long serialVersionUID = 1L;

                @Override
                public Component getListCellRendererComponent(
                    final JList<?> list,
                    final Object value,
                    final int index,
                    final boolean isSelected,
                    final boolean cellHasFocus)
                {
                    if (value instanceof final Suspect suspect) {
                        return super.getListCellRendererComponent(list, suspect.name, index, isSelected, cellHasFocus);
                    }
                    return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                }

            };
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        suspects.setCellRenderer(renderer);
//        final DefaultListCellRenderer renderer = (DefaultListCellRenderer)suspects.getCellRenderer();
//        renderer.
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
