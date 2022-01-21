import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Company {

    int incomeLow, incomeHigh, countOperator, countManager, countTopManager, income = 0, totalIncome = 0;
    String nameCompany = "";
    List<Employee> listEmployee = new ArrayList<>();
    Employee employee = new Employee() {
        @Override
        public int getMonthSalary() {
            return employee.getMonthSalary();
        }

    };

    public Company(String nameCompany, int countManager, int countTopManager, int countOperator, int incomeLow, int incomeHigh) {
        this.incomeLow = incomeLow;
        this.incomeHigh = incomeHigh;
        this.countManager = countManager;
        this.countTopManager = countTopManager;
        this.countOperator = countOperator;
        this.nameCompany = nameCompany;
    }

    public void hire(Employee employee) {

        listEmployee.add(employee);
    }


    public void hireAll(List<Employee> newList) {

        listEmployee.addAll(newList);
    }

    public void fire(int index) {

        listEmployee.remove(index);
    }

    public int getIncome() {

        return (int) (Math.random() * ((incomeHigh + 1 - incomeLow) + incomeHigh));
    }


    public List<Employee> getTopSalaryStaff(List<Employee> list, int count) {

        if (count > 0 && count <= list.size()) {
            list.sort(Comparator.comparing(Employee::getMonthSalary));
            Collections.reverse(list);
            return list.subList(0, count);

        } else return null;
    }

    List<Employee> getLowestSalaryStaff(List<Employee> list, int count) {

        if (count > 0 && count <= list.size()) {
            list.sort(Comparator.comparing(Employee::getMonthSalary));
            return list.subList(0, count);

        } else return null;

    }

    public void headHunter(Company company, int countManager, int countTopManager, int countOperator) {
        for (int i = 0; i < countManager; i++) {

            Employee manager = new Manager(company);

            hire(manager);
            totalIncome = totalIncome + income;
        }
        for (int i = 0; i < countTopManager; i++) {
            Employee topManager = new TopManager(company);
            hire(topManager);
        }
        for (int i = 0; i < countOperator; i++) {
            Employee operator = new Operator(company);
            hire(operator);
        }
    }

    public void printList(List<Employee> list) {
        for (Employee value : list) {
            employee = value;
            System.out.println(employee.getMonthSalary() + "   руб.");
        }
    }
}








