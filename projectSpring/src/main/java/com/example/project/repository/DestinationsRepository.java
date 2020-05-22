package com.example.project.repository;

import com.example.project.entity.DestinationProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface DestinationsRepository extends JpaRepository<DestinationProperty,Long> {



}
