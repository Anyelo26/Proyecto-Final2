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

public class RegisterGoogleUser  extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
			UserService us = UserServiceFactory.getUserService();
			User usuario = us.getCurrentUser();
			if(usuario==null){
				response.sendRedirect(us.createLoginURL("/User/Register"));
			}else{
				String gmail=usuario.getEmail();
				response.sendRedirect(us.createLogoutURL("/User/Create?mode=register&correo="+gmail));
			}
		
		
	}
}
