package pt.uc.dei.proj5.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.persistence.criteria.Subquery;

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
	public static ArrayList<String> convertEntityListToArrayString (Set<Keyword> keywords){
		System.out.println("Entrei em convertEntityListToArrayString Keyword");
		
		 ArrayList<String> arrayString = new ArrayList<>();
		 
		 for (Keyword keyword : keywords) {
			 arrayString.add(keyword.getKeyword());
			 System.out.println(keyword.getKeyword());
		}
		 
		 
		 return arrayString;
		
		
	}

	


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

	
	public List<News> getOnlyPublicNewsAssocToKeyword(String keywordStr) {
//		final CriteriaQuery<News> criteriaQuery = em.getCriteriaBuilder().createQuery(News.class);
//		Root<Keyword> keywordRoot = criteriaQuery.from(Keyword.class);
//		Join<Keyword,News> joinNewsKeywords = keywordRoot.join("keywords");
//		criteriaQuery.where(em.getCriteriaBuilder().equal(keywordRoot.get("keywords"), keywordStr));
//				final CriteriaQuery<Project> criteriaQuery = em.getCriteriaBuilder().createQuery(Project.class);
		final CriteriaQuery<News> criteriaQuery = em.getCriteriaBuilder().createQuery(News.class);
		Root<Keyword> keyword = criteriaQuery.from(Keyword.class);
		Join<Keyword, News> news = keyword.join("news");
		criteriaQuery.select(news).where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(keyword.get("keyword"), keywordStr),
				em.getCriteriaBuilder().equal(news.get("visibility"), true)));

		try{
			List<News> resultado= em.createQuery(criteriaQuery.select(news)).getResultList();
			return resultado;


		}catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public List<News> getAllNewsAssocToKeyword(String keywordStr) {
		final CriteriaQuery<News> criteriaQuery = em.getCriteriaBuilder().createQuery(News.class);
		Root<Keyword> keyword = criteriaQuery.from(Keyword.class);
		Join<Keyword, News> news = keyword.join("news");
		criteriaQuery.select(news).where(em.getCriteriaBuilder().equal(keyword.get("keyword"), keywordStr));
			
		try{
			List<News> result = em.createQuery(criteriaQuery.select(news)).getResultList();
			return result;
		}catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Project> getOnlyPublicProjectsAssocToKeyword(String keywordStr) {
		final CriteriaQuery<Project> criteriaQuery = em.getCriteriaBuilder().createQuery(Project.class);
		Root<Keyword> keyword = criteriaQuery.from(Keyword.class);
		Join<Keyword, Project> project = keyword.join("projects");
		criteriaQuery.select(project).where(em.getCriteriaBuilder().and(
				em.getCriteriaBuilder().equal(keyword.get("keyword"), keywordStr),
				em.getCriteriaBuilder().equal(project.get("visibility"), true)));
		
		try{
			List<Project> resultado= em.createQuery(criteriaQuery.select(project)).getResultList();
			return resultado;

		}catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * devolve todos os projectos que estejam associados a determinada keyword
	 * @param keywordStr
	 * @return
	 */
	public List<Project> getAllProjectsAssocToKeyword(String keywordStr) {
//		final CriteriaQuery<Project> criteriaQuery = em.getCriteriaBuilder().createQuery(Project.class);
		//Root<Keyword> keywordRoot = criteriaQuery.from(Keyword.class);
		//Join<Keyword,Project> joinProjectKeywords = keywordRoot.join("projects");
		//criteriaQuery.where(em.getCriteriaBuilder().equal(keywordRoot.get("keywords"), keywordStr));

		
		final CriteriaQuery<Project> criteriaQuery = em.getCriteriaBuilder().createQuery(Project.class);
		Root<Keyword> keyword = criteriaQuery.from(Keyword.class);
		Join<Keyword, Project> project = keyword.join("projects");
		criteriaQuery.select(project).where(em.getCriteriaBuilder().equal(keyword.get("keyword"), keywordStr));
					
		try{
			//List<Project> resultado= em.createQuery(criteriaQuery.select(joinProjectKeywords)).getResultList();
			List<Project> result = em.createQuery(criteriaQuery.select(project)).getResultList();

			return result;

		}catch (EJBException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public Keyword getKeywordEntityFromString(String keywordStr) {
		System.out.println("Entrei em associateKeywordToProjectOrNews Keyword");
		System.out.println("keyword que vou gravar é " + keywordStr);
		Keyword keyword;
		//TODO complete here
		try {
			keyword= find(keywordStr);
		
			if(keyword==null) {
				//System.out.println("esta keyword já existe");
				//keyword= find(keywordStr);
//				if(news!=null) {
//					keyword.getNews().add(news);//como temos um set de cada lado, não vão ficar combinações repetidas na BD
//				}
//				
//				if(project!=null) {
//					keyword.getProjects().add(project); //como temos um set de cada lado, não vão ficar combinações repetidas na BD
//				}
//				merge(keyword);
//				System.out.println("adicionei news ou proj à keyword já existe");
//				return keyword;
//			}else {
				System.out.println("esta keyword nao  existe");
				keyword = new Keyword();
				System.out.println("inicializei keyword");
				keyword.setKeyword(keywordStr);
//				System.out.println("atribui string keyword");
//				if(news!=null) {
//					System.out.println("as noticiass nao sao null");
//					keyword.addNews(news);//como temos um set de cada lado, não vão ficar combinações repetidas na BD
//				}
//				System.out.println("passei o if das noticiass a null");
//				if(project!=null) {
//					System.out.println("o projecto nao é null");
//					keyword.addProject(project);//como temos um set de cada lado, não vão ficar combinações repetidas na BD
//					System.out.println("o projecto nao é null");
//				}
//				System.out.println("passei o if do projecto a null");
				persist(keyword);
				System.out.println("criei keyword nova com 1 news ou proj ");
			}
				return keyword;
		//	}
		} catch (NullPointerException e) {
			//e.printStackTrace();
			System.out.println("esta keyword nao  existe");
			keyword = new Keyword();
			keyword.setKeyword(keywordStr);
//			if(news!=null) {
//				keyword.addNews(news);//como temos um set de cada lado, não vão ficar combinações repetidas na BD
//			}
//			
//			if(project!=null) {
//				keyword.addProject(project);//como temos um set de cada lado, não vão ficar combinações repetidas na BD
//			}
			persist(keyword);
			System.out.println("criei keyword nova com 1 news ou proj no catch do null pointexception ");
			return keyword;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}


}
