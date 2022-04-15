package pt.uc.dei.proj5.dao;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import pt.uc.dei.proj5.dto.NewsDTO;
import pt.uc.dei.proj5.dto.NewsDTOResp;
import pt.uc.dei.proj5.dto.ProjectSharingDTO;
import pt.uc.dei.proj5.entity.News;
import pt.uc.dei.proj5.entity.Project;
import pt.uc.dei.proj5.entity.ProjectSharing;
import pt.uc.dei.proj5.entity.User;
@Stateless
public class ProjectSharingDao extends AbstractDao<ProjectSharing> {
	private static final long serialVersionUID = 1L;

	public ProjectSharingDao() {
		super(ProjectSharing.class);
	}
	
	
	/////////////////////////////////////////////////////////
	//METODOS ESTATICOS DE CONVERSAO ENTRE ENTIDADE E DTOs
	////////////////////////////////////////////////////////
	
	public static Project convertDTOToEntity(ProjectSharingDTO projectSharingDTO, User createdBy, User lastModifdBy ) {
		System.out.println("Entrei em convertDTOToEntity Project");
		Project projectSharingEntity = new Project();
		//TODO complete here
		
		
		
		return projectSharingEntity;
	}

	public static ProjectSharingDTO convertEntityToDTO(Project project) {
		ProjectSharingDTO projectSharingDTO = new ProjectSharingDTO();
		//TODO complete here
		
		
		
		return projectSharingDTO;
	}


	/////////////////////////////////////////////////////////
	//METODOS devolvem Listas
	////////////////////////////////////////////////////////

	
	
	/////////////////////////////////////////////////////////
	//METODOS Queries sobre diversas condiçoes
	////////////////////////////////////////////////////////


	/**
	 * metodo que verifica se um user já está associado a algum projecto ou se é o criador desse projecto
	 *return true se já estiver associado ou se for o criador dele, falso caso contrário
	 */
	public boolean isUserAlreadyAssociatedToProject(Project project, User user) {
		
		if(project.getCreatedBy().getId()==user.getId()) {
			return true;
		}
		
		final CriteriaQuery<ProjectSharing> criteriaQuery = em.getCriteriaBuilder().createQuery(ProjectSharing.class);

		Root<ProjectSharing> c = criteriaQuery.from(ProjectSharing.class);

		criteriaQuery.select(c).where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(c.get("user"), user), //user convidado
				em.getCriteriaBuilder().equal(c.get("project"), project), //categoria que foi partilhada
				em.getCriteriaBuilder().equal(c.get("accepted"), true)));
		try {
		
			List<ProjectSharing> result = em.createQuery(criteriaQuery).getResultList(); // em principio a existir seria uma lista de 1 elemento
		
			if (result.size() > 0) {
				System.out.println("o projecto está partilhado com este user e está aceite");
				return true;
			} else {
				
				System.out.println("o projecto não está partilhada com este user ou não está aceite");
				
				return false;
			}
		
		} catch (EJBException e) {
			e.printStackTrace();
			return false;
		}

	
	
		
	}
	
	
}
