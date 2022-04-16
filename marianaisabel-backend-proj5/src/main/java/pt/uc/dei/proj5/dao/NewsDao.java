package pt.uc.dei.proj5.dao;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import pt.uc.dei.proj5.dto.NewsDTO;
import pt.uc.dei.proj5.dto.NewsDTOResp;
import pt.uc.dei.proj5.entity.News;
import pt.uc.dei.proj5.entity.User;

@Stateless
public class NewsDao extends AbstractDao<News> {
	private static final long serialVersionUID = 1L;

	public NewsDao() {
		super(News.class);
	}

	/////////////////////////////////////////////////////////
	// METODOS ESTATICOS DE CONVERSAO ENTRE ENTIDADE E DTOs
	////////////////////////////////////////////////////////

	public static News convertDTOToEntity(NewsDTO newsDTO, News existentNews) {
		System.out.println("Entrei em convertDTOToEntity News");
		News newsEntity;
		if (existentNews == null) {
			newsEntity = new News();
		} else {
			newsEntity = existentNews;
		}

		newsEntity.setTitle(newsDTO.getTitle());
		newsEntity.setDescription(newsDTO.getDescription());
		newsEntity.setImage(newsDTO.getImage());
		newsEntity.setVisibility(newsDTO.isVisibility());

		// TODO complete here
		// news
		// users associated

		return newsEntity;

	}

	public static NewsDTOResp convertEntityToDTOResp(News newsEntity) {
		System.out.println("Entrei em convertEntityToDTOResp News");
		NewsDTOResp newsDTOResp = new NewsDTOResp();
		newsDTOResp.setId(newsEntity.getId());
		newsDTOResp.setTitle(newsEntity.getTitle());
		newsDTOResp.setDescription(newsEntity.getDescription());
		newsDTOResp.setImage(newsEntity.getImage());
		newsDTOResp.setDeleted(newsEntity.isDeleted());
		newsDTOResp.setVisibility(newsEntity.isVisibility());
		newsDTOResp.setId(newsEntity.getId());
		newsDTOResp.setCreatedBy(UserDao.convertEntitytoDTOResp(newsEntity.getCreatedBy()));
		newsDTOResp.setKeywords(KeywordDao.convertEntityListToArrayString(newsEntity.getKeywords()));

		if (newsEntity.getCreatedDate() != null) {
			newsDTOResp.setCreatedDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(newsEntity.getCreatedDate()));

		} else {
			newsDTOResp.setCreatedDate("");
		}

		if (newsEntity.getLastModifDate() != null) {
			newsDTOResp.setLastModifDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(newsEntity.getLastModifDate()));
			newsDTOResp.setLastModifBy(UserDao.convertEntitytoDTOResp(newsEntity.getLastModifBy()));

		} else {
			newsDTOResp.setLastModifDate("");
		}

		// TODO complete here
		// newsDTOResp.setLastModifBy(UserDao.convertEntitytoDTOResp(newsEntity.getLastModifBy()));
		// private ArrayList<UserDTOResp> associatedUsersOfThisNews= new ArrayList<>();
		// //se esta categoria foi partilhada com alguem este array é >0 e tem o
		// username com quem se partilhou a mesma
		// private ArrayList<newsDTOResp> associatedNewsOfThisNews= new ArrayList<>();
		// //se esta categoria foi partilhada com alguem este array é >0 e tem o
		// username com quem se partilhou a mesma

		return newsDTOResp;
	}

	/////////////////////////////////////////////////////////
	// METODOS devolvem Listas de News
	////////////////////////////////////////////////////////

	/**
	 * devolve as News de um user que nao estejam marcados para apagar
	 * (independentemente de terem sido ou nao partilhados)
	 * 
	 * @param user
	 * @return
	 */
	public List<News> getAllNonDeletedNewssFromUser(User createdBy) {

		final CriteriaQuery<News> criteriaQuery = em.getCriteriaBuilder().createQuery(News.class);

		Root<News> c = criteriaQuery.from(News.class);

		criteriaQuery.select(c)
				.where(em.getCriteriaBuilder().and(em.getCriteriaBuilder().equal(c.get("createdBy"), createdBy),
						em.getCriteriaBuilder().equal(c.get("deleted"), false)));
		try {
			return em.createQuery(criteriaQuery).getResultList();
		} catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * devolve as News públicos de um user que nao estejam marcados para apagar
	 * (independentemente de terem sido ou nao partilhados)
	 * 
	 * @param user
	 * @return
	 */
	public List<News> getOnlyPublicNonDeletedNewssFromUser(User createdBy) {
		final CriteriaQuery<News> criteriaQuery = em.getCriteriaBuilder().createQuery(News.class);

		Root<News> c = criteriaQuery.from(News.class);

		criteriaQuery.select(c)
				.where(em.getCriteriaBuilder().and(em.getCriteriaBuilder().equal(c.get("createdBy"), createdBy),
						em.getCriteriaBuilder().equal(c.get("visibility"), true),
						em.getCriteriaBuilder().equal(c.get("deleted"), false)));
		try {
			return em.createQuery(criteriaQuery).getResultList();
		} catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * devolve as News de um user que estejam marcados para apagar
	 * (independentemente de terem sido ou nao partilhados)
	 * 
	 * @param user
	 * @return
	 */
	public List<News> getAllNewssMarkedAsDeletedFromUser(User createdBy) {
		final CriteriaQuery<News> criteriaQuery = em.getCriteriaBuilder().createQuery(News.class);
		Root<News> c = criteriaQuery.from(News.class);
		criteriaQuery.select(c)
				.where(em.getCriteriaBuilder().and(em.getCriteriaBuilder().equal(c.get("createdBy"), createdBy),
						em.getCriteriaBuilder().equal(c.get("deleted"), true)));
		try {
			return em.createQuery(criteriaQuery).getResultList();
		} catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * devolve as News publicos de um user que estejam marcados para apagar
	 * (independentemente de terem sido ou nao partilhados)
	 * 
	 * @param user
	 * @return
	 */
	public List<News> getOnlyPublicNewssMarkedAsDeletedFromUser(User createdBy) {
		final CriteriaQuery<News> criteriaQuery = em.getCriteriaBuilder().createQuery(News.class);
		Root<News> c = criteriaQuery.from(News.class);
		criteriaQuery.select(c)
				.where(em.getCriteriaBuilder().and(em.getCriteriaBuilder().equal(c.get("createdBy"), createdBy),
						em.getCriteriaBuilder().equal(c.get("visibility"), true),
						em.getCriteriaBuilder().equal(c.get("deleted"), true)));
		try {
			return em.createQuery(criteriaQuery).getResultList();
		} catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * devolve as News que estejam marcados para apagar de visibilidade publica
	 * (independentemente de terem sido ou nao partilhados)
	 * 
	 * @param user
	 * @return
	 */
	public List<News> getOnlyPublicNewssMarkedAsDeleted() {
		final CriteriaQuery<News> criteriaQuery = em.getCriteriaBuilder().createQuery(News.class);
		Root<News> c = criteriaQuery.from(News.class);
		criteriaQuery.select(c)
				.where(em.getCriteriaBuilder().and(em.getCriteriaBuilder().equal(c.get("visibility"), true),
						em.getCriteriaBuilder().equal(c.get("deleted"), true)));
		try {
			return em.createQuery(criteriaQuery).getResultList();
		} catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * devolve as News que nao estejam marcados para apagar de visibilidade
	 * publica (independentemente de terem sido ou nao partilhados)
	 * 
	 * @param user
	 * @return
	 */
	public List<News> getOnlyPublicNewssNonDeleted() {
		final CriteriaQuery<News> criteriaQuery = em.getCriteriaBuilder().createQuery(News.class);
		Root<News> c = criteriaQuery.from(News.class);
		criteriaQuery.select(c)
				.where(em.getCriteriaBuilder().and(em.getCriteriaBuilder().equal(c.get("visibility"), true),
						em.getCriteriaBuilder().equal(c.get("deleted"), false)));
		try {
			return em.createQuery(criteriaQuery).getResultList();
		} catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * devolve as News que nao estejam marcados para apagar de visibilidade
	 * publica ou privada (independentemente de terem sido ou nao partilhados)
	 * 
	 * @param user
	 * @return
	 */
	public List<News> getAllNewssNonDeleted() {
		final CriteriaQuery<News> criteriaQuery = em.getCriteriaBuilder().createQuery(News.class);
		Root<News> c = criteriaQuery.from(News.class);
		criteriaQuery.select(c).where(em.getCriteriaBuilder().equal(c.get("deleted"), false));
		try {
			return em.createQuery(criteriaQuery).getResultList();
		} catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * devolve as News que nao estejam marcados para apagar de visibilidade
	 * publica ou privada (independentemente de terem sido ou nao partilhados)
	 * 
	 * @param user
	 * @return
	 */
	public List<News> getAllNewssMarkedAsDeleted() {
		final CriteriaQuery<News> criteriaQuery = em.getCriteriaBuilder().createQuery(News.class);
		Root<News> c = criteriaQuery.from(News.class);
		criteriaQuery.select(c).where(em.getCriteriaBuilder().equal(c.get("deleted"), true));
		try {
			return em.createQuery(criteriaQuery).getResultList();
		} catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}

	/////////////////////////////////////////////////////////
	// METODOS verificam condições
	////////////////////////////////////////////////////////

}
