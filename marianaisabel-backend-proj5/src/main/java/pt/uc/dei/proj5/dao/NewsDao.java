package pt.uc.dei.proj5.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import pt.uc.dei.proj5.dto.NewsDTO;
import pt.uc.dei.proj5.dto.NewsDTOResp;
import pt.uc.dei.proj5.dto.ProjectDTOResp;
import pt.uc.dei.proj5.dto.UserDTOResp;
import pt.uc.dei.proj5.entity.Keyword;
import pt.uc.dei.proj5.entity.News;
import pt.uc.dei.proj5.entity.Project;
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
		System.out.println("adicionei os atributos simples dentro news DTOResp");
		Set<Project> projectListFromNews =newsEntity.getProjects();
		if(projectListFromNews!=null) {
			ArrayList<ProjectDTOResp> projectArray = new ArrayList<>();
			for (Project p : projectListFromNews) {
				projectArray.add(ProjectDao.convertEntityToDTO_FORNEWSARRAY(p));
			}
			newsDTOResp.setProjects(projectArray);
		}
		System.out.println("adicioneei os projectos dentro news DTOResp");
		Set<User> usersListFromNews=newsEntity.getUsers();
		if(usersListFromNews!=null) {
			ArrayList<UserDTOResp> userArray = new ArrayList<>();
			for (User u : usersListFromNews) {
				userArray.add(UserDao.convertEntitytoDTOResp(u));
			}
			newsDTOResp.setUsers(userArray);
		}
		System.out.println("adicionei os utilizadores dentro news DTOResp");
		
		
		
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
		
		// private ArrayList<UserDTOResp> associatedUsersOfThisNews= new ArrayList<>();
		// //se esta news foi partilhada com alguem este array é >0 e tem o
		// username com quem se partilhou a mesma
		// private ArrayList<newsDTOResp> associatedNewsOfThisNews= new ArrayList<>();
		// //se esta news foi partilhada com alguem este array é >0 e tem o
		// username com quem se partilhou a mesma

		return newsDTOResp;
	}

	public static NewsDTOResp convertEntityToDTOResp_FORPROJECTARRAY(News newsEntity) {
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
	 * devolve as News de um user que nao estejam marcados para apagar
	 * (independentemente de terem sido ou nao partilhados)
	 * 
	 * @param user
	 * @return
	 */
	public List<News> getAllNonDeletedAssocNewssFromUser(User assocTo) {

		final CriteriaQuery<News> criteriaQuery = em.getCriteriaBuilder().createQuery(News.class);
		Root<User> user = criteriaQuery.from(User.class);
		Join<User, News> news = user.join("news");
		criteriaQuery.select(news).where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(user.get("id"), assocTo.getId()),
				em.getCriteriaBuilder().equal(news.get("deleted"), false)));

		try{
			List<News> resultado= em.createQuery(criteriaQuery.select(news)).getResultList();
			return resultado;


		}catch (EJBException e) {
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
	public List<News> getOnlyPublicNonDeletedAssocNewssFromUser(User assocTo) {
		final CriteriaQuery<News> criteriaQuery = em.getCriteriaBuilder().createQuery(News.class);
		Root<User> user = criteriaQuery.from(User.class);
		Join<User, News> news = user.join("news");
		criteriaQuery.select(news).where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(user.get("id"), assocTo.getId()),
				em.getCriteriaBuilder().equal(news.get("deleted"), false),
				em.getCriteriaBuilder().equal(news.get("visibility"), true)));

		try{
			List<News> resultado= em.createQuery(criteriaQuery.select(news)).getResultList();
			return resultado;


		}catch (EJBException e) {
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
	public List<News> getAllAssocNewssMarkedAsDeletedFromUser(User assocTo) {
		final CriteriaQuery<News> criteriaQuery = em.getCriteriaBuilder().createQuery(News.class);
		Root<User> user = criteriaQuery.from(User.class);
		Join<User, News> news = user.join("news");
		criteriaQuery.select(news).where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(user.get("id"), assocTo.getId()),
				em.getCriteriaBuilder().equal(news.get("deleted"), true)));

		try{
			List<News> resultado= em.createQuery(criteriaQuery.select(news)).getResultList();
			return resultado;


		}catch (EJBException e) {
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
	public List<News> getOnlyPublicAssocNewssMarkedAsDeletedFromUser(User assocTo) {
		
			final CriteriaQuery<News> criteriaQuery = em.getCriteriaBuilder().createQuery(News.class);
			Root<User> user = criteriaQuery.from(User.class);
			Join<User, News> news = user.join("news");
			criteriaQuery.select(news).where(em.getCriteriaBuilder().and(
					em.getCriteriaBuilder().equal(user.get("id"), assocTo.getId()),
					em.getCriteriaBuilder().equal(news.get("deleted"), true),
					em.getCriteriaBuilder().equal(news.get("visibility"), true)));

			try{
				List<News> resultado= em.createQuery(criteriaQuery.select(news)).getResultList();
				return resultado;


			}catch (EJBException e) {
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
	// METODOS dashboard
	////////////////////////////////////////////////////////

	
	
	public Long countPublicNews() {
		final CriteriaQuery<Long> criteriaQuery = em.getCriteriaBuilder().createQuery(Long.class);
		Root<News> c = criteriaQuery.from(News.class);
		criteriaQuery.select(em.getCriteriaBuilder().count(c));
		criteriaQuery.where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(c.get("visibility"), true),
				em.getCriteriaBuilder().equal(c.get("deleted"), false)));
		try {
//			return em.createQuery(criteriaQuery).getResultList().size();
			return em.createQuery(criteriaQuery).getSingleResult();

		} catch (EJBException e) {
			e.printStackTrace();
			return null ;
		}
	}
	

	
	public Long countPrivateNews() {
		final CriteriaQuery<Long> criteriaQuery = em.getCriteriaBuilder().createQuery(Long.class);
		Root<News> c = criteriaQuery.from(News.class);
		criteriaQuery.select(em.getCriteriaBuilder().count(c));
		criteriaQuery.where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(c.get("visibility"), false),
				em.getCriteriaBuilder().equal(c.get("deleted"), false)));
		try {
//			return em.createQuery(criteriaQuery).getResultList().size();
			return em.createQuery(criteriaQuery).getSingleResult();

		} catch (EJBException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public Long countDeletedPublicNews() {
		final CriteriaQuery<Long> criteriaQuery = em.getCriteriaBuilder().createQuery(Long.class);
		Root<News> c = criteriaQuery.from(News.class);
		criteriaQuery.select(em.getCriteriaBuilder().count(c));
		criteriaQuery.where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(c.get("visibility"), true),
				em.getCriteriaBuilder().equal(c.get("deleted"), true)));
		try {
//			return em.createQuery(criteriaQuery).getResultList().size();
			return em.createQuery(criteriaQuery).getSingleResult();

		} catch (EJBException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public Long countDeletedPrivateNews() {
		final CriteriaQuery<Long> criteriaQuery = em.getCriteriaBuilder().createQuery(Long.class);
		Root<News> c = criteriaQuery.from(News.class);
		criteriaQuery.select(em.getCriteriaBuilder().count(c));
		criteriaQuery.where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(c.get("visibility"), false),
				em.getCriteriaBuilder().equal(c.get("deleted"), true)));
		try {
//			return em.createQuery(criteriaQuery).getResultList().size();
			return em.createQuery(criteriaQuery).getSingleResult();

		} catch (EJBException e) {
			e.printStackTrace();
			return null ;
		}
	}
	

	/**
	 * devolve as News que nao estejam marcados para apagar de visibilidade
	 * publica (independentemente de terem sido ou nao partilhados)
	 * 
	 * @param user
	 * @return
	 */
	public News moreRecentNews() {
		final CriteriaQuery<News> criteriaQuery = em.getCriteriaBuilder().createQuery(News.class);
		Root<News> c = criteriaQuery.from(News.class);
	//	criteriaQuery.select(c).where(em.getCriteriaBuilder().max(c.get("createdDate")));
	//	return em.createQuery(criteriaQuery).getSingleResult();
		
//		predicateList.add(em.getCriteriaBuilder().greatest(c.get("createdDate")));
//		
		criteriaQuery.orderBy(em.getCriteriaBuilder().desc(c.get("createdDate")));
		
		try {
			return em.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
				//	getFirstResult();
		} catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
}
