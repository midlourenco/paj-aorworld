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

import java.sql.Timestamp;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

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
	}
	
	
	
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
	

	/**
	 * neste método verificamos se o persist do user é chamado e se sequentemente o dashboard update é chamado
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
	
}
