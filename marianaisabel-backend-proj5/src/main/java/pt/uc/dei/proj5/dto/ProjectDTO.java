package pt.uc.dei.proj5.dto;

import java.util.ArrayList;

public class ProjectDTO {
	private String title;
	private String description;
	private String image;
	private ArrayList<String> keywords;
	private boolean visibility;
	
	
	
	/**
	 * 
	 */
	public ProjectDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param title
	 * @param description
	 * @param image
	 */
	public ProjectDTO(String title, String description, String image) {
		super();
		this.title = title;
		this.description = description;
		this.image = image;
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
	public ArrayList<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(ArrayList<String> keywords) {
		this.keywords = keywords;
	}
	

	public boolean isVisibility() {
		return visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	@Override
	public String toString() {
		return "ProjectDTO [title=" + title + ", description=" + description + ", image=" + image + "]";
	}

	
	
	

	

}
