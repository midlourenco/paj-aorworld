package pt.uc.dei.proj5.dto;

import java.util.ArrayList;

public class ProjectSharingDTOResp {

	private int id;
	private String title;
	private String description;
	private String image;
	private boolean deleted;
	private boolean visibility;
	private String createdDate;
	private String lastModifDate;
	private UserDTOResp createdBy;
	private UserDTOResp lastModifBy;
	
	private boolean shared;
	private String userRoleInProject;
	
	private ArrayList<UserDTOResp> users= new ArrayList<>(); //se esta categoria foi partilhada com alguem este array é >0 e tem o username com quem se partilhou a mesma
	private ArrayList<NewsDTOResp> news = new ArrayList<>(); //se esta categoria foi partilhada com alguem este array é >0 e tem o username com quem se partilhou a mesma
	private ArrayList<String> keywords= new ArrayList<>(); //se esta categoria foi partilhada com alguem este array é >0 e tem o username com quem se partilhou a mesma
	
	
	
	/**
	 * 
	 */
	public ProjectSharingDTOResp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public boolean isVisibility() {
		return visibility;
	}
	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getLastModifDate() {
		return lastModifDate;
	}
	public void setLastModifDate(String lastModifDate) {
		this.lastModifDate = lastModifDate;
	}
	public UserDTOResp getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(UserDTOResp createdBy) {
		this.createdBy = createdBy;
	}
	public UserDTOResp getLastModifBy() {
		return lastModifBy;
	}
	public void setLastModifBy(UserDTOResp lastModifBy) {
		this.lastModifBy = lastModifBy;
	}

	
	public ArrayList<UserDTOResp> getAssociatedUsers() {
		return users;
	}

	public void setAssociatedUsers(ArrayList<UserDTOResp> associatedUsers) {
		this.users = associatedUsers;
	}

	public ArrayList<NewsDTOResp> getAssociatedNews() {
		return news;
	}

	public void setAssociatedNews(ArrayList<NewsDTOResp> associatedNews) {
		this.news = associatedNews;
	}

	public ArrayList<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(ArrayList<String> keywords) {
		this.keywords = keywords;
	}

	@Override
	public String toString() {
		return "ProjectDTOResp [id=" + id + ", title=" + title + ", description=" + description + ", image=" + image
				+ ", deleted=" + deleted + ", visibility=" + visibility + ", createdDate=" + createdDate
				+ ", lastModifDate=" + lastModifDate + ", createdBy=" + createdBy + ", lastModifBy=" + lastModifBy
				+ "]";
	}
	

	
	
}
