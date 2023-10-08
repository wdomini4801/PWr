#define LED_RED 6
#define LED_GREEN 5
#define LED_BLUE 3

void setup()
{
  pinMode(LED_RED, OUTPUT);
  digitalWrite(LED_RED, LOW);

  pinMode(LED_GREEN, OUTPUT);
  digitalWrite(LED_GREEN, LOW);

  pinMode(LED_BLUE, OUTPUT);
  digitalWrite(LED_BLUE, LOW);  
}

void blinkLedRed()
{
  const unsigned long BlinkChangePeriod = 900UL;
  static int diodState = LOW;

  static unsigned long lastBlinkChange = 0UL;

  if (millis() - lastBlinkChange >= BlinkChangePeriod)
  {
    if (diodState == HIGH)
    {
      diodState = LOW;
    }
    else
    {
      diodState = HIGH;
    }

    digitalWrite(LED_RED, diodState);
    lastBlinkChange += BlinkChangePeriod;
  }
}

void blinkLedGreen()
{
  const unsigned long BlinkChangePeriod = 1000UL;
  static int diodState = LOW;

  static unsigned long lastBlinkChange = 0UL;

  if (millis() - lastBlinkChange >= BlinkChangePeriod)
  {
    if (diodState == HIGH)
    {
      diodState = LOW;
    }
    else
    {
      diodState = HIGH;
    }

    digitalWrite(LED_GREEN, diodState);
    lastBlinkChange += BlinkChangePeriod;
  }
}

void blinkLedBlue()
{
  const unsigned long BlinkChangePeriod = 1100UL;
  static int diodState = LOW;

  static unsigned long lastBlinkChange = 0UL;

  if (millis() - lastBlinkChange >= BlinkChangePeriod)
  {
    if (diodState == HIGH)
    {
      diodState = LOW;
    }
    else
    {
      diodState = HIGH;
    }

    digitalWrite(LED_BLUE, diodState);
    lastBlinkChange += BlinkChangePeriod;
  }
}


void loop()
{
  blinkLedRed();
  blinkLedGreen();
  blinkLedBlue();
}
