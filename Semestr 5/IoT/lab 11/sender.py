#!/usr/bin/env python3

# pylint: disable=no-member

import time
from tkinter.tix import DirTree
import RPi.GPIO as GPIO
from config import *  # pylint: disable=unused-wildcard-import
from mfrc522 import MFRC522
from datetime import datetime
import paho.mqtt.client as mqtt
import tkinter

executing = True


terminal_id = "T0"
broker = "localhost"
client = mqtt.Client()


def buttonPressedCallback(channel):
    global executing
    executing = False

def buzzer_state(state):
     GPIO.output(buzzerPin, not state)  # pylint: disable=no-member

def buzzer():
    buzzer_state(True)
    time.sleep(1)
    buzzer_state(False)

def blink():
    GPIO.output(led1, GPIO.HIGH)
    time.sleep(1)
    GPIO.output(led1, GPIO.LOW)
    time.sleep(1)

def rfidRead():
    global executing
    MIFAREReader = MFRC522()
    last_scan = datetime.timestamp(datetime.now()) - 3
    while executing:
        
        if datetime.timestamp(datetime.now()) - last_scan > 3.0:
            (status, TagType) = MIFAREReader.MFRC522_Request(MIFAREReader.PICC_REQIDL)
            if status == MIFAREReader.MI_OK:
                (status, uid) = MIFAREReader.MFRC522_Anticoll()
                if status == MIFAREReader.MI_OK:
                    dt = datetime.now()
                    num = 0
                    for i in range(0, len(uid)):
                        num += uid[i] << (i*8)
                    print(f"Card read UID: {num}")
                    print(f"Date and time of scanning: {dt}")
                    call_worker(num, dt)
                    buzzer()
                    blink()
                    last_scan = datetime.timestamp(datetime.now())

def call_worker(worker_id, dt):
   client.publish("worker/card", str(worker_id) + " - " + str(dt))

def connect_to_broker():
   client.connect(broker)
   call_worker("Client connected", datetime.now())

def disconnect_from_broker():
   call_worker("Client disconnected", datetime.now())
   client.disconnect()


def test():
    GPIO.add_event_detect(buttonRed, GPIO.FALLING, callback=buttonPressedCallback, bouncetime=200)

    print('Place the card close to the reader (on the right side of the set).')
    connect_to_broker()
    rfidRead()
    disconnect_from_broker()



if __name__ == "__main__":
    test()
    GPIO.cleanup()  # pylint: disable=no-member