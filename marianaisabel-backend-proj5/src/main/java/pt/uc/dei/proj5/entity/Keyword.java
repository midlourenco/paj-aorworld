package pt.uc.dei.proj5.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity// classe que vai ter uma ligaçao a um data source
@Table(name="Keyword") //nome da tabela java é no singular, tabela é no plural
public class Keyword implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id 
	@Column(name = "keyword", nullable = false)
	private String keyword;
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(mappedBy="keywords")
	private Set<Project> projects=new HashSet<>();
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(mappedBy="keywords")
	private Set<News> news=new HashSet<>();

	
	
	/**
	 * 
	 */
	public Keyword() {
	
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public Set<News> getNews() {
		return news;
	}

	public void setNews(Set<News> news) {
		this.news = news;
	}
	
	public void addProject(Project project) {
//		if (this.projects==null) {
//			this.projects=new HashSet<>();
//		}
		this.projects.add(project);
	}
	
	public void addNews(News news) {
//		if (this.news==null) {
//			this.news=new HashSet<>();
//		}
		this.news.add(news);
	}
	
	
	@Override
	public String toString() {
		return "Keyword [keyword=" + keyword + "]";
	}
	
	
}
