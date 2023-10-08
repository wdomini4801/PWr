#!/usr/bin/env python3

from config import *
import w1thermsensor
import time
import os
import neopixel
import board
import busio
import adafruit_bme280.advanced as adafruit_bme280

current_parameter = 0
color = (255, 0, 0)

temperature_range = [18, 19, 20, 21, 22, 23, 24, 25]
humidity_range = [40, 41, 42, 43, 44, 45, 46, 47]
pressure_range = [995, 996, 997, 998, 999, 1000, 1001, 1002]


def bme280_config():
    i2c = busio.I2C(board.SCL, board.SDA)
    bme280 = adafruit_bme280.Adafruit_BME280_I2C(i2c, 0x76)
    bme280.sea_level_pressure = 1013.25
    bme280.standby_period = adafruit_bme280.STANDBY_TC_500
    bme280.iir_filter = adafruit_bme280.IIR_FILTER_X16
    return bme280


def bme280_read(bme):
    bme.overscan_pressure = adafruit_bme280.OVERSCAN_X16
    bme.overscan_humidity = adafruit_bme280.OVERSCAN_X1
    bme.overscan_temperature = adafruit_bme280.OVERSCAN_X2
    print(bme.temperature)
    print(bme.humidity)
    print(bme.pressure)
    return {"temperature" : bme.temperature, "humidity" : bme.humidity, "pressure" : bme.pressure}

def diode_configuration(pixel, dict):
    global color
    pixel.fill((0, 0, 0))
    print(dict["temperature"])
    if current_parameter == 0:
        for i in range(7):
            if dict["temperature"] > temperature_range[i] and dict["temperature"] < temperature_range[i+1]:
                pixel[i] = color
                break
            pixel[i] = color

    elif current_parameter == 1:
        for i in range(7):
            if dict["humidity"] > humidity_range[i] and dict["humidity"] < humidity_range[i+1]:
                pixel[i] = color
                break
            pixel[i] = color

    elif current_parameter == 2:
        for i in range(7):
            if dict["pressure"] > pressure_range[i] and dict["pressure"] < pressure_range[i+1]:
                pixel[i] = color
                break
            pixel[i] = color
    pixel.show()

def buttonPressedCallback(channel):
    global color
    global current_parameter
    current_parameter = (current_parameter + 1) % 3
    print("\nButton connected to GPIO " + str(channel) + " pressed.")
    if current_parameter == 0:
        color = (255, 0, 0)
    elif current_parameter == 1:
        color = (0, 255, 0)
    elif current_parameter == 2:
        color = (0, 0, 255)
    


if __name__ == "__main__":
    pixels = neopixel.NeoPixel(board.D18, 8, brightness=1.0/32, auto_write=False)
    bme1 = bme280_config()
    GPIO.add_event_detect(buttonRed, GPIO.FALLING, callback=buttonPressedCallback, bouncetime=200)
    
    while(True):
        parameters =  bme280_read(bme1)
        diode_configuration(pixels, parameters)
    
