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
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the Activity database table.
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

	@Column(name = "start_date", nullable = false)
	private Timestamp startDate;

	@Column(name = "end_date", nullable = true)
	private Timestamp endDate;

	@Column(name = "reminder_date", nullable = true)
	private Timestamp reminderDate;

	@Column(name = "deleted", nullable = false)
	private boolean deleted;

	@Column(name = "createDate", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createDate;


//	@Column(name = "Category", nullable = false)
	// bi-directional many-to-one association to Product_Type
//	@ManyToOne(optional = false)
//	@JoinColumn(name = "Category_Activity")
//	private Category category;

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

	public Timestamp getReminderDate() {
		return reminderDate;
	}

	public void setReminderDate(Timestamp reminderDate) {
		this.reminderDate = reminderDate;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}



	@Override
	public String toString() {
		return "News [nesws=" + id + ", title=" + title + ", description=" + description + ", checked="
				+ checked + "]";
	}

}
