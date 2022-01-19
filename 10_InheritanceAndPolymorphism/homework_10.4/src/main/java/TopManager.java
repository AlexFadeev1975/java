public class TopManager implements Employee {


    int salary;
    String nameCompany;
    final int INCOMELIMIT = 10000000;
    final int BONUS = 150;

    TopManager(int fixedSalary) {

        this.nameCompany = Company.nameCompany;
        if (Company.income > INCOMELIMIT) {
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
