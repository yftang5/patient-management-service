package com.uhn.patientmanagementservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uhn.patientmanagementservice.entity.Patient;

@Repository
public interface PatientDao extends JpaRepository<Patient, Long>{
	
	
	/**
	 * Find patient by first name
	 * 
	 * @param firstName
	 * @return
	 */
	@Query("SELECT p FROM Patient p WHERE LOWER(p.firstName) = LOWER(:firstName)")
    public List<Patient> findByFirstName(@Param("firstName") String firstName);
}
