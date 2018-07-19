package controller.role;

import java.io.IOException;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

import model.AccesoUsuario;
import model.PMF;
import model.Role;

public class CreateRole extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
			PersistenceManager pm = PMF.get().getPersistenceManager();
				
			Date fecha= new Date();
			Role nuevoRol=new Role(request.getParameter("nombre"),fecha);
			
			try {
				pm.makePersistent(nuevoRol);
				System.out.println("creado");
			} finally {
				pm.close();
			}
			response.sendRedirect("/Role/Display");
		
	}
}
