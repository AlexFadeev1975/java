public class main {

    public static void main(String[] args) {

        Company company = new Company("Шариков", 80, 10, 180, 115000, 140000);

        company.headHunter(company, company.countManager, company.countTopManager, company.countOperator);

        company.printList(company.getTopSalaryStaff(company.listEmployee, 15));
        System.out.println();
        company.printList(company.getLowestSalaryStaff(company.listEmployee, 30));
        System.out.println();

        int totalCountPerson = company.listEmployee.size();
        for (int i = 0; i < totalCountPerson / 2; i++) {
            int personNumber = (int) (Math.random() * (company.listEmployee.size()));
            company.fire(personNumber);
        }
        company.printList(company.getTopSalaryStaff(company.listEmployee, 15));
        System.out.println();
        company.printList(company.getLowestSalaryStaff(company.listEmployee, 30));
    }
}
