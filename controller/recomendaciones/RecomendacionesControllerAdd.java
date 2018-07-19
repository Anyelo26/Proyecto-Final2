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

public class RecomendacionesControllerAdd extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		if (AccesoUsuario.dameAcceso(request.getServletPath(), UserServiceFactory.getUserService().getCurrentUser(),
				PMF.get().getPersistenceManager(), getServletContext(), request, response)) {
			if (request.getParameter("titulo") != null && request.getParameter("informacion") != null
					 &&request.getParameter("imagen") != null) {

				String titulo = request.getParameter("titulo");
				String informacion = request.getParameter("informacion");
				String imagen = request.getParameter("imagen");

				Recomendacion nuevo = new Recomendacion(titulo, informacion,"");

				try {
					pm.makePersistent(nuevo);
				} finally {
					pm.close();
				}

				response.sendRedirect("/Recomendaciones");

			} else {
				RequestDispatcher dispatcher = getServletContext()
						.getRequestDispatcher("/WEB-INF/JSPFiles/Recomendaciones/add.jsp");
				dispatcher.forward(request, response);
			}
		}
	}
}
