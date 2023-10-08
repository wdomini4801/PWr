#include <util/atomic.h>
#include <LiquidCrystal_I2C.h>

#define LED_RED 6
#define LED_GREEN 5
#define LED_BLUE 3

#define RED_BUTTON 2

#define ENCODER1 A2
#define ENCODER2 A3

#define DEBOUNCING_PERIOD 100
#define DEBOUNCE_PERIOD 10UL

LiquidCrystal_I2C lcd(0x27, 16, 2);

void printResults(int val) 
{
  char buffer[40];
  sprintf(buffer, "Intensity: %3d", val);
  lcd.setCursor(2, 0);
  lcd.print(buffer);
}

void menu(int val) {
  if (val + 1 == 1) {
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print(">Red diod");
    lcd.setCursor(1, 1);
    lcd.print("Green diod");
  } else if (val + 1 == 2) {
    lcd.clear();
    lcd.setCursor(1, 0);
    lcd.print("Red diod");
    lcd.setCursor(0, 1);
    lcd.print(">Green diod");
  } else {
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print(">Blue diod");
  }
}

void change_intensity(int diod, int val) {
  printResults(val);
  analogWrite(diod, val);
}

void setup() {
  pinMode(LED_RED, OUTPUT);
  digitalWrite(LED_RED, LOW);

  pinMode(LED_GREEN, OUTPUT);
  digitalWrite(LED_GREEN, LOW);

  pinMode(LED_BLUE, OUTPUT);
  digitalWrite(LED_BLUE, LOW);

  pinMode(ENCODER1, INPUT_PULLUP);
  pinMode(ENCODER2, INPUT_PULLUP);

  pinMode(RED_BUTTON, INPUT_PULLUP);

  lcd.init();
  lcd.backlight();
  menu(0);

  PCICR |= (1 << PCIE1);
  PCMSK1 |= (1 << PCINT10);
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

  if (millis() - last_change_time > DEBOUNCE_PERIOD) {
    if (current_reading != debounced_button_state) {
      if (debounced_button_state == HIGH && current_reading == LOW) {
        isPressed = true;
      }
      debounced_button_state = current_reading;
    }
  }

  previous_reading = current_reading;

  return isPressed;
}

int diod_by_option(int option)
{
  if (option == 0)
  {
    return 6;
  }
  else if (option == 1)
  {
    return 5;    
  }
  else
  {
    return 3;
  }
}

volatile bool menuMode = true;
volatile int encoder1 = HIGH;
volatile int encoder2 = HIGH;
volatile unsigned long encoderTimestamp = 0UL;

ISR(PCINT1_vect) {
  encoder1 = digitalRead(ENCODER1);
  encoder2 = digitalRead(ENCODER2);
  encoderTimestamp = millis();
}

int chosen_option;
int encoderValue = 0;
int lastEn1 = LOW;
unsigned long lastChangeTimestamp = 0UL;

void loop() {
  int en1;
  int en2;
  unsigned long timestamp;

  ATOMIC_BLOCK(ATOMIC_RESTORESTATE) {
    en1 = encoder1;
    en2 = encoder2;
    timestamp = encoderTimestamp;
  }

  if (menuMode) 
  {
    if (en1 == LOW && timestamp > lastChangeTimestamp + DEBOUNCING_PERIOD) {
      if (en2 == HIGH) {
        if (encoderValue < 2)
          encoderValue += 1;
      } else {
        if (encoderValue > 0)
          encoderValue -= 1;
      }
      lastChangeTimestamp = timestamp;

      menu(encoderValue);
    }
    lastEn1 = en1;
  } 
  if (isRedButtonPressed()) 
  {
    if (menuMode)
    {
      menuMode = false;
      chosen_option = encoderValue;
      lcd.clear();
      encoderValue = 0;
    }
    else if (!menuMode)
    {
      menuMode = true;
      lcd.clear();
      encoderValue = 0;
    }
  }
  if (!menuMode)
  {
    if (en1 == LOW && timestamp > lastChangeTimestamp + DEBOUNCING_PERIOD) 
    {
      if (en2 == HIGH) 
      {
        if (encoderValue < 255)
          encoderValue += 15;
      } 
      else 
      {
        if (encoderValue > 0)
          encoderValue -= 15;
      }
      lastChangeTimestamp = timestamp;

      change_intensity(diod_by_option(chosen_option), encoderValue);
    }
    lastEn1 = en1;
  }
}