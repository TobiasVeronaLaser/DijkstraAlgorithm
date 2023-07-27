import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {
    private String name;
    private Map<String, Route> routes;

    public Node() {
        this("");
    }

    public Node(String name) {
        this(name, new HashMap<String, Route>());
    }

    public Node(String name, Map<String, Route> routes) {
        setName(name);
        setRoutes(routes);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (this.name != name)
            this.name = name;
    }

    public Map<String, Route> getRoutes() {
        return routes;
    }

    public List<String> getRouteNameList() {
        return routes.keySet().stream().toList();
    }

    public String[] getRouteNameArray() {
        return routes.keySet().toArray(new String[0]);
    }

    public void clear() {
        String[] routeNames = getRouteNameArray();
        for (int i = 0; i < routeNames.length; i++) {
            routes.get(routeNames[i]).clear();
        }
        name = null;
    }

    public void setRoutes(Map<String, Route> routes) {
        if (this.routes != routes)
            this.routes = routes;
    }

    public Route getRoute(String name) {
        return routes.get(name);
    }

    public void setRoute(String name, Route route) {
        if (!routes.containsKey(name))
            routes.put(name, route);
    }

    public Route removeRoute(String name) {
        if (!routes.containsKey(name))
            return null;
        return routes.remove(name);
    }

    public int getRouteCount() {
        return routes.keySet().size();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + name + "," + getRouteCount() + ")";
    }


}
