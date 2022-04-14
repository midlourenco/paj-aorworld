package pt.uc.dei.proj5.dto;

import java.util.ArrayList;

public class ProjectDTOResp {

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
	
	
	
	private ArrayList<UserDTOResp> associatedUsersOfThisProject= new ArrayList<>(); //se esta categoria foi partilhada com alguem este array é >0 e tem o username com quem se partilhou a mesma
	private ArrayList<ProjectDTOResp> associatedNewsOfThisProject= new ArrayList<>(); //se esta categoria foi partilhada com alguem este array é >0 e tem o username com quem se partilhou a mesma
	private ArrayList<String> keywordsOfThisProject= new ArrayList<>(); //se esta categoria foi partilhada com alguem este array é >0 e tem o username com quem se partilhou a mesma
	
	
	
	/**
	 * 
	 */
	public ProjectDTOResp() {
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
		
	public ArrayList<UserDTOResp> getAssociatedUsersOfThisProject() {
		return associatedUsersOfThisProject;
	}
	public void setAssociatedUsersOfThisProject(ArrayList<UserDTOResp> associatedUsersOfThisProject) {
		this.associatedUsersOfThisProject = associatedUsersOfThisProject;
	}
	public ArrayList<ProjectDTOResp> getAssociatedNewsOfThisProject() {
		return associatedNewsOfThisProject;
	}
	public void setAssociatedNewsOfThisProject(ArrayList<ProjectDTOResp> associatedNewsOfThisProject) {
		this.associatedNewsOfThisProject = associatedNewsOfThisProject;
	}
	public ArrayList<String> getKeywordsOfThisProject() {
		return keywordsOfThisProject;
	}
	public void setKeywordsOfThisProject(ArrayList<String> keywordsOfThisProject) {
		this.keywordsOfThisProject = keywordsOfThisProject;
	}
	@Override
	public String toString() {
		return "ProjectDTOResp [id=" + id + ", title=" + title + ", description=" + description + ", image=" + image
				+ ", deleted=" + deleted + ", visibility=" + visibility + ", createdDate=" + createdDate
				+ ", lastModifDate=" + lastModifDate + ", createdBy=" + createdBy + ", lastModifBy=" + lastModifBy
				+ "]";
	}
	
	
	
	
}
