/*
 * Esta versión funciona nitido con 3 sensores: Temperatura Corporal, Frecuencia Respiratoria,
 * SpO2 = Pulso y Saturación Parcial del Oxígeno. Todo probado a una velocidad de 115200 baudios.
 * MEGATEC-Zacatecoluca ~ HOSPITAL SANTA TERESA.
 * Autor: Manuel Gámez
 * Fecha: 01/01/2020.
 * sketch de prueba para comunicación serial por dispositivo Bluetooth.
 * Resultado: 
 *            Funciona perfectamente la comunicación
 *            Bidereccional con el modulo BT y los caracteres de prueba.
 *            COMANDOS UTILIZADOS EN EL SKETCH:
 *            R: Para pedir o iniciar el envio de datos captados o procesados por el sensor.
 *            F: para parar o detener el envio de datos.
*/

#include <MySignals.h>
#include "Wire.h"
#include "SPI.h" 

extern volatile unsigned long timer0_overflow_count;
float fanalog0;
int analog0;
unsigned long time;
byte serialByte;

int Alarma=0;
//Variable donde almacenaremos el numero aleatorio
int randomNumber;
float temperature = 0.0; //air=0;

//Inicio Variables Sensor SPO2"
int valuePulse;
int valueSPO2;
uint8_t pulsioximeter_state = 0;
//Fin variables
void setup()
{
  MySignals.begin();  
  //Serial.begin(9600);
  //Serial.begin(19200);  //Sensor SpO2 Oximetria funciona a 2 velocidades.
  Serial.begin(115200);   //A esta velocidad trabajan bien los 2 sensores UART
                          //Oximetria y Tensión Arterial. 
                          //OJO: El sensor de Tensión Arterial solo funciona a velocidad de 115200 baudios.
                          //     por tal razón hay que trabajar a esta ya que tambien lo permiten los demás sensores.
  Serial1.begin(9600);    //Bluetooth
  Serial2.begin(9600);    //FTDI = USB TIPO A MACHO.

  randomSeed(500);

  //Var SPO2
    MySignals.initSensorUART();
    MySignals.enableSensorUART(PULSIOXIMETER);
  //Fin Var
  
  Serial2.println("Starting...");
}

void loop() 
{  
  Alarma=1;
  //Genera un numero aleatorio entre 1 y 1023
  //randomNumber = random(1,1023);

  while (Serial1.available()>0) 
  {
    serialByte = Serial1.read();
    if (serialByte == 'R')
    {
      while(1) 
      {
        //Sensor para variable biométrica AirFlow o Flujo de Aire
        //uint16_t air = (uint16_t)MySignals.getAirflow(DATA);   
        //Serial.println("Flujo de Aire: ");
        //Serial.println(air);
        //delay(20);

        randomNumber = random(1,1023);

        /*
         * uint16_t air = (uint16_t)MySignals.getAirflow(DATA);   
         * Serial.println(air);  
         * delay(20);
         */

        //uint16_t air = (uint16_t)MySignals.getAirflow(DATA);   
        fanalog0 = MySignals.getAirflow(DATA);  
        
        // Use the timer0 => 1 tick every 4 us
        time = (timer0_overflow_count << 8) + TCNT0;
        
        // Microseconds conversion.
        time = time*4;
        
        //Print in a file for simulation
        /*
          Serial1.print(time);
          Serial1.print(";");
          Serial1.println(fanalog0,5);
        */

        Serial1.print(0);                //SpO2 = Saturación Parcial del Oxígeno y Pulso/Frecuencia Cardiaca.
        Serial1.print(",");
        Serial1.print(randomNumber);     //Serial.print(Frec_cardiaca);
        Serial1.print(",");
        Serial1.print(0);                //Tensión Arterial
        Serial1.print(",");
        Serial1.print(fanalog0);              //Frecuencia Respiratoria 
        Serial1.print(",");
        Serial1.print(temperature, 2);   //Temperatura Corporal
        Serial1.print(",");
        Serial1.print(Alarma);           //Alarma
        Serial1.print(",");
        Serial1.println("~");
        Serial1.flush();
      
        //Envio los datos de sensores por interface FTDI  a USB    
        Serial2.print(0);                //SpO2 = Saturación Parcial del Oxígeno y Pulso/Frecuencia Cardiaca.
        Serial2.print(",");
        Serial2.print(randomNumber);     //Serial.print(Frec_cardiaca);
        Serial2.print(",");
        Serial2.print(0);                //Tensión Arterial
        Serial2.print(",");
        Serial2.print(fanalog0);              //Frecuencia Respiratoria
        Serial2.print(",");
        Serial2.print(temperature, 2);   //Temperatura Corporal
        Serial2.print(",");
        Serial2.print(Alarma);           //Alarma
        Serial2.print(",");
        Serial2.println("~");
        Serial2.flush();
        
        if (Serial1.available()>0)
        {
          serialByte = Serial1.read();
          if (serialByte == 'F') break;
        }

        delay(20);
      }
    }

    //Sensor de Temperatura Corporal.
    //serialByte = Serial1.read();
    if (serialByte == 'T')
    {
      while(1) 
      {
        randomNumber = random(1,1023);
        float temperature = MySignals.getCalibratedTemperature(100, 10, -2, 5);
        //Envio los datos de sensores por Bluetooth    
        Serial1.print(0);                //SpO2 = Saturación Parcial del Oxígeno y Pulso/Frecuencia Cardiaca.
        Serial1.print(",");
        Serial1.print(randomNumber);     //Serial.print(Frec_cardiaca);
        Serial1.print(",");
        Serial1.print(0);                //Tensión Arterial
        Serial1.print(",");
        Serial1.print(0);                //Frecuencia Respiratoria 
        Serial1.print(",");
        Serial1.print(temperature, 2);   //Temperatura Corporal
        Serial1.print(",");
        Serial1.print(Alarma);           //Alarma
        Serial1.print(",");
        Serial1.println("~");
        Serial1.flush();
      
        //Envio los datos de sensores por interface FTDI  a USB    
        Serial2.print(0);                //SpO2 = Saturación Parcial del Oxígeno y Pulso/Frecuencia Cardiaca.
        Serial2.print(",");
        Serial2.print(randomNumber);     //Serial.print(Frec_cardiaca);
        Serial2.print(",");
        Serial2.print(0);                //Tensión Arterial
        Serial2.print(",");
        Serial2.print(0);                //Frecuencia Respiratoria
        Serial2.print(",");
        Serial2.print(temperature, 2);   //Temperatura Corporal
        Serial2.print(",");
        Serial2.print(Alarma);           //Alarma
        Serial2.print(",");
        Serial2.println("~");
        Serial2.flush();

        if (Serial1.available()>0)
        {
          serialByte = Serial1.read();
          if (serialByte == 'C') break;
        }

         delay(1000);  //
      }
    }


    //Sensor Oximetria = Sensor de pulso  y oxígeno en la sangre.
    //Sensor Oximetria = Frecuencia Cardiaca y SpO2 (Saturación Parcial del Oxígeno).
    //serialByte = Serial1.read();
    if (serialByte == 'O')
    {
      while(1) 
      {
        //BEGIN SPO2
         if (MySignals.spo2_micro_detected == 0 && MySignals.spo2_mini_detected == 0){
            uint8_t statusPulsioximeter = MySignals.getStatusPulsioximeterGeneral();
        
        if (statusPulsioximeter == 1){
            MySignals.spo2_mini_detected = 0;
            MySignals.spo2_micro_detected = 1;
            //tft.drawString("Micro detected", 0, 100, 2);            //Mi conector de sensores....
          }else if (statusPulsioximeter == 2){
            MySignals.spo2_mini_detected = 1;
            MySignals.spo2_micro_detected = 0;
            //tft.drawString("Mini detected", 0, 100, 2);
          }else{
            MySignals.spo2_micro_detected = 0;
            MySignals.spo2_mini_detected = 0;
          }
        }
      
        if(MySignals.spo2_micro_detected == 1){
          MySignals.enableSensorUART(PULSIOXIMETER_MICRO);
          delay(10);
          uint8_t getPulsioximeterMicro_state = MySignals.getPulsioximeterMicro();
          if (getPulsioximeterMicro_state == 1){
              /*
              tft.drawNumber(MySignals.pulsioximeterData.BPM, 0, 60, 2);
              tft.drawString("PRbpm", 25, 65, 2); 
              tft.drawNumber(MySignals.pulsioximeterData.O2, 0, 75, 2);
              tft.drawString("%SpO2", 25, 80, 2); 
              */

              randomNumber = random(1,1023);
              //float temperature = MySignals.getCalibratedTemperature(100, 10, -2, 5);
              //Envio los datos de sensores por Bluetooth    
              Serial1.print(MySignals.pulsioximeterData.O2);                //SpO2 = Saturación Parcial del Oxígeno y Pulso/Frecuencia Cardiaca.
              Serial1.print(",");
              Serial1.print(MySignals.pulsioximeterData.BPM);               //Serial.print(Frec_cardiaca);
              Serial1.print(",");
              Serial1.print(0);                //Tensión Arterial
              Serial1.print(",");
              Serial1.print(0);                //Frecuencia Respiratoria 
              Serial1.print(",");
              Serial1.print(0);                //Temperatura Corporal
              Serial1.print(",");
              Serial1.print(Alarma);           //Alarma
              Serial1.print(",");
              Serial1.println("~");
              Serial1.flush();
            
              //Envio los datos de sensores por interface FTDI  a USB    
              Serial2.print(MySignals.pulsioximeterData.O2);                //SpO2 = Saturación Parcial del Oxígeno y Pulso/Frecuencia Cardiaca.
              Serial2.print(",");
              Serial2.print(MySignals.pulsioximeterData.BPM);               //Serial.print(Frec_cardiaca);
              Serial2.print(",");
              Serial2.print(0);                //Tensión Arterial
              Serial2.print(",");
              Serial2.print(0);                //Frecuencia Respiratoria
              Serial2.print(",");
              Serial2.print(0);                //Temperatura Corporal
              Serial2.print(",");
              Serial2.print(Alarma);           //Alarma
              Serial2.print(",");
              Serial2.println("~");
              Serial2.flush();
              
            }else if (getPulsioximeterMicro_state == 2){
              //Serial.println(F("Finger out or calculating"));
              Serial2.println(F("X"));     //Perdiendo la captura de datos del sensor. Se quito el dedo...
              
              //Serial1.println(F("0"));     //Mando centinela a la App para saber que se ha quitado el dedo del sensor.
              Serial1.print(0);              //SpO2 = Saturación Parcial del Oxígeno y Pulso/Frecuencia Cardiaca.
              Serial1.print(",");
              Serial1.print(0);              //Serial.print(Frec_cardiaca);
              Serial1.print(",");
              Serial1.print(0);              //Tensión Arterial
              Serial1.print(",");
              Serial1.print(0);              //Frecuencia Respiratoria
              Serial1.print(",");
              Serial1.print(0);              //Temperatura Corporal
              Serial1.print(",");
              Serial1.print(Alarma);         //Alarma
              Serial1.print(",");
              Serial1.println("~");
              Serial1.flush();
            }else{
              MySignals.spo2_micro_detected = 0;
              //Serial.println(F("SPO2 Micro lost connection"));
              Serial2.println(F("Y"));     //Se perdio por completo la captura de datos.             
            }
          }else{
            Serial2.println(F("Error de Lectura de datos: ...."));
            //Serial2.println(F("1. El sensor de oximetria esta desconectado de la tarjeta MySignals"));
            //Serial2.println(F("2. El sensor de oximetria esta conectado pero no ha colocado su dedo indice en el."));
            //Serial1.println(F("0"));     //Mando centinela a la App para saber que se ha quitado el dedo del sensor.
              Serial1.print(0);              //SpO2 = Saturación Parcial del Oxígeno y Pulso/Frecuencia Cardiaca.
              Serial1.print(",");
              Serial1.print(0);              //Serial.print(Frec_cardiaca);
              Serial1.print(",");
              Serial1.print(0);              //Tensión Arterial
              Serial1.print(",");
              Serial1.print(0);              //Frecuencia Respiratoria
              Serial1.print(",");
              Serial1.print(0);              //Temperatura Corporal
              Serial1.print(",");
              Serial1.print(Alarma);         //Alarma
              Serial1.print(",");
              Serial1.println("~");
              Serial1.flush();
          }
        //END SPO2  

        if (Serial1.available()>0)
        {
          serialByte = Serial1.read();
          if (serialByte == 'S') break;
        }

        delay(1000);
      }
    }


    //Sensor Tensión/Presión Arterial.
    //serialByte = Serial1.read();
    if (serialByte == 'T')
    {
      while(1) 
      {

        if (Serial1.available()>0)
        {
          serialByte = Serial1.read();
          if (serialByte == 'A') break;
        }

      }
    }

    

    
  }//Fin del bucle while principal    
}//Fin del loop
