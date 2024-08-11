package com.example.mappingunibi.controller;

import com.example.mappingunibi.model.uni.Child;
import com.example.mappingunibi.model.uni.Parent;
import com.example.mappingunibi.repository.ChildRepo;
import com.example.mappingunibi.repository.ParentRepo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UniController {

    private ParentRepo parentRepo;
    private ChildRepo childRepo;

    @PostMapping("/parent")
    public Parent addParent(@RequestBody Parent parent) {
        return parentRepo.save(parent);
    }

    @DeleteMapping("/parent/{id}")
    public void removeParent(@PathVariable(name = "id") int id) {
        parentRepo.deleteById(id);
    }

    @GetMapping("/parents")
    public List<Parent> getParents() {
        return parentRepo.findAll();
    }

    @PostMapping("/child")
    public Child addChild(@RequestBody Child child) {
        return childRepo.save(child);
    }

    @GetMapping("/children")
    public List<Child> getChildren() {
        return childRepo.findAll();
    }



}
