#define _GLIBCXX_USE_CXX11_ABI 0
#include <Arduino.h>

#include <string>
#include <iostream>
#include <fstream>

#ifdef USE_LittleFS
	#include <FS.h>
	#define SPIFFS LittleFS
	#include <LittleFS.h>
#endif

#ifdef ESP32
	#include <WiFi.h>
	#include <AsyncTCP.h>
#elif defined(ESP8266)
	#include <ESP8266WiFi.h>
	#include <ESPAsyncTCP.h>
#endif

#include <ESPAsyncWebServer.h>

const size_t port = 80;
const char* ssid = "CoffeeBreaker";
const char* password = "CoffeeBreakerTest"; // global constants assigned to your wifi network
const char *PARAM_MESSAGE = "message";

AsyncWebServer server(port);

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

void notFound(AsyncWebServerRequest *request) {
	request->send(200, "text/plain", readFile("index.html").c_str());
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

void setup() {
	connectToWiFi();
	server.on("/", HTTP_GET, [](AsyncWebServerRequest *request) {
		request->send(200, "text/plain", "Successfully connected");
	});

	server.on("/post", HTTP_POST, [](AsyncWebServerRequest *request) {
		int params = request->params();
		for (auto i = 0; i < params; i++) {
			AsyncWebParameter *p = request->getParam(i);
			Serial.printf("GET[%s]: %s\n", p->name().c_str(), p->value().c_str());
		}
	});

	server.onNotFound(notFound);

	server.begin();
}

void loop() {
}
