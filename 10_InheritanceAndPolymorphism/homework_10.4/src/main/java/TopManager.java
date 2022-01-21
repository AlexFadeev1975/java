public class TopManager implements Employee {


    int salary, fixedSalary;
    final int INCOMELIMIT = 10000000;
    final int BONUS = 150;
    Company company;

    TopManager(Company company) {
        this.company = company;
        fixedSalary = ((int) ((Math.random() * 50000) + 150000));
        if (company.getIncome() > INCOMELIMIT) {
            salary = fixedSalary + fixedSalary * BONUS / 100;
        } else {
            salary = fixedSalary;

        }
    }

    @Override
    public int getMonthSalary() {

        return salary;

    }

}
