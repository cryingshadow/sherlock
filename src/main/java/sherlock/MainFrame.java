package sherlock;

import java.awt.*;

import javax.swing.*;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private final RoomsPanel grundrissPanel;

    public MainFrame() {
        this.setTitle("Gebäudegrundriss");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Vollbildmodus
        this.setUndecorated(true);
        this.setExtendedState(Frame.MAXIMIZED_BOTH);

        this.grundrissPanel = new RoomsPanel();
        this.add(this.grundrissPanel);

        // ESC beendet den Vollbildmodus / das Programm
        this.getRootPane()
            .registerKeyboardAction(
                e -> System.exit(0),
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW
            );
    }

}
