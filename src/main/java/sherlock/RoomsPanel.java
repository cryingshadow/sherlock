package sherlock;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;

public class RoomsPanel extends JPanel {

    public static final Color ROOM_BACKGROUND = new Color(225, 235, 245);

    public static final int STROKE = 4;

    private static final long serialVersionUID = 1L;

    private final List<Door> doors;

    private final List<Room> rooms;

    public RoomsPanel(final Engine engine) {
        this.setBackground(Color.WHITE);
        this.rooms =
            List.of(
                new Room("Hobbyraum", new double[][] {{0.55, 0.5}, {0.8, 0.5}, {0.8, 0.9}, {0.55, 0.9}}),
                new Room(
                    "Wohnzimmer",
                    new double[][] {{0.01, 0.9}, {0.3, 0.9}, {0.3, 0.5}, {0.2, 0.5}, {0.2, 0.2}, {0.01, 0.2}}
                ),
                new Room("Bibliothek", new double[][] {{0.3, 0.5}, {0.55, 0.5}, {0.55, 0.9}, {0.3, 0.9}}),
                new Room("Esszimmer", new double[][] {{0.2, 0.2}, {0.2, 0.5}, {0.55, 0.5}, {0.55, 0.2}}),
                new Room("Küche", new double[][] {{0.55, 0.2}, {0.55, 0.5}, {0.8, 0.5}, {0.8, 0.2}}),
                new Room("Garage", new double[][] {{0.8, 0.2}, {0.8, 0.7}, {0.99, 0.7}, {0.99, 0.2}}),
                new Room("OK", new double[][] {{0.85, 0.8}, {0.99, 0.8}, {0.99, 0.9}, {0.85, 0.9}})
            );
        this.doors =
            List.of(
                new Door(this.rooms.get(1), this.rooms.get(2)),
                new Door(this.rooms.get(1), this.rooms.get(3)),
                new Door(this.rooms.get(0), this.rooms.get(2)),
                new Door(this.rooms.get(0), this.rooms.get(5)),
                new Door(this.rooms.get(3), this.rooms.get(4)),
                new Door(this.rooms.get(4), this.rooms.get(5))
            );
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final String clickedRoom = RoomsPanel.this.findRoom(e.getX(), e.getY());
                if (clickedRoom != null) {
                    engine.roomClicked(clickedRoom);
                }
            }
        });
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D canvas = (Graphics2D) g.create();
        canvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        canvas.setColor(Color.DARK_GRAY);
        canvas.setFont(new Font("Arial", Font.BOLD, 32));
        final String titel = "Wann waren Sie in diesem Raum?";
        final FontMetrics fm = canvas.getFontMetrics();
        canvas.drawString(titel, (this.getWidth() - fm.stringWidth(titel)) / 2, 55);
        final int width = this.getWidth();
        final int height = this.getHeight();
        for (final Room room : this.rooms) {
            room.draw(canvas, width, height);
        }
        for (final Door door : this.doors) {
            door.draw(canvas, width, height);
        }
        canvas.dispose();
    }

    private String findRoom(final int x, final int y) {
        for (final Room room : this.rooms) {
            if (room.toPolygon(this.getWidth(), this.getHeight()).contains(x, y)) {
                return room.name();
            }
        }
        return null;
    }
}
