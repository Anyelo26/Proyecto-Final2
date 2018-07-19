package controller.resource;

import java.io.IOException;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

import model.*;

public class CreateResource extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
			PersistenceManager pm = PMF.get().getPersistenceManager();
				
			Date fecha= new Date();
			Resource nuevoRecurso=new Resource(request.getParameter("nombre"),fecha);
			
			try {
				pm.makePersistent(nuevoRecurso);
			} finally {
				pm.close();
			}
			response.sendRedirect("/Resource/Display");
		
	}
}
