package pt.uc.dei.proj5.bean;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import pt.uc.dei.proj5.dto.UserDTO;
import pt.uc.dei.proj5.entity.User;

//@Startup
//@Singleton
@ApplicationScoped
public class InitialDataBean {
	@Inject
	UserBean userService;
	
	@PostConstruct
	public void incializa() {
		//UserBean userService= new UserBean ();
		System.out.println("postConstruct para criaçao de admin: ");
		// TODO ver como é que se vê o tamanho da BD
		
		if(userService.getUserEntitybyEmail("admin@aor.pt")==null) {
			System.out.println("não existe user admin");
			UserDTO admin= new UserDTO("root_admin", "ultimo nome", "admin", "admin@aor.pt", "https://png.pngitem.com/pimgs/s/24-245629_awesome-face-pose-5-2017-stay-calm-and.png","eu sou o admin construido por defeito na BD");
			userService.createUser(admin);
		}
	}
	

}
