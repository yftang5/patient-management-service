package com.uhn.patientmanagementservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uhn.patientmanagementservice.dao.PatientDao;
import com.uhn.patientmanagementservice.entity.Patient;
import com.uhn.patientmanagementservice.serviceImpl.PatientServiceImpl;

@WebMvcTest(PatientServiceImpl.class)
public class PatientServiceTest {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper mapper;
	
	@MockBean
	PatientDao patientDao;
	
	@Autowired
	PatientServiceImpl patientService;
	
	public static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("yyyy-MM-dd").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }
	Patient patient1 = new Patient(1L, "Jay", "Chan", parseDate("1998-02-17"));
	Patient patient2 = new Patient(2L, "John", "Fung", parseDate("1990-01-24"));
	Patient patient3 = new Patient(3L, "David", "Watson", parseDate("1999-11-14"));
	@Test
	public void createPatient_success() {
		Patient mockedInput = new Patient(null, "Andrew", "Parker", parseDate("2000-05-14"));
		Patient mockedPatient = new Patient(5L, "Andrew", "Parker", parseDate("2000-05-14"));
		Mockito.when(patientDao.save(any(Patient.class))).thenReturn(mockedPatient);
		
		Patient created = patientService.createPatient(mockedInput);
		assertEquals(created.getId(), mockedPatient.getId());
	}
	
	@Test
	public void getAllPatients_success() {
		List<Patient> mocked = new ArrayList<>(Arrays.asList(patient1, patient2, patient3));
		Mockito.when(patientDao.findAll()).thenReturn(mocked);
		
		List<Patient> retrieved = patientService.getAllPatients();
		assertEquals(retrieved.size(), mocked.size());
	}
	
	@Test
	public void getPatientById_success() {
		Mockito.when(patientDao.findById(3L)).thenReturn(java.util.Optional.of(patient3));
		Optional<Patient> retrieved = patientService.getPatientById(3L);
		
		assertEquals(retrieved.get(), patient3);
	}
	
	@Test
	public void getPatientByFirstName_success() {
		List<Patient> mocked = new ArrayList<>(Arrays.asList(patient3));
		Mockito.when(patientDao.findByFirstName("david")).thenReturn(mocked);
		List<Patient> retrieved = patientService.getPatientByFirstName("david");
		assertEquals(retrieved.get(0), mocked.get(0));
		
	}
}
