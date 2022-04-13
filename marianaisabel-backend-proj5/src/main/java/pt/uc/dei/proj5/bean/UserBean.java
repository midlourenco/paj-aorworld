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
	//@Inject
	//private DashboardBean dashboardService;

	
	/////////////////////////////////////////////////////////////////////////////
	// *************GETTERS de USERS*************
	/////////////////////////////////////////////////////////////////////////////
	
	//get do user by token, by email, by id, 
	//get do token

	
	/**
	 * Devolve um utilizador a partir do seu token
	 * @param authString
	 * @return
	 */
	public User getUserEntityByToken(String authString) {
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
	 * 
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
	 * devolve um utilizador DTO a partir do seu id quer ele esteja marcado para apagar ou não
	 * @param userID
	 * @return
	 */
	public UserDTO getUserDTOById(int userID) {
		
		try {
			UserDTO userDto = UserDao.convertEntitytoDTO(userDao.find(userID));
			System.out.println("dentro do user do serviço " + userID + " e o user respectivo " + userDto);
			return userDto;
			
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
					if (user.getPassword().equals(password)) {
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
					
				}else {
					// o email é de um registo que ainda está para ser aprovado por um admin
					System.out.println("ainda não tem registo aprovado por admin ");
					return false;
				}
				
			} else {
				// nao existe username
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
		User user = userDao.findUserByToken(authString);
		if(user.getPrivileges()==User.UserPriv.ADMIN) {
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
		String loggedUserToken = getNonDeletedUserEntityById(userID).getToken(); 
		
		//compara-se o token de quem está logado com o do serviço que se está a aceder:
		if (!authString.equals(loggedUserToken)) {
			return false;
		} else {
			return true;
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
			userDao.deleteValueOfCurrentToken(authString);
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
			//dashboardService.updateGeneralDashboard();
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
	public boolean updateUser(int userId, UserDTO changedUser) {

		User uTmp = getNonDeletedUserEntityById(userId);

		 try {
			uTmp.setFirstName(changedUser.getFirstName());
			uTmp.setLastName(changedUser.getLastName());
			uTmp.setEmail(changedUser.getEmail());
			uTmp.setImage(changedUser.getImage());
			//uTmp.setPrivileges(changedUser.getPrivileges());

			System.out.println("changedUser info2: " + uTmp);
			userDao.merge(uTmp); // guarda na BD
			return true;
		
		} catch (Exception e) {
			System.out.println("não foi possível atulalizar info do user "+ userId);
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método que no caso em que o utilizador não esteja marcado para eliminar, marca-o para eliminar, caso contrário elimina-o da Base de dados
	 * @return
	 */
	public boolean deleteUser(int userID) {
		try {
			User user = userDao.find(userID);
			if (user.isDeleted()) {
				userDao.deleteById(userID);
				//dashboardService.updateGeneralDashboard();
			} else {
				userDao.markedAsDeleted(userID);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ocorreu algum problema a procurar por user na BD - actividade não existe?");
			return false;
		}
	}

	/**
	 * Método que permite desmarcar de eliminar de um utilizador
	 * @return
	 */
	public boolean undeletedUser(int userID) {
		try {
			User user = userDao.find(userID);
			if (user.isDeleted()) { // se estiver marcado como deleted coloca o delete a false
				userDao.markedAsNonDeleted(userID);
				return true;
			} else { // se não estiver marcado como delete não faz nada;
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ocorreu algum problema a procurar por user na BD - actividade não existe?");
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
