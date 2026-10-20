package edu.ifsp.seunutri.security;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import edu.ifsp.seunutri.modelo.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component 
public class AuthInterceptor implements HandlerInterceptor {

	private static final String[] ROTAS_PUBLICAS = { "/login", "/logout", "/usuarios/novo", "/usuarios",
			"/css/", "/js/", "/images/", "/favicon.ico" };

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String caminho = request.getRequestURI();
		if (request.getContextPath() != null && !request.getContextPath().isEmpty()) {
			caminho = caminho.substring(request.getContextPath().length());
		}

		for (String publica : ROTAS_PUBLICAS) {
			if (caminho.equals(publica) || caminho.startsWith(publica)) {
				return true;
			}
		}

		HttpSession session = request.getSession(false);
		Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;
		if (usuario == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		}

		// ADMIN: acesso total, como hoje.
		if ("ADMIN".equals(usuario.getPapel())) {
			return true;
		}

		// USUARIO: apenas rotas e métodos da lista de permitidos. Qualquer outra
		// rota ou método é negado (redireciona para a home). Nunca redireciona
		// para "/" quando a requisição já é para "/", evitando loop infinito.
		if ("USUARIO".equals(usuario.getPapel())) {
			String metodo = request.getMethod();
			boolean permitido = ehPermitidoParaUsuario(caminho, metodo);
			if (!permitido) {
				if (!"/".equals(caminho)) {
					response.sendRedirect(request.getContextPath() + "/");
				}
				return false;
			}
			return true;
		}

		// Qualquer outro papel sem permissão.
		response.sendRedirect(request.getContextPath() + "/login");
		return false;
	}

	private boolean ehPermitidoParaUsuario(String caminho, String metodo) {
		// 0) rotas públicas e estáticos que o USUARIO também pode acessar.
		if (caminho.startsWith("/css/") || caminho.startsWith("/js/")
				|| caminho.startsWith("/images/") || "/favicon.ico".equals(caminho)) {
			return true; // estáticos: já estavam liberados hoje (ROTAS_PUBLICAS)
		}
		if ("GET".equalsIgnoreCase(metodo) && "/".equals(caminho)) {
			return true;
		}
		if ("/login".equals(caminho)) {
			return "GET".equalsIgnoreCase(metodo) || "POST".equalsIgnoreCase(metodo);
		}
		if ("GET".equalsIgnoreCase(metodo) && "/logout".equals(caminho)) {
			return true;
		}
		if ("GET".equalsIgnoreCase(metodo) && "/usuarios/novo".equals(caminho)) {
			return true;
		}
		if ("POST".equalsIgnoreCase(metodo) && "/usuarios".equals(caminho)) {
			return true; // cadastro público
		}
		// a) ver a listagem de Pratos (GET)
		if ("GET".equalsIgnoreCase(metodo) && "/pratos".equals(caminho)) {
			return true;
		}
		// b) ver a listagem das PRÓPRIAS fichas (GET)
		if ("GET".equalsIgnoreCase(metodo) && "/fichas".equals(caminho)) {
			return true;
		}
		// b) ver a visualização da PRÓPRIA ficha (GET)
		if ("GET".equalsIgnoreCase(metodo) && caminho.startsWith("/fichas/visualizar/")) {
			return true;
		}
		// c) gerar ficha apenas para si mesmo (POST)
		if ("POST".equalsIgnoreCase(metodo) && caminho.startsWith("/fichas/gerar/")) {
			return true;
		}
		// d) ver o ranque (GET)
		if ("GET".equalsIgnoreCase(metodo) && "/ranques".equals(caminho)) {
			return true;
		}
		// e) editar o próprio cadastro de usuário (GET/POST)
		if ("GET".equalsIgnoreCase(metodo) && caminho.startsWith("/usuarios/")
				&& caminho.endsWith("/editar")) {
			return true;
		}
		if ("POST".equalsIgnoreCase(metodo) && caminho.startsWith("/usuarios/")
				&& !caminho.endsWith("/editar") && !caminho.endsWith("/excluir")) {
			return true;
		}

		return false;
	}
}
