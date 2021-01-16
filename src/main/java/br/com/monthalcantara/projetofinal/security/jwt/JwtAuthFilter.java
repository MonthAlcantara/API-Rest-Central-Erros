package br.com.monthalcantara.projetofinal.security.jwt;

import br.com.monthalcantara.projetofinal.service.implementacoes.UserDetailsServiceImpl;
import br.com.monthalcantara.projetofinal.service.implementacoes.UsuarioServiceImpl;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Profile({"prod","test"})
public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private UserDetailsServiceImpl userService;

    public JwtAuthFilter(JwtService jwtService, UserDetailsServiceImpl userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorization = httpServletRequest.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer")) {
            String token = authorization.split(" ")[1];
            if (jwtService.isValid(token)) {
                String userLogin = jwtService.getUserLogin(token);
                UserDetails userDetails = userService.loadUserByUsername(userLogin);
                UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(userDetails,
                        null,
                        userDetails.getAuthorities());
                user.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(user);
            }

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
