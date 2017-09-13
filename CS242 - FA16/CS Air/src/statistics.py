

# Delegate the task to the correct method
def statistics_helper(graph):
    while 1:
        data = ""
        request = raw_input("What statistical information are you looking for?\n" +
                            "\tPress 0 for the longest flight.\n" +
                            "\tPress 1 for the shortest flight.\n" +
                            "\tPress 2 for the average flight distance.\n" +
                            "\tPress 3 for the largest city.\n" +
                            "\tPress 4 for the smallest city.\n" +
                            "\tPress 5 for the average city.\n" +
                            "\tPress 6 for a list of continents and destinations in those continents.\n" +
                            "\tPress 7 for hub cities.\n" +
                            "\tPress * to exit.\n")
        if request == '0':
            data = find_longest_flight(graph)
        elif request == '1':
            data = find_shortest_flight(graph)
        elif request == '2':
            data = find_average_flight(graph)
        elif request == '3':
            data = find_largest_city(graph)
        elif request == '4':
            data = find_smallest_city(graph)
        elif request == '5':
            data = find_average_city(graph)
        elif request == '6':
            data = find_continents_and_cities(graph)
        elif request == '7':
            data = find_hubs(graph)
        elif request == '*':
            break
        print data
    return


# Find the longest flight in the network
def find_longest_flight(graph):
    max_distance = 0
    airport_codes = graph.get_airport_codes()
    node_src = graph.find_node(airport_codes[0])
    destination = ""
    for i in range(len(airport_codes)):
        node = graph.find_node(airport_codes[i])
        routes = node.get_routes()
        for j in range(len(routes)):
            if routes[j].distance > max_distance:
                max_distance = routes[j].distance
                node_src = graph.find_node(airport_codes[i])
                destination = routes[j].destination
    node_dest = graph.find_node(destination)
    return "The longest flight is from " + node_src.name + " to " + node_dest.name + \
           " at " + str(max_distance) + " km.\n"


# Find the shortest flight in the network
def find_shortest_flight(graph):
    min_distance = float('inf')
    airport_codes = graph.get_airport_codes()
    node_src = graph.find_node(airport_codes[0])
    destination = ""
    for i in range(len(airport_codes)):
        node = graph.find_node(airport_codes[i])
        routes = node.get_routes()
        for j in range(len(routes)):
            if routes[j].distance < min_distance:
                min_distance = routes[j].distance
                node_src = graph.find_node(airport_codes[i])
                destination = routes[j].destination
    node_dest = graph.find_node(destination)
    return "The shortest flight is from " + node_src.name + " to " + node_dest.name + \
           " at " + str(min_distance) + " km.\n"


# Find the average distance of all flights
def find_average_flight(graph):
    total_distance = 0
    num_flights = 0
    airport_codes = graph.get_airport_codes()
    for i in range(len(airport_codes)):
        node = graph.find_node(airport_codes[i])
        routes = node.get_routes()
        for j in range(len(routes)):
            total_distance += routes[j].distance
            num_flights += 1
    return "The average flight is " + str(total_distance/num_flights) + " km long.\n"


# Find the largest city in the network
def find_largest_city(graph):
    max_population = 0
    airport_codes = graph.get_airport_codes()
    node_largest = graph.find_node(airport_codes[0])
    for i in range(len(airport_codes)):
        node = graph.find_node(airport_codes[i])
        if node.population > max_population:
            max_population = node.population
            node_largest = node
    return node_largest.name + " is the largest city in our network with " + str(max_population) + " people.\n"


# Find the smallest city in the network
def find_smallest_city(graph):
    min_population = float('inf')
    airport_codes = graph.get_airport_codes()
    node_smallest = graph.find_node(airport_codes[0])
    for i in range(len(airport_codes)):
        node = graph.find_node(airport_codes[i])
        if node.population < min_population:
            min_population = node.population
            node_smallest = node
    return node_smallest.name + " is the smallest city in our network with " + str(min_population) + " people.\n"


# Find the average population of all cities in the network
def find_average_city(graph):
    total_population = 0
    num_cities = 0
    airport_codes = graph.get_airport_codes()
    for i in range(len(airport_codes)):
        node = graph.find_node(airport_codes[i])
        total_population += node.population
        num_cities += 1
    return "The average population of all cities in our network is " + str(total_population/num_cities) + " people.\n"


# Find the largest city in the network
def find_continents_and_cities(graph):
    na = []
    sa = []
    africa = []
    europe = []
    asia = []
    australia = []
    airport_codes = graph.get_airport_codes()
    ret_string = "\n"
    for i in range(len(airport_codes)):
        node = graph.find_node(airport_codes[i])
        if node.continent == "North America":
            na.append(str(node.name))
        if node.continent == "South America":
            sa.append(str(node.name))
        if node.continent == "Africa":
            africa.append(str(node.name))
        if node.continent == "Asia":
            asia.append(str(node.name))
        if node.continent == "Australia":
            australia.append(str(node.name))
        if node.continent == "Europe":
            europe.append(str(node.name))
    if len(na) > 0:
        ret_string += "North American Cities: \n\t" + str(na) + "\n"
    if len(sa) > 0:
        ret_string += "South American Cities: \n\t" + str(sa) + "\n"
    if len(africa) > 0:
        ret_string += "African Cities: \n\t" + str(africa) + "\n"
    if len(asia) > 0:
        ret_string += "Asian Cities: \n\t" + str(asia) + "\n"
    if len(australia) > 0:
        ret_string += "Australian Cities: \n\t" + str(australia) + "\n"
    if len(europe) > 0:
        ret_string += "European Cities: \n\t" + str(europe) + "\n"
    return ret_string


# Find the hub cities in the network
def find_hubs(graph):
    max_hub_size = 0
    airport_codes = graph.get_airport_codes()
    for i in range(len(airport_codes)):
        node = graph.find_node(airport_codes[i])
        routes = node.get_routes()
        if len(routes) > max_hub_size:
            max_hub_size = len(routes)
    largest_hubs = []
    for i in range(len(airport_codes)):
        node = graph.find_node(airport_codes[i])
        routes = node.get_routes()
        if len(routes) == max_hub_size:
            largest_hubs.append(str(node.code))
    return "CSAir's Hubs: " + str(largest_hubs) + "\n"
