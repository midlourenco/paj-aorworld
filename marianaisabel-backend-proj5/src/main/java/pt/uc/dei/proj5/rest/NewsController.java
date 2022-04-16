package pt.uc.dei.proj5.rest;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.uc.dei.proj5.dto.NewsDTOResp;
import pt.uc.dei.proj5.entity.News;
import pt.uc.dei.proj5.entity.User;
import pt.uc.dei.proj5.entity.User.UserPriv;
import pt.uc.dei.proj5.other.GestaoErros;
import pt.uc.dei.proj5.bean.NewsBean;
import pt.uc.dei.proj5.bean.UserBean;
import pt.uc.dei.proj5.dto.NewsDTO;

@Path("news")
public class NewsController {
	
	@Inject
	private GestaoErros gestaoErros;
	@Inject
	private UserBean userService;
	@Inject
	private NewsBean newsService;

	/**
	 * Add news to user - so o user logado pode criar conteúdos no seu nome
	 * @param authString
	 * @param newNews
	 * @return
	 */
	//@Path("{userId}/news")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addNews(@HeaderParam("Authorization") String authString, NewsDTO newNews) {

		System.out.println("Entrei em add news no controller com  token " + authString);

		if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não está logado ou não tem token válido																				
			return Response.status(401).entity(GestaoErros.getMsg(1)).build();
		}

		try {
			User user = userService.getNonDeletedEntityByToken(authString); // utilizador onde estou a criar o newso

			if(user.getPrivileges()!=UserPriv.VIEWER){ // (duplo check-ele podia estar logado qdo foi despromovido a viewer) o user logado é membro ou admin:
				NewsDTOResp resultado = newsService.addNews(user, newNews);
				if (resultado != null) {
					return Response.ok(resultado).build();
				} else {
					return Response.status(400).entity((GestaoErros.getMsg(6))).build();
				}
			}
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();

		} catch (Exception e) {
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}

	}
	
	/**
	 * update one news
	 * @param newsId
	 * @param authString
	 * @param newNews
	 * @return
	 */
	@POST
	@Path("{newsId: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateNews(@PathParam("newsId") int newsId, @HeaderParam("Authorization") String authString, NewsDTO newNews) {
		System.out.println("Entrei em updateNews no controller com token " + authString);
		try {
			News news =newsService.getNonDeletedNewsEntityById(newsId); //não se vai fazer update de um newso apagado
			User userOwner = news.getCreatedBy(); // o criador da noticia
			User userAuthenticated  = userService.getNonDeletedEntityByToken(authString); //vai ser o lastModifBy
	
			boolean isloggedUserPrivAdmin = userService.hasLoggedUserAdminPriv(userAuthenticated);// verifica se quem está logado é um admin
			boolean isOwnerSameAsAuthenticated = userService.isUserAuthenticated(userAuthenticated, userOwner);// verifica se utilizador ao qual se está adicionar news é o user logado
			System.out.println("o utilizador logado tem privilégios de admin: " + isloggedUserPrivAdmin);
			System.out.println("o utilizador logado é o utilizador onde queremos aceder: " + isOwnerSameAsAuthenticated);
			
			if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não é publico e o utilizador não está logado ou não tem token válido																				
				return Response.status(401).entity(GestaoErros.getMsg(1)).build();
			}
			
			if (!isOwnerSameAsAuthenticated && !isloggedUserPrivAdmin){// quem está logado não é o utilizador do userid nem é admin - não pode criar newsos noutro utilizador
				System.out.println("não tem permissões para ver newsos deste utilizador");
				return Response.status(403).entity(GestaoErros.getMsg(13)).build();
			}
			
			NewsDTOResp resultado = newsService.updateNews(userAuthenticated,newsId, newNews);

			if (resultado != null) {
				return Response.ok(resultado).build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(6))).build();
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}		
	}
	

	/**
	 * Get one specific news
	 * @param newsId
	 * @param authString
	 * @return
	 */
	@GET
	@Path("{newsId: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNews( @PathParam("newsId") int newsId, @HeaderParam("Authorization") String authString) {
		boolean newsPublicVisibilty=false;
		NewsDTOResp resultado=null ;
		System.out.println("Entrei em get news no controller com token " + authString);
		try {		
			newsPublicVisibilty = newsService.isNewsWithPublicVisibility(newsId);
			resultado = newsService.getNewsDTORespById(newsId);
			System.out.println("o newso é visivel ao público em geral: " + newsPublicVisibilty);
			
			if ((!newsPublicVisibilty) && (authString == null || authString.isEmpty() || !userService.isValidToken(authString))) {// não é publico e o utilizador não está logado ou não tem token válido																				
				return Response.status(401).entity(GestaoErros.getMsg(1)).build();
			}
			
			if (resultado != null) {
				return Response.ok(resultado).build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(6))).build();
			}
		} catch (NullPointerException e) { // caso não tenha Authorization no header
			e.printStackTrace();
			System.out.println("null pointer exception pq não mandou o authorization string");
			if(newsPublicVisibilty) {
				if (resultado != null) {
					return Response.ok(resultado).build();
				}else {
					return Response.status(400).entity((GestaoErros.getMsg(6))).build();
				}
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(6))).build();
			}				
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}

	}
	

	
//	//TODO validar se o user no queryParam é um numero - annotation pattern não funcionou no postman  @Pattern(regexp = "[0-9]+", message = "The user id must be a valid number") int userId,
//	//https://dennis-xlc.gitbooks.io/restful-java-with-jax-rs-2-0-2rd-edition/content/en/part1/chapter5/query_param.html
//	//GET /news?user=xxxx
	/**
	 *  Get All news general or from one user (user- optional queryParam)
	 * @param userId
	 * @param authString
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllNews (@QueryParam("user")  int userId, @HeaderParam("Authorization") String authString) {
		System.out.println("Entrei em getAllNews por user no controller com token? : " + authString);
		System.out.println("vou querer os newsos do userid " + userId);
		User user = userService.getUserEntitybyId(userId); //mesmo que um user tenha sido apagado podemos ver os seus newsos
		System.out.println(user);
		ArrayList<NewsDTOResp> resultado=new ArrayList<>();
		
		System.out.println("Entrei em getAllNews no controller com token? : " + authString);
		try {
			if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não está logado ou não tem token válido																				
				if(userId>0) {
					resultado = newsService.getOnlyPublicNonDeletedNewssFromUser(user);	
				}else {
					resultado = newsService.getOnlyPublicNewssNonDeleted();
				}
				if (resultado != null) {
					return Response.ok(resultado).build();
				} else {
					return Response.status(400).entity((GestaoErros.getMsg(6))).build();
				}			
			}
			
			
			if(userId>0) {
				resultado = newsService.getAllNonDeletedNewssFromUser(user);	
			}else {
				resultado = newsService.getAllNewssNonDeleted();

			}
			if (resultado != null) {
				return Response.ok(resultado).build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(6))).build();
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
			if(userId>0) {
				resultado = newsService.getOnlyPublicNonDeletedNewssFromUser(user);	
			}else {
				resultado = newsService.getOnlyPublicNewssNonDeleted();
			}
	
			if (resultado != null) {
				return Response.ok(resultado).build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(6))).build();
			}
		} catch (Exception e) {
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}

	}

	
	/**
	 *  Get All news marked as deleted gerais ou de um utilizador especifico (queryparam do utilizador é opcional)
	 * @param userId
	 * @param authString
	 * @return
	 */
	@GET
	@Path("deletedList")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllNewsMarkedAsDeleted (@QueryParam("user")  int userId, @HeaderParam("Authorization") String authString) {
		ArrayList<NewsDTOResp> resultado  = new ArrayList<>();
		System.out.println("Entrei em getAllNews deleted no controller com token? : " + authString);
		User user = userService.getUserEntitybyId(userId); //mesmo que um user tenha sido apagado podemos ver os seus newsos
		
		try {
			if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não está logado ou não tem token válido																				
				if(userId>0) {
					resultado = newsService.getOnlyPublicNewssMarkedAsDeletedFromUser(user);
				}else {
					resultado = newsService.getOnlyPublicNewssNonDeleted();
				}
				if (resultado != null) {
					return Response.ok(resultado).build();
				} else {
					return Response.status(400).entity((GestaoErros.getMsg(6))).build();
				}			
			}
			if(userId>0) {
				resultado = newsService.getAllNewssMarkedAsDeletedFromUser(user);
			}else {
				resultado = newsService.getAllNewssMarkedAsDeleted();
			}
			if (resultado != null) {
				return Response.ok(resultado).build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(6))).build();
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
			if(userId>0) {
				resultado = newsService.getOnlyPublicNewssMarkedAsDeletedFromUser(user);
			}else {
				resultado = newsService.getOnlyPublicNewssNonDeleted();
			}
			if (resultado != null) {
				return Response.ok(resultado).build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(6))).build();
			}
		} catch (Exception e) {
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}

	}

	
	
	
	
	
	
	
	


	
	
	
	

}
