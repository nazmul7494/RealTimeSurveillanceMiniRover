# This script will obtain the video footage from the USB webcam
# connected to the Raspberry Pi 2 onboard the mini-rover utilizing OpenCV and
# send that video frame data to the remote control device through the 
# central server.

import numpy as np
import cv2
import socket
import time

# Setting up the webcam capture object parameters
cap = cv2.VideoCapture(0)
cap.set(3,320.0)
cap.set(4,240.0)


clientSocket = socket.socket(socket.AF_INET,socket.SOCK_STREAM,0)
hostIP = "192.168.0.101" # Put server ip address here
port = 6001 # Put the port number here

clientSocket.connect((hostIP,port)) # Connecting to the central server
id = "1"

clientSocket.send(id) # Identifying self to the central server 
print("\nRegistered To Server\n")


if(not(cap.isOpened())):	
	cap.open()	
	print('Capture Opened')


while(True):		
	ret, frame = cap.read()	# Reading a single video frame
	gray = cv2.cvtColor(frame,cv2.COLOR_BGR2GRAY) # Converting the RGB frame to a Grayscale frame
	b = bytearray(gray) # Converting the frame object to a bytearray					
	clientSocket.sendall(b) # Writing the whole bytearray to the server
	print("Data Sent")
	print("----------------------")

# Once outside the loop,		
cap.release() # Releasing the capture, freeing the WebCam.
