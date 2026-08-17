package sherlock;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

public class RoomsPanel extends JPanel {

    public static final Color ROOM_BACKGROUND = new Color(225, 235, 245);

    public static final int STROKE = 4;

    private static final long serialVersionUID = 1L;

    public RoomsPanel(final Engine engine) {
        this.setBackground(Color.WHITE);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final Room clickedRoom = RoomsPanel.this.findRoom(e.getX(), e.getY());
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
        for (final Room room : Room.values()) {
            room.draw(canvas, width, height);
        }
        for (final Door door : Game.DOORS) {
            door.draw(canvas, width, height);
        }
        canvas.dispose();
    }

    private Room findRoom(final int x, final int y) {
        for (final Room room : Room.values()) {
            if (room.toPolygon(this.getWidth(), this.getHeight()).contains(x, y)) {
                return room;
            }
        }
        return null;
    }
}
