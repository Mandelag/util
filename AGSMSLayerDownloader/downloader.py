import requests, os, sys, shutil, time

def download(mapservice_layer_url, CHUNK_SIZE=50, OFFSET=0):
    """ Download MapService layer as chunks of separated EsriJSON file(s).
        Chunks refers to the number of feature(s) we want to download at each request.
        We download it this way so that the server is not overwhelmed by our requests.
        Generally, you want to specify small CHUNK_SIZE if most of the features size in the layer is big.
        Downloads the whole layer if the number of features in the layer < than the chunk size.
    """
    print(mapservice_layer_url.strip()[-1])
    if mapservice_layer_url.strip()[-1] == "/":
        mapservice_layer_url = mapservice_layer_url[:-1]
    token = mapservice_layer_url.split("/")
    outname = token[-3] + "_" + str(token[-1])  # output file name

    mapservice_layer_url = mapservice_layer_url + "/query"
    params = {
        "where": "1=1",
        "returnIdsOnly": "true",
        "f": "json"
    }
    print "Requesting Ids.."
    r = requests.post(mapservice_layer_url, params=params)

    ids = r.json()["objectIds"]
    ids.sort()
    print "Features length: "+ str(len(ids))
    i = OFFSET

    try:
        shutil.rmtree(outname)
    except:
        pass
    print "Creating directory: "+outname
    os.makedirs(outname)

    print "Download chunks of features with CHUNK_SIZE = "+str(CHUNK_SIZE)
    while i <= int(len(ids)/CHUNK_SIZE):
        try:
            print "Processing "+str(i+1)+"/"+str(int(len(ids)/CHUNK_SIZE)+1)+" chunks"
            subIds = ids[i*CHUNK_SIZE:(i+1)*CHUNK_SIZE]
            chunkIds = ",".join(map(str, subIds))
            with open(outname+"/"+outname+"_"+str(i)+".json", "w") as f:
                result = getFeaturesByIds(mapservice_layer_url, chunkIds)
                f.write(result)
            i+=1
        except Exception as e:
            print("Exception.. retrying in 30 seconds.."+str(e))
            #raise Exception(e)
            time.sleep(30)
    print "DONE."

def getFeaturesByIds(url, ids):
    """ Get chunks of features """
    params = {
        "objectIds": ids,
        "outFields": "*",
        "f": "json"
    }
    r = requests.post(url, params=params)
    return r.content

if __name__ == "__main__":
    if len(sys.argv) > 3:
        download(sys.argv[1], int(sys.argv[2]), int(sys.argv[3]))
    elif len(sys.argv) > 2:
        download(sys.argv[1], int(sys.argv[2]))
    elif len(sys.argv) > 1:
        download(sys.argv[1])
    else:
        print "Usage: \n    python downloader.py [Map Service Layer URL] [Chunk size]\n    Chunk size -> feature downloaded on each request."
        exit()




