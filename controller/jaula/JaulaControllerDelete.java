package controller.jaula;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

import controller.*;
import model.*;

public class JaulaControllerDelete extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		if(AccesoUsuario.dameAcceso(request.getServletPath(),UserServiceFactory.getUserService().getCurrentUser(), 
				PMF.get().getPersistenceManager(), getServletContext(),request, response)){
			Jaula jaula=pm.getObjectById(Jaula.class, Long.parseLong(request.getParameter("idJaula")));
			jaula.borrarPez(Long.parseLong(request.getParameter("pezID")));
			ElementoJaula eleJaula=pm.getObjectById(ElementoJaula.class, Long.parseLong(request.getParameter("pezID")));

			if(jaula.getPeces().size()==0){
				try {
					pm.deletePersistent(eleJaula);
					pm.deletePersistent(jaula);
					
				} finally {
					pm.close();
				}
			}
			
			response.sendRedirect("/Clientes");
		}
	}
}