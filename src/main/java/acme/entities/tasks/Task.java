package acme.entities.tasks;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Task extends DomainEntity{
	 
	protected static final long serialVersionUID = 1L;
	
	@NotBlank
	@Length(min = 2, max = 80)
	protected String title;
	
	@NotBlank
	protected String visibility;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Future
	protected Date				startDate;

	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Future
	protected Date				endDate;
	
	@Digits(integer = 2, fraction = 2)
	protected Double workload;

	@NotBlank
	@Length(min = 8, max = 500)
	protected String description;
	
	@URL
	protected String optionalLink;
	

	
	
	

}