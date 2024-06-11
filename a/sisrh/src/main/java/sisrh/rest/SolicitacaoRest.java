package sisrh.rest;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import io.swagger.annotations.Api;
import sisrh.banco.Banco;
import sisrh.dto.Solicitacao;

@Api // indica que a classe possui uma API REST.
@Path("/solicitacao") //indica o caminho da API.
public class SolicitacaoRest {	
	@GET //Anota que o método SolicitacaoRest atenderá às requisições HTTP com o método GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarSolicitacoes() throws Exception {
		List<Solicitacao> lista = Banco.listarSolicitacoes();		
		GenericEntity<List<Solicitacao>> entity = new GenericEntity<List<Solicitacao>>(lista) {};
		return Response.ok().entity(entity).build();
	}
	
	@GET
	@Path("{id}") //indica a formação da chamada
	@Produces(MediaType.APPLICATION_JSON) //informa que o serviço retornará com certo formato, neste caso, na estrutura JSON.
	public Response obterSolicitacao(@PathParam("id") Integer _id) throws Exception { // PathParam indica que o valor será atribuído à variável id.
		try {
			Solicitacao solicitacao = Banco.buscarSolicitacaoPorId(_id);
			if ( solicitacao != null ) {
				return Response.ok().entity(solicitacao).build();
			}else {
				return Response.status(Status.NOT_FOUND)
						.entity("{ \"mensagem\" : \"ID nao encontrada!\" }").build();
			}
		}catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("{ \"mensagem\" : \"Falha para obter Solicitacao!\" , \"detalhe\" :  \""+ e.getMessage() +"\"  }").build();
		}
	}
	
	@POST	//Perceba que o método recebeu a anotação @POST, pois se trata de uma inclusão
	@Consumes(MediaType.APPLICATION_JSON) //serviço receberá dados (@Consumes) no formato JSON
	@Produces(MediaType.APPLICATION_JSON) //produzirá (@Produces) dados de resultado também no formato JSON.
	public Response incluirSolicitacao(Solicitacao solicitacao) {
		try {
			Solicitacao id = Banco.incluirSolicitacao(solicitacao);
			return Response.ok().entity(id).build();
		}catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("{ \"mensagem\" : \"Falha na inclusao da Solicitacao!\" , \"detalhe\" :  \""+ e.getMessage() +"\"  }").build();
		}		
	}
	
	@PUT	
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response alterarSolicitacao(@PathParam("id") Integer _id, Solicitacao solicitacao)  {
		try {			
			if ( Banco.buscarSolicitacaoPorId(_id) == null ) {				
				return Response.status(Status.NOT_FOUND)
						.entity("{ \"mensagem\" : \"Solicitacao nao encontrada!\" }").build();
			}				
			Solicitacao sol = Banco.alterarSolicitacao(_id, solicitacao);	
			return Response.ok().entity(sol).build();
		}catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("{ \"mensagem\" : \"Falha na alteracao da solicitação!\" , \"detalhe\" :  \""+ e.getMessage() +"\"  }").build();
		}
	}
	
	@DELETE
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response excluirSolicitacao(@PathParam("id") Integer _id) throws Exception {
		try {
			if ( Banco.buscarSolicitacaoPorId(_id) == null ) {				
				return Response.status(Status.NOT_FOUND).
						entity("{ \"mensagem\" : \"Empregado nao encontrado!\" }").build();
			}				
			Banco.excluirSolicitacao(_id);
			return Response.ok().entity("{ \"mensagem\" : \"Solicitacao "+ _id + " excluida!\" }").build();	
		}catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).
					entity("{ \"mensagem\" : \"Falha na exclusao do empregado!\" , \"detalhe\" :  \""+ e.getMessage() +"\"  }").build();
		}		
	}

}