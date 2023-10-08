#define LED_GREEN 5

#define RED_BUTTON 2
#define GREEN_BUTTON 4

void initRGB()
{
  pinMode(LED_GREEN, OUTPUT);
  analogWrite(LED_GREEN, 255);
}

void initButtons()
{
  pinMode(RED_BUTTON, INPUT_PULLUP);
  pinMode(GREEN_BUTTON, INPUT_PULLUP);
}

void setup()
{
  initRGB();
  initButtons();
}

int intensity = 255;

void loop()
{
  if (digitalRead(RED_BUTTON) == LOW && intensity > 0)
  {
    intensity--;
    analogWrite(LED_GREEN, intensity);
    delay(20);
  }

  if (digitalRead(GREEN_BUTTON) == LOW && intensity < 255)
  {
    intensity++;
    analogWrite(LED_GREEN, intensity);
    delay(20);
  }
}

