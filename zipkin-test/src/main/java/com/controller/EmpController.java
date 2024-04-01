package com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.Employee;
import com.repository.EmpRepo;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/emp")
@Slf4j
class EmpController {

    @Autowired
    public EmpRepo empRepo;

    @GetMapping(path = "/all")
    public ResponseEntity<List<String>> getAll() {
        try {
            List<String> items = new ArrayList();

            empRepo.findAll().forEach(emp->items.add(emp.getName()));
            log.info("all emp : ", items);
            if (items.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") int id) {
        Optional<Employee> existingItemOptional = empRepo.findById(id);

        if (existingItemOptional.isPresent()) {
            return new ResponseEntity<>(existingItemOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/save")
    public ResponseEntity<Employee> create(@RequestBody Employee item) {
        try {
        
            Employee savedItem = empRepo.save(item);
            log.info("created Employee {}",item);
            return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> update(@PathVariable("id") int id,
            @RequestBody Employee item) {
        Optional<Employee> existingItemOptional = empRepo.findById(id);
        if (existingItemOptional.isPresent()) {
            Employee existingItem = existingItemOptional.get();
            System.out
                    .println("TODO for developer - update logic is unique to entity and must be implemented manually.");
            // existingItem.setSomeField(item.getSomeField());
            return new ResponseEntity<>(empRepo.save(existingItem), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        try {
            empRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}