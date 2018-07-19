package controller.resource;

import java.io.IOException;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

import model.AccesoUsuario;
import model.ControlaMenu;
import model.PMF;
import model.Resource;

public class UpdateResource extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
			PersistenceManager pm = PMF.get().getPersistenceManager();
			
				String query = "select from "+Resource.class.getName()
						+" where id=="+request.getParameter("idRecurso");
				
				List<Resource> recursos=(List<Resource>)pm.newQuery(query).execute();
				Resource recurso= recursos.get(0);
				System.out.println(recurso);
				recurso.setUrl(request.getParameter("nombre"));
				response.sendRedirect("/Resource/Display");
		
		
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}
}
