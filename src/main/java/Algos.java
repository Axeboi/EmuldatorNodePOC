import java.util.ArrayList;
import java.util.List;

public class Algos {
    public static void main(String[] args) {

        List<Node> connections = new ArrayList<>();

        PathManager paths = new PathManager();

        Node root = new Node();
        root.type = Node.Type.GROUND;

        Node node1 = new Node();
        node1.type = Node.Type.NONE;

        Node node2 = new Node();
        node2.type = Node.Type.NONE;

        Node node3 = new Node();
        node3.type = Node.Type.NONE;

        Node node4 = new Node();
        node4.type = Node.Type.NONE;

        Node nand1_in1 = new Node();
        nand1_in1.type = Node.Type.NONE;
        Node nand1_in2 = new Node();
        nand1_in2.type = Node.Type.NONE;
        Node nand1_out = new Node();
        nand1_out.type = Node.Type.NONE;

        Node nand2_in1 = new Node();
        nand2_in1.type = Node.Type.NONE;
        Node nand2_in2 = new Node();
        nand2_in2.type = Node.Type.NONE;
        Node nand2_out = new Node();
        nand2_out.type = Node.Type.NONE;

        Node nand3_in1 = new Node();
        nand3_in1.type = Node.Type.NONE;
        Node nand3_in2 = new Node();
        nand3_in2.type = Node.Type.NONE;
        Node nand3_out = new Node();
        nand3_out.type = Node.Type.NONE;

        connections.add(root);
        connections.add(node1);
        connections.add(node2);
        connections.add(node3);
        connections.add(node4);

        connections.add(nand1_in1);
        connections.add(nand1_in2);
        connections.add(nand1_out);

        connections.add(nand2_in1);
        connections.add(nand2_in2);
        connections.add(nand2_out);

        connect_nodes(root, node1);
        connect_nodes(root, node2);
        connect_nodes(node1, nand1_in1);
        connect_nodes(node2, nand1_in2);

        connect_nodes(root, node3);
        connect_nodes(root, node4);
        connect_nodes(node3, nand2_in1);
        connect_nodes(node4, nand2_in2);

        connect_nodes(nand1_out, nand3_in1);
        connect_nodes(nand2_out, nand3_in2);

        paths.add(new ConnectedNodePaths(nand1_in1));
        paths.add(new ConnectedNodePaths(nand1_in2));

        paths.add(new ConnectedNodePaths(nand2_in1));
        paths.add(new ConnectedNodePaths(nand2_in2));

        paths.add(new ConnectedNodePaths(nand3_in1));
        paths.add(new ConnectedNodePaths(nand3_in2));

        for (;;) {
            for (int i = 0; i < paths.size(); i++) {
                for (Node n: connections) {
                    n.visited = false;
                }
                ConnectedNodePaths p = paths.get(i);

                find_paths(p.origin, p.origin, p);

                if (p.includes_origin(nand1_in1) || p.includes_origin(nand1_in2)) {
                    if (paths.has_complete_path(nand1_in1) && paths.has_complete_path(nand1_in2)) {
                        if (nand1_in1.type == Node.Type.VOLTAGE && nand1_in2.type == Node.Type.VOLTAGE) {
                            nand1_out.type = Node.Type.VOLTAGE;
                        }

                        if (nand1_in1.type == Node.Type.GROUND && nand1_in2.type == Node.Type.GROUND) {
                            nand1_out.type = Node.Type.GROUND;
                        }
                    }
                }

                if (p.includes_origin(nand2_in1) || p.includes_origin(nand2_in2)) {
                    if (paths.has_complete_path(nand2_in1) && paths.has_complete_path(nand2_in2)) {
                        if (nand2_in1.type == Node.Type.VOLTAGE && nand1_in2.type == Node.Type.VOLTAGE) {
                            nand2_out.type = Node.Type.VOLTAGE;
                        }

                        if (nand2_in1.type == Node.Type.GROUND && nand2_in2.type == Node.Type.GROUND) {
                            nand2_out.type = Node.Type.GROUND;
                        }
                    }
                }

                if (p.includes_origin(nand3_in1) || p.includes_origin(nand3_in2)) {
                    if (paths.has_complete_path(nand3_in1) && paths.has_complete_path(nand3_in2)) {
                        if (nand3_in1.type == Node.Type.VOLTAGE && nand3_in2.type == Node.Type.VOLTAGE) {
                            nand3_out.type = Node.Type.VOLTAGE;
                        }

                        if (nand3_in1.type == Node.Type.GROUND && nand3_in2.type == Node.Type.GROUND) {
                            nand3_out.type = Node.Type.GROUND;
                        }
                    }
                }
            }

            System.out.println(nand1_out.type);
            System.out.println(nand2_out.type);
            System.out.println(nand3_out.type);
        }
    }

    // Find Node that match Voltage node
    private static void find_paths(Node origin, Node current, ConnectedNodePaths paths) {
        List<Node> nodes = current.children;
        for (Node n : nodes) {
            if (n.visited) {
                return;
            }

            if (origin.type == Node.Type.NONE && (n.type == Node.Type.GROUND || n.type == Node.Type.VOLTAGE)) {
                if (n.type == Node.Type.VOLTAGE) {
                    origin.type = Node.Type.GROUND;
                } else if (n.type == Node.Type.GROUND) {
                    origin.type = Node.Type.VOLTAGE;
                }

                paths.add(n);
                return;
            }
            else if ((origin.type == Node.Type.VOLTAGE && n.type == Node.Type.GROUND) || (origin.type == Node.Type.GROUND && n.type == Node.Type.VOLTAGE)) {

                if (n.type == Node.Type.VOLTAGE) {
                    origin.type = Node.Type.GROUND;
                } else if (n.type == Node.Type.GROUND) {
                    origin.type = Node.Type.VOLTAGE;
                }
                paths.add(n);
                return;
            } else {
                n.visited = true;
                find_paths(origin, n, paths);
            }

        }
    }

    private static void connect_nodes(Node first, Node second) {
        first.children.add(second);
        second.children.add(first);
    }
}

