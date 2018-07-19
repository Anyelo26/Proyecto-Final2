package controller.jaula;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

import controller.*;
import model.*;

@SuppressWarnings("serial")
public class JaulaControllerEdit extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		if(AccesoUsuario.dameAcceso(request.getServletPath(),UserServiceFactory.getUserService().getCurrentUser(), 
				PMF.get().getPersistenceManager(), getServletContext(),request, response)){	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");

			if (request.getParameter("action").equals("guardar")) {
				ElementoJaula nuevo = pm.getObjectById(ElementoJaula.class, Long.parseLong(request.getParameter("cambiarID")));

				Long pez = Long.parseLong(request.getParameter("pez"));
				boolean status=false;
				if(request.getParameter("status").equals("Vivo")){
					status=true;
				}
				Date fecha = null;
				try {
					fecha = sdf.parse(request.getParameter("fecha"));
				} catch (ParseException e) {

				}
			
				nuevo.setPez(pez);
				nuevo.setStatus(status);
				nuevo.setFecha(fecha);
				response.sendRedirect("/Clientes");
			} else {

				ElementoJaula cambio = pm.getObjectById(ElementoJaula.class, Long.parseLong(request.getParameter("cambiarID")));
				String nombre=pm.getObjectById(Pez.class, cambio.getPez()).getNombre();
				request.setAttribute("editJaula", cambio);
				request.setAttribute("nombre", nombre);
				
				String query= "select from " + Pez.class.getName();
				List<Pez> elementos = (List<Pez>)pm.newQuery(query).execute();
			
				request.setAttribute("elementos", elementos);
				RequestDispatcher dispatcher = getServletContext()
						.getRequestDispatcher("/WEB-INF/JSPFiles/Jaula/edit.jsp");
				dispatcher.forward(request, response);
			}
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
	}

}