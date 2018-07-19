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

import model.ControlaMenu;
import model.PMF;
import model.Product;

public class ReadProduct extends HttpServlet {
public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException {
		
		
	
		PersistenceManager pm = PMF.get().getPersistenceManager();
		//------------------------------Menu
		ControlaMenu.construyeMenu(pm, UserServiceFactory.getUserService().getCurrentUser(), req);
		String query= "select from "+Product.class.getName()+" where this.id =="+req.getParameter("idProducto");
		List<Product> productos = (List<Product>) pm.newQuery(query).execute();
		
		Product producto= productos.get(0);
		req.setAttribute("producto", producto);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/JSPProduct/ProductView.jsp");
		dispatcher.forward(req, resp);

	}
}