public class Operator implements Employee {

    int salary;
    Company company;

    Operator(Company company) {
        this.company = company;
        salary = ((int) ((Math.random() * 5000) + 10000));
    }

    @Override
    public int getMonthSalary() {

        return salary;
    }

}
