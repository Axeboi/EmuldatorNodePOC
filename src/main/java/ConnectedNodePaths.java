import java.util.ArrayList;
import java.util.List;

public class ConnectedNodePaths {
    public final Node origin;
    public final List<Node> end_points;

    ConnectedNodePaths(Node origin) {
        this.origin = origin;
        end_points = new ArrayList<Node>();
    }

    public void add(Node n) {
        end_points.add(n);
    }

    public Boolean includes_end_point(Node n) {
        return end_points.contains(n);
    }

    public Boolean includes_origin(Node origin) {
        return this.origin.id == origin.id;
    }
}
