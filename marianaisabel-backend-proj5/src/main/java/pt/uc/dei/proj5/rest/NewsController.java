package pt.uc.dei.proj5.rest;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
			User user = userService.getNonDeletedEntityByToken(authString); // utilizador onde estou a criar o news

			if(user.getPrivileges()!=UserPriv.VIEWER){ // (duplo check-ele podia estar logado qdo foi despromovido a viewer) o user logado é membro ou admin:
				NewsDTOResp resultado = newsService.addNews(user, newNews);
				if (resultado != null) {
					return Response.ok(resultado).build();
				} else {
					return Response.status(400).entity((GestaoErros.getMsg(17))).build();
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
			News news =newsService.getNonDeletedNewsEntityById(newsId); //não se vai fazer update de um news apagado
			User userOwner = news.getCreatedBy(); // o criador da noticia
			User userAuthenticated  = userService.getNonDeletedEntityByToken(authString); //vai ser o lastModifBy
	
			boolean isloggedUserPrivAdmin = userService.hasLoggedUserAdminPriv(userAuthenticated);// verifica se quem está logado é um admin
			boolean isOwnerSameAsAuthenticated = userService.isUserAuthenticated(userAuthenticated, userOwner);// verifica se utilizador ao qual se está adicionar news é o user logado
			System.out.println("o utilizador logado tem privilégios de admin: " + isloggedUserPrivAdmin);
			System.out.println("o utilizador logado é o utilizador onde queremos aceder: " + isOwnerSameAsAuthenticated);
			
			if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não é publico e o utilizador não está logado ou não tem token válido																				
				return Response.status(401).entity(GestaoErros.getMsg(1)).build();
			}
			
			if (!isOwnerSameAsAuthenticated && !isloggedUserPrivAdmin){// quem está logado não é o utilizador do userid nem é admin - não pode criar news noutro utilizador
				System.out.println("não tem permissões para ver news deste utilizador");
				return Response.status(403).entity(GestaoErros.getMsg(13)).build();
			}
			
			NewsDTOResp resultado = newsService.updateNews(userAuthenticated,newsId, newNews);

			if (resultado != null) {
				return Response.ok(resultado).build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(17))).build();
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
			System.out.println("o news é visivel ao público em geral: " + newsPublicVisibilty);
			
			if ((!newsPublicVisibilty) && (authString == null || authString.isEmpty() || !userService.isValidToken(authString))) {// não é publico e o utilizador não está logado ou não tem token válido																				
				return Response.status(401).entity(GestaoErros.getMsg(1)).build();
			}
			
			if (resultado != null) {
				return Response.ok(resultado).build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(17))).build();
			}
		} catch (NullPointerException e) { // caso não tenha Authorization no header
			e.printStackTrace();
			System.out.println("null pointer exception pq não mandou o authorization string");
			if(newsPublicVisibilty) {
				if (resultado != null) {
					return Response.ok(resultado).build();
				}else {
					return Response.status(400).entity((GestaoErros.getMsg(17))).build();
				}
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(17))).build();
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
		System.out.println("vou querer os news do userid " + userId);
		User user = userService.getUserEntitybyId(userId); //mesmo que um user tenha sido apagado podemos ver os seus news
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
					return Response.status(400).entity((GestaoErros.getMsg(17))).build();
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
				return Response.status(400).entity((GestaoErros.getMsg(17))).build();
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
				return Response.status(400).entity((GestaoErros.getMsg(17))).build();
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
		User user = userService.getUserEntitybyId(userId); //mesmo que um user tenha sido apagado podemos ver os seus news
		
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
					return Response.status(400).entity((GestaoErros.getMsg(17))).build();
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
				return Response.status(400).entity((GestaoErros.getMsg(17))).build();
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
				return Response.status(400).entity((GestaoErros.getMsg(17))).build();
			}
		} catch (Exception e) {
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}

	}

	
	
	
	
	
	
	
	@GET
	@Path("associatedToUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllNewsAssocToUser (@QueryParam("user")  int userId, @HeaderParam("Authorization") String authString) {
		System.out.println("Entrei em getAllNews por user no controller com token? : " + authString);
		System.out.println("vou querer os news do userid " + userId);
		User user = userService.getUserEntitybyId(userId); //mesmo que um user tenha sido apagado podemos ver os seus news
		System.out.println(user);
		ArrayList<NewsDTOResp> resultado=new ArrayList<>();
		
		System.out.println("Entrei em getAllNews no controller com token? : " + authString);
		try {
			if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não está logado ou não tem token válido																				
				if(userId>0) {
					resultado = newsService.getOnlyPublicNonDeletedAssocNewssFromUser(user);	
				}else {
					resultado = null;
				}
				if (resultado != null) {
					return Response.ok(resultado).build();
				} else {
					return Response.status(400).entity((GestaoErros.getMsg(17))).build();
				}			
			}
			
			
			if(userId>0) {
				resultado = newsService.getAllNonDeletedAssocNewssFromUser(user);	
			}else {
				resultado = null;

			}
			if (resultado != null) {
				return Response.ok(resultado).build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(17))).build();
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
			if(userId>0) {
				resultado = newsService.getOnlyPublicNonDeletedAssocNewssFromUser(user);	
			}else {
				resultado = null;
			}
	
			if (resultado != null) {
				return Response.ok(resultado).build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(17))).build();
			}
		} catch (Exception e) {
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}

	}

	
	/**
	 * Apagar (marcar para apagado, soft delete) de um noticia 
	 * @param newsId
	 * @param authString
	 * @return
	 */
	@DELETE
	@Path("{newsId: [0-9]+}")
	public Response deleteNews(@PathParam("newsId") int newsId,	@HeaderParam("Authorization") String authString) {
		System.out.println("entrei em deleteNews trouxe o id: " + newsId +  " token "+ authString);


		if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// está logado mas token não é valido																					// válido
			return Response.status(401).entity(GestaoErros.getMsg(1)).build();
		}
		News news =newsService.getNonDeletedNewsEntityById(newsId);
		boolean isLoggedUserPrivAdmin = userService.hasLoggedUserAdminPriv(authString);
		boolean isAuthenticatedUserCreaterOfNews = userService.isUserAuthenticated(authString, news.getCreatedBy().getId());// utilizador ao qual se está modificar/apagar rpojecto vs detentor do token
		System.out.println("o noticia é do user autenticado: " + isAuthenticatedUserCreaterOfNews);
		System.out.println("o user autenticado tem priv admin: " + isLoggedUserPrivAdmin);
	
		if ((!isAuthenticatedUserCreaterOfNews && !isLoggedUserPrivAdmin)) { //so o criador ou um admin é que pode apagar um noticia
			System.out.println(GestaoErros.getMsg(13));
			return Response.status(403).entity(GestaoErros.getMsg(13)).build();
		}

		try {
			if (newsService.deleteNews(authString,newsId)) {
				return Response.ok().build();
			} else {
				return Response.status(400).entity("A news continua marcada para eliminar").build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}

	}

	/**
	 *  Recuperar  um noticia marcado para apagar
	 * @param newsId
	 * @param authString
	 * @return
	 */
	@POST
	@Path("{newsId: [0-9]+}/undelete")
//	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response undeletedPrject(@PathParam("newsId") int newsId, @HeaderParam("Authorization") String authString) {
		System.out.println("entrei em undeletedPrject ");
		if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// está logado mas token não é valido																					// válido
			return Response.status(401).entity(GestaoErros.getMsg(1)).build();
		}
		News news =newsService.getNonDeletedNewsEntityById(newsId);
		System.out.println("vou vero  user autenticado autor da noticia: ");
		User newsOwener =news.getCreatedBy();
		System.out.println("o user autenticado autor da noticia: " + newsOwener);
		boolean isLoggedUserPrivAdmin = userService.hasLoggedUserAdminPriv(authString);
		System.out.println("o user autenticado tem priv admin: " + isLoggedUserPrivAdmin);
		boolean isAuthenticatedUserCreaterOfNews = userService.isUserAuthenticated(authString,newsOwener.getId());// utilizador ao qual se está modificar/apagar rpojecto vs detentor do token
		System.out.println("o noticia é do user autenticado: " + isAuthenticatedUserCreaterOfNews);

		
		if ((!isAuthenticatedUserCreaterOfNews && !isLoggedUserPrivAdmin)) { //so o criador ou um admin é que pode apagar um noticia
			System.out.println(GestaoErros.getMsg(13));
			return Response.status(403).entity(GestaoErros.getMsg(13)).build();
		}

		try {
			System.out.println("vou desmarcar para apagar");
			if (newsService.undeleteNews(authString,newsId)) {
				return Response.ok().build();
			} else {
				return Response.status(400).entity("A news continua marcada para eliminar").build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}

	}
	
	
	

	/**
	 *  Associar  um utilizador a uma noticia
	 * @param newsId
	 * @param authString
	 * @return
	 */
	@POST
	@Path("{newsId: [0-9]+}/associateToMySelf")
//	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response associateLoggedUserToNews(@PathParam("newsId") int newsId, @HeaderParam("Authorization") String authString) {
		System.out.println("entrei em associateLoggedUserToNews ");
		if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// está logado mas token não é valido																					// válido
			return Response.status(401).entity(GestaoErros.getMsg(1)).build();
		}
		try {
			if (newsService.assocUserToNews(authString,newsId)) {
				return Response.ok().build();
			} else {
				return Response.status(400).entity("nao fui  associado").build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}

	}	
	


	
	
	
	

}
