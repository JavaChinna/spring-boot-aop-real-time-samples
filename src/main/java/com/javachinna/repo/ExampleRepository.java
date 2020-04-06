package com.javachinna.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javachinna.model.ExampleEntity;

@Repository
public interface ExampleRepository extends JpaRepository<ExampleEntity, Long> {

}
