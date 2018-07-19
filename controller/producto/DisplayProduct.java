
package controller.producto;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

import model.AccesoUsuario;
import model.PMF;
import model.Product;
import model.ControlaMenu;

public class DisplayProduct extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException {
		
		
		if(AccesoUsuario.dameAcceso(req.getServletPath(),UserServiceFactory.getUserService().getCurrentUser(), 
				  PMF.get().getPersistenceManager(), getServletContext(),req, resp)){
			PersistenceManager pm = PMF.get().getPersistenceManager();
			//------------------------------Menu
			ControlaMenu.construyeMenu(pm, UserServiceFactory.getUserService().getCurrentUser(), req);
			System.out.println("holaaaa");
			String query= "select from "+Product.class.getName()+" where status==true";
			List<Product> productos = (List<Product>)pm.newQuery(query).execute();
			
			req.setAttribute("productos",productos);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/JSPProduct/ProductDisplay.jsp");
			dispatcher.forward(req, resp);
		}
	}
}