package controller.resource;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserServiceFactory;

import model.AccesoUsuario;
import model.PMF;
import model.Resource;


public class CreateResourceRedirect extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		if(AccesoUsuario.dameAcceso(request.getServletPath(),UserServiceFactory.getUserService().getCurrentUser(), 
				  PMF.get().getPersistenceManager(), getServletContext(),request, response)){
			if(request.getParameter("mode").equals("update")){
				Key k = KeyFactory.createKey(Resource.class.getSimpleName(),Long.parseLong(request.getParameter("idRecurso")));
				Resource recurso = pm.getObjectById(Resource.class, k);
				request.setAttribute("recurso", recurso);
				pm.close();
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/JSPResource/ResourceEdit.jsp");
				dispatcher.forward(request, response);
			}else if(request.getParameter("mode").equals("create")){
				pm.close();
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/JSPResource/ResourceCreate.jsp");
				dispatcher.forward(request, response);
			}
		
		}
			
	}
}
