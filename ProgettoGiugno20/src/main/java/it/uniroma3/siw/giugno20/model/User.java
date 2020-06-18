package it.uniroma3.siw.giugno20.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	@Column(nullable=false,length=100)
	private String firstName;
	@Column(nullable=false,length=100)
	private String lastName;
	@Column(nullable=false,updatable = false)
	private LocalDateTime creationDate;
	
	@ManyToMany(mappedBy = "members")
	private List<Project> visibleProject;
	@OneToMany(mappedBy = "owner",cascade=CascadeType.REMOVE)
	private List<Project> ownedProject;	
	@OneToMany
	@JoinColumn(name = "user_task_id")
	private List<Task> activeTasks;
	
	public User () {
		this.visibleProject= new ArrayList<Project>();
		this.ownedProject = new ArrayList<Project>();
		this.activeTasks = new ArrayList<Task>();
	}
	
	public User(String firstName, String lastName) {
		this();
		this.firstName= firstName;
		this.lastName=lastName;
				
	}
	
	@PrePersist
	public void onPersist() {
		this.creationDate= LocalDateTime.now();
	}
	public void addVisibleProject(Project p) {
		this.visibleProject.add(p);
	}
	
	public void addOwnedProject(Project p) {
		this.ownedProject.add(p);
	}
	
	public void addTask(Task t) {
		this.activeTasks.add(t);
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public List<Project> getVisibleProject() {
		return visibleProject;
	}

	public void setVisibleProject(List<Project> visibleProject) {
		this.visibleProject = visibleProject;
	}

	public List<Project> getOwnerProject() {
		return ownedProject;
	}

	public void setOwnerProject(List<Project> ownerProject) {
		this.ownedProject = ownerProject;
	}
	

	public List<Task> getActiveTasks() {
		return activeTasks;
	}

	public void setActiveTasks(List<Task> activeTasks) {
		this.activeTasks = activeTasks;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}
	
	
	
	
	
	
}
