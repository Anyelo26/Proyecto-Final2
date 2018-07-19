package controller.jaula;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.users.UserServiceFactory;

import model.*;

@SuppressWarnings("serial")
public class JaulaControllerAdd extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String query;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		com.google.appengine.api.users.User uGoogle = UserServiceFactory.getUserService().getCurrentUser();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Date fecha=null;
		
		  if(AccesoUsuario.dameAcceso(request.getServletPath(),
		  UserServiceFactory.getUserService().getCurrentUser(),
		  PMF.get().getPersistenceManager(), getServletContext(),request,
		  response)){
		 if (request.getParameter("action") != null) {
			 System.out.println("Ingresasiosi");
				boolean status = false;
				if (request.getParameter("status").equals("Vivo")) {
					status = true;
				}
				Jaula jaula;
				query = "select from " + Jaula.class.getName() + " where email == '" + request.getParameter("email")
						+ "'";
				List<Jaula> jaulas = (List<Jaula>) pm.newQuery(query).execute();
				System.out.println("Llega");
				try {
					fecha = sdf.parse(request.getParameter("fecha"));

				} catch (ParseException e) {

				}
				if (jaulas.size() != 0) {
					jaula = jaulas.get(0);
					
					ElementoJaula elemento=new ElementoJaula(Long.parseLong(request.getParameter("pez")), status,fecha);
					try {
						pm.makePersistent(elemento);
						jaula.addPez(elemento.getID());
					} finally {
						pm.close();
					}
				} else {
					ElementoJaula elemento=new ElementoJaula(Long.parseLong(request.getParameter("pez")), status,fecha);
					try {
						pm.makePersistent(elemento);
					}catch(Exception e){
						System.out.println("Error");
					}
					jaula = new Jaula(request.getParameter("email"),elemento.getID(), status,fecha);
					try {
						pm.makePersistent(jaula);
					} finally {
						pm.close();
					}
				}
				response.sendRedirect("/Jaulas?leerID=" + jaula.getID());
			
		} else {
			query = "select from " + Account.class.getName();
			List<Account> cuentas = (List<Account>) pm.newQuery(query).execute();
			request.setAttribute("cuentas", cuentas);
			query = "select from " + Pez.class.getName();
			List<Pez> peces = (List<Pez>) pm.newQuery(query).execute();
			request.setAttribute("peces", peces);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/Clientes/add.jsp");
			dispatcher.forward(request, response);
		}
	}

	 }
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		doGet(req,resp);
	}
}
