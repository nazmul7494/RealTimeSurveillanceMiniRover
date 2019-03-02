# This script will run on the Raspberry Pi 2 onboard the mini-rover
# and it will control the mini-rover's movement control circuit connected to the R-Pi

import socket
import RPi.GPIO as gpio

#-------------- GPIO Pin Setup Starts-------------------------
gpio.setwarnings(False)

#----------------- Setting Pins For the Rear Motor ---------------------
gpio.setmode(gpio.BOARD)
gpio.setup(7,gpio.OUT)
gpio.setup(11,gpio.OUT)
gpio.setup(13,gpio.OUT)
gpio.setup(15,gpio.OUT)

#------------- Setting Pins For the Front Motor ---------------------
gpio.setup(16,gpio.OUT)
gpio.setup(18,gpio.OUT)
gpio.setup(38,gpio.OUT)
gpio.setup(40,gpio.OUT)

#--------------- GPIO Setup Ends ------------------------------


#------------- Function Definitions Starts--------------------------------

def goForward():
	gpio.output(7,True)
 	gpio.output(11,True)

	gpio.output(13,False)
	gpio.output(15,True)
	

def goBackward():
	gpio.output(7,True)
 	gpio.output(11,True)

	gpio.output(13,True)
	gpio.output(15,False)
	

def goRight():	
	gpio.output(16,True)
	gpio.output(18,True)

	gpio.output(38,True)
	gpio.output(40,False)
	

def goLeft():	
	gpio.output(16,True)
	gpio.output(18,True)

	gpio.output(38,False)
	gpio.output(40,True)
	

def movStop():
	gpio.output(7,False)
	gpio.output(11,False)

def movStraight():	
	gpio.output(16,False)
	gpio.output(18,False)


#--------------------------Function Definitions Ends -------------------------------------------
	



#-------------------------- Main Controlling Function Starts -----------------------------------------


clientSocket = socket.socket(socket.AF_INET,socket.SOCK_STREAM,0)

hostIP = "192.168.0.101" # Put server IP here
port = 6000

clientSocket.connect((hostIP,port))
print("Connected To Server")

id = "1"
clientSocket.send(id) # Identifying self to the central server

flag = True

while flag:
	# Receiving command data sent by remote control device
	message = clientSocket.recv(1024)
	# Need to encode as unicode string
	message = str(message).encode('unicode_escape')

	message = message[8:]
	print("From Server: "+message)

	if message=="UP":
		goForward()
		print("Up func called")	    

	if message=="DOWN":
		goBackward()
		print("Down func called")	
		
	if message=="RIGHT":
		goRight()		

	if message=="LEFT":
		goLeft()		

	if message=="STOP":
		movStop()		

	if message=="STRAIGHT":
		movStraight()	

	if message=="EXIT":
		flag=False
   
print("OutSide while loop. Exiting Program.")

movStraight()
movStop()

# Cleaning up the GPIO pin setup of the Raspberry Pi
gpio.cleanup()