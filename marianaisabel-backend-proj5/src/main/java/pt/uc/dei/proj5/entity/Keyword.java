package pt.uc.dei.proj5.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity// classe que vai ter uma ligaçao a um data source
@Table(name="Keyword") //nome da tabela java é no singular, tabela é no plural
public class Keyword implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id 
	@Column(name = "keyword", nullable = false)
	private int keyword;
	
	@ManyToMany(mappedBy="keywords",cascade = CascadeType.REMOVE)
	private List<Project> projects;
	
	@ManyToMany(mappedBy="keywords",cascade = CascadeType.REMOVE)
	private List<News> news;

	
	
	/**
	 * 
	 */
	public Keyword() {
	
	}

	public int getKeyword() {
		return keyword;
	}

	public void setKeyword(int keyword) {
		this.keyword = keyword;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<News> getNews() {
		return news;
	}

	public void setNews(List<News> news) {
		this.news = news;
	}

	@Override
	public String toString() {
		return "Keyword [keyword=" + keyword + ", projects=" + projects + ", news=" + news + "]";
	}
	
	
}
