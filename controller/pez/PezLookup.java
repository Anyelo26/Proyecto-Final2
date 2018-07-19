package controller.pez;
import java.io.IOException;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

import model.Pez;
import model.ControlaMenu;
import model.PMF;
public class PezLookup  extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String query;PersistenceManager pm = PMF.get().getPersistenceManager();
		com.google.appengine.api.users.User uGoogle=UserServiceFactory.getUserService().getCurrentUser();
		//------------------------------Menu
				ControlaMenu.construyeMenu(pm, UserServiceFactory.getUserService().getCurrentUser(), request);
						
							// query for the entities by name
							query = "select from " + Pez.class.getName() + " where estado == '" + "true" + "'";
							@SuppressWarnings("unchecked")
							List<Pez> Peces = (List<Pez>) pm.newQuery(query).execute();
							// pass the list to the jsp
							request.setAttribute("Peces", Peces);
							// forward the request to the jsp
							RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/Peces/PezLookup.jsp");
							dispatcher.forward(request, response);
						
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
