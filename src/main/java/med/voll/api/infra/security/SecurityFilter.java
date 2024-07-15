package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override //el filter chain se refiere ala cadena de filtros, un filtro lo recibe y se envia a otro para que se empieze a despachar la solicitud y dependiendo de esta llegue al controller
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("este s el inicio del filter");
        var authHeader  = request.getHeader("Authorization");//la primera accion es obtener el token, esto lo voy a obtener de los headers
        System.out.println(authHeader);
        if(authHeader != null){//si el token no esta nulo, se inicia la validacion dentro del if
            System.out.println("validamos que el token no es null");
            var token = authHeader.replace("Bearer ","");
            System.out.println(token);

            System.out.println(tokenService.getSubject(token)); //con esta linea lo que quiero saber es,  ¿este usuario tiene sesion?
            var nombreDeUsuario = tokenService.getSubject(token);//extraenis el subject
            if(nombreDeUsuario != null){
                //token valio
                var usuario= usuarioRepository.findByLogin(nombreDeUsuario);
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()); //forzamos un inicio de sesion
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            //if(tokenEsValido)
            System.out.println("El filtro eta siendo llamado");
            //en la siguiente linea es donde se hace el filtro y se reenvia el request y el respnse hacia el siguiente nivel
        }
        filterChain.doFilter(request, response);

    }
}
