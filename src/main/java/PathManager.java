import java.util.ArrayList;
import java.util.List;

public class PathManager {
    List<ConnectedNodePaths> paths = new ArrayList<ConnectedNodePaths>();

    public void add(ConnectedNodePaths path) {
        paths.add(path);
    }

    public void remove(Node origin) {
        for (ConnectedNodePaths p: paths) {
            if (p.origin.id == origin.id) {
                paths.remove(p);
            }
        }
    }

    public int size() {
        return paths.size();
    }

    public ConnectedNodePaths get(int i) {
        return paths.get(i);
    }

    public Boolean has_complete_path(Node origin) {
        for (ConnectedNodePaths path: paths) {
            if (path.origin.id == origin.id) {
                return path.end_points.size() > 0;
            }
        }

        return false;
    }

    public Boolean includes_end_point(Node n) {
        for (ConnectedNodePaths path: paths) {
            if (path.includes_origin(n)) return true;
        }

        return false;
    }

    public Boolean includes_start_point(Node n) {
        for (ConnectedNodePaths path: paths) {
            if (path.origin.id == n.id) return true;
        }

        return false;
    }
}
