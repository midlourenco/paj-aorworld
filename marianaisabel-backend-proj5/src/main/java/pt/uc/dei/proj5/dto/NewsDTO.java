package pt.uc.dei.proj5.dto;

import java.util.ArrayList;

public class NewsDTO {
	private String title;
	private String description;
	private String image;
	private ArrayList<String> keywords;
	private ArrayList<Integer> projects;
	private ArrayList<Integer> users;
	private boolean visibility;
	
	
	
	/**
	 * 
	 */
	public NewsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param title
	 * @param description
	 * @param image
	 */
	public NewsDTO(String title, String description, String image) {
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

	public ArrayList<Integer> getProjects() {
		return projects;
	}

	public void setProjects(ArrayList<Integer> projects) {
		this.projects = projects;
	}

	public boolean isVisibility() {
		return visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	
	public ArrayList<Integer> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<Integer> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "NewsDTO [title=" + title + ", description=" + description + ", image=" + image + "]";
	}

	
	
	

	

}
