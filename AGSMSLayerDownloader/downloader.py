import requests, os, sys, shutil

def download(mapservice_layer_url, CHUNK_SIZE=50):
    """ Download MapService layer as chunks of separated GeoJSON file(s).
        Chunks refers to the number of feature(s) we want to download at each request.
        We download it this way so that the server is not overwhelmed by our requests.
        Generally, you want to specify small CHUNK_SIZE if most of the features size in the layer is big.
        Downloads the whole layer if the number of features in the layer < than the chunk size.
    """
    token = mapservice_layer_url.split("/")
    outname = token[-3] + "_" + str(token[-1])  # output file name
    
    mapservice_layer_url = mapservice_layer_url + "/query"
    params = {
        "where": "1=1", 
        "returnIdsOnly": "true",
        "f": "geojson" #or "pjson"
    }
    print "Requesting Ids.."
    r = requests.post(mapservice_layer_url, params=params)
    
    ids = r.json()["objectIds"]
    ids.sort()
    print "Features length: "+ str(len(ids))
    i = 0
    
    try:
        shutil.rmtree(outname)
    except:
        pass
    print "Creating directory: "+outname
    os.makedirs(outname)

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
    r = requests.post(url, params=params)
    return r.content

if __name__ == "__main__":
    if len(sys.argv) > 2:
        download(sys.argv[1], int(sys.argv[2]))    
    elif len(sys.argv) > 1:
        download(sys.argv[1])
    else:
        print "Usage: \n    python downloader.py [Map Service Layer URL] [Chunk size]\n    Chunk size -> feature downloaded on each request."
        exit()

    


