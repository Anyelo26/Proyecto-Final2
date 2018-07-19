package controller.producto;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import model.*;

public class CreateProduct extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
			PersistenceManager pm = PMF.get().getPersistenceManager();
				
			
			Product nuevoProducto=new Product(request.getParameter("nombre"),
					Double.parseDouble(request.getParameter("precio")),
							Integer.parseInt(request.getParameter("stok")));
			
			try {
				pm.makePersistent(nuevoProducto);
			} finally {
				pm.close();
			}
			response.sendRedirect("/Product/Display");
		
	}
}