package controller.user;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserServiceFactory;

import model.AccesoUsuario;
import model.Account;
import model.PMF;


public class DeleteUser extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if(AccesoUsuario.dameAcceso(request.getServletPath(),UserServiceFactory.getUserService().getCurrentUser(), 
				  PMF.get().getPersistenceManager(), getServletContext(),request, response)){
			try{
				PersistenceManager pm = PMF.get().getPersistenceManager();
					
				Key k = KeyFactory.createKey(Account.class.getSimpleName(),Long.parseLong(request.getParameter("idUsuario")));
				Account usuario = pm.getObjectById(Account.class, k);
						
				
					pm.currentTransaction().begin();
				
				  pm.deletePersistent(usuario); // object is marked for deletion 
				  pm.currentTransaction().commit(); // object is physically deleted
				  
				  response.sendRedirect("/User/Display");
				}catch(Exception e){
					response.sendRedirect("/User/Display");
				}
			
		
		}
		
	}
}
