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

import model.PMF;
import model.AccesoUsuario;
import model.Pez;


public class CreatePez extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String query;PersistenceManager pm = PMF.get().getPersistenceManager();
		com.google.appengine.api.users.User uGoogle=UserServiceFactory.getUserService().getCurrentUser();

		if(AccesoUsuario.dameAcceso(request.getServletPath(),UserServiceFactory.getUserService().getCurrentUser(), 
				PMF.get().getPersistenceManager(), getServletContext(),request, response)){
			if(request.getParameter("action")!=null){
				if (request.getParameter("action").equals("PezCreateDo")) {
					Pez Pescadito = new Pez(request.getParameter("Nombre"), request.getParameter("Orden"),
							request.getParameter("Familia"), request.getParameter("Genero"), request.getParameter("Precio"),
							request.getParameter("Stock"));
					try {
						pm.makePersistent(Pescadito);
					} finally {
						pm.close();
					}
					response.sendRedirect("/peces/view?action=PezDisplay&PezId=" + Pescadito.getId());		
				}
			
			}
			else{
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/Peces/CreatePez.jsp");
				dispatcher.forward(request, response);
			}
		}			
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
