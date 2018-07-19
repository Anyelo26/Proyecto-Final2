package controller.role;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

import model.Account;
import model.ControlaMenu;
import model.PMF;
import model.Role;

public class ReadRole extends HttpServlet {
public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		//------------------------------Menu
				ControlaMenu.construyeMenu(pm, UserServiceFactory.getUserService().getCurrentUser(), req);
		
		String query= "select from "+Role.class.getName()+" where this.id =="+req.getParameter("idRol");
		List<Role> roles = (List<Role>) pm.newQuery(query).execute();
		
		Role rol= roles.get(0);
		req.setAttribute("rol", rol);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/JSPRole/RoleView.jsp");
		dispatcher.forward(req, resp);

	}
}
