# Imagen base ligera de Python
FROM python:3.10-slim

# Directorio de trabajo
WORKDIR /app

# Copiar dependencias si existen
COPY requirements.txt /app/

# Instalar dependencias
RUN pip install --no-cache-dir -r requirements.txt

# Copiar todo el código
COPY . /app

# Variable de entorno por defecto
ENV FILENAME=parking_slots.json

# Ejecutar tu simulador
CMD ["sh", "-c", "python iot_simulator.py --file $FILENAME"]
