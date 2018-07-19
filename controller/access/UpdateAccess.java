package controller.access;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

import model.PMF;
import model.Resource;
import model.Role;
import model.AccesoUsuario;
import model.Access;

public class UpdateAccess extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
			PersistenceManager pm = PMF.get().getPersistenceManager();

				String query = "select from "+Access.class.getName()
						+" where id=="+request.getParameter("idAcceso");
				
				List<Access> accesos=(List<Access>)pm.newQuery(query).execute();
				Access acceso= accesos.get(0);
				
				String queryRol= "select from "+Role.class.getName() +" where nombre=='"+request.getParameter("rol")+"'";
				List<Role> roles =(List<Role>)pm.newQuery(queryRol).execute();
				Role rol= roles.get(0);
				
				String queryRecurso= "select from "+Resource.class.getName() +" where url=='"+request.getParameter("recurso")+"'";
				List<Resource> recursos =(List<Resource>)pm.newQuery(queryRecurso).execute();
				Resource recurso= recursos.get(0);
				
				
				acceso.setFecha(new Date());
				acceso.setIdRole(rol.getId());
				acceso.setIdUrl(recurso.getId());
				
				
				response.sendRedirect("/Access/Display");
		
		
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}
}
