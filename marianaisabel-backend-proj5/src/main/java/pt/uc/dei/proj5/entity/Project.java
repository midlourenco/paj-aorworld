package pt.uc.dei.proj5.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;



/**
* The persistent class for the Project database table.
* 
*/
@Entity// classe que vai ter uma ligaçao a um data source
@Table(name="Project") //nome da tabela java é no singular, tabela é no plural
public class Project implements Serializable{
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
	
	@Column(name = "visibility", nullable = false) //true if public, false if private
	private boolean visibility;

	@Column(name = "createdDate", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdDate;

	@Column(name = "lastModifDate", nullable = true )
	private Timestamp lastModifDate;

	
	
	//TODO se houver tempo pode-se adicionar uma linha temporal para os projectos:
//	@Column(name = "start_date", nullable = true)
//	private Timestamp startDate;
//
//	@Column(name = "end_date", nullable = true)
//	private Timestamp endDate;
//	
//	
	

	@ManyToOne (optional=false)  //presença obrigatória na relação
	@JoinColumn(name="CreatedBy_Project")
	private User createdBy;
	
	@ManyToOne (optional=true)  //presença não é  obrigatória na relação
	@JoinColumn(name="UpdatedBy_Project")
	private User lastModifBy;

	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="project",cascade = CascadeType.REMOVE)
	private List<ProjectSharing> projectSharing;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(mappedBy="projects")
	private Set<News> news=new HashSet<>();
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(cascade = CascadeType.MERGE)
//	@JoinTable(name="Project_Keyword", joinColumns={@JoinColumn(name="projects_id", referencedColumnName="id")},
//	inverseJoinColumns={@JoinColumn(name="keywords_keyword", referencedColumnName="keyword")})
	private Set<Keyword> keywords=new HashSet<>();;


	
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

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

//	public Timestamp getStartDate() {
//		return startDate;
//	}
//
//	public void setStartDate(Timestamp startDate) {
//		this.startDate = startDate;
//	}
//
//	public Timestamp getEndDate() {
//		return endDate;
//	}
//
//	public void setEndDate(Timestamp endDate) {
//		this.endDate = endDate;
//	}

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

	public Set<News> getNews() {
		return news;
	}

	public void setNews(Set<News> news) {
		this.news = news;
	}

	public Set<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<Keyword> keywords) {
		this.keywords = keywords;
	}

	public void addKeywords(Keyword keyword) {
		this.keywords.add(keyword);
	}
	
	public Timestamp getLastModifDate() {
		return lastModifDate;
	}

	public void setLastModifDate(Timestamp lastModifDate) {
		this.lastModifDate = lastModifDate;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", title=" + title + ", description=" + description + ", image=" + image
				+ ", deleted=" + deleted + ", visibility=" + visibility + ", createdDate=" + createdDate
				+ ", lastModifDate=" + lastModifDate + ", createdBy=" + createdBy +  "]";
	}

	
	

	

	
}
