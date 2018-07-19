package controller.user;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import model.PMF;
import model.Role;
import model.AccesoUsuario;
import model.Account;
import model.ControlaMenu;

public class CreateUser extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
			PersistenceManager pm = PMF.get().getPersistenceManager();
			//------------------------------Menu
			ControlaMenu.construyeMenu(pm, UserServiceFactory.getUserService().getCurrentUser(), request);
			String queryUsuario= "select from "+ Account.class.getName()+" where correo=='"+request.getParameter("correo")+"'";
			List<Account> cuentas= (List<Account>)pm.newQuery(queryUsuario).execute();
			
			if(cuentas.isEmpty()){
				String query = "select from "+Role.class.getName() +" where nombre =='"+request.getParameter("rol")+"'";
				System.out.println(query);
				List<Role> roles=(List<Role>)pm.newQuery(query).execute();
				Role rol = roles.get(0);
				
				
				Account nuevoUsuario=new Account(request.getParameter("correo"), request.getParameter("ciudad"),
				request.getParameter("telefono"),new Date(),rol.getId());
				
				try {
					pm.makePersistent(nuevoUsuario);
					
					
				} finally {
					pm.close();
				}
				response.sendRedirect("/User/Display");
			}else{
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/UserDenied/UsuarioYaRegistrado.jsp");
				dispatcher.forward(request, response);
			}
		
		
	}
}
