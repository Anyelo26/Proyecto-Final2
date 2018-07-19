package controller.role;

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

import model.AccesoUsuario;
import model.Account;
import model.PMF;
import model.Role;

public class DeleteRole extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if(AccesoUsuario.dameAcceso(request.getServletPath(),UserServiceFactory.getUserService().getCurrentUser(), 
				  PMF.get().getPersistenceManager(), getServletContext(),request, response)){
			try{
				PersistenceManager pm = PMF.get().getPersistenceManager();
				
				Key k = KeyFactory.createKey(Role.class.getSimpleName(),Long.parseLong(request.getParameter("idRol")));
				Role rol = pm.getObjectById(Role.class, k);
				
				if(rol.getNombre().equalsIgnoreCase("invitado")){
					
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/UserDenied/NoEliminarRol.jsp");
					dispatcher.forward(request, response);
				}else{
					String query ="select from " +Account.class.getName()+" where idRole=="+rol.getId(); 
					List<Account> cuentas=(List<Account>)pm.newQuery(query).execute();
					if(cuentas.size()>0){
						System.out.println("hay cuentas en peligro");
						request.setAttribute("id", rol.getId());
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/UserDenied/RiezgoUsuario.jsp");
						dispatcher.forward(request, response);
					}else{
						pm.currentTransaction().begin();
						
						  pm.deletePersistent(rol); // object is marked for deletion 
						  pm.currentTransaction().commit(); // object is physically deleted
						  
						  response.sendRedirect("/Role/Display");
					}
					
				}
				
			
			}catch(Exception e){
				response.sendRedirect("/RoleDisplay");
			}
		}
	}
}
