# Review Questions
## 1. Identify a couple of examples that use AssertJ expressive methods chaining.
From *A_EmployeeRepoTest.java*:
```java
assertThat(found).isEqualTo(alex);
assertThat(fromDb.getEmail()).isEqualTo( emp.getEmail());
```
From *B_EmployeeService_UnitTest.java*:
```java
assertThat(fromDb).isNull();
```

## 2. Identify an example in which you mock the behavior of the repository (and avoid involving a database).
From *B_EmployeeService_UnitTest.java*:
```java
    @BeforeEach
    public void setUp() {

        //these expectations provide an alternative to the use of the repository
        Employee john = new Employee("john", "john@deti.com");
        john.setId(111L);

        Employee bob = new Employee("bob", "bob@deti.com");
        Employee alex = new Employee("alex", "alex@deti.com");

        List<Employee> allEmployees = Arrays.asList(john, bob, alex);

        Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
        Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
        Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
        Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
        Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
        Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
    }
```

## 3. What is the difference between standard @Mock and @MockBean?
*@Mock* is to be used when unit testing business logic (when using only JUnit and Mockito). However, if a test is backed by a Spring Test Context and you want to add or replace a bean with a mocked version, use *@MockBean*.

## 4. What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?
That file contains the properties needed to run the integration test, and correctly access the results of our test. Given the purpose of an integration test, we need to know where the component to be tested is located, as to properly test it's connection.

## 5. the sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences?
In C, the application is not fully loaded. Only the controller is being tested, so there is no need to load the services and repositories. MockMvc is used to mock the other components.  
In D and E, the application is fully loaded. However, in D, one accesses the server context through a special testing servlet (MockMvc object) while in E the requester is a REST client (TestRestTemplate)