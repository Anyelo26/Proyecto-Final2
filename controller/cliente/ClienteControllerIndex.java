package controller.cliente;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserServiceFactory;

import model.*;

@SuppressWarnings("serial")
public class ClienteControllerIndex extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		com.google.appengine.api.users.User uGoogle=UserServiceFactory.getUserService().getCurrentUser();
		
	/*	if(AccesoUsuario.dameAcceso(request.getServletPath(),UserServiceFactory.getUserService().getCurrentUser(), 
			PMF.get().getPersistenceManager(), getServletContext(),request, response)){
		*/	String query= "select from " + Jaula.class.getName();
			List<Jaula> jaulas = (List<Jaula>)pm.newQuery(query).execute();
			request.setAttribute("jaulas", jaulas);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/Clientes/index.jsp");
			dispatcher.forward(request, response);
			
		}
	//}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
	}
}
