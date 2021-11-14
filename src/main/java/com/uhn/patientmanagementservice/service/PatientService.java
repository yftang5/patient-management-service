package com.uhn.patientmanagementservice.service;

import java.util.List;
import java.util.Optional;

import com.uhn.patientmanagementservice.entity.Patient;

public interface PatientService {
	/**
	 * Create patient
	 * 
	 * @param patient
	 * @return the patient
	 */
	public Patient createPatient(Patient patient);
	
	/**
	 * Get all patients
	 * 
	 * @return the list of patient
	 */
	public List<Patient> getAllPatients();
	
	/**
	 * Get patient by id
	 * 
	 * @param id
	 * @return the patient
	 */
	public Optional<Patient> getPatientById(Long id);
	
	/**
	 * Get patient by first name
	 * 
	 * @param firstName
	 * @return the list of patient
	 */
	public List<Patient> getPatientByFirstName(String firstName);

}
