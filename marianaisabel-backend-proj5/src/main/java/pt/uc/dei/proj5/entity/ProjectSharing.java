package pt.uc.dei.proj5.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
* The persistent class for the Notifications database table.
* 
*/
@Entity// classe que vai ter uma ligaçao a um data source
@Table(name="ProjectSharing") //nome da tabela java é no singular, tabela é no plural
public class ProjectSharing implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;
	
	@Column(name = "accepted", nullable = false)
	private boolean accepted;

	@ManyToOne(optional = false)
	@JoinColumn(name = "User_ProjectSharing")
	private User user;

	@ManyToOne
	@JoinColumn(name = "Project_ProjectSharing")
	private News project;

	@OneToMany(mappedBy = "projectSharing", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Notification> notifications;

	
	@Column(name="createDate", nullable=false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createDate;
	
	public ProjectSharing() {
		
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public User getUserJoinSharing() {
		return user;
	}

	public void setUserJoinSharing(User user) {
		this.user = user;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}



}
