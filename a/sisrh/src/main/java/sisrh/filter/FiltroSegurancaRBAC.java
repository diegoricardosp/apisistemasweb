package sisrh.filter; //Construção do filtro de controle de acesso RBAC 

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sisrh.seguranca.RBAC;

@WebFilter("/rest/*")
public class FiltroSegurancaRBAC implements Filter {

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) //verificar se a requisição HTTP pode ser executada a partir do JWT e das regras definidas no modelo RBAC
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		Enumeration<String> headerNames = httpRequest.getHeaderNames();	
		
		String token = null;
		String recurso = null;
		String operacao = httpRequest.getMethod();

		//obter recurso
		String[] uri = httpRequest.getRequestURI().toString().split("/");
		if (uri.length >= 3) {
			recurso = uri[3];
		}
		
		//requisicoes que publicas
		if (recurso.equals("swagger.json") || recurso.equals("loginunico") || recurso.equals("sistema")) {
			chain.doFilter(httpRequest, response);
			return;
		}

		//obter token jwt
		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				String chave = headerNames.nextElement();
				if (chave.equals("jwt")) {
					token = httpRequest.getHeader(chave);
				}
			}
		}
		
		//aplicar autorizacao RBAC
		if (!RBAC.autorizarAcesso(token, recurso, operacao)) {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Token JWT nao encontrado ou nao autorizado!");
		} else {
			chain.doFilter(httpRequest, response);
		}
	}


	@Override
	public void destroy() {
	}
}
