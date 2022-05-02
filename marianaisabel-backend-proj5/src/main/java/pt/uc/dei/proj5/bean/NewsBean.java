package pt.uc.dei.proj5.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import pt.uc.dei.proj5.dao.KeywordDao;
import pt.uc.dei.proj5.dao.NewsDao;
import pt.uc.dei.proj5.dao.NotificationDao;
import pt.uc.dei.proj5.dao.ProjectDao;
import pt.uc.dei.proj5.dao.ProjectSharingDao;
import pt.uc.dei.proj5.dao.UserDao;
import pt.uc.dei.proj5.dto.NewsDTO;
import pt.uc.dei.proj5.dto.NewsDTOResp;
import pt.uc.dei.proj5.entity.Keyword;
import pt.uc.dei.proj5.entity.News;
import pt.uc.dei.proj5.entity.Project;
import pt.uc.dei.proj5.entity.News;
import pt.uc.dei.proj5.entity.User;

@RequestScoped
public class NewsBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private UserDao userDao;
	@Inject
	private NewsDao newsDao;
	@Inject
	private ProjectDao projectDao;
	@Inject
	private KeywordDao keywordDao;
//	@Inject
//	private NewsSharingDao newsSharingDao;
	@Inject
	private NotificationDao notificationDao;
	@Inject
	private ProjectSharingDao projectSharingDao;
	@Inject
	private DashboardBean dashboardService;
	
	///////////////////////////////
	//GET DE NOTICIAS
	/////////////////////////////
		
	public News getNonDeletedNewsEntityById(int newsId) {
		try {
			return newsDao.findEntityIfNonDelete(newsId);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public NewsDTOResp getNonDeletedNewsDTORespById(int newsId) {
		return NewsDao.convertEntityToDTOResp(getNonDeletedNewsEntityById(newsId));
	}
	
	public News getNewsEntityById(int newsId) {
		try {
			return newsDao.find(newsId);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public NewsDTOResp getNewsDTORespById(int newsId) {
		return NewsDao.convertEntityToDTOResp(getNewsEntityById(newsId));
	}
	
	
	
	///////////////////////////////
	//AUTORIZAçÃO SOBRE NOTICIAS
	/////////////////////////////
	
	
	//FOR NEWS UPDATE Só PODE SER feito pelo user criador
	/**
	 * devolve se o user é o criador de um newso (nao marcado para apagar)
	 * @param newsId
	 * @param userId
	 * @return
	 */
	public boolean isNewsCreatedByUser(int newsId, int  userId) {
		System.out.println("entrei em isNewsCreatedByUser e trouxe o id e user id: " + newsId + userId);

		try {
			User user = userDao.findEntityIfNonDelete(userId);
			News news = newsDao.findEntityIfNonDelete(newsId);

			//USER ORIGINAL DETENTOR DO PROJECTO:
			if(news.getCreatedBy().getId()==user.getId()) {
				System.out.println("o utilizador é o titular deste newso ");
				return true;
			} else {
				System.out.println("o newso não pertence ao utilizador ");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ocorreu algum problema a procurar por newso");
			return false;
		}
	}
	
	
	
	//FOR GETs do PROJECTO PODE SER feito pelo user criador e pelos que estão associados
	/**
	 * devolve se o user é o criador de um newso (nao marcado para apagar) ou está associado a esse newso por newsSharing
	 * @param newsId
	 * @param userId
	 * @return
	 */
	public boolean isNewsAssocToUser(int newsId, int  userId) {
		System.out.println("entrei em isNewsCreatedByUser e trouxe o id e user id: " + newsId + userId);

		try {
			User user = userDao.findEntityIfNonDelete(userId);
			News news = newsDao.findEntityIfNonDelete(newsId);

			//USER ORIGINAL DETENTOR DO PROJECTO:
			if(news.getCreatedBy().getId()==user.getId()) {
				System.out.println("o utilizador é o titular deste newso ");
				return true;
				
				//TODO
//			} else if(newsSharingDao.isUserAlreadyAssociatedToNews(news, user)) {
//				System.out.println("o newso foi partilhado e aceite por este utilizador ");
//				return true;
			} else {
				System.out.println("o newso não pertence ao utilizador ");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ocorreu algum problema a procurar por newso");
			return false;
		}
	}
	
	
	public boolean isNewsWithPublicVisibility(int newsId) {
		try {
			return newsDao.findEntityIfNonDelete(newsId).isVisibility();
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	

	}
	
	
	///////////////////////////////
	//FUNCIONALIDADES NOTICIAS
	/////////////////////////////
	
	
	public NewsDTOResp addNews(User createdBy, NewsDTO newsDTO ) {
		System.out.println("Entrei em addNews NewsBean"); 
		
		//if(!newsDao.alreadyExistNewsTitleByThisCreatedUser(newsDTO.getTitle(), createdBy) && !newsDTO.getTitle().equals("")) { ---> RETIRADA VALIDAÇAO /RESTRIÇÂO De um user nao poder inserir mais do 1 proj com o mesmo titulo
			
			News news = NewsDao.convertDTOToEntity(newsDTO,null);
			news.setCreatedBy(createdBy); //adiciono o titular do newso À entidade
			newsDao.persist(news);
			
			Set<Keyword> keywords = new HashSet<>();
			ArrayList<String> keywordsSTR = newsDTO.getKeywords();
			for (String keyword : keywordsSTR) {
				System.out.println("entrei no for das keywords");
				keywords.add(keywordDao.getKeywordEntityFromString(keyword));
				System.out.println("adicionei a keyword ao set");
				//Keyword keywordEntity =	keywordDao.getKeywordEntityFromString(keyword);
				//News newsEnytity= getNewsEntitybyId(news.getId());
				//System.out.println("criei entity keyword" + keywordEntity + "vou adicionar a newso "+ news);
				//newsDao.associateKeywordToNews(keywordEntity, news);
				//System.out.println("associei ao proj. fim do ciclo for");
			}
			news.setKeywords(keywords);
			
			Set<Project> projects = new HashSet<>();
			ArrayList<Integer> projectsIdList = newsDTO.getProjects();
			for (Integer projectId : projectsIdList) {
				System.out.println("entrei no for projectsIdlist " + projectId );
				//try {
					Project p = projectDao.findEntityIfNonDelete(projectId);//a ideia será adicionar um projecto ja existente
					if(p!=null) {
						projects.add(p);
						notificationDao.assocProjToNewstNotif(p.getCreatedBy(),news,p);
						System.out.println("Enviei notif ao criador");
						//try {
						List<User> usersAssocProject = projectSharingDao.getUserAssocToProject(p);
						System.out.println("fiz query para ver associados associados");
						if(usersAssocProject!=null) {
							System.out.println("entrei no if dos usersAssocProject");
							for (User u : usersAssocProject) {
								notificationDao.assocProjToNewstNotif(u,news,p);
							}
						}
//						}catch(Exception e) {
//							e.printStackTrace();
//							System.out.println("exception para o caso de nao ter associados");
//						}
						System.out.println("adicionei a project ao set");
					}
//				}catch (Exception e) {
//					e.printStackTrace();
//					System.out.println("o projecto " + projectId + " nao existe ou está marcada para eliminar");
//				}
			}
			if(projects.size()>0) {
				news.setProjects(projects);
			}
			Set<User> users = new HashSet<>();
			ArrayList<Integer> usersId = newsDTO.getUsers();
			for (Integer userId : usersId) {
				System.out.println("entrei no for projectsIdlist " + userId );
				User u = userDao.findEntityIfNonDelete(userId);//a ideia será adicionar um projecto ja existente
				if(u!=null) {
					users.add(u);
					notificationDao.assocUserToNewstNotif(u,news);
					System.out.println("adicionei a project ao set");
				}
			}
		//	if(users.size()>0) {
				news.setUsers(users);
		//	}
		
			newsDao.merge(news);
			dashboardService.updateGeneralDashboard();
			NewsDTOResp newsDTOResp=NewsDao.convertEntityToDTOResp(news);
//		}else {
//			System.out.println("Já existe um newso com este título criado por este utilizador");
//		}
	
		return newsDTOResp;
	}
	
	
	public NewsDTOResp updateNews(User lastModifBy, int newsId, NewsDTO newsDTO ) {
		
		News news = getNewsEntityById(newsId);
		news = NewsDao.convertDTOToEntity(newsDTO,news);
		news.setId(newsId);
		news.setLastModifByAndDate(lastModifBy);
		Set<Keyword> keywords = new HashSet<>();
		ArrayList<String> keywordsSTR = newsDTO.getKeywords();
		for (String keyword : keywordsSTR) {
			System.out.println("entrei no for das keywords");
			keywords.add(keywordDao.getKeywordEntityFromString(keyword));
			System.out.println("adicionei a keyword ao set");
		}
		news.setKeywords(keywords);
		Set<Project> projects = new HashSet<>();
		ArrayList<Integer> projectsIdList = newsDTO.getProjects();
		for (Integer projectId : projectsIdList) {
			System.out.println("entrei no for projectsIdlist " + projectId );
			Project p = projectDao.findEntityIfNonDelete(projectId);//a ideia será adicionar um projecto ja existente
			if(p!=null) {
				projects.add(p);
				System.out.println("adicionei a project ao set");
			}
		}
//		if(projects.size()>0) {
			news.setProjects(projects);
//		}
		Set<User> users = new HashSet<>();
		ArrayList<Integer> usersId = newsDTO.getUsers();
		for (Integer userId : usersId) {
			System.out.println("entrei no for projectsIdlist " + userId );
			User u = userDao.findEntityIfNonDelete(userId);//a ideia será adicionar um projecto ja existente
			if(u!=null) {
				users.add(u);
				System.out.println("adicionei a project ao set");
			}
		}
//		if(users.size()>0) {
			news.setUsers(users);
	//	}
	

		newsDao.merge(news);
		dashboardService.updateGeneralDashboard();
		NewsDTOResp newsDTOResp=NewsDao.convertEntityToDTOResp(news);
		
		return newsDTOResp;
	}
	
	

	public boolean deleteNews(String authString, int newsID) {
		try {
			User user = userDao.findUserByToken(authString);
			News news = newsDao.find(newsID);
			if (news.isDeleted()) {
				System.out.println("nesta fase, não faz nada. nao permitimos delete definitivo da base de dados");
//				newsDao.deleteById(userID); // este delete vai remover o conteudo associado ao user - REMOVER OS CASCADES?!?
//				dashboardService.updateGeneralDashboard();
				return false;		
			} else {
				newsDao.markAsDeleted(newsID,user); // fica marcado como deleted na BD
				dashboardService.updateGeneralDashboard();
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
	public boolean undeleteNews(String authString,int newsID) {
		try {
			System.out.println("entrei em undeleteNews");
			User user = userDao.findUserByToken(authString);
			News news = newsDao.find(newsID);
			System.out.println("a noticia e user sao  " +news + user);
			if (news.isDeleted()) { // se estiver marcado como deleted coloca o delete a false	
				newsDao.markAsNonDeleted(newsID,user);
				dashboardService.updateGeneralDashboard();
//				news.setLastModifByAndDate(user);
//				newsDao.merge(news);
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
	
	
	public boolean assocUserToNews(String authString, int newsId ) {
		try {
			News  news = newsDao.findEntityIfNonDelete(newsId);
			User user = userDao.findUserByToken(authString);
			news.getUsers().add(user);
			news.setLastModifByAndDate(user);
			newsDao.merge(news);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	public boolean desassocUserToNews(String authString, int newsId ) {
		try {
			News  news = newsDao.findEntityIfNonDelete(newsId);
			User user = userDao.findUserByToken(authString);
			news.getUsers().remove(user);
			news.setLastModifByAndDate(user);
			newsDao.merge(news);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	///////////////////////////////
	//LISTAS DE NOTICIAS
	/////////////////////////////
	//************LISTAS Por user*******
	
	
	public ArrayList<NewsDTOResp> getAllNonDeletedNewssFromUser(User createdBy){
		ArrayList<NewsDTOResp> newssDTOResp =new ArrayList<NewsDTOResp> ();
		
		List<News> newss =  newsDao.getAllNonDeletedNewssFromUser(createdBy);
		
		for (News news : newss) {
			newssDTOResp.add(NewsDao.convertEntityToDTOResp(news));
		}
		
		return newssDTOResp;
	}
	
	public ArrayList<NewsDTOResp> getOnlyPublicNonDeletedNewssFromUser(User createdBy){
		ArrayList<NewsDTOResp> newssDTOResp =new ArrayList<NewsDTOResp> ();
		
		List<News> newss =  newsDao.getOnlyPublicNonDeletedNewssFromUser(createdBy);
		
		for (News news : newss) {
			newssDTOResp.add(NewsDao.convertEntityToDTOResp(news));
		}
		
		return newssDTOResp;
	}
	
	
	
	
	
	public ArrayList<NewsDTOResp> getAllNewssMarkedAsDeletedFromUser(User createdBy){
		ArrayList<NewsDTOResp> newssDTOResp =new ArrayList<NewsDTOResp> ();
		
		List<News> newss =  newsDao.getAllNewssMarkedAsDeletedFromUser(createdBy);
		
		for (News news : newss) {
			newssDTOResp.add(NewsDao.convertEntityToDTOResp(news));
		}
		
		return newssDTOResp;
	}

	
	public ArrayList<NewsDTOResp> getOnlyPublicNewssMarkedAsDeletedFromUser(User createdBy){
		ArrayList<NewsDTOResp> newssDTOResp =new ArrayList<NewsDTOResp> ();
		
		List<News> newss =  newsDao.getOnlyPublicNewssMarkedAsDeletedFromUser(createdBy);
		
		for (News news : newss) {
			newssDTOResp.add(NewsDao.convertEntityToDTOResp(news));
		}
		
		return newssDTOResp;
	}
	
	/////*******************************************************************************************
	
	public ArrayList<NewsDTOResp> getAllNonDeletedAssocNewssFromUser(User createdBy){
		ArrayList<NewsDTOResp> newssDTOResp =new ArrayList<NewsDTOResp> ();
		
		List<News> newss =  newsDao.getAllNonDeletedAssocNewssFromUser(createdBy);
		
		for (News news : newss) {
			newssDTOResp.add(NewsDao.convertEntityToDTOResp(news));
		}
		
		return newssDTOResp;
	}
	
	public ArrayList<NewsDTOResp> getOnlyPublicNonDeletedAssocNewssFromUser(User createdBy){
		ArrayList<NewsDTOResp> newssDTOResp =new ArrayList<NewsDTOResp> ();
		
		List<News> newss =  newsDao.getOnlyPublicNonDeletedAssocNewssFromUser(createdBy);
		
		for (News news : newss) {
			newssDTOResp.add(NewsDao.convertEntityToDTOResp(news));
		}
		
		return newssDTOResp;
	}
	
	
	
	
	
	public ArrayList<NewsDTOResp> getAllAssocNewssMarkedAsDeletedFromUser(User createdBy){
		ArrayList<NewsDTOResp> newssDTOResp =new ArrayList<NewsDTOResp> ();
		
		List<News> newss =  newsDao.getAllAssocNewssMarkedAsDeletedFromUser(createdBy);
		
		for (News news : newss) {
			newssDTOResp.add(NewsDao.convertEntityToDTOResp(news));
		}
		
		return newssDTOResp;
	}

	
	public ArrayList<NewsDTOResp> getOnlyPublicAssocNewssMarkedAsDeletedFromUser(User createdBy){
		ArrayList<NewsDTOResp> newssDTOResp =new ArrayList<NewsDTOResp> ();
		
		List<News> newss =  newsDao.getOnlyPublicAssocNewssMarkedAsDeletedFromUser(createdBy);
		
		for (News news : newss) {
			newssDTOResp.add(NewsDao.convertEntityToDTOResp(news));
		}
		
		return newssDTOResp;
	}
	
	
	
	
	
	//************LISTAS GERAIS*******
	
	
	public ArrayList<NewsDTOResp> getOnlyPublicNewssNonDeleted(){
		ArrayList<NewsDTOResp> newssDTOResp =new ArrayList<NewsDTOResp> ();
		
		List<News> newss =  newsDao.getOnlyPublicNewssNonDeleted();
		
		for (News news : newss) {
			newssDTOResp.add(NewsDao.convertEntityToDTOResp(news));
		}
		
		return newssDTOResp;
	}

	public ArrayList<NewsDTOResp> getOnlyPublicNewssMarkedAsDeleted(){
		ArrayList<NewsDTOResp> newssDTOResp =new ArrayList<NewsDTOResp> ();
		
		List<News> newss =  newsDao.getOnlyPublicNewssMarkedAsDeleted();
		
		for (News news : newss) {
			newssDTOResp.add(NewsDao.convertEntityToDTOResp(news));
		}
		
		return newssDTOResp;
	}

	public ArrayList<NewsDTOResp> getAllNewssNonDeleted(){
		ArrayList<NewsDTOResp> newssDTOResp =new ArrayList<NewsDTOResp> ();
		
		List<News> newss =  newsDao.getAllNewssNonDeleted();
		
		for (News news : newss) {
			newssDTOResp.add(NewsDao.convertEntityToDTOResp(news));
		}
		
		return newssDTOResp;
	}

	public ArrayList<NewsDTOResp> getAllNewssMarkedAsDeleted(){
		ArrayList<NewsDTOResp> newssDTOResp =new ArrayList<NewsDTOResp> ();
		
		List<News> newss =  newsDao.getAllNewssMarkedAsDeleted();
		
		for (News news : newss) {
			newssDTOResp.add(NewsDao.convertEntityToDTOResp(news));
		}
		
		return newssDTOResp;
	}
	
}
