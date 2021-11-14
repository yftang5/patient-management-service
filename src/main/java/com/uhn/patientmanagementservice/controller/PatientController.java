package com.uhn.patientmanagementservice.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.uhn.patientmanagementservice.entity.Patient;
import com.uhn.patientmanagementservice.exception.PatientNotFoundException;
import com.uhn.patientmanagementservice.service.PatientService;

@RequestMapping(value = "/patients")
@RestController
public class PatientController {
	@Autowired
	private PatientService patientService;
	
	/**
	 * Create patient.
	 * 
	 * @param patient the patient
	 * @return the response entity
	 */
	@PostMapping
	public ResponseEntity<Object> createPatient(@Valid @RequestBody Patient patient) {
		Patient savedPatient = patientService.createPatient(patient);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPatient.getId()).toUri();
		return ResponseEntity.created(location).build();
	}


	/**
	 * Get patient by id
	 * 
	 * @param id the patient id
	 * @return the patient by id
	 */
	@GetMapping(path="/{id}")
	public ResponseEntity<Object> retrievePatientById(@PathVariable("id") Long id){
		Optional<Patient> patient = patientService.getPatientById(id);
		
		if(!patient.isPresent()) {
			throw new PatientNotFoundException("Resource with id-"+ id + " is not found");
		}	
		return new ResponseEntity<>(patient, HttpStatus.OK);
	}
	
	/**
	 * Get all patients or Get patients by first name
	 * 
	 * @param firstName the first name of the patient
	 * @return the list of patient
	 */
	@GetMapping
	public ResponseEntity<Object> retrievePatients(@RequestParam(value="firstname", required = false) String firstName){
		if(firstName==null || "".equals(firstName)) {
			return new ResponseEntity<>(patientService.getAllPatients(), HttpStatus.OK);
		}
		List<Patient> patients = patientService.getPatientByFirstName(firstName);
		if(patients.isEmpty()) {
			throw new PatientNotFoundException("Resource with firstName-"+ firstName + " is not found");
		}
		return new ResponseEntity<>(patients, HttpStatus.OK);
	}
}
