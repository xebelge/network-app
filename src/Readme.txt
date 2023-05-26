There is 5 different server 4 of them is TCP, the other one is UDP. UDP using for video streaming simulation.


To testing application firstly you need to run 

LoadBalancer.java
TCPServer.java
UDPServer.java
CalculationServer.java
FileServer.java

then

Client.java


After running to Client you can send request servers.

There is Download folder in the src, when you choose "Get File" option its copy Test.txt file in to the Downlaod folder


Ports:

LoadBalancer : 6780
TCPServer: 1112
FileServer: 2223
CalculationServer: 3334
UDPServer: 5556




All servers behave first come first serve approach.




