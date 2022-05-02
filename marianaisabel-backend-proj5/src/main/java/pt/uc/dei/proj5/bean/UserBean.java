package pt.uc.dei.proj5.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import pt.uc.dei.proj5.dao.UserDao;
import pt.uc.dei.proj5.dto.UserDTO;
import pt.uc.dei.proj5.dto.UserDTORegister;
import pt.uc.dei.proj5.dto.UserDTOResp;
import pt.uc.dei.proj5.entity.User;

//@ApplicationScoped
@RequestScoped
public class UserBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private UserDao userDao;
	@Inject
	private DashboardBean dashboardService;

	
	/////////////////////////////////////////////////////////////////////////////
	// *************GETTERS de USERS*************
	/////////////////////////////////////////////////////////////////////////////
	
	//get do user by token, by email, by id, 
	//get do token

	
	/**
	 * Devolve um utilizador a partir do seu token que não esteja marcado para eliminar
	 * @param authString
	 * @return
	 */
	public User getNonDeletedEntityByToken(String authString) {
		// System.out.println("entrei no get user by token");
		try {
			User user = userDao.findUserByToken(authString);
			// System.out.println("getUserbytoken:" + user);
			return user;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Devolve um UserDTO válido (não marcado como eliminado) recebendo um token
	 * @deprecated
	 * @param authString
	 * @return
	 */
	public UserDTO getUserDTOByToken(String authString) {
		// System.out.println("entrei no get user by token");
		try {
			User user = userDao.findUserByToken(authString);
			// System.out.println("getUserbytoken:" + user);
			return UserDao.convertEntitytoDTO(user);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Devolve um UserDTOResp válido (não marcado como eliminado) recebendo um token
	 * @param authString
	 * @return
	 */
	public UserDTOResp getUserDTORespByToken(String authString) {
		// System.out.println("entrei no get user by token");
		try {
			User user = userDao.findUserByToken(authString);
			// System.out.println("getUserbytoken:" + user);
			return UserDao.convertEntitytoDTOResp(user);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Devolve um utilizador a partir do seu id quer  esteja marcado para eliminar ou não
	 * @param email
	 * @return
	 */
	public User getUserEntitybyId(int userId ) {
		try {
			User user = userDao.find(userId);
			return user;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	/**
	 * Devolve um utilizador a partir da sua PK (id) que não esteja marcado para eliminar
	 * @param userID
	 * @return
	 */
	public User getNonDeletedUserEntityById(int userID) {
		// System.out.println("entrei no get user by token");
		try {
			User user = userDao.findEntityIfNonDelete(userID);
			// System.out.println("getUserbytoken:" + user);
			return user;

		} catch (Exception e) {
			System.out.println("o user não existe ou está marcado para apagar");
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Devolve um utilizador a partir da sua PK ( id ) que não esteja marcado para eliminar
	 * @param userID
	 * @deprecated
	 * @return
	 */
	public UserDTO getNonDeletedUserDTOById(int userID) {
		// System.out.println("entrei no get user by token");
		try {
			User user = userDao.findEntityIfNonDelete(userID);
			return UserDao.convertEntitytoDTO(user);

		} catch (Exception e) {
			System.out.println("o user não existe ou está marcado para apagar");
			e.printStackTrace();
			return null;
		}
	}
	
	

	/**
	 * Devolve um utilizador a partir da sua PK ( id ) que não esteja marcado para eliminar
	 * @param userID
	 * @return
	 */
	public UserDTOResp getNonDeletedUserDTORespById(int userID) {
		// System.out.println("entrei no get user by token");
		try {
			User user = userDao.findEntityIfNonDelete(userID);
			return UserDao.convertEntitytoDTOResp(user);

		} catch (Exception e) {
			System.out.println("o user não existe ou está marcado para apagar");
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * devolve um utilizador DTO a partir do seu id quer ele esteja marcado para apagar ou não
	 * @param userID
	 * @return
	 */
	public UserDTOResp getUserDTORespById(int userID) {
		
		try {
			UserDTOResp userDtoResp = UserDao.convertEntitytoDTOResp(userDao.find(userID));
			System.out.println("dentro do user do serviço " + userID + " e o user respectivo " + userDtoResp);
			return userDtoResp;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	
	
	/**
	 * Devolve um utilizador a partir do seu email quer  esteja marcado para eliminar ou não
	 * @param email
	 * @return
	 */
	public User getUserEntitybyEmail(String email) {
		try {
			User user = userDao.findUserByEmail(email);
			return user;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public UserDTOResp getUserUserDTORespbyEmail(String email) {
		return UserDao.convertEntitytoDTOResp(getUserEntitybyEmail(email));
	}
	
	/**
	 * Devolve um utilizador a partir do seu email quer  esteja marcado para eliminar ou não
	 * @param email
	 * @return
	 */
	public User getNonDeletedUserEntitybyEmail(String email) {
		try {
			User user = userDao.findNonDeletedUserByEmail(email);
			return user;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	// usado para passar a informaçao do Token gerado após login
	/**
	 * Devolve o Token de user recebendo o username
	 * 
	 * @param username
	 * @return
	 */
	public String getTokenForLoggedUser(String email) {
		System.out.println("vou buscar o token em getTokenForLoggedUser em userBean");
		User user = getNonDeletedUserEntitybyEmail(email);
		return user.getToken();
	}

	
	
	
/////////////////////////////////////////////////////////////////////////////
// **** GET DE LISTAS DE UTILIZADOR **********
///////////	////////////////////////////////////////////////////////////////
	
	/**
	* Devolve todos os utilizadores não marcados para eliminar que sejam membros ou admins (não devolve aqueles que estão À espera de aprovação)
	* @return
	*/
	public ArrayList<UserDTOResp> getAllNonDeletedUsers() {
		UserDTOResp userDTOResp;
		ArrayList<UserDTOResp> listaUserDTO = new ArrayList<UserDTOResp>();
		//List<User> listaUserEntity = userDao.findAllNonDeleted(); //se quisessemos todos incluindo  os que estão à espera de aprovação
		
		List<User> listaUserEntity = userDao.findAllUserOfOrganization();
		System.out.println("tam lista Entity " + listaUserEntity.size());
	
		for (User userEntity : listaUserEntity) {
			// System.out.println(userEntity);
			userDTOResp = UserDao.convertEntitytoDTOResp(userEntity);
			// System.out.println(userDTO);
			listaUserDTO.add(userDTOResp);
		}
		System.out.println("tam lista DTO " + listaUserDTO.size());
		return listaUserDTO;
	}
	
	/**
	* Devolve todos os utilizadores não marcados para eliminar e com privilegios de admin
	* @return
	*/
	public ArrayList<UserDTOResp> getAllNonDeletedAdmins() {
		ArrayList<UserDTOResp> listaUserDTO = new ArrayList<UserDTOResp>();
		List<User> listaUserEntity = userDao.findAllAdmins();
		for (User userEntity : listaUserEntity) {
			listaUserDTO.add(UserDao.convertEntitytoDTOResp(userEntity));
		}
	
		return listaUserDTO;
	}
	
	/**
	* Devolve todos os utilizadores não marcados para eliminar e com privilegios de membro registado
	* @return
	*/
	public ArrayList<UserDTOResp> getAllNonDeletedMembers() {
	ArrayList<UserDTOResp> listaUserDTO = new ArrayList<UserDTOResp>();
	List<User> listaUserEntity = userDao.findAllMembers();
	for (User userEntity : listaUserEntity) {
	listaUserDTO.add(UserDao.convertEntitytoDTOResp(userEntity));
	}
	
	return listaUserDTO;
	}
	
	
	//exemplo listar os perfis que ainda não estão aprovados por admin
	/**
	* Devolve todos os utilizadores não marcados para eliminar e com privilegios de viewer (apenas podem ver o que é publico)
	* @return
	*/
	public ArrayList<UserDTOResp> getAllNonDeletedViewers() {
	ArrayList<UserDTOResp> listaUserDTO = new ArrayList<UserDTOResp>();
	List<User> listaUserEntity = userDao.findAllViewersToAprove();
	for (User userEntity : listaUserEntity) {
	listaUserDTO.add(UserDao.convertEntitytoDTOResp(userEntity));
	}
	
	return listaUserDTO;
	}
	
	
	/**
	* Devolve todoso os utilizadores marcados para eliminar
	* @return
	*/
	public ArrayList<UserDTOResp> getAllDeletedUsers() {
	ArrayList<UserDTOResp> listaUserDTO = new ArrayList<UserDTOResp>();
	List<User> listaUserEntity = userDao.findAllMarkedAsDeleted();
	for (User userEntity : listaUserEntity) {
	listaUserDTO.add(UserDao.convertEntitytoDTOResp(userEntity));
	}
	
	return listaUserDTO;
	}

	
	
	/////////////////////////////////////////////////////////////////////////////
	// *************AUTENTICAÇÃO, LOGIN E LOGOUT*************
	/////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Método que valida se as credenciais de um LOGIN são válidas
	 * atendendo a que um utilizado apagado não pode fazer login
	 * 
	 * Login permitindo que um utilizador tenha sessão em mais do que um sítio ao mesmo tempo
	 * @param email email do utilizador
	 * @param password password do utilizador
	 * @return true se estiver ok, false se estiver errada
	 */
	public boolean validAutentication(String email, String password) {

		System.out.println("entrei no validAutentication em userBean");
		try {
			User user = getNonDeletedUserEntitybyEmail(email);
	
			if (user != null) {
				if(user.getPrivileges()!=User.UserPriv.VIEWER) { //alguém que se registou mas o seu registo ainda não foi apreciado or um admin
				        String[] astr = (user.getHashPassword().split(":"));

				        try {
				            String hashedPassLogin = user.hashingPasswordLogin(password, astr[2], Integer.parseInt(astr[0]), Integer.parseInt(astr[3]));
				            if (hashedPassLogin.equals(user.getHashPassword())) {

								if(user.getToken()!=null) {
									//as credenciais estão ok  e já tinha token (mantemos-lhe o token) e assim pode ter várias sessões ao mesmo tempo
									System.out.println("credencais ok, token anterior mantido: " + user.getToken());
									return true; 
								}else {
									//as credenciais estão ok e não tinha nenhum token, geramos um token novo
									user.setToken(UUID.randomUUID().toString()); 
									userDao.merge(user);
									System.out.println("credencais ok, novo token gerado: " + user.getToken());
									return true; 
								}
				            	
				            } else {
								// as credenciais não estão correctas
								System.out.println("as credenciais estao erradas ");
								return false;
							}
				            
				        } catch (RuntimeException e) {
				            System.out.println("houve um problema com o hashing da password - runtime exception");
				        	return false;
				        }
				        
				}else {
					// o email é de um registo que ainda está para ser aprovado por um admin
					System.out.println("ainda não tem registo aprovado por admin ");
					return false;
				}
				
			} else {
				// nao existe id
				System.out.println("o id do user não existe ");
				return false;
			}
				
		}catch (Exception e){
			
			e.printStackTrace();
			System.out.println("o id do user não existe ");
			return false;
		}
	}

	

	/**
	 * verifica se o token recebido existe na BD (em utilizador não marcado para eliminar)
	 * 
	 * @param authString
	 * @return
	 */
	public boolean isValidToken(String authString) {
		try {
			UserDTO userdto = getUserDTOByToken(authString);

			if (userdto != null) {
				return true;
			} else {
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
		System.out.println("dentro do hasLoggedUserAdminPriv");
		User user = userDao.findUserByToken(authString);
		if(user.getPrivileges()==User.UserPriv.ADMIN) {
			System.out.println("privilegios do user logado: " + user.getPrivileges());
			return true;
		}
		return false;
	}
	
	/**
	 * Devolve se um user tem ou não priv admin
	 * 
	 * @param authString
	 * @return
	 */
	public boolean hasLoggedUserAdminPriv(User user) {
		System.out.println("dentro do hasLoggedUserAdminPriv");
		if(user.getPrivileges()==User.UserPriv.ADMIN) {
			System.out.println("privilegios do user logado: " + user.getPrivileges());
			return true;
		}
		return false;
	}
	
	/**
	 * Devolve se um user tem ou não priv de membro registado
	 * 
	 * @param authString
	 * @return
	 */
	public boolean hasLoggedUserMemberPriv(String authString) {
		User user = userDao.findUserByToken(authString);
		if(user.getPrivileges()==User.UserPriv.MEMBER) {
			return true;
		}
		return false;
	}
	
	/**
	 * Devolve se um user tem ou não apenas priv de publico geral
	 * 
	 * @param authString
	 * @return
	 */
	public boolean hasLoggedUserPublicPriv(String authString) {
		User user = userDao.findUserByToken(authString);
		if(user.getPrivileges()==User.UserPriv.VIEWER) {
			return true;
		}
		return false;
	}


	
	/**
	 * Devolve se o user autenticado é o user do serviço que se está aceder
	 * 
	 * @param authstring
	 * @param username
	 * @return
	 */
	public boolean isUserAuthenticated(String authString, int userID) {
		//token do utilizador do serviço ao qual se está a aceder
		User loggedUser = getNonDeletedUserEntityById(userID);
		String loggedUserToken ="";
		if(loggedUser!=null) {
			loggedUserToken = loggedUser.getToken(); 
		}
		//compara-se o token de quem está logado com o do serviço que se está a aceder:
		if (!authString.equals(loggedUserToken)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Devolve se o user autenticado é o user do serviço que se está aceder
	 * 
	 * @param authstring
	 * @param username
	 * @return
	 */
	public boolean isUserAuthenticated(User authenticatedUser, User createdBy) {
		//compara-se os ids de quem está logado com o do serviço que se está a aceder:
		if (authenticatedUser.getId()==createdBy.getId()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Apaga o token de um utiliador que faz logout
	 * @param authString
	 * @return
	 */
	public boolean logout(String authString) {
		// User u =userDao.findUserByToken(authString);
		// System.out.println("antes de delete token: " + u.getToken());
		try {
			userDao.logout(authString);
			// System.out.println("depois de delete token: " + u.getToken());
			return true;
		} catch (Exception e) {
			System.out.println("ocorreu um erro ao eliminar o token na BD");
			e.printStackTrace();
			return false;
		}
	}

	
	
	/////////////////////////////////////////////////////////////////////////////
	// **** SERVIÇOS DE UTILIZADOR **********
	///////////	////////////////////////////////////////////////////////////////
	/**
	 * Método que cria um Utilizador na Base de Dados, caso o seu username já não
	 * exista na lista de utilizadores registado
	 * 
	 * @param newUser Utilizador que se pretende criar
	 * @return
	 */
	public boolean createUser(UserDTORegister newUser) {
		if (getUserEntitybyEmail(newUser.getEmail()) == null) { //se não houver mais ninguém com este email registado continua o registo
			User userToPersist = UserDao.convertDTORegisterToEntity(newUser);
			userDao.persist(userToPersist);
			dashboardService.updateGeneralDashboard();
			return true;
		} else {
			System.out.println("O email ja existe na BD");
			return false;
		}
	}

	
	
	/**
	 * Guarda novos detalhes de um utilizador na Base de dados
	 * @return
	 */
	public boolean updateUser(int userId, UserDTO changedUser, boolean hasAdminPriv, String authstring) {

		User user = getNonDeletedUserEntityById(userId);
		User lastModifBy = getNonDeletedEntityByToken(authstring);

		 try {
			user= UserDao.convertDTOtoEntity(changedUser, user,hasAdminPriv,null,lastModifBy);

			System.out.println("changedUser info: " + user);
			userDao.merge(user); // guarda na BD
			dashboardService.updateGeneralDashboard();
			return true;
		
		} catch (Exception e) {
			System.out.println("não foi possível atulalizar info do user "+ userId);
			e.printStackTrace();
			return false;
		}
	}

	
	/**
	 * Guarda nova password de um utilizador na Base de dados
	 * @return
	 */
	public boolean updateUserPassword(int userId, String password, boolean hasAdminPriv,String authstring) {

		User user = getNonDeletedUserEntityById(userId);
		User lastModifBy = getNonDeletedEntityByToken(authstring);

		 try {
			user= UserDao.convertDTOtoEntity(null, user,hasAdminPriv, password,lastModifBy);

			System.out.println("changedUser info: " + user);
			userDao.merge(user); // guarda na BD
			return true;
		
		} catch (Exception e) {
			System.out.println("não foi possível atulalizar info do user "+ userId);
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * Método que no caso em que o utilizador não esteja marcado para eliminar, marca-o para eliminar, (FUNCIONALIDADE SUSPENSA: caso contrário elimina-o da Base de dados)
	 * @return
	 */
	public boolean deleteUser(int userID,String authString) {
		try {
			User user = userDao.find(userID);
			User loggedUser =getNonDeletedEntityByToken(authString);
			if (user.isDeleted()) {
				System.out.println("nesta fase, não faz nada. nao permitimos delete definitivo da base de dados");
//				userDao.deleteById(username); // este delete vai remover o conteudo associado ao user - REMOVER OS CASCADES?!?
//				dashboardService.updateGeneralDashboard();
				return false;		
			} else {
				userDao.blockUser(userID,loggedUser); //passa viwer, fica sem token e fica marcado como deleted na BD
			//	userDao.markAsDeleted(userID); // fica marcado como deleted na BD
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
	 * Método que permite desmarcar de eliminar de um utilizador
	 * @return
	 */
	public boolean undeletedUser(int userID, String authString) {
		try {
			User user = userDao.find(userID);
			User loggedUser =getNonDeletedEntityByToken(authString);
			if (user.isDeleted()) { // se estiver marcado como deleted coloca o delete a false	
				userDao.unblockUser(userID,loggedUser);
				System.out.println("vou chamar o update do dashboard geral ");
				dashboardService.updateGeneralDashboard();
				System.out.println("o update do dashboard geral foi chamado");
			//	userDao.markAsNonDeleted(userID);
				return true;
			} else { // se não estiver marcado como delete não faz nada;
				System.out.println("o user nao estava marcado para apagar");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ocorreu algum problema a procurar por user na BD - user não existe?");
			return false;
		}
	}
	
	


	/**
	 * Método que permite alterar priv de um utilizador a membro 
		 * @return
	 */
	public boolean changeUserPrivToMember (int userID,String authString) {
		try {
			User user = userDao.find(userID);
			User loggedUser =getNonDeletedEntityByToken(authString);
			System.out.println("o user logado" + loggedUser);
			if (!user.isDeleted()) {
				userDao.changeUserPrivToMember(userID,loggedUser);
				dashboardService.updateGeneralDashboard();
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ocorreu algum problema a procurar por user na BD - user não existe?");
			return false;
		}
	}
	
	/**
	 * Método que permite alterar priv de um utilizador a admin 
	 * @return
	 */
	public boolean changeUserPrivToAdmin(int userID, String authString) {
		try {
			User user = userDao.find(userID);
			User loggedUser =getNonDeletedEntityByToken(authString);
			if (!user.isDeleted()) {
			userDao.changeUserPrivToAdmin(userID,loggedUser);
			dashboardService.updateGeneralDashboard();
			return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ocorreu algum problema a procurar por user na BD - user não existe?");
			return false;
		}
	}
	
	
	
	
//	/////////////////////////////////////////////////////////////////////////////
//	// *********INFORMAÇÂO PARA DASHBOARD**********
//	/////////////////////////////////////////////////////////////////////////////
//	
//	/**
//	 * Devolve p número de utilizadores registados na Base de dados
//	 * @return
//	 */
//	public int getTotalUsers() {
//		try { 
//			System.out.println("ok total utilizadores no serviço " );
//			return userDao.getTotalUsers();
//					
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("ocorreu algum problema a procurar por user na BD -  get total users no serviço");
//			return 0;
//		}
//	}
	
}
