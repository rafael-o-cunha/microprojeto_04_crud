package praticas.microprojeto_04.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_pets")
public class Pet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false, length = 120)
	private String name;
	
	@Column(name = "species", length = 80)
	private String species;
	
	@Column(name = "breed", length = 120)
	private String breed;
	
	@Column(name = "color", length = 80)
	private String color;
	
	@Column(name = "weight", precision = 10, scale = 2)
	private BigDecimal weight;
	
	@Column(name = "vaccinated")
	private Boolean vaccinated;
	
	@Column(name = "birth_date")
	private LocalDate birthDate;

	@Column(name = "nomes", columnDefinition = "TEXT")
	private String notes;
	
	@Column(name = "deleted")
	private Boolean deleted;
	
	@Column(name = "created_at", updatable = false)
	private LocalDateTime created_at;
	
	@Column(name = "updated_at")
	private LocalDateTime updated_at;
}
