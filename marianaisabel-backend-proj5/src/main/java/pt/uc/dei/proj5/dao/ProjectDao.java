package pt.uc.dei.proj5.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import pt.uc.dei.proj5.dto.ProjectDTOResp;
import pt.uc.dei.proj5.dto.UserDTOResp;
import pt.uc.dei.proj5.dto.NewsDTOResp;
import pt.uc.dei.proj5.dto.ProjectDTO;
import pt.uc.dei.proj5.entity.Keyword;
import pt.uc.dei.proj5.entity.News;
import pt.uc.dei.proj5.entity.Project;
import pt.uc.dei.proj5.entity.ProjectSharing;
import pt.uc.dei.proj5.entity.User;

@Stateless
public class ProjectDao extends AbstractDao<Project> {
	private static final long serialVersionUID = 1L;

	public ProjectDao() {
		super(Project.class);
	}

	/////////////////////////////////////////////////////////
	//METODOS ESTATICOS DE CONVERSAO ENTRE ENTIDADE E DTOs
	////////////////////////////////////////////////////////
	
	public static Project convertDTOToEntity(ProjectDTO projectDTO, Project existentProject) {
		System.out.println("Entrei em convertDTOToEntity Project");
		Project projectEntity;
		if(existentProject==null) {
			projectEntity = new Project();
		}else {
			projectEntity=existentProject;
		}
		
		projectEntity.setTitle(projectDTO.getTitle());
		projectEntity.setDescription(projectDTO.getDescription());
		projectEntity.setImage(projectDTO.getImage());
		projectEntity.setVisibility(projectDTO.isVisibility());
		
//		if(createdBy!=null) {
//			projectEntity.setCreatedBy(createdBy);
//		}
//		if(lastModifdBy!=null) {
//			projectEntity.setLastModifBy(lastModifdBy);
//			projectEntity.setLastModifDate(new Timestamp(System.currentTimeMillis()));
//		}
		
		//TODO complete here
		//news
		//users associated
		
		
		return projectEntity;

	}

	public static ProjectDTOResp convertEntityToDTOResp(Project projectEntity) {
		if(projectEntity!=null) {
		System.out.println("Entrei em convertEntityToDTOResp Project");
		ProjectDTOResp projectDTOResp = new ProjectDTOResp();
		projectDTOResp.setId(projectEntity.getId());
		projectDTOResp.setTitle(projectEntity.getTitle());
		projectDTOResp.setDescription(projectEntity.getDescription());
		projectDTOResp.setImage(projectEntity.getImage());
		projectDTOResp.setDeleted(projectEntity.isDeleted());
		projectDTOResp.setVisibility(projectEntity.isVisibility());
		projectDTOResp.setId(projectEntity.getId());
		projectDTOResp.setCreatedBy(UserDao.convertEntitytoDTOResp(projectEntity.getCreatedBy()));
		projectDTOResp.setKeywords(KeywordDao.convertEntityListToArrayString(projectEntity.getKeywords()));
		
		Set<News> newsListFromBD =projectEntity.getNews();
		if(newsListFromBD.size()>0) {
			ArrayList<NewsDTOResp> newsArray = new ArrayList<>();
			for (News news : newsListFromBD) {
				newsArray.add(NewsDao.convertEntityToDTOResp_FORPROJECTARRAY(news));
			}
			projectDTOResp.setAssociatedNews(newsArray);
		}
		
//		ArrayList<UserDTOResp> usersDTOResp =new ArrayList<>();
//		List<User> users=getUserAssocToProject(projectEntity);
//		for (User user : users) {
//			usersDTOResp.add(UserDao.convertEntitytoDTOResp(user));
//		}		
//		projectDTOResp.setAssociatedUsers(users);
		
		if (projectEntity.getCreatedDate() != null) {
			projectDTOResp.setCreatedDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(projectEntity.getCreatedDate()));

		} else {
			projectDTOResp.setCreatedDate("");
		}
		
		
		if (projectEntity.getLastModifDate() != null) {
			projectDTOResp.setLastModifDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(projectEntity.getLastModifDate()));
			projectDTOResp.setLastModifBy(UserDao.convertEntitytoDTOResp(projectEntity.getLastModifBy()));		

		} else {
			projectDTOResp.setLastModifDate("");
		}
		
		return projectDTOResp;
		}return null;
	}
	
		public static ProjectDTOResp convertEntityToDTO_FORNEWSARRAY(Project projectEntity) {
			System.out.println("Entrei em convertEntityToDTOResp Project");
			ProjectDTOResp projectDTOResp = new ProjectDTOResp();
			projectDTOResp.setId(projectEntity.getId());
			projectDTOResp.setTitle(projectEntity.getTitle());
			projectDTOResp.setDescription(projectEntity.getDescription());
			projectDTOResp.setImage(projectEntity.getImage());
			projectDTOResp.setDeleted(projectEntity.isDeleted());
			projectDTOResp.setVisibility(projectEntity.isVisibility());
			projectDTOResp.setId(projectEntity.getId());
			projectDTOResp.setCreatedBy(UserDao.convertEntitytoDTOResp(projectEntity.getCreatedBy()));

			if (projectEntity.getCreatedDate() != null ) {
				projectDTOResp.setCreatedDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(projectEntity.getCreatedDate()));

			} else {
				projectDTOResp.setCreatedDate("");
			}
			
			if (projectEntity.getLastModifDate() != null ) {
				projectDTOResp.setLastModifDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(projectEntity.getLastModifDate()));
				projectDTOResp.setLastModifBy(UserDao.convertEntitytoDTOResp(projectEntity.getLastModifBy()));		

			} else {
				projectDTOResp.setLastModifDate("");
			}
		

		//TODO complete here
//	projectDTOResp.setLastModifBy(UserDao.convertEntitytoDTOResp(projectEntity.getLastModifBy()));		
//		private ArrayList<UserDTOResp> associatedUsersOfThisProject= new ArrayList<>(); //se esta categoria foi partilhada com alguem este array ?? >0 e tem o username com quem se partilhou a mesma
//		private ArrayList<ProjectDTOResp> associatedNewsOfThisProject= new ArrayList<>(); //se esta categoria foi partilhada com alguem este array ?? >0 e tem o username com quem se partilhou a mesma

		
		return projectDTOResp;
	}

	
	/////////////////////////////////////////////////////////
	//METODOS devolvem Listas de Projectos
	////////////////////////////////////////////////////////
	

//	
//	/**
//	 * devolve todos os projectos criados por de um User (apagados ou nao, partilhados ou nao, visiveis para todos ou nao)
//	 * @param ProjectName
//	 * @param user
//	 * @return
//	 */
//	public List<Project> getAllProjectCreatedByUser(User createdBy) {
//
//		final CriteriaQuery<Project> criteriaQuery = em.getCriteriaBuilder().createQuery(Project.class);
//
//		Root<Project> c = criteriaQuery.from(Project.class);
//
//		criteriaQuery.select(c).where(em.getCriteriaBuilder().equal(c.get("createdBy"), createdBy));
//		
//		try {
//			return em.createQuery(criteriaQuery).getResultList();
//
//		} catch (EJBException e) {
//			e.printStackTrace();
//			return null;
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("false");
//			return null;
//		}
//	}
//
//	
	/**
	 * devolve as projectos de um user que nao estejam marcados para apagar (independentemente de terem sido ou nao partilhados)
	 * @param user
	 * @return
	 */
	public List<Project> getAllNonDeletedProjectsFromUser(User createdBy) {
		
		final CriteriaQuery<Project> criteriaQuery = em.getCriteriaBuilder().createQuery(Project.class);
		
		Root<Project> c = criteriaQuery.from(Project.class);
		
		criteriaQuery.select(c).where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(c.get("createdBy"), createdBy),
				em.getCriteriaBuilder().equal(c.get("deleted"), false)));
		try {
			return em.createQuery(criteriaQuery).getResultList();
		} catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * devolve as projectos p??blicos de um user que nao estejam marcados para apagar (independentemente de terem sido ou nao partilhados)
	 * @param user
	 * @return
	 */
	public List<Project> getOnlyPublicNonDeletedProjectsFromUser(User createdBy) {
		final CriteriaQuery<Project> criteriaQuery = em.getCriteriaBuilder().createQuery(Project.class);
		
		Root<Project> c = criteriaQuery.from(Project.class);
		
		criteriaQuery.select(c).where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(c.get("createdBy"), createdBy),
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
	 * devolve as projectos de um user que estejam marcados para apagar (independentemente de terem sido ou nao partilhados)
	 * @param user
	 * @return
	 */
	public List<Project> getAllProjectsMarkedAsDeletedFromUser(User createdBy) {
		final CriteriaQuery<Project> criteriaQuery = em.getCriteriaBuilder().createQuery(Project.class);
		Root<Project> c = criteriaQuery.from(Project.class);
		criteriaQuery.select(c).where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(c.get("createdBy"), createdBy),
				em.getCriteriaBuilder().equal(c.get("deleted"), true)));
		try {
			return em.createQuery(criteriaQuery).getResultList();
		} catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * devolve as projectos publicos de um user que estejam marcados para apagar (independentemente de terem sido ou nao partilhados)
	 * @param user
	 * @return
	 */
	public List<Project> getOnlyPublicProjectsMarkedAsDeletedFromUser(User createdBy) {
		final CriteriaQuery<Project> criteriaQuery = em.getCriteriaBuilder().createQuery(Project.class);
		Root<Project> c = criteriaQuery.from(Project.class);
		criteriaQuery.select(c).where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(c.get("createdBy"), createdBy),
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
	 * devolve as projectos  que  estejam marcados para apagar de visibilidade publica (independentemente de terem sido ou nao partilhados)
	 * @param user
	 * @return
	 */
	public List<Project> getOnlyPublicProjectsMarkedAsDeleted() {
		final CriteriaQuery<Project> criteriaQuery = em.getCriteriaBuilder().createQuery(Project.class);
		Root<Project> c = criteriaQuery.from(Project.class);
		criteriaQuery.select(c).where(em.getCriteriaBuilder().and(
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
	 * devolve as projectos  que nao estejam marcados para apagar de visibilidade publica (independentemente de terem sido ou nao partilhados)
	 * @param user
	 * @return
	 */
	public List<Project> getOnlyPublicProjectsNonDeleted() {
		final CriteriaQuery<Project> criteriaQuery = em.getCriteriaBuilder().createQuery(Project.class);
		Root<Project> c = criteriaQuery.from(Project.class);
		criteriaQuery.select(c).where(em.getCriteriaBuilder().and(
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
	 * devolve as projectos  que nao estejam marcados para apagar de visibilidade publica ou privada (independentemente de terem sido ou nao partilhados)
	 * @param user
	 * @return
	 */
	public List<Project> getAllProjectsNonDeleted() {
		final CriteriaQuery<Project> criteriaQuery = em.getCriteriaBuilder().createQuery(Project.class);
		Root<Project> c = criteriaQuery.from(Project.class);
		criteriaQuery.select(c).where(em.getCriteriaBuilder().equal(c.get("deleted"), false));
		try {
			return em.createQuery(criteriaQuery).getResultList();
		} catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	/**
	 * devolve as projectos  que nao estejam marcados para apagar de visibilidade publica ou privada (independentemente de terem sido ou nao partilhados)
	 * @param user
	 * @return
	 */
	public List<Project> getAllProjectsMarkedAsDeleted() {
		final CriteriaQuery<Project> criteriaQuery = em.getCriteriaBuilder().createQuery(Project.class);
		Root<Project> c = criteriaQuery.from(Project.class);
		criteriaQuery.select(c).where(em.getCriteriaBuilder().equal(c.get("deleted"), true));
		try {
			return em.createQuery(criteriaQuery).getResultList();
		} catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	/////////////////////////////////////////////////////////
	//METODOS verificam condi????es 
	////////////////////////////////////////////////////////
	/**
	 * metodo para fazer a valida??ao se um projecto com determinado titulo j?? existe em determinado user
	 * @deprecated
	 * @param projectTitle
	 * @param user
	 * @return
	 */
	public boolean alreadyExistProjectTitleByThisCreatedUser(String projectTitle, User user) {
		final CriteriaQuery<Project> criteriaQuery = em.getCriteriaBuilder().createQuery(Project.class);
		
		Root<Project> c = criteriaQuery.from(Project.class);
		
		criteriaQuery.select(c).where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(c.get("createdBy"), user),
				em.getCriteriaBuilder().equal(c.get("title"), projectTitle)));
		try {
			List<Project> resultado = em.createQuery(criteriaQuery).getResultList();
			 
			if(resultado.size()>0) {
				return true;
			}else {
				return false;
			}
		} catch (EJBException e) {
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	
	public void associateKeywordToProject(Keyword keyword, Project project) {
		project.addKeywords(keyword);
		System.out.println("adicionei keyword ao project em associateKeywordToProject ");
		//System.out.println("lista" + project.getKeywords());
		//merge(project);
	}

	
	public void associateNewsToProject(News news, Project project) {
		if(!project.getNews().contains(news)) {
			project.getNews().add(news);
		System.out.println("adicionei news ao project em associateKeywordToProject ");
		
		}
	}
	
	
	/////////////////////////////////////////////////////////
	// METODOS dashboard
	////////////////////////////////////////////////////////

	
	
	public Long countPublicProject() {
		final CriteriaQuery<Long> criteriaQuery = em.getCriteriaBuilder().createQuery(Long.class);
		Root<Project> c = criteriaQuery.from(Project.class);
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
	

	
	public Long countPrivateProject() {
		final CriteriaQuery<Long> criteriaQuery = em.getCriteriaBuilder().createQuery(Long.class);
		Root<Project> c = criteriaQuery.from(Project.class);
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
	
	public Long countDeletedPublicProject() {
		final CriteriaQuery<Long> criteriaQuery = em.getCriteriaBuilder().createQuery(Long.class);
		Root<Project> c = criteriaQuery.from(Project.class);
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
	
	public Long countDeletedPrivateProject() {
		final CriteriaQuery<Long> criteriaQuery = em.getCriteriaBuilder().createQuery(Long.class);
		Root<Project> c = criteriaQuery.from(Project.class);
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
	public Project moreRecentProject() {
		final CriteriaQuery<Project> criteriaQuery = em.getCriteriaBuilder().createQuery(Project.class);
		Root<Project> c = criteriaQuery.from(Project.class);
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
	
//
//	/**
//	 * lista de projectos em que um utilizador est?? associado (ap??s partilha aceite)
//	 * @param user
//	 * @return
//	 */
//	public List<User> getUserAssocToProject(Project project) {
//		final CriteriaQuery<User> criteriaQuery = em.getCriteriaBuilder().createQuery(User.class);
//		Root<ProjectSharing> c = criteriaQuery.from(ProjectSharing.class);
//		Join<ProjectSharing, User> users = c.join("user");
//		
//		criteriaQuery.select(users).where(em.getCriteriaBuilder().and(
//				em.getCriteriaBuilder().equal(c.get("project"), project), //projecto que foi partilhada
//				em.getCriteriaBuilder().equal(c.get("accepted"), true)));
//		
//		try {
//			List<User> result = em.createQuery(criteriaQuery).getResultList(); // em principio a existir seria uma lista de 1 elemento
//			if (result.size() > 0) {
//				System.out.println("o projecto est?? partilhado com estes users e est?? aceite");
//				return result;
//			} else {
//				System.out.println("o projecto n??o est?? partilhado/aceite com nenhum user");
//				return null;
//			}
//		
//		} catch (EJBException e) {
//			e.printStackTrace();
//			return null;
//		}
//		
//		
//	}
	
	
	
}
