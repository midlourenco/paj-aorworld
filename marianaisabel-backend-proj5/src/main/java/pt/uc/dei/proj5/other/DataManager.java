package pt.uc.dei.proj5.other;

public class DataManager {

	/**
	 * 
	 * User
	 * há um user admin criado por defeito na bd, user esse que não é possível outra pessoa alterar ou apagar- existe uma flag que o identifica adminAuto
	 * permitido login em mais do que um dispositivo (se ja tiver token damos-lhe o mesmo). 
	 * logout num sitio força o logout em todos os dispositivos onde estiver logado (apagamos o token na BD).
	 * 
	 * está a ser usado hashing da password
	 * 
	 * optou-se por colocar a atualizaçao da password no endpoint diferente do de atualizar o resto da informação do user	 * 
	 * despromover/bloquear / não aprovar o registo de um user - apagamos o token, alteramos os priv para viewer e marcamos como apagado
	 * desbloquear um registo previamente bloqueado - volta para a lista das aprovações, desmarcamos de apagado, e por duplo check fica com priv de Viewer assim 
	 * alterar os priv de um user (para membro ou admin) directamente -  só permitimos fazer isto se o user não estiver marcado para apagar (pensado para a parte de aceitar um registo)
	 * update de um user: se for admin aceitamos que aqui também pode alterar os privilégios
	 * 
	 * 
	 **************************
	 *
	 * Projecto /Noticia
	 * 
	 *
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	
	
	
	
	
	
	
}
