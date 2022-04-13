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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the News database table.
 * 
 */
@Entity // classe que vai ter uma ligaçao a um data source
@Table(name = "News") // nome da tabela java é no singular, tabela é no plural	
public class News implements Serializable {

	private static final long serialVersionUID = 1L;
//	private String activityId = UUID.randomUUID().toString();
	// @GeneratedValue
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private int id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "checked", nullable = false)
	private boolean checked;

	@Column(name = "deleted", nullable = false)
	private boolean deleted;

	@Column(name = "createDate", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdDate;


//	@Column(name = "Category", nullable = false)
	// bi-directional many-to-one association to Product_Type
//	@ManyToOne(optional = false)
//	@JoinColumn(name = "Category_Activity")
//	private Category category;

	
	@ManyToOne (optional=false)  //presença obrigatória na relação
	@JoinColumn(name="CreatedBy_News")
	private User createdBy;
	
	@ManyToOne (optional=false)  //presença obrigatória na relação
	@JoinColumn(name="UpdatedBy_News")
	private User lastModifBy;

	@ManyToMany(cascade = CascadeType.REMOVE)
	private List<Project> projects;
	
	@ManyToMany(cascade = CascadeType.REMOVE)
	private List<Keyword> keywords;
	
	@OneToMany(mappedBy = "news", cascade = CascadeType.REMOVE) // cada news tem uma muitas notificaçoes associados. por isso tem uma lista de produtos
	private List<Notification> notifications;

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

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
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

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<Keyword> keywords) {
		this.keywords = keywords;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", title=" + title + ", description=" + description + ", checked=" + checked
				+ ", deleted=" + deleted + ", createdDate=" + createdDate + ", createdBy=" + createdBy
				+ ", lastModifBy=" + lastModifBy + ", projects=" + projects + ", keywords=" + keywords
				+ ", notifications=" + notifications + "]";
	}




}
