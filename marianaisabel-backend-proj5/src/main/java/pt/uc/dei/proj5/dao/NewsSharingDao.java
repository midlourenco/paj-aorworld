package pt.uc.dei.proj5.dao;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import pt.uc.dei.proj5.entity.Keyword;
import pt.uc.dei.proj5.entity.NewsSharing;
import pt.uc.dei.proj5.entity.News;
import pt.uc.dei.proj5.entity.NewsSharing;
import pt.uc.dei.proj5.entity.User;
@Stateless
public class NewsSharingDao extends AbstractDao<NewsSharing> {
	private static final long serialVersionUID = 1L;

	public NewsSharingDao() {
		super(NewsSharing.class);
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

	public boolean isUserAlreadyAssociatedToNews(News news, User user) {
		
		if(news.getCreatedBy().getId()==user.getId()) {
			return true;
		}
		
		final CriteriaQuery<NewsSharing> criteriaQuery = em.getCriteriaBuilder().createQuery(NewsSharing.class);

		Root<NewsSharing> c = criteriaQuery.from(NewsSharing.class);

		criteriaQuery.select(c).where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(c.get("user"), user), //user convidado
				em.getCriteriaBuilder().equal(c.get("news"), news), //categoria que foi partilhada
				em.getCriteriaBuilder().equal(c.get("accepted"), true)));
		try {
		
			List<NewsSharing> result = em.createQuery(criteriaQuery).getResultList(); // em principio a existir seria uma lista de 1 elemento
		
			if (result.size() > 0) {
				System.out.println("a news está partilhada com este user e está aceite");
				return true;
			} else {
				
				System.out.println("a news não está partilhada com este user ou não está aceite");
				
				return false;
			}
		
		} catch (EJBException e) {
			e.printStackTrace();
			return false;
		}

	
	
		
	}

}
