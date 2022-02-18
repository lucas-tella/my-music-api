package br.inatel.mymusicapi.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.inatel.mymusicapi.model.User;
import br.inatel.mymusicapi.repository.UserRepository;
import br.inatel.mymusicapi.service.TokenService;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
	private TokenService tokenService;
	private UserRepository userRepository;
	public TokenAuthenticationFilter(TokenService tokenService, UserRepository userRepository) {
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = recoverToken(request);
		boolean validToken = tokenService.isTokenValid(token);
		if (validToken) {
			authenticateClient(token);
		}
		filterChain.doFilter(request, response);
	}
	private void authenticateClient(String token) {
		Long userId = tokenService.getUserId(token);
		User user = userRepository.findById(userId).get();
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
				user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	private String recoverToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		} 
		return token.substring(7, token.length());
	}
}