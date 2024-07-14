package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override //el filter chain se refiere ala cadena de filtros, un filtro lo recibe y se envia a otro para que se empieze a despachar la solicitud y dependiendo de esta llegue al controller
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //la primera accion es obtener el token, esto lo voy a obtener de los headers
        var token  = request.getHeader("Authorization");
        if(token == null || token == ""){
            throw new RuntimeException("El token enviado no es valido");
        }
        token = token.replace("Bearer ","");
        System.out.println(token);

        System.out.println(tokenService.getSubject(token)); //con esta linea lo que quiero saber es,  ¿este usuario tiene sesion?

        //if(tokenEsValido)
        System.out.println("El filtro eta siendo llamado");
        //en la siguiente linea es donde se hace el filtro y se reenvia el request y el respnse hacia el siguiente nivel
        filterChain.doFilter(request, response);
    }
}
