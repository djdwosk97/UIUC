import json
import sys
from graph import Graph
import statistics
from map import Map
import webbrowser
import modifynetwork
import generateJson
import connections


class Controller:
    def __init__(self):
        return

    # initialize a graph from a file
    @staticmethod
    def create_graph(graph, infile, bidirectional):
        try:
            with open(infile, 'r') as data_file:
                data = json.load(data_file)
        except IOError:
            print "That file is invalid.\n"
            return
        graph.initialize_nodes(data['metros'])
        if bidirectional == '0':
            graph.initialize_routes(data['routes'])
        else:
            graph.initialize_directed_routes(data['routes'])


def main():
    graph = Graph()
    Controller.create_graph(graph, '../dataFile/json_data.json', '0')
    while 1:
        user_input = raw_input("What would you like to do?\n" +
                               "\tPress 0 for a list of all cities CSAir flies to\n" +
                               "\tPress 1 to get information about a specific city\n" +
                               "\tPress 2 for statistical information about CSAir's route network\n" +
                               "\tPress 3 to generate a route map\n" +
                               "\tPress 4 to modify CSAir's network\n" +
                               "\tPress 5 to create a new JSON file.\n" +
                               "\tPress 6 to import an additional flight network.\n" +
                               "\tPress 7 to find a connecting route.\n" +
                               "\tPress 8 to find the shortest route.\n" +
                               "\tPress * to exit.\n")
        if user_input == '0':
            print str(graph.get_cities()) + "\n"
        elif user_input == '1':
            user_input = raw_input("Enter city name.\n")
            print str(graph.get_city_data(user_input)) + "\n"
        elif user_input == '2':
            statistics.statistics_helper(graph)
        elif user_input == '3':
            map_url = Map.generate_route_map(graph)
            webbrowser.open_new(map_url)
        elif user_input == '4':
            modifynetwork.modify_network(graph)
        elif user_input == '5':
            print generateJson.generate_json_main(graph)
        elif user_input == '6':
            new_graph = raw_input("Enter the location of the new JSON file (ex: '../dataFile/new_json_data.json'.\n")
            while 1:
                bidirectional = raw_input("Would you like to add bi-directional routes for every route in the JSON?\n" +
                                          "\tPress 0 to add bi-directional routes.\n" +
                                          "\tPress 1 to add a single direction route.\n" +
                                          "\tPress * to cancel and exit.\n")
                if bidirectional == '*':
                    break
                elif (bidirectional == '0') or (bidirectional == '1'):
                    Controller.create_graph(graph, new_graph, bidirectional)
                    break
                else:
                    print "That input is not valid.\n"
        elif user_input == '7':
            print connections.find_connecting_routes(graph)
        elif user_input == '8':
            print connections.find_shortest_route(graph)
        elif user_input == '*':
            print "Have a nice day!"
            break
        else:
            print "That input is invalid, please try again.\n"

if __name__ == '__main__':
    sys.exit(main())
