public class Manager implements Employee {

    int salary, fixedSalary;
    final int BONUS = 5;
    Company company;

    Manager(Company company) {
        this.company = company;
        fixedSalary = ((int) ((Math.random() * 10000) + 20000));
        salary = fixedSalary + company.income * BONUS / 100;
    }

    @Override

    public int getMonthSalary() {

        return salary;
    }
}

