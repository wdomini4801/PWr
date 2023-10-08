#!/usr/bin/env python3

import paho.mqtt.client as mqtt
import time

# The broker name or IP address.
broker = "localhost"
# broker = "127.0.0.1"
# broker = "10.0.0.1"

# The MQTT client.
client = mqtt.Client()

def process_message(client, userdata, message):
    # Decode message.
    message_decoded = str(message.payload.decode("utf-8"))
    print(message_decoded)
    
    # Print message to console.
    # if message_decoded[0] != "Client connected" and message_decoded[0] != "Client disconnected":
    #     print(time.ctime() + ", " +
    #           message_decoded[0] + " used the RFID card.")
    # else:
    #     print(message_decoded[0] + " : " + message_decoded[1])

def connect_to_broker():
    # Connect to the broker.
    client.connect(broker)
    # Send message about conenction.
    client.on_message = process_message
    # Starts client and subscribe.
    client.subscribe("worker/card")
    while client.loop() == 0:
        pass


def disconnect_from_broker():
    # Disconnet the client.
    client.loop_stop()
    client.disconnect()


def run_receiver():
    connect_to_broker()
    #create_main_window()
    # Start to display window (It will stay here until window is displayed)
    #window.mainloop()
    disconnect_from_broker()


if __name__ == "__main__":
    run_receiver()
