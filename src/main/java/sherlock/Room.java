package sherlock;

import java.awt.*;

public enum Room {

    DINING_ROOM("Esszimmer", new double[][] {{0.2, 0.2}, {0.2, 0.5}, {0.55, 0.5}, {0.55, 0.2}}),

    GARAGE("Garage", new double[][] {{0.8, 0.2}, {0.8, 0.7}, {0.99, 0.7}, {0.99, 0.2}}),

    HOBBY_ROOM("Hobbyraum", new double[][] {{0.55, 0.5}, {0.8, 0.5}, {0.8, 0.9}, {0.55, 0.9}}),

    KITCHEN("Küche", new double[][] {{0.55, 0.2}, {0.55, 0.5}, {0.8, 0.5}, {0.8, 0.2}}),

    LIBRARY("Bibliothek", new double[][] {{0.3, 0.5}, {0.55, 0.5}, {0.55, 0.9}, {0.3, 0.9}}),

    LIVING_ROOM(
        "Wohnzimmer",
        new double[][] {{0.01, 0.9}, {0.3, 0.9}, {0.3, 0.5}, {0.2, 0.5}, {0.2, 0.2}, {0.01, 0.2}}
    ),

    OK("OK", new double[][] {{0.85, 0.8}, {0.99, 0.8}, {0.99, 0.9}, {0.85, 0.9}});

    public final String name;

    private final double[][] shape;

    private Room(final String name, final double[][] shape) {
        this.name = name;
        this.shape = shape;
    }

    public void draw(final Graphics2D canvas, final int width, final int height) {
        final Polygon polygon = this.toPolygon(width, height);
        canvas.setColor(RoomsPanel.ROOM_BACKGROUND);
        canvas.fillPolygon(polygon);
        canvas.setColor(Color.BLACK);
        canvas.setStroke(new BasicStroke(RoomsPanel.STROKE));
        canvas.drawPolygon(polygon);
        canvas.setFont(new Font("Arial", Font.BOLD, 20));
        canvas.setColor(Color.DARK_GRAY);
        final FontMetrics fontMetrics = canvas.getFontMetrics();
        final Rectangle bounds = polygon.getBounds();
        final int textX = bounds.x + (bounds.width - fontMetrics.stringWidth(this.name)) / 2;
        final int textY = bounds.y + (bounds.height + fontMetrics.getAscent()) / 2;
        canvas.drawString(this.name, textX, textY);
    }

    public Polygon toPolygon(final int width, final int height) {
        final Polygon result = new Polygon();
        for (int i = 0; i < this.shape.length; i++) {
            final int xCoordinate = (int)(width * this.shape[i][0]);
            final int yCoordinate = (int)(height * this.shape[i][1]);
            result.addPoint(xCoordinate, yCoordinate);
        }
        return result;
    }

}