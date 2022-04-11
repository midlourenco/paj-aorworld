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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import javax.persistence.Table;



/**
* The persistent class for the Products database table.
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
	private Timestamp createDate;
	
	

	//@Column(name = "Activities", nullable = true)
	//bi-directional many-to-one association to A
//	@OneToMany(mappedBy="project",cascade = CascadeType.REMOVE)
//	private List<News> news;
//	
	@ManyToOne (optional=false)  //presença obrigatória na relação
	@JoinColumn(name="User_Project")
	private User user;

	
	@OneToMany(mappedBy="project",cascade = CascadeType.REMOVE)//mappedBy="category" - é o category em CategorySharing
	private List<ProjectSharing> projectSharing;
	

//	@ManyToMany(mappedBy="categories")//cada tipo tem uma muitos produtos associados. por isso tem uma lista de produtos
//	private List<User> users;

	
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

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}


	

	@Override
	public String toString() {
		return "Category [id=" + id + ", categoryName=" + categoryName + ", deleted=" + deleted + ", createDate="
				+ createDate;
	}

	
	
}
