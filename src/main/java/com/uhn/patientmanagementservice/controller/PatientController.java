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

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Tag(name="Patient", description="the patient API with documentation annotations")
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
	@Operation(summary = "Add a patient")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "201", description = "Patient added", 
			    content = { @Content(mediaType = "application/json", 
			      schema = @Schema(implementation = Patient.class)) }),
			  @ApiResponse(responseCode = "400", description = "Validation failed", 
			    content = @Content)})
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
	@Operation(summary = "Get patient by its id")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Found the patient", 
			    content = { @Content(mediaType = "application/json", 
			      schema = @Schema(implementation = Patient.class)) }),
			  @ApiResponse(responseCode = "400", description = "Invalid id supplied", 
			    content = @Content), 
			  @ApiResponse(responseCode = "404", description = "Patient not found", 
			    content = @Content) })
	@GetMapping(path="/{id}")
	public ResponseEntity<Object> retrievePatientById(@Parameter(description = "id of patient to be searched") @PathVariable("id") Long id){
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
	@Operation(summary = "Get all patients or filtered by first name")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Found patients", 
			    content = { @Content(mediaType = "application/json", 
			      schema = @Schema(implementation = Patient.class)) }),
			  @ApiResponse(responseCode = "404", description = "Patient not found", 
			    content = @Content) })
	@GetMapping
	public ResponseEntity<Object> retrievePatients(@RequestParam(value="firstname", required = false) String firstName){
		List<Patient> patients;
		if(firstName==null || "".equals(firstName)) {
			patients = patientService.getAllPatients();
			if(patients.isEmpty()) {
				throw new PatientNotFoundException("Patient not found");
			}
			return new ResponseEntity<>(patients, HttpStatus.OK);
		}
		patients = patientService.getPatientByFirstName(firstName);
		if(patients.isEmpty()) {
			throw new PatientNotFoundException("Resource with firstName-"+ firstName + " is not found");
		}
		return new ResponseEntity<>(patients, HttpStatus.OK);
	}
}
