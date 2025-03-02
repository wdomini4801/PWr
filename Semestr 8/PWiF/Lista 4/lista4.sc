// Wojciech Dominiak

// zadanie 1
class MyPair[A, B](var fst: A, var snd: B):
  override def toString = s"($fst, $snd)"

val pair1 = MyPair("a", 1)
pair1.fst == "a"
pair1.snd == 1
pair1.toString == "(a, 1)"
pair1.fst = "b"
pair1.snd = 10
pair1.fst == "b"
pair1.toString == "(b, 10)"

val pair2 = MyPair(true, false)
pair2.toString == "(true, false)"
pair2.fst = false
pair2.toString == "(false, false)"

// zadanie 2
class BankAccount(initialBalance: Double):
  private var balance = initialBalance
  def checkBalance = balance
  def deposit(amount : Double) = { balance += amount; balance}
  def withdraw(amount : Double) = { balance -= amount; balance}
  override def toString = "%.2f".format(balance)

// zadanie 2a
class CheckingAccount(initialBalance: Double) extends BankAccount(initialBalance):
  override def deposit(amount: Double) = super.deposit(amount - 1)
  override def withdraw(amount: Double) = super.withdraw(amount + 1)

val bankAccount = BankAccount(100)
val amount = 50
bankAccount.deposit(amount) == 150
bankAccount.withdraw(amount) == 100
bankAccount.toString == "100,00"

val checkingAccount = CheckingAccount(100)
checkingAccount.deposit(amount) == 149
checkingAccount.withdraw(amount) == 98
checkingAccount.checkBalance == bankAccount.checkBalance - 2
checkingAccount.toString == "98,00"

// zadanie 2b
class SavingsAccount(initialBalance: Double) extends BankAccount(initialBalance):
  private var amountOfTransactions = 0

  def earnMonthlyInterest(): Unit =
    amountOfTransactions = 0
    if checkBalance > 0 then deposit(checkBalance * 0.01)
    else withdraw(-checkBalance * 0.01)

  override def deposit(amount: Double): Double =
    amountOfTransactions += 1
    super.deposit(if amountOfTransactions <= 3 then amount else amount - 1)

  override def withdraw(amount: Double): Double =
    amountOfTransactions += 1
    super.withdraw(if amountOfTransactions <= 3 then amount else amount + 1)

val savingsAccount = SavingsAccount(100)
savingsAccount.withdraw(20) == 80
savingsAccount.withdraw(10) == 70
savingsAccount.deposit(30) == 100
// ponad 3 transakcje
savingsAccount.deposit(20) == 120-1
savingsAccount.withdraw(20) == 100-2
savingsAccount.deposit(3) == 100
savingsAccount.earnMonthlyInterest()
savingsAccount.checkBalance == 101
savingsAccount.withdraw(201) == -100
savingsAccount.earnMonthlyInterest()
savingsAccount.checkBalance == -101
savingsAccount.toString == "-101,00"

// zadanie 3a
abstract class Zwierz(val imie: String):
  def rodzaj = getClass.getSimpleName
  def dajGlos: String
  override def toString = s"$rodzaj $imie daje głos $dajGlos!"

// zadanie 3b
class Pies(imie: String = "bez imienia") extends Zwierz(imie):
  override def dajGlos = "Hau hau"

class Kot(imie: String = "bez imienia") extends Zwierz(imie):
  override def dajGlos = "Miau miau"

class Krowa(imie: String = "bez imienia") extends Zwierz(imie):
  override def dajGlos = "Muuuu"

// zadanie 3c
object TestZwierza:
  def main(args: Array[String]): Unit =
    val animals = Vector(
      Krowa(),
      Pies("Azor"),
      Kot("Mruczek"),
      Krowa("Biała"))
    for animal <- animals do print(animal)

TestZwierza.main(Array())
