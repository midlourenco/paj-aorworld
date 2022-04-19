package pt.uc.dei.proj5.dto;


public class ProjectSharingDTO {

//	private boolean accepted;
	//private int projectId;
	private int userId; // com quem é que foi partilhada a noticia
	private String userRoleInProject;
	
	/**
	 * 
	 */
	public ProjectSharingDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
//	public boolean isAccepted() {
//		return accepted;
//	}
//	public void setAccepted(boolean accepted) {
//		this.accepted = accepted;
//	}
//	public int getProjectId() {
//		return projectId;
//	}
//	public void setProjectId(int projectId) {
//		this.projectId = projectId;
//	}
	public String getUserRoleInProject() {
		return userRoleInProject;
	}
	public void setUserRoleInProject(String userRoleInProject) {
		this.userRoleInProject = userRoleInProject;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "ProjectSharingDTO [userId=" + userId + ", userRoleInProject=" + userRoleInProject + "]";
	}
	

	
	

	

	
	
}
