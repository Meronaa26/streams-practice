package com.example.streampracticetask.practice;

import com.example.streampracticetask.bootstrap.DataGenerator;
import com.example.streampracticetask.model.*;
import com.example.streampracticetask.service.*;
import com.fasterxml.jackson.core.PrettyPrinter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Practice {

    public static CountryService countryService;
    public static DepartmentService departmentService;
    public static EmployeeService employeeService;
    public static JobHistoryService jobHistoryService;
    public static JobService jobService;
    public static LocationService locationService;
    public static RegionService regionService;

    public Practice(CountryService countryService, DepartmentService departmentService,
                    EmployeeService employeeService, JobHistoryService jobHistoryService,
                    JobService jobService, LocationService locationService,
                    RegionService regionService) {

        Practice.countryService = countryService;
        Practice.departmentService = departmentService;
        Practice.employeeService = employeeService;
        Practice.jobHistoryService = jobHistoryService;
        Practice.jobService = jobService;
        Practice.locationService = locationService;
        Practice.regionService = regionService;

    }

    // You can use the services above for all the CRUD (create, read, update, delete) operations.
    // Above services have all the required methods.
    // Also, you can check all the methods in the ServiceImpl classes inside the service.impl package, they all have explanations.

    // Display all the employees
    public static List<Employee> getAllEmployees() {
        return employeeService.readAll();
       // return new ArrayList<>();
    }

    // Display all the countries
    public static List<Country> getAllCountries() {
     return    countryService.readAll();
       // return new ArrayList<>();
    }

    // Display all the departments
    public static List<Department> getAllDepartments() {
     return   departmentService.readAll();
       // return new ArrayList<>();
    }

    // Display all the jobs
    public static List<Job> getAllJobs() {
     return   jobService.readAll();
       // return new ArrayList<>();
    }

    // Display all the locations
    public static List<Location> getAllLocations() {
     return   locationService.readAll();
       // return new ArrayList<>();
    }

    // Display all the regions
    public static List<Region> getAllRegions() {
      return regionService.readAll();
        //return new ArrayList<>();
    }

    // Display all the job histories
    public static List<JobHistory> getAllJobHistories() {
      return jobHistoryService.readAll();
      //  return new ArrayList<>();
    }

    // Display all the employees' first names
    public static List<String> getAllEmployeesFirstName() {
        //Stream<String> emplList =    employeeService.readAll().stream().map(Employee::getFirstName);
       // List<String>result=new ArrayList<>();
       // result=emplList.collect(Collectors.toList());
        return employeeService.readAll().stream().map(Employee::getFirstName).collect(Collectors.toList());
    }

    // Display all the countries' names
    public static List<String> getAllCountryNames() {
       Stream<String> countries=countryService.readAll().stream().map(Country::getCountryName);
       List<String>result1= new ArrayList<>();
       result1=countries.collect(Collectors.toList());
        return result1;
    }



    // Display all the departments' managers' first names
    public static List<String> getAllDepartmentManagerFirstNames() {
        Stream<String>emplList= departmentService.readAll().stream().map(Department::getManager).map(Employee::getFirstName);
        List<String>result3= new ArrayList<>();
        result3=emplList.collect(Collectors.toList());
        return result3;
    }

    // Display all the departments where manager name of the department is 'Steven'
    public static List<Department> getAllDepartmentsWhichManagerFirstNameIsSteven() {

        return    departmentService.readAll().stream().
                filter(dep -> dep.getManager().getFirstName()=="Steven")
                .collect(Collectors.toList());

    }

    // Display all the departments where postal code of the location of the department is '98199'
    public static List<Department> getAllDepartmentsWhereLocationPostalCodeIs98199() {
        return   departmentService.readAll().stream().
                filter(loc ->loc.getLocation().getPostalCode()=="98199")
                .collect(Collectors.toList());

    }

    // Display the region of the IT department
    public static Region getRegionOfITDepartment() throws Exception {
      // return  departmentService.readAll().stream().filter(dep ->dep.getDepartmentName()=="IT")
         //     .map(depart -> depart.getLocation().getCountry().getRegion()).findAny().get();

        //teacher way
       return departmentService.readAll().stream().filter(department -> department.getDepartmentName().equals("IT")).
        findFirst().orElseThrow(()->new Exception("No department found")).getLocation().getCountry().getRegion();

    }

    // Display all the departments where the region of department is 'Europe'
    public static List<Department> getAllDepartmentsWhereRegionOfCountryIsEurope() {
        return    departmentService.readAll().stream()
                .filter(department -> department.getLocation().getCountry().getRegion().getRegionName()=="Europe").collect(Collectors.toList());

   }

    // Display if there is any employee with salary less than 1000. If there is none, the method should return true
    public static boolean checkIfThereIsNoSalaryLessThan1000() {
      //  return  getAllEmployees().stream().allMatch(employee -> employee.getSalary()>1000);
      //  return ! getAllEmployees().stream().anyMatch(employee -> employee.getSalary()<1000);
        return getAllEmployees().stream().noneMatch(employee -> employee.getSalary()<1000);
    }

    // Check if the salaries of all the employees in IT department are greater than 2000 (departmentName: IT)
    public static boolean checkIfThereIsAnySalaryGreaterThan2000InITDepartment() {
       return getAllEmployees().stream().filter(employee -> employee.getDepartment().getDepartmentName().equals("IT"))
               .noneMatch(employee -> employee.getSalary()<2000);
    }

    // Display all the employees whose salary is less than 5000
    public static List<Employee> getAllEmployeesWithLessSalaryThan5000() {
      return getAllEmployees().stream().filter(employee -> employee.getSalary()<5000).collect(Collectors.toList());
    }

    // Display all the employees whose salary is between 6000 and 7000
    public static List<Employee> getAllEmployeesSalaryBetween() {
       //return getAllEmployees().stream().
           //    filter(employee -> employee.getSalary()>6000).filter(employee -> employee.getSalary()<7000).collect(Collectors.toList());
        return getAllEmployees().stream().
                filter(employee -> employee.getSalary()>6000 && employee.getSalary()<7000).collect(Collectors.toList());
    }

  // Display the salary of the employee Grant Douglas (lastName: Grant, firstName: Douglas)
 public static Long getGrantDouglasSalary() throws Exception {
   return getAllEmployees().stream().
          filter(employee -> employee.getFirstName().equals("Douglas") && employee.getLastName().equals("Grant")).
          findAny().get().getSalary();

 }

    // Display the maximum salary an employee gets
    public static Long getMaxSalary() throws Exception {
       // return getAllEmployees().stream().max(Comparator.comparing(Employee::getSalary)).get().getSalary();
       // or
        // return getAllEmployees().stream().map(Employee::getSalary).reduce((Long::max).get();
            return getAllEmployees().stream().map(Employee::getSalary).reduce((a,b)->a>b? a:b) .get();
    }

    // Display the employee(s) who gets the maximum salary
    public static List<Employee> getMaxSalaryEmployee() {
    return getAllEmployees().stream().filter(employee -> {
        try {
            return employee.getSalary().equals(getMaxSalary());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }).collect(Collectors.toList());
    }

    // Display the max salary employee's job
    public static Job getMaxSalaryEmployeeJob() throws Exception {
       // return getMaxSalaryEmployee().get(0).getJob(); // #1
       // return getAllEmployees().stream().max(Comparator.comparing(Employee::getSalary)).orElseThrow(()-> new Exception("something is wrong")).getJob(); //#2
        return getMaxSalaryEmployee().stream().map(Employee::getJob).findAny().orElseThrow();

    }

    // Display the max salary in Americas Region
    public static Long getMaxSalaryInAmericasRegion() throws Exception {

     return getAllEmployees().stream().filter(employee -> employee.getDepartment().getLocation().getCountry().getRegion().getRegionName().equals("Americas"))
             .max(Comparator.comparing(Employee::getSalary)).orElseThrow(() -> new Exception("something is wrong")).getSalary();
    }

//    // Display the max salary in Americas Region
//    public static Long getMaxSalaryInAmericasRegion() throws Exception {
//        return employeeService.readAll().stream()
//                .filter(employee -> employee.getDepartment().getLocation().getCountry().getRegion().getRegionName().equals("Americas"))
//                .max(Comparator.comparing(Employee::getSalary))
//                .orElseThrow(() -> new Exception("Something went wrong!")).getSalary();
//    }

    // Display the second maximum salary an employee gets
    public static Long getSecondMaxSalary() throws Exception {
        return getAllEmployees().stream().map(Employee::getSalary).reduce((a,b)-> {
            try {
                return a>b && a<getMaxSalary() ? a: b;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).orElseThrow(()-> new Exception("Employee couldn't be found"));
    }

    // Display the employee(s) who gets the second maximum salary
    public static List<Employee> getSecondMaxSalaryEmployee() {
       return getAllEmployees().stream().filter(employee -> {
           try {
               return employee.getSalary().equals(getSecondMaxSalary());
           } catch (Exception e) {
               e.printStackTrace();
           }
           return false;
       }).collect(Collectors.toList());
    }

    // Display the minimum salary an employee gets
    public static Long getMinSalary() throws Exception {

      return getAllEmployees().stream().min(Comparator.comparing(Employee::getSalary))
              .get().getSalary();
    }

    // Display the employee(s) who gets the minimum salary
    public static List<Employee> getMinSalaryEmployee() {
       return  getAllEmployees().stream().filter(employee -> {
           try {
               return employee.getSalary().equals(getMinSalary());
           } catch (Exception e) {
               e.printStackTrace();
           }
           return  false;
       }).collect(Collectors.toList());
    }

    // Display the second minimum salary an employee gets
    public static Long getSecondMinSalary() throws Exception {
       return  getAllEmployees().stream().map(Employee::getSalary)
               .reduce((a,b) -> {
                   try {
                       return a<b && a>getMinSalary()? a:b;
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
                   return -1L;
               }).orElseThrow(() -> new Exception("Something went wrong"));
       // other suggestions
       // return getAllEmployees().stream().map(Employee::getSalary).sorted().distinct().limit(2).collect(Collectors.toList()).get(1);
    }

    // Display the employee(s) who gets the second minimum salary
    public static List<Employee> getSecondMinSalaryEmployee() {
       return getAllEmployees().stream().filter(employee -> {
           try {
               return employee.getSalary().equals(getSecondMinSalary());
           } catch (Exception e) {
               e.printStackTrace();
           }
           return false;
       }).collect(Collectors.toList());
    }

    // Display the average salary of the employees
    public static Double getAverageSalary() {
        return getAllEmployees().stream().collect(Collectors.averagingDouble(Employee::getSalary));
    }

    // Display all the employees who are making more than average salary
    public static List<Employee> getAllEmployeesAboveAverage() {
      return getAllEmployees().stream().filter(employee -> employee.getSalary()>getAverageSalary()).collect(Collectors.toList());
    }

    // Display all the employees who are making less than average salary
    public static List<Employee> getAllEmployeesBelowAverage() {
        return getAllEmployees().stream().filter(employee -> employee.getSalary()<getAverageSalary()).collect(Collectors.toList());
    }

    // Display all the employees separated based on their department id number
    public static Map<Long, List<Employee>> getAllEmployeesForEachDepartment() {
       return getAllEmployees().stream().collect(Collectors.groupingBy(employee -> employee.getDepartment().getId()));
    }

    // Display the total number of the departments
    public static Long getTotalDepartmentsNumber() {
        //return getAllDepartments().stream().count();     // with stream
        return  (long)getAllDepartments().size();
    }

    // Display the employee whose first name is 'Alyssa' and manager's first name is 'Eleni' and department name is 'Sales'
    public static Employee getEmployeeWhoseFirstNameIsAlyssaAndManagersFirstNameIsEleniAndDepartmentNameIsSales() throws Exception {
        return getAllEmployees().stream().filter(employee -> employee.getFirstName().equals("Alyssa")
                && employee.getManager().getFirstName().equals("Eleni") &&
                employee.getDepartment().getDepartmentName().equals("Sales")).findFirst().get();
    }

    // Display all the job histories in ascending order by start date
    public static List<JobHistory> getAllJobHistoriesInAscendingOrder() {
       return getAllJobHistories().stream().sorted(Comparator.comparing(JobHistory::getStartDate))
               .collect(Collectors.toList());
    }

    // Display all the job histories in descending order by start date
    public static List<JobHistory> getAllJobHistoriesInDescendingOrder() {
        return getAllJobHistories().stream().sorted(Comparator.comparing(JobHistory::getStartDate).reversed())
                .collect(Collectors.toList());
    }

    // Display all the job histories where the start date is after 01.01.2005
    public static List<JobHistory> getAllJobHistoriesStartDateAfterFirstDayOfJanuary2005() {
      return getAllJobHistories().stream().filter(jobHistory -> jobHistory.getStartDate().isAfter(LocalDate.of(2005,1,1)))
              .collect(Collectors.toList());
    }

    // Display all the job histories where the end date is 31.12.2007 and the job title of job is 'Programmer'
    public static List<JobHistory> getAllJobHistoriesEndDateIsLastDayOfDecember2007AndJobTitleIsProgrammer() {
      return getAllJobHistories().stream().filter(jobHistory -> jobHistory.getEndDate().equals(LocalDate.of(2007,12,31))
              &&  jobHistory.getJob().getJobTitle().equals("Programmer")).collect(Collectors.toList());

    }

    // Display the employee whose job history start date is 01.01.2007 and job history end date is 31.12.2007 and department's name is 'Shipping'
    public static Employee getEmployeeOfJobHistoryWhoseStartDateIsFirstDayOfJanuary2007AndEndDateIsLastDayOfDecember2007AndDepartmentNameIsShipping() throws Exception {
        return getAllJobHistories().stream().filter(jobHistory -> jobHistory.getStartDate().equals(LocalDate.of(2007, 01, 01))
                && jobHistory.getEndDate().equals(LocalDate.of(2007, 12, 31))
                && jobHistory.getDepartment().getDepartmentName().equals("Shipping")).map(JobHistory::getEmployee).findAny().get();

    }
    // Display all the employees whose first name starts with 'A'
    public static List<Employee> getAllEmployeesFirstNameStartsWithA() {
      return getAllEmployees().stream().filter(employee -> employee.getFirstName().startsWith("A")).collect(Collectors.toList());
    }

    // Display all the employees whose job id contains 'IT'
    public static List<Employee> getAllEmployeesJobIdContainsIT() {
      return getAllEmployees().stream().filter(employee -> employee.getJob().getId().contains("IT")).collect(Collectors.toList());
    }

    // Display the number of employees whose job title is programmer and department name is 'IT'
    public static Long getNumberOfEmployeesWhoseJobTitleIsProgrammerAndDepartmentNameIsIT() {
       return getAllEmployees().stream().filter(employee -> employee.getJob().getJobTitle().equals("Programmer")
               && employee.getDepartment().getDepartmentName().equals("IT")).count();
    }

    // Display all the employees whose department id is 50, 80, or 100
    public static List<Employee> getAllEmployeesDepartmentIdIs50or80or100() {
        return getAllEmployees().stream().filter(employee -> employee.getDepartment().getId().equals(50L)
                                                || employee.getDepartment().getId().equals(80L)
                                                || employee.getDepartment().getId().equals(100L)).collect(Collectors.toList());
    }

    // Display the initials of all the employees
    // Note: You can assume that there is no middle name
    public static List<String> getAllEmployeesInitials() {
        return getAllEmployees().stream().map(employee -> {
            String firstInitial =employee.getFirstName().substring(0,1);
            String lastNameInt= employee.getLastName().substring(0,1);
            return firstInitial+ lastNameInt;
        }).collect(Collectors.toList());
    }

    // Display the full names of all the employees
    public static List<String> getAllEmployeesFullNames() {
       return getAllEmployees().stream().map(employee -> {
           String firstname= employee.getFirstName() + " ";
           String lastName=employee.getLastName();
           return firstname+lastName;
       }).collect(Collectors.toList());
    }

    // Display the length of the longest full name(s)
    public static Integer getLongestNameLength() throws Exception {
        Employee employee =getAllEmployees().stream().reduce((employee1,employee2)->
                                     employee1.getFirstName().length()
                                    + employee1.getLastName().length()
                                             >
                                    employee2.getFirstName().length()
                                    +employee2.getLastName().length() ?  employee1 :employee2)
                .orElseThrow(() -> new Exception("something is wrong"));
        return employee.getFirstName().length() + employee.getLastName().length() + 1;
    }

    // Display the employee(s) with the longest full name(s)
    public static List<Employee> getLongestNamedEmployee() {
    return getAllEmployees().stream().filter(employee -> {
        try {
            return employee.getFirstName().length()
                                         + employee.getLastName().length() + 1 == getLongestNameLength();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }).collect(Collectors.toList());

    }

    // Display all the employees whose department id is 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIs90or60or100or120or130() {
               //  return  employeeService.readAll().stream()     ****  MINE ISN'T WORKING    *******
                  //         .filter(employee -> employee.getDepartment().getId().equals("60L")
                   //        ||  employee.getDepartment().getId().equals("90L")
                     //          ||    employee.getDepartment().getId().equals("100L")
                       //           || employee.getDepartment().getId().equals("130L")
                         //          ).collect(Collectors.toList());

      //  return getAllEmployees().stream().filter(employee ->
                                     //         employee.getDepartment().getId().toString().contains("90")
                                         //   && employee.getDepartment().getId().toString().contains("60")
                                          //   && employee.getDepartment().getId().toString().contains("100")
                                          //   && employee.getDepartment().getId().toString().contains("130")).collect(Collectors.toList());

        List<Long>list1=Arrays.asList(90L,100L,60L,130L,120L);
        return getAllEmployees().stream().filter(employee -> list1.contains(employee.getDepartment().getId())).collect(Collectors.toList());



    }

    // Display all the employees whose department id is NOT 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIsNot90or60or100or120or130() {
//       return employeeService.readAll()..                   // ***8  COULDN'T FIGURE IT OUT MINE IS FAILING ************
//               .filter(employee -> !(employee.getDepartment().getId().equals("90L"))
//                       && !(employee.getDepartment().getId().equals("60L"))
//                       && !(employee.getDepartment().getId().equals("100L"))
//                       && !(employee.getDepartment().getId().equals("120L"))
//                       && !(employee.getDepartment().getId().equals("130L"))
//               ).collect(Collectors.toList());

        //  TEACHERS WAY

//        return employeeService.readAll().stream()
//          .filter(employee -> !(employee.getDepartment().getId().equals(60L)) && !(employee.getDepartment().getId().equals(90L))
//                      && !(employee.getDepartment().getId().equals(100L)) && !(employee.getDepartment().getId().equals(120L)) && !(employee.getDepartment().getId().equals(130L)))
//              .collect(Collectors.toList());


       // best way
        List<Long> list= Arrays.asList(90L,100L,60L,130L,120L);
        return getAllEmployees().stream().filter(employee -> !list.contains(employee.getDepartment().getId())).collect(Collectors.toList());
    }



}



