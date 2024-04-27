package com.patil.software.solutions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patil.software.solutions.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
