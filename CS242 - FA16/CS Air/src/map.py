class Map:
    def __init__(self):
        return

    # Generate a route map URL
    @staticmethod
    def generate_route_map(graph):
        base_url = "http://www.gcmap.com/mapui?P="
        airport_codes = graph.get_airport_codes()
        for i in range(len(airport_codes)):
            node = graph.find_node(airport_codes[i])
            routes = node.get_routes()
            for j in range(len(routes)):
                base_url += str(node.code) + "-" + str(routes[j].destination + ",")
        return base_url.rstrip(',')
