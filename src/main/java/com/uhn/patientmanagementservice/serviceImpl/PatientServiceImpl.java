package com.uhn.patientmanagementservice.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uhn.patientmanagementservice.dao.PatientDao;
import com.uhn.patientmanagementservice.entity.Patient;
import com.uhn.patientmanagementservice.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService{
	
	@Autowired
	private PatientDao patientDao;
	
	
	public Patient createPatient(Patient patient) {
		return patientDao.save(patient);
	}
	
	public List<Patient> getAllPatients() {
		return patientDao.findAll();
	}
	
	public Optional<Patient> getPatientById(Long id) {
		return patientDao.findById(id);
	}
	
	public List<Patient> getPatientByFirstName(String firstName) {
		return patientDao.findByFirstName(firstName);
	}
}
