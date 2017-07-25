Description:

This program will download the MapServer layer as chunks of separate GeoJSON files.
Chunks refers to the number of feature(s) we want to download at each request.
We download it this way so that the server is not overwhelmed by our requests.
Generally, you want to specify small chunk size if most of the feature size in the layer are big.
Downloads the whole layer if the number of features in the layer < than the chunk size.

How to run the code:

1. Install Python 2.7
2. Install requests library
3. Run the downloader.py (read the usage)

[Optional]
If you want to merge the separate GeoJSON files, use GDAL/OGR's ogr2ogr tool.

