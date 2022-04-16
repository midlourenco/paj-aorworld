package pt.uc.dei.proj5.rest;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.uc.dei.proj5.bean.KeywordBean;
import pt.uc.dei.proj5.bean.ProjectBean;
import pt.uc.dei.proj5.bean.UserBean;
import pt.uc.dei.proj5.dto.NewsDTOResp;
import pt.uc.dei.proj5.dto.ProjectDTOResp;
import pt.uc.dei.proj5.other.GestaoErros;

@Path("keywords")
public class KeywordController {
	@Inject
	private GestaoErros gestaoErros;
	@Inject
	private UserBean userService;
	@Inject
	private ProjectBean projectService;
	@Inject
	private KeywordBean keywordService;
	
	
	
	
	
	// Get All project associated to a keyword
	@GET
	@Path("{keyword}/projects")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProjectAssociatedToKeyword (@PathParam("keyword") String keyword, @HeaderParam("Authorization") String authString) {
		
		System.out.println("Entrei em getAllProjectAssociatedToKeyword no controller com token? : " + authString);
		try {
			if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não está logado ou não tem token válido																				
				ArrayList<ProjectDTOResp> resultado = keywordService.getOnlyPublicProjectsAssocToKeyword(keyword);
				if (resultado != null) {
					return Response.ok(resultado).build();
				} else {
					return Response.status(400).entity((GestaoErros.getMsg(6))).build();
				}			
			}
			
			ArrayList<ProjectDTOResp>  resultado = keywordService.getAllProjectsAssocToKeyword(keyword);
			if (resultado != null) {
				return Response.ok(resultado).build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(6))).build();
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
			ArrayList<ProjectDTOResp> resultado = keywordService.getOnlyPublicProjectsAssocToKeyword(keyword);
			if (resultado != null) {
				return Response.ok(resultado).build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(6))).build();
			}
		} catch (Exception e) {
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}

	}
	
	// Get All project associated to a keyword
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllKeyword (@HeaderParam("Authorization") String authString) {
		
		System.out.println("Entrei em getAllProjectAssociatedToKeyword no controller com token? : " + authString);
		ArrayList<String> resultado = keywordService.getAllKeywords();
		
		try {
			if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não está logado ou não tem token válido																				
				if (resultado != null) {
					return Response.ok(resultado).build();
				} else {
					return Response.status(400).entity((GestaoErros.getMsg(6))).build();
				}			
			}
			if (resultado != null) {
				return Response.ok(resultado).build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(6))).build();
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
			if (resultado != null) {
				return Response.ok(resultado).build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(6))).build();
			}
		} catch (Exception e) {
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}

	}
		
	
	@GET
	@Path("{keyword}/news")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllNewsAssociatedToKeyword (@PathParam("keyword") String keyword, @HeaderParam("Authorization") String authString) {
		
		System.out.println("Entrei em getAllProjectAssociatedToKeyword no controller com token? : " + authString);
		try {
			if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não está logado ou não tem token válido																				
				ArrayList<NewsDTOResp> resultado = keywordService.getOnlyPublicNewsAssocToKeyword(keyword);
				if (resultado != null) {
					return Response.ok(resultado).build();
				} else {
					return Response.status(400).entity((GestaoErros.getMsg(6))).build();
				}			
			}
			
			ArrayList<NewsDTOResp>  resultado = keywordService.getAllNewsAssocToKeyword(keyword);
			if (resultado != null) {
				return Response.ok(resultado).build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(6))).build();
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
			ArrayList<NewsDTOResp> resultado = keywordService.getOnlyPublicNewsAssocToKeyword(keyword);
			if (resultado != null) {
				return Response.ok(resultado).build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(6))).build();
			}
		} catch (Exception e) {
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}

	}
}
