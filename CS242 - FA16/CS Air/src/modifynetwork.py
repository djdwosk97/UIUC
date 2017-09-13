

# Delegate the task to the correct method
# data will return the final node and it's information
def modify_network(graph):
    while 1:
        data = ""
        request = raw_input("What modification would you like to make?\n" +
                            "\tPress 0 to remove a city.\n" +
                            "\tPress 1 to remove a route.\n" +
                            "\tPress 2 to add a city.\n" +
                            "\tPress 3 to add a route.\n" +
                            "\tPress 4 to modify an existing route.\n"
                            "\tPress * to exit.\n")
        if request == '0':
            data = remove_city(graph)
        elif request == '1':
            data = remove_route(graph)
        elif request == '2':
            data = add_city(graph)
        elif request == '3':
            data = add_route(graph)
        elif request == '4':
            data = edit_city(graph)
        elif request == '*':
            break
        print data
    return


# Remove a city from the network
def remove_city(graph):
    removed_cities = []
    while 1:
        user_input = raw_input("What city would you like to remove?\n" +
                               "\tPress * to exit.\n")
        if user_input == '*':
            break
        code = graph.get_code(user_input)
        if code is not None:
            removed_cities.append(code)
            graph.remove_city(code)
            print user_input + " has been successfully removed.\n"
        else:
            print user_input + " is not a valid city in CSAir's network.\n"
    if len(removed_cities) > 0:
        return removed_cities
    return "No cities were removed.\n"


# Remove a route from the network
def remove_route(graph):
    removed_routes = []
    while 1:
        source = raw_input("From what city would you like to remove routes?\n" +
                           "\tPress * to exit.\n")
        if source == '*':
            break
        origin_city = graph.get_code(source)
        if origin_city is None:
            print source + "does not exist in CSAir's network.\n"
        else:
            node = graph.find_node(origin_city)
            possible_routes = node.get_routes()
            for route in possible_routes:
                print route.destination
                user_input = raw_input("\tPress 0 to remove this route.\n" +
                                       "\tPress 1 to continue to the next route and save changes.\n" +
                                       "\tPress * to exit and save changes.\n")
                if user_input == '0':
                    destination = route.destination
                    removed_routes.append(source + "-" + str(route.destination))
                    graph.remove_directed_route(origin_city, route)
                    print "The route from " + source + " to " + destination + " has been removed.\n"
                elif user_input == '*':
                    break
    if len(removed_routes) > 0:
        return "The following routes have been removed: " + str(removed_routes) + "\n"
    return "No routes have been removed.\n"


# Add a city to the network
def add_city(graph):
    code = raw_input("Enter the airport code.\n")
    city_code = graph.get_code(code)
    temp = graph.find_node(city_code)
    if temp is not None:
        user_input = raw_input("That city already exists in the network.\n" +
                               "\tPress 0 to modify the city.\n" +
                               "\tPress * to cancel and exit.\n")
        if user_input == '*':
            return "Cancelled adding a node.\n"
        else:
            return edit_city(graph)
    name = raw_input("Enter the city name.\n")
    country = raw_input("Enter the country.\n")
    continent = raw_input("Enter the continent.\n")
    timezone = raw_input("Enter the timezone is GMT format (ex: -5).\n")
    latitude_region = raw_input("Enter the latitude region (ex: N or S).\n")
    latitude = raw_input("Enter the latitude of the city.\n")
    longitude_region = raw_input("Enter the longitude region (ex: E or W).\n")
    longitude = raw_input("Enter the longitude of the City.\n")
    population = raw_input("Enter the city's population.\n")
    region = raw_input("Enter the region.\n")

    coordinates = "{\"" + latitude_region + "\": " + latitude + ", \"" + longitude_region + "\": " + longitude + "}"
    new_metro = {"code": code, "name": name, "country": country, "continent": continent,
                 "timezone": timezone, "coordinates": coordinates, "population": population,
                 "region": region}
    graph.add_node(new_metro)
    print new_metro['name'] + " has been added to the network.\n"

    while 1:
        destination_city = raw_input("Enter a city to add a route to.\n" +
                                     "Press * to exit.\n")
        if destination_city == '*':
            break
        destination_code = graph.get_code(destination_city)
        if destination_code == "":
            print "No such city exists in our network.\n"
        else:
            distance = raw_input("How many miles is the flight?\n")
            new_route = {"ports": [code, destination_code], "distance": distance}
            both_directions = raw_input("Would you like to also add a route from " + destination_city + " to " +
                                        name + "?\n" +
                                        "\tPress 0 to add a route for both directions.\n" +
                                        "\tPress 1 to add just the single route.\n" +
                                        "\tPress * to exit.\n")
            if both_directions == '*':
                break
            elif both_directions == '0':
                other_route = {"ports": [destination_code, code], "distance": distance}
                graph.add_directed_route(other_route)
                graph.add_directed_route(new_route)
                print "A bi-directional route has been added between " + code + " and " + destination_code + "\n"
            elif both_directions == '1':
                graph.add_directed_route(new_route)
                print "A single route has been added from " + code + " to " + destination_code + "\n"
    node = graph.find_node(code)
    if node is None:
        return "Failed to add node.\n"
    return graph.get_city_data(node.name)


# Add a route to the network
def add_route(graph):
    routes = []
    destination = ""
    while 1:
        source = raw_input("Enter the city from which you would like to add a route.\n" +
                           "\tPress * to exit.\n")
        if source == '*':
            break
        source_code = graph.get_code(source)
        destination_code = ""
        if source_code is None:
            print source + " is not in our network. Try again\n"
        else:
            while 1:
                destination = raw_input("Enter the city to which you would like to add a route.\n")
                destination_code = graph.get_code(destination)
                if destination_code is None:
                    print destination + " is not in our network. Try again\n"
                break

        # source and destination are valid cities
        distance = raw_input("How many miles is the flight?\n")
        new_route = {"ports": [source_code, destination_code], "distance": distance}
        both_directions = raw_input("Would you like to also add a route from " + destination + " to " + source + "?\n" +
                                    "\tPress 0 to add a route for both directions.\n" +
                                    "\tPress 1 to add just the single route.\n" +
                                    "\tPress * to exit.\n")
        if both_directions == '*':
            break
        elif both_directions == '0':
            other_route = {"ports": [destination_code, source_code], "distance": distance}
            graph.add_directed_route(other_route)
            graph.add_directed_route(new_route)
            routes.append(other_route)
            print "A bi-directional route has been added between " + source_code + " and " + destination_code + "\n"
        elif both_directions == '1':
            graph.add_directed_route(new_route)
            print "A single route has been added from " + source_code + " to " + destination_code + "\n"
        routes.append(new_route)

    added_routes = ""
    for i in range(len(routes)):
        added_routes += ("A route has been added from " + routes[i]['ports'][0] +
                         " to " + routes[i]['ports'][1] + " at " + routes[i]['distance'] + " miles.\n")
    return added_routes


# Modify a city in the network
def edit_city(graph):
    modified_cities = []
    while 1:
        user_input = raw_input("What city would you like to update?\n" +
                               "Press * to exit.\n")
        if user_input == '*':
            break
        code = graph.get_code(user_input)
        node = graph.find_node(code)
        if code is None:
            print "That city is not in the network, try again.\n"
        else:
            print ("Enter the new information.\n" +
                   "\tPress # to leave any field unchanged.\n" +
                   "\tPress * at any time to cancel any changes and exit after the final entry.\n" +
                   "\tFor coordinates you must either change everything or change nothing.\n")
            name = raw_input("Enter the city name.\n")
            if name == '#':
                name = node.name
            elif name == '*':
                break
            country = raw_input("Enter the country.\n")
            if country == '#':
                country = node.country
            elif country == '*':
                break
            continent = raw_input("Enter the continent.\n")
            if continent == '#':
                continent = node.continent
            elif continent == '*':
                break
            timezone = raw_input("Enter the timezone is GMT format (ex: -5).\n")
            if timezone == '#':
                timezone = node.timezone
            elif timezone == '*':
                break
            latitude_region = raw_input("Enter the latitude region (ex: N or S).\n")
            if latitude_region == '*':
                break
            longitude_region = raw_input("Enter the longitude region (ex: E or W).\n")
            if longitude_region == '*':
                break
            latitude = raw_input("Enter the latitude of the city.\n")
            if latitude == '*':
                break
            longitude = raw_input("Enter the longitude of the City.\n")
            if longitude == '*':
                break
            if latitude == '#' or latitude_region == '#' or longitude == '#' or longitude_region == '#':
                coordinates = node.coordinates
            else:
                coordinates = ("{\"" + latitude_region + "\": " +
                               latitude + ", \"" + longitude_region + "\": " + longitude + "}")
            population = raw_input("Enter the city's population.\n")
            if population == '#':
                population = node.population
            elif population == '*':
                break
            region = raw_input("Enter the region.\n")
            if region == '#':
                region = node.region
            elif region == '*':
                break

            new_metro = {"code": node.code, "name": name, "country": country, "continent": continent,
                         "timezone": timezone, "coordinates": coordinates, "population": population,
                         "region": region}
            verify_input = raw_input("Is this information correct?\n" +
                                     "\tress 0 if it is correct.\n" +
                                     "\tPress 1 to start over.\n" +
                                     "\tPress * to cancel and exit.\n" +
                                     "\t" + str(new_metro) + "\n")
            if verify_input == '0':
                graph.modify_node(code, new_metro)
                modified_cities.append(str(new_metro['code']))
            elif verify_input == '*':
                break
    if len(modified_cities) > 0:
        return "These cities have been modified: " + str(modified_cities) + "\n"
    return "No cities were modified.\n"
