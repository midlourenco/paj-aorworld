package pt.uc.dei.proj5.dto;


public class ProjectSharingDTO {

	private boolean accepted;
	private String projectTitle;
	private int sharedProjectId;
	private UserDTOResp assocUserToThisProject; // com quem é que foi partilhada a noticia
	/**
	 * 
	 */
	public ProjectSharingDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public boolean isAccepted() {
		return accepted;
	}
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	public String getProjectTitle() {
		return projectTitle;
	}
	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}
	public int getSharedProjectId() {
		return sharedProjectId;
	}
	public void setSharedProjectId(int sharedProjectId) {
		this.sharedProjectId = sharedProjectId;
	}
	public UserDTOResp getAssocUserToThisProject() {
		return assocUserToThisProject;
	}
	public void setAssocUserToThisProject(UserDTOResp assocUserToThisProject) {
		this.assocUserToThisProject = assocUserToThisProject;
	}
	@Override
	public String toString() {
		return "ProjectSharingDTO [accepted=" + accepted + ", projectTitle=" + projectTitle + ", sharedProjectId="
				+ sharedProjectId + ", assocUserToThisProject=" + assocUserToThisProject + "]";
	}

	

	
	
}
