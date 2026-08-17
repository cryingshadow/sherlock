package sherlock;

import java.awt.*;

public record Door(Room room1, Room room2) {

    public void draw(final Graphics2D canvas, final int width, final int height) {
        canvas.setColor(RoomsPanel.ROOM_BACKGROUND);
        final Rectangle bounds1 = this.room1().toPolygon(width, height).getBounds();
        final Rectangle bounds2 = this.room2().toPolygon(width, height).getBounds();
        final Rectangle intersection = bounds1.intersection(bounds2);
        final int halfStroke = RoomsPanel.STROKE / 2 + 1;
        final int stroke = halfStroke * 2;
        if (intersection.width > 0) {
            if (intersection.height > 0) {
                final int area1 = bounds1.width * bounds1.height;
                final int area2 = bounds2.width * bounds2.height;
                if (area1 > area2) {
                    if (intersection.width > intersection.height) {
                        if (intersection.y == bounds2.y) {
                            canvas.fillRect(
                                intersection.width / 2 + intersection.x - intersection.width / 8,
                                intersection.y - halfStroke,
                                intersection.width / 4,
                                stroke
                            );
                        } else {
                            canvas.fillRect(
                                intersection.width / 2 + intersection.x - intersection.width / 8,
                                intersection.y + intersection.height - halfStroke,
                                intersection.width / 4,
                                stroke
                            );
                        }
                    } else {
                        if (intersection.x == bounds2.x) {
                            canvas.fillRect(
                                intersection.x - halfStroke,
                                intersection.height / 2 + intersection.y - intersection.height / 8,
                                stroke,
                                intersection.height / 4
                            );
                        } else {
                            canvas.fillRect(
                                intersection.x + intersection.width - halfStroke,
                                intersection.height / 2 + intersection.y - intersection.height / 8,
                                stroke,
                                intersection.height / 4
                            );
                        }
                    }
                } else {
                    if (intersection.width > intersection.height) {
                        if (intersection.y == bounds1.y) {
                            canvas.fillRect(
                                intersection.width / 2 + intersection.x - intersection.width / 8,
                                intersection.y - halfStroke,
                                intersection.width / 4,
                                stroke
                            );
                        } else {
                            canvas.fillRect(
                                intersection.width / 2 + intersection.x - intersection.width / 8,
                                intersection.y + intersection.height - halfStroke,
                                intersection.width / 4,
                                stroke
                            );
                        }
                    } else {
                        if (intersection.x == bounds1.x) {
                            canvas.fillRect(
                                intersection.x - halfStroke,
                                intersection.height / 2 + intersection.y - intersection.height / 8,
                                stroke,
                                intersection.height / 4
                            );
                        } else {
                            canvas.fillRect(
                                intersection.x + intersection.width - halfStroke,
                                intersection.height / 2 + intersection.y - intersection.height / 8,
                                stroke,
                                intersection.height / 4
                            );
                        }
                    }
                }
            } else {
                canvas.fillRect(
                    intersection.width / 2 + intersection.x - intersection.width / 8,
                    intersection.y - halfStroke,
                    intersection.width / 4,
                    stroke
                );
            }
        } else {
            canvas.fillRect(
                intersection.x - halfStroke,
                intersection.height / 2 + intersection.y - intersection.height / 8,
                stroke,
                intersection.height / 4
            );
        }
    }

    public Room nextRoom(final Room room) {
        if (room == this.room1()) {
            return this.room2();
        }
        if (room == this.room2()) {
            return this.room1();
        }
        return null;
    }

}
