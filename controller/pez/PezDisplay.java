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

import model.PMF;
import model.AccesoUsuario;
import model.Access;
import model.Pez;
import model.Resource;
import model.Account;
import model.Comentario;
import model.ControlaMenu;
public class PezDisplay extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String query;PersistenceManager pm = PMF.get().getPersistenceManager();
		query="select from "+Comentario.class.getName()+" where idPez=="+request.getParameter("PezId");
		List<Comentario>arreglo=(List<Comentario>)pm.newQuery(query).execute();
		
		com.google.appengine.api.users.User uGoogle=UserServiceFactory.getUserService().getCurrentUser();
		//------------------------------Menu
		ControlaMenu.construyeMenu(pm, UserServiceFactory.getUserService().getCurrentUser(), request);
						
							Key k = KeyFactory.createKey(Pez.class.getSimpleName(),
									new Long(request.getParameter("PezId")).longValue());
							Pez a = pm.getObjectById(Pez.class, k);
							request.setAttribute("Pez", a);
							request.setAttribute("Comentarios", arreglo);
							RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/Peces/PezDisplay.jsp");
							dispatcher.forward(request, response);
							
						
						
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
