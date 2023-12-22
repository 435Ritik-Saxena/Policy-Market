package com.monocept.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.insurance.entities.Payment;

//import com.monocept.insurance.entities.Admin;

public interface PaymentRepo extends JpaRepository<Payment,Integer>{

}
