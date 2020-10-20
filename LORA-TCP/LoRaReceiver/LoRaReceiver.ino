#include <SPI.h>
#include <LoRa.h>
#include <ESP8266WiFi.h>
WiFiClient client;
char* SID="<SID>";
char* PWD="<PWD>";
char* IP="<ip>";
uint16_t port=8087;
String str="";
void init_wifi(){
    WiFi.begin(SID, PWD);
    while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
   Serial.print("CONNECTED OK");
}
void send_data(){
   Serial.println("client.connect OK");
      String getStr = "GET /?id=jimmy";
      getStr +=" HTTP/1.1\r\nHost:"+(String)IP;
      getStr += "\r\n\r\n";
      String message="id="+str;
      Serial.println("---------------------------------------------");
      Serial.println(message);
      Serial.println("---------------------------------------------");
      client.print(message);
      delay(2000);  
  }
void uploadData(){
   if (client.connect(IP,port)) {
      send_data();
  }
  else{
    Serial.println("client.connect erro");
    init_wifi();
     if(client.connect(IP,port)){
      send_data();
      }
    }
}
void setup() {
  Serial.begin(9600);
   init_wifi();
  while (!Serial);
  LoRa.setPins(D8,D3,D2);
  Serial.println("LoRa Receiver");

  if (!LoRa.begin(433E6)) {
    Serial.println("Starting LoRa failed!");
    while (1){Serial.println("GG");yield();};
  }
}

void loop() {
 // yield();
  // try to parse packet
  str="";
  int packetSize = LoRa.parsePacket();
  //yield();
  if (packetSize) {
    //yield();
    Serial.print("Received packet '");
    // read packet
    while (LoRa.available()) {
      //yield();
      //Serial.print((char)LoRa.read());
      str+=(char)LoRa.read();
    }
    Serial.print(str);
    // print RSSI of packet
    Serial.print("' with RSSI ");
    Serial.println(LoRa.packetRssi());
    uploadData();
  }
  //delay(5000);
}
