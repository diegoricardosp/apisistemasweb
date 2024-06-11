package soap; 

import java.util.Map;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebMethod; 
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import exception.DivisaoPorZeroException;

@WebService //indica que a classe Calculadora implementa um serviço SOAP.

@SOAPBinding(style = Style.RPC) //informa que nosso WSDL utilizará o estilo RPC.


public class Calculadora {
	
	@Resource
    WebServiceContext context; //objeto context terá todas as informações da requisição HTTP que precisaremos para obter o usuário e senha.
	
	@SuppressWarnings("rawtypes")
	private boolean autenticar (WebServiceContext context) throws Exception {
		MessageContext mc = context.getMessageContext();        
	    Map httpHeaders = (Map) mc.get(MessageContext.HTTP_REQUEST_HEADERS);
       	if (!httpHeaders.containsKey("usuario"))
       		throw new Exception("Informe um usuário");
       	if (!httpHeaders.containsKey("senha"))
       		throw new Exception("Informe uma senha");
       	String usuario = ((List) httpHeaders.get("usuario")).get(0).toString();
	    String senha = ((List) httpHeaders.get("senha")).get(0).toString();
	    if (usuario.equals("sisfin") && senha.equals("sisfin123")) {
	    	return true;
	    } else {
	        throw new Exception("Usuário e senha inválido");
	    }
}
	
	@WebMethod(action = "somar") //indica que a mensagem nomeada somar será implementada pelo método somar.
	public int somar(
			@WebParam(name = "numero1") int numero1, //descreve os parâmetros da mensagem somar.
			@WebParam(name = "numero2") int numero2) throws Exception { //descreve os parâmetros da mensagem somar.
		autenticar(context);
		return numero1 + numero2;
	}   
	
	@WebMethod(action = "subtracao") //indica que a mensagem nomeada somar será implementada pelo método somar.
	public int subtracao(
			@WebParam(name = "numero1") int numero1, //descreve os parâmetros da mensagem somar.
			@WebParam(name = "numero2") int numero2) { //descreve os parâmetros da mensagem somar.
	return numero1 - numero2;
	}     
	
	@WebMethod(action = "dividir")
    public double dividir(
           @WebParam(name = "numero1") double numero1, 
           @WebParam(name = "numero2") double numero2) {
		
    return numero1 / numero2;

    }
}