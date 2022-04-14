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
 * The persistent class for the ProjectSharing database table.
 * 
 */
@Entity// classe que vai ter uma ligaçao a um data source
@Table(name="NewsSharing") //nome da tabela java é no singular, tabela é no plural
public class NewsSharing implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;
	
	@Column(name = "accepted", nullable = false)
	private boolean accepted;

	@ManyToOne(optional = false)
	@JoinColumn(name = "User_NewsSharing")
	private User user;

	@ManyToOne
	@JoinColumn(name = "News_NewsSharing")
	private News news;

	@OneToMany(mappedBy = "newsSharing", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Notification> newsSharing;

	
	@Column(name="createdDate", nullable=false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdDate;
	
	
	public NewsSharing() {
		
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public List<Notification> getNewsSharing() {
		return newsSharing;
	}

	public void setNewsSharing(List<Notification> newsSharing) {
		this.newsSharing = newsSharing;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "NewsSharing [id=" + id + ", accepted=" + accepted + ", user=" + user + ", news=" + news
				+ ", newsSharing=" + newsSharing + ", createdDate=" + createdDate + "]";
	}
	



}
