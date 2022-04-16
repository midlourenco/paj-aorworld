package pt.uc.dei.proj5.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * The persistent class for the News database table.
 * 
 */
@Entity // classe que vai ter uma ligaçao a um data source
@Table(name = "News") 	
public class News implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private int id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "image", nullable = true)
	private String image;
	
	@Column(name = "deleted", nullable = false)
	private boolean deleted;
	
	@Column(name = "visibility", nullable = false)
	private boolean visibility;

	@Column(name = "createdDate", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdDate;

	@Column(name = "lastModifDate", nullable = true )
	private Timestamp lastModifDate;
	
	@ManyToOne (optional=false)  //presença obrigatória na relação
	@JoinColumn(name="CreatedBy_News")
	private User createdBy;
	
	@ManyToOne (optional=true)  //presença obrigatória na relação
	@JoinColumn(name="UpdatedBy_News")
	private User lastModifBy;
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany
	private List<Project> projects;
	
	//alternativa	private String keywords;
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(cascade = CascadeType.MERGE)
	private Set<Keyword> keywords;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "news", cascade = CascadeType.REMOVE) // cada news tem uma muitas notificaçoes associados. por isso tem uma lista de produtos
	private List<Notification> notifications;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="news",cascade = CascadeType.REMOVE)
	private List<NewsSharing> projectSharing;
	
	
	public News() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getLastModifBy() {
		return lastModifBy;
	}

	public void setLastModifBy(User lastModifBy) {
		this.lastModifBy = lastModifBy;
	}
	public void setLastModifByAndDate(User lastModifBy) {
		this.lastModifBy = lastModifBy;
		this.lastModifDate = new Timestamp(System.currentTimeMillis());
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public Set<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<Keyword> keywords) {
		this.keywords = keywords;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}
	
	public Timestamp getLastModifDate() {
		return lastModifDate;
	}

	public void setLastModifDate(Timestamp lastModifDate) {
		this.lastModifDate = lastModifDate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isVisibility() {
		return visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", title=" + title + ", description=" + description + ", image=" + image
				+ ", deleted=" + deleted + ", visibility=" + visibility + ", createdDate=" + createdDate
				+ ", lastModifDate=" + lastModifDate + ", createdBy=" + createdBy + ", lastModifBy=" + lastModifBy
				+ ", projects=" + projects + ", keywords=" + keywords + ", notifications=" + notifications + "]";
	}




}
