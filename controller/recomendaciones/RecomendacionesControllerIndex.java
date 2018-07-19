package controller.recomendaciones;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

import model.*;

public class RecomendacionesControllerIndex extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException,IOException {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		//------------------------------Menu
				ControlaMenu.construyeMenu(pm, UserServiceFactory.getUserService().getCurrentUser(), request);
			String query = "select  from " + Recomendacion.class.getName()+" where exist =='recomendacion' ";
			List<Recomendacion> recomendaciones = (List<Recomendacion>) pm.newQuery(query).execute();
			// pass the list to the jsp
			request.setAttribute("recomendaciones", recomendaciones);
			// forward the request to the jsp
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/Recomendaciones/index.jsp");
					dispatcher.forward(request, response);
	
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException,IOException {
		doGet(request,response);
	}
}