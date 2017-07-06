import urllib
import requests

file = open("info.txt")
n = 1
while 1:
    line = file.readline()
    if not line:
	break
    urllib.urlretrieve(line,str(n)+".apk")
    n = n+1
pass
