public class BankAccount {

  public double bankAccount = 0;

  public double getAmount() {
    //TODO: реализуйте метод и удалите todo
    // верните значение количества денег не счету
    return bankAccount;
  }

  public void put(double amountToPut) {
    //TODO: реализуйте метод и удалите todo
    // метод зачисляет деньги на счет
    if (amountToPut >= 0) {
      bankAccount += amountToPut;
    }
  }

  public void take(double amountToTake) {
    //TODO: реализуйте метод и удалите todo
    // метод списывает деньги со счета
    if (amountToTake <= bankAccount) {
      bankAccount -= amountToTake;
    }
  }
}
