import java.util.List;

public class TSPResult {

    private List<Integer> path;
    private double cost;

    public TSPResult(List<Integer> path, double cost) {
        this.path = path;
        this.cost = cost;
    }

    public List<Integer> getPath() {
        return path;
    }

    public double getCost() {
        return cost;
    }
}