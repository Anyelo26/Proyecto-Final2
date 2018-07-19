package controller.user;

import java.io.IOException;
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

import model.Account;
import model.ControlaMenu;
import model.PMF;


public class LoginUser extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserService us = UserServiceFactory.getUserService();
		User usuario = us.getCurrentUser();
		if(usuario==null){
			response.sendRedirect(us.createLoginURL("/UserLogin"));
		}else{
			PersistenceManager pm = PMF.get().getPersistenceManager();
			
			
			//------------------------------Menu
			ControlaMenu.construyeMenu(pm, UserServiceFactory.getUserService().getCurrentUser(), request);
			
			String query= "select from "+Account.class.getName()+" where correo=='"+usuario.getEmail()+"'";
			List<Account> cuenta = (List<Account>)pm.newQuery(query).execute();
			
			if(cuenta.isEmpty()){
			
				response.sendRedirect(us.createLogoutURL("/Logout"));
	
			}else{
				request.setAttribute("usuario", usuario);
				RequestDispatcher rq= getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/JSPUser/UserLogin.jsp");
				rq.forward(request, response);
			}
			
		}
	
		
	}
}
