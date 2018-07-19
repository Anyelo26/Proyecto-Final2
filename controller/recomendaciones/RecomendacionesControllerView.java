package controller.recomendaciones;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

import model.*;

public class RecomendacionesControllerView extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException,IOException {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		//------------------------------Menu
				ControlaMenu.construyeMenu(pm, UserServiceFactory.getUserService().getCurrentUser(), request);
		Recomendacion leer=pm.getObjectById(Recomendacion.class,Long.parseLong(request.getParameter("leerID")));
		
		request.setAttribute("read", leer);
		RequestDispatcher dispatcher=getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/Recomendaciones/view.jsp");
		dispatcher.forward(request, response);
	}
}