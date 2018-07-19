package controller.resource;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

import model.AccesoUsuario;
import model.PMF;
import model.Resource;

public class DisplayResource extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException {
		
		if(AccesoUsuario.dameAcceso(req.getServletPath(),UserServiceFactory.getUserService().getCurrentUser(), 
			  PMF.get().getPersistenceManager(), getServletContext(),req, resp)){
			PersistenceManager pm = PMF.get().getPersistenceManager();
			String query= "select from "+Resource.class.getName()+" where status ==true";
			List<Resource> recursos = (List<Resource>) pm.newQuery(query).execute();
			req.setAttribute("recursos",recursos);
			// forward the request to the jsp
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/JSPResource/ResourceDisplay.jsp");
			dispatcher.forward(req, resp);
			
		}
	}
}
