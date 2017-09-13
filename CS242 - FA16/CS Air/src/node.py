from route import Route


# Data Structure
class Node:
    # Creates a new node
    # @param metro - a city and all its data
    def __init__(self, metro):
        self.code = metro['code']
        self.name = metro['name']
        self.country = metro['country']
        self.continent = metro['continent']
        self.timezone = metro['timezone']
        self.coordinates = metro['coordinates']
        self.population = metro['population']
        self.region = metro['region']
        self.routes = []

    # Add a link representing a route from self to destination
    # @param code - destination identification
    def add_route(self, code, distance):
        self.routes.append(Route(code, distance))

    def get_routes(self):
        return self.routes

    # find all possible destinations
    def get_destinations(self):
        destinations = []
        for routes in self.routes:
            destinations.append(routes.destination)
        return destinations

    def modify_node(self, metro):
        self.code = metro['code']
        self.name = metro['name']
        self.country = metro['country']
        self.continent = metro['continent']
        self.timezone = metro['timezone']
        self.coordinates = metro['coordinates']
        self.population = metro['population']
        self.region = metro['region']

    def remove_route(self, destination):
        my_routes = self.get_routes()
        for route in my_routes:
            if route.destination == destination:
                my_routes.remove(route)

    def destroy_node(self):
        self.code = None
        self.name = None
        self.country = None
        self.continent = None
        self.timezone = None
        self.coordinates = None
        self.population = None
        self.region = None
        self.routes = None
