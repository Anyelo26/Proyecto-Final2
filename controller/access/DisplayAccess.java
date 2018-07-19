package controller.access;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

import model.PMF;
import model.AccesoUsuario;
import model.Access;

public class DisplayAccess extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException {
		
		if(AccesoUsuario.dameAcceso(req.getServletPath(),UserServiceFactory.getUserService().getCurrentUser(), 
				PMF.get().getPersistenceManager(), getServletContext(),req, resp)){
			PersistenceManager pm = PMF.get().getPersistenceManager();
			String query= "select from "+Access.class.getName()+" where status ==true";
			List<Access> accesos = (List<Access>) pm.newQuery(query).execute();
			req.setAttribute("accesos",accesos);
			// forward the request to the jsp
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/JSPAccess/AccessDisplay.jsp");
			dispatcher.forward(req, resp);
		}
	}
}
