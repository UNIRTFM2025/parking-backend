import json
import time
import random
import paho.mqtt.client as mqtt

# Configuración del broker MQTT
broker = "broker.hivemq.com"
port = 1883
topic = "iot/parking/slot"

# Cargar los datos desde parking_slots.json
with open("parking_slots.json", "r", encoding="utf-8") as f:
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