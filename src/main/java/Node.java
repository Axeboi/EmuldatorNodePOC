import java.util.ArrayList;
import java.util.List;

public class Node {
    private static int id_generator = 0;

    enum Type {
        VOLTAGE,
        GROUND,
        NONE
    }

    public int id;
    public List<Node> children;
    public String data = "I am a node";
    public Type type = null;
    public Boolean visited = false;

    Node() {
        id = id_generator++;
        children = new ArrayList<Node>();
    }
}
