package it.uniroma3.siw.giugno20.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

@Entity
public class Project {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(nullable=false,length=50)
	private String name;
	@Column(nullable=false,updatable = false)
	private LocalDateTime creationDate;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private User owner;
	@ManyToMany
	private List<User> members;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="project_id")
	private List<Task> tasks;
	
	@OneToMany
	@JoinColumn(name="project_tag_id" )
	private List<Tag> tags;
	
	
	public Project () {
		this.members= new ArrayList<User>();
		this.tasks = new ArrayList<Task>();
		this.tags = new ArrayList<>();
		
	}
	
	public Project(String name) {
		this();
		this.name=name;
	}
	
	@PrePersist
	public void onPersist() {
		this.creationDate= LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getCreationData() {
		return creationDate;
	}

	public void setCreationData(LocalDateTime creationData) {
		this.creationDate = creationData;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	
	public void addMember(User newMember) {
		this.members.add(newMember);
	}
	
	

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public List<Tag> getTag() {
		return tags;
	}

	public void setTag(List<Tag> tag) {
		this.tags = tag;
	}
	
	public void addTag(Tag t) {
		this.tags.add(t);
	}
	
	public void addTask(Task t) {
		tasks.add(t);
	}
	
	
	
	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Project other = (Project) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}




	
	
}
