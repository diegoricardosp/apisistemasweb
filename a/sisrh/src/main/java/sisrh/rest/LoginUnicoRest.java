package sisrh.rest; //receberá um objeto login, que possui um identificador e uma senha. Caso a autenticação ocorra com sucesso, o serviço retornará o JWT; caso contrário, uma mensagem de falha

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.*;

import io.swagger.annotations.Api;
import sisrh.dto.Login;
import sisrh.seguranca.LoginUnico;

@Api
@Path("/loginunico")
public class LoginUnicoRest {

	@POST
	@Path("jwt")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public Response gerarToken(Login login) {
		try {
			String token = LoginUnico.geraToken(login.getUsuario(), login.getSenha());
			if (token != null) {
				return Response.ok().entity(token).build();
			} else {
				return Response.status(Status.FORBIDDEN)
						.entity("{ \"mensagem\" : \"Usuario ou senha invalido!\" }")
						.build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(
					"{ \"mensagem\" : \"Falha para gerar token JWT!\" , \"detalhe\" :  \"" 
					+ e.getMessage() + "\"  }")
					.build();
		}
	}

}
