from unittest import TestCase
from src.graph import Graph
from src import statistics
from src import connections
import json


class TestAirportGraph(TestCase):

    # Initialize graph for testing
    def setUp(self):
        with open('../dataFile/json_data.json', 'r') as data_file:
            data = json.load(data_file)
        self.graph = Graph()
        self.graph.initialize_nodes(data['metros'])
        self.graph.initialize_routes(data['routes'])

    # Test that the graph is correctly built
    def testInitialize(self):
        assert (self.graph.find_node("SCL").name == "Santiago")
        assert (self.graph.find_node("SCL").get_destinations() == ['LIM'])
        assert (self.graph.find_node("MEX").population == 23400000)
        assert (self.graph.find_node("LOS").get_destinations() == ['SAO', 'KRT', 'FIH'])

    # Test adding a node and incident edges
    def testAddNodeAndRoutes(self):
        new_metro = {"code": "EWR", "name": "New Jersey", "country": "US",
                     "continent": "North America", "timezone": -5,
                     "coordinates": {"N": 41, "W": 74}, "population": 75, "region": "North"}
        self.graph.add_node(new_metro)
        assert (self.graph.find_node("EWR").name == "New Jersey")
        new_routes = [{"ports": ["EWR", "MEX"], "distance": 1300},
                      {"ports": ["EWR", "LAX"], "distance": 3500}]
        self.graph.add_new_routes(new_routes)
        assert (self.graph.find_node("EWR").get_destinations() == ['MEX', 'LAX'])
        assert (self.graph.find_node("LAX").get_destinations() == ['MEX', 'SYD', 'SFO', 'CHI', 'EWR'])
        assert (self.graph.find_node("MEX").get_destinations() == ['LIM', 'LAX', 'CHI', 'MIA', 'BOG', 'EWR'])

    # Test modifying a city
    def testModification(self):
        modified_metro = {"code": "SCL", "name": "Santiago", "country": "CL",
                          "continent": "South America", "timezone": -4,
                          "coordinates": {"S": 33, "W": 71}, "population": 9000000, "region": 1}
        self.graph.modify_node("SCL", modified_metro)
        assert (self.graph.find_node("SCL").population == 9000000)

    # Test statistics
    def testStatistics(self):
        with open('../dataFile/test_data.json') as data_file:
            data = json.load(data_file)
        test_graph = Graph()
        test_graph.initialize_nodes(data['metros'])
        test_graph.initialize_routes(data['routes'])
        assert statistics.find_largest_city(test_graph) == "Mexico City is the largest city in our network with 23400000 people.\n"  # Mexico City @ 23,400,000
        assert statistics.find_smallest_city(test_graph) == "Miami is the smallest city in our network with 5400000 people.\n"  # Miami @ 5,400,000
        assert statistics.find_average_city(test_graph) == "The average population of all cities in our network is 11457142 people.\n"  # 80,200,000 total population / 7 cities
        assert statistics.find_longest_flight(test_graph) == "The longest flight is from Lima to Mexico City at 4231 miles.\n"     # LIM <-> MEX @ 4,231 Miles
        assert statistics.find_shortest_flight(test_graph) == "The shortest flight is from Lima to Bogota at 1879 miles.\n"  # LIM <-> BOG @ 1,879 Miles
        assert statistics.find_average_flight(test_graph) == "The average flight is 2638 miles long.\n"  # 15829/6 = 2638
        assert statistics.find_hubs(test_graph) == "CSAir's Hubs: ['MEX']\n"  # Mexico City with 4 unique flights

    def testAddNewMap(self):
        with open('../dataFile/new_json_data.json') as data_file:
            data = json.load(data_file)
        self.graph.initialize_nodes(data['metros'])
        self.graph.initialize_routes(data['routes'])
        assert statistics.find_hubs(self.graph) == "CSAir's Hubs: ['CMI']\n"
        assert statistics.find_largest_city(self.graph) == "Tokyo is the largest city in our network with 34000000 people.\n"

    def testFindShortestRoute(self):
        assert connections.dijkstra(self.graph, "SCL", "MIA") == ("Your flight from SCL to MIA will cost $2028.5 " + # (2453*.35)+(1879*.30)+(2425*.25)=$2028.50
                                                                  "and the total travel time will be 816.56 minutes.\n" + # ((400/375*60)+(2053/750*60))+((400/375*60)+(1479/750*60))+((400/375*60)+(2025/750*60)) + 100+80
                                                                  "The total travel distance will be 6757 km and follows the route SCL->LIM->BOG->MIA.\n") # 2453+1879+2425
