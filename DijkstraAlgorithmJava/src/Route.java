public class Route {
    private Node node0;
    private Node node1;
    private Integer value;

    public Route() throws Exception {
        this(null, null, null);
    }

    public Route(Node node0, Node node1, Integer value) throws Exception {
        route(node0, node1, value);
    }

    public void route(Node node0, Node node1, int value) throws Exception {
        route(node0, node1, value, false);
    }

    public void route(Node node0, Node node1, int value, boolean clear) throws Exception {
        if (clear)
            clear();
        if (node0.getRoutes().containsKey(node1.getName()))
            throw new Exception("Nodes are already connected");
        if (node1.getRoutes().containsKey(node1.getName()))
            throw new Exception("Nodes are already connected");
        this.node0 = node0;
        this.node1 = node1;
        this.value = value;
        node0.setRoute(node1.getName(), this);
        node1.setRoute(node0.getName(), this);
    }


    public void clear() {
        if (node0.getRoutes().containsKey(node1.getName()))
            node0.removeRoute(node1.getName());
        if (node1.getRoutes().containsKey(node0.getName()))
            node1.removeRoute(node0.getName());
        node0 = null;
        node1 = null;
        value = null;
    }

    public Node getNode0() {
        return node0;
    }

    public void setNode0(Node node0) {
        if (this.node0 != node0)
            this.node0 = node0;
    }

    public Node getNode1() {
        return node1;
    }

    public void setNode1(Node node1) {
        if (this.node1 != node1)
            this.node1 = node1;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (this.value != value)
            this.value = value;
    }

    public Node getNeighbourNode(Node node) {
        if (node == node0)
            return node1;
        if (node == node1)
            return node0;
        return null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                node0.getName() + "," +
                node1.getName() + "," +
                value + ")";
    }
}
