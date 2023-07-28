import java.util.*;

public class DijkstraAlgorithm {
    public final String
            CURRENT = "current",
            DISTANCE = "distance",
            PREVIOUS = "previous",
            VISITED = "visited",
            LENGTH = "length",
            VALUE = "value";

    public DijkstraAlgorithm() {
    }

    public Map<Node, Map<Node, Map<String, Object>>> calculateAllRouteMaps(List<Node> nodeList) {
        if (nodeList.isEmpty()) {
            return null;
        }
        Map<Node, Map<Node, Map<String, Object>>> history = new HashMap<Node, Map<Node, Map<String, Object>>>();
        for (int i = 0; i < nodeList.size(); i++) {
            history.put(nodeList.get(i), calculateRouteMap(nodeList.get(i), nodeList));
        }
        return history;
    }

    public Map<Node, Map<String, Object>> calculateRouteMap(Node node, List<Node> nodes) {
        if (node == null || nodes == null)
            return null;
        Map<Node, Map<String, Object>> history = new HashMap<Node, Map<String, Object>>();
        Map<String, Object> tempHistory = new HashMap<String, Object>();
        tempHistory.put(CURRENT, node);
        tempHistory.put(DISTANCE, 0);
        tempHistory.put(PREVIOUS, null);
        tempHistory.put(VISITED, false);
        history.put(node, tempHistory);
        return calculate(node, history);
    }

    private Map<Node, Map<String, Object>> calculate(Node currentNode, Map<Node, Map<String, Object>> history) {
        Node[] neighbourNodeArray = currentNode.getRouteNeighbourNodeArray();
        //String[] nextNodeNameArray = currentNode.getRouteNameArray();
        if (neighbourNodeArray.length <= 0) {
            return null;
        }
        for (int i = 0; i < neighbourNodeArray.length; i++) {
            Map<String, Object> tempHistory;
            if (history.containsKey(neighbourNodeArray[i])) {
                System.out.println(neighbourNodeArray[i]);
                tempHistory = history.get(neighbourNodeArray[i]);
                if ((boolean) tempHistory.get(VISITED)) {
                    continue;
                }
                if ((int) history.get(currentNode).get(DISTANCE) + currentNode.getRoute(neighbourNodeArray[i].getName()).getValue() < (int) tempHistory.get(DISTANCE)) {
                    tempHistory.put(DISTANCE, (int) history.get(currentNode).get(DISTANCE) + currentNode.getRoute(neighbourNodeArray[i].getName()).getValue());
                    tempHistory.put(PREVIOUS, currentNode);
                }
            } else {
                tempHistory = new HashMap<String, Object>();
                tempHistory.put(CURRENT, neighbourNodeArray[i]);
                tempHistory.put(DISTANCE, (int) history.get(currentNode).get(DISTANCE) + currentNode.getRoute(neighbourNodeArray[i].getName()).getValue());
                tempHistory.put(PREVIOUS, currentNode);
                tempHistory.put(VISITED, false);
            }
            history.put(neighbourNodeArray[i], tempHistory);
        }
        history.get(currentNode).put(VISITED, true);
        for (int i = 0; i < neighbourNodeArray.length; i++) {
            if ((boolean) history.get(neighbourNodeArray[i]).get(VISITED)) {
                continue;
            } else {
                calculate(currentNode.getRoute(neighbourNodeArray[i].getName()).getNeighbourNode(currentNode), history);
            }
        }
        return history;
    }


    /**
     * @param startNode
     * @param endNode
     * @param history
     * @return Map<String, Object> Containing key "length", the keys 0 to length-1 for getting the nodes and the total route-value
     */
    public Map<Object, Object> calculateOneRoute(Node startNode, Node endNode, Map<Node, Map<Node, Map<String, Object>>> history) {
        if (history.isEmpty()) {
            return null;
        }

        Map<Object, Object> route = new HashMap<Object, Object>();
        Map<Node, Map<String, Object>> tempHistory = history.get(startNode);
        Node currentNode = (Node) tempHistory.get(endNode).get(CURRENT);
        route.put(LENGTH, 0);
        for (int i = 0; ; i++) {
            route.put(i, currentNode);
            route.put(LENGTH, 1 + (int) route.get(LENGTH));
            if (currentNode == tempHistory.get(startNode).get(CURRENT)) {
                break;
            }
            currentNode = (Node) tempHistory.get(currentNode).get(PREVIOUS);
        }
        route.put(VALUE, tempHistory.get(endNode).get(DISTANCE));
        return route;
    }


}
