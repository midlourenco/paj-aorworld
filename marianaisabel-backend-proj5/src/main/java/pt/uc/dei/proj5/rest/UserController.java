package pt.uc.dei.proj5.rest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import pt.uc.dei.proj5.bean.InitialDataBean;
import pt.uc.dei.proj5.bean.UserBean;
import pt.uc.dei.proj5.dto.UserDTO;
import pt.uc.dei.proj5.dto.UserDTORegister;
import pt.uc.dei.proj5.dto.UserDTOResp;
import pt.uc.dei.proj5.entity.User;
import pt.uc.dei.proj5.entity.User.UserPriv;
import pt.uc.dei.proj5.other.GestaoErros;

import org.json.JSONArray;
import org.json.JSONObject;

@Path("users")
public class UserController {

// o context é utilizado para criar o url correspondente ao user novo registado.
	@Context
	private UriInfo context;
	@Inject
	private InitialDataBean initialDataService;

	@Inject
	private UserBean userService;
	@Inject
	private GestaoErros gestaoErros;

	// POST /rest/users/login
	/**
	 * Login de um utilizador 
	 * @param credenciais email/password em formato JSON
	 * @return token em formato JSON
	 */
	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(String credenciais) {
		
		System.out.println("entrei em login");
		
		Response a_resposta;
		JSONObject obj = new JSONObject(credenciais);

		String email = obj.getString("email");
		String password = obj.getString("password");
		boolean resultado = userService.validAutentication(email, password);

		if (resultado) {
			System.out.println("credenciais ok no controller " + GestaoErros.getMsg(8));
			String token = userService.getTokenForLoggedUser(email);

			// a_resposta=Response.ok().header("Authorization",token).entity(GestaoErros.getMsg(8)).build();
			// a_resposta=Response.ok().entity("{'Authorization': '" + token+"'}").build();

			JSONObject tokenJson = new JSONObject();
			tokenJson.put("Authorization", token);
			// ObjectMapper mapper = new ObjectMapper();
			// String json = mapper.writeValueAsString();
			a_resposta = Response.ok().entity(tokenJson.toString()).build();
//			UserDTOResp user = userService.getUserDTORespByToken(token);
//			a_resposta = Response.ok(user).build();
		} else {
			System.out.println(GestaoErros.getMsg(7));
			a_resposta = Response.status(401).entity(GestaoErros.getMsg(7)).build();
		}

		return a_resposta;

	}

	
	// POST /rest/users/logout
	// Sample Input: Empty Body Sample Output: Empty Body Response Codes:
	// 200 - Success 401 - Failed
	/**
	 * Logout como utilizador
	 * @param authString
	 * @return
	 */
	@POST
	@Path("logout")
	public Response logout(@HeaderParam("Authorization") String authString) {
		Response a_resposta;
		//401: está logado mas o token não é válido
		if (authString == null || authString.isEmpty() || (!userService.isValidToken(authString))) {																				
			return a_resposta = Response.status(401).entity(GestaoErros.getMsg(1)).build();
		}
		if (userService.logout(authString)) {
			a_resposta = Response.ok().build();
		} else {
			a_resposta = Response.status(400).entity(GestaoErros.getMsg(0)).build();
		}

		return a_resposta;

	}


	
	// Register as user POST /rest/users/register 
	// registo pode ser feito por qq um sem login - nao pode ser um registo com priv de um admin nem um membro -tem de ser aprovado por um admin
	@POST
	@Path("register")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(@Valid UserDTORegister newUser) {
		System.out.println("entrei no addUser : " );

		Response a_resposta = null;
		try {
//			if (newUser.getPrivileges() != UserPriv.VIEWER) {
//				System.out.println("erro: está a criar user com priv admin");
//				return a_resposta = Response.status(400).entity("Não pode criar user Admin ou Membro").build(); 
//
//			}else {
				if (userService.createUser(newUser)) {// método que grava user na BD
					System.out.println("user criado com sucesso");
					UriBuilder createdURI = context.getAbsolutePathBuilder(); //podia-se ainda adicionar a path directa para o novo user criado .path(Integer.toString(userId));
					a_resposta = Response.created(createdURI.build()).build();

				} else {
					a_resposta = Response.status(400).entity("email já se encontra registado").build();// 400 - conflito - o username (PK do user, já existe)
				}
			//}
					
		} catch (Exception e) {
			System.out.println("entrei no catch de registar user no controller do user: " );
			System.out.println("erro na criação de user -> 400!!!! - " + e.getClass().getName());
			return a_resposta = Response.status(400).build();
		}

		return a_resposta;
	}
	
	// Consultar lista de todos os utilizadores - pode ser consultado por qualquer um!!!
	// input: empty
	// output: empty
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAllUsers(@HeaderParam("Authorization") String authString) {
		System.out.println("get users");
		ArrayList<UserDTOResp> listaUsers= new ArrayList<>();

		try {
//			authString.equals("") || authString.isEmpty() || authString == nul
//			if (!authString.equals("") && !userService.isValidToken(authString)) {// está logado mas o token não é válido
//				return Response.status(401).entity(GestaoErros.getMsg(1)).build();
//			} 
			
//			if(!userService.hasLoggedUserAdminPriv(authString)) { // está logado e não é admin - não pode ver os users todos
//				return Response.status(403).entity(GestaoErros.getMsg(9)).build();
//			} 
			
			listaUsers = userService.getAllNonDeletedUsers();
			System.out.println("Lista de users ok!");

			return Response.ok(listaUsers).build();
	

		} catch (NullPointerException e) { // caso não tenha Authorization no header
			e.printStackTrace();
			return Response.ok(listaUsers).build();
		}
	}

	// Consultar lista de todos os admines- pode ser por qualquer um 
	@GET
	@Path("adminsList")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAllAdmins(@HeaderParam("Authorization") String authString) {
		System.out.println("get admins");
		ArrayList<UserDTOResp> listaUsers= new ArrayList<>();

		try {
//			if (authString.equals("") || authString.isEmpty() || authString == null) {
//				return Response.status(401).entity(GestaoErros.getMsg(1)).build();
//			}
//			if (!authString.equals("") && !userService.isValidToken(authString)) {// está logado mas o token não é válido
//					return Response.status(401).entity(GestaoErros.getMsg(1)).build();
//			}  
//			if (!userService.hasLoggedUserAdminPriv(authString)) { // está logado e não é admin - não pode ver os users todos
//					return Response.status(403).entity("Não pode ver lista user porque não tem privilégios de admin")
//							.build();
//			} 
			listaUsers = userService.getAllNonDeletedAdmins();
			return Response.ok(listaUsers).build();
	
			
		} catch (NullPointerException e) { // caso não tenha Authorization no header
			e.printStackTrace();
			return Response.ok(listaUsers).build();
		}

	}
	
	// Consultar lista de todos os membros- pode ser por qualquer um 
		@GET
		@Path("membersList")
		@Produces(MediaType.APPLICATION_JSON)
		public Response listAllMembers(@HeaderParam("Authorization") String authString) {
			System.out.println("get members");
			ArrayList<UserDTOResp> listaUsers= new ArrayList<>();

			try {
//				if (authString.equals("") || authString.isEmpty() || authString == null) {
//					return Response.status(401).entity(GestaoErros.getMsg(1)).build();
//				}
//				if (!authString.equals("") && !userService.isValidToken(authString)) {// está logado mas o token não é válido
//						return Response.status(401).entity(GestaoErros.getMsg(1)).build();
//				}  
//				if (!userService.hasLoggedUserAdminPriv(authString)) { // está logado e não é admin - não pode ver os users todos
//						return Response.status(403).entity("Não pode ver lista  user porque não tem privilégios de admin")
//								.build();
//				} 
				listaUsers = userService.getAllNonDeletedMembers();
				return Response.ok(listaUsers).build();
		
				
			} catch (NullPointerException e) { // caso não tenha Authorization no header
				e.printStackTrace();
				return Response.ok(listaUsers).build();
			}

		}
		
		// Consultar lista de todos os viewers com registo para aprovar- apenas pode ser vista por admins
		@GET
		@Path("viwersList")
		@Produces(MediaType.APPLICATION_JSON)
		public Response listAllViwersToAprove(@HeaderParam("Authorization") String authString) {
			System.out.println("get admins");
	
			try {
				if (authString.equals("") || authString.isEmpty() || authString == null) {
					return Response.status(401).entity(GestaoErros.getMsg(1)).build();
				}
				if (!userService.isValidToken(authString)) {// está logado mas o token não é válido
						return Response.status(401).entity(GestaoErros.getMsg(1)).build();
				}  
				if (!userService.hasLoggedUserAdminPriv(authString)) { // está logado e não é admin - não pode ver os users todos
						return Response.status(403).entity("Não pode ver lista user porque não tem privilégios de admin").build();
				} 
				ArrayList<UserDTOResp> listaUsers = userService.getAllNonDeletedViewers();
				return Response.ok(listaUsers).build();
		
			} catch (NullPointerException e) { // caso não tenha Authorization no header
				e.printStackTrace();
				return Response.status(401).entity(GestaoErros.getMsg(1)).build();
			}

		}

	// Consultar lista de todos os utilizadores marcados como eliminados- por um admin
	@GET
	@Path("deletedList")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAllUsersMarkedAsDeleted(@HeaderParam("Authorization") String authString) {
		System.out.println("lista de delete users");
		ArrayList<UserDTOResp> listaUsers = userService.getAllDeletedUsers();

		try {
			if (authString.equals("") || authString.isEmpty() || authString == null
					|| !userService.isValidToken(authString)) {// está logado mas o token não é válido
				return Response.status(401).entity(GestaoErros.getMsg(1)).build();
			} 
			if (!userService.hasLoggedUserAdminPriv(authString)) { // está logado e não é admin - não pode ver os users todos
				return Response.status(403).entity("Não pode ver esta lista porque não tem privilégios de admin").build();
			} 

			if (listaUsers != null) {
				System.out.println("Lista de users ok!");
				return Response.ok(listaUsers).build();
			} else {
				System.out.println("No lista de users");
				return Response.ok().build();
			}

		} catch (NullPointerException e) { // caso não tenha Authorization no header
			e.printStackTrace();
			return Response.status(401).entity(GestaoErros.getMsg(1)).build();

		}

	}


		// Get loggeduser profile
		@GET
		@Path("/myProfile")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getMyProfile(@HeaderParam("Authorization") String authString) {
			try {
				System.out.println("dentro do getMyProfile no controller:  " + authString);
				
				if (authString.equals("") || authString.isEmpty() || authString == null
						|| !userService.isValidToken(authString)) {// está logado mas o token não é válido
					return Response.status(401).entity(GestaoErros.getMsg(1)).build();
				} 
				UserDTOResp user = userService.getUserDTORespByToken(authString);
				return Response.ok(user).build();
				

			} catch (NullPointerException e) { // caso não tenha Authorization no header
				e.printStackTrace();
				return Response.status(401).entity(GestaoErros.getMsg(1)).build();
			}
		}
	
	//não vamos mandar a password junto com o perfil
	// Get an user profile
	@GET
	@Path("{userId: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("userId") int userId, @HeaderParam("Authorization") String authString) {
		try {
			System.out.println("dentro do getUser no controller: " + userId + " " + authString);
			UserDTOResp userDTOResp = userService.getNonDeletedUserDTORespById(userId);
			System.out.println("userDTO : " + userDTOResp);

			if (authString != null && !authString.equals("") && !authString.isEmpty() 
					&& !userService.isValidToken(authString)) {// está logado mas o token não é válido
				return Response.status(401).entity(GestaoErros.getMsg(1)).build();
			} 
//			if (!userService.isUserAuthenticated(authString, userId)
//					&& !userService.hasLoggedUserAdminPriv(authString)) {//quem está a aceder ao perfil não é o mesmo  user que está logado nem tem privilégios de admin
//				return Response.status(403).entity(GestaoErros.getMsg(5)).build();
//			}
			if (userDTOResp != null) {
				return Response.ok(userDTOResp).build(); //[solved 13-4-2022] este user tem a pass. Um admin consegue ver a pass do user seleccionado. RESOLVER!!!!!!!! IDEIA - alterar pass num sitio difrente/metodo diferente
			} else {
				return Response.status(400).entity("User inválido").build();
			}
			

		} catch (NullPointerException e) { // caso não tenha Authorization no header
			e.printStackTrace();
			return Response.status(401).entity(GestaoErros.getMsg(1)).build();
		}
	}

	//  Change user profile POST /rest/users/{userId}/
	@POST
	@Path("{userId: [0-9]+}/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUserInfo(@PathParam("userId") int userId, @Valid UserDTO changeUser,
			@HeaderParam("Authorization") String authString) {

		User userToUpdate = userService.getNonDeletedUserEntityById(userId);
		if (userToUpdate == null) { // o id do url não existir nao avança
			return Response.status(400).entity(GestaoErros.getMsg(2)).build();
		}

		//if (userId!=1) {// não permitimos que se atualize o user admin automatico a não ser  que seja o proprio
		if(!userToUpdate.isAutoAdmin() || (userService.isUserAuthenticated(authString, userId))) {
		
			System.out.println("changedUser recebido: " + changeUser);
			if (authString.equals("") || authString.isEmpty() || authString == null || !userService.isValidToken(authString)) { // token valido?
				return Response.status(401).entity(GestaoErros.getMsg(1)).build();
			}
			
			boolean loggedUserHasAdminPriv = userService.hasLoggedUserAdminPriv(authString);
			boolean isThisProfileFromLoggedUser = userService.isUserAuthenticated(authString, userId);
			
			if (!isThisProfileFromLoggedUser && !loggedUserHasAdminPriv) {// username nao é de quem está logado nem quem está logado tem priv de admin
				return Response.status(403).entity(GestaoErros.getMsg(5)).build();
			}
			
			try {
				boolean resultado = userService.updateUser(userId, changeUser, loggedUserHasAdminPriv,authString);
				UserDTOResp userDTOResp = userService.getUserDTORespById(userId);
	
				if (resultado == true) {
					return Response.ok(userDTOResp).build();
				}
			} catch (Exception e) {
				return Response.status(400).entity(GestaoErros.getMsg(0)).build();
			}
		}
		return Response.status(400).entity(GestaoErros.getMsg(0)).build(); //
	}

	
//  Change user profile POST /rest/users/{userId}/
	@POST
	@Path("{userId: [0-9]+}/updatePassword")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUserInfo(@PathParam("userId") int userId, String newPass,
			@HeaderParam("Authorization") String authString) {

		Response a_resposta;
		JSONObject obj = new JSONObject(newPass);
		String password = obj.getString("password");
		User userToUpdate = userService.getNonDeletedUserEntityById(userId);
		if (userToUpdate == null) { // o id do url não existir nao avança
			return Response.status(400).entity(GestaoErros.getMsg(2)).build();
		}

		//if (userId!=1) {// não permitimos que se atualize o user admin automatico a não ser  que seja o proprio
		if(!userToUpdate.isAutoAdmin() || (userService.isUserAuthenticated(authString, userId))) {
		
			if(password.equals("") ||password.isEmpty() || password==null) {
				return Response.status(400).entity(GestaoErros.getMsg(0)).build(); 
			}
			
			System.out.println("changedUserPass recebido: " + newPass);
			
			if (authString.equals("") || authString.isEmpty() || authString == null || !userService.isValidToken(authString)) { // token valido?
				return Response.status(401).entity(GestaoErros.getMsg(1)).build();
			}
			
			boolean loggedUserHasAdminPriv = userService.hasLoggedUserAdminPriv(authString);
			boolean isThisProfileFromLoggedUser = userService.isUserAuthenticated(authString, userId);
			
			if (!isThisProfileFromLoggedUser && !loggedUserHasAdminPriv) {// username nao é de quem está logado nem quem está logado tem priv de admin
				return Response.status(403).entity(GestaoErros.getMsg(5)).build();
			}
			
			try {
				boolean resultado = userService.updateUserPassword(userId, password, loggedUserHasAdminPriv,authString);
				UserDTOResp userDTOResp = userService.getUserDTORespById(userId);
	
				if (resultado == true) {
					return Response.ok().entity(GestaoErros.getMsg(11)).build();
				}
			
			} catch (Exception e) {
				return Response.status(400).entity(GestaoErros.getMsg(0)).build();
			}
		}
		return Response.status(400).entity(GestaoErros.getMsg(0)).build(); //
	}

	
	/**
	 * permite bloquear um user membro ou admin e/ou nao aprovar o seu registo
	 * @param userId
	 * @param authString
	 * @return
	 */
	@DELETE
	@Path("{userId: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response blockUser(@PathParam("userId") int userId,
			@HeaderParam("Authorization") String authString) {
		try {
			User userToUpdate = userService.getNonDeletedUserEntityById(userId);
			if (userToUpdate == null) { // o id do url não existir nao avança
				return Response.status(400).entity(GestaoErros.getMsg(2)).build();
			}

			//if (userId!=1) {// não permitimos que se atualize o user admin automatico a não ser  que seja o proprio
			if(!userToUpdate.isAutoAdmin() || (userService.isUserAuthenticated(authString, userId))) {
				if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não está logado ou está logado mas o token não é válido
					return Response.status(401).entity(GestaoErros.getMsg(1)).build();
				}
	
				if (!userService.hasLoggedUserAdminPriv(authString)) {// está logado não tem priv admin nao pode apagar ninguem
					System.out.println("não tem permissões para apagar este utilizador");
					return Response.status(403).entity(GestaoErros.getMsg(9)).build();
				}
				
				if (userService.deleteUser(userId,authString)) {
					return Response.ok().entity(GestaoErros.getMsg(11)).build();
				} else {
					return Response.status(400).entity("o user não foi apagado").build();
				}
					
			} else {
				System.out.println("não tem permissões para apagar o ADMIN");
				return Response.status(403).entity(GestaoErros.getMsg(9)).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).entity("não foi possível apagar user").build();
		}

	}

	
	
	/**
	 * permite recuperar um user nao aprovado ou bloqueado
	 * @param userId
	 * @param authString
	 * @return
	 */
	@POST
	@Path("{userId: [0-9]+}/undelete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response undeletedUser(@PathParam("userId") int  userId, @HeaderParam("Authorization") String authString) {
		try {
			System.out.println("dentro de undeletedUser");
			
			User userToUpdate = userService.getUserEntitybyId(userId);
			if (userToUpdate == null) { // o id do url não existir nao avança
				System.out.println("user to update nao é existe");
				return Response.status(400).entity(GestaoErros.getMsg(2)).build();
			}

			//if (userId!=1) {// não permitimos que se atualize o user admin automatico a não ser  que seja o proprio
			if(!userToUpdate.isAutoAdmin() || (userService.isUserAuthenticated(authString, userId))) {
				if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não está logado ou está logado mas o token não é válido
					System.out.println("token recebido nao é existe");
					return Response.status(401).entity(GestaoErros.getMsg(1)).build();
				}
	
				if (!userService.hasLoggedUserAdminPriv(authString)) {// está logado não tem priv admin nao pode apagar ninguem
					System.out.println("não tem permissões para desmarcar para apagar este utilizador");
					return Response.status(403).entity(GestaoErros.getMsg(9)).build();
				}
	
				System.out.println("vou desmarcar de apagar");
				if (userService.undeletedUser(userId,authString)) {
					UserDTOResp updatedUser = userService.getNonDeletedUserDTORespById(userId );
					System.out.println("desmarcado de apagar");
					return Response.ok(updatedUser).build();
				} else {
					return Response.status(400).entity("o user não foi atualizado, continua com marca de apagar").build();
				}
				 
			} else {
				System.out.println("não tem permissões para apagar o ADMIN");
				return Response.status(403).entity(GestaoErros.getMsg(9)).build();
	
			}
		}catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).entity("O user continua marcado para eliminar").build();
		}
	}

	/**
	 * permite alterar priv de user para membro
	 * @param userId
	 * @param authString
	 * @return
	 */
	@POST
	@Path("{userId: [0-9]+}/updateToMember")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response changeUserPrivToMember(@PathParam("userId") int  userId,
			@HeaderParam("Authorization") String authString) {
		System.out.println("dentro do changeUserPrivToMember");
		try {
			User userToUpdate = userService.getNonDeletedUserEntityById(userId);
			if (userToUpdate == null) { // o id do url não existir nao avança
				System.out.println("o user to update nao existe");
				return Response.status(400).entity(GestaoErros.getMsg(2)).build();
			}

			if (authString != null && !authString.equals("") && !authString.isEmpty() 
					&& !userService.isValidToken(authString)) {// está logado mas o token não é válido
				return Response.status(401).entity(GestaoErros.getMsg(1)).build();
			} 
			
			//if (userId!=1) {// não permitimos que se atualize o user admin automatico a não ser  que seja o proprio
			if(!userToUpdate.isAutoAdmin() || (userService.isUserAuthenticated(authString, userId))) {
//				if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não está logado ou está logado mas o token não é válido
//					return Response.status(401).entity(GestaoErros.getMsg(1)).build();
//				}
			
				
				if (!userService.hasLoggedUserAdminPriv(authString)) {// está logado não tem priv admin nao pode apagar ninguem
					System.out.println("não tem permissões para desmarcar para apagar este utilizador");
					return Response.status(403).entity(GestaoErros.getMsg(9)).build();
				}
	
				
				if (userService.changeUserPrivToMember(userId,authString)) {
					UserDTOResp updatedUser = userService.getNonDeletedUserDTORespById(userId);
					
					return Response.ok(updatedUser).build();
				} else {
					return Response.status(400).entity("o user não foi continua com os priv anteriores").build();
				}
				 
			} else {
				System.out.println("não tem permissões para apagar o ADMIN");
				return Response.status(403).entity(GestaoErros.getMsg(9)).build();
	
			}
		}catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).entity("O user continua marcado para eliminar").build();
		}
	}
	
	/**
	 * permite alterar priv de user para membro
	 * @param userId
	 * @param authString
	 * @return
	 */
	@POST
	@Path("{userId: [0-9]+}/updateToAdmin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeUserPrivToAdmin(@PathParam("userId") int  userId,
			@HeaderParam("Authorization") String authString) {
		try {
			User userToUpdate = userService.getNonDeletedUserEntityById(userId);
			if (userToUpdate == null) { // o id do url não existir nao avança
				return Response.status(400).entity(GestaoErros.getMsg(2)).build();
			}

			//if (userId!=1) {// não permitimos que se atualize o user admin automatico a não ser  que seja o proprio
			if(!userToUpdate.isAutoAdmin() || (userService.isUserAuthenticated(authString, userId))) {
				if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não está logado ou está logado mas o token não é válido
					return Response.status(401).entity(GestaoErros.getMsg(1)).build();
				}
	
				if (!userService.hasLoggedUserAdminPriv(authString)) {// está logado não tem priv admin nao pode apagar ninguem
					System.out.println("não tem permissões para desmarcar para apagar este utilizador");
					return Response.status(403).entity(GestaoErros.getMsg(9)).build();
				}
	
				
				if (userService.changeUserPrivToAdmin(userId,authString)) {
					UserDTOResp updatedUser = userService.getNonDeletedUserDTORespById(userId);
					
					return Response.ok(updatedUser).build();
				} else {
					return Response.status(400).entity("o user não foi continua com os priv anteriores").build();
				}
				 
			} else {
				System.out.println("não tem permissões para apagar o ADMIN");
				return Response.status(403).entity(GestaoErros.getMsg(9)).build();
	
			}
		}catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).entity("O user continua marcado para eliminar").build();
		}
	}
	
	
	
	
	
	
	 
		@POST
		@Path("populateUserTable")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response populateUserTable(String users) {
			
//			System.out.println("entrei em populateUserTable");
//			JSONObject objList = new JSONObject(users);
//			
//			
//
//			try {
//			//JSONObject obj = new JSONObject(users);
//			for (JSONObject obj : objList) {
//			String email = obj.getString("email");
//			String password = obj.getString("password");
//			String biography = obj.getString("biography");
//		//	String createdDate = obj.getString("createdDate");
//			String deleted = obj.getString("deleted");
//			String firstName = obj.getString("firstName");
//			String lastName = obj.getString("lastName");
//			String image = obj.getString("image");
//			String UserPriv = obj.getString("UserPriv");
//			String token = obj.getString("token");
//			String lastModifDate = obj.getString("lastModifDate");
//			
//			userService.populateUserTable(email, password,biography,deleted, firstName,lastName,UserPriv, image, token,lastModifDate);
	//		}
			return Response.ok().build();
//			}catch(Exception e) {
//		
//			return  Response.status(400).build();;
//			}

		}

	
	
	
	
}
