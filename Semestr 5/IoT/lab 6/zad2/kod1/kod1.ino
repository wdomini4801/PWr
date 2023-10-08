#include "MyLed.h"

#define LED_RED 6
#define LED_GREEN 5
#define LED_BLUE 3

MyLed led_controller;

void setup() 
{
  led_controller.init(LED_RED, LED_GREEN, LED_BLUE);
}

void loop() {
  led_controller.colorOn(BLACK);

}
