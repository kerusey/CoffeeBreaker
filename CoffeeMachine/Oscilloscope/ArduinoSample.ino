// currently availbale only for one analog input
// TODO : add a cpp multi-thread script for the RPI

#if ARDUINO >= 100
#include <Arduino.h>
#else
#include <WProgram.h>
#endif

#define ANALOG_IN 0 // TODO : line 2

void setup() {
    Serial.begin(9600);
}
void loop() { // YA AFIGENNIY PROGER NAFIG
    int val = analogRead(ANALOG_IN);
    Serial.write(0xff);
    Serial.write((val >> 8) & 0xff);
    Serial.write(val & 0xff);
}
