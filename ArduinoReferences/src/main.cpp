#include <Arduino.h>
#include <iostream>
#include <fstream>
#include <string>

#ifdef ESP32
	#include <WiFi.h>
	#include <AsyncTCP.h>
#elif defined(ESP8266)
	#include <ESP8266WiFi.h>
	#include <ESPAsyncTCP.h>
#endif
#include "ESPAsyncWebServer.h"

const size_t port = 80;
const char* ssid = "CoffeeBreaker";
const char* password = "CoffeeBreakerTest"; // global constants assigned to your wifi network
const char *PARAM_MESSAGE = "message";

AsyncWebServer server(port);

void notFound(AsyncWebServerRequest *request) {
	request->send(404, "text/plain", "Not found");
}

void connectToWiFi() { // connects to wifi network
	Serial.begin(115200);
	WiFi.begin(ssid, password);
	if (WiFi.waitForConnectResult() != WL_CONNECTED) {
		Serial.printf("WiFi Failed!\n");
		return;
	}
	Serial.println("Connected to the WiFi network");
	Serial.print("IP Address: ");
	Serial.println(WiFi.localIP());
}

std::string readFile(std::string destination) {
	std::string text, line;
	std::ifstream file(destination);
	if (file.is_open()) {
		while (std::getline(file, line))
			text += line + "\n";
		file.close();
	}
	text.pop_back();
	return text;
}

void setup() {
	connectToWiFi();
	server.on("/", HTTP_GET, [](AsyncWebServerRequest *request) {
		request->send(200, "text/plain", "Hello, world");
	});

	server.on("/get", HTTP_GET, [](AsyncWebServerRequest *request) {
		String message;
		if (request->hasParam(PARAM_MESSAGE))
			message = request->getParam(PARAM_MESSAGE)->value();
		else
			message = "No message sent";
		request->send(200, "text/plain", "Hello, GET: " + message);
	});

	server.on("/post", HTTP_POST, [](AsyncWebServerRequest *request) {
		String message;
		if (request->hasParam(PARAM_MESSAGE, true))
			message = request->getParam(PARAM_MESSAGE, true)->value();
		else
			message = "No message sent";
		request->send(200, "text/plain", "Hello, POST: " + message);
	});

	server.onNotFound(notFound);

	server.begin();
}

void loop() {
}
