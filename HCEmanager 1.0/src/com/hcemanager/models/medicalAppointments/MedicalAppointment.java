package com.hcemanager.models.medicalAppointments;


import com.hcemanager.models.users.*;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class MedicalAppointment {

    private int idConsult;
    private User patient;
    private User doctor;
    private String date;
    private String weight;
    private String size;
    private String bloodPressure;
    private String temperature;
    private String pulse;
    private String familyHistory;
    private String personalHistory;
    private String habits;
    private String QueryReason;
    private String physicalExamination;
    private String disease;
    private String groupDisease;
    private String observationDisease;

    public int getIdConsult() {return idConsult;}

    public void setIdConsult(int idConsult) {this.idConsult = idConsult;}

    public User getPatient() {return patient;}

    public void setPatient(User patient) {this.patient = patient;}

    public User getDoctor() {return doctor;}

    public void setDoctor(User doctor) {this.doctor = doctor;}

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date;}

    public String getWeight() {return weight;}

    public void setWeight(String weight) {this.weight = weight;}

    public String getSize() {return size;}

    public void setSize(String size) {this.size = size;}

    public String getBloodPressure() {return bloodPressure;}

    public void setBloodPressure(String bloodPressure) {this.bloodPressure = bloodPressure;}

    public String getTemperature() {return temperature;}

    public void setTemperature(String temperature) {this.temperature = temperature;}

    public String getPulse() {return pulse;}

    public void setPulse(String pulse) {this.pulse = pulse;}

    public String getFamilyHistory() {return familyHistory;}

    public void setFamilyHistory(String familyHistory) {this.familyHistory = familyHistory;}

    public String getPersonalHistory() {return personalHistory;}

    public void setPersonalHistory(String personalHistory) {this.personalHistory = personalHistory;}

    public String getQueryReason() {return QueryReason;}

    public void setQueryReason(String queryReason) {this.QueryReason = queryReason;}

    public String getHabits() {return habits;}

    public void setHabits(String habits) {this.habits = habits;}

    public String getPhysicalExamination() {return physicalExamination;}

    public void setPhysicalExamination(String physicalExamination) {this.physicalExamination = physicalExamination;}

    public String getDisease() {return disease;}

    public void setDisease(String disease) {this.disease = disease;}

    public String getGroupDisease() {return groupDisease;}

    public void setGroupDisease(String groupDisease) {this.groupDisease = groupDisease;}

    public String getObservationDisease() {return observationDisease;}

    public void setObservationDisease(String observationDisease) {this.observationDisease = observationDisease;}
}
