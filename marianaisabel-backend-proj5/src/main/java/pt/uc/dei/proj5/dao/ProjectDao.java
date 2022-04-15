package pt.uc.dei.proj5.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;

import javax.persistence.criteria.Root;

import pt.uc.dei.proj5.dto.ProjectDTOResp;
import pt.uc.dei.proj5.dto.ProjectDTO;
import pt.uc.dei.proj5.entity.Keyword;
import pt.uc.dei.proj5.entity.Project;
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
	
	public static Project convertDTOToEntity(ProjectDTO projectDTO, User createdBy, User lastModifdBy ) {
		System.out.println("Entrei em convertDTOToEntity Project");
		Project projectEntity = new Project();
		projectEntity.setTitle(projectDTO.getTitle());
		projectEntity.setDescription(projectDTO.getDescription());
		projectEntity.setImage(projectDTO.getImage());
		projectEntity.setVisibility(projectDTO.isVisibility());
		
		if(createdBy!=null) {
			projectEntity.setCreatedBy(createdBy);
		}
		if(lastModifdBy!=null) {
			projectEntity.setLastModifBy(lastModifdBy);
			projectEntity.setLastModifDate(new Timestamp(System.currentTimeMillis()));
		}
		
		//TODO complete here
		//news
		//users associated
		
		
		return projectEntity;

	}

	public static ProjectDTOResp convertEntityToDTOResp(Project projectEntity) {
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
		
		if (projectEntity.getCreatedDate() != null) {
			projectDTOResp.setCreatedDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(projectEntity.getCreatedDate()));

		} else {
			projectDTOResp.setCreatedDate("");
		}
		
		
		if (projectEntity.getLastModifDate() != null) {
			projectDTOResp.setLastModifDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(projectEntity.getLastModifDate()));

		} else {
			projectDTOResp.setLastModifDate("");
		}
	

		//TODO complete here
//	projectDTOResp.setLastModifBy(UserDao.convertEntitytoDTOResp(projectEntity.getLastModifBy()));		
//		private ArrayList<UserDTOResp> associatedUsersOfThisProject= new ArrayList<>(); //se esta categoria foi partilhada com alguem este array é >0 e tem o username com quem se partilhou a mesma
//		private ArrayList<ProjectDTOResp> associatedNewsOfThisProject= new ArrayList<>(); //se esta categoria foi partilhada com alguem este array é >0 e tem o username com quem se partilhou a mesma

		
		return projectDTOResp;
	}

	
	/////////////////////////////////////////////////////////
	//METODOS devolvem Listas de Projectos
	////////////////////////////////////////////////////////
	

	
	/**
	 * devolve todos os projectos criados por de um User (apagados ou nao, partilhados ou nao, visiveis para todos ou nao)
	 * @param ProjectName
	 * @param user
	 * @return
	 */
	public List<Project> getAllProjectCreatedByUser(User createdBy) {

		final CriteriaQuery<Project> criteriaQuery = em.getCriteriaBuilder().createQuery(Project.class);

		Root<Project> c = criteriaQuery.from(Project.class);

		criteriaQuery.select(c).where(em.getCriteriaBuilder().equal(c.get("createdBy"), createdBy));
		
		try {
			return em.createQuery(criteriaQuery).getResultList();

		} catch (EJBException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("false");
			return null;
		}
	}

	
	/**
	 * devolve as projectos de um user que nao estejam marcados para apagar (independentemente de terem sido ou nao partilhados)
	 * @param user
	 * @return
	 */
	public List<Project> getNonDeletedProjectsCreatedByUser(User createdBy) {
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
	 * devolve as projectos de um user que estejam marcados para apagar (independentemente de terem sido ou nao partilhados)
	 * @param user
	 * @return
	 */
	public List<Project> getMarkedAsDeletedProjectsCreatedByUser(User createdBy) {
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
	//METODOS verificam condições 
	////////////////////////////////////////////////////////
	/**
	 * metodo para fazer a validaçao se um projecto com determinado titulo já existe em determinado user
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

}
