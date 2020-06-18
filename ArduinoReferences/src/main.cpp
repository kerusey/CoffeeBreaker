#include <Arduino.h>
#include <WiFi.h>

const char* ssid = "CoffeeBreaker";
const char* password = "CoffeeBreakerTest"; // global constants assigned to your wifi network

void connectToWiFi() {
	Serial.begin(115200);
	WiFi.begin(ssid, password);
	while (WiFi.status() != WL_CONNECTED) {
		delay(500);
		Serial.println("Connecting to WiFi..");
	}
	Serial.println("Connected to the WiFi network");
}

void setup() {
	connectToWiFi();
	// put your setup code here, to run once:
}

void loop() {
	// kys
}