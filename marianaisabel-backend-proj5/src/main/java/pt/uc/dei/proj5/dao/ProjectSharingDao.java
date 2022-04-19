package pt.uc.dei.proj5.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
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
				System.out.println("o projecto não está partilhado com este user ou não está aceite");
				return false;
			}
		
		} catch (EJBException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	public ProjectSharing associateUserToProject(Project project, User user) {
		ProjectSharing projectSharing = new ProjectSharing();
		projectSharing.setAccepted(user.isAutoAcceptInvites());
		projectSharing.setProject(project);
		projectSharing.setUser(user);

		persist(projectSharing);
		return projectSharing;
	}
	


	/**
	 * metodo que verifica se um user já está associado a algum projecto ou se é o criador desse projecto
	 *return true se já estiver associado ou se for o criador dele, falso caso contrário
	 */
	public ProjectSharing getProjectAssociatedToUser(Project project, User user) {
		
		final CriteriaQuery<ProjectSharing> criteriaQuery = em.getCriteriaBuilder().createQuery(ProjectSharing.class);
		Root<ProjectSharing> c = criteriaQuery.from(ProjectSharing.class);
		criteriaQuery.select(c).where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(c.get("user"), user), //user convidado
				em.getCriteriaBuilder().equal(c.get("project"), project), //categoria que foi partilhada
				em.getCriteriaBuilder().equal(c.get("accepted"), false)));
		
		try {
			 ProjectSharing result = em.createQuery(criteriaQuery).getSingleResult(); // em principio a existir seria uma lista de 1 elemento
			 return result;
		
		} catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	

	/**
	 * lista de projectos em que um utilizador está associado (após partilha aceite)
	 * @param user
	 * @return
	 */
	public List<User> getUserAssocToProject(Project project) {
		final CriteriaQuery<User> criteriaQuery = em.getCriteriaBuilder().createQuery(User.class);
		Root<ProjectSharing> c = criteriaQuery.from(ProjectSharing.class);
		Join<ProjectSharing, User> users = c.join("user");
		
		criteriaQuery.select(users).where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(c.get("project"), project), //projecto que foi partilhada
				em.getCriteriaBuilder().equal(c.get("accepted"), true)));
		
		try {
			List<User> result = em.createQuery(criteriaQuery).getResultList(); // em principio a existir seria uma lista de 1 elemento
			if (result.size() > 0) {
				System.out.println("o projecto está partilhado com estes users e está aceite");
				return result;
			} else {
				System.out.println("o projecto não está partilhado/aceite com nenhum user");
				return null;
			}
		
		} catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	/**
	 * lista de projectos em que um utilizador está associado (após partilha aceite)
	 * @param user
	 * @return
	 */
	public List<Project> getNonDeletedAssocProjectFromUser(User user) {
		final CriteriaQuery<Project> criteriaQuery = em.getCriteriaBuilder().createQuery(Project.class);
		Root<ProjectSharing> c = criteriaQuery.from(ProjectSharing.class);
		Join<ProjectSharing, Project> project = c.join("project");

		criteriaQuery.select(project).where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(c.get("user"), user), //user convidado
				em.getCriteriaBuilder().equal(c.get("accepted"), true),
				em.getCriteriaBuilder().equal(project.get("delete"), false)));
		
		try {
			List<Project> result = em.createQuery(criteriaQuery).getResultList(); // em principio a existir seria uma lista de 1 elemento
			if (result.size() > 0) {
				System.out.println("o projecto está partilhado com este user e está aceite");
				return result;
			} else {
				System.out.println("o projecto não está partilhado com este user ou não está aceite");
				return null;
			}
		
		} catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Project> getOnlyPublicNonDeletedAssocProjectFromUser(User user) {
		final CriteriaQuery<Project> criteriaQuery = em.getCriteriaBuilder().createQuery(Project.class);
		Root<ProjectSharing> c = criteriaQuery.from(ProjectSharing.class);
		Join<ProjectSharing, Project> project = c.join("project");

		criteriaQuery.select(project).where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(c.get("user"), user), //user convidado
				em.getCriteriaBuilder().equal(c.get("accepted"), true),
				em.getCriteriaBuilder().equal(project.get("visibility"), true),
				em.getCriteriaBuilder().equal(project.get("delete"), false)));
		
		try {
			List<Project> result = em.createQuery(criteriaQuery).getResultList(); // em principio a existir seria uma lista de 1 elemento
			if (result.size() > 0) {
				System.out.println("o projecto está partilhado com este user e está aceite");
				return result;
			} else {
				System.out.println("o projecto não está partilhado com este user ou não está aceite");
				return null;
			}
		
		} catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Project> getMarkedAsDeletedAssocProjectFromUser(User user) {
		final CriteriaQuery<Project> criteriaQuery = em.getCriteriaBuilder().createQuery(Project.class);
		Root<ProjectSharing> c = criteriaQuery.from(ProjectSharing.class);
		Join<ProjectSharing, Project> project = c.join("project");

		criteriaQuery.select(project).where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(c.get("user"), user), //user convidado
				em.getCriteriaBuilder().equal(c.get("accepted"), true),
				em.getCriteriaBuilder().equal(project.get("delete"), true)));
		
		try {
			List<Project> result = em.createQuery(criteriaQuery).getResultList(); // em principio a existir seria uma lista de 1 elemento
			if (result.size() > 0) {
				System.out.println("o projecto está partilhado com este user e está aceite");
				return result;
			} else {
				System.out.println("o projecto não está partilhado com este user ou não está aceite");
				return null;
			}
		
		} catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Project> getOnlyPublicMarkedAsDeletedAssocProjectFromUser(User user) {
		final CriteriaQuery<Project> criteriaQuery = em.getCriteriaBuilder().createQuery(Project.class);
		Root<ProjectSharing> c = criteriaQuery.from(ProjectSharing.class);
		Join<ProjectSharing, Project> project = c.join("project");

		criteriaQuery.select(project).where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(c.get("user"), user), //user convidado
				em.getCriteriaBuilder().equal(c.get("accepted"), true),
				em.getCriteriaBuilder().equal(project.get("visibility"), true),
				em.getCriteriaBuilder().equal(project.get("delete"), true)));
		
		try {
			List<Project> result = em.createQuery(criteriaQuery).getResultList(); // em principio a existir seria uma lista de 1 elemento
			if (result.size() > 0) {
				System.out.println("o projecto está partilhado com este user e está aceite");
				return result;
			} else {
				System.out.println("o projecto não está partilhado com este user ou não está aceite");
				return null;
			}
		
		} catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * lista de usernames com os quais umaprojecto foi partilhada
	 * @param Project
	 * @return
	 */
	public ArrayList<String> getUsernamesOfSharingOfThisProject(Project project){
		ArrayList<String> teamNames = new ArrayList<>();
		
		System.out.println("Entrei em  getUsernamesOfSharingOfThisProject em ProjectSharingDao " );
		final CriteriaQuery<ProjectSharing> criteriaQuery = em.getCriteriaBuilder().createQuery(ProjectSharing.class);
		Root<ProjectSharing> c = criteriaQuery.from(ProjectSharing.class);
		criteriaQuery.select(c).where(em.getCriteriaBuilder().equal(c.get("project"), project));
		
		try {
			//Listas das partilhas destaprojecto à qual se vai retirar o nome com quem foram partilhadas
			List<ProjectSharing> partilhas = em.createQuery(criteriaQuery).getResultList(); 
			for (ProjectSharing partilha : partilhas) {
				teamNames.add(partilha.getUser().getFirstName() + " " + partilha.getUser().getLastName());
			}
			
			if (teamNames.size() > 0) {
				System.out.println("projecto é partilhada com 1 ou + users ");
				return teamNames;
			} else {
				System.out.println("projecto não é partilhada com nenhum user ");
				return null;
			}
		
		} catch (EJBException e) {
			e.printStackTrace();
			return null;
		}	
	}
}
