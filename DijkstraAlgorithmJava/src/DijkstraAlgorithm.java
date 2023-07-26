import java.util.*;

public class DijkstraAlgorithm {
    public final String NAME = "name", DISTANCE = "distance", PREVIOUS = "previous", VISITED = "visited";
    private Map<String, Node> nodes;
    private Map<String, Map<String, Map<String, Object>>> wholeHistory = new HashMap<String, Map<String, Map<String, Object>>>();

    public DijkstraAlgorithm() {
        this(null);
    }

    public DijkstraAlgorithm(Map<String, Node> nodes) {
        this.nodes = nodes;
    }

    public void calculateAllBestRoutes(){
        String[] nodeNames = nodes.keySet().toArray(new String[0]);
        for(int i = 0; i < nodeNames.length; i++){
            calculateBestRoutes(nodeNames[i]);
        }
    }

    public Map<String, Map<String, Object>> calculateBestRoutes(String startNodeName) {
        if (nodes == null)
            return null;
        Map<String, Map<String, Object>> wholeNodeHistory = new HashMap<String, Map<String, Object>>();
        Map<String, Object> nodeHistory = new HashMap<String, Object>();
        nodeHistory.put(NAME, startNodeName);
        nodeHistory.put(DISTANCE, 0);
        nodeHistory.put(PREVIOUS, null);
        nodeHistory.put(VISITED, false);
        wholeNodeHistory.put(startNodeName, nodeHistory);
        calculate(nodes.get(startNodeName), wholeNodeHistory);
        wholeHistory.put(startNodeName, wholeNodeHistory);
        return wholeNodeHistory;
    }

    public Map<String, Object> calculateBestRoute(String startNodeName, String endNodeName) {
        Map<String, Object> m = new HashMap<String, Object>();
        return null;
    }

    private void calculate(Node currentNode, Map<String, Map<String, Object>> wholeNodeHistory) {
        Map<String, Route> neighbours = currentNode.getRoutes();
        String[] names = neighbours.keySet().toArray(new String[0]);
        if (names.length <= 0)
            return;
        System.out.println(currentNode.getName());
        int currentDistance = (int) wholeNodeHistory.get(currentNode.getName()).get(DISTANCE);
        for (int i = 0; i < names.length; i++) {
            Route route = neighbours.get(names[i]);
            Node node = route.getNeighbourNode(currentNode);
            Map<String, Object> nodeHistory;
            if (wholeNodeHistory.containsKey(node.getName())) {
                nodeHistory = wholeNodeHistory.get(node.getName());
                if ((boolean) nodeHistory.get(VISITED))
                    continue;
                if (currentDistance + route.getValue() < (int) nodeHistory.get(DISTANCE)) {
                    nodeHistory.put(DISTANCE, currentDistance + route.getValue());
                    nodeHistory.put(PREVIOUS, currentNode);
                }
            } else {
                nodeHistory = new HashMap<String, Object>();
                nodeHistory.put(NAME, node.getName());
                nodeHistory.put(DISTANCE, currentDistance + route.getValue());
                nodeHistory.put(PREVIOUS, currentNode);
                nodeHistory.put(VISITED, false);
            }
            wholeNodeHistory.put(node.getName(), nodeHistory);
        }

        wholeNodeHistory.get(currentNode.getName()).put(VISITED, true);
        for (int i = 0; i < names.length; i++) {
            Route route = neighbours.get(names[i]);
            Node node = route.getNeighbourNode(currentNode);
            if ((boolean) wholeNodeHistory.get(node.getName()).get(VISITED))
                continue;
            else
                calculate(node, wholeNodeHistory);
        }
    }

    public Map<String, Map<String, Map<String, Object>>> getWholeHistory() {
        return wholeHistory;
    }
}
