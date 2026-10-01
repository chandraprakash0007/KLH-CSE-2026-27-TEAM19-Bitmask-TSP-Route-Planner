import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RouteVisualizer extends JPanel {

    private List<City> cities;
    private List<Integer> path;

    public RouteVisualizer(
            List<City> cities,
            List<Integer> path) {

        this.cities = cities;
        this.path = path;

        setPreferredSize(new Dimension(900, 600));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(3));

        // Draw route lines
        for (int i = 0; i < path.size() - 1; i++) {

            City city1 = cities.get(path.get(i));
            City city2 = cities.get(path.get(i + 1));

            int x1 = (int) city1.getX();
            int y1 = (int) city1.getY();

            int x2 = (int) city2.getX();
            int y2 = (int) city2.getY();

            g2.drawLine(x1, y1, x2, y2);
        }

        // Draw cities
        for (City city : cities) {

            int x = (int) city.getX();
            int y = (int) city.getY();

            g2.fillOval(
                    x - 10,
                    y - 10,
                    20,
                    20
            );

            g2.drawString(
                    city.getName(),
                    x + 15,
                    y + 5
            );
        }
    }

    public static void showRoute(
            List<City> cities,
            List<Integer> path) {

        JFrame frame =
                new JFrame("Bitmask TSP - Optimal Route");

        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        frame.add(
                new RouteVisualizer(cities, path)
        );

        frame.pack();

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}
