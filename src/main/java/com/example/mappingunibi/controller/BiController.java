package com.example.mappingunibi.controller;

import com.example.mappingunibi.model.Response;
import com.example.mappingunibi.model.bi.Person;
import com.example.mappingunibi.model.bi.Vehicle;
import com.example.mappingunibi.repository.PersonRepo;
import com.example.mappingunibi.repository.VehicleRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class BiController {

    private PersonRepo personRepo;
    private VehicleRepo vehicleRepo;

    //prefer creating child first and parent to prevent merging issues
    @PostMapping("/person")
    public Person addPerson(@RequestBody Person person) {
        Person saved = personRepo.save(person);
        person.setVehicles(null);
        return saved;
    }

    //this is one effective way to save parent with child
    @PostMapping("/personwithvehicle")
    public ResponseEntity<?> postPersonWithVehicle(@RequestBody Person person) {
        person.getVehicles().stream().forEach(vehicle -> {
            vehicle.setPerson(person);
        });
        return new ResponseEntity<>(personRepo.save(person), HttpStatus.CREATED);
    }

    //this one is an effective way to add child to saved person - use this
    @PostMapping("/savevehicletoperson/{id}")
    public ResponseEntity<?> saveVehicleToPerson(@PathVariable("id") int personId,
                                                 @RequestBody Vehicle vehicle){
        Person savedPerson = personRepo.findById(personId).orElse(null);
        if (savedPerson==null) {
            return new ResponseEntity<>(new Response("Person with id not found"), HttpStatus.NOT_FOUND);
        }
        savedPerson.addVehicle(vehicle);
        return new ResponseEntity<>(personRepo.save(savedPerson), HttpStatus.OK);
    }

    @PutMapping("/addvehicletoperson/{id}")
    public ResponseEntity<?> mergePersonAndVehicle(@PathVariable(name = "id") int id,
                                                   @RequestBody Vehicle vehicle) {
        Person savedPerson = personRepo.findById(id).orElse(null);
        if (savedPerson==null) {
            return new ResponseEntity<>(new Response("Person with id not found"), HttpStatus.NOT_FOUND);
        }
        if (vehicle.getId()!=0) {
            Vehicle savedVehicle = vehicleRepo.findById(vehicle.getId()).orElse(null);
            if (savedPerson==null)
                return new ResponseEntity<>(new Response("Vehicle with id not found"), HttpStatus.NOT_FOUND);
            savedVehicle.setPerson(savedPerson);
            vehicleRepo.save(savedVehicle);
            return new ResponseEntity<>(personRepo.findById(id).get(), HttpStatus.OK);
        }
        vehicle.setPerson(savedPerson);
        vehicleRepo.save(vehicle);
        return new ResponseEntity<>(personRepo.findById(id).get(), HttpStatus.OK);
    }

    @DeleteMapping("/person/{id}")
    public void deletePerson(@PathVariable(name = "id") int id) {
        personRepo.deleteById(id);
    }

    @GetMapping("/persons")
    public List<Person> getAllPerson() {
        return personRepo.findAll();
    }

    @PostMapping("/vehicle")
    public Vehicle addVehicle(@RequestBody Vehicle vehicle) {
        return vehicleRepo.save(vehicle);
    }

    @PostMapping("/vehiclewithperson")
    public Vehicle addVehicleWithPerson(@RequestBody Vehicle vehicle) {
//        vehicle.getPerson().addVehicle(vehicle);
        return vehicleRepo.save(vehicle);
    }

    @DeleteMapping("/vehicle/{id}")
    public void deleteVehicle(@PathVariable(name = "id") int id) {
        vehicleRepo.deleteById(id);
    }

    @GetMapping("/vehicles")
    public List<Vehicle> getAllVehicle() {
        return vehicleRepo.findAll();
    }
}
