import java.util.*;

public class TSP {

    private double[][] distance;
    private int n;

    private double[][] dp;
    private int[][] parent;

    private static final double INF = Double.POSITIVE_INFINITY;

    public TSP(double[][] distance) {
        this.distance = distance;
        this.n = distance.length;
    }

    public TSPResult solve() {

        int totalMasks = 1 << n;

        dp = new double[totalMasks][n];
        parent = new int[totalMasks][n];

        // Initialize DP table
        for (int mask = 0; mask < totalMasks; mask++) {
            Arrays.fill(dp[mask], INF);
            Arrays.fill(parent[mask], -1);
        }

        // Start from city 0
        dp[1][0] = 0;

        // Bitmask Dynamic Programming
        for (int mask = 1; mask < totalMasks; mask++) {

            for (int current = 0; current < n; current++) {

                // Check whether current city is visited
                if ((mask & (1 << current)) == 0) {
                    continue;
                }

                if (dp[mask][current] == INF) {
                    continue;
                }

                // Try every unvisited city
                for (int next = 0; next < n; next++) {

                    // Skip if city is already visited
                    if ((mask & (1 << next)) != 0) {
                        continue;
                    }

                    int newMask = mask | (1 << next);

                    double newCost =
                            dp[mask][current] + distance[current][next];

                    if (newCost < dp[newMask][next]) {

                        dp[newMask][next] = newCost;
                        parent[newMask][next] = current;
                    }
                }
            }
        }

        // All cities visited
        int fullMask = totalMasks - 1;

        double minimumCost = INF;
        int lastCity = -1;

        // Find best last city and return to city 0
        for (int city = 1; city < n; city++) {

            double totalCost =
                    dp[fullMask][city] + distance[city][0];

            if (totalCost < minimumCost) {

                minimumCost = totalCost;
                lastCity = city;
            }
        }

        // Reconstruct route
        List<Integer> path =
                reconstructPath(fullMask, lastCity);

        Collections.reverse(path);

        // Add starting city at the end
        path.add(0);

        return new TSPResult(path, minimumCost);
    }

    private List<Integer> reconstructPath(
            int mask,
            int current) {

        List<Integer> path = new ArrayList<>();

        while (current != -1) {

            path.add(current);

            int previous = parent[mask][current];

            mask = mask ^ (1 << current);

            current = previous;
        }

        return path;
    }
}
