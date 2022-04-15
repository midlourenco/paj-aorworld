package pt.uc.dei.proj5.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import pt.uc.dei.proj5.entity.Project;
import pt.uc.dei.proj5.entity.User;
import pt.uc.dei.proj5.dao.KeywordDao;
import pt.uc.dei.proj5.dao.ProjectDao;
import pt.uc.dei.proj5.dao.ProjectSharingDao;
import pt.uc.dei.proj5.dao.UserDao;
import pt.uc.dei.proj5.dto.ProjectDTO;
import pt.uc.dei.proj5.dto.ProjectDTOResp;
import pt.uc.dei.proj5.dto.UserDTO;

@RequestScoped
public class ProjectBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private UserDao userDao;
	@Inject
	private ProjectDao projectDao;
	@Inject
	private KeywordDao keywordDao;
	@Inject
	private ProjectSharingDao projectSharingDao;
	
	
	///////////////////////////////
	//GET DE PROJECTOS
	/////////////////////////////
		
	public Project getProjectEntitybyId(int projectId) {
		try {
			return projectDao.findEntityIfNonDelete(projectId);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	

	}
	
	
	
	///////////////////////////////
	//AUTORIZAçÃO SOBRE PROJECTOS
	/////////////////////////////
	
	
	//FOR PROJECT UPDATE Só PODE SER feito pelo user criador
	/**
	 * devolve se o user é o criador de um projecto (nao marcado para apagar)
	 * @param projectId
	 * @param userId
	 * @return
	 */
	public boolean isProjectCreatedByUser(int projectId, int  userId) {
		System.out.println("entrei em isProjectCreatedByUser e trouxe o id e user id: " + projectId + userId);

		try {
			User user = userDao.findEntityIfNonDelete(userId);
			Project project = projectDao.findEntityIfNonDelete(projectId);

			//USER ORIGINAL DETENTOR DO PROJECTO:
			if(project.getCreatedBy().getId()==user.getId()) {
				System.out.println("o utilizador é o titular deste projecto ");
				return true;
			} else {
				System.out.println("o projecto não pertence ao utilizador ");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ocorreu algum problema a procurar por projecto");
			return false;
		}
	}
	
	
	
	//FOR GETs do PROJECTO PODE SER feito pelo user criador e pelos que estão associados
	/**
	 * devolve se o user é o criador de um projecto (nao marcado para apagar) ou está associado a esse projecto por projectSharing
	 * @param projectId
	 * @param userId
	 * @return
	 */
	public boolean isProjectAssocToUser(int projectId, int  userId) {
		System.out.println("entrei em isProjectCreatedByUser e trouxe o id e user id: " + projectId + userId);

		try {
			User user = userDao.findEntityIfNonDelete(userId);
			Project project = projectDao.findEntityIfNonDelete(projectId);

			//USER ORIGINAL DETENTOR DO PROJECTO:
			if(project.getCreatedBy().getId()==user.getId()) {
				System.out.println("o utilizador é o titular deste projecto ");
				return true;
			} else if(projectSharingDao.isUserAlreadyAssociatedToProject(project, user)) {
				System.out.println("o projecto foi partilhado e aceite por este utilizador ");
				return true;
			} else {
				System.out.println("o projecto não pertence ao utilizador ");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ocorreu algum problema a procurar por projecto");
			return false;
		}
	}
	
	
	public boolean isProjectWithPublicVisibility(int projectId) {
		try {
			return projectDao.findEntityIfNonDelete(projectId).isVisibility();
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	

	}
	
	
	///////////////////////////////
	//FUNCIONALIDADES PROJECTOS
	/////////////////////////////
	
	
	public ProjectDTOResp addProject(User createdBy, ProjectDTO projectDTO ) {
		ProjectDTOResp projectDTOResp=null;
		System.out.println("Entrei em addProject ProjectBean"); 
		
		if(!projectDao.alreadyExistProjectTitleByThisCreatedUser(projectDTO.getTitle(), createdBy) && !projectDTO.getTitle().equals("")) {
			
			Project project = ProjectDao.convertDTOToEntity(projectDTO, createdBy, null);
			projectDao.persist(project);
			
			ArrayList<String> keywords = projectDTO.getKeywords();
			for (String keyword : keywords) {
				keywordDao.associateKeywordToProjectOrNews(keyword, project, null);
			}
			
			projectDTOResp=ProjectDao.convertEntityToDTOResp(project);
		}else {
			System.out.println("Já existe um projecto com este título criado por este utilizador");
		}
		
		
		return projectDTOResp;
	}
	
	
	public ProjectDTOResp getProject(int projectId) {
		return ProjectDao.convertEntityToDTOResp(projectDao.findEntityIfNonDelete(projectId));
	}
	
	
	
	public ArrayList<ProjectDTOResp> getAllProjectCreatedByUser(User createdBy){
		ArrayList<ProjectDTOResp> projectsDTOResp =new ArrayList<ProjectDTOResp> ();
		
		List<Project> projects =  projectDao.getAllProjectCreatedByUser(createdBy);
		
		for (Project project : projects) {
			projectsDTOResp.add(ProjectDao.convertEntityToDTOResp(project));
		}
		
		return projectsDTOResp;
	}

	public ArrayList<ProjectDTOResp> getOnlyPublicProjectsNonDeleted(){
		ArrayList<ProjectDTOResp> projectsDTOResp =new ArrayList<ProjectDTOResp> ();
		
		List<Project> projects =  projectDao.getOnlyPublicProjectsNonDeleted();
		
		for (Project project : projects) {
			projectsDTOResp.add(ProjectDao.convertEntityToDTOResp(project));
		}
		
		return projectsDTOResp;
	}

	public ArrayList<ProjectDTOResp> getOnlyPublicProjectsMarkedAsDeleted(){
		ArrayList<ProjectDTOResp> projectsDTOResp =new ArrayList<ProjectDTOResp> ();
		
		List<Project> projects =  projectDao.getOnlyPublicProjectsMarkedAsDeleted();
		
		for (Project project : projects) {
			projectsDTOResp.add(ProjectDao.convertEntityToDTOResp(project));
		}
		
		return projectsDTOResp;
	}

	public ArrayList<ProjectDTOResp> getAllProjectsNonDeleted(){
		ArrayList<ProjectDTOResp> projectsDTOResp =new ArrayList<ProjectDTOResp> ();
		
		List<Project> projects =  projectDao.getAllProjectsNonDeleted();
		
		for (Project project : projects) {
			projectsDTOResp.add(ProjectDao.convertEntityToDTOResp(project));
		}
		
		return projectsDTOResp;
	}

	public ArrayList<ProjectDTOResp> getAllProjectsMarkedAsDeleted(){
		ArrayList<ProjectDTOResp> projectsDTOResp =new ArrayList<ProjectDTOResp> ();
		
		List<Project> projects =  projectDao.getAllProjectsMarkedAsDeleted();
		
		for (Project project : projects) {
			projectsDTOResp.add(ProjectDao.convertEntityToDTOResp(project));
		}
		
		return projectsDTOResp;
	}
	
	
	
}
