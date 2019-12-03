void crear_accion(int gas_lectura, int pir_lectura, float temp_lectura) {
    StaticJsonBuffer<200> jsonBuffer;
    JsonObject& root = jsonBuffer.createObject();
    root["pir"] = pir_lectura;
    root["gas"] = gas_lectura;
    root["temp"] = temp_lectura;
    String name = Firebase.push("/accion", root);
     
    //error
    if (Firebase.failed()) {
       Serial.print("Firebase Pushing /accion failed:");
       Serial.println(Firebase.error()); 
       delay(1000);             
       return;
    }else{
       Serial.print("Firebase Pushed /accion ");
       Serial.println(name);
       delay(1000); 
    }
}

/*
bool en_accion() {
FirebaseObject comida = Firebase.get("Comida1");
    if (Firebase.failed()) {
      Serial.println("Firebase get failed");
      Serial.println(Firebase.error());
    }
  //lee los datos de comida1
 // ver si accion es 0 o 1
 // enviar respuesta
}
*/
