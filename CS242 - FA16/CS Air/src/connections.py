from __future__ import division


# find connecting routes
def find_connecting_routes(graph):
    city_array = []
    distance_array = []
    index = 0
    while 1:
        start_city = raw_input("Enter the start city for your trip.\n" +
                               "\tPress * to exit.\n")
        if start_city == '*':
            return "Your search for a connecting flight has been cancelled.\n"
        start_city_code = graph.get_code(start_city)
        if start_city_code is None:
            print start_city + " is not part of our network. Try again.\n"
        else:
            city_array.append(start_city_code)
            index += 1
            break
    while 1:
        valid_destination = False
        last_node = graph.find_node(city_array[index - 1])
        possible_destinations = last_node.get_destinations()
        print "Here is a list of all possible destinations: \n" + str(possible_destinations)
        next_city = raw_input("Enter the next city you would like to connect to.\n" +
                              "\tPress * to exit.\n")
        if next_city == '*':
            return "Your search for a connecting flight has been cancelled.\n"
        next_city_code = graph.get_code(next_city)
        if next_city_code is None:
            print next_city + " is not part of our network. Try again.\n"
        else:
            for i in range(len(possible_destinations)):
                if next_city_code == possible_destinations[i]:
                    valid_destination = True
                    break
            if valid_destination:
                city_array.append(next_city_code)
                routes = last_node.get_routes()
                for route in routes:
                    if next_city_code == route.destination:
                        distance_array.append(route.distance)
                        break
                index += 1
                final_city = raw_input("Is this your final stop?\n" +
                                       "\tPress 0 if this is your final stop.\n" +
                                       "\tPress 1 to add another city.\n" +
                                       "\tPress * to exit.\n")
                if final_city == '0':
                    break
                elif final_city == '*':
                    return "Your search for a connecting flight has been cancelled.\n"
            else:
                print next_city + " is not a valid destination from " + last_node.name + "\n"

    return find_cost_time(graph, distance_array, city_array, index)


# calculate flight/route cost and time
def find_cost_time(graph, distance_array, city_array, index):
    cost = 0.0
    time = float(0.0)  # in minutes
    for i in range(len(distance_array)):
        # calculate cost
        if i < 7:
            cost += distance_array[i] * (0.35 - i * .05)
        else:
            cost += 0.0

        # calculate time flying
        if distance_array[i] <= 400:
            # acceleration/deceleration time is equal
            time += (distance_array[i] / 375) * 60
        else:
            # first/last 200km is acceleration/deceleration
            # distance (km) * average velocity (h/km) * 60 minutes (m/h) * 2 for accel/decel
            time += (200 / 375) * 60 * 2
            remaining_dist = distance_array[i] - 400
            time += (remaining_dist / 750) * 60
        if i != 0 and i < len(city_array):
            # calculate layover time
            node = graph.find_node(city_array[i])
            routes = node.get_routes()
            num_routes = len(routes)
            time += 130 - (num_routes * 10)
    return ("Your flight from " + city_array[0] + " to " + city_array[index - 1] +
            " will cost $" + str(cost) + " and the total travel time will be " + str(time) + " minutes.\n")


# find shortest connecting flight
def find_shortest_route(graph):
    start_city_code = ""
    destination_city_code = ""
    while 1:
        start_city = raw_input("Enter the start city for your trip.\n" +
                               "\tPress * to exit.\n")
        if start_city == '*':
            return "Your search for a connecting flight has been cancelled.\n"
        start_city_code = graph.get_code(start_city)
        if start_city_code is None:
            print start_city + " is not part of our network. Try again.\n"
        else:
            break
    while 1:
        destination_city = raw_input("Enter the destination city for your trip.\n" +
                                     "\tPress * to exit.\n")
        if destination_city == '*':
            return "Your search for a connecting flight has been cancelled.\n"
        destination_city_code = graph.get_code(destination_city)
        if destination_city_code is None:
            print destination_city + " is not part of our network. Try again.\n"
        else:
            break

    return dijkstra(graph, start_city_code, destination_city_code)


# find the shortest path between the start and end nodes
def dijkstra(graph, origin, destination):
    airport_codes = graph.get_airport_codes()
    distance_to = []
    path_to = []
    for i in range(len(airport_codes)):
        distance_to.append(float('inf'))
        path_to.append(origin)
    distance_to[airport_codes.index(origin)] = 0

    for i in range(len(airport_codes)):
        node = graph.find_node(airport_codes[i])
        routes = node.get_routes()
        for route in routes:
            index = airport_codes.index(route.destination)
            curr_index = airport_codes.index(node.code)
            if distance_to[index] > (route.distance + distance_to[curr_index]):
                distance_to[index] = route.distance + distance_to[curr_index]
                path_to[index] = str(path_to[curr_index]) + "->" + str(airport_codes[index])

    final_index = airport_codes.index(destination)
    city_array = parse_path_array(path_to[final_index])
    distance_array = generate_distance_array(graph, city_array)
    cost_time = find_cost_time(graph, distance_array, city_array, len(city_array))
    return (cost_time + "The total travel distance will be " + str(distance_to[final_index]) +
            " km and follows the route " + str(path_to[final_index]) + ".\n")


# covert the string path (SCL->LIM->MEX) to a list [SCL, LIM, MEX]
def parse_path_array(string):
    city_array = []
    start = -5
    length = len(string)
    while length >= 0:
        start += 5
        end = start+3
        city_array.append(string[start:end])
        length -= 5
    return city_array


# Create a list of distances between each city in the route
def generate_distance_array(graph, city_array):
    distance_array = []
    for i in range(len(city_array) - 1):
        node = graph.find_node(city_array[i])
        routes = node.get_routes()
        for route in routes:
            if route.destination == city_array[i+1]:
                distance_array.append(route.distance)
    return distance_array
