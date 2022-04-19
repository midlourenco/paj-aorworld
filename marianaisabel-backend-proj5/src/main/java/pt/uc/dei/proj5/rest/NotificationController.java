package pt.uc.dei.proj5.rest;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.uc.dei.proj5.bean.NotificationBean;
import pt.uc.dei.proj5.bean.UserBean;
import pt.uc.dei.proj5.dto.NotificationDTO;
import pt.uc.dei.proj5.dto.ProjectDTO;
import pt.uc.dei.proj5.dto.ProjectDTOResp;
import pt.uc.dei.proj5.entity.User;
import pt.uc.dei.proj5.entity.User.UserPriv;
import pt.uc.dei.proj5.other.GestaoErros;



@Path("notifications")
public class NotificationController {
	@Inject
	private GestaoErros gestaoErros;
	@Inject
	private UserBean userService;
	@Inject
	private NotificationBean notificationService;
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNotificationsFromUser(@HeaderParam("Authorization") String authString) {

		System.out.println("Entrei em getNotificationsFromUser no controller com  token " + authString);

		if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não está logado ou não tem token válido																				
			return Response.status(401).entity(GestaoErros.getMsg(1)).build();
		}

		try {
			User user = userService.getNonDeletedEntityByToken(authString); // utilizador dono das notificações 
			ArrayList<NotificationDTO> resultado = notificationService.getNotificationsFromUser(user);
			if (resultado != null) {
				return Response.ok(resultado).build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(17))).build();
			}
			
	
		} catch (Exception e) {
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}

	}
	
	
	
	@GET
	@Path("lastNotifications")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNotificationsSinceLastLogoutDate(@HeaderParam("Authorization") String authString) {

		System.out.println("Entrei em  lastNotifications no controller com  token " + authString);

		if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não está logado ou não tem token válido																				
			return Response.status(401).entity(GestaoErros.getMsg(1)).build();
		}

		try {
			User user = userService.getNonDeletedEntityByToken(authString); // utilizador dono das notificações 
			ArrayList<NotificationDTO> resultado = notificationService.getNotificationsSinceLastLogoutDate(user);
			if (resultado != null) {
				return Response.ok(resultado).build();
			} else {
				return Response.status(400).entity((GestaoErros.getMsg(17))).build();
			}
	
		} catch (Exception e) {
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}
	}
	
	
	@GET
	@Path("unreadNumber")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNumberUnreadNotifFromUser(@HeaderParam("Authorization") String authString) {

		System.out.println("Entrei em  getNumberUnreadNotifFromUser no controller com  token " + authString);

		if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não está logado ou não tem token válido																				
			return Response.status(401).entity(GestaoErros.getMsg(1)).build();
		}

		try {
			User user = userService.getNonDeletedEntityByToken(authString); // utilizador dono das notificações 
			int resultado = notificationService.getNumberUnreadNotifFromUser(user);
			return Response.ok(resultado).build();
					
		} catch (Exception e) {
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}
	}
	
	@POST
	@Path("{notificationId: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response markNotifAsRead(@PathParam("notificationId") int notificationId,@HeaderParam("Authorization") String authString) {

		System.out.println("Entrei em markNotifAsRead  no controller com  token " + authString);

		if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não está logado ou não tem token válido																				
			return Response.status(401).entity(GestaoErros.getMsg(1)).build();
		}

		try {
			User user = userService.getNonDeletedEntityByToken(authString); // utilizador dono das notificações 
			if(notificationService.markAsReadNotif(notificationId, user)) {
			return Response.ok().build();
			}
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();	
		} catch (Exception e) {
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response markNotifAsRead(@HeaderParam("Authorization") String authString) {

		System.out.println("Entrei em markNotifAsRead no controller com  token " + authString);

		if (authString == null || authString.isEmpty() || !userService.isValidToken(authString)) {// não está logado ou não tem token válido																				
			return Response.status(401).entity(GestaoErros.getMsg(1)).build();
		}

		try {
			User user = userService.getNonDeletedEntityByToken(authString); // utilizador dono das notificações 
			notificationService.markAsReadAllNotifFromUser(user);
			return Response.ok().build();
					
		} catch (Exception e) {
			return Response.status(400).entity(GestaoErros.getMsg(17)).build();
		}
	}
	
}
