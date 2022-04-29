package pt.uc.dei.proj5.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import pt.uc.dei.proj5.bean.DashboardBean;
import pt.uc.dei.proj5.bean.UserBean;
import pt.uc.dei.proj5.other.GestaoErros;


@Path("dashboard")
public class DashboardController {

	@Inject
	private DashboardBean dashboardService;
	@Inject
	private GestaoErros gestaoErros;

	@Inject
	private UserBean userService;

	
	//Dxx. todas as estatisticas
	@GET
	@Path("allStats")
	@Produces(MediaType.APPLICATION_JSON)
public Response getAllStats(@HeaderParam("Authorization") String authString) {
	System.out.println("get all stats " );
	
	try {
//		if (authString.equals("") || authString.isEmpty() || authString == null|| !userService.isValidToken(authString)) {// está logado mas o token não é válido
//			System.out.println("token não existe ou não é válido" );
//			return Response.status(401).entity(GestaoErros.getMsg(1)).build();
//		} 
//		if (!userService.hasLoggedUserAdminPriv(authString)) {
//			System.out.println("não tem priv de admin" );
//			return Response.status(403).entity(GestaoErros.getMsg(5)).build();
//		}
		if(dashboardService.validateTokenForDashboardAcess(authString)) {
//		
			String allstats = dashboardService.updateGeneralDashboard();

			System.out.println("stats actuais" + allstats);
		
			return Response.ok(allstats).build();
		}else {
			return Response.status(403).build();
		}
	} catch (NullPointerException e) { // caso não tenha Authorization no header
		System.out.println("erro no get total utilizadores controller " );
		e.printStackTrace();
		return Response.status(401).entity(GestaoErros.getMsg(1)).build();
	}		
	
}
}