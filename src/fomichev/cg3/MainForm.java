package fomichev.cg3;

import fomichev.cg3.draw.DrawCurve;
import javax.swing.*;
import java.awt.*;
//import java.beans.Expression;
//import java.beans.Expression;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainForm extends JFrame {
    private JTextField textFieldFunc;
    private JSpinner spinnerValueA;
    private JSpinner spinnerValueX;
    private JSpinner spinnerValueY;
    private JButton buttonAddPoint;
    private JComboBox comboBoxMethod;
    private JButton buttonDrawFunc;
    private JPanel panelDraw;
    private JPanel panelMain;
    private JPanel panelCurve;
    private JButton buttonClear;
    private JPanel panel;
    private JSpinner spinnerScale;
    private JButton buttonDrawFuncWithMethod;
    private JButton buttonRandom;
    private JTextField textFieldStep;

    private int centerX;
    private int centerY;
    private int step;
    private List<Point> pointsFunc = new ArrayList<>();
    private List<Point> pointsCurve = new ArrayList<>();
    private List<Point> changedPoints = new ArrayList<>();

    private boolean withMethod;

//    DrawFunc drawFunc;
    DrawCurve drawCurve;

    public MainForm() {
        this.setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.pack();
        this.setSize(new Dimension(1200, 700));
        this.spinnerScale.setValue(1);
//        DrawPanel drawPanel = new DrawPanel();

//        textFieldFunc.setText("x^2");
//        textFieldStep.setText("1");

        buttonClear.addActionListener( k -> {
            pointsFunc.clear();
            pointsCurve.clear();
        });

//        buttonDrawFunc.addActionListener(k -> {
//            withMethod = false;
//            pointsFunc.clear();
//            for (double x = -centerX; x <= centerX; x += Double.parseDouble(textFieldStep.getText())) {
//                if (x == 0) x += 0.0000001;
//                if (findParameter()) {
//                    Expression e = new ExpressionBuilder(textFieldFunc.getText())
//                            .variables("x", "a")
//                            .build()
//                            .setVariable("x", x)
//                            .setVariable("a", Integer.parseInt(String.valueOf(spinnerValueA.getValue())));
//                    double y = e.evaluate();
//                    if (y >= -centerY && y <= centerY) pointsFunc.add(new Point(x, y));
//                } else {
//                    Expression e = new ExpressionBuilder(textFieldFunc.getText())
//                            .variables("x")
//                            .build()
//                            .setVariable("x", x);
//                    double y = e.evaluate();
//                    if (y >= -centerY && y <= centerY) pointsFunc.add(new Point(x, y));
//                }
//            }
//        });

//        buttonDrawFuncWithMethod.addActionListener(k -> {
//            withMethod = true;
//            pointsFunc.clear();
//            for (double x = -centerX; x <= centerX; x += Double.parseDouble(textFieldStep.getText())) {
//                if (x == 0) x += 0.0000001;
//                if (findParameter()) {
//                    Expression e = new ExpressionBuilder(textFieldFunc.getText())
//                            .variables("x", "a")
//                            .build()
//                            .setVariable("x", x)
//                            .setVariable("a", Integer.parseInt(String.valueOf(spinnerValueA.getValue())));
//                    double y = e.evaluate();
//                    if (y >= -centerY && y <= centerY) pointsFunc.add(new Point(x, y));
//                } else {
//                    Expression e = new ExpressionBuilder(textFieldFunc.getText())
//                            .variables("x")
//                            .build()
//                            .setVariable("x", x);
//                    double y = e.evaluate();
//                    if (y >= -centerY && y <= centerY) pointsFunc.add(new Point(x, y));
//                }
//            }
//        });

        buttonAddPoint.addActionListener(e -> {
            pointsFunc.clear();
            centerX = panelDraw.getWidth() / 2;
            centerY = panelDraw.getHeight() / 2;
            step = 10 * Integer.parseInt(String.valueOf(spinnerScale.getValue()));
            pointsCurve.add((new Point(Float.parseFloat(String.valueOf(spinnerValueX.getValue())),
                    Math.round(Float.parseFloat(String.valueOf(spinnerValueY.getValue()))))));
        });

        buttonRandom.addActionListener(k -> {
            pointsFunc.clear();
            centerX = panelDraw.getWidth() / 2;
            centerY = panelDraw.getHeight() / 2;
            step = 10 * Integer.parseInt(String.valueOf(spinnerScale.getValue()));
            Random r = new Random();
            for (int i = 0; i < 4; i++) {
                pointsCurve.add((new Point(r.nextInt(-panelDraw.getWidth()/(2 * step), panelDraw.getWidth()/(2 * step)),
                        r.nextInt(-panelDraw.getHeight()/(2 * step),panelDraw.getHeight()/(2 * step)))));
            }
        });
    }

    private boolean findParameter() {
        String str = textFieldFunc.getText();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == 'a') return true;
        }
        return false;
    }

    private void createUIComponents() {
        panelDraw = new DrawPanel();
        panelDraw.setMinimumSize(new Dimension(700, 650));
    }

    public class DrawPanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            centerX = panelDraw.getWidth() / 2;
            centerY = panelDraw.getHeight() / 2;
            step = 10 * Integer.parseInt(String.valueOf(spinnerScale.getValue()));

            drawLines(g2, centerX, centerY, step);
            g2.setStroke(new BasicStroke(2));
            drawAxes(g2, centerX, centerY);
            g2.setStroke(new BasicStroke(1));

//            if (pointsFunc.size() > 0 && !withMethod) {
////                drawFunc = new DrawFunc(g2, centerX, centerY, step, pointsFunc);
////                drawFunc.paintFunc();
//            } else
            if (pointsFunc.size() > 0) {
                drawCurve = new DrawCurve(g2, centerX, centerY, step, pointsFunc, "Б-сплайн");
                drawCurve.paintCurve();
            } else if (pointsCurve.size() > 0) {
                drawCurve = new DrawCurve(g2, centerX, centerY, step, pointsCurve, String.valueOf(comboBoxMethod.getSelectedItem()));
                drawCurve.paintCurve();
            }
            g2.setColor(Color.black);
            repaint();

        }
        private void drawAxes(Graphics2D g2, int x, int y) {
            g2.setColor(new Color(0, 0, 0));
            g2.drawLine(x, 0, x, y * 2);
            g2.drawLine(0, y, x * 2, y);
        }

        private void drawLines(Graphics2D g2, int centerX, int centerY, int one_step) {
            g2.setColor(new Color(200, 200, 200));
            int step = centerX;
            while (step <= centerX * 2) {
                g2.drawLine(step, 0, step, centerY * 2);
                step += one_step;
            }
            step = centerX;
            while (step >= 0) {
                g2.drawLine(step, 0, step, centerY * 2);
                step -= one_step;
            }
            step = centerY;
            while (step >= 0) {
                g2.drawLine(0, step, centerX * 2, step);
                step -= one_step;
            }
            step = centerY;
            while (step <= centerY * 2) {
                g2.drawLine(0, step, centerX * 2, step);
                step += one_step;
            }
        }
    }
}