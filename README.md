# SQL Submission Application

## Project Structure
- **DemoApplication.java**: Main Spring Boot application
- **WebhookService.java**: Service for webhook generation and submission
- **SqlQueryService.java**: Service for SQL query logic
- **AppStartupRunner.java**: CommandLineRunner that executes on startup
- **Runner.java**: Alternative ApplicationRunner implementation

## SQL Solution
The application solves Question 1: Find the highest salary not credited on the 1st day of any month.

```sql
SELECT 
    p.AMOUNT as SALARY,
    CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) as NAME,
    TIMESTAMPDIFF(YEAR, e.DOB, CURDATE()) as AGE,
    d.DEPARTMENT_NAME
FROM PAYMENTS p
JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID
JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID
WHERE DAY(p.PAYMENT_TIME) != 1
ORDER BY p.AMOUNT DESC
LIMIT 1
```

## To Build and Run:
```bash
mvn clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

## Expected Output:
- SALARY: 74998.00
- NAME: Emily Brown
- AGE: 32
- DEPARTMENT_NAME: Sales

The application will automatically submit this solution on startup.
