package pt.uc.dei.proj5.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import pt.uc.dei.proj5.dao.UserDao;
import pt.uc.dei.proj5.dto.UserDTO;
import pt.uc.dei.proj5.dto.UserDTORegister;
import pt.uc.dei.proj5.entity.User;
import pt.uc.dei.proj5.entity.User.UserPriv;

//@ApplicationScoped
@Startup
@Singleton
public class InitialDataBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject 
	UserDao userDao;
	
	@PostConstruct
	public void incializa() {
		//UserBean userService= new UserBean ();
		System.out.println("postConstruct para criaçao de admin: ");
		try {
		if(userDao.findUserByEmail("admin@aor.pt")==null) {
			System.out.println("não existe user admin");
			UserDTORegister admin= new UserDTORegister("root_admin", "ultimo nome", "admin", "admin@aor.pt", "https://png.pngitem.com/pimgs/s/24-245629_awesome-face-pose-5-2017-stay-calm-and.png","eu sou o admin construido por defeito na BD");
			//userService.createUser(admin);
			User userToPersist = UserDao.convertDTORegisterToEntity(admin);
			userToPersist.setPrivileges(UserPriv.ADMIN);
			userDao.persist(userToPersist);
			System.out.println("criei user admin");
			
		}
		}catch (Exception e) {
			System.out.println("houve aqui um erro ao criar user admin!!!!!!!!");
			//e.printStackTrace();
		}
	}
	

}
