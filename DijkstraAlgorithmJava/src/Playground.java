import java.util.ArrayList;
import java.util.List;

public class Playground {
    public static void main(String[] args) throws Exception{
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
    }
}
