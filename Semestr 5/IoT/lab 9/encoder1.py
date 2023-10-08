#!/usr/bin/env python3

from config import *
import RPi.GPIO as GPIO
import time

brightness = 0
execute = True
diode1 = GPIO.PWM(led1, 50)

encoderLeftPrevoiusState = GPIO.input(encoderLeft)
encoderRightPrevoiusState = GPIO.input(encoderRight)

def turn_encoder(channel):
    global encoderLeftPrevoiusState
    global encoderRightPrevoiusState
    global brightness
    encoderLeftCurrentState = GPIO.input(encoderLeft)
    encoderRightCurrentState = GPIO.input(encoderRight)

    if(encoderLeftPrevoiusState == 1 and encoderLeftCurrentState == 0 and brightness < 100):
            brightness += 10
            diode1.ChangeDutyCycle(brightness)
            print(brightness)
    if(encoderRightPrevoiusState == 1 and encoderRightCurrentState == 0 and brightness > 0):
            brightness -= 10
            diode1.ChangeDutyCycle(brightness)
            print(brightness)
    
    encoderLeftPrevoiusState = encoderLeftCurrentState
    encoderRightPrevoiusState = encoderRightCurrentState

def main():
    dutyCycle = 0
    diode1.start(dutyCycle)
    
    GPIO.add_event_detect(encoderLeft, GPIO.FALLING, callback=turn_encoder, bouncetime=200)
    GPIO.add_event_detect(encoderRight, GPIO.FALLING, callback=turn_encoder, bouncetime=200)

    while True:
        pass

if __name__ == "__main__":
    main()