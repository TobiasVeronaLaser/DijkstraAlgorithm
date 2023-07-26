import copy


def to_str(class_name: str, object_name: str = None, object_list: list = None):
    output = f"{class_name}("
    if object_name is not None:
        output += object_name
    if object_list is None:
        output += ")"
        return output
    elif object_name is not None:
        output += "|"
    output += f"{len(object_list)}):["
    for i in range(len(object_list)):
        output += f"{object_list[i]}"
        if i < len(object_list) - 1:
            output += ", "
    output += "]"
    return output


class Node:
    def __init__(self, name: str = None):
        self.name = name
        self.links = {}

    def size(self):
        return len(self.links)

    def get_link(self, node):
        return self.links[node.name]

    def drop_links(self):
        keys = copy.deepcopy(list(self.links.keys()))
        for key in keys:
            self.links[key].drop()
        return len(keys)

    def get_nodes(self):
        return [self.links[key].get_opposite_node(self) for key in self.links.keys()]

    def __str__(self):
        return to_str(
            class_name=self.__class__.__name__,
            object_name=self.name,
            object_list=list(self.links.keys())
        )


class Link:
    def __init__(self, first_node: Node, second_node: Node, value: int):
        self.__used = False
        self.__first_node: Node = None
        self.__second_node: Node = None
        self.__listed: list = None
        self.__name: str = None
        self.__value = value
        self.set_nodes(first_node, second_node)

    #   Activates link
    def set_nodes(self, first_node: Node, second_node: Node):
        if first_node is second_node:
            raise ValueError("Nodes are not allowed to be the same object")
        self.__first_node = first_node
        self.__second_node = second_node
        self.__listed = [first_node, second_node]
        self.__name = f"({first_node.name}|{second_node.name})"
        first_node.links[second_node.name] = self
        second_node.links[first_node.name] = self
        self.__used = True

    def set_value(self, value: int):
        if self.__used is False:
            raise ValueError("Link is not active")
        self.__value = value

    def get_value(self):
        return self.__value

    def get_opposite_node(self, node: Node):
        return [n for n in self.__listed if n is not node][0]

    #    Deactivates link
    def drop(self):
        self.__first_node.links.pop(self.__second_node.name)
        self.__second_node.links.pop(self.__first_node.name)
        self.__first_node = None
        self.__second_node = None
        self.__value = None
        self.__used = False

    def __str__(self):
        if not self.__used:
            return self.__class__.__name__ + " is unused"
        return to_str(
            class_name=self.__class__.__name__,
            object_name=None,
            object_list=[self.__first_node.name, self.__second_node.name]
        )


class RouteElement:
    def __init__(self, current_node: Node = None, previous_node: Node = None, value: int = None):
        self.current_node: Node = current_node
        self.previous_node: Node = previous_node
        self.value: int = value

    def __str__(self):
        return to_str(
            class_name=self.__class__.__name__,
            object_name=self.current_node.name if self.current_node is not None else None,
            object_list=[self.previous_node, self.value]
        )


class Djikstra:
    def __init__(self, name: str = None):
        self.name = name
        self.__nodes = {}
        self.route = {}
        self.__link_amount = 0

    def add(self, node: Node, calc_routes: bool = False):
        self.__nodes[node.name] = node
        if calc_routes:
            self.calculate_routes()

    def delete(self, node: Node):
        self.__link_amount -= self.__nodes[node.name].drop_links()

    def link(self, first_node: Node, second_node: Node, value: int):
        Link(first_node, second_node, value)
        self.__link_amount += 1

    def drop_link(self, first_node: Node, second_node: Node):
        self.__nodes[first_node.name].get_link(second_node).drop()
        self.__link_amount -= 1

    def link_size(self):
        return self.__link_amount

    # @DeprecationWarning
    def calculate_route(self, start_node: Node):
        self.route[start_node.name] = {key: RouteElement() for key in self.__nodes.keys()}
        visited_nodes = {self.__nodes[key].name: 0 for key in self.__nodes.keys() if len(self.__nodes[key].links) == 0}
        next_nodes: dict = {start_node.name: [start_node]}
        step = 0
        while not sorted([vn for vn in visited_nodes]).__eq__(
                sorted([self.__nodes[key].name for key in self.__nodes.keys()])):
            temp_next_nodes: dict = copy.deepcopy(next_nodes)
            next_nodes = {}
            for previous_node_key in temp_next_nodes.keys():  # A
                for current_node in temp_next_nodes[previous_node_key]:
                    next_nodes[current_node.name] = self.__calculate(start_node, self.__nodes[previous_node_key],
                                                                     current_node, visited_nodes)
                    visited_nodes[current_node.name] = 0
                    print(next_nodes)
            step += 1

    #   Problem: visited_nodes won't change, when a current_node might say it has a lower value
    def __calculate(self, start_node: Node, previous_node: Node, current_node: Node, visited_nodes: list):
        route_element: RouteElement = self.route[start_node.name][current_node.name]
        link: Link = None
        if start_node.name == current_node.name:
            route_element: RouteElement = self.route[start_node.name][start_node.name]
            route_element.previous_node = start_node
            route_element.current_node = start_node
            route_element.value = 0
        elif route_element.current_node is None:
            route_element.current_node = current_node
            route_element.previous_node = previous_node
            link = current_node.get_link(route_element.previous_node)
            route_element.value = self.route[start_node.name][previous_node.name].value + link.get_value()
        else:
            link = current_node.get_link(previous_node)
            if self.route[start_node.name][previous_node.name].value + link.get_value() < route_element.value:
                route_element.previous_node = previous_node
                link = current_node.get_link(route_element.previous_node)
                route_element.value = self.route[start_node.name][previous_node.name].value + link.get_value()
        return [self.__nodes[key] for key in current_node.links.keys() if
                self.__nodes[key].name not in [vn for vn in visited_nodes]]

    @DeprecationWarning
    def get_best_route(self, start_node, end_node, auto_update: False):
        pass

    def __str__(self):
        return to_str(
            class_name=self.__class__.__name__,
            object_name=self.name,
            object_list=list(self.__nodes.keys())
        )


def add_to_djikstra(algorithm: Djikstra, *nodes):
    for node in nodes:
        algorithm.add(node)
    return algorithm


if __name__ == "__main__":
    #   Algorithm
    _oesterreich_djikstra = Djikstra("oesterreich")
    #   Nodes
    bregenz = Node("bregenz")
    bludenz = Node("bludenz")
    landeck = Node("landeck")
    ibk = Node("ibk")
    kitzbuehel = Node("kitzbuehel")
    lienz = Node("lienz")
    zell_am_see = Node("zell_am_see")
    salzburg = Node("salzburg")
    ried = Node("ried")
    wels = Node("wels")
    tamsweg = Node("tamsweg")
    villach = Node("villach")
    linz = Node("linz")
    liezen = Node("liezen")
    klagenfurt = Node("klagenfurt")
    wolfsberg = Node("wolfsberg")
    horn = Node("horn")
    st_poelten = Node("st_poelten")
    kapfenberg = Node("kapfenberg")
    graz = Node("graz")
    wien = Node("wien")
    eisenstadt = Node("eisenstadt")
    fuerstenfeld = Node("fuerstenfeld")
    #   Links
    Link(bregenz, ibk, 127)
    Link(bregenz, bludenz, 39)
    Link(bludenz, landeck, 56)
    Link(landeck, ibk, 62)
    Link(ibk, kitzbuehel, 80)
    Link(ibk, lienz, 100)
    Link(kitzbuehel, salzburg, 70)
    Link(kitzbuehel, zell_am_see, 35)
    Link(kitzbuehel, lienz, 44)
    Link(salzburg, ried, 56)
    Link(salzburg, zell_am_see, 56)
    Link(salzburg, wels, 84)
    Link(salzburg, liezen, 102)
    Link(ried, wels, 41)
    Link(zell_am_see, lienz, 29)
    Link(zell_am_see, tamsweg, 71)
    Link(tamsweg, villach, 60)
    Link(tamsweg, liezen, 65)
    Link(klagenfurt, villach, 35)
    Link(klagenfurt, wolfsberg, 47)
    Link(liezen, linz, 86)
    Link(liezen, kapfenberg, 72)
    Link(liezen, wolfsberg, 86)
    Link(liezen, graz, 98)
    Link(linz, wels, 24)
    Link(linz, kapfenberg, 122)
    Link(linz, st_poelten, 100)
    Link(linz, horn, 113)
    Link(graz, wolfsberg, 52)
    Link(graz, kapfenberg, 43)
    Link(graz, fuerstenfeld, 48)
    Link(wolfsberg, fuerstenfeld, 97)
    Link(kapfenberg, st_poelten, 88)
    Link(kapfenberg, wien, 118)
    Link(kapfenberg, eisenstadt, 103)
    Link(kapfenberg, fuerstenfeld, 74)
    Link(horn, st_poelten, 57)
    Link(horn, wien, 76)
    Link(wien, st_poelten, 55)
    Link(wien, eisenstadt, 42)
    Link(eisenstadt, fuerstenfeld, 95)
    Link(lienz, villach, 102)
    # adding nodes to oesterreich-algorithm
    add_to_djikstra(_oesterreich_djikstra,
                    bregenz, bludenz, landeck, ibk, kitzbuehel, lienz, zell_am_see, salzburg, ried, wels, tamsweg,
                    villach, linz, liezen, klagenfurt, wolfsberg, horn, st_poelten, kapfenberg, graz, wien, eisenstadt,
                    fuerstenfeld)
    _oesterreich_djikstra.calculate_route(ibk)
    print(_oesterreich_djikstra.route[ibk.name][st_poelten.name].value)
    print()
