import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Company {

    static int incomeLow, incomeHigh;
    static String nameCompany = "";
    static int income = (int) (Math.random() * ((incomeHigh + 1 - incomeLow) + incomeHigh));
    int salary = 0, totalIncome = 0;
    List<Employee> listEmployee = new ArrayList<>();
    List<Employee> listSalarySorted = new ArrayList<>();


    Employee employee = new Employee() {
        @Override
        public int getMonthSalary() {
            return employee.getMonthSalary();
        }
    };

    public Company(String nameCompany, int countManager, int countTopManager, int countOperator, int incomeLow, int incomeHigh) {
        Company.incomeLow = incomeLow;
        Company.incomeHigh = incomeHigh;
        hirePerson(countManager, countTopManager, countOperator);
        Company.nameCompany = nameCompany;
    }

    public void hire(String type, int salary) {
        this.salary = salary;

        if (Objects.equals(type, "manager")) {

            Employee manager = new TopManager(salary);
            listEmployee.add(manager);
            totalIncome = totalIncome + income;
        }
        if (Objects.equals(type, "operator")) {

            Employee operator = new Operator(salary);
            listEmployee.add(operator);
        }

        if (Objects.equals(type, "topManager")) {

            Employee topManager = new Manager(salary);
            listEmployee.add(topManager);
        }
    }

    public void hireAll(List<Employee> newList) {

        listEmployee.addAll(newList);
    }

    public void fire(int index) {

        listEmployee.remove(index);
    }

    public int getIncome() {

        return totalIncome;
    }

    public void hirePerson(int countManager, int countTopManager, int countOperator) {
        for (int i = 0; i < countManager; i++) {
            salary = ((int) ((Math.random() * 10000) + 20000));
            hire("manager", salary);

        }
        for (int i = 0; i < countTopManager; i++) {
            salary = ((int) ((Math.random() * 50000) + 150000));
            hire("topManager", salary);

        }

        for (int i = 0; i < countOperator; i++) {
            salary = ((int) ((Math.random() * 5000) + 10000));
            hire("operator", salary);

        }
    }

    public List<Employee> sortList(int count, Comparator type) {
        List<Integer> listSalary = new ArrayList<>();
        List<Integer> tempList;
        listSalarySorted.clear();
        if (count <= listEmployee.size() && count > 0) {

            for (Employee value : listEmployee) {

                employee = value;
                listSalary.add(employee.getMonthSalary());
            }
            listSalary.sort(type);
            tempList = listSalary.subList(0, count);

            for (int item : tempList) {

                for (Employee value : listEmployee) {
                    employee = value;
                    if (item == employee.getMonthSalary()) {
                        listSalarySorted.add(employee);

                    }
                }
            }
        }
        return listSalarySorted;
    }

    public List<Employee> getTopSalaryStaff(int count) {

        return sortList(count, Comparator.reverseOrder());
    }

    List<Employee> getLowestSalaryStaff(int count) {

        return sortList(count, Comparator.naturalOrder());
    }

    public void printList(List<Employee> list) {
        for (Employee value : list) {
            employee = value;
            System.out.println(employee.getMonthSalary() + "   руб.");
        }
    }


}








