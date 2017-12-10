#-------------------------------------------------------------------------------
# Name:        module1
# Purpose:
#
# Author:      Keenan
#
# Created:     10/12/2017
# Copyright:   (c) Keenan 2017
# Licence:     <your licence>
#-------------------------------------------------------------------------------
import sys, requests, json

def get_services(arcgis_server_catalog, catalog_folder=""):
    """ Recursively search ArcGIS services in a given server and specified folder
        Services are queried using ArcGIS REST API
    """
    result = list()
    resp = requests.get(arcgis_server_catalog+catalog_folder+"?f=json").json()
    try:
        services = resp["services"]
        for service in services:
            url = str(arcgis_server_catalog+""+service["name"] +"/"+ service["type"])
            result.append(url)
    except KeyError:
        print("  except: "+arcgis_server_catalog+catalog_folder+"\n    "+json.dumps(resp))
    try:
        folders = resp["folders"]
        for folder in folders:
            result.extend(get_services(arcgis_server_catalog, folder+"/"))
    except KeyError:
        print("  except: "+arcgis_server_catalog+catalog_folder+"\n    "+json.dumps(resp))
    return result

if __name__ == '__main__':
    #arcgis_server_catalog = "http://demo.esriindonesia.co.id/arcgis/rest/services/"
    arcgis_server_catalog = "https://portal.ina-sdi.or.id/server/rest/services/"
    if len(sys.argv) == 1:
        sys.argv.append(arcgis_server_catalog)
    print sys.argv[1]
    #for service in get_services(sys.argv[1]):
    #    print service

    resp = requests.post("https://esriindonesia.maps.arcgis.com/sharing/rest/generateToken?f=json", data={"username":"", "password":"", "referer":"http://www.arcgis.com"})
    print(json.dumps(resp.json()["token"]))
