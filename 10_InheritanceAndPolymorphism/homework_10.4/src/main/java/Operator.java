public class Operator implements Employee {

    int salary;
    String nameCompany;

    Operator(int fixedSalary) {
        this.nameCompany = Company.nameCompany;
        salary = fixedSalary;
    }

    @Override
    public int getMonthSalary() {

        return salary;
    }


}
