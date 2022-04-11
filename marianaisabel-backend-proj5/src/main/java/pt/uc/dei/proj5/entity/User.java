
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
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the Product_Types database table.
 * 
 */
@Entity
@Table(name = "Users")
//@NamedQueries({ @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
//		@NamedQuery(name = "User.findUserByToken", query = "SELECT u FROM User u WHERE  u.token = :token"),
//		@NamedQuery(name = "User.deleteDefinitelyUserByUsername", query = "DELETE FROM User WHERE username = :username"),
//// @NamedQuery(name = "User.updateToNullUserToken", query="UPDATE FROM User set
//// token = null, WHERE u.token = :token" )
//})
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "firstName", nullable = false)
	private String firstName;

	@Column(name = "lastName", nullable = false)
	private String lastName;

//	@Id
//	@Column(name = "username", nullable = false)
//	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Id
	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "image", nullable = false)
	private String image;

	@Column(name = "token", nullable = true) // só tem token se houver login
	private String token;

	// UUID.randomUUID().toString();
	@Column(name = "deleted", nullable = false)
	private boolean deleted;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "notificationType", nullable = false, columnDefinition = "ENUM('PUBLIC','MEMBER','ADMIN')")
	private UserPriv privileges;

	@Column(name = "createDate", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createDate;


//	// bi-directional many-to-one association to Product
//	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) // este fetch EAGER - as lista seguinte não é ignorada ao fazer get do user faz com que as																																																							
//	private List<News> news;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<Notification> notifications;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<ProjectSharing> projectSharing;

	
	public enum UserPriv {PUBLIC,MEMBER,ADMIN};
	
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



	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}





}
