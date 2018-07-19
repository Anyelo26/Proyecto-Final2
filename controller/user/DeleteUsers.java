package controller.user;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserServiceFactory;

import model.AccesoUsuario;
import model.Access;
import model.Account;
import model.ControlaMenu;
import model.PMF;
import model.Role;

public class DeleteUsers extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		//------------------------------Menu
				ControlaMenu.construyeMenu(pm, UserServiceFactory.getUserService().getCurrentUser(), request);
		
			try{
				
				
				String queryUsuarios ="select from " +Account.class.getName()+" where idRole=="+request.getParameter("id"); 
				List<Account> cuentas=(List<Account>)pm.newQuery(queryUsuarios).execute();
				String queryAccesos ="select from " +Access.class.getName()+" where idRole=="+request.getParameter("id"); 
				List<Access> accesos=(List<Access>)pm.newQuery(queryAccesos).execute();
				
				for (int i = 0; i < cuentas.size(); i++) {
					Key k = KeyFactory.createKey(Account.class.getSimpleName(),cuentas.get(i).getId());
					Account usuario = pm.getObjectById(Account.class, k);
					pm.currentTransaction().begin();
					
					  pm.deletePersistent(usuario); // object is marked for deletion 
					  pm.currentTransaction().commit(); // object is physically deleted
					  
				}
				for (int i = 0; i < accesos.size(); i++) {
					Key k = KeyFactory.createKey(Access.class.getSimpleName(),accesos.get(i).getId());
					Access acceso = pm.getObjectById(Access.class, k);
					pm.currentTransaction().begin();
					
					  pm.deletePersistent(acceso); // object is marked for deletion 
					  pm.currentTransaction().commit(); // object is physically deleted
					  
				}
				
				
				Key k = KeyFactory.createKey(Role.class.getSimpleName(),Long.parseLong(request.getParameter("id")));
				Role rol = pm.getObjectById(Role.class, k);
				pm.currentTransaction().begin();
				  pm.deletePersistent(rol); // object is marked for deletion 
				  pm.currentTransaction().commit(); // object is physically deleted
				
				  response.sendRedirect("/Role/Display");
			}catch(Exception e){
				response.sendRedirect("/Role/Display");
			}
			
		
		
	
	}
}
