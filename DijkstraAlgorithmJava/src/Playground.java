import java.util.*;

public class Playground {
    public static void main(String[] args) throws Exception {
        testNetwork0();
    }

    private static void testNetwork0() throws Exception {
        Node node0 = new Node("node0");
        Node node1 = new Node("node1");
        Node node2 = new Node("node2");
        Node node3 = new Node("node3");
        List<Node> nodeList = new ArrayList<Node>();
        nodeList.add(node0);
        nodeList.add(node1);
        nodeList.add(node2);
        nodeList.add(node3);
        List<Route> routes = new ArrayList<>();
        routes.add(new Route(node0, node1, 1));
        routes.add(new Route(node0, node2, 2));
        routes.add(new Route(node1, node2, 1));
        routes.add(new Route(node1, node3, 10));
        routes.add(new Route(node2, node3, 6));
        DijkstraAlgorithm da = new DijkstraAlgorithm();
        Map<Node, Map<Node, Map<String, Object>>> history = da.calculateAllRouteMaps(nodeList);
        System.out.println("Test0");
        Map<Object, Object> route = da.calculateOneRoute(node0, node3, history);
        System.out.println("Test0");
    }
}
