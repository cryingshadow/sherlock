package sherlock;

import javax.swing.*;

public class Main {

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame(new Engine("Alice")).setVisible(true));
    }

}
