#include "MyLed.h"

void MyLed::init(int red, int green, int blue)
{
    led_red = red;
    led_green = green;
    led_blue = blue;
    
    pinMode(led_red, OUTPUT);
    digitalWrite(led_red, LOW);

    pinMode(led_green, OUTPUT);
    digitalWrite(led_green, LOW);

    pinMode(led_blue, OUTPUT);
    digitalWrite(led_blue, LOW);
}

void MyLed::colorOn(int led)
{
    if (led == 1)
    {
        analogWrite(led_red, 255);
        analogWrite(led_green, 0);
        analogWrite(led_blue, 0);
    }

    else if (led == 2)
    {
        analogWrite(led_red, 0);
        analogWrite(led_green, 255);
        analogWrite(led_blue, 0);
    }

    else if (led == 3)
    {
        analogWrite(led_red, 0);
        analogWrite(led_green, 0);
        analogWrite(led_blue, 255);
    }

    else if (led == 4)
    {
        analogWrite(led_red, 255);
        analogWrite(led_green, 255);
        analogWrite(led_blue, 0);
    }

    else if (led == 5)
    {
        analogWrite(led_red, 0);
        analogWrite(led_green, 255);
        analogWrite(led_blue, 255);
    }

    else if (led == 6)
    {
        analogWrite(led_red, 255);
        analogWrite(led_green, 0);
        analogWrite(led_blue, 255);
    }

    else if (led == 7)
    {
        analogWrite(led_red, 0);
        analogWrite(led_green, 0);
        analogWrite(led_blue, 0);
    }

    else if (led == 8)
    {
        analogWrite(led_red, 255);
        analogWrite(led_green, 255);
        analogWrite(led_blue, 255);
    }
    
}
