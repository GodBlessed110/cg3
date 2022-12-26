package fomichev.cg3.draw;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import fomichev.cg3.Point;
import fomichev.cg3.method.BSpline;
import fomichev.cg3.method.Bezier;

public class DrawCurve {

    private final Graphics2D g2;
    private final int centerX;
    private final int centerY;
    private final int step;
    private final List<fomichev.cg3.Point> pointsCurve;
    private final List<fomichev.cg3.Point> changedPoints = new ArrayList<>();
    private final String method;

    public DrawCurve(Graphics2D g2, int centerX, int centerY, int step, List<Point> pointsCurve, String method) {
        this.g2 = g2;
        this.centerX = centerX;
        this.centerY = centerY;
        this.step = step;
        this.pointsCurve = pointsCurve;
        this.method = method;
    }

    public void paintCurve() {
        List<fomichev.cg3.Point> pointsToDrawCurve = new ArrayList<>();
        changedPoints.clear();
        g2.setStroke(new BasicStroke(1));
        switch (method) {
            case "Безье" -> {
                if (pointsCurve.size() > 2) {
                    Bezier bezier = new Bezier(pointsCurve);
                    bezier.calculation();
                    pointsToDrawCurve = bezier.getAddPoints();
                }
            }
            case "Б-сплайн" -> {
                if (pointsCurve.size() > 3) {
                    BSpline bSpline = new BSpline(pointsCurve);
                    bSpline.calculation();
                    pointsToDrawCurve = bSpline.getAddPoints();
                }
            }
        }
        g2.setColor(new Color(60,200,60));
        DrawPoints dp = new DrawPoints(g2, centerX, centerY, step, pointsToDrawCurve);
        dp.connectingPoints();
        drawPoints(pointsCurve);
    }

    private void drawPoints(List<fomichev.cg3.Point> list) {
        for (fomichev.cg3.Point value : list) {
            fomichev.cg3.Point coordinates = new Point(0, 0);
            coordinates.setX(value.getX() * step + centerX);
            coordinates.setY(centerY - value.getY() * step);

            g2.setColor(new Color(128,0,128));
            g2.fillOval((int)Math.round(coordinates.getX()) - 2, (int)Math.round(coordinates.getY()) - 2, 4, 4);
            changedPoints.add(coordinates);
        }
    }

}