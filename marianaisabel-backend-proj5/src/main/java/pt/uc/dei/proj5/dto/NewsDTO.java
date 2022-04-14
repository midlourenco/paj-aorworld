package pt.uc.dei.proj5.dto;


public class NewsDTO {
	private String title;
	private String description;
	private String image;
	
	
	
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
	@Override
	public String toString() {
		return "NewsDTO [title=" + title + ", description=" + description + ", image=" + image + "]";
	}

	
	
	

	

}
