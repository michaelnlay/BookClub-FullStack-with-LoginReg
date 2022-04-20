package com.ml.bookclub.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
    
@Entity 
@Table(name="books")
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message="The title cannot be emptied.")
	/*
	 * @Size(min=3, max=30, message="Username must be between 3 and 30 characters")
	 */
    private String title;
    
    @NotEmpty(message="The author cannot be emptied.")
	/*
	 * @Size(min=3, max=30, message="Username must be between 3 and 30 characters")
	 */
    private String author;
    
    @NotEmpty(message="The thoughts cannot be emptied.")
	/*
	 * @Size(min=3, max=1000,
	 * message="Username must be between 3 and 1000 characters")
	 */
    private String myThought;
    
  
    // This will not allow the createdAt column to be updated after creation
    @Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;
    
    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }

	@PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
	
	
	//============================================================================
	//Many to One
	//============================================================================
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;

	
	
	//============================================================================
	//CONSTRUCTORS
	//============================================================================

	public Book() {
	}

	public Book(
			@NotEmpty(message = "The title cannot be emptied.") @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters") String title,
			@NotEmpty(message = "The author cannot be emptied.") @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters") String author,
			@NotEmpty(message = "The thoughts cannot be emptied.") @Size(min = 3, max = 1000, message = "Username must be between 3 and 1000 characters") String myThought,
			User user) {
		super();
		this.title = title;
		this.author = author;
		this.myThought = myThought;
		this.user = user;
	}

	public Book(Long id,
			@NotEmpty(message = "The title cannot be emptied.") @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters") String title,
			@NotEmpty(message = "The author cannot be emptied.") @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters") String author,
			@NotEmpty(message = "The thoughts cannot be emptied.") @Size(min = 3, max = 1000, message = "Username must be between 3 and 1000 characters") String myThought,
			Date createdAt, Date updatedAt, User user) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.myThought = myThought;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.user = user;
	}



	
	
	//============================================================================
	//GETTERS & SETTERS
	//============================================================================
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getMyThought() {
		return myThought;
	}

	public void setMyThought(String myThought) {
		this.myThought = myThought;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    
    
  
}
    
