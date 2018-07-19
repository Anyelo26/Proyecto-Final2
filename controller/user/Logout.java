package controller.user;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

import model.ControlaMenu;
import model.PMF;



public class Logout extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		//------------------------------Menu
		ControlaMenu.construyeMenu(pm, UserServiceFactory.getUserService().getCurrentUser(), request);
		RequestDispatcher rq= getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/UserDenied/NoRegistrado.jsp");
		rq.forward(request, response);
	}
}
