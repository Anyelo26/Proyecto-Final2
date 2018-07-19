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
import model.Access;
import model.ControlaMenu;

public class ReadAccess extends HttpServlet {
public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		//------------------------------Menu
		ControlaMenu.construyeMenu(pm, UserServiceFactory.getUserService().getCurrentUser(), req);
		String query= "select from "+Access.class.getName()+" where this.id =="+req.getParameter("idAcceso");
		List<Access> accesos = (List<Access>) pm.newQuery(query).execute();
		
		Access acceso= accesos.get(0);
		req.setAttribute("acceso", acceso);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/JSPAccess/AccessView.jsp");
		dispatcher.forward(req, resp);

	}
}
