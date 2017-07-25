import requests, os, sys

def download(mapservice_layer_url, CHUNK_SIZE=5):
    """ Download MapService layer as chunks of separated GeoJSON file(s).
        Chunks refers to the number of feature(s) we want to download at each request.
        We download it this way so that the server is not overwhelmed by our requests.
        Generally, you want to specify small CHUNK_SIZE if most of the features size in the layer is big.
        Downloads the whole layer if the number of features in the layer < than the chunk size.
    """
    token = mapservice_layer_url.split("/")
    outname = token[6] + "_" + str(token[8])  # output file name
    
    mapservice_layer_url = mapservice_layer_url + "/query"
    params = {
        "where": "1=1", 
        "returnIdsOnly": "true",
        "f": "geojson" #or "pjson"
    }
    print "Requesting Ids.."
    r = requests.get(mapservice_layer_url, params=params)
    
    ids = r.json()["objectIds"]
    ids.sort()
    print "Features length: "+ str(len(ids))
    i = 0
    
    try:
        print "Creating directory: "+outname
        os.makedirs(outname)
    except OSError as e:
        print "Please delete the existing directory: "+outname
        raise

    print "Download chunks of features with CHUNK_SIZE = "+str(CHUNK_SIZE)
    while i <= int(len(ids)/CHUNK_SIZE):
        print "Processing "+str(i+1)+"/"+str(int(len(ids)/CHUNK_SIZE)+1)+" chunks"
        subIds = ids[i*CHUNK_SIZE:(i+1)*CHUNK_SIZE]
        chunkIds = ",".join(map(str, subIds))
        
        with open(outname+"/"+outname+"_"+str(i)+".geojson", "w") as f:
            f.write(getFeaturesByIds(mapservice_layer_url, chunkIds))
        i+=1
    print "DONE."

def getFeaturesByIds(url, ids):
    """ Get chunks of features """
    params = {
        "objectIds": ids,
        "outFields": "*",
        "f": "geojson" #or "pjson"
    }
    r = requests.get(url, params=params)
    return r.content

if __name__ == "__main__":
    if len(sys.argv) < 3:
        print "Usage: \n    python downloader.py [Map Service Layer URL] [Chunk size]"
        exit()
    download(sys.argv[1], int(sys.argv[2]))
      
    


