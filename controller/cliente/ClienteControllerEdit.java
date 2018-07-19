package controller.cliente;

import java.io.IOException;
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
public class ClienteControllerEdit extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		if(AccesoUsuario.dameAcceso(request.getServletPath(),UserServiceFactory.getUserService().getCurrentUser(), 
				PMF.get().getPersistenceManager(), getServletContext(),request, response)){	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
			if(AccesoUsuario.dameAcceso(request.getServletPath(),UserServiceFactory.getUserService().getCurrentUser(), 
					PMF.get().getPersistenceManager(), getServletContext(),request, response)){
			if (request.getParameter("action").equals("guardar")) {
				Jaula nuevo = pm.getObjectById(Jaula.class, Long.parseLong(request.getParameter("cambiarID")));

				String email = request.getParameter("email");
				Date fecha = null;
				try {
					fecha = sdf.parse(request.getParameter("fecha"));
				} catch (ParseException e) {

				}

				nuevo.setEmail(email);
				nuevo.setFecha(fecha);
				response.sendRedirect("/Clientes");
			} else {

				Jaula cambio = pm.getObjectById(Jaula.class, Long.parseLong(request.getParameter("cambiarID")));
				request.setAttribute("editJaula", cambio);
				
				String query= "select from " + Account.class.getName();
				List<Account> cuentas = (List<Account>)pm.newQuery(query).execute();
				request.setAttribute("cuentas", cuentas);
				RequestDispatcher dispatcher = getServletContext()
						.getRequestDispatcher("/WEB-INF/JSPFiles/Clientes/edit.jsp");
				dispatcher.forward(request, response);
			}
			}
	}}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
	}

}