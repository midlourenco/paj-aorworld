package pt.uc.dei.proj5.dto;

import java.util.ArrayList;

public class NewsSharingDTO {

	private boolean accepted;
	private String newsTitle;
	private int sharedNewsId;
	private UserDTOResp assocUserToThisNews; // com quem é que foi partilhada a noticia
	/**
	 * 
	 */
	public NewsSharingDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public boolean isAccepted() {
		return accepted;
	}
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	public String getNewsTitle() {
		return newsTitle;
	}
	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}
	public int getSharedNewsId() {
		return sharedNewsId;
	}
	public void setSharedNewsId(int sharedNewsId) {
		this.sharedNewsId = sharedNewsId;
	}
	public UserDTOResp getAssocUserToThisNews() {
		return assocUserToThisNews;
	}
	public void setAssocUserToThisNews(UserDTOResp assocUserToThisNews) {
		this.assocUserToThisNews = assocUserToThisNews;
	}
	@Override
	public String toString() {
		return "NewsSharingDTO [accepted=" + accepted + ", newsTitle=" + newsTitle + ", sharedNewsId=" + sharedNewsId
				+ ", assocUserToThisNews=" + assocUserToThisNews + "]";
	}

	
	
}
