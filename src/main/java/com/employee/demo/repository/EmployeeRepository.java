package com.employee.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.employee.demo.entity.Employee;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository <Employee,Long> {
}
