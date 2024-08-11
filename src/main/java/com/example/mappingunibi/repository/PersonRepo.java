package com.example.mappingunibi.repository;

import com.example.mappingunibi.model.bi.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepo extends JpaRepository<Person, Integer> {
}
