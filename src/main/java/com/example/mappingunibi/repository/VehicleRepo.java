package com.example.mappingunibi.repository;

import com.example.mappingunibi.model.bi.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepo extends JpaRepository<Vehicle, Integer> {
}
