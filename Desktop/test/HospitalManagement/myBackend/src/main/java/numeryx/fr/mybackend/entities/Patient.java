package numeryx.fr.mybackend.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "patients")
public class Patient implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String symptoms;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "person_id", referencedColumnName = "id")
	private Person person;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "doctor_patients", joinColumns = @JoinColumn(name = "patient_id"), inverseJoinColumns = @JoinColumn(name = "doctor_id"))
	private Doctor doctor;

	
	public Patient() {
	}

	public Patient(Long id, String symptoms, Person person) {
		this.id = id;
		this.symptoms = symptoms;
		this.person = person;
	}



	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public String getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
