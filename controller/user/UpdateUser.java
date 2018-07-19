package controller.user;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.PMF;
import model.Role;


public class UpdateUser extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
				PersistenceManager pm = PMF.get().getPersistenceManager();

				String query = "select from "+Account.class.getName()
						+" where id=="+request.getParameter("idUsuario");
				
				List<Account> usuarios=(List<Account>)pm.newQuery(query).execute();
				Account usuario= usuarios.get(0);
				
				usuario.setCity(request.getParameter("ciudad"));
				usuario.setCorreo(request.getParameter("correo"));
				
				String queryRol = "select from "+Role.class.getName() +" where nombre =='"+request.getParameter("rol")+"'";
				List<Role> roles=(List<Role>)pm.newQuery(queryRol).execute();
				Role rol = roles.get(0);
				usuario.setIdRole(rol.getId());
				usuario.setPhone(request.getParameter("telefono"));
				usuario.setUltimaActualizacion(new Date());
				
				response.sendRedirect("/User/Display");
		
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}
}
