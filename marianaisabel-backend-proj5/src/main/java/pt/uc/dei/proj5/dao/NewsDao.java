package pt.uc.dei.proj5.dao;

import javax.ejb.Stateless;

import pt.uc.dei.proj5.dto.NewsDTO;
import pt.uc.dei.proj5.dto.NewsDTOResp;
import pt.uc.dei.proj5.entity.News;
import pt.uc.dei.proj5.entity.User;
@Stateless
public class NewsDao {

	public static News convertDTOToEntity(NewsDTO newsDTO, User createdBy, User lastModifdBy ) {
		System.out.println("Entrei em convertDTOToEntity Project");
		News newsEntity = new News();
		//TODO complete here
		
		
		
		return newsEntity;
	}

	public static NewsDTO convertEntityToDTO(News newsEntity) {
		NewsDTO newsDTO = new NewsDTO();
		//TODO complete here
		
		
		
		return newsDTO;
	}

	public static NewsDTOResp convertEntityToDTOResp(News newsEntity) {
		NewsDTOResp newsDTOResp = new NewsDTOResp();
	
		//TODO complete here
		
		return newsDTOResp;
	}

	
	
}
