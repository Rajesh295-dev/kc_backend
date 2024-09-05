//
//
//package com.e_management.fullstack_backend.model;
//import jakarta.persistence.*;
//import java.time.LocalDate;
//import java.util.Random;
//
//@Entity
//@Table(name = "user")  // This will create or use a table named "user"
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
//    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
//    private Long id;
//
//    private String name;
//    private String email;
//    private String phone;
//    private String address;
//    private String role;
//    private LocalDate dateOfBirth;
//    private Double salary;
//    private String ssn;
//    private String employeeId;
//
//
//    private String password;
//
//    // Constructor
//    public User() {
//        // Empty constructor
//    }
//
//    // Generate a random 5-digit employee ID
//    private String generateEmployeeId() {
//        Random random = new Random();
//        int employeeId = 10000 + random.nextInt(90000); // Generates a number between 10000 and 99999
//        return String.valueOf(employeeId);
//    }
//
//    @PrePersist
//    protected void onCreate() {
//        if (this.employeeId == null || this.employeeId.isEmpty()) {
//            this.employeeId = generateEmployeeId();
//        }
//    }
//
//    // Getters and Setters
//    public Long getId() {
//        return null;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//
//    public LocalDate getDateOfBirth() {
//        return dateOfBirth;
//    }
//
//    public void setDateOfBirth(LocalDate dateOfBirth) {
//        this.dateOfBirth = dateOfBirth;
//    }
//
//    public Double getSalary() {
//        return salary;
//    }
//
//    public void setSalary(Double salary) {
//        this.salary = salary;
//    }
//
//    public String getSsn() {
//        return ssn;
//    }
//
//    public void setSsn(String ssn) {
//        this.ssn = ssn;
//    }
//
//    public String getEmployeeId() {
//        return employeeId;
//    }
//
//    public void setEmployeeId(String employeeId) {
//        this.employeeId = employeeId;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//}

package com.e_management.fullstack_backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Random;

@Entity
@Table(name = "user")  // This will create or use a table named "user"
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment id
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String address;
    private String role;
    private LocalDate dateOfBirth;
    private Double salary;
    private String ssn;
    private String employeeId;
    private String password;

    // Constructor
    public User() {
        // Empty constructor
    }

    // Generate a random 5-digit employee ID
    private String generateEmployeeId() {
        Random random = new Random();
        int employeeId = 10000 + random.nextInt(90000);  // Generates a number between 10000 and 99999
        return String.valueOf(employeeId);
    }

    @PrePersist
    protected void onCreate() {
        if (this.employeeId == null || this.employeeId.isEmpty()) {
            this.employeeId = generateEmployeeId();  // Set the employeeId only if it's null
        }
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
