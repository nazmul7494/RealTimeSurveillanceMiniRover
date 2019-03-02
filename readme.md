This repository contains all the codes utilized in the project "A Real-time Surveillance Mini-rover Based on OpenCV-Python-JAVA Using Raspberry Pi 2" (presented at the 2015 5th IEEE ICCSCE conference, DOI: 10.1109/ICCSCE.2015.7482232).

Major components of the project:

	A)	Hardware Components:

		1)	Mini-rover:
				a)	Raspberry Pi 2
				b)	L298N Dual H-bridge Motor Controller
				c)	USB Webcam
				d)	Portable GSM Router
				e)	RC Car Chassis

		2)	Central Server:
			
				a)	When all components are on the same local network:
					PC/ Laptop (with JAVA installed) connected to the same subnet as the mini-rover and the remote control platform.

				b)	When diffrent components are on different subnets (Global operational range):
					A cloud server (with JAVA installed) with global ip address.

		3)	Remote Control Device:

				PC/ Laptop with JAVA installed.

	B)	Software Components:

		1)	OpenCV:
			For capturing video frame data from the webcam connected to the Raspberry Pi 2 onboard the mini-rover).
		
		2)	JAVA:
			The central server program and the programs for the remote control machine are written in JAVA.

		3)	Python:
			Python scripts are utilized for streaming the mini-rover's webcam data to the remote control device and also
			for receiving movement control commands from the remote control device.