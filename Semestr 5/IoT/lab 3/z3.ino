#include <LiquidCrystal_I2C.h>

#define LED_RED 6

#define DEBOUNCE_PERIOD 10UL

#define RED_BUTTON 2
#define GREEN_BUTTON 4

LiquidCrystal_I2C lcd(0x27, 16, 2);

int current_time = 0;
bool running = true;

void initButtons() {
  pinMode(LED_RED, OUTPUT);

  pinMode(RED_BUTTON, INPUT_PULLUP);
  pinMode(GREEN_BUTTON, INPUT_PULLUP);
}

bool isGreenButtonPressed() 
{
  static int debounced_button_state = HIGH;
  static int previous_reading = HIGH;
  static unsigned long last_change_time = 0UL;
  bool isPressed = false;

  int current_reading = digitalRead(GREEN_BUTTON);

  if (previous_reading != current_reading) 
  {
    last_change_time = millis();
  }

  if (millis() - last_change_time > DEBOUNCE_PERIOD) 
  {
    if (current_reading != debounced_button_state) 
    {
      if (debounced_button_state == HIGH && current_reading == LOW) 
      {
        isPressed = true;
      }
      debounced_button_state = current_reading;
    }
  }

  previous_reading = current_reading;

  return isPressed;
}

bool isRedButtonPressed() 
{
  static int debounced_button_state = HIGH;
  static int previous_reading = HIGH;
  static unsigned long last_change_time = 0UL;
  bool isPressed = false;

  int current_reading = digitalRead(RED_BUTTON);

  if (previous_reading != current_reading) 
  {
    last_change_time = millis();
  }

  if (millis() - last_change_time > DEBOUNCE_PERIOD) 
  {
    if (current_reading != debounced_button_state) 
    {
      if (debounced_button_state == HIGH && current_reading == LOW) 
      {
        isPressed = true;
      }
      debounced_button_state = current_reading;
    }
  }

  previous_reading = current_reading;

  return isPressed;
}

void reset() 
{
  current_time = 0;
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print(current_time);
}

void run() 
{
  if (millis() % 1000 == 0) 
  {
    current_time++;
    lcd.setCursor(0, 0);
    lcd.print(current_time);
  }

}

void setup() {
  initButtons();

  lcd.init();
  lcd.backlight();

  lcd.setCursor(0, 0);
  digitalWrite(LED_RED, LOW);
}

void loop() {

  if (running)
  {
    run();
    if (isGreenButtonPressed())
    {
      running = false;
    }     
  }
  else if (!running)
  {
    if (isGreenButtonPressed())
    {
      running = true;        
    }  
  }
  if (isRedButtonPressed())
  {
    reset();
    running = false;
  }   

  digitalWrite(LED_RED, digitalRead(GREEN_BUTTON));
}