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

import model.ControlaMenu;
import model.PMF;
import model.Resource;

public class ReadResource extends HttpServlet {
public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		//------------------------------Menu
				ControlaMenu.construyeMenu(pm, UserServiceFactory.getUserService().getCurrentUser(), req);
		String query= "select from "+Resource.class.getName()+" where this.id =="+req.getParameter("idRecurso");
		List<Resource> recursos = (List<Resource>) pm.newQuery(query).execute();
		
		Resource recurso= recursos.get(0);
		req.setAttribute("recurso", recurso);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/JSPResource/ResourceView.jsp");
		dispatcher.forward(req, resp);

	}
}
