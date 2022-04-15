package pt.uc.dei.proj5.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.persistence.criteria.Subquery;

import pt.uc.dei.proj5.dto.ProjectSharingDTO;
import pt.uc.dei.proj5.entity.Keyword;
import pt.uc.dei.proj5.entity.News;
import pt.uc.dei.proj5.entity.Project;
import pt.uc.dei.proj5.entity.ProjectSharing;
import pt.uc.dei.proj5.entity.User;
@Stateless
public class KeywordDao extends AbstractDao<Keyword> {
	private static final long serialVersionUID = 1L;

	public KeywordDao() {
		super(Keyword.class);
	}
	
	
	/////////////////////////////////////////////////////////
	//METODOS ESTATICOS DE CONVERSAO ENTRE ENTIDADE E DTOs
	////////////////////////////////////////////////////////
	

	


	/////////////////////////////////////////////////////////
	//METODOS devolvem Listas
	////////////////////////////////////////////////////////

	
	
	/////////////////////////////////////////////////////////
	//METODOS Queries sobre diversas condiçoes
	////////////////////////////////////////////////////////

	
	public List<Keyword> getKeywordsAssocToProjet(int projectId) {

		final CriteriaQuery<Keyword> criteriaQuery = em.getCriteriaBuilder().createQuery(Keyword.class);

		Root<Project> projectRoot = criteriaQuery.from(Project.class);
		Join<Project, Keyword> joinProjectsKeywords = projectRoot.join("keywords");

		criteriaQuery.where(em.getCriteriaBuilder().equal(projectRoot.get("projects"), projectId));
			

		try{
			List<Keyword> resultado= em.createQuery(criteriaQuery.select(joinProjectsKeywords)).getResultList();
			
			return resultado;

		}catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}


	public List<Keyword> getKeywordsAssocToNews(int newsId) {

		final CriteriaQuery<Keyword> criteriaQuery = em.getCriteriaBuilder().createQuery(Keyword.class);

		Root<News> newsRoot = criteriaQuery.from(News.class);
		Join<News, Keyword> joinNewsKeywords = newsRoot.join("keywords");
		
		criteriaQuery.where(em.getCriteriaBuilder().equal(newsRoot.get("news"), newsId));
			
		try{
			List<Keyword> resultado= em.createQuery(criteriaQuery.select(joinNewsKeywords)).getResultList();
			return resultado;

		}catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public List<News> getNewsAssocToKeyword(String keywordStr) {

		final CriteriaQuery<News> criteriaQuery = em.getCriteriaBuilder().createQuery(News.class);

		Root<Keyword> keywordRoot = criteriaQuery.from(Keyword.class);
		Join<Keyword,News> joinNewsKeywords = keywordRoot.join("keywords");
		
		criteriaQuery.where(em.getCriteriaBuilder().equal(keywordRoot.get("keywords"), keywordStr));
			
		try{
			List<News> resultado= em.createQuery(criteriaQuery.select(joinNewsKeywords)).getResultList();
			return resultado;

		}catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}
	public List<Project> getProjectsAssocToKeyword(String keywordStr) {

		final CriteriaQuery<Project> criteriaQuery = em.getCriteriaBuilder().createQuery(Project.class);

		Root<Keyword> keywordRoot = criteriaQuery.from(Keyword.class);
		Join<Keyword,Project> joinProjectKeywords = keywordRoot.join("keywords");
		
		criteriaQuery.where(em.getCriteriaBuilder().equal(keywordRoot.get("keywords"), keywordStr));
			
		try{
			List<Project> resultado= em.createQuery(criteriaQuery.select(joinProjectKeywords)).getResultList();
			return resultado;

		}catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
	public Keyword associateKeywordToProjectOrNews(String keywordStr, Project project, News news) {
		System.out.println("Entrei em convertDTOToEntity Project");
		Keyword keyword;
		//TODO complete here
		try {
			keyword= find(keywordStr);
			if(keyword!=null) {
				System.out.println("esta keyword já existe");
				if(news!=null) {
					keyword.getNews().add(news);//como temos um set de cada lado, não vão ficar combinações repetidas na BD
				}
				
				if(project!=null) {
					keyword.getProjects().add(project); //como temos um set de cada lado, não vão ficar combinações repetidas na BD
				}
				merge(keyword);
				System.out.println("adicionei news ou proj à keyword já existe");
			}
		} catch (EJBException e) {
			keyword = new Keyword();
			keyword.setKeyword(keywordStr);
			if(news!=null) {
				keyword.getNews().add(news);//como temos um set de cada lado, não vão ficar combinações repetidas na BD
			}
			
			if(project!=null) {
				keyword.getProjects().add(project);//como temos um set de cada lado, não vão ficar combinações repetidas na BD
			}
			persist(keyword);
			System.out.println("criei keyword nova com 1 news ou proj ");

		}
		
		return keyword;
	}


}
