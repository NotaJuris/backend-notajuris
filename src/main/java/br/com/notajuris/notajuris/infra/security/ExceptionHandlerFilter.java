package br.com.notajuris.notajuris.infra.security;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import br.com.notajuris.notajuris.exceptions.BusinessException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ExceptionHandlerFilter extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        try{
            System.out.println("capturou a exceção");
            filterChain.doFilter(request, response);
        } catch(BusinessException e){
            request.setAttribute("javax.servlet.error.status_code", e.getStatusCode().value());
            request.setAttribute("javax.servlet.error.exception", e);
            request.getRequestDispatcher("/error").forward(request, response);
        }
    }
}
