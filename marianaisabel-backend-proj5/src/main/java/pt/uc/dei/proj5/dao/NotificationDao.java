package pt.uc.dei.proj5.dao;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import pt.uc.dei.proj5.dto.NotificationDTO;
import pt.uc.dei.proj5.entity.News;
import pt.uc.dei.proj5.entity.ProjectSharing;
import pt.uc.dei.proj5.entity.User;
import pt.uc.dei.proj5.entity.Notification;
import pt.uc.dei.proj5.entity.Notification.NotificationType;
import pt.uc.dei.proj5.entity.Project;

@Stateless
public class NotificationDao extends AbstractDao<Notification> {
	private static final long serialVersionUID = 1L;

	public NotificationDao() {
		super(Notification.class);
	}
	
	
	/////////////////////////////////////////////////////////
	//METODOS ESTATICOS DE CONVERSAO ENTRE ENTIDADE E DTOs
	////////////////////////////////////////////////////////
	

	public static NotificationDTO convertEntityToDTO(Notification notification) {
		NotificationDTO notifDTO = new NotificationDTO();
		notifDTO.setAlreadyRead(notification.isAlreadyRead());
		notifDTO.setNotificationType(notification.getNotificationType());
		notifDTO.setTitle(notification.getTitle());
		notifDTO.setId(notification.getId());
		notifDTO.setCreatedDate(notification.getCreatedDate().toString());
		return notifDTO;
	}


	/////////////////////////////////////////////////////////
	//METODOS devolvem Listas
	////////////////////////////////////////////////////////

	
	
	/////////////////////////////////////////////////////////
	//METODOS Queries sobre diversas condiçoes
	////////////////////////////////////////////////////////

	//////////////////////////////
	/**
	 * Notificaçao  INFORMAR UM USER QUE FOI ASSOCIADO A UM PROJECT
	 * @param user
	 * @param projectSharing
	 */
	/////////////////////////////
	public void inviteAssocProjectNotif(User user, ProjectSharing projectSharing) {
		Notification notifEntity = new Notification();
		notifEntity.setProjectSharing(projectSharing);
		notifEntity.setUser(user);
		notifEntity.setAlreadyRead(false);
		notifEntity.setNotificationType(NotificationType.PROJECT_ASSOC);
		notifEntity.setTitle(" Foi associado ao projecto '" + projectSharing.getProject().getTitle() + "'");
		persist(notifEntity);
	}
		
	//////////////////////////////////////
	/**
	 * Notificaçao INFORMAR UM USER QUE FOI ASSOCIADO A UMA NEWS SHARING
	 * @param user
	 * @param newsSharing
	 */
	/////////////////////////////////////
//	public void inviteAssocNewstNotif(User user, NewsSharing newsSharing) {
//		Notification notifEntity = new Notification();
//		notifEntity.setNewsSharing(newsSharing);
//		notifEntity.setUser(user);
//		notifEntity.setAlreadyRead(false);
//		notifEntity.setNotificationType(NotificationType.NEWS_ASSOC);
//		notifEntity.setTitle("Foi associado à notícia '"+ newsSharing.getNews().getTitle() + "'");
//		persist(notifEntity);
//	}
	
	//////////////////////////////////////
	/**
	 * Notificaçao INFORMAR UM USER QUE FOI ASSOCIADO A UMA NEWS
	 * @param user
	 * @param newsSharing
	 */
	/////////////////////////////////////
	
	public void assocUserToNewstNotif(User user, News news) {
		Notification notifEntity = new Notification();
		notifEntity.setNews(news);
		notifEntity.setUser(user);
		notifEntity.setAlreadyRead(false);
		notifEntity.setNotificationType(NotificationType.NEWS_ASSOC);
		notifEntity.setTitle("Foi associado à notícia '"+ news.getTitle() + "'");
		persist(notifEntity);
	}
	
	
	//////////////////////////////////////
	/**
	 * Notificaçao INFORMAR UM USER QUE FOI UM PROJECTO AO QUAL ESTÁ ASSOC FPO ASSOC A UMA NEWS
	 * @param user
	 * @param newsSharing
	 */
	/////////////////////////////////////
	
	public void assocProjToNewstNotif(User user, News news, Project project) {
		Notification notifEntity = new Notification();
		notifEntity.setNews(news);
		notifEntity.setUser(user);
		notifEntity.setAlreadyRead(false);
		notifEntity.setNotificationType(NotificationType.NEWS_ASSOC);
		notifEntity.setTitle("O projecto '" + project.getTitle() +"'ao qual está ligado, foi associado à notícia '"+ news.getTitle() + "'");
		persist(notifEntity);
	}
	
	//////////////////////////////////
	/**
	 * lista de notificações
	 * @param user
	 * @return
	 */
	//////////////////////////////////
	public List<Notification> getNotificationsFromUser(User user) {
		final CriteriaQuery<Notification> criteriaQuery = em.getCriteriaBuilder().createQuery(Notification.class);
		Root<Notification> n = criteriaQuery.from(Notification.class);
		criteriaQuery.select(n).where(em.getCriteriaBuilder().equal(n.get("user"), user));

		try {
			return em.createQuery(criteriaQuery).getResultList();
		} catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	//////////////////////////////////
	/**
	 * lista de notificações desde da ultima vez que fez logout
	 * @param user
	 * @return
	 */
	//////////////////////////////////
	public List<Notification> getNotificationsSinceLastLogoutDate(User user) {
		final CriteriaQuery<Notification> criteriaQuery = em.getCriteriaBuilder().createQuery(Notification.class);
		Root<Notification> n = criteriaQuery.from(Notification.class);
		criteriaQuery.select(n).where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(n.get("user"), user),
				em.getCriteriaBuilder().greaterThan(n.get("createdDate"),user.getLastLogoutDate() )));
		try {
			return em.createQuery(criteriaQuery).getResultList();
		} catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	////////////////////////////////////////////////////////////////////////
	/** 
	 * retorna a quantidade de notificações NÃO lidas de um user
	 * @param user
	 * @return
	 */
	////////////////////////////////////////////////////////////////////////
	public Long getNumberUnreadNotifFromUser(User user) {
		final CriteriaQuery<Long> criteriaQuery = em.getCriteriaBuilder().createQuery(Long.class);
		Root<Notification> c = criteriaQuery.from(Notification.class);
		criteriaQuery.select(em.getCriteriaBuilder().count(c));
		criteriaQuery.where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(c.get("user"), user),
				em.getCriteriaBuilder().equal(c.get("alreadyRead"), false)));
		try {
//			return em.createQuery(criteriaQuery).getResultList().size();
			return em.createQuery(criteriaQuery).getSingleResult();

		
		
		} catch (EJBException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	
	
	
	/**
	 * altera uma notificação para lida
	 * @param id
	 */
	public void markAsReadNotif(int notifId) {
		try {
			System.out.println("entrei em markNotifAsRead e o id é :" + notifId);
			final CriteriaUpdate<Notification> criteriaUpdate = em.getCriteriaBuilder().createCriteriaUpdate(Notification.class);
			Root<Notification> c = criteriaUpdate.from(Notification.class);
			criteriaUpdate.set("alreadyRead", true);
			criteriaUpdate.where(em.getCriteriaBuilder().equal(c.get("id"), notifId));
			em.createQuery(criteriaUpdate).executeUpdate();

		} catch (EJBException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * altera uma notificação para lida
	 * @param id
	 */
	
	public void markAsReadAllNotifFromUser(User user) {
		try {
			System.out.println("entrei em markAsReadAllNotifFromUser e o id é :" + user.getId());
			final CriteriaUpdate<Notification> criteriaUpdate = em.getCriteriaBuilder().createCriteriaUpdate(Notification.class);
			Root<Notification> c = criteriaUpdate.from(Notification.class);
			criteriaUpdate.set("alreadyRead", true);
			criteriaUpdate.where(em.getCriteriaBuilder().equal(c.get("user"), user));
			em.createQuery(criteriaUpdate).executeUpdate();

		} catch (EJBException e) {
			e.printStackTrace();
		}
	}
}
	

