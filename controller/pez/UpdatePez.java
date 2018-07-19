package controller.pez;
import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserServiceFactory;

import model.Pez;
import model.AccesoUsuario;
import model.PMF;
public class UpdatePez  extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String query;PersistenceManager pm = PMF.get().getPersistenceManager();
		com.google.appengine.api.users.User uGoogle=UserServiceFactory.getUserService().getCurrentUser();
		if(AccesoUsuario.dameAcceso(request.getServletPath(),UserServiceFactory.getUserService().getCurrentUser(), 
				  PMF.get().getPersistenceManager(), getServletContext(),request, response)){
						if (request.getParameter("action").equals("Actualizar")) {
							Key k = KeyFactory.createKey(Pez.class.getSimpleName(),new Long(request.getParameter("PezId")).longValue());
							Pez a = pm.getObjectById(Pez.class, k);
							request.setAttribute("Pez", a);
							RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/Peces/Update.jsp");
							dispatcher.forward(request, response);
						}
						if (request.getParameter("action").equals("Update")) {
							Pez pez = pm.getObjectById(Pez.class, Long.parseLong((request.getParameter("PezId"))));
							pez.setFamilia(request.getParameter("Familia"));
							pez.setGenero(request.getParameter("Genero"));
							pez.setNombre(request.getParameter("Nombre"));
							pez.setOrden(request.getParameter("Orden"));
							pez.setPrecio(request.getParameter("Precio"));
							pez.setStock(request.getParameter("Stock"));
							response.sendRedirect("/peces?action=PezLookup");
						}
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
