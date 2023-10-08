#define POTENTIOMETER A0

void setup() 
{
  Serial.begin(9600);
  while(!Serial)
  {    
  }
}

void loop() 
{
  const unsigned long printPeriod = 100UL;
  static unsigned long lastPrint = 0UL;
  
  if (millis() - lastPrint >= printPeriod)
  {
    int value = analogRead(A0);
    value = min(max(0, value - 10), 1003);
    int scaled_value = map(value, 0, 1003, 0, 100);
    Serial.println(scaled_value);  
    lastPrint += printPeriod; 
  }

}