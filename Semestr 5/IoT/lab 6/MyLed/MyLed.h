#ifndef MYLED_H_
#define MYLED_H_

#include "Arduino.h"

#define RED 1
#define GREEN 2
#define BLUE 3
#define YELLOW 4
#define CYAN 5
#define MAGENTA 6
#define BLACK 7
#define WHITE 8

class MyLed 
{
public:
    void init(int red, int green, int blue);
    void colorOn(int led);
private:
    int led_red;
    int led_green;
    int led_blue;
};

#endif