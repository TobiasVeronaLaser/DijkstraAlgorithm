import java.util.HashMap;
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
        this.name = name;
    }

    public Map<String, Route> getRoutes() {
        return routes;
    }

    public void setRoutes(Map<String, Route> routes) {
        this.routes = routes;
    }

    public void setRoute(String name, Route route) {
        routes.put(name, route);
    }

    public boolean removeRoute(String name) {
        if (!routes.containsKey(name))
            return false;
        routes.remove(name);
        return true;
    }

    public int getRouteCount() {
        return routes.keySet().size();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + name + "," + getRouteCount() + ")";
    }


}
