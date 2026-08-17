package sherlock;

import javax.swing.*;

public class Main {

    public static void main(final String[] args) {
        final MainFrame mainFrame = new MainFrame(new Engine(Suspect.ALICE));
        SwingUtilities.invokeLater(() -> mainFrame.setVisible(true));
        if (
            JOptionPane.showConfirmDialog(
                mainFrame,
                Game.jobDescription(),
                "Auftrag",
                JOptionPane.YES_NO_OPTION
            ) == JOptionPane.NO_OPTION
        ) {
            System.exit(0);
        }
    }

}
