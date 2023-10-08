#define LED_RED 6
#define LED_GREEN 5
#define LED_BLUE 3

void setup()
{
  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, LOW);

  pinMode(LED_RED, OUTPUT);
  analogWrite(LED_RED, 255);

  pinMode(LED_GREEN, OUTPUT);
  analogWrite(LED_GREEN, 0);

  pinMode(LED_BLUE, OUTPUT);
  analogWrite(LED_BLUE, 0);  
}

void increase(int diod)
{
  for (int i=0; i<256; i++)
  {
    analogWrite(diod, i);
    delay(5);
  }
}

void decrease(int diod)
{
  for (int i=255; i>0; i--)
  {
    analogWrite(diod, i);
    delay(5);
  }
}

void loop()
{
  increase(LED_GREEN);
  decrease(LED_RED);
  increase(LED_BLUE);
  decrease(LED_GREEN);
  increase(LED_RED);
  decrease(LED_BLUE);
}
