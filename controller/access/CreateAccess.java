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
import model.AccesoUsuario;
import model.Access;
import model.Role;
public class CreateAccess extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
			
			PersistenceManager pm = PMF.get().getPersistenceManager();
			String queryRol= "select from "+Role.class.getName() +" where nombre=='"+request.getParameter("rol")+"'";
			List<Role> roles =(List<Role>)pm.newQuery(queryRol).execute();
			Role rol= roles.get(0);
			
			String queryRecurso= "select from "+Resource.class.getName() +" where url=='"+request.getParameter("recurso")+"'";
			List<Resource> recursos =(List<Resource>)pm.newQuery(queryRecurso).execute();
			Resource recurso= recursos.get(0);
			Date fecha= new Date();
			Access nuevoAcceso=new Access(rol.getId(),recurso.getId(),fecha,rol.getNombre(),recurso.getUrl());
			
			try {
				pm.makePersistent(nuevoAcceso);
			} finally {
				pm.close();
			}
			response.sendRedirect("/Access/Display");
		
	
	}
}
