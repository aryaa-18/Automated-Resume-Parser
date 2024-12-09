package com.example.demo.repository;

import com.example.demo.entity.AdminDashboard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminDashboardRepository extends JpaRepository<AdminDashboard, Long> {
    List<AdminDashboard> findByEmail(String email);
}
