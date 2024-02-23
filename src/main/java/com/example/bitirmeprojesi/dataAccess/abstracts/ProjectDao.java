package com.example.bitirmeprojesi.dataAccess.abstracts;

import com.example.bitirmeprojesi.models.concretes.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectDao extends JpaRepository<Project, Long>{

}
