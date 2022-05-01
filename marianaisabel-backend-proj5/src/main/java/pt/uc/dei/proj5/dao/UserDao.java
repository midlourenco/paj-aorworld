package pt.uc.dei.proj5.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import pt.uc.dei.proj5.dto.UserDTO;
import pt.uc.dei.proj5.dto.UserDTORegister;
import pt.uc.dei.proj5.dto.UserDTOResp;
import pt.uc.dei.proj5.dto.UserDTORespSharingProject;
import pt.uc.dei.proj5.entity.User;
import pt.uc.dei.proj5.entity.User.UserPriv;



@Stateless
public class UserDao extends AbstractDao<User> {
	private static final long serialVersionUID = 1L;

	public UserDao() {
		super(User.class);
	}
	
	
	/////////////////////////////////////////////////////////
	//METODOS ESTATICOS DE CONVERSAO ENTRE ENTIDADE E DTOs
	////////////////////////////////////////////////////////
	// REGISTER
	/**
	 *Função para converter um DTO para uma Entidade de utilizador
	 * @return
	 */
	public static User convertDTORegisterToEntity(UserDTORegister userDTOReg) {
		System.out.println("Entrei em converter user dto registo para entidade");
		User userEntity = new User();
		userEntity.setEmail(userDTOReg.getEmail());
		//userEntity.setPassword(userDTOReg.getPassword());
		userEntity.setPassword(userDTOReg.getPassword());
		userEntity.setFirstName(userDTOReg.getFirstName());
		userEntity.setLastName(userDTOReg.getLastName());
		userEntity.setImage(userDTOReg.getImage());
		userEntity.setBiography(userDTOReg.getBiography());
		userEntity.setAutoAcceptInvites(userDTOReg.isAutoAcceptInvites());
		
		userEntity.setPrivileges(UserPriv.VIEWER);
		userEntity.setDeleted(false);
		//a data de criação fica preenchida pela base de dados no moemnto de criação
		return userEntity;
	}
	
	//UPDATE
	//aqui não temos quando foi alterado pela ultima vez nem  por quem??
	/**
	 *Função para converter um user DTO para uma Entidade (pre-existente) de utilizador
	 * @return
	 */
	public static User convertDTOtoEntity(UserDTO userDTO, User userEntity, boolean hasAdminPriv,String password, User lastModifBy) {
		System.out.println("Entrei em converter user dto para entidade");
		if(userDTO!=null) {
			userEntity.setEmail(userDTO.getEmail());
			userEntity.setFirstName(userDTO.getFirstName());
			userEntity.setLastName(userDTO.getLastName());
			userEntity.setImage(userDTO.getImage());
			userEntity.setBiography(userDTO.getBiography());
			if(hasAdminPriv) {
				userEntity.setPrivileges(userDTO.getPrivileges());
			}
		}
		if(password!=null) {
			userEntity.setPassword(password);
		}
		userEntity.setLastModifByAndDate(lastModifBy);
	//	userEntity.setLastModifDate(new Timestamp(System.currentTimeMillis()));
		System.out.println(userEntity.getLastModifDate());
		return userEntity;
	}
	
	
	/**
	 *Função para converter uma Entidade para um DTO de utilizador
	 * @return
	 * @deprecated
	 */
	public static UserDTO convertEntitytoDTO(User userEntity) {
		
		UserDTO userDTO = new UserDTO();
		
		userDTO.setEmail(userEntity.getEmail());
		userDTO.setFirstName(userEntity.getFirstName());
		userDTO.setLastName(userEntity.getLastName());
		userDTO.setImage(userEntity.getImage());


		return userDTO;
	}

	/**
	 *Função para converter uma Entidade para um DTOResp de utilizador
	 * @return
	 */
	public static UserDTOResp convertEntitytoDTOResp(User userEntity) {
		
		UserDTOResp userDTOResp = new UserDTOResp();
		userDTOResp.setEmail(userEntity.getEmail());
		userDTOResp.setFirstName(userEntity.getFirstName());
		userDTOResp.setLastName(userEntity.getLastName());
		userDTOResp.setImage(userEntity.getImage());
		userDTOResp.setBiography(userEntity.getBiography());
		userDTOResp.setPrivileges(userEntity.getPrivileges());
		userDTOResp.setId(userEntity.getId());
		userDTOResp.setDeleted(userEntity.isDeleted());
		return userDTOResp;
	}
	
	
	
	

	/**
	 *Função para converter uma Entidade para um DTOResp de utilizador
	 * @return
	 */
	public static UserDTORespSharingProject convertEntitytoDTORespSharingProject(User userEntity,String userRoleInProject) {
		
		UserDTORespSharingProject userDTORespSharingProject = new UserDTORespSharingProject();
		userDTORespSharingProject.setEmail(userEntity.getEmail());
		userDTORespSharingProject.setFirstName(userEntity.getFirstName());
		userDTORespSharingProject.setLastName(userEntity.getLastName());
		userDTORespSharingProject.setImage(userEntity.getImage());
		userDTORespSharingProject.setBiography(userEntity.getBiography());
		userDTORespSharingProject.setPrivileges(userEntity.getPrivileges());
		userDTORespSharingProject.setId(userEntity.getId());
		userDTORespSharingProject.setDeleted(userEntity.isDeleted());
		userDTORespSharingProject.setUserRoleInProject(userRoleInProject);
		return userDTORespSharingProject;
	}
	
	

	/**
	 *Função que devolve um utilizador a partir de um determinado token que nao esteja marcado para eliminar
	 * @return
	 */
	public User findUserByToken(String token) {
		try {
			final CriteriaQuery<User> criteriaQuery = em.getCriteriaBuilder().createQuery(User.class);
			Root<User> c= criteriaQuery.from(User.class);
			criteriaQuery.select(c).where(em.getCriteriaBuilder().and(
					em.getCriteriaBuilder().equal(c.get("deleted"),false),
					em.getCriteriaBuilder().equal(c.get("token"),token)));
			
			return em.createQuery(criteriaQuery).getSingleResult();
		}catch(EJBException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 *Função que devolve um utilizador a partir de um determinado token que nao esteja marcado para eliminar
	 * @return
	 */
	public boolean existAutoAdmin() {
		try {
			final CriteriaQuery<User> criteriaQuery  = em.getCriteriaBuilder().createQuery(User.class);
			Root<User> c= criteriaQuery.from(User.class);
			criteriaQuery.select(c).where(em.getCriteriaBuilder().equal(c.get("autoAdmin"),true));
			
			if( em.createQuery(criteriaQuery).getSingleResult()!=null) {
				return true;
			}else {
				return false;
			}
		}catch(EJBException e) {
			System.out.println("não há nenhum utilizador na BD com este atributo: ");
			e.printStackTrace();
			return false;
		}catch (Exception e) {
			System.out.println("não há nenhum utilizador na BD com este atributo: ");
			return false;
		}
	}
	/**
	 *Função que devolve um utilizador a partir de um determinado email (pretende-se marcado para eliminar ou não)
	 * @return
	 */
	public User findUserByEmail(String email) {
		try {
			final CriteriaQuery<User> criteriaQuery = em.getCriteriaBuilder().createQuery(User.class);
			Root<User> c= criteriaQuery.from(User.class);
			criteriaQuery.select(c).where(em.getCriteriaBuilder().equal(c.get("email"),email));
			
			return em.createQuery(criteriaQuery).getSingleResult();
		}catch(EJBException e) {
			System.out.println("não há nenhum utilizador na BD com este email: " + email);
			//e.printStackTrace();
			return null;
		}catch (Exception e) {
			System.out.println("não há nenhum utilizador na BD com este email: "+ email);
			return null;
		}
	}
	
	/**
	 *Função que devolve um utilizador a partir de um determinado email (pretende-se marcado para eliminar ou não)
	 * @return
	 */
	public User findNonDeletedUserByEmail(String email) {
		try {
			final CriteriaQuery<User> criteriaQuery = em.getCriteriaBuilder().createQuery(User.class);
			Root<User> c= criteriaQuery.from(User.class);
			criteriaQuery.select(c).where(em.getCriteriaBuilder().and(
					em.getCriteriaBuilder().equal(c.get("deleted"),false),
					em.getCriteriaBuilder().equal(c.get("email"),email)));
			
			return em.createQuery(criteriaQuery).getSingleResult();
		}catch(EJBException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Função que devolve todos os utilizadores não marcados para apagar que tenham privilégios de administrador
	 * @return
	 */
	public List<User> findAllUserOfOrganization(){
		try {
			final CriteriaQuery<User> criteriaQuery = em.getCriteriaBuilder().createQuery(User.class);
	
			Root<User> c= criteriaQuery.from(User.class);
			
			criteriaQuery.select(c).where(em.getCriteriaBuilder().and(
					em.getCriteriaBuilder().equal(c.get("deleted"),false),
					em.getCriteriaBuilder().or(
							em.getCriteriaBuilder().equal(c.get("privileges"),User.UserPriv.ADMIN),
							em.getCriteriaBuilder().equal(c.get("privileges"),User.UserPriv.MEMBER)
							)));
			
			return em.createQuery(criteriaQuery).getResultList();
			
		}catch(EJBException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Função que devolve todos os utilizadores não marcados para apagar que tenham privilégios de administrador
	 * @return
	 */
	public List<User> findAllAdmins(){
		try {
			final CriteriaQuery<User> criteriaQuery = em.getCriteriaBuilder().createQuery(User.class);
	
			Root<User> c= criteriaQuery.from(User.class);
			
			criteriaQuery.select(c).where(em.getCriteriaBuilder().and(
					em.getCriteriaBuilder().equal(c.get("deleted"),false),
					em.getCriteriaBuilder().equal(c.get("privileges"),User.UserPriv.ADMIN)));
			
			return em.createQuery(criteriaQuery).getResultList();
			
		}catch(EJBException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Função que devolve todos os utilizadores não marcados para apagar que tenham privilégios de membro
	 * @return
	 */
	public List<User> findAllMembers(){
		try {
			final CriteriaQuery<User> criteriaQuery = em.getCriteriaBuilder().createQuery(User.class);
	
			Root<User> c= criteriaQuery.from(User.class);
			
			criteriaQuery.select(c).where(em.getCriteriaBuilder().and(
					em.getCriteriaBuilder().equal(c.get("deleted"),false),
					em.getCriteriaBuilder().equal(c.get("privileges"),User.UserPriv.MEMBER)));
			
			return em.createQuery(criteriaQuery).getResultList();
			
		}catch(EJBException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Função que devolve todos os utilizadores não marcados para apagar que tenham não tenham privilégios de administrador nem membros
	 * @return
	 */
	public List<User> findAllViewersToAprove(){
		try {
			final CriteriaQuery<User> criteriaQuery = em.getCriteriaBuilder().createQuery(User.class);
	
			Root<User> c= criteriaQuery.from(User.class);
			
			criteriaQuery.select(c).where(em.getCriteriaBuilder().and(
					em.getCriteriaBuilder().equal(c.get("deleted"),false),
					em.getCriteriaBuilder().equal(c.get("privileges"),User.UserPriv.VIEWER))); 
			//os viewers com deleted a true, são viewers que se decidiram não aprovar, ou outros membros e admin despromovido a este estado
			
			return em.createQuery(criteriaQuery).getResultList();
			
		}catch(EJBException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 *Função que coloca um determinado token a null na base de dados
	 * @param token
	 * @return
	 */
	public void logout(String token) {
		try {
			System.out.println("deleting token");
			final CriteriaUpdate<User> criteriaUpdate = em.getCriteriaBuilder().createCriteriaUpdate(User.class);
			Root<User> c= criteriaUpdate.from(User.class);
			criteriaUpdate.set("token",null);
			criteriaUpdate.set("lastLogoutDate",new Timestamp(System.currentTimeMillis()));
			criteriaUpdate.where(em.getCriteriaBuilder().equal(c.get("token"),token));
			
			em.createQuery(criteriaUpdate).executeUpdate();
			
			}catch(EJBException e) {
			e.printStackTrace();
			
		}
	}
	
	
	

	/**
	 * Bloquear (despromover ou não aceitar) um user: alterar os privilégios para viewer, marcar como apagado, retirar token
	 * @param id
	 */
	public void blockUser(Object id, User lastModifBy) {
		try {
			System.out.println("entrei em blockUser e o id é :" + id);

			final CriteriaUpdate<User> criteriaUpdate = em.getCriteriaBuilder().createCriteriaUpdate(User.class);

			Root<User> c = criteriaUpdate.from(User.class);
			criteriaUpdate.set("deleted", true);
			criteriaUpdate.set("privileges", UserPriv.VIEWER);
			criteriaUpdate.set("token", null);
			criteriaUpdate.set("lastModifBy", lastModifBy);
			criteriaUpdate.set("lastModifDate",  new Timestamp(System.currentTimeMillis()));
			criteriaUpdate.where(em.getCriteriaBuilder().equal(c.get("id"), id));

			em.createQuery(criteriaUpdate).executeUpdate();

		} catch (EJBException e) {
			e.printStackTrace();
		}
	}
		
	
	/**
	 * Desbloquear (estado de registo para aprovação) um user: alterar os privilégios para viewer, desmarcar como apagado, retirar token
	 * @param id
	 */
	public void unblockUser(Object id, User lastModifBy) {
		try {
			System.out.println("entrei em blockUser e o id é :" + id);

			final CriteriaUpdate<User> criteriaUpdate = em.getCriteriaBuilder().createCriteriaUpdate(User.class);

			Root<User> c = criteriaUpdate.from(User.class);
			criteriaUpdate.set("deleted", false);
			criteriaUpdate.set("privileges", UserPriv.VIEWER);
			criteriaUpdate.set("token", null);
			criteriaUpdate.set("lastModifBy", lastModifBy);
			criteriaUpdate.set("lastModifDate",  new Timestamp(System.currentTimeMillis()));
			criteriaUpdate.where(em.getCriteriaBuilder().equal(c.get("id"), id));

			em.createQuery(criteriaUpdate).executeUpdate();

		} catch (EJBException e) {
			e.printStackTrace();
		}
	}
		
	
	
	/**
	 * alterar privilégios de um utilizador para membro
	 * @param id
	 */
		public void changeUserPrivToMember(Object id, User lastModifBy) {
			try {
				System.out.println("entrei em promoteUserToMember e o id é :" + id);

				final CriteriaUpdate<User> criteriaUpdate = em.getCriteriaBuilder().createCriteriaUpdate(User.class);

				Root<User> c = criteriaUpdate.from(User.class);
				//criteriaUpdate.set("deleted", false);
				criteriaUpdate.set("privileges", UserPriv.MEMBER);
				criteriaUpdate.where(em.getCriteriaBuilder().equal(c.get(getIdColumnName()), id));
				criteriaUpdate.set("lastModifBy", lastModifBy);
				criteriaUpdate.set("lastModifDate",  new Timestamp(System.currentTimeMillis()));
				em.createQuery(criteriaUpdate).executeUpdate();

			} catch (EJBException e) {
				e.printStackTrace();
			}

		}

		/**
		 * alterar privilégios de um utilizador para admin
		 * @param id
		 */
		
		public void changeUserPrivToAdmin(Object id, User lastModifBy) {
			try {
				System.out.println("entrei em promoteUserToMember e o id é :" + id);

				final CriteriaUpdate<User> criteriaUpdate = em.getCriteriaBuilder().createCriteriaUpdate(User.class);

				Root<User> c = criteriaUpdate.from(User.class);
				//criteriaUpdate.set("deleted", false);
				criteriaUpdate.set("privileges", UserPriv.ADMIN);
				criteriaUpdate.where(em.getCriteriaBuilder().equal(c.get(getIdColumnName()), id));
				criteriaUpdate.set("lastModifBy", lastModifBy);
				criteriaUpdate.set("lastModifDate",  new Timestamp(System.currentTimeMillis()));
				em.createQuery(criteriaUpdate).executeUpdate();

			} catch (EJBException e) {
				e.printStackTrace();
			}

		}
	
	///////////////////////////
	//DASHBOARD
	///////////////////////
	
	
	
	
	/**
	 * D1 número total de users;
	 * @return
	 */
	public int getTotalUsers() {
		System.out.println("entrei no get total users do dao " );
		List<User> users = findAll();

		return users.size();
	}
	
	
	/**
	 * D4 número de utilizadores registados ao longo do tempo (por dia)
	 *  Sobre Tuple: https://www.javatpoint.com/java-tuple
	 * @return
	 */
	public List<Tuple> getRegisterPerDay(){
		try {
			final CriteriaQuery<Tuple> criteriaQuery = em.getCriteriaBuilder().createTupleQuery();		
			Root<User> c= criteriaQuery.from(User.class);
		
			//Expression<Date> dateByDay = em.getCriteriaBuilder().function("TRUNC", Date.class,c.get("createDate"));
			//Expression<String> dateByDay = em.getCriteriaBuilder().function("ToDate",
			//        String.class,c.get("createDate"), em.getCriteriaBuilder().literal("yyyy-MM-dd");
		
			criteriaQuery.multiselect(em.getCriteriaBuilder().function("date", Date.class,c.get("createDate")), em.getCriteriaBuilder().count(c));
			//criteriaQuery.groupBy(c.get("createDate"));
			
			criteriaQuery.groupBy(em.getCriteriaBuilder().function("date", Date.class,c.get("createDate")));
			return em.createQuery(criteriaQuery).getResultList();
			
		}catch(EJBException e) {
			e.printStackTrace();
			return null;
		}
	}
	
//	@Override
//	public String getIdColumnName() {
//		return "email";
//	}

	
	
}