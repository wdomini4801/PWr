#!/usr/bin/env python3

from config import *
import w1thermsensor
import time
import os
import neopixel
import board
import busio
import adafruit_bme280.advanced as adafruit_bme280
from PIL import Image, ImageDraw, ImageFont
import lib.oled.SSD1331 as SSD1331

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
    return {"temperature" : round(bme.temperature, 2), "humidity" : round(bme.humidity, 2), "pressure" : round(bme.pressure, 2)}

def display(parameters):
    disp = SSD1331.SSD1331()

    disp.Init()

    image1 = Image.new("RGB", (disp.width, disp.height), "WHITE")
    draw = ImageDraw.Draw(image1)
    fontLarge = ImageFont.truetype('./lib/oled/Font.ttf', 20)
    fontSmall = ImageFont.truetype('./lib/oled/Font.ttf', 8)

    image_temperature = Image.open('./pictures/temperature.jpg')
    image_temperature = image_temperature.resize((15, 10))
    image_humidity = Image.open('./pictures/humidity.png')
    image_humidity = image_humidity.resize((15, 10))
    image_pressure = Image.open('./pictures/pressure.png')
    image_pressure = image_pressure.resize((15, 10))

    image1.paste(image_temperature, (0, 0))
    draw.text((17,0), f'Temperature: {parameters["temperature"]}', font=fontSmall, fill="BLACK")

    image1.paste(image_humidity, (0, 25))
    draw.text((17,25), f'Humidity: {parameters["humidity"]}', font=fontSmall, fill="BLACK")

    image1.paste(image_pressure, (0, 50))
    draw.text((17,50), f'Pressure: {parameters["pressure"]}', font=fontSmall, fill="BLACK")
    
    disp.ShowImage(image1, 0, 0)




if __name__ == "__main__":
    pixels = neopixel.NeoPixel(board.D18, 8, brightness=1.0/32, auto_write=False)
    bme1 = bme280_config()
    
    while(True):
        parameters = bme280_read(bme1)
        display(parameters)
    disp.clear()
