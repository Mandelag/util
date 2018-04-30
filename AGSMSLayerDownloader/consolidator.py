import  os
import sys
import arcpy
import json

def consolidate(directory, output, output_name):
    #import pdb
    scriptpath = sys.path[0]
    inputdir = os.path.join(scriptpath, directory)
    outputdir = os.path.join(scriptpath, output)
    
    files = os.listdir(inputdir)
    n_files = len(files)
    jsons = []
    features = {}
    counter = 0
    group = 0
    #pdb.set_trace()
    once = True;
    for filename in files:
        file_full_name = os.path.join(inputdir, filename)
        output_full_name = os.path.join(outputdir, filename)
        #arcpy.JSONToFeatures_conversion(file_full_name, output_full_name)
        with open(file_full_name) as input:
            if once:
                features = json.load(input)
                once = False
            else:
                features["features"].extend(json.load(input)["features"])
        counter = counter + 1;
        if counter >= 500:
            group = group + 1
            with open(output_name+"_"+str(group), "w+") as outputfile:
                outputfile.write(json.dumps(features))
            counter = 0
            features = {}
            once = True
    
if __name__ == "__main__":
    if len(sys.argv) > 3:
        consolidate(sys.argv[1], sys.argv[2], sys.argv[3])
    else:
        print "Usage: \n    python consolidator.py [input_folder] [output_folder] [output_name]"
        exit()




