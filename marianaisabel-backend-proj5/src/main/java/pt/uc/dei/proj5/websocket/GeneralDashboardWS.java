package pt.uc.dei.proj5.websocket;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import pt.uc.dei.proj5.bean.DashboardBean;

//endpoint para o websocket
@ServerEndpoint("/dashboard/generalStatsWS/{authString}")
public class GeneralDashboardWS {
	// estático, a interacçao deve ser feita com metodos estaticos para ossibilitar
	// que outros recursos consigam
	// evovcar a lista e outras coisas dest classe.

	private static final Logger logger = Logger.getLogger("GeneralDashboardWS");
	/* Queue for all open WebSocket sessions */
	static Queue<Session> queue = new ConcurrentLinkedQueue<>(); // por cada sessao que exista nesta queue vai mandar
																	// uma mensagem.
	// pode ter acessos concorrenciais. e bloqueia mais do que uma thread a aceder
	// directamente a este endpoint
	// este queue é bloqueante so permite que uma thread de cada vez aceda

	@Inject
	DashboardBean dashboardService;

	// o metodo seguinte serve para mandar mensagens
	/* PriceVolumeBean calls this method to send updates */
	// public static void send(String stats) {
	public static void send(String stats) {
		// String msg = String.format("%.2f / %d", price, volume); //pq so posso mandar
		// strings por websockets
		// String msg=String.format("%d", totalUsers);
		String msg = stats;
		try {
			/* Send updates to all open WebSocket sessions */
			for (Session session : queue) {
				session.getBasicRemote().sendText(msg);
				logger.log(Level.INFO, "Sent: {0}", msg);
			}
		} catch (IOException e) {
			logger.log(Level.INFO, e.toString());
		}
	}

	// foi feita a comunicaçao. é do session q sai tudo que é nec para comunicar

	@OnOpen 
	public void openConnection(Session session, @PathParam("authString") String authString) {
		// public void openConnection(Session session) {
		
		System.out.println("entrei em openConnection DashBoard GEneral");
		//System.out.println("******estou no connection do websocket geral: protocolo do front*******************");
		//System.out.println("imprimi algo?  " );
		if (dashboardService.validateTokenForDashboardAcess(authString)) {
			/* Register this connection in the queue */

			queue.add(session); // está a ser criado uma fila que se abriram com ligaçao a este websocket do
								// lado do servidor
								// pq podemos ter varias sessoes ao mesmo tempo

			String msg = dashboardService.updateGeneralDashboard();
			try {

				session.getBasicRemote().sendText(msg);
				logger.log(Level.INFO, "Sent: {0}", msg);

			} catch (IOException e) {
				e.printStackTrace();
			}
			logger.log(Level.INFO, "Connection opened.");

		} else {

			try {
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("nao tem token valido para dashboard nem conseguiu fechar o socket");
			}
		}
	}

	@OnClose
	public void closedConnection(Session session, @PathParam("authString") String authString) {
		/* Remove this connection from the queue */
		queue.remove(session);
		
		// tiramos da fila dos sockets abertos apenas do nosso lado
		// nao se está a invalidar sessao
		// a sessao termina por si so
		logger.log(Level.INFO, "Connection closed.");
	}

	@OnError
	public void error(Session session, @PathParam("username") String username,
			@PathParam("authString") String authString, Throwable t) {
		/* Remove this connection from the queue */
		queue.remove(session);
		// se há um erro abortamos a sessao
		logger.log(Level.INFO, t.toString());
		logger.log(Level.INFO, "Connection error.");
	}

	// nao existe onMessage, nao recebe respostas do clientes.
	// so envia msg com o metodo send()
	// o cliente nao sabe q o servidor nao está a receber as msg dele

}
