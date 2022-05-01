package pt.uc.dei.proj5.bean;

import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.json.JSONObject;

import pt.uc.dei.proj5.dao.UserDao;
import pt.uc.dei.proj5.dto.NewsDTOResp;
import pt.uc.dei.proj5.dto.UserDTO;
import pt.uc.dei.proj5.entity.News;
import pt.uc.dei.proj5.entity.User;
import pt.uc.dei.proj5.entity.User.UserPriv;
import pt.uc.dei.proj5.websocket.GeneralDashboardWS;
import pt.uc.dei.proj5.dao.KeywordDao;
import pt.uc.dei.proj5.dao.NewsDao;
import pt.uc.dei.proj5.dao.ProjectDao;

@Startup
@Singleton
public class DashboardBean implements Serializable {
	private static final long serialVersionUID = 1L;

	//A dashboard de gestão deve mostrar estatísticas sobre a informação do website: 
	// * número de notícias, 
	//* número de projetos, 
	//*número de membros,
	//*número de tópicos diferentes que existem, 
	//* data da última publicação, 
	
	@Inject
	private UserDao userDao;
	@Inject
	private ProjectDao projectDao;
	@Inject
	private NewsDao newsDao;
	@Inject
	private KeywordDao keywordDao;
	
	
	/**
	 * verifica se o token recebido existe na BD
	 * 
	 * @param authString
	 * @return
	 */
	public boolean isValidToken(String authString) {
		try {
			User user = userDao.findUserByToken(authString);
			if(user!=null) {
				return true;	
			}else {
				return false;
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Devolve se um user tem ou não priv admin
	 * 
	 * @param authString
	 * @return
	 */
	public boolean hasLoggedUserAdminPriv(String authString) {
		User user = userDao.findUserByToken(authString);
		if(user.getPrivileges()==UserPriv.ADMIN) {
			return true;
		}else{
			return false;
		}
	}

	
	public boolean validateTokenForDashboardAcess(String authString) {
		System.out.println("validate token " +authString );
		try {
			if (authString.equals("") || authString.isEmpty() || authString == null|| !isValidToken(authString)) {
				// está logado mas o token não é válido
				System.out.println("token não existe ou não é válido" );
				return false;
			} 
			if (!hasLoggedUserAdminPriv(authString)) {
				System.out.println("não tem priv de admin" );
				return false;
			}
			
			return true;
		} catch (NullPointerException e) { // caso não tenha Authorization no header
			System.out.println("erro no get total utilizadores controller " );
			e.printStackTrace();
			return false;
		}	
	}
	
		
	
	
	///////////////////////////////////////////////
	//Devolve o número total de users;
	///////////////////////////////////////////////
	public long getTotalUsers() {
		try { 
		System.out.println("ok total utilizadores no serviço do dashboard" );
		return userDao.countTotalEntity();
		
		} catch (NullPointerException e) { // caso não tenha Authorization no header
			System.out.println("erro null point o get total utilizadores controller " );
			e.printStackTrace();
			return 0;
		}catch (Exception e) {
		e.printStackTrace();
		System.out.println("ocorreu algum problema a procurar por user na BD -  get total users no serviço");
		return 0;
		}
	}
	
	
	///////////////////////////////////////////////
	//Devolve o número total de projectos;
	///////////////////////////////////////////////
	public long getTotalProjects() {
		try { 
		System.out.println("ok total getTotalProjects no serviço do dashboard" );
		return projectDao.countTotalEntity();
		
		} catch (NullPointerException e) { // caso não tenha Authorization no header
			System.out.println("erro null point o getTotalProjects controller " );
			e.printStackTrace();
			return 0;
		}catch (Exception e) {
		e.printStackTrace();
		System.out.println("ocorreu algum problema a procurar por user na BD -  getTotalProjects serviço");
		return 0;
		}
	}
	
	///////////////////////////////////////////////
	//Devolve o número total de news;
	///////////////////////////////////////////////
	public long getTotalNews() {
		try { 
		System.out.println("ok total getTotalNews no serviço do dashboard" );
		return newsDao.countTotalEntity();
		
		} catch (NullPointerException e) { // caso não tenha Authorization no header
			System.out.println("erro null point o getTotalNews controller " );
			e.printStackTrace();
			return 0;
		}catch (Exception e) {
		e.printStackTrace();
		System.out.println("ocorreu algum problema a procurar por user na BD -  getTotalNewsno serviço");
		return 0;
		}
	}
	
	///////////////////////////////////////////////
	//Devolve o número total de keywords; 
	///////////////////////////////////////////////
	public long getTotalKeywords() {
		try { 
		System.out.println("ok total getTotalKeywords no serviço do dashboard" );
		return keywordDao.countTotalEntity();
		
		} catch (NullPointerException e) { // caso não tenha Authorization no header
			System.out.println("erro null point o getTotalKeywordss controller " );
			e.printStackTrace();
			return 0;
		}catch (Exception e) {
		e.printStackTrace();
		System.out.println("ocorreu algum problema a procurar por user na BD -  getTotalKeywords no serviço");
		return 0;
		}
	}
	

	
	
	///////////////////////////////////////////////
	//Devolve o número total notícias Publicas; 
	///////////////////////////////////////////////
	public long countPublicNews() {
		try { 
		System.out.println("ok total getTotalKeywords no serviço do dashboard" );
		return newsDao.countPublicNews();
		
		} catch (NullPointerException e) { // caso não tenha Authorization no header
			System.out.println("erro null point o getTotalKeywordss controller " );
			e.printStackTrace();
			return 0;
		}catch (Exception e) {
		e.printStackTrace();
		System.out.println("ocorreu algum problema a procurar por user na BD -  getTotalKeywords no serviço");
		return 0;
		}
	}
	
	///////////////////////////////////////////////
	//Devolve o número total notícias Privates; 
	///////////////////////////////////////////////
	public long countPrivateNews() {
		try { 
		System.out.println("ok total getTotalKeywords no serviço do dashboard" );
		return newsDao.countPrivateNews();
		
		} catch (NullPointerException e) { // caso não tenha Authorization no header
			System.out.println("erro null point o getTotalKeywordss controller " );
			e.printStackTrace();
			return 0;
		}catch (Exception e) {
		e.printStackTrace();
		System.out.println("ocorreu algum problema a procurar por user na BD -  getTotalKeywords no serviço");
		return 0;
		}
	}
	
	///////////////////////////////////////////////
	//Devolve o número total notícias Privates apagdas; 
	///////////////////////////////////////////////
	public long countDeletedPrivateNews() {
		try { 
		System.out.println("ok total getTotalKeywords no serviço do dashboard" );
		return newsDao.countDeletedPrivateNews();
		
		} catch (NullPointerException e) { // caso não tenha Authorization no header
			System.out.println("erro null point o getTotalKeywordss controller " );
			e.printStackTrace();
			return 0;
		}catch (Exception e) {
		e.printStackTrace();
		System.out.println("ocorreu algum problema a procurar por user na BD -  getTotalKeywords no serviço");
		return 0;
		}
	}
	
	///////////////////////////////////////////////
	//Devolve o número total notícias pub apagdas; 
	///////////////////////////////////////////////
	public long countDeletedPublicNews() {
		try { 
		System.out.println("ok total getTotalKeywords no serviço do dashboard" );
		return newsDao.countDeletedPublicNews();
		
		} catch (NullPointerException e) { // caso não tenha Authorization no header
			System.out.println("erro null point o getTotalKeywordss controller " );
			e.printStackTrace();
			return 0;
		}catch (Exception e) {
		e.printStackTrace();
		System.out.println("ocorreu algum problema a procurar por user na BD -  getTotalKeywords no serviço");
		return 0;
		}
	}
	
	
	///////////////////////////////////////////////
	//Devolve a notícias mais recente; 
	///////////////////////////////////////////////
	public NewsDTOResp moreRecentNews() {
		try { 
		System.out.println("ok total getTotalKeywords no serviço do dashboard" );
		return NewsDao.convertEntityToDTOResp(newsDao.moreRecentNews());
		
		} catch (NullPointerException e) { // caso não tenha Authorization no header
			System.out.println("erro null point o getTotalKeywordss controller " );
			e.printStackTrace();
			return null;
		}catch (Exception e) {
		e.printStackTrace();
		System.out.println("ocorreu algum problema a procurar por user na BD -  getTotalKeywords no serviço");
		return null;
		}
	}
	
	
	
	
	//project in average x keywords
	//news in average x keywords
	
	
	//Total noticias/projectos
	//noticias/projectos visiveis
	//noticias/projectos privadas
	//noticias/projectos apagas
	
	//Total utilizadores registados:
	//membros
	//admin
	//por aprovar
	//bloqueados
	
	//Media de Projectos criados por utilizador
	
	//Medias de projectos a que um utilizador está associado
	
	// Utilizador com mais projectos criados
	
	//Data Ultima publicaçao
	
		
	//////////////////////////////////////////////
	//Metodo que devolve todas as estatisticas gerais
	/////////////////////////////////////////////// 
	//ATENCAO: o nome destas chaves está a ser usado no frontend
	public String updateGeneralDashboard() {
	
	System.out.println("o update do dashboard geral foi chamado");
	
	ArrayList<JSONObject>newsPieChart= new ArrayList<>();
	JSONObject obj1 = new JSONObject();
	obj1.put("key", "PublicNews");
	obj1.put("data", countPublicNews());
	newsPieChart.add(obj1);
	
	JSONObject obj2 = new JSONObject();
	obj2.put("key", "PublicDeletedNews");
	obj2.put("data", countDeletedPublicNews());
	newsPieChart.add(obj2);
	
	JSONObject obj3 = new JSONObject();
	obj3.put("key", "PrivateNews");
	obj3.put("data", countPrivateNews());
	newsPieChart.add(obj3);
	
	JSONObject obj4= new JSONObject();
	obj4.put("key", "PrivateDeteledNews");
	obj4.put("data", countDeletedPrivateNews());
	newsPieChart.add(obj4);
	
	
	//grafico com 2 linhas projectos e noticias por dia
	JSONObject recentNews = new JSONObject(moreRecentNews());
	JSONObject stats = new JSONObject();
	
	stats.put("TotalUsers", getTotalUsers());
	stats.put("TotalProjects", getTotalProjects());
	stats.put("TotalNews", getTotalNews());
	stats.put("TotalKeywords", getTotalKeywords());
	stats.put("newsPieChart", newsPieChart);
	stats.put("moreRecentNews", recentNews);
	
	
	System.out.println(stats.toString());
	GeneralDashboardWS.send( stats.toString());
	
	
	return stats.toString();
	}



	
	
	
	

}
