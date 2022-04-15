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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.uc.dei.proj5.dto.ProjectDTOResp;
import pt.uc.dei.proj5.entity.User;
import pt.uc.dei.proj5.other.GestaoErros;
import pt.uc.dei.proj5.bean.ProjectBean;
import pt.uc.dei.proj5.bean.UserBean;
import pt.uc.dei.proj5.dto.ProjectDTO;
@Path("users")
public class ProjectController {
	
	@Inject
	private GestaoErros gestaoErros;

	@Inject
	private UserBean userService;
	@Inject
	private ProjectBean projectService;

	
	// Add project to user
	@POST
	@Path("{userId}/projects")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addProject(@PathParam("userId") int userId, @HeaderParam("Authorization") String authString, ProjectDTO newProject) {

		System.out.println("Entrei em add project no controller com userId: " + userId + " token " + authString);

		if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não está logado ou não tem token válido																				
			return Response.status(401).entity(GestaoErros.getMsg(1)).build();
		}

		try {
			User user = userService.getNonDeletedUserEntityById(userId); // utilizador onde estou a criar o projecto
			boolean userPrivAdmin = userService.hasLoggedUserAdminPriv(authString);// verifica se quem está logado é um admin
			boolean userAuthenticated = userService.isUserAuthenticated(authString, userId);// verifica se utilizador ao qual se está adicionar projecto é o user logado
			System.out.println("o utilizador logado tem privilégios de admin: " + userPrivAdmin);
			System.out.println("o utilizador logado é o utilizador onde queremos aceder: " + userAuthenticated);
			
			if (!userAuthenticated && !userPrivAdmin) {// quem está logado não é o utilizador do userid nem é admin - não pode criar projectos noutro utilizador
				System.out.println("não tem permissões para criar projectos neste utilizador");
				return Response.status(403).entity(GestaoErros.getMsg(13)).build();
			}

			try {
				ProjectDTOResp resultado = projectService.addProject(user, newProject);
				if (resultado != null) {
					return Response.ok(resultado).build();
				} else {
					return Response.status(400).entity((GestaoErros.getMsg(6))).build();
				}

			} catch (Exception e) {
				e.printStackTrace();
				return Response.status(400).entity(GestaoErros.getMsg(17)).build();
			}

		} catch (Exception e) {
			return Response.status(400).entity(GestaoErros.getMsg(3)).build();
		}

	}


	// Get project from user
	@GET
	@Path("{userId}/projects/{projectId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProject(@PathParam("userId") int userId, @PathParam("projectId") int projectId, @HeaderParam("Authorization") String authString) {
		boolean projectPublicVisibilty=false;
		ProjectDTOResp resultado=null ;
		System.out.println("Entrei em get project no controller com userId: " + userId + " token " + authString);
		try {
			User user = userService.getNonDeletedUserEntityById(userId); // utilizador onde estou a criar o projecto
			boolean userPrivAdmin = userService.hasLoggedUserAdminPriv(authString);// verifica se quem está logado é um admin
			boolean userAuthenticated = userService.isUserAuthenticated(authString, userId);// verifica se utilizador ao qual se está adicionar projecto é o user logado
			boolean userAuthorizedToGetProject = projectService.isProjectAssocToUser(projectId, userId);// verifica se utilizador do qual se está a fazer get projecto é o user criador ou um dos associados ao projecto 
			projectPublicVisibilty = projectService.isProjectWithPublicVisibility(projectId);
			System.out.println("o utilizador logado tem privilégios de admin: " + userPrivAdmin);
			System.out.println("o utilizador logado é o utilizador onde queremos aceder: " + userAuthenticated);
			System.out.println("o utilizador logado tem autorização para fazer get do Projecto: " + userPrivAdmin);
			System.out.println("o projecto é visivel ao público em geral: " + projectPublicVisibilty);
	
			resultado = projectService.getProject(projectId);
			
			if ((!projectPublicVisibilty) && (authString == null || authString.isEmpty() || !userService.isValidToken(authString))) {// não é publico e o utilizador não está logado ou não tem token válido																				
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
			if(projectPublicVisibilty) {
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
	
	
	// Get All project from user
	@GET
	@Path("projects")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProject (@HeaderParam("Authorization") String authString) {

		System.out.println("Entrei em getAllProject no controller com token? : " + authString);
		try {
			if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não está logado ou não tem token válido																				
				ArrayList<ProjectDTOResp> resultado = projectService.getOnlyPublicProjectsNonDeleted();
				if (resultado != null) {
					return Response.ok(resultado).build();
				} else {
					return Response.status(400).entity((GestaoErros.getMsg(6))).build();
				}			
			}
			
			ArrayList<ProjectDTOResp>  resultado = projectService.getAllProjectsNonDeleted();
			if (resultado != null) {
				return Response.ok(resultado).build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(6))).build();
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
			ArrayList<ProjectDTOResp> resultado = projectService.getOnlyPublicProjectsNonDeleted();
			if (resultado != null) {
				return Response.ok(resultado).build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(6))).build();
			}
		} catch (Exception e) {
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}

	}
	
	//para editar é que se tem de validar o seguinte
//	if ((!userAuthenticated && !userPrivAdmin) || (!userAuthorizedToGetProject && !userPrivAdmin)){// quem está logado não é o utilizador do userid nem é admin - não pode criar projectos noutro utilizador
//		System.out.println("não tem permissões para ver projectos deste utilizador");
//		return Response.status(403).entity(GestaoErros.getMsg(13)).build();
//	}

}
