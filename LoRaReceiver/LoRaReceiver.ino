#include <SPI.h>
#include <LoRa.h>
//String str="";
void setup() {
  Serial.begin(9600);
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
  int packetSize = LoRa.parsePacket();
  //yield();
  if (packetSize) {
    //yield();
    Serial.print("Received packet '");
    // read packet
    while (LoRa.available()) {
      //yield();
      Serial.print((char)LoRa.read());
    // str+=(char)LoRa.read();
    }
//Serial.print(str);
    // print RSSI of packet
    Serial.print("' with RSSI ");
    Serial.println(LoRa.packetRssi());
  }
  //delay(5000);
}
