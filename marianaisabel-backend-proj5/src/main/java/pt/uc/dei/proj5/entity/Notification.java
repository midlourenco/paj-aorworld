package pt.uc.dei.proj5.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the Notifications database table.
 * 
 */
@Entity // classe que vai ter uma ligaçao a um data source
@Table(name = "Notification") // nome da tabela java é no singular, tabela é no plural
@NamedQuery(name = "Notification.findAll", query = "SELECT n FROM Notification n")
public class Notification implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "alreadyRead", nullable = false)
	private boolean alreadyRead;
	
	@Column(name = "createDate", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdDate;

	@Column(name = "finalDate")
	private Timestamp finalDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "notificationType", nullable = true, columnDefinition = "ENUM('ACTIVITY_REMINDER', 'ACTIVITY_END', 'CATEGORY_SHARING', 'CATEGORY_ACCEPTED','CATEGORY_REJECTED')")
	private NotificationType notificationType;

	public enum NotificationType {
		ACTIVITY_REMINDER, ACTIVITY_END, CATEGORY_SHARING, CATEGORY_ACCEPTED, CATEGORY_REJECTED
	}

	@ManyToOne(optional = false) // 1 notificação tem obrigatoriamente 1 user
	@JoinColumn(name = "User_Notification")
	private User user;

	@ManyToOne // 1 notificação pode ter 1 noticia ou 1 partilha de projecto
	@JoinColumn(name = "News_Notification", nullable = true)
	private News news;

	@ManyToOne // 1 notificação pode ter 1 noticia ou 1 partilha de projecto
	@JoinColumn(name = "ProjectSharing_Notification", nullable = true)
	private ProjectSharing projectSharing;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public NotificationType getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAlreadyRead() {
		return alreadyRead;
	}

	public void setAlreadyRead(boolean alreadyRead) {
		this.alreadyRead = alreadyRead;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(Timestamp finalDate) {
		this.finalDate = finalDate;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public ProjectSharing getProjectSharing() {
		return projectSharing;
	}

	public void setProjectSharing(ProjectSharing projectSharing) {
		this.projectSharing = projectSharing;
	}

	@Override
	public String toString() {
		return "Notification [id=" + id + ", title=" + title + ", description=" + description + ", alreadyRead="
				+ alreadyRead + ", createdDate=" + createdDate + ", finalDate=" + finalDate + ", notificationType="
				+ notificationType + ", user=" + user + ", news=" + news + ", projectSharing=" + projectSharing + "]";
	}
	

}