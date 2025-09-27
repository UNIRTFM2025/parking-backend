import json
import time
import random
import paho.mqtt.client as mqtt
import os
import argparse

# Configuración del broker MQTT
broker = "broker.hivemq.com"
port = 1883
topic = "iot/parking/slot"

parser = argparse.ArgumentParser()
parser.add_argument("--file", default=os.getenv("FILENAME", "parking_slots.json"))
args = parser.parse_args()

print(f"✅ Usando archivo: {args.file}")

# Cargar los datos desde parking_slots_maloka.json
with open(args.file, "r", encoding="utf-8") as f:
    parking_slots = json.load(f)

# Crear cliente MQTT y conectar
client = mqtt.Client()
client.connect(broker, port)
print("✅ Conectado al broker MQTT")

# Iterar sobre cada registro, modificar status y publicar
for slot in parking_slots:
    slot["status"] = random.choice([True, False])
    payload = json.dumps(slot)
    client.publish(topic, payload)
    print(f"📤 Enviado: {payload}")
    time.sleep(1)  # Intervalo de 1 segundo entre envíos (ajustable)

print("🚗 Todos los mensajes fueron enviados al topic.")