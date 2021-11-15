package com.uhn.patientmanagementservice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uhn.patientmanagementservice.controller.PatientController;
import com.uhn.patientmanagementservice.entity.Patient;
import com.uhn.patientmanagementservice.service.PatientService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper mapper;
	
	@MockBean
	PatientService patientService;
//	PatientDao patientRepository;
	
	
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
	public void retrievePatients_success() throws Exception{
		List<Patient> patients = new ArrayList<>(Arrays.asList(patient1, patient2, patient3));
		Mockito.when(patientService.getAllPatients()).thenReturn(patients);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/patients")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[1].lastName", is ("Fung")));
	}
	
	@Test
	public void retrievePatientById_success() throws Exception{
		Mockito.when(patientService.getPatientById(2L)).thenReturn(java.util.Optional.of(patient2));
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/patients/2")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.firstName", is ("John")))
				.andExpect(jsonPath("$.lastName", is ("Fung")));
	}
	
	@Test
	public void retrievePatientById_notFound() throws Exception{
		Mockito.when(patientService.getPatientById(5L)).thenReturn(java.util.Optional.empty());
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/patients/5")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void retrievePatients_byFirstName_success() throws Exception{
		List<Patient> patients = new ArrayList<>(Arrays.asList(patient3));
		Mockito.when(patientService.getPatientByFirstName("david")).thenReturn(patients);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/patients?firstname=david")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$[0].firstName", is ("David")))
				.andExpect(jsonPath("$[0].lastName", is ("Watson")));
	}
	
	@Test
	public void retrievePatients_byFirstName_notFound() throws Exception{
		List<Patient> patients = new ArrayList<>();
		Mockito.when(patientService.getPatientByFirstName("Dave")).thenReturn(patients);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/patients/findByFirstName/Dave")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void createPatient_success() throws Exception{
		Patient patientPayload = new Patient(null, "Andrew", "Parker", parseDate("2000-05-14"));
		Patient mockCreatedPatient = new Patient(5L, "Andrew", "Parker", parseDate("2000-05-14"));
		
		Mockito.when(patientService.createPatient(any(Patient.class))).thenReturn(mockCreatedPatient);
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/patients")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(patientPayload));
		
		mockMvc.perform(mockRequest)
				.andExpect(status().isCreated());
	}

}
