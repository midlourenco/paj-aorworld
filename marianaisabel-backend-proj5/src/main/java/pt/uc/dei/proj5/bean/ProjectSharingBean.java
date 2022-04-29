package pt.uc.dei.proj5.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import pt.uc.dei.proj5.dao.NotificationDao;
import pt.uc.dei.proj5.dao.ProjectDao;
import pt.uc.dei.proj5.dao.ProjectSharingDao;
import pt.uc.dei.proj5.dao.UserDao;
import pt.uc.dei.proj5.dto.ProjectDTOResp;
import pt.uc.dei.proj5.dto.ProjectSharingDTO;
import pt.uc.dei.proj5.dto.UserDTOResp;
import pt.uc.dei.proj5.entity.Project;
import pt.uc.dei.proj5.entity.ProjectSharing;
import pt.uc.dei.proj5.entity.User;

@RequestScoped
public class ProjectSharingBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private UserBean userService;
	@Inject
	private ProjectDao projectDao;
	@Inject
	private ProjectSharingDao projectSharingDao;
	@Inject
	private NotificationDao notificationDao;
	
	public boolean associateUserToProject(User createBy, int projectId,ProjectSharingDTO projectSharingDTO) {
		System.out.println("entrei em associateUserToProject");
		
		try {
			Project project = projectDao.findEntityIfNonDelete(projectId);
			//for (String userIdSTR : usersId) {
				//int userId=  Integer.parseInt(userIdSTR);
				
			User userToAssocToProject = userService.getNonDeletedUserEntityById(projectSharingDTO.getUserId());
			if(!projectSharingDao.isUserAlreadyAssociatedToProject(project, userToAssocToProject)) {
				System.out.println("o user pode ser associado ao projecto");
		
				ProjectSharing projectSharing = projectSharingDao.associateUserToProject(project, projectSharingDTO, userToAssocToProject );
				notificationDao.inviteAssocProjectNotif(userToAssocToProject, projectSharing);			
				project.setLastModifByAndDate(createBy);
				projectDao.merge(project);
			}
			return true;
			//}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public boolean acceptAssocToProject(User lastModifBy, int projectId) {
		System.out.println("entrei em acceptAssocToProject");
		try {
			//ProjectSharing projectSharing = projectSharingDao.find(projectSharingId);
			Project project = projectDao.findEntityIfNonDelete(projectId);
			ProjectSharing projectSharing = projectSharingDao.getProjectAssociatedToUser(project, lastModifBy);
			if(projectSharing!=null) {
				projectSharing.setAccepted(true);
				projectSharingDao.merge(projectSharing);
				project.setLastModifByAndDate(lastModifBy);
				projectDao.merge(project);
				return true;
			}
			return false;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean cancelAssocToProject(User lastModifBy, int projectId,ProjectSharingDTO projectSharingDTO) {
		System.out.println("entrei em cancelAssocToProject");
		try {
			User userToDesassocFromProject = userService.getNonDeletedUserEntityById(projectSharingDTO.getUserId());
			//ProjectSharing projectSharing = projectSharingDao.find(projectSharingId);
			Project project = projectDao.findEntityIfNonDelete(projectId);
			ProjectSharing projectSharing = projectSharingDao.getProjectAssociatedToUser(project, userToDesassocFromProject);
			if(projectSharing!=null) {
				System.out.println("entrei no if do projectSharing nao nulo");
				projectSharingDao.delete(projectSharing);
				System.out.println("apguei o projectSharing");
				project.setLastModifByAndDate(lastModifBy);
				System.out.println("alterei a data e o modificado por no projecto");
				Project projectToSave = projectDao.findEntityIfNonDelete(projectId);
				projectDao.merge(projectToSave);
				System.out.println("fiz merge ao projecto");
				return true;
			}
			return false;
		}catch(Exception e) {
			System.out.println("entrei no catch em cancelAssocToProject");
			e.printStackTrace();
			return false;
		}
	}
	
	
	//TODO !!! ideia:  Map<String,  ArrayList<UserDTOResp>> ===>>> Map<funçao do user no projecto, Lista de users>
	
	public ArrayList<UserDTOResp> getUserAssocToProject(int projectId){
		try {
		Project project = projectDao.findEntityIfNonDelete(projectId);
		ArrayList<UserDTOResp> usersDTOResp =new ArrayList<>();
		
		List<User> users=projectSharingDao.getUserAssocToProject(project);
		
		for (User user : users) {
			usersDTOResp.add(UserDao.convertEntitytoDTOResp(user));
		}
		
		return usersDTOResp;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<ProjectDTOResp> getNonDeletedAssocProjectFromUser(User user){
		ArrayList<ProjectDTOResp> projectsDTOResp =new ArrayList<>();
		
		List<Project> projects=projectSharingDao.getNonDeletedAssocProjectFromUser(user);
		
		for (Project project : projects) {
			ProjectDTOResp projDTOResp = ProjectDao.convertEntityToDTOResp(project);
			ArrayList<UserDTOResp> users = getUserAssocToProject(project.getId());
			projDTOResp.setAssociatedUsers(users);
			projectsDTOResp.add(projDTOResp);
		}
		
		
		return projectsDTOResp;
	}
	
	public ArrayList<ProjectDTOResp> getOnlyPublicNonDeletedAssocProjectFromUser(User user){
		ArrayList<ProjectDTOResp> projectsDTOResp =new ArrayList<>();
		
		List<Project> projects=projectSharingDao.getOnlyPublicNonDeletedAssocProjectFromUser(user);
		
		for (Project project : projects) {
			ProjectDTOResp projDTOResp = ProjectDao.convertEntityToDTOResp(project);
			ArrayList<UserDTOResp> users = getUserAssocToProject(project.getId());
			projDTOResp.setAssociatedUsers(users);
			projectsDTOResp.add(projDTOResp);
		}	
		return projectsDTOResp;
	}
	
	
	
	
	public ArrayList<ProjectDTOResp> getNonDeletedAssocProjectFromUser(String authString){
		ArrayList<ProjectDTOResp> projectsDTOResp =new ArrayList<>();
		User user = userService.getNonDeletedEntityByToken(authString);
		List<Project> projects=projectSharingDao.getNonDeletedAssocProjectFromUser(user);
		
		for (Project project : projects) {
			ProjectDTOResp projDTOResp = ProjectDao.convertEntityToDTOResp(project);
			ArrayList<UserDTOResp> users = getUserAssocToProject(project.getId());
			projDTOResp.setAssociatedUsers(users);
			projectsDTOResp.add(projDTOResp);
		}
		
		
		return projectsDTOResp;
	}
	
	public ArrayList<ProjectDTOResp> getOnlyPublicNonDeletedAssocProjectFromUser(String authString){
		ArrayList<ProjectDTOResp> projectsDTOResp =new ArrayList<>();
		User user = userService.getNonDeletedEntityByToken(authString);
		List<Project> projects=projectSharingDao.getOnlyPublicNonDeletedAssocProjectFromUser(user);
		
		for (Project project : projects) {
			ProjectDTOResp projDTOResp = ProjectDao.convertEntityToDTOResp(project);
			ArrayList<UserDTOResp> users = getUserAssocToProject(project.getId());
			projDTOResp.setAssociatedUsers(users);
			projectsDTOResp.add(projDTOResp);
		}	
		return projectsDTOResp;
	}
	
	
	
	
	public ArrayList<ProjectDTOResp> getMarkedAsDeletedAssocProjectFromUser(User user){
		ArrayList<ProjectDTOResp> projectsDTOResp =new ArrayList<>();
		
		List<Project> projects=projectSharingDao.getMarkedAsDeletedAssocProjectFromUser(user);
		
		for (Project project : projects) {
			ProjectDTOResp projDTOResp = ProjectDao.convertEntityToDTOResp(project);
			ArrayList<UserDTOResp> users = getUserAssocToProject(project.getId());
			projDTOResp.setAssociatedUsers(users);
			projectsDTOResp.add(projDTOResp);
		}
		
		return projectsDTOResp;
	}
	
	public ArrayList<ProjectDTOResp> getOnlyPublicMarkedAsDeletedAssocProjectFromUser(User user){
		ArrayList<ProjectDTOResp> projectsDTOResp =new ArrayList<>();
		
		List<Project> projects=projectSharingDao.getOnlyPublicMarkedAsDeletedAssocProjectFromUser(user);
		
		for (Project project : projects) {
			ProjectDTOResp projDTOResp = ProjectDao.convertEntityToDTOResp(project);
			ArrayList<UserDTOResp> users = getUserAssocToProject(project.getId());
			projDTOResp.setAssociatedUsers(users);
			projectsDTOResp.add(projDTOResp);
		}
		return projectsDTOResp;
	}
	
	
	public ArrayList<String> getUsernamesOfSharingOfThisProject(int projectId){
		Project project = projectDao.findEntityIfNonDelete(projectId);
		ArrayList<String>  result=projectSharingDao.getUsernamesOfSharingOfThisProject(project);
		
		
		return result;
	}
	
	
}