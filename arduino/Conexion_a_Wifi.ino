#include <WiFi.h>
#include <WiFiClientSecure.h>
#include <HTTPClient.h>
#include <math.h>

const char* WIFI_SSID     = "INFINITUMCBB8_2.4";
const char* WIFI_PASSWORD = "6330274593";

const char* API_URL = "https://api.tankr.pjasoft.com/api/readings";
const char* SERIAL_NUMBER = "TKR-BROWN-002";

const int POT_PIN = 34;

const unsigned long SEND_INTERVAL_MS = 5000;
unsigned long lastSend = 0;

void connectWifi() {
  Serial.printf("Conectando a WiFi \"%s\"", WIFI_SSID);

  WiFi.mode(WIFI_STA);
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.printf("\nConectado. IP: %s\n",
                WiFi.localIP().toString().c_str());
}

float readTankLevel() {
  long suma = 0;

  // Promedio de 10 lecturas para reducir ruido
  for (int i = 0; i < 10; i++) {
    suma += analogRead(POT_PIN);
    delay(5);
  }

  int raw = suma / 10;

  // Convertir ADC (0-4095) a litros (0-1000)
  float liters = (raw / 4095.0f) * 1000.0f;

  // Redondear al múltiplo de 10 más cercano
  liters = round(liters / 10.0f) * 10.0f;

  // Limitar rango
  if (liters < 0) liters = 0;
  if (liters > 1000) liters = 1000;

  Serial.print("ADC: ");
  Serial.print(raw);
  Serial.print(" | Litros: ");
  Serial.println(liters);

  return liters;
}

void sendReading(float liters) {
  if (WiFi.status() != WL_CONNECTED) {
    Serial.println("WiFi desconectado, reconectando...");
    connectWifi();
  }

  WiFiClientSecure client;
  client.setInsecure();

  HTTPClient http;

  if (!http.begin(client, API_URL)) {
    Serial.println("Error: http.begin()");
    return;
  }

  http.addHeader("Content-Type", "application/json");

  char body[128];

  snprintf(
    body,
    sizeof(body),
    "{\"serialNumber\":\"%s\",\"liters\":%d}",
    SERIAL_NUMBER,
    (int)liters
  );

  Serial.println(body);

  int code = http.POST((uint8_t*)body, strlen(body));

  if (code > 0) {
    String resp = http.getString();

    Serial.print("HTTP ");
    Serial.println(code);

    Serial.println(resp);
  } else {
    Serial.print("Error POST: ");
    Serial.println(http.errorToString(code));
  }

  http.end();
}

void setup() {
  Serial.begin(115200);
  delay(1000);

  analogReadResolution(12);

  Serial.println("\n== Tankr ESP32 Potentiometer Sender ==");

  connectWifi();
}

void loop() {
  unsigned long now = millis();

  if (now - lastSend >= SEND_INTERVAL_MS) {
    lastSend = now;

    float liters = readTankLevel();

    sendReading(liters);
  }
}