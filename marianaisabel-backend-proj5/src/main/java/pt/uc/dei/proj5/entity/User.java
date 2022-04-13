
package pt.uc.dei.proj5.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the User database table.
 * 
 */
@Entity
@Table(name = "Users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;
	
	@Column(name = "firstName", nullable = false)
	private String firstName;

	@Column(name = "lastName", nullable = false)
	private String lastName;

	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "image", nullable = false)
	private String image;
	
	@Column(name = "biography", nullable = true)
	private String biography;

	@Column(name = "token", nullable = true) // só tem token se houver login
	private String token;

	@Column(name = "deleted", nullable = true, columnDefinition ="BOOLEAN DEFAULT FALSE")
	private boolean deleted;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "UserPriv", nullable = true, columnDefinition = "ENUM('VIEWER','MEMBER','ADMIN') DEFAULT 'VIEWER'")
	private UserPriv privileges;

	@Column(name = "createDate", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdDate;

//fetch => FetchType.EAGER, cascade = CascadeType.REMOVE) // este fetch EAGER - as lista seguinte não é ign
	
	@OneToMany(mappedBy = "createdBy", cascade = CascadeType.REMOVE)
	private List<News> createdNews;
	@OneToMany(mappedBy = "lastModifBy", cascade = CascadeType.REMOVE)
	private List<News> updatedNews;
	
	
	@OneToMany(mappedBy = "createdBy", cascade = CascadeType.REMOVE)
	private List<Project> createdProjects;
	
	@OneToMany(mappedBy = "lastModifBy", cascade = CascadeType.REMOVE)
	private List<Project> updatedProjects;

	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<Notification> notifications;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<ProjectSharing> projectSharing;

	
	
	//OUTRAS IDEIAS: em vez de member- editor/publisher ...
	public enum UserPriv {VIEWER,MEMBER,ADMIN};
	
//	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE) // este fetch faz com que as categorias não sejam ignoradas, por causa do erro do get all users
//	private List<Category> categories;	

	// o código que serializa e desserializa usa o construtor vazio e depois chama
	// os setters e getters. -> usado por exemplo em R3
	/**
	 * Construtor vazio
	 */
	public User() {
		// nothing to do here;
	}

	
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}


	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	public UserPriv getPrivileges() {
		return privileges;
	}

	public void setPrivileges(UserPriv privileges) {
		this.privileges = privileges;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public List<News> getCreatedNews() {
		return createdNews;
	}

	public void setCreatedNews(List<News> createdNews) {
		this.createdNews = createdNews;
	}

	public List<News> getUpdatedNews() {
		return updatedNews;
	}

	public void setUpdatedNews(List<News> updatedNews) {
		this.updatedNews = updatedNews;
	}

	public List<Project> getCreatedProjects() {
		return createdProjects;
	}

	public void setCreatedProjects(List<Project> createdProjects) {
		this.createdProjects = createdProjects;
	}

	public List<Project> getUpdatedProjects() {
		return updatedProjects;
	}

	public void setUpdatedProjects(List<Project> updatedProjects) {
		this.updatedProjects = updatedProjects;
	}

	public List<ProjectSharing> getProjectSharing() {
		return projectSharing;
	}

	public void setProjectSharing(List<ProjectSharing> projectSharing) {
		this.projectSharing = projectSharing;
	}

	public String getBiography() {
		return biography;
	}



	public void setBiography(String biography) {
		this.biography = biography;
	}



	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", password=" + password + ", email=" + email
				+ ", image=" + image + ", token=" + token + ", deleted=" + deleted + ", privileges=" + privileges
				+ ", createdDate=" + createdDate + ", createdNews=" + createdNews + ", updatedNews=" + updatedNews
				+ ", createdProjects=" + createdProjects + ", updatedProjects=" + updatedProjects + ", notifications="
				+ notifications + ", projectSharing=" + projectSharing + "]";
	}



	//@NamedQueries({ @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
//			@NamedQuery(name = "User.findUserByToken", query = "SELECT u FROM User u WHERE  u.token = :token"),
//			@NamedQuery(name = "User.deleteDefinitelyUserByUsername", query = "DELETE FROM User WHERE username = :username"),
	//// @NamedQuery(name = "User.updateToNullUserToken", query="UPDATE FROM User set
	//// token = null, WHERE u.token = :token" )
	//})


}
