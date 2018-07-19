package controller.role;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserServiceFactory;

import model.AccesoUsuario;
import model.Account;
import model.PMF;
import model.Role;


public class CreateRoleRedirect extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if(AccesoUsuario.dameAcceso(request.getServletPath(),UserServiceFactory.getUserService().getCurrentUser(), 
				  PMF.get().getPersistenceManager(), getServletContext(),request, response)){
			PersistenceManager pm = PMF.get().getPersistenceManager();
			
			if(request.getParameter("mode").equals("update")){
				Key k = KeyFactory.createKey(Role.class.getSimpleName(),Long.parseLong(request.getParameter("idRol")));
				Role rol = pm.getObjectById(Role.class, k);
				request.setAttribute("rol", rol);
				pm.close();
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/JSPRole/RoleEdit.jsp");
				dispatcher.forward(request, response);
			}else if(request.getParameter("mode").equals("create")){
				pm.close();
				System.out.println("redireccionando");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/JSPRole/RoleCreate.jsp");
				dispatcher.forward(request, response);
			}
			
		
		}
	}
}
