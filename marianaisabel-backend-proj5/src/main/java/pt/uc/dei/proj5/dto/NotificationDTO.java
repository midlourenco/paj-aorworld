package pt.uc.dei.proj5.dto;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.NotBlank;

import pt.uc.dei.proj5.entity.Notification.NotificationType;



public class NotificationDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	@NotBlank
	private String title;
	@NotBlank
	private String description;
	@NotBlank
	private boolean alreadyRead;
	@NotBlank
	private int id;
	@NotBlank
	private NotificationType notificationType;
	@NotBlank
	private String createdDate;


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isAlreadyRead() {
		return alreadyRead;
	}
	public void setAlreadyRead(boolean alreadyRead) {
		this.alreadyRead = alreadyRead;
	}
	public NotificationType getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	
	
	
	
}