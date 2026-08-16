package sherlock;

import java.awt.*;

public record Room(String name, double[][] shape) {

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
        final int textX = bounds.x + (bounds.width - fontMetrics.stringWidth(this.name())) / 2;
        final int textY = bounds.y + (bounds.height + fontMetrics.getAscent()) / 2;
        canvas.drawString(this.name(), textX, textY);
    }

    public Polygon toPolygon(final int width, final int height) {
        final Polygon result = new Polygon();
        for (int i = 0; i < this.shape().length; i++) {
            final int xCoordinate = (int)(width * this.shape()[i][0]);
            final int yCoordinate = (int)(height * this.shape()[i][1]);
            result.addPoint(xCoordinate, yCoordinate);
        }
        return result;
    }

}