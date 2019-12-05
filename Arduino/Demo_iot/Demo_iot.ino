#include <FirebaseArduino.h>
#include <FirebaseCloudMessaging.h>
#include <FirebaseError.h>
#include <FirebaseHttpClient.h>
#include <FirebaseObject.h>

#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>

#include <OneWire.h>                
#include <DallasTemperature.h>

#define FIREBASE_HOST "cocinainteligenteiot.firebaseio.com"
#define FIREBASE_AUTH "yTAiKCZDy4uymwsR9HiVelNcHCGnbu0rUWjxXSH5"
#define WIFI_SSID "SARMIENTO"
#define WIFI_PASSWORD "OACFJINX"

const int GAS = 13; // d7
int gas_lectura;

const int PIR = 12;  //d6
int pir_lectura;

const int TEMP = 14; //d5
float temp_lectura;
OneWire oneWire(TEMP);
DallasTemperature temp(&oneWire);

void crear_accion();

void setup() {
  Serial.begin(9600);


  //  componentes
    pinMode(GAS, INPUT);
    pinMode(PIR, INPUT);
    pinMode(TEMP, INPUT);
    
    temp.begin(); // Inicializa el sensor

  // connect to wifi.
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("connected: ");
  Serial.println(WiFi.localIP());
  
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);

}


void loop() {    
    
      //captura de datos
      gas_lectura =analogRead(GAS); // Mide el gas
      pir_lectura =digitalRead(PIR); //Muestra 0 (sin gente), 1 (con gente)  
      temp.requestTemperatures();   //Se envía el comando para leer la temperatura
      temp_lectura = temp.getTempCByIndex(0); //Se obtiene la temperatura en ºC
      crear_accion( gas_lectura, pir_lectura, temp_lectura); 
      delay(999);
}
