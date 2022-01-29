import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Main {

    private static final String STAFF_TXT = "data/staff.txt";

    public static void main(String[] args) {
        List<Employee> staff = Employee.loadStaffFromFile(STAFF_TXT);
        Employee employeeMaxSalary = findEmployeeWithHighestSalary(staff, 2017);
        System.out.println(employeeMaxSalary);
    }

    public static Employee findEmployeeWithHighestSalary(List<Employee> staff, int year) {
        //TODO Метод должен вернуть сотрудника с максимальной зарплатой среди тех,
        // кто пришёл в году, указанном в переменной year

        Date date = new Date((year - 1899), Calendar.JANUARY, 1);
        Date date1 = new Date((year - 1901), Calendar.DECEMBER, 31);

        return staff.stream().filter(e -> e.getWorkStart().before(date)).filter(m -> m.getWorkStart().after(date1)).
                max(Comparator.comparing(Employee::getSalary)).get();


    }


}