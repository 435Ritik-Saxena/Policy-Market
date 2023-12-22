package com.monocept.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.insurance.entities.Employee;

public interface EmployeeRepo extends JpaRepository<Employee,Integer> {

}