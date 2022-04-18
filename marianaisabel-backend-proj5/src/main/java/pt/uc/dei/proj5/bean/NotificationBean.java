package pt.uc.dei.proj5.bean;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import pt.uc.dei.proj5.dao.NotificationDao;
import pt.uc.dei.proj5.entity.Notification;
import pt.uc.dei.proj5.entity.ProjectSharing;
import pt.uc.dei.proj5.entity.User;

@RequestScoped
public class NotificationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private NotificationDao notificationDao;
	
//	////////////////////////////////////////////////////////////////////////
//	//N1. Notificação sobre o convite de associar a um projecto;
//	////////////////////////////////////////////////////////////////////////
//	public void addNotificationN1(User user, ProjectSharing projectSharing) {
//		System.out.println("Entrei em add notificationN1  na classe NotificationBean");
//		Notification notifEntity = new Notification();
//		try {
//			notificationDao.createNotifN1(user, projectSharing);
//			System.out.println("gravei a notificação");
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("não consegui gravar notificaçao n1");
//		}
//	
//	}
	
	
	
}
