#define LED_RED 6
#define LED_GREEN 5
#define LED_BLUE 3

#define RED_BUTTON 2
#define GREEN_BUTTON 4

void initRGB() 
{
  pinMode(LED_RED, OUTPUT);
  pinMode(LED_GREEN, OUTPUT);
  pinMode(LED_BLUE, OUTPUT);
  digitalWrite(LED_RED, LOW);
  digitalWrite(LED_GREEN, LOW);
  digitalWrite(LED_BLUE, LOW);
}

void initButtons() 
{
  pinMode(RED_BUTTON, INPUT_PULLUP);
  pinMode(GREEN_BUTTON, INPUT_PULLUP);
}

void setup() {
  initRGB();
  initButtons();

  Serial.begin(9600);

  while (!Serial) 
  { /* just wait */
  }
}

void loop() 
{
  Serial.println(String(digitalRead(RED_BUTTON)) + " " + String(digitalRead(GREEN_BUTTON)));

  if (Serial.available() > 0) 
  {
    String command = Serial.readStringUntil('\n');
    command.trim();
    command.toLowerCase();

    if (command.length() > 9) 
    {
      Serial.println(String("Unknown command ’") + command + "’");
    } 
    else if (command.startsWith("red")) 
    {
      if (command.endsWith("on"))
      {
        digitalWrite(LED_RED, HIGH);
      }
      else if (command.endsWith("off"))
      {
        digitalWrite(LED_RED, LOW);
      }
      else
      {
        Serial.println(String("Unknown command ’") + command + "’");
      }
    } 
    else if (command.startsWith("green")) 
    {
      if (command.endsWith("on"))
      {
        digitalWrite(LED_GREEN, HIGH);
      }
      else if (command.endsWith("off"))
      {
        digitalWrite(LED_GREEN, LOW);
      }
      else
      {
        Serial.println(String("Unknown command ’") + command + "’");
      }
    }
    else if (command.startsWith("blue")) 
    {
      if (command.endsWith("on"))
      {
        digitalWrite(LED_BLUE, HIGH);
      }
      else if (command.endsWith("off"))
      {
        digitalWrite(LED_BLUE, LOW);
      }
      else
      {
        Serial.println(String("Unknown command ’") + command + "’");
      }
    }
    else 
    {
      Serial.println(String("Unknown command ’") + command + "’");
    }
  }
}