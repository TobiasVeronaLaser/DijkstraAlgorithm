import java.util.*;

public class DijkstraAlgorithm {
    public final String
            NAME = "name",
            DISTANCE = "distance",
            PREVIOUS = "previous",
            VISITED = "visited",
            LENGTH = "length",
            VALUE = "value";

    public DijkstraAlgorithm() {
    }

    public Map<String, Map<String, Map<String, Object>>> calculateAllRouteMaps(Map<String, Node> nodes) {
        if (nodes.isEmpty()) {
            return null;
        }
        Map<String, Map<String, Map<String, Object>>> history = new HashMap<String, Map<String, Map<String, Object>>>();
        String[] nodeNameArray = nodes.keySet().toArray(new String[0]);
        for (int i = 0; i < nodeNameArray.length; i++) {
            history.put(nodeNameArray[i], calculateRouteMap(nodeNameArray[i], nodes));
        }
        return history;
    }

    public Map<String, Map<String, Object>> calculateRouteMap(String startNodeName, Map<String, Node> nodes) {
        if (nodes == null)
            return null;
        Map<String, Map<String, Object>> history = new HashMap<String, Map<String, Object>>();
        Map<String, Object> tempHistory = new HashMap<String, Object>();
        tempHistory.put(NAME, startNodeName);
        tempHistory.put(DISTANCE, 0);
        tempHistory.put(PREVIOUS, null);
        tempHistory.put(VISITED, false);
        history.put(startNodeName, tempHistory);
        calculate(nodes.get(startNodeName), history);
        return history;
    }

    private Map<String, Map<String, Object>> calculate(Node currentNode, Map<String, Map<String, Object>> history) {
        String[] nextNodeNameArray = currentNode.getRouteNameArray();
        if (nextNodeNameArray.length <= 0) {
            return null;
        }
        for (int i = 0; i < nextNodeNameArray.length; i++) {
            Map<String, Object> tempHistory;
            if (history.containsKey(nextNodeNameArray[i])) {
                System.out.println(nextNodeNameArray[i]);
                tempHistory = history.get(nextNodeNameArray[i]);
                if ((boolean) tempHistory.get(VISITED)) {
                    continue;
                }
                if ((int) history.get(currentNode.getName()).get(DISTANCE) + currentNode.getRoute(nextNodeNameArray[i]).getValue() < (int) tempHistory.get(DISTANCE)) {
                    tempHistory.put(DISTANCE, (int) history.get(currentNode.getName()).get(DISTANCE) + currentNode.getRoute(nextNodeNameArray[i]).getValue());
                    tempHistory.put(PREVIOUS, currentNode);
                }
            } else {
                tempHistory = new HashMap<String, Object>();
                tempHistory.put(NAME, nextNodeNameArray[i]);
                tempHistory.put(DISTANCE, (int) history.get(currentNode.getName()).get(DISTANCE) + currentNode.getRoute(nextNodeNameArray[i]).getValue());
                tempHistory.put(PREVIOUS, currentNode);
                tempHistory.put(VISITED, false);
            }
            history.put(nextNodeNameArray[i], tempHistory);
        }
        history.get(currentNode.getName()).put(VISITED, true);
        for (int i = 0; i < nextNodeNameArray.length; i++) {
            if ((boolean) history.get(nextNodeNameArray[i]).get(VISITED)) {
                continue;
            } else {
                calculate(currentNode.getRoute(nextNodeNameArray[i]).getNeighbourNode(currentNode), history);
            }
        }
        return history;
    }


    /**
     * @param startNodeName
     * @param endNodeName
     * @param history
     * @return Map<String, Object> Containing key "length", the keys 0 to length-1 for getting the nodes and the total route-value
     */
    public Map<Object, Object> calculateOneRoute(String startNodeName, String endNodeName, Map<String, Map<String, Map<String, Object>>> history) {
        if (history.isEmpty()) {
            return null;
        }
        Map<Object, Object> route = new HashMap<Object, Object>();
        Map<String, Map<String, Object>> tempHistory = history.get(startNodeName);
        /*Node currentNode = history.get(endNodeName).get()
        for (int i = 0; i < ) {

        }*/
        return null;
    }


}
