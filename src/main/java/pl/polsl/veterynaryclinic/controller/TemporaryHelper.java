package pl.polsl.veterynaryclinic.controller;

import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.model.Appointment;
import pl.polsl.veterynaryclinic.model.Patient;
import pl.polsl.veterynaryclinic.model.User;

import java.util.*;

public class TemporaryHelper {

    public ArrayList<Patient> getUserPets() {
        User owner = new User();

        Patient a = new Patient();
        a.setPatientId(1);
        a.setPatientName("Azor");
        a.setOwner(owner);

        Patient b = new Patient();
        b.setPatientId(2);
        b.setPatientName("Bruno");
        b.setOwner(owner);

        Patient c = new Patient();
        c.setPatientId(3);
        c.setPatientName("Mruczek");
        c.setOwner(owner);

        Patient d = new Patient();
        d.setPatientId(4);
        d.setPatientName("Filemon");
        d.setOwner(owner);

        Patient e = new Patient();
        e.setPatientId(5);
        e.setPatientName("Abc");
        e.setOwner(owner);

        Patient f = new Patient();
        f.setPatientId(6);
        f.setPatientName("Def");
        f.setOwner(owner);

        Patient g = new Patient();
        g.setPatientId(7);
        g.setPatientName("Ghi");
        g.setOwner(owner);

        return new ArrayList<>(Arrays.asList(a, b, c, d, e, f, g));
    }

    public Patient getEditedPatient(Integer patientId) {
        TemporaryHelper temporaryHelper = new TemporaryHelper();
        List<Patient> patientList = temporaryHelper.getUserPets();
        for (Patient patient : patientList) {
            if (patient.getPatientId().equals(patientId))
                return patient;
        }
        return null;
    }

    public ArrayList<Appointment> getUserPastAppointments() {

        User doctor1 = new User("Zbigniew", "Religa", UserType.DOCTOR_ID, "b", "b", "123456789");
        User doctor2 = new User("James", "Watson", UserType.DOCTOR_ID, "b", "b", "123456789");


        Patient patient1 = new Patient();
        patient1.setPatientName("Azor");

        Patient patient2 = new Patient();
        patient2.setPatientName("Bruno");

        int year = 2018;
        int month = 1;
        int date = 12;
        int hrs = 12;
        int min = 30;

        Appointment a = new Appointment(1.0f, new Date(year, month, date, hrs, min), 1, doctor1, patient1);
        month++;
        Appointment b = new Appointment(2.0f, new Date(year, month, date, hrs, min), 1, doctor1, patient2);
        year++;
        Appointment c = new Appointment(3.5f, new Date(year, month, date, hrs, min), 1, doctor2, patient1);
        hrs = 15;
        min = 0;
        Appointment d = new Appointment(3.5f, new Date(year, month, date, hrs, min), 1, doctor2, patient2);

        return new ArrayList<>(Arrays.asList(a, b, c, d));
    }

    public ArrayList<User> getAllDoctors() {
        User doctor1 = new User("Zbigniew", "Religa", 'L', "b", "b", "123456789");
        doctor1.setUserId(1);
        User doctor2 = new User("James", "Watson", 'L', "b", "b", "123456789");
        doctor2.setUserId(2);

        return new ArrayList<>(Arrays.asList(doctor1, doctor2));
    }

    public ArrayList<Appointment> getDoctorAppointmentHistory(User doctor) {

//      TODO: Dodać zapytanie wyszukujące wszystkie zaplanowane wizyty do konkretnego doktora
        doctor = new User("Hans", "Zimmer", 'L', "b", "b", "123456789");

        User owner1 = new User("Andrzej", "Psiara", UserType.CLIENT_ID, "a", "a", "123456789");
        owner1.setUserId(1);
        User owner2 = new User("Jerzy", "Kociarz", UserType.CLIENT_ID, "a", "a", "123456789");
        owner2.setUserId(2);


        Patient patient1 = new Patient();
        patient1.setPatientName("Borys");
        patient1.setOwner(owner1);
        patient1.setPatientId(1);

        Patient patient3 = new Patient();
        patient3.setPatientName("Maja");
        patient3.setOwner(owner2);
        patient3.setPatientId(3);

        Patient patient2 = new Patient();
        patient2.setPatientName("Szafir");
        patient2.setOwner(owner2);
        patient2.setPatientId(2);



        int year = 2018;
        int month = Calendar.MARCH;
        int date = 11;
        int hrs = 10;
        int min = 15;

        Appointment a = new Appointment(1.0f, new Date(year, month, date, hrs, min), 1, doctor, patient1);
        month = Calendar.APRIL;
        Appointment b = new Appointment(10.0f, new Date(year, month, date, hrs, min), 1, doctor, patient2);
        month = Calendar.JUNE;
        date = 22;
        hrs = 15;
        Appointment c = new Appointment(115.0f, new Date(year, month, date, hrs, min), 1, doctor, patient3);
        return new ArrayList<>(Arrays.asList(a, b, c));
    }

    public ArrayList<Appointment> getFutureDoctorAppointments(User doctor) {

//      TODO: Dodać zapytanie wyszukujące wszystkie zaplanowane wizyty do konkretnego doktora
        doctor = new User("Hans", "Zimmer", 'L', "b", "b", "123456789");

        User owner1 = new User("Adam", "Psiara", UserType.CLIENT_ID, "a", "a", "123456789");
        owner1.setUserId(1);
        User owner2 = new User("Grzegorz", "Kociarz", UserType.CLIENT_ID, "a", "a", "123456789");
        owner2.setUserId(2);


        Patient patient1 = new Patient();
        patient1.setPatientName("Azor");
        patient1.setOwner(owner1);
        patient1.setPatientId(1);

        Patient patient2 = new Patient();
        patient2.setPatientName("Bruno");
        patient2.setOwner(owner2);
        patient2.setPatientId(2);

        Patient patient3 = new Patient();
        patient3.setPatientName("Maja");
        patient3.setOwner(owner2);
        patient3.setPatientId(3);

        int year = 2019;
        int month = Calendar.MARCH;
        int date = 11;
        int hrs = 10;
        int min = 15;

        Appointment a = new Appointment(0f, new Date(year, month, date, hrs, min), 0, doctor, patient1);
        month = Calendar.APRIL;
        Appointment b = new Appointment(0f, new Date(year, month, date, hrs, min), 0, doctor, patient2);
        month = Calendar.JUNE;
        date = 22;
        hrs = 15;
        Appointment c = new Appointment(0f, new Date(year, month, date, hrs, min), 0, doctor, patient3);
        return new ArrayList<>(Arrays.asList(a, b, c));
    }

    public ArrayList<User> getOwners(List<Appointment> appointments){
        ArrayList<User> owners = new ArrayList<>();
        for (Appointment appointment: appointments) {
            User owner = appointment.getPatient().getOwner();
            if (!owners.contains(owner)) {
                owners.add(owner);
            }
        }
        return owners;
    }

    public ArrayList<Appointment> getFutureClientAppointments(List<Appointment> appointments, int clientId){
        ArrayList<Appointment> clientAppointments = new ArrayList<>();
        for (Appointment appointment: appointments){
            if (appointment.getPatient().getOwner().getUserId() == clientId){
                clientAppointments.add(appointment);
            }
        }
        return clientAppointments;
    }

    public ArrayList<Patient> getPatients(List<Appointment> appointments){
        ArrayList<Patient> patients = new ArrayList<>();
        for (Appointment appointment: appointments){
            Patient patient = appointment.getPatient();
            if (!patients.contains(patient)){
                patients.add(patient);
            }
        }
        return patients;
    }

    public ArrayList<Appointment> getFuturePatientsAppointments(List<Appointment> appointments, int patientId){
        ArrayList<Appointment> patientAppointments = new ArrayList<>();
        for (Appointment appointment: appointments) {
            if (appointment.getPatient().getPatientId() == patientId){
                patientAppointments.add(appointment);
            }
        }
        return patientAppointments;
    }

}










