package pt.uc.dei.proj5.dao;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;

import javax.persistence.criteria.Root;

import pt.uc.dei.proj5.dto.ProjectDTOResp;
import pt.uc.dei.proj5.dto.ProjectDTO;
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
	
	public static Project convertDTOToEntity(ProjectDTO projectDTO, User user) {
		System.out.println("Entrei em convertDTOToEntity Project");
		Project projectEntity = new Project();
	
		
		//TODO complete here
		
		
		return projectEntity;

	}

	public static ProjectDTO convertEntityToDTO(Project projectEntity) {
		ProjectDTO projectDTO = new ProjectDTO();
		//TODO complete here
		
		
		
		return projectDTO;
	}

	public static ProjectDTOResp convertEntityToDTOResp(Project projectEntity) {
		ProjectDTOResp ProjectDTOResp = new ProjectDTOResp();
	
		//TODO complete here
		
		return ProjectDTOResp;
	}

	
	
	
	/**
	 * devolve as projectos de um User (apagados ou nao, partilhadas ou nao)
	 * @param ProjectName
	 * @param user
	 * @return
	 */
	public boolean existProjectByNameFromUser(String ProjectName, User user) {

		final CriteriaQuery<Project> criteriaQuery = em.getCriteriaBuilder().createQuery(Project.class);

		Root<Project> c = criteriaQuery.from(Project.class);

		criteriaQuery.select(c).where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(c.get("user"), user),
				em.getCriteriaBuilder().equal(c.get("ProjectName"), ProjectName)));
		// não permite criar projecto com o mesmo nome se o utilizador já tiver uma
		// projecto com esse nome mesmo que esteja marcada para eliminar...
		try {

			List<Project> result = em.createQuery(criteriaQuery).getResultList();
			if (result.size() > 0) {

				return true;
			} else {

				return false;
			}
		} catch (EJBException e) {
			e.printStackTrace();

			return false;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("false");
			return false;
		}
	}

	
	/**
	 * devolve as projectos de um user que nao estejam marcadas para apagar (independentemente de terem sido ou nao partilhadas)
	 * @param user
	 * @return
	 */
	
	public List<Project> getNonDeletedProjectsFromUser(User user) {
		final CriteriaQuery<Project> criteriaQuery = em.getCriteriaBuilder().createQuery(Project.class);
		
		Root<Project> c = criteriaQuery.from(Project.class);
		
		criteriaQuery.select(c).where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(c.get("user"), user),
				em.getCriteriaBuilder().equal(c.get("deleted"), false)));
		try {
			return em.createQuery(criteriaQuery).getResultList();
		} catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * devolve as projectos de um user que estejam marcadas para apagar (independentemente de terem sido ou nao partilhadas)
	 * @param user
	 * @return
	 */
	
	public List<Project> getMarkedAsDeletedProjectsFromUser(User user) {
		final CriteriaQuery<Project> criteriaQuery = em.getCriteriaBuilder().createQuery(Project.class);
		Root<Project> c = criteriaQuery.from(Project.class);
		criteriaQuery.select(c).where(em.getCriteriaBuilder().and(em.getCriteriaBuilder().equal(c.get("user"), user),
				em.getCriteriaBuilder().equal(c.get("deleted"), true)));
		try {
			return em.createQuery(criteriaQuery).getResultList();
		} catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}

	// public boolean updateProjectFromUser(Project Project, User user) {

	// }
	
	

	

}
