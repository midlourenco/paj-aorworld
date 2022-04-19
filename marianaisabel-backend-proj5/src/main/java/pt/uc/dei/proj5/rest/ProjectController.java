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

import org.json.JSONObject;

import pt.uc.dei.proj5.dto.ProjectDTOResp;
import pt.uc.dei.proj5.dto.ProjectSharingDTO;
import pt.uc.dei.proj5.dto.UserDTOResp;
import pt.uc.dei.proj5.entity.Project;
import pt.uc.dei.proj5.entity.User;
import pt.uc.dei.proj5.entity.User.UserPriv;
import pt.uc.dei.proj5.other.GestaoErros;
import pt.uc.dei.proj5.bean.ProjectBean;
import pt.uc.dei.proj5.bean.ProjectSharingBean;
import pt.uc.dei.proj5.bean.UserBean;
import pt.uc.dei.proj5.dto.ProjectDTO;

@Path("projects")
public class ProjectController {
	
	@Inject
	private GestaoErros gestaoErros;
	@Inject
	private UserBean userService;
	@Inject
	private ProjectBean projectService;
	@Inject
	private ProjectSharingBean projectSharingService;

	/**
	 * Add project to user - so o user logado pode criar conteúdos no seu nome
	 * @param authString
	 * @param newProject
	 * @return
	 */
	//@Path("{userId}/projects")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addProject(@HeaderParam("Authorization") String authString, ProjectDTO newProject) {

		System.out.println("Entrei em add project no controller com  token " + authString);

		if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não está logado ou não tem token válido																				
			return Response.status(401).entity(GestaoErros.getMsg(1)).build();
		}

		try {
			User user = userService.getNonDeletedEntityByToken(authString); // utilizador onde estou a criar o projecto
		//	boolean userAuthenticated = userService.isUserAuthenticated(authString, userId);// verifica se utilizador ao qual se está adicionar projecto é o user logado
		//	System.out.println("o utilizador logado é o utilizador onde queremos aceder: " + userAuthenticated);
//			if (!userAuthenticated) {// quem está logado não é o utilizador do userid - não pode criar projectos noutro utilizador -> //&& !userPrivAdmin - o admin poderia criar noutro utilizador -RETIRADO
//				System.out.println("não tem permissões para criar projectos neste utilizador");
//				return Response.status(403).entity(GestaoErros.getMsg(13)).build();
//			}

			if(user.getPrivileges()!=UserPriv.VIEWER){ // (duplo check-ele podia estar logado qdo foi despromovido a viewer) o user logado é membro ou admin:
				ProjectDTOResp resultado = projectService.addProject(user, newProject);
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
	 * update one project
	 * @param projectId
	 * @param authString
	 * @param newProject
	 * @return
	 */
	@POST
	@Path("{projectId: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProject(@PathParam("projectId") int projectId, @HeaderParam("Authorization") String authString, ProjectDTO newProject) {
		System.out.println("Entrei em updateProject no controller com token " + authString);
		try {
			Project project =projectService.getNonDeletedProjectEntityById(projectId); //não se vai fazer update de um projecto apagado
			User userOwner = project.getCreatedBy(); // o criador do projecto
			User userAuthenticated  = userService.getNonDeletedEntityByToken(authString); //vai ser o lastModifBy
	
			boolean isloggedUserPrivAdmin = userService.hasLoggedUserAdminPriv(userAuthenticated);// verifica se quem está logado é um admin
			boolean isOwnerSameAsAuthenticated = userService.isUserAuthenticated(userAuthenticated, userOwner);// verifica se utilizador ao qual se está adicionar projecto é o user logado
			//boolean isloggedAuthorizedToGetProject = projectService.isProjectAssocToUser(projectId, userOwner.getId());// verifica se utilizador do qual se está a fazer get projecto é o user criador ou um dos associados ao projecto - neste caso este tabém não pode editar
			System.out.println("o utilizador logado tem privilégios de admin: " + isloggedUserPrivAdmin);
			System.out.println("o utilizador logado é o utilizador onde queremos aceder: " + isOwnerSameAsAuthenticated);
			//System.out.println("o utilizador logado tem autorização para fazer get do Projecto: " + isloggedAuthorizedToGetProject);
		
			if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não é publico e o utilizador não está logado ou não tem token válido																				
				return Response.status(401).entity(GestaoErros.getMsg(1)).build();
			}
			
			if (!isOwnerSameAsAuthenticated && !isloggedUserPrivAdmin){// quem está logado não é o utilizador do userid nem é admin - não pode criar projectos noutro utilizador
				System.out.println("não tem permissões para ver projectos deste utilizador");
				return Response.status(403).entity(GestaoErros.getMsg(13)).build();
			}
			
			ProjectDTOResp resultado = projectService.updateProject(userAuthenticated,projectId, newProject);

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
	 * Get one specific project
	 * @param projectId
	 * @param authString
	 * @return
	 */
	@GET
	@Path("{projectId: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProject( @PathParam("projectId") int projectId, @HeaderParam("Authorization") String authString) {
		boolean projectPublicVisibilty=false;
		ProjectDTOResp resultado=null ;
		System.out.println("Entrei em get project no controller com token " + authString);
		try {		
			projectPublicVisibilty = projectService.isProjectWithPublicVisibility(projectId);
			resultado = projectService.getProjectDTORespById(projectId);
			System.out.println("o projecto é visivel ao público em geral: " + projectPublicVisibilty);
			
			if ((!projectPublicVisibilty) && (authString == null || authString.isEmpty() || !userService.isValidToken(authString))) {// não é publico e o utilizador não está logado ou não tem token válido																				
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
			if(projectPublicVisibilty) {
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
//	//GET /projects?user=xxxx
	/**
	 *  Get All projects general or from one user (user- optional queryParam)
	 * @param userId
	 * @param authString
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProject (@QueryParam("user")  int userId, @HeaderParam("Authorization") String authString) {
		System.out.println("Entrei em getAllProject por user no controller com token? : " + authString);
		System.out.println("vou querer os projectos do userid " + userId);
		User user = userService.getUserEntitybyId(userId); //mesmo que um user tenha sido apagado podemos ver os seus projectos
		System.out.println(user);
		ArrayList<ProjectDTOResp> resultado=new ArrayList<>();
		
		System.out.println("Entrei em getAllProject no controller com token? : " + authString);
		try {
			if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não está logado ou não tem token válido																				
				if(userId>0) {
					resultado = projectService.getOnlyPublicNonDeletedProjectsFromUser(user);	
				}else {
					resultado = projectService.getOnlyPublicProjectsNonDeleted();
				}
				//ArrayList<ProjectDTOResp> resultado = projectService.getOnlyPublicProjectsNonDeleted();
				if (resultado != null) {
					return Response.ok(resultado).build();
				} else {
					return Response.status(400).entity((GestaoErros.getMsg(17))).build();
				}			
			}
			
			
			if(userId>0) {
				resultado = projectService.getAllNonDeletedProjectsFromUser(user);	
			}else {
				resultado = projectService.getAllProjectsNonDeleted();

			}
			//ArrayList<ProjectDTOResp>  resultado = projectService.getAllProjectsNonDeleted();
			if (resultado != null) {
				return Response.ok(resultado).build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(17))).build();
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
			if(userId>0) {
				resultado = projectService.getOnlyPublicNonDeletedProjectsFromUser(user);	
			}else {
				resultado = projectService.getOnlyPublicProjectsNonDeleted();
			}
	
			//ArrayList<ProjectDTOResp> resultado = projectService.getOnlyPublicProjectsNonDeleted();
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
	 *  Get All projects marked as deleted gerais ou de um utilizador especifico (queryparam do utilizador é opcional)
	 * @param userId
	 * @param authString
	 * @return
	 */
	@GET
	@Path("deletedList")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProjectMarkedAsDeleted (@QueryParam("user")  int userId, @HeaderParam("Authorization") String authString) {
		ArrayList<ProjectDTOResp> resultado  = new ArrayList<>();
		System.out.println("Entrei em getAllProject deleted no controller com token? : " + authString);
		User user = userService.getUserEntitybyId(userId); //mesmo que um user tenha sido apagado podemos ver os seus projectos
		
		try {
			if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não está logado ou não tem token válido																				
				if(userId>0) {
					resultado = projectService.getOnlyPublicProjectsMarkedAsDeletedFromUser(user);
				}else {
					resultado = projectService.getOnlyPublicProjectsNonDeleted();
				}
				if (resultado != null) {
					return Response.ok(resultado).build();
				} else {
					return Response.status(400).entity((GestaoErros.getMsg(17))).build();
				}			
			}
			if(userId>0) {
				resultado = projectService.getAllProjectsMarkedAsDeletedFromUser(user);
			}else {
				resultado = projectService.getAllProjectsMarkedAsDeleted();
			}
			if (resultado != null) {
				return Response.ok(resultado).build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(17))).build();
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
			if(userId>0) {
				resultado = projectService.getOnlyPublicProjectsMarkedAsDeletedFromUser(user);
			}else {
				resultado = projectService.getOnlyPublicProjectsNonDeleted();
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
	 * Apagar (marcar para apagado, soft delete) de um projecto 
	 * @param projectId
	 * @param authString
	 * @return
	 */
	@DELETE
	@Path("{projectId: [0-9]+}")
	public Response deleteProject(@PathParam("projectId") int projectId,	@HeaderParam("Authorization") String authString) {
		System.out.println("entrei em deleteProject trouxe o id: " + projectId +  " token "+ authString);


		if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// está logado mas token não é valido																					// válido
			return Response.status(401).entity(GestaoErros.getMsg(1)).build();
		}
		Project project =projectService.getNonDeletedProjectEntityById(projectId);
		boolean isLoggedUserPrivAdmin = userService.hasLoggedUserAdminPriv(authString);
		boolean isAuthenticatedUserCreaterOfProject = userService.isUserAuthenticated(authString, project.getCreatedBy().getId());// utilizador ao qual se está modificar/apagar rpojecto vs detentor do token
		System.out.println("o projecto é do user autenticado: " + isAuthenticatedUserCreaterOfProject);
		System.out.println("o user autenticado tem priv admin: " + isLoggedUserPrivAdmin);
	
		if ((!isAuthenticatedUserCreaterOfProject && !isLoggedUserPrivAdmin)) { //so o criador ou um admin é que pode apagar um projecto
			System.out.println(GestaoErros.getMsg(13));
			return Response.status(403).entity(GestaoErros.getMsg(13)).build();
		}

		try {
			if (projectService.deleteProject(authString,projectId)) {
				return Response.ok().build();
			} else {
				return Response.status(400).entity("A categoria continua marcada para eliminar").build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}

	}

	/**
	 *  Recuperar  um projecto marcado para apagar
	 * @param projectId
	 * @param authString
	 * @return
	 */
	@POST
	@Path("{projectId: [0-9]+}/undelete")
//	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response undeletedPrject(@PathParam("projectId") int projectId, @HeaderParam("Authorization") String authString) {
		System.out.println("entrei em undeletedPrject ");
		if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// está logado mas token não é valido																					// válido
			return Response.status(401).entity(GestaoErros.getMsg(1)).build();
		}
		Project project =projectService.getNonDeletedProjectEntityById(projectId);
		boolean isLoggedUserPrivAdmin = userService.hasLoggedUserAdminPriv(authString);
		boolean isAuthenticatedUserCreaterOfProject = userService.isUserAuthenticated(authString, project.getCreatedBy().getId());// utilizador ao qual se está modificar/apagar rpojecto vs detentor do token
		System.out.println("o projecto é do user autenticado: " + isAuthenticatedUserCreaterOfProject);
		System.out.println("o user autenticado tem priv admin: " + isLoggedUserPrivAdmin);
	
		if ((!isAuthenticatedUserCreaterOfProject && !isLoggedUserPrivAdmin)) { //so o criador ou um admin é que pode apagar um projecto
			System.out.println(GestaoErros.getMsg(13));
			return Response.status(403).entity(GestaoErros.getMsg(13)).build();
		}

		try {
			if (projectService.undeleteProject(authString,projectId)) {
				return Response.ok().build();
			} else {
				return Response.status(400).entity("A categoria continua marcada para eliminar").build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}

	}
	
	
/////////////////////////////////////////////////////////////
///     Endpoints referentes a associar users a projectos ///
/////////////////////////////////////////////////////////////
	
	
	
	/**
	 * associar users a projecto
	 * @param projectId
	 * @param authString
	 * @param newProject
	 * @return
	 */
	@POST
	@Path("{projectId: [0-9]+}/associateUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response assocUsersToProect(@PathParam("projectId") int projectId, @HeaderParam("Authorization") String authString, ProjectSharingDTO projectSharingDTO) {
		System.out.println("Entrei em updateProject no controller com token " + authString);
		try {
			Project project =projectService.getNonDeletedProjectEntityById(projectId); //não se vai fazer update de um projecto apagado
			User userOwner = project.getCreatedBy(); // o criador do projecto
			User userAuthenticated  = userService.getNonDeletedEntityByToken(authString); //vai ser o lastModifBy
	
			boolean isloggedUserPrivAdmin = userService.hasLoggedUserAdminPriv(userAuthenticated);// verifica se quem está logado é um admin
			boolean isOwnerSameAsAuthenticated = userService.isUserAuthenticated(userAuthenticated, userOwner);// verifica se utilizador ao qual se está adicionar projecto é o user logado
			//boolean isloggedAuthorizedToGetProject = projectService.isProjectAssocToUser(projectId, userOwner.getId());// verifica se utilizador do qual se está a fazer get projecto é o user criador ou um dos associados ao projecto - neste caso este tabém não pode editar
			System.out.println("o utilizador logado tem privilégios de admin: " + isloggedUserPrivAdmin);
			System.out.println("o utilizador logado é o utilizador onde queremos aceder: " + isOwnerSameAsAuthenticated);
			//System.out.println("o utilizador logado tem autorização para fazer get do Projecto: " + isloggedAuthorizedToGetProject);
		
			if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não é publico e o utilizador não está logado ou não tem token válido																				
				return Response.status(401).entity(GestaoErros.getMsg(1)).build();
			}
			
			if (!isOwnerSameAsAuthenticated && !isloggedUserPrivAdmin){// quem está logado não é o utilizador do userid nem é admin - não pode criar projectos noutro utilizador
				System.out.println("não tem permissões para ver projectos deste utilizador");
				return Response.status(403).entity(GestaoErros.getMsg(13)).build();
			}
			
	
			if(projectSharingService.associateUserToProject(userAuthenticated,projectId, projectSharingDTO)) {
				return Response.ok().build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(17))).build();
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}		
	}
	
	

	/**
	 * associar users a projecto
	 * @param projectId
	 * @param authString
	 * @param newProject
	 * @return
	 */
	@POST
	@Path("{projectId: [0-9]+}/accpetInvite") //to mySelf
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response acceptInvite(@PathParam("projectId") int projectId, @HeaderParam("Authorization") String authString) {
		System.out.println("Entrei em updateProject no controller com token " + authString);
		try {
			Project project =projectService.getNonDeletedProjectEntityById(projectId); //não se vai fazer update de um projecto apagado
			User userOwner = project.getCreatedBy(); // o criador do projecto
			User userAuthenticated  = userService.getNonDeletedEntityByToken(authString); //vai ser o lastModifBy
	
			boolean isloggedUserPrivAdmin = userService.hasLoggedUserAdminPriv(userAuthenticated);// verifica se quem está logado é um admin
			boolean isOwnerSameAsAuthenticated = userService.isUserAuthenticated(userAuthenticated, userOwner);// verifica se utilizador ao qual se está adicionar projecto é o user logado
			//boolean isloggedAuthorizedToGetProject = projectService.isProjectAssocToUser(projectId, userOwner.getId());// verifica se utilizador do qual se está a fazer get projecto é o user criador ou um dos associados ao projecto - neste caso este tabém não pode editar
			System.out.println("o utilizador logado tem privilégios de admin: " + isloggedUserPrivAdmin);
			System.out.println("o utilizador logado é o utilizador onde queremos aceder: " + isOwnerSameAsAuthenticated);
			//System.out.println("o utilizador logado tem autorização para fazer get do Projecto: " + isloggedAuthorizedToGetProject);
		
			if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não é publico e o utilizador não está logado ou não tem token válido																				
				return Response.status(401).entity(GestaoErros.getMsg(1)).build();
			}
			
			if (!isOwnerSameAsAuthenticated && !isloggedUserPrivAdmin){// quem está logado não é o utilizador do userid nem é admin - não pode criar projectos noutro utilizador
				System.out.println("não tem permissões para ver projectos deste utilizador");
				return Response.status(403).entity(GestaoErros.getMsg(13)).build();
			}
			
			if(projectSharingService.acceptAssocToProject(userAuthenticated,projectId)) {

				return Response.ok().build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(17))).build();
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}		
	}
	
	/**
	 * associar users a projecto
	 * @param projectId
	 * @param authString
	 * @param newProject
	 * @return
	 */
	@POST
	@Path("{projectId: [0-9]+}/cancelUserAssoc")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cancelAssoc(@PathParam("projectId") int projectId, @HeaderParam("Authorization") String authString,ProjectSharingDTO projectSharingDTO) {
		System.out.println("Entrei em updateProject no controller com token " + authString);
		try {
			Project project =projectService.getNonDeletedProjectEntityById(projectId); //não se vai fazer update de um projecto apagado
			User userOwner = project.getCreatedBy(); // o criador do projecto
			User userAuthenticated  = userService.getNonDeletedEntityByToken(authString); //vai ser o lastModifBy
	
			boolean isloggedUserPrivAdmin = userService.hasLoggedUserAdminPriv(userAuthenticated);// verifica se quem está logado é um admin
			boolean isOwnerSameAsAuthenticated = userService.isUserAuthenticated(userAuthenticated, userOwner);// verifica se utilizador ao qual se está adicionar projecto é o user logado
			//boolean isloggedAuthorizedToGetProject = projectService.isProjectAssocToUser(projectId, userOwner.getId());// verifica se utilizador do qual se está a fazer get projecto é o user criador ou um dos associados ao projecto - neste caso este tabém não pode editar
			System.out.println("o utilizador logado tem privilégios de admin: " + isloggedUserPrivAdmin);
			System.out.println("o utilizador logado é o utilizador onde queremos aceder: " + isOwnerSameAsAuthenticated);
			//System.out.println("o utilizador logado tem autorização para fazer get do Projecto: " + isloggedAuthorizedToGetProject);
		
			if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não é publico e o utilizador não está logado ou não tem token válido																				
				return Response.status(401).entity(GestaoErros.getMsg(1)).build();
			}
			
			if (!isOwnerSameAsAuthenticated && !isloggedUserPrivAdmin){// quem está logado não é o utilizador do userid nem é admin - não pode criar projectos noutro utilizador
				System.out.println("não tem permissões para ver projectos deste utilizador");
				return Response.status(403).entity(GestaoErros.getMsg(13)).build();
			}
			
			if(projectSharingService.cancelAssocToProject(userAuthenticated,projectId,projectSharingDTO)) {

				return Response.ok().build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(17))).build();
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}		
	}
	


	/**
	 * lista de users associados a projecto
	 * @param projectId
	 * @param authString
	 * @param newProject
	 * @return
	 */
	@GET
	@Path("{projectId: [0-9]+}/assocUsersList")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getUserAssocToProject(@PathParam("projectId") int projectId, @HeaderParam("Authorization") String authString) {
		System.out.println("Entrei em updateProject no controller com token " + authString);
		try {
			Project project =projectService.getNonDeletedProjectEntityById(projectId); //não se vai fazer update de um projecto apagado
			User userOwner = project.getCreatedBy(); // o criador do projecto
			User userAuthenticated  = userService.getNonDeletedEntityByToken(authString); //vai ser o lastModifBy
	
			boolean isloggedUserPrivAdmin = userService.hasLoggedUserAdminPriv(userAuthenticated);// verifica se quem está logado é um admin
			boolean isOwnerSameAsAuthenticated = userService.isUserAuthenticated(userAuthenticated, userOwner);// verifica se utilizador ao qual se está adicionar projecto é o user logado
			//boolean isloggedAuthorizedToGetProject = projectService.isProjectAssocToUser(projectId, userOwner.getId());// verifica se utilizador do qual se está a fazer get projecto é o user criador ou um dos associados ao projecto - neste caso este tabém não pode editar
			System.out.println("o utilizador logado tem privilégios de admin: " + isloggedUserPrivAdmin);
			System.out.println("o utilizador logado é o utilizador onde queremos aceder: " + isOwnerSameAsAuthenticated);
			//System.out.println("o utilizador logado tem autorização para fazer get do Projecto: " + isloggedAuthorizedToGetProject);
		
			if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não é publico e o utilizador não está logado ou não tem token válido																				
				return Response.status(401).entity(GestaoErros.getMsg(1)).build();
			}
			
			if (!isOwnerSameAsAuthenticated && !isloggedUserPrivAdmin){// quem está logado não é o utilizador do userid nem é admin - não pode criar projectos noutro utilizador
				System.out.println("não tem permissões para ver projectos deste utilizador");
				return Response.status(403).entity(GestaoErros.getMsg(13)).build();
			}
			
			ArrayList<UserDTOResp> users= projectSharingService.getUserAssocToProject(projectId);
			if (users!=null) {

				return Response.ok(users).build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(17))).build();
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}		
	}
	


	
	//projects/4/assocUsersList?user=8
	@GET
	@Path("{projectId: [0-9]+}/projectAssocToUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProjectAssocToUser (@QueryParam("user")  int userId, @HeaderParam("Authorization") String authString) {
		System.out.println("Entrei em getAllProjectAssocToUser por user no controller com token? : " + authString);
		System.out.println("vou querer os projectos do userid " + userId);
		User user = userService.getUserEntitybyId(userId); //mesmo que um user tenha sido apagado podemos ver os seus projectos
		System.out.println(user);
		ArrayList<ProjectDTOResp> resultado=new ArrayList<>();
		
		System.out.println("Entrei em getAllProject no controller com token? : " + authString);
		try {
			if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não está logado ou não tem token válido																				
				if(userId>0) {
					resultado = projectSharingService.getOnlyPublicNonDeletedAssocProjectFromUser(user);	
				}else {
					resultado = null;

				}
				//ArrayList<ProjectDTOResp> resultado = projectService.getOnlyPublicProjectsNonDeleted();
				if (resultado != null) {
					return Response.ok(resultado).build();
				} else {
					return Response.status(400).entity((GestaoErros.getMsg(17))).build();
				}			
			}
			
			
			if(userId>0) {
				resultado = projectSharingService.getNonDeletedAssocProjectFromUser(user);	
			}else {
				resultado = null;

			}
			//ArrayList<ProjectDTOResp>  resultado = projectService.getAllProjectsNonDeleted();
			if (resultado != null) {
				return Response.ok(resultado).build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(17))).build();
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
			if(userId>0) {
				resultado = projectSharingService.getOnlyPublicNonDeletedAssocProjectFromUser(user);	
			}else {
				resultado = null;

			}
	
			//ArrayList<ProjectDTOResp> resultado = projectService.getOnlyPublicProjectsNonDeleted();
			if (resultado != null) {
				return Response.ok(resultado).build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(17))).build();
			}
		} catch (Exception e) {
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}

	}
	
	
	
	

}
