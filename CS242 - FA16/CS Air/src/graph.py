from node import Node


class Graph:
    def __init__(self):
        self.nodes = {}
        self.airport_list = []
        self.cities = []

    # adds a node for all cities
    def initialize_nodes(self, metros):
        for metro in metros:
            self.add_node(metro)

    # adds all possible routes
    def initialize_routes(self, routes):
        self.add_new_routes(routes)

    def initialize_directed_routes(self, routes):
        self.add_new_directed_routes(routes)

    # finds the designated airport
    def find_node(self, code):
        return self.nodes.get(code)

    # add a single node
    def add_node(self, new_metro):
        self.nodes[new_metro['code']] = Node(new_metro)
        self.airport_list.append(new_metro['code'])
        self.cities.append(str(new_metro['name']))

    # add routes to an existing node
    def add_new_routes(self, new_routes):
        for routes in new_routes:
            node = self.nodes.get(routes['ports'][0])
            node.add_route(routes['ports'][1], routes['distance'])
            node = self.nodes.get(routes['ports'][1])
            node.add_route(routes['ports'][0], routes['distance'])

    # add routes to an existing node
    def add_new_directed_routes(self, new_routes):
        for routes in new_routes:
            node = self.nodes.get(routes['ports'][0])
            node.add_route(routes['ports'][1], routes['distance'])

    # add new directed route to an existing node
    def add_directed_route(self, new_route):
        node = self.nodes.get(new_route['ports'][0])
        node.add_route(new_route['ports'][1], new_route['distance'])

    # remove a city and all its outgoing and incoming routes
    def remove_city(self, code):
        node = self.find_node(code)
        node_routes = node.get_routes()
        for route in node_routes:
            self.remove_directed_route(code, route)
        airport_codes = self.get_airport_codes()
        airport_codes.remove(node.code)
        cities = self.get_cities()
        cities.remove(node.name)
        node.destroy_node()

        for i in range(len(airport_codes)):
            node = self.find_node(airport_codes[i])
            routes = node.get_routes()
            for route in routes:
                if route.destination == code:
                    self.remove_directed_route(airport_codes[i], route)

    # remove a directed route
    def remove_directed_route(self, code, route):
        node = self.find_node(code)
        node.remove_route(route.destination)

    # updates the city's information
    def modify_node(self, metro_code, modified_metro):
        node = self.find_node(metro_code)
        node.modify_node(modified_metro)
        airport_codes = self.get_airport_codes()
        index = airport_codes.index(metro_code)
        self.airport_list[index] = node.code
        self.cities[index] = node.name

    # get the set of nodes
    def get_nodes(self):
        return self.nodes

    # Get a list of airport codes
    def get_airport_codes(self):
        return self.airport_list

    # Get a list of cities
    def get_cities(self):
        return self.cities

    # print a city's data
    def get_city_data(self, name):
        code = self.get_code(name)
        if code is None:
            return "CSAir has no flights to/from " + name
        node = self.find_node(code)
        array = ["Code: " + str(node.code), "City: " + str(node.name), "Country: " + str(node.country),
                 "Continent: " + str(node.continent), "Timezone: " + str(node.timezone),
                 "Position: " + str(node.coordinates), "Population: " + str(node.population),
                 "Region: " + str(node.region)]
        routes = node.get_routes()
        dest_array = []
        for i in range(len(routes)):
            dest_array.append(str(routes[i].destination) + " (" + str(routes[i].distance) + " km)")
        if len(routes) == 0:
            dest_array.append("No incoming/outgoing flights.")
        return array + dest_array

    # Convert city/code to code
    # If the city/code doesn't exist, return None
    def get_code(self, user_input):
        try:
            uppercase_input = user_input.upper()
            self.airport_list.index(uppercase_input)
            code = uppercase_input
        except ValueError:
            try:
                title_input = user_input.title()
                index = self.cities.index(title_input)
                code = self.airport_list[index]
            except ValueError:
                code = None
        return code
