package pt.uc.dei.proj5.bean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import pt.uc.dei.proj5.entity.Project;
import pt.uc.dei.proj5.dao.KeywordDao;
import pt.uc.dei.proj5.dao.ProjectDao;
import pt.uc.dei.proj5.dto.ProjectDTOResp;

@RequestScoped
public class KeywordBean {
	@Inject
	private KeywordDao keywordDao;
	
	
	public ArrayList<ProjectDTOResp> getOnlyPublicProjectsAssocToKeyword(String keyword){
		ArrayList<ProjectDTOResp>  projDTOResp= new ArrayList<>();
		List<Project> projects= keywordDao.getOnlyPublicProjectsAssocToKeyword(keyword);
		
		for (Project project : projects) {
			projDTOResp.add(ProjectDao.convertEntityToDTOResp(project));
		}
		return projDTOResp;
	}
	
	public ArrayList<ProjectDTOResp> getAllProjectsAssocToKeyword(String keyword){
		ArrayList<ProjectDTOResp>  projDTOResp= new ArrayList<>();
		List<Project> projects= keywordDao.getAllProjectsAssocToKeyword(keyword);
		
		for (Project project : projects) {
			projDTOResp.add(ProjectDao.convertEntityToDTOResp(project));
		}
		return projDTOResp;
	}
	
}
