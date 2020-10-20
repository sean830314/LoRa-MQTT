#include <SPI.h>
#include <LoRa.h>

int counter = 0;
long randNumber;
void setup() {
  Serial.begin(9600);
  while (!Serial);
 // LoRa.setPins(D8,D3,D2);
  Serial.println("LoRa Sender");
//LoRa.begin(433E6);
 Serial.println("TTT");
  if (!LoRa.begin(433E6)) {
    Serial.println("Starting LoRa failed!");
    while (1){Serial.println("GG");yield();};
  }
  Serial.println("end");
}

void loop() {
  Serial.print("Sending packet: ");
  randNumber = random(10, 200);
  Serial.println((int)randNumber);

  // send packet
  LoRa.beginPacket();
  LoRa.print("hello ");
  LoRa.print((String)randNumber);
  LoRa.endPacket();
  Serial.println("send ok ");
  counter++;

  delay(5000);
}
