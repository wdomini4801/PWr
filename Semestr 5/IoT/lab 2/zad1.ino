#define LED_RED 6
#define LED_GREEN 5
#define LED_BLUE 3

#define RED_BUTTON 2
#define GREEN_BUTTON 4

void initRGB()
{
  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, LOW);

  pinMode(LED_RED, OUTPUT);
  digitalWrite(LED_RED, LOW);

  pinMode(LED_GREEN, OUTPUT);
  digitalWrite(LED_GREEN, LOW);

  pinMode(LED_BLUE, OUTPUT);
  digitalWrite(LED_BLUE, LOW);
}

void initButtons()
{
  pinMode(RED_BUTTON, INPUT_PULLUP);
  pinMode(GREEN_BUTTON, INPUT_PULLUP);
}

bool on = false;
int current_color = 0;

void setup()
{
  initRGB();
  initButtons();  
}

void loop()
{
  if (digitalRead(RED_BUTTON) == LOW)
  {
    if (!on)
      {
        digitalWrite(LED_RED, HIGH);
        current_color = LED_RED;
      }
    else
      {
        digitalWrite(LED_RED, LOW);
        digitalWrite(LED_GREEN, LOW);
        digitalWrite(LED_BLUE, LOW);
        current_color = 0;
      }
    on = !on;
  }

  if (digitalRead(GREEN_BUTTON) == LOW)
  {
    if (current_color == LED_RED)
    {
      digitalWrite(LED_RED, LOW);
      digitalWrite(LED_GREEN, HIGH);
      current_color = LED_GREEN;      
    }
    else if (current_color == LED_GREEN)
    {
      digitalWrite(LED_GREEN, LOW);
      digitalWrite(LED_BLUE, HIGH);
      current_color = LED_BLUE;      
    }
    else
    {
      digitalWrite(LED_BLUE, LOW);
      digitalWrite(LED_RED, HIGH);
      current_color = LED_RED;      
    }
  }

}