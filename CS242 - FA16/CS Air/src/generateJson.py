# Generate a JSON data file from the graph
def generate_json_main(graph):
    with open('../dataFile/output_file.json', 'w') as outfile:
        airport_codes = graph.get_airport_codes()
        output = "{\n" + "\t\t\"metros\" : [\n"
        for i in range(len(airport_codes)):
            node = graph.find_node(airport_codes[i])
            if i == 0:
                output += "\t\t\t\t{\n"
            else:
                output += "{\n"
            output += "\t\t\t\t\t\t" + "\"code\" : \"" + node.code + "\" ,\n"
            output += "\t\t\t\t\t\t" + "\"name\" : \"" + node.name + "\" ,\n"
            output += "\t\t\t\t\t\t" + "\"country\" : \"" + node.country + "\" ,\n"
            output += "\t\t\t\t\t\t" + "\"continent\" : \"" + node.continent + "\" ,\n"
            output += "\t\t\t\t\t\t" + "\"timezone\" : \"" + str(node.timezone) + "\" ,\n"
            output += "\t\t\t\t\t\t" + "\"coordinates\" : \"" + str(node.coordinates) + "\" ,\n"
            output += "\t\t\t\t\t\t" + "\"population\" : \"" + str(node.population) + "\" ,\n"
            output += "\t\t\t\t\t\t" + "\"region\" : \"" + str(node.region) + "\"\n"
            if i != len(airport_codes)-1:
                output += "\t\t\t\t} , "
            else:
                output += "\t\t\t\t}\n"
        output += "\t\t] ,\n"

        output += "\t\t\"routes\" : [\n"
        for i in range(len(airport_codes)):
            node = graph.find_node(airport_codes[i])
            routes = node.get_routes()
            for route in routes:
                if i == 0:
                    output += "\t\t\t\t{\n"
                else:
                    output += "\t\t\t\t} , {\n"
                output += "\t\t\t\t\t\t" + "\"ports\" : [\"" + node.code + "\" , " + route.destination + "] ,\n"
                output += "\t\t\t\t\t\t" + "\"distance\" : " + str(route.distance) + "\n"
        output += "\t\t\t\t}\n"
        output += "\t\t]\n}"
        outfile.write(output)
    return "Successfully generated JSON file"
