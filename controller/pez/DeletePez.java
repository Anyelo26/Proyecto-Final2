package controller.pez;
import model.AccesoUsuario;
import model.Access;
import model.Pez;
import model.Resource;
import model.Account;
import model.PMF;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;


public class DeletePez extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String query;PersistenceManager pm = PMF.get().getPersistenceManager();
		com.google.appengine.api.users.User uGoogle=UserServiceFactory.getUserService().getCurrentUser();
		if(AccesoUsuario.dameAcceso(request.getServletPath(),UserServiceFactory.getUserService().getCurrentUser(), 
				  PMF.get().getPersistenceManager(), getServletContext(),request, response)){
						if (request.getParameter("action").equals("Eliminar")) {
							Pez a = pm.getObjectById(Pez.class, Long.parseLong((request.getParameter("PezId"))));
							try {
								pm.deletePersistent(a);
								response.sendRedirect("/peces?action=PezLookup");
							} catch (Exception e) {
							} finally {
								pm.close();
							}
						}
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
