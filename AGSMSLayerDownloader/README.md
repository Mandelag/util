## ArcGIS MapServer Layer Downloader

This program will download the MapServer layer as chunks of separate GeoJSON files.
We download it this way so that the map server is not overwhelmed by our requests.
Generally, you want to specify small chunk size if most of the feature sizes are big.
This program will download the whole layer if the number of features in the layer < chunk size.
This utility is inspired by https://github.com/tannerjt/AGStoShapefile


This script will work if only the MapServer query supports GeoJSON.


### How to run the code

1. Install Python 2.7
    1. Install Python 2.7
    2. Make sure you can run `python` in your command line terminal
  
2. Install `requests` library (http://python-requests.org/)
    1. Type `pip requests` in your command line terminal
  
3. Run `python downloader.py [MapServer Layer URL] [chunk size]` in your command line terminal
    - Chunk size refers to the number of feature(s) we want to download at each request.
 
4. The result will be stored in a folder named `[MapServer Name]_[Layer Id]`
 
 
### Example Usage

```
python downloader.py https://portal.ina-sdi.or.id/arcgis/rest/services/KOTABOGOR/JalurAngkot/MapServer/0/ 100
```


### Optional

If you want to merge the separate GeoJSON files, use GDAL/OGR's `ogr2ogr` tool.

You can download GDAL/OGR tools here: https://trac.osgeo.org/gdal/wiki/DownloadingGdalBinaries

For more information, please visit http://mandelag.com


### Alternatives

https://github.com/esri/terraformer

https://github.com/tannerjt/AGStoShapefile
