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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import javax.persistence.Table;



/**
* The persistent class for the Project database table.
* 
*/
@Entity// classe que vai ter uma ligaçao a um data source
@Table(name="Project") //nome da tabela java é no singular, tabela é no plural
public class Project implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;
	
	@Column(name = "categoryName", nullable = false)
	private String categoryName;
	
	@Column(name = "deleted", nullable = false)
	private boolean deleted;
	
	@Column(name="createDate", nullable=false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdDate;
	
	
	@Column(name = "start_date", nullable = true)
	private Timestamp startDate;

	@Column(name = "end_date", nullable = true)
	private Timestamp endDate;
	
	
	//@Column(name = "Activities", nullable = true)
	//bi-directional many-to-one association to A
//	@OneToMany(mappedBy="project",cascade = CascadeType.REMOVE)
//	private List<News> news;
	
	

	@ManyToOne (optional=false)  //presença obrigatória na relação
	@JoinColumn(name="CreatedBy_Project")
	private User createdBy;
	
	@ManyToOne (optional=false)  //presença obrigatória na relação
	@JoinColumn(name="UpdatedBy_Project")
	private User lastModifBy;

	
	
	@OneToMany(mappedBy="project",cascade = CascadeType.REMOVE)
	private List<ProjectSharing> projectSharing;
	

	@ManyToMany(mappedBy="projects",cascade = CascadeType.REMOVE)
	private List<News> news;
	
	
	@ManyToMany(cascade = CascadeType.REMOVE)
	private List<Keyword> keywords;


	
	public Project() {
		
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
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

	public List<ProjectSharing> getProjectSharing() {
		return projectSharing;
	}

	public void setProjectSharing(List<ProjectSharing> projectSharing) {
		this.projectSharing = projectSharing;
	}

	public List<News> getNews() {
		return news;
	}

	public void setNews(List<News> news) {
		this.news = news;
	}

	public List<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<Keyword> keywords) {
		this.keywords = keywords;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", categoryName=" + categoryName + ", deleted=" + deleted + ", createdDate="
				+ createdDate + ", startDate=" + startDate + ", endDate=" + endDate + ", createdBy=" + createdBy
				+ ", lastModifBy=" + lastModifBy + ", projectSharing=" + projectSharing + ", news=" + news
				+ ", keywords=" + keywords + "]";
	}

	

	

	
}
