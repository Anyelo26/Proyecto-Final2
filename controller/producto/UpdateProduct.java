package controller.producto;

import java.io.IOException;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

import model.AccesoUsuario;
import model.PMF;
import model.Product;

public class UpdateProduct extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
			PersistenceManager pm = PMF.get().getPersistenceManager();

				String query = "select from "+Product.class.getName()
						+" where id=="+request.getParameter("idProducto");
				
				List<Product> recursos=(List<Product>)pm.newQuery(query).execute();
				Product producto= recursos.get(0);
			
				producto.setNombre(request.getParameter("nombre"));
				producto.setStok(Integer.parseInt(request.getParameter("stok")));
				producto.setPrecio(Double.parseDouble(request.getParameter("precio")));
				
				response.sendRedirect("/Product/Display");
		
		
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}
}