package com.prodyna.person.repository;

import java.util.UUID;

import com.prodyna.person.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {

}
