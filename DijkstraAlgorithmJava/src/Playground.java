import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Playground {
    public static void main(String[] args) throws Exception {
        testNetwork0();
    }

    private static void testNetwork0() throws Exception {
        Node node0 = new Node("node0");
        Node node1 = new Node("node1");
        Node node2 = new Node("node2");
        Node node3 = new Node("node3");
        List<Route> routes = new ArrayList<>();
        routes.add(new Route(node0, node1, 1));
        routes.add(new Route(node0, node2, 2));
        routes.add(new Route(node1, node2, 1));
        routes.add(new Route(node1, node3, 10));
        routes.add(new Route(node2, node3, 6));
        Map<String, Node> nodes = new HashMap<String, Node>();
        nodes.put(node0.getName(), node0);
        nodes.put(node1.getName(), node1);
        nodes.put(node2.getName(), node2);
        nodes.put(node3.getName(), node3);
        DijkstraAlgorithm da = new DijkstraAlgorithm(nodes);
        Map<String, Map<String, Object>> history = da.calculateBestRoutes("node0");
        System.out.println("Test0");
        da.calculateAllBestRoutes();
        Map<String, Map<String, Map<String, Object>>> wholeHistory = da.getWholeHistory();
        System.out.println("Test0");


    }
}
