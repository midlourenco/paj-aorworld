package pt.uc.dei.proj5.dao;

import javax.ejb.Stateless;

import pt.uc.dei.proj5.entity.Keyword;
import pt.uc.dei.proj5.entity.Notification;
@Stateless
public class NotificationDao extends AbstractDao<Notification> {
	private static final long serialVersionUID = 1L;

	public NotificationDao() {
		super(Notification.class);
	}
	
	
	/////////////////////////////////////////////////////////
	//METODOS ESTATICOS DE CONVERSAO ENTRE ENTIDADE E DTOs
	////////////////////////////////////////////////////////
	

	


	/////////////////////////////////////////////////////////
	//METODOS devolvem Listas
	////////////////////////////////////////////////////////

	
	
	/////////////////////////////////////////////////////////
	//METODOS Queries sobre diversas condiçoes
	////////////////////////////////////////////////////////



}