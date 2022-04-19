package pt.uc.dei.proj5.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import pt.uc.dei.proj5.dao.NotificationDao;
import pt.uc.dei.proj5.dto.NotificationDTO;
import pt.uc.dei.proj5.entity.Notification;
import pt.uc.dei.proj5.entity.User;


@RequestScoped
public class NotificationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private NotificationDao notificationDao;
	

	public ArrayList<NotificationDTO> getNotificationsFromUser(User user) {
		ArrayList<NotificationDTO> notificationDTOResp =new ArrayList<> ();
		
		List<Notification> notifications =  notificationDao.getNotificationsFromUser(user);
		
		for (Notification notification : notifications) {
			notificationDTOResp.add(NotificationDao.convertEntityToDTO(notification));
		}
		
		return notificationDTOResp;
	}
	
	public ArrayList<NotificationDTO> getNotificationsSinceLastLogoutDate(User user) {
		ArrayList<NotificationDTO> notificationDTOResp =new ArrayList<> ();
		
		List<Notification> notifications =  notificationDao.getNotificationsSinceLastLogoutDate(user);
		
		for (Notification notification : notifications) {
			notificationDTOResp.add(NotificationDao.convertEntityToDTO(notification));
		}
		
		return notificationDTOResp;
	}
	
	public int getNumberUnreadNotifFromUser(User user) {
		ArrayList<NotificationDTO> notificationDTOResp =new ArrayList<> ();
		
		int unreadNotifications =  notificationDao.getNumberUnreadNotifFromUser(user);
		
		return unreadNotifications;
	}
	
	
	public boolean markAsReadNotif(int notifId, User user) {
		Notification notif = notificationDao.find(notifId);
		if (notif.getUser().getId()==user.getId()) {
			notificationDao.markAsReadNotif(notifId);
			return true;
		}
		return false;
	}
	
	public void markAsReadAllNotifFromUser(User user) {
		notificationDao.markAsReadAllNotifFromUser(user);
	}
	

	
}
