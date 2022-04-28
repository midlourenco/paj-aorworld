package pt.uc.dei.proj5.bean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import pt.uc.dei.proj5.entity.Keyword;
import pt.uc.dei.proj5.entity.News;
import pt.uc.dei.proj5.entity.Project;
import pt.uc.dei.proj5.dao.KeywordDao;
import pt.uc.dei.proj5.dao.NewsDao;
import pt.uc.dei.proj5.dao.ProjectDao;
import pt.uc.dei.proj5.dto.NewsDTOResp;
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
	public ArrayList<ProjectDTOResp> getOnlyPublicMardkedAsDeletedProjectsAssocToKeyword(String keyword){
		ArrayList<ProjectDTOResp>  projDTOResp= new ArrayList<>();
		List<Project> projects= keywordDao.getOnlyPublicMardkedAsDeletedProjectsAssocToKeyword(keyword);
		
		for (Project project : projects) {
			projDTOResp.add(ProjectDao.convertEntityToDTOResp(project));
		}
		return projDTOResp;
	}
	
	public ArrayList<ProjectDTOResp> getAllMardkedAsDeletedProjectsAssocToKeyword(String keyword){
		ArrayList<ProjectDTOResp>  projDTOResp= new ArrayList<>();
		List<Project> projects= keywordDao.getAllMardkedAsDeletedProjectsAssocToKeyword(keyword);
		
		for (Project project : projects) {
			projDTOResp.add(ProjectDao.convertEntityToDTOResp(project));
		}
		return projDTOResp;
	}
	
	
	public ArrayList<NewsDTOResp> getOnlyPublicNewsAssocToKeyword(String keyword){
		ArrayList<NewsDTOResp>  newsDTOResp= new ArrayList<>();
		List<News> news= keywordDao.getOnlyPublicNewsAssocToKeyword(keyword);
		
		for (News n : news) {
			newsDTOResp.add(NewsDao.convertEntityToDTOResp(n));
		}
		return newsDTOResp;
	}
	
	public ArrayList<NewsDTOResp> getAllNewsAssocToKeyword(String keyword){
		ArrayList<NewsDTOResp>  newsDTOResp= new ArrayList<>();
		List<News> news= keywordDao.getAllNewsAssocToKeyword(keyword);
		
		for (News n : news) {
			newsDTOResp.add(NewsDao.convertEntityToDTOResp(n));
		}
		return newsDTOResp;
	}
	public ArrayList<NewsDTOResp> getOnlyPublicMardkedAsDeletedNewsAssocToKeyword(String keyword){
		ArrayList<NewsDTOResp>  newsDTOResp= new ArrayList<>();
		List<News> news= keywordDao.getOnlyPublicMardkedAsDeletedNewsAssocToKeyword(keyword);
		
		for (News n : news) {
			newsDTOResp.add(NewsDao.convertEntityToDTOResp(n));
		}
		return newsDTOResp;
	}
	
	public ArrayList<NewsDTOResp> getAllMardkedAsDeletedNewsAssocToKeyword(String keyword){
		ArrayList<NewsDTOResp>  newsDTOResp= new ArrayList<>();
		List<News> news= keywordDao.getAllMardkedAsDeletedNewsAssocToKeyword(keyword);
		
		for (News n : news) {
			newsDTOResp.add(NewsDao.convertEntityToDTOResp(n));
		}
		return newsDTOResp;
	}
	
	public ArrayList<String> getAllKeywords() {
		try {
		ArrayList<String>  str_keyword = new ArrayList<>();
		List<Keyword> keywords = keywordDao.findAll();
		for (Keyword k : keywords) {
			str_keyword.add(k.getKeyword());
		}
		return str_keyword;
		}catch(Exception e) {
			System.out.println("não há keywords");
			e.printStackTrace();
			return null;
		}
		
	}
}
