import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.*;

@SuppressWarnings("serial")
public class LinearGraph extends JPanel {
    private static final int MAX_HEIGHT = 20;
    private static final int PREFER_WIDTH = 800;
    private static final int PREFER_HEIGHT = 250;
    private static final int GAP = 30;
    private static final Stroke GRAPH_STROKE = new BasicStroke(3f);
    private static final Color GRAPH_COLOR = Color.BLUE;
    private static final int GRAPH_POINT_WIDTH = 12;
    private static final Color GRAPH_POINT_COLOR = new Color(0,0,139, 180);
    private static final int Y_HATCH_CNT = 10;
    private ArrayList<Double> scores;

    public LinearGraph(List<Double> scores) {
        this.scores = new ArrayList<Double>();
        for (int i = 0; i < scores.size(); i++) {
            this.scores.add(scores.get(i));
        }
    }

    public void drawLines(Graphics2D graph2d) {
        for (int i = 0; i < Y_HATCH_CNT; i++) {
            int x0 = GAP;
            int x1 = GRAPH_POINT_WIDTH + GAP;
            int y0 = getHeight() - (((i + 1) * (getHeight() - GAP * 2)) / Y_HATCH_CNT + GAP);
            int y1 = y0;
            graph2d.drawLine(x0, y0, x1, y1);
        }
        for (int i = 0; i < scores.size() - 1; i++) {
            int x0 = (i + 1) * (getWidth() - GAP * 2) / (scores.size() - 1) + GAP;
            int x1 = x0;
            int y0 = getHeight() - GAP;
            int y1 = y0 - GRAPH_POINT_WIDTH;
            graph2d.drawLine(x0, y0, x1, y1);
        }
    }

    public void drawLinesSetColor(Graphics2D graph2d, ArrayList<Point> graphPoints) {
        Stroke oldStroke = graph2d.getStroke();
        graph2d.setColor(GRAPH_COLOR);
        graph2d.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            graph2d.drawLine(x1, y1, x2, y2);
        }
        graph2d.setStroke(oldStroke);
        graph2d.setColor(GRAPH_POINT_COLOR);
    }

    public void fillOvals(Graphics2D graph2d, ArrayList<Point> graphPoints) {
        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - GRAPH_POINT_WIDTH / 2;
            int y = graphPoints.get(i).y - GRAPH_POINT_WIDTH / 2;
            int ovalW = GRAPH_POINT_WIDTH;
            int ovalH = GRAPH_POINT_WIDTH;
            graph2d.fillOval(x, y, ovalW, ovalH);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREFER_WIDTH, PREFER_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics graph) {
        super.paintComponent(graph);
        Graphics2D graph2d = (Graphics2D) graph;
        graph2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double xScale = ((double) getWidth() - 2 * GAP) / (scores.size() - 1);
        double yScale = ((double) getHeight() - 2 * GAP) / (MAX_HEIGHT - 1);
        ArrayList<Point> graphPoints = new ArrayList<Point>();
        for (int i = 0; i < scores.size(); i++) {
            int x1 = (int) (i * xScale + GAP );
            int y1 = (int) ((MAX_HEIGHT - scores.get(i)) * yScale + GAP);
            graphPoints.add(new Point(x1, y1));
        }
        graph2d.drawLine(GAP, getHeight() - GAP, GAP, GAP);
        graph2d.drawLine(GAP, getHeight() - GAP, getWidth() - GAP, getHeight() - GAP);
        drawLines(graph2d);
        drawLinesSetColor(graph2d,graphPoints);
        fillOvals(graph2d,graphPoints);
    }

    private static void drawLinearGraph(ArrayList<ArrayList<Double>> gamesPlayed) {
        ArrayList<Double> scores = new ArrayList<Double>();
        for (int i = 0; i < gamesPlayed.size(); i++) {
            double total = 0;
            for (int j = 0; j < gamesPlayed.get(i).size(); j++) {
                total += (double) gamesPlayed.get(i).get(j);
            }
            total /= gamesPlayed.get(i).size();
            scores.add((Double) total);
        }
        LinearGraph mainPanel = new LinearGraph(scores);
        JFrame frame = new JFrame("DrawGraph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ArrayList<ArrayList<Double>> matchesPlayed = new ArrayList<>();
                ArrayList<Double> arr1 = new ArrayList<Double>(Arrays.asList(3.9,4.9,3.5,3.0,4.5,4.7,1.0,3.7,3.8,3.2));
                matchesPlayed.add(arr1);
                ArrayList<Double> arr2 = new ArrayList<Double>(Arrays.asList(4.5,3.7,1.7,1.3,4.8,3.2,2.3,3.8,2.6,2.1));
                matchesPlayed.add(arr2);
                ArrayList<Double> arr3 = new ArrayList<Double>(Arrays.asList(1.4,3.3,4.1,1.1,4.4,4.6,4.1,3.9,2.3,2.3));
                matchesPlayed.add(arr3);
                ArrayList<Double> arr4 = new ArrayList<Double>(Arrays.asList(4.3,3.5,4.0,2.1,4.2,3.5,3.6,4.5,2.2,3.5));
                matchesPlayed.add(arr4);
                ArrayList<Double> arr5 = new ArrayList<Double>(Arrays.asList(3.9,4.9,3.5,3.0,4.5,4.7,1.0,3.7,3.8,3.2));
                matchesPlayed.add(arr5);
                ArrayList<Double> arr6 = new ArrayList<Double>(Arrays.asList(1.3,1.1,4.4,1.7,2.0,1.6,1.5,4.4,2.2,4.4));
                matchesPlayed.add(arr6);
                ArrayList<Double> arr7 = new ArrayList<Double>(Arrays.asList(1.4,1.9,1.4,1.6,3.7,1.4,5.0,2.5,4.8,3.6));
                matchesPlayed.add(arr7);
                ArrayList<Double> arr8 = new ArrayList<Double>(Arrays.asList(4.3,3.5,4.0,2.1,4.2,3.5,3.6,4.5,2.2,3.5));
                matchesPlayed.add(arr8);
                ArrayList<Double> arr9 = new ArrayList<Double>(Arrays.asList(4.7,2.5,4.0,4.8,2.9,4.2,1.3,4.3,4.0,2.6,1.9));
                matchesPlayed.add(arr9);
                ArrayList<Double> arr10 = new ArrayList<Double>(Arrays.asList(2.3,1.3,2.9,4.6,3.5,3.7,1.8,2.8,2.8,2.8));
                matchesPlayed.add(arr10);
                drawLinearGraph(matchesPlayed);
            }
        });
    }
}
