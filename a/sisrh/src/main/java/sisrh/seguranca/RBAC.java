package sisrh.seguranca; //As regras de acesso serão armazenadas nessa classe. Uma evolução futura para um sistema real seria armazenar as regras em banco de dados.

import java.util.*;
import io.jsonwebtoken.*;

public class RBAC {
	private static Map<String, Map<String, String>> rbac = new HashMap<>(); //armazenará as regras de acesso
	
	static {
		inicializarRegrasRBAC(); //tornar o carregamento automático, sempre que o sistema for inicializado, chamada estática ao método de inicialização
	}

	
	private static void inicializarRegrasRBAC() { //construirá as regras para os recursos empregado, usuário e sistema para os perfis Servico, Admininstrador e Usuario
		Map<String, String> perfil_servico = new HashMap<>();		
		perfil_servico.put("empregado", "GET,POST,PUT,DELETE");
		perfil_servico.put("usuario", "GET,POST,PUT,DELETE");
		perfil_servico.put("sistema", "GET");			
		
		Map<String, String> perfil_administrador = new HashMap<>();		
		perfil_administrador.put("empregado", "GET,POST,PUT,DELETE");
		perfil_administrador.put("usuario", "GET,POST,PUT,DELETE");
		perfil_administrador.put("sistema", "GET");					
		
		Map<String, String> perfil_usuario = new HashMap<>();		
		perfil_usuario.put("empregado", "GET");
		perfil_usuario.put("sistema", "GET");				
		
		rbac.put("SERVICO", perfil_servico);		
		rbac.put("ADMINISTRADOR", perfil_administrador);
		rbac.put("USUARIO", perfil_usuario);		
	}
	
	public static boolean autorizarAcesso(String token, String recurso, String operacao) { //método que verificará se o token possui permissão para realizar uma dada operação a determinado recurso
		try {
			Jws<Claims> declaracores = LoginUnico.validarToken(token);
			String perfil = declaracores.getBody().get("perfil").toString();			
			Map<String, String> perfilRBAC = rbac.get(perfil);
			if(perfilRBAC != null && perfilRBAC.get(recurso).contains(operacao)){
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

}
