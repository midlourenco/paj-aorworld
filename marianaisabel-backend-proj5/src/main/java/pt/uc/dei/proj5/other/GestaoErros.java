package pt.uc.dei.proj5.other;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class  GestaoErros {
	 private static Map<Integer, String>  tabErros = new HashMap <Integer, String> ();

	 public GestaoErros() {
		 //GestaoErros.getMsg(1)
		 //401
		
		tabErros.put(1, "Token inexistente ou inválido" );
		tabErros.put(7, "Ocorreu um problema no login");
			
		//400
		tabErros.put(2, "O username ao qual está a aceder para operações detalhes não existe ou está marcado para eliminar");
		tabErros.put(3, "O username ao qual está a aceder para operações de projecto não existe ou está marcado para eliminar");
		tabErros.put(4, "O username ao qual está a aceder para operações de noticia não existe ou está marcado para eliminar");
		tabErros.put(6, "Já existe uma projecto com o mesmo titulo criado por este utilizador");
		tabErros.put(12, "Não foi possível listar as noticias / projectos deste utilizador");
		tabErros.put(18, "Não é possível alterar o email");
		tabErros.put(17, "Não foi possível apresentar/criar/apagar/alterar/recuperar a projecto/noticia");
		tabErros.put(14, "Não foi possível apagar a utilizador");
		tabErros.put(22, "Não pode partilhar uma projeto/categoria consigo mesmo");
		
		//403
		tabErros.put(5, "Não tem permissões para esta operação neste utilizador");
		tabErros.put(9, "Não tem permissões para ver/criar/apagar/recuperar utilizador");
		tabErros.put(13,"não tem permissões para criar/apresentar/alterar/apagar projectos/noticias neste utilizador");

		
		//200
		tabErros.put(8, "Login com Sucesso");
		tabErros.put(11, "Operação realizada com Sucesso");
		tabErros.put(20, "User criado com Sucesso");
		tabErros.put(19, "Não existem utilizadores com privilégios de Administrador na base dados");
		tabErros.put(10, "Não existem utilizadores na base dados");
		tabErros.put(21, "Não existem projectos/noticias na base dados para o filtro pedido");

		
		//default
		tabErros.put(0,"Ocorreu um erro");
	 }
	 
	public static String getMsg(int code) {
		 return  tabErros.get(code);
	}



}
