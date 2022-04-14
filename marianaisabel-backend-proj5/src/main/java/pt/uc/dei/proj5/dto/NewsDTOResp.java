package pt.uc.dei.proj5.dto;

import java.util.ArrayList;

public class NewsDTOResp {

	private int id;
	private String title;
	private String description;
	private String image;
	private boolean deleted;
	private boolean visibility;
	private String createdDate;
	private String lastModifDate;
	private String createdBy;
	private String lastModifBy;
	
	
	private ArrayList<UserDTOResp> associatedUsersOfThisNews= new ArrayList<>(); //se esta categoria foi partilhada com alguem este array é >0 e tem o username com quem se partilhou a mesma
	private ArrayList<ProjectDTOResp> associatedProjectsOfThisNews= new ArrayList<>(); //se esta categoria foi partilhada com alguem este array é >0 e tem o username com quem se partilhou a mesma
	private ArrayList<String> keywordsOfThisNews= new ArrayList<>(); //se esta categoria foi partilhada com alguem este array é >0 e tem o username com quem se partilhou a mesma
	
	
	
	
	/**
	 * 
	 */
	public NewsDTOResp() {
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastModifBy() {
		return lastModifBy;
	}
	public void setLastModifBy(String lastModifBy) {
		this.lastModifBy = lastModifBy;
	}
	
		public ArrayList<UserDTOResp> getAssociatedUsersOfThisNews() {
		return associatedUsersOfThisNews;
	}


	public void setAssociatedUsersOfThisNews(ArrayList<UserDTOResp> associatedUsersOfThisNews) {
		this.associatedUsersOfThisNews = associatedUsersOfThisNews;
	}


	public ArrayList<ProjectDTOResp> getAssociatedProjectsOfThisNews() {
		return associatedProjectsOfThisNews;
	}


	public void setAssociatedProjectsOfThisNews(ArrayList<ProjectDTOResp> associatedProjectsOfThisNews) {
		this.associatedProjectsOfThisNews = associatedProjectsOfThisNews;
	}


	public ArrayList<String> getKeywordsOfThisNews() {
		return keywordsOfThisNews;
	}


	public void setKeywordsOfThisNews(ArrayList<String> keywordsOfThisNews) {
		this.keywordsOfThisNews = keywordsOfThisNews;
	}


	@Override
	public String toString() {
		return "NewsDTOResp [id=" + id + ", title=" + title + ", description=" + description + ", image=" + image
				+ ", deleted=" + deleted + ", visibility=" + visibility + ", createdDate=" + createdDate
				+ ", lastModifDate=" + lastModifDate + ", createdBy=" + createdBy + ", lastModifBy=" + lastModifBy
				+ "]";
	}
	
	
	
	
}
