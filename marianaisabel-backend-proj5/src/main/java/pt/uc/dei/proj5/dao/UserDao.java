package pt.uc.dei.proj5.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import pt.uc.dei.proj5.dto.UserDTO;
import pt.uc.dei.proj5.dto.UserDTOResp;
import pt.uc.dei.proj5.entity.User;



@Stateless
public class UserDao extends AbstractDao<User> {
	private static final long serialVersionUID = 1L;

	public UserDao() {
		super(User.class);
	}
	
	
	/////////////////////////////////////////////////////////
	//METODOS ESTATICOS DE CONVERSAO ENTRE ENTIDADE E DTOs
	////////////////////////////////////////////////////////
	
	/**
	 *Função para converter um DTO para uma Entidade de utilizador
	 * @return
	 */
	public static User convertDTOtoEntity(UserDTO userDTO) {
		
		System.out.println("Entrei em converter user dto para entidade");
		
		User userEntity = new User();
		
		userEntity.setEmail(userDTO.getEmail());
		userEntity.setFirstName(userDTO.getFirstName());
		userEntity.setLastName(userDTO.getLastName());
		userEntity.setImage(userDTO.getImage());
		userEntity.setPassword(userDTO.getPassword());
		return userEntity;
		
	}
	
	/**
	 *Função para converter uma Entidade para um DTO de utilizador
	 * @return
	 */
	public static UserDTO convertEntitytoDTO(User userEntity) {
		
		UserDTO userDTO = new UserDTO();
		
		userDTO.setEmail(userEntity.getEmail());
		userDTO.setFirstName(userEntity.getFirstName());
		userDTO.setLastName(userEntity.getLastName());
		userDTO.setImage(userEntity.getImage());
		userDTO.setPassword(userEntity.getPassword());

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
		return userDTOResp;
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
			e.printStackTrace();
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
	public void deleteValueOfCurrentToken(String token) {
		try {
			System.out.println("deleting token");
			final CriteriaUpdate<User> criteriaUpdate = em.getCriteriaBuilder().createCriteriaUpdate(User.class);
			
			Root<User> c= criteriaUpdate.from(User.class);
			
			criteriaUpdate.set("token",null);
			criteriaUpdate.where(em.getCriteriaBuilder().equal(c.get("token"),token));
			
			em.createQuery(criteriaUpdate).executeUpdate();
			
			}catch(EJBException e) {
			e.printStackTrace();
			
		}
	}
	
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