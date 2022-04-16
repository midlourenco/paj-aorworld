package pt.uc.dei.proj5.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import pt.uc.dei.proj5.entity.Keyword;
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
		
	public Project getNonDeletedProjectEntityById(int projectId) {
		try {
			return projectDao.findEntityIfNonDelete(projectId);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public ProjectDTOResp getNonDeletedProjectDTORespById(int projectId) {
		return ProjectDao.convertEntityToDTOResp(getNonDeletedProjectEntityById(projectId));
	}
	
	public Project getProjectEntityById(int projectId) {
		try {
			return projectDao.find(projectId);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ProjectDTOResp getProjectDTORespById(int projectId) {
		return ProjectDao.convertEntityToDTOResp(getProjectEntityById(projectId));
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
		System.out.println("Entrei em addProject ProjectBean"); 
		
		//if(!projectDao.alreadyExistProjectTitleByThisCreatedUser(projectDTO.getTitle(), createdBy) && !projectDTO.getTitle().equals("")) { ---> RETIRADA VALIDAÇAO /RESTRIÇÂO De um user nao poder inserir mais do 1 proj com o mesmo titulo
			
			Project project = ProjectDao.convertDTOToEntity(projectDTO);
			project.setCreatedBy(createdBy); //adiciono o titular do projecto À entidade
			projectDao.persist(project);
			
			Set<Keyword> keywords = new HashSet<>();
			ArrayList<String> keywordsSTR = projectDTO.getKeywords();
			for (String keyword : keywordsSTR) {
				System.out.println("entrei no for das keywords");
				keywords.add(keywordDao.getKeywordEntityFromString(keyword));
				System.out.println("adicionei a keyword ao set");
				//Keyword keywordEntity =	keywordDao.getKeywordEntityFromString(keyword);
				//Project projectEnytity= getProjectEntitybyId(project.getId());
				//System.out.println("criei entity keyword" + keywordEntity + "vou adicionar a projecto "+ project);
				//projectDao.associateKeywordToProject(keywordEntity, project);
				//System.out.println("associei ao proj. fim do ciclo for");
			}
			project.setKeywords(keywords);
			projectDao.merge(project);
	
			ProjectDTOResp projectDTOResp=ProjectDao.convertEntityToDTOResp(project);
//		}else {
//			System.out.println("Já existe um projecto com este título criado por este utilizador");
//		}
	
		return projectDTOResp;
	}
	
	
	public ProjectDTOResp updateProject(User lastModifBy, int projectId, ProjectDTO projectDTO ) {
		Project project = ProjectDao.convertDTOToEntity(projectDTO);
		project.setId(projectId);
		project.setLastModifByAndDate(lastModifBy);
		Set<Keyword> keywords = new HashSet<>();
		ArrayList<String> keywordsSTR = projectDTO.getKeywords();
		for (String keyword : keywordsSTR) {
			System.out.println("entrei no for das keywords");
			keywords.add(keywordDao.getKeywordEntityFromString(keyword));
			System.out.println("adicionei a keyword ao set");
		}
		project.setKeywords(keywords);
		projectDao.merge(project);
		
		ProjectDTOResp projectDTOResp=ProjectDao.convertEntityToDTOResp(project);
		
		return projectDTOResp;
	}
	
	
	///////////////////////////////
	//LISTAS DE PROJECTOS
	/////////////////////////////
	//************LISTAS Por user*******
	
	
	public ArrayList<ProjectDTOResp> getAllNonDeletedProjectsFromUser(User createdBy){
		ArrayList<ProjectDTOResp> projectsDTOResp =new ArrayList<ProjectDTOResp> ();
		
		List<Project> projects =  projectDao.getOnlyPublicNonDeletedProjectsFromUser(createdBy);
		
		for (Project project : projects) {
			projectsDTOResp.add(ProjectDao.convertEntityToDTOResp(project));
		}
		
		return projectsDTOResp;
	}
	
	public ArrayList<ProjectDTOResp> getOnlyPublicNonDeletedProjectsFromUser(User createdBy){
		ArrayList<ProjectDTOResp> projectsDTOResp =new ArrayList<ProjectDTOResp> ();
		
		List<Project> projects =  projectDao.getOnlyPublicNonDeletedProjectsFromUser(createdBy);
		
		for (Project project : projects) {
			projectsDTOResp.add(ProjectDao.convertEntityToDTOResp(project));
		}
		
		return projectsDTOResp;
	}
	
	
	
	
	
	public ArrayList<ProjectDTOResp> getAllProjectsMarkedAsDeletedFromUser(User createdBy){
		ArrayList<ProjectDTOResp> projectsDTOResp =new ArrayList<ProjectDTOResp> ();
		
		List<Project> projects =  projectDao.getAllProjectsMarkedAsDeletedFromUser(createdBy);
		
		for (Project project : projects) {
			projectsDTOResp.add(ProjectDao.convertEntityToDTOResp(project));
		}
		
		return projectsDTOResp;
	}

	
	public ArrayList<ProjectDTOResp> getOnlyPublicProjectsMarkedAsDeletedFromUser(User createdBy){
		ArrayList<ProjectDTOResp> projectsDTOResp =new ArrayList<ProjectDTOResp> ();
		
		List<Project> projects =  projectDao.getOnlyPublicProjectsMarkedAsDeletedFromUser(createdBy);
		
		for (Project project : projects) {
			projectsDTOResp.add(ProjectDao.convertEntityToDTOResp(project));
		}
		
		return projectsDTOResp;
	}
	
	
	
	
	
	
	//************LISTAS GERAIS*******
	
	
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
