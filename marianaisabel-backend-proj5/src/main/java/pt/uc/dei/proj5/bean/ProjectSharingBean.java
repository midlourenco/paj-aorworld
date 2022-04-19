package pt.uc.dei.proj5.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import pt.uc.dei.proj5.dao.NotificationDao;
import pt.uc.dei.proj5.dao.ProjectDao;
import pt.uc.dei.proj5.dao.ProjectSharingDao;
import pt.uc.dei.proj5.dto.ProjectDTOResp;
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
	
	public boolean associateUserToProject(User lastModifBy, int projectId, String [] usersId) {
		System.out.println("entrei em associateUserToProject");
		
		try {
			Project project = projectDao.findEntityIfNonDelete(projectId);
			for (String userIdSTR : usersId) {
				int userId=  Integer.parseInt(userIdSTR);
				
				User userToAssocToProject = userService.getNonDeletedUserEntityById(userId);
				if(!projectSharingDao.isUserAlreadyAssociatedToProject(project, userToAssocToProject)) {
					System.out.println("o user pode ser associado ao projecto");
			
					ProjectSharing projectSharing = projectSharingDao.associateUserToProject(project, userToAssocToProject );
					notificationDao.inviteAssocProjectNotif(userToAssocToProject, projectSharing);			
					project.setLastModifByAndDate(lastModifBy);
					projectDao.merge(project);
				}
				return true;
			}
			return false;
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
	
	public boolean cancelAssocToProject(User lastModifBy, int projectId) {
		System.out.println("entrei em cancelAssocToProject");
		try {
			//ProjectSharing projectSharing = projectSharingDao.find(projectSharingId);
			Project project = projectDao.findEntityIfNonDelete(projectId);
			ProjectSharing projectSharing = projectSharingDao.getProjectAssociatedToUser(project, lastModifBy);
			if(projectSharing!=null) {
				projectSharingDao.delete(projectSharing);
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
	
	public ArrayList<ProjectDTOResp> getNonDeletedAssocProjectFromUser(User user){
		ArrayList<ProjectDTOResp> projectsDTORwsp =new ArrayList<>();
		
	//	List<ProjectSharing> projectSharing=projectSharingDao.getNonDeletedAssocProjectFromUser(user);
		
		
		
		return projectsDTORwsp;
	}
	
}