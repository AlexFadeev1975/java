public class Manager implements Employee {

    int salary;
    String nameCompany;
    final int BONUS = 5;

    Manager(int fixedSalary) {
        this.nameCompany = Company.nameCompany;
        salary = fixedSalary + Company.income * BONUS / 100;
    }

    @Override

    public int getMonthSalary() {

        return salary;
    }

}

