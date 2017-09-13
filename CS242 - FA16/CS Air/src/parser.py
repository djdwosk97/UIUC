import json


class Parser:
    def __init__(self):
        return

    @staticmethod
    def parse_file():
        with open('json_data.json') as data_file:
            data = json.load(data_file)
            metros = data['metros']
            routes = data['routes']
        print metros
        print routes
    # for metros in metros:
    #    print metros
    # for routes in routes:
    #    print routes
    # for metros in metros:
    #    print metros['code'] + '>' +  metros['name'] + '>' + metros['country']
    #
    # for routes in routes:
    #    print (routes['ports'][1] == "MEX")
    # for routes in routes:
    #    print (routes)
