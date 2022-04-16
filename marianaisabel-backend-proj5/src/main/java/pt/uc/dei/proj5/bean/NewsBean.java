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
import pt.uc.dei.proj5.dao.NewsSharingDao;
import pt.uc.dei.proj5.dao.UserDao;
import pt.uc.dei.proj5.dto.NewsDTO;
import pt.uc.dei.proj5.dto.NewsDTOResp;
import pt.uc.dei.proj5.entity.Keyword;
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
	private KeywordDao keywordDao;
	@Inject
	private NewsSharingDao newsSharingDao;
	
	
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
			} else if(newsSharingDao.isUserAlreadyAssociatedToNews(news, user)) {
				System.out.println("o newso foi partilhado e aceite por este utilizador ");
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
			newsDao.merge(news);
	
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
		newsDao.merge(news);
		
		NewsDTOResp newsDTOResp=NewsDao.convertEntityToDTOResp(news);
		
		return newsDTOResp;
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
