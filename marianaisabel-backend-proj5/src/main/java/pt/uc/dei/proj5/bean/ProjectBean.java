package pt.uc.dei.proj5.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import pt.uc.dei.proj5.entity.Keyword;
import pt.uc.dei.proj5.entity.News;
import pt.uc.dei.proj5.entity.Project;
import pt.uc.dei.proj5.entity.User;
import pt.uc.dei.proj5.dao.KeywordDao;
import pt.uc.dei.proj5.dao.ProjectDao;
import pt.uc.dei.proj5.dao.ProjectSharingDao;
import pt.uc.dei.proj5.dao.NewsDao;
import pt.uc.dei.proj5.dao.UserDao;
import pt.uc.dei.proj5.dto.ProjectDTO;
import pt.uc.dei.proj5.dto.ProjectDTOResp;
import pt.uc.dei.proj5.dto.UserDTO;
import pt.uc.dei.proj5.dto.UserDTOResp;
import pt.uc.dei.proj5.dto.UserDTORespSharingProject;

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
	private NewsDao newsDao;
	@Inject
	private ProjectSharingDao projectSharingDao;
	@Inject
	private ProjectSharingBean projectSharingService;
	@Inject
	private DashboardBean dashboardService;
	
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
			
			Project project = ProjectDao.convertDTOToEntity(projectDTO,null);
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
			dashboardService.updateGeneralDashboard();
//			As noticias é que adicionam projectos
//		Set<News> news = new HashSet<>();
//			ArrayList<Integer> newsIdList = projectDTO.getNews();
//			for (Integer newsId : newsIdList) {
//				System.out.println("entrei no for das newsId");
//				//try {
//					News n = newsDao.findEntityIfNonDelete(newsId);//a ideia será adicionar uma noticia ja existente
//					if(n!=null) {
//						System.out.println(n);
//						news.add(n);
//					}
//					System.out.println("adicionei a news ao set");
////				}catch (Exception e) {
////					e.printStackTrace();
////					System.out.println("a noticia " + newsId + " nao existe ou está marcada para eliminar");
////				}
//			}
//			if(news.size()>0) {
//				project.setNews(news);
//			}
			ProjectDTOResp projectDTOResp=ProjectDao.convertEntityToDTOResp(project);
//		}else {
//			System.out.println("Já existe um projecto com este título criado por este utilizador");
//		}
	
		return projectDTOResp;
	}
	
	
	public ProjectDTOResp updateProject(User lastModifBy, int projectId, ProjectDTO projectDTO ) {
		
		Project project = getProjectEntityById(projectId);
		project = ProjectDao.convertDTOToEntity(projectDTO,project);
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
		dashboardService.updateGeneralDashboard();
		
		ProjectDTOResp projectDTOResp=ProjectDao.convertEntityToDTOResp(project);
		
		return projectDTOResp;
	}
	
	
	
	/**
	 * Método que no caso em que o projecto não esteja marcado para eliminar, marca-o para eliminar, (FUNCIONALIDADE SUSPENSA: caso contrário elimina-o da Base de dados)
	 * @return
	 */
	public boolean deleteProject(String authString,int projectID) {
		try {
			User lastModifBy= userDao.findEntityIfNonDelete(authString);
			Project project = projectDao.find(projectID);
			if (project.isDeleted()) {
				System.out.println("nesta fase, não faz nada. nao permitimos delete definitivo da base de dados");
//				projectDao.deleteById(userID); // este delete vai remover o conteudo associado ao user - REMOVER OS CASCADES?!?
//				dashboardService.updateGeneralDashboard();
				return false;		
			} else {
				projectDao.markAsDeleted(projectID,lastModifBy); 
				dashboardService.updateGeneralDashboard();// fica marcado como deleted na BD
				//project.setLastModifByAndDate(lastModifBy);
				return true;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ocorreu algum problema a procurar por user na BD - user não existe?");
			return false;
		}
	}
	


	/**
	 * Método que permite desmarcar de eliminar de um projecto
	 * @return
	 */
	public boolean undeleteProject(String authString,int projectID) {
		try {
			User lastModifBy= userDao.findEntityIfNonDelete(authString);
			Project project = projectDao.find(projectID);
			if (project.isDeleted()) { // se estiver marcado como deleted coloca o delete a false	
				projectDao.markAsNonDeleted(projectID,lastModifBy);
				dashboardService.updateGeneralDashboard();
				//project.setLastModifByAndDate(lastModifBy);
				return true;
			} else { // se não estiver marcado como delete não faz nada;
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ocorreu algum problema a procurar por user na BD - user não existe?");
			return false;
		}
	}
	
	
	
	///////////////////////////////
	//LISTAS DE PROJECTOS
	/////////////////////////////
	//************LISTAS Por user*******
	
	public ArrayList<ProjectDTOResp> getAllNonDeletedProjectsFromUser(User createdBy){
		ArrayList<ProjectDTOResp> projectsDTOResp =new ArrayList<ProjectDTOResp> ();
		
		List<Project> projects =  projectDao.getAllNonDeletedProjectsFromUser(createdBy);
		
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
			//projectsDTOResp.add(ProjectDao.convertEntityToDTOResp(project));
			ProjectDTOResp projectDTOResp= ProjectDao.convertEntityToDTOResp(project);
			ArrayList<UserDTORespSharingProject> assocUsers =projectSharingService.getUserAssocToProject(project.getId());
			if(assocUsers!=null) {
			projectDTOResp.setAssociatedUsers(assocUsers);
			}
			projectsDTOResp.add(projectDTOResp);
		}
		
		return projectsDTOResp;
	}

	public ArrayList<ProjectDTOResp> getOnlyPublicProjectsMarkedAsDeleted(){
		ArrayList<ProjectDTOResp> projectsDTOResp =new ArrayList<ProjectDTOResp> ();
		
		List<Project> projects =  projectDao.getOnlyPublicProjectsMarkedAsDeleted();
		
		for (Project project : projects) {
			//projectsDTOResp.add(ProjectDao.convertEntityToDTOResp(project));
			ProjectDTOResp projectDTOResp= ProjectDao.convertEntityToDTOResp(project);
			ArrayList<UserDTORespSharingProject> assocUsers =projectSharingService.getUserAssocToProject(project.getId());
			if(assocUsers!=null) {
			projectDTOResp.setAssociatedUsers(assocUsers);
			}
			projectsDTOResp.add(projectDTOResp);
		}
		
		return projectsDTOResp;
	}

	public ArrayList<ProjectDTOResp> getAllProjectsNonDeleted(){
		ArrayList<ProjectDTOResp> projectsDTOResp =new ArrayList<ProjectDTOResp> ();
		
		List<Project> projects =  projectDao.getAllProjectsNonDeleted();
		
		for (Project project : projects) {
			//projectsDTOResp.add(ProjectDao.convertEntityToDTOResp(project));
			ProjectDTOResp projectDTOResp= ProjectDao.convertEntityToDTOResp(project);
			ArrayList<UserDTORespSharingProject> assocUsers =projectSharingService.getUserAssocToProject(project.getId());
			if(assocUsers!=null) {
			projectDTOResp.setAssociatedUsers(assocUsers);
			}
			projectsDTOResp.add(projectDTOResp);
		}
		
		return projectsDTOResp;
	}

	public ArrayList<ProjectDTOResp> getAllProjectsMarkedAsDeleted(){
		ArrayList<ProjectDTOResp> projectsDTOResp =new ArrayList<ProjectDTOResp> ();
		
		List<Project> projects =  projectDao.getAllProjectsMarkedAsDeleted();
		
		for (Project project : projects) {
			//projectsDTOResp.add(ProjectDao.convertEntityToDTOResp(project));
			ProjectDTOResp projectDTOResp= ProjectDao.convertEntityToDTOResp(project);
			ArrayList<UserDTORespSharingProject> assocUsers =projectSharingService.getUserAssocToProject(project.getId());
			if(assocUsers!=null) {
			projectDTOResp.setAssociatedUsers(assocUsers);
			}
			projectsDTOResp.add(projectDTOResp);
		}
		
		return projectsDTOResp;
	}

	
}
