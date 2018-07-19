package controller.user;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

import model.Account;
import model.ControlaMenu;
import model.PMF;


public class ReadUser extends HttpServlet {
public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ControlaMenu.construyeMenu(pm, UserServiceFactory.getUserService().getCurrentUser(), req);
		String query= "select from "+Account.class.getName()+" where this.id =="+req.getParameter("idUsuario");
		List<Account> usuarios = (List<Account>) pm.newQuery(query).execute();
		
		Account usuario= usuarios.get(0);
		req.setAttribute("usuario", usuario);
		System.out.println("hola");
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/JSPUser/UserView.jsp");
		dispatcher.forward(req, resp);

	}
}
