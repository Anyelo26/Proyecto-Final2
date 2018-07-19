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

@SuppressWarnings("serial")
public class RecomendacionesControllerEdit extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
			PersistenceManager pm = PMF.get().getPersistenceManager();
			if(AccesoUsuario.dameAcceso(request.getServletPath(),UserServiceFactory.getUserService().getCurrentUser(), 
					  PMF.get().getPersistenceManager(), getServletContext(),request, response)){

				if (request.getParameter("action").equals("guardar")) {
					Recomendacion nuevo = pm.getObjectById(Recomendacion.class, Long.parseLong(request.getParameter("cambiarID")));
	
					String titulo = request.getParameter("titulo");
					String informacion = request.getParameter("informacion");
					String imagen =request.getParameter("imagen");
	
	
					nuevo.setTitulo(titulo);
					nuevo.setInformacion(informacion);
					nuevo.setImagen(imagen);
					
					response.sendRedirect("/Recomendaciones");
				} else {
	
					Recomendacion cambio = pm.getObjectById(Recomendacion.class, Long.parseLong(request.getParameter("cambiarID")));
	
					request.setAttribute("editRecomendacion", cambio);
					RequestDispatcher dispatcher = getServletContext()
							.getRequestDispatcher("/WEB-INF/JSPFiles/Recomendaciones/edit.jsp");
					dispatcher.forward(request, response);
				}
			}
		}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
	}

}