package numeryx.fr.mybackend.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import numeryx.fr.mybackend.entities.enums.ESpecialist;
import jakarta.persistence.*;

@Entity
@Table(name = "specialist")
public class Specialist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ESpecialist name;
	
	@OneToOne(mappedBy = "specialization", fetch = FetchType.EAGER)
	@JsonBackReference
	public Doctor doctor;
	
	public Specialist() {}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Specialist(Long id, ESpecialist name) {
		this.id = id;
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ESpecialist getName() {
		return name;
	}
	public void setName(ESpecialist name) {
		this.name = name;
	}
	



	
	
}
