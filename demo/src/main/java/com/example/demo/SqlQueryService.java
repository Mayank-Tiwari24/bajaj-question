package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class SqlQueryService {

    public String getQueryForQuestion(boolean isQuestion1) {
        if (isQuestion1) {
            // Question 1: Find highest salary not credited on 1st day of month
            return """
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
                """;
        } else {
            // Question 2: Same query for now, replace if you have different question
            return """
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
                """;
        }
    }

    public boolean isOddQuestion(String regNo) {
        String digits = regNo.replaceAll("\\D", "");
        if (digits.length() >= 2) {
            String last2Digits = digits.substring(digits.length() - 2);
            int last2 = Integer.parseInt(last2Digits);
            return (last2 % 2) == 1;
        }
        return true; // Default to question 1
    }
}