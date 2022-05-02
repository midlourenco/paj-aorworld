package pt.uc.dei.proj5.bean;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import java.sql.Timestamp;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import pt.uc.dei.proj5.dao.UserDao;
import pt.uc.dei.proj5.dto.UserDTORegister;
import pt.uc.dei.proj5.entity.User;
import pt.uc.dei.proj5.entity.User.UserPriv;


@RunWith(MockitoJUnitRunner.class)
public class UserBeanTest {

//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}
	
	private Validator validator;
	@Mock
	private UserDao userDao;
	@Mock
	private DashboardBean dashboardService;
	@Captor
	ArgumentCaptor<User> userCaptor;
	
	@InjectMocks
	UserBean userServTester = new UserBean();
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
	}
  
	
	//1
	/**
	 * neste método verificamos se o persist do user é chamado e se sequentemente o dashboard update é chamado
	 */
	@Test
	public void testPersistWithSuccess() {
		UserDTORegister user = new UserDTORegister();
		user.setFirstName("Mario");
		user.setLastName("Silva");
		user.setEmail("mariosilva@aor.pt");
		user.setBiography("Esta é a minha bio");
		user.setImage("http://urlrandom.pt");
		user.setPassword("teste123");
		user.setAutoAcceptInvites(true);
		
		

		userServTester.createUser(user);
		verify(userDao, times(1)).persist(userCaptor.capture());
		verify(dashboardService, times(1)).updateGeneralDashboard();
	
	}

	//2
	/**
	 * neste método verificamos se após o persist do user a informação que é guardada na entidade é igual há que passámos
	 * bem como se a informação default é a que esperamos que seja
	 */
	@Test
	public void testPersistedInfoAsExpected() {
		UserDTORegister user = new UserDTORegister();
		user.setFirstName("Mario");
		user.setLastName("Silva");
		user.setEmail("mariosilva@aor.pt");
		user.setBiography("Esta é a minha bio");
		user.setImage("http://urlrandom.pt");
		user.setPassword("teste123");
		user.setAutoAcceptInvites(true);
		
		userServTester.createUser(user);
		verify(userDao).persist(userCaptor.capture());
		assertNotEquals(user.getPassword(), userCaptor.getValue().getPassword());	
		assertEquals(user.getFirstName(), userCaptor.getValue().getFirstName());		
		assertEquals(user.getLastName(), userCaptor.getValue().getLastName());
		assertEquals(user.getImage(),userCaptor.getValue().getImage());	
		assertEquals(user.getBiography(), userCaptor.getValue().getBiography());
		
		assertTrue( "a password não é guardada na base de dados em pleno text",!userCaptor.getValue().getPassword().equals(user.getPassword()));
		//assertTrue( "a data de criação é a data atual??? nao é nula",userCaptor.getValue().getCreatedDate()!=null );
		//assertTrue( "foi gerado um id para user",userCaptor.getValue().getId()>0);
		assertTrue( "a data de modificação é nula",userCaptor.getValue().getLastModifDate()  ==null);
		assertTrue( "o user criado não é tem a flag de autoAdmin",userCaptor.getValue().isAutoAdmin()  ==false);
		assertTrue( "o user criado tema  a flag de deleted a false",userCaptor.getValue().isDeleted()  ==false);
		assertTrue( "o user criado não tem nenhum token na base de dados",userCaptor.getValue().getToken()  ==null);
		assertTrue( "o user criado tem privilégios de viewer",userCaptor.getValue().getPrivileges()  ==UserPriv.VIEWER);
		
//		  User u =null;
//		  u.getLastModifDate();
//	
	}
	
	//3
	/**
	 * neste método verificamos se a tentativa de registar um email já existente falha e como tal o persist e atualizaçao do dashboard nao chegam a correr
	 */
	@Test
	public void testPersistEmailAlreadyExistent() {
		UserDTORegister user = new UserDTORegister();
		user.setFirstName("Mario");
		user.setLastName("Silva");
		user.setEmail("mariosilva@aor.pt");
		user.setBiography("Esta é a minha bio");
		user.setImage("http://urlrandom.pt");
		user.setPassword("teste123");
		user.setAutoAcceptInvites(true);
		
		User userEntity = new User();

		when(userServTester.getUserEntitybyEmail("mariosilva@aor.pt")).thenReturn(userEntity);
		assertFalse("não consegue criar utilizador com um email existente", userServTester.createUser(user));
		
		verify(userDao, never()).persist(userCaptor.capture());
		verify(dashboardService, never()).updateGeneralDashboard();
	
	}
	
	
	//4
	/**
	 * neste método verificamos se após fazer o registo se o user existe na base de dados e se conseguimos fazer login/validar credenciais (sem que o perfil tenha sido aprovado por ninguem)
	 */
	@Test
	public void testLoginAfterRegister() {
		UserDTORegister user = new UserDTORegister();
		user.setFirstName("Mario");
		user.setLastName("Silva");
		user.setEmail("mariosilva@aor.pt");
		user.setBiography("Esta é a minha bio");
		user.setImage("http://urlrandom.pt");
		user.setPassword("teste123");
		user.setAutoAcceptInvites(true);
		
		userServTester.createUser(user);
		verify(userDao).persist(userCaptor.capture());
		assertTrue("o user criado existe na base dados", userDao.findUserByEmail(user.getEmail())==(userCaptor.capture()));
		assertFalse("não se consegue validar as credênciais de unm user acabado de criar porque ainda não foi aprovado", userServTester.validAutentication(user.getEmail(), user.getPassword()));
	}
	
	//(expected = ConstraintViolationException.class)
	//https://stackoverflow.com/questions/24386771/javax-validation-validationexception-hv000183-unable-to-load-javax-el-express
	
	//5
	/**
	 * Verificamos se conseguimos fazer um registo sem os campos obrigatorios preenchidos
	 */	
	@Test
	public void testRegisterWithMissingInfo() {
		UserDTORegister user = new UserDTORegister();
		user.setFirstName("");
		user.setLastName("Silva");
		user.setEmail("mariosilva2@aor.pt");
		user.setBiography("Esta é a minha bio");
		user.setImage("http://urlrandom.pt");
		user.setPassword("teste123");
		user.setAutoAcceptInvites(true);
		
		userServTester.createUser(user);
//		verify(userDao, never()).persist(userCaptor.capture());
//		verify(dashboardService, never()).updateGeneralDashboard();	
		 Set<ConstraintViolation<UserDTORegister>> violations = validator.validate(user);
	     assertFalse(violations.isEmpty());
	     
	     

	     
	
	     //sem o campo do email

	 	UserDTORegister user5 = new UserDTORegister();
		user5.setFirstName("Mario");
		user5.setLastName("Silva");

		user5.setBiography("Esta é a minha bio");
		user5.setImage("http://urlrandom.pt");
		user5.setPassword("teste123");
		user5.setAutoAcceptInvites(true);

		userServTester.createUser(user5);
//				verify(userDao, never()).persist(userCaptor.capture());
//				verify(dashboardService, never()).updateGeneralDashboard();	
		 Set<ConstraintViolation<UserDTORegister>> violations5 = validator.validate(user5);
	     assertFalse(violations5.isEmpty());
 
	}
	
	//6
	/**
	 * Verificamos se conseguimos fazer um registo com password a null
	 */	
	@Test
	public void testRegisterWithBlankPassword() {
		UserDTORegister user = new UserDTORegister();
		user.setFirstName("Mario");
		user.setLastName("Silva");
		user.setEmail("mariosilva2@aor.pt");
		user.setBiography("Esta é a minha bio");
		user.setImage("http://urlrandom.pt");
		user.setPassword(" ");
		user.setAutoAcceptInvites(true);
		
		 Set<ConstraintViolation<UserDTORegister>> violations = validator.validate(user);
	     assertFalse(violations.isEmpty());
	}
	
	//7
	/**
	 * Verificamos se conseguimos fazer um registo com apenas uma string normal no email (sem @ nem pontos) 
	 */	
	@Test
	public void testRegisterWithNotEmail() {
	     //email mal formatado
	 	UserDTORegister user = new UserDTORegister();
		user.setFirstName("Mario");
		user.setLastName("Silva");
		user.setEmail("mariosilva");
		user.setBiography("Esta é a minha bio");
		user.setImage("http://urlrandom.pt");
		user.setPassword("teste123");
		user.setAutoAcceptInvites(true);
		
		userServTester.createUser(user);
//		verify(userDao, never()).persist(userCaptor.capture());
//		verify(dashboardService, never()).updateGeneralDashboard();	
		 Set<ConstraintViolation<UserDTORegister>> violations = validator.validate(user);
	     assertFalse(violations.isEmpty());
	}
	
}
