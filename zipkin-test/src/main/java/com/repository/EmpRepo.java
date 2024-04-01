package com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.model.Employee;

//Remove @RepositoryRestResource below to disable auto REST api:
@Repository
public interface EmpRepo extends CrudRepository<Employee, Integer> {
}