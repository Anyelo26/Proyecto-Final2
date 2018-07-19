package controller.roles;
import java.io.IOException;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserServiceFactory;

import controller.controller.PMF;
import model.entity.*;
public class RolesControllerDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
				com.google.appengine.api.users.User uGoogle=UserServiceFactory.getUserService().getCurrentUser();
				if(uGoogle==null){
					request.setAttribute("mensaje","necesita loguearse");
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/Views/Access/error.jsp");
					dispatcher.forward(request, response);
				}else{
					String query="select from "+Users.class.getName()+" where email=='"+uGoogle.getEmail()+
							"' && status==true";
					List<Users> uSearch=(List<Users>)pm.newQuery(query).execute();
					if(uSearch.isEmpty()){
						request.setAttribute("mensaje"," no esta registrado");
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/Views/Access/error.jsp");
						dispatcher.forward(request, response);
					}else{
						System.out.println(request.getServletPath());
						query="select from "+Resource.class.getName()+" where url=='"+request.getServletPath()+
								"' && status==true";
						List<Resource> rSearch=(List<Resource>)pm.newQuery(query).execute();
						if(rSearch.isEmpty()){
							request.setAttribute("mensaje","No existe el recurso ");
							RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/Views/Access/error.jsp");
							dispatcher.forward(request, response);
						}else{
							query="select from "+Access.class.getName()+" where idRole=="+
							uSearch.get(0).getIdRole()+" && idUrl=="+rSearch.get(0).getId()+
							" && status==true";
							List<Access> aSearch=(List<Access>)pm.newQuery(query).execute();
							if(aSearch.isEmpty()){
								request.setAttribute("mensaje","no tiene acceso.");
								RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/Views/Access/error.jsp");
								dispatcher.forward(request, response);
							}else{
		if(request.getParameter("action").equals("roleDelete")){
						Key k=KeyFactory.createKey(Roles.class.getName(),new Long(request.getParameter("roleId")));
			Roles r = pm.getObjectById(Roles.class, Long.parseLong(request.getParameter("roleId")));
			String query1= "select from " + Users.class.getName()+" where idRole=="+r.getId();
			List<Users> users = (List<Users>)pm.newQuery(query).execute();
			if(users.size()>0){
				request.setAttribute("mensaje","borre a todos los usuarios con este rol antes de borrarlo");
				RequestDispatcher rd= getServletContext().getRequestDispatcher("/roles/view");
				rd.forward(request,response);
			}else{
				query= "select from " + Access.class.getName()+" where idRole=="+r.getId();
				List<Access> access = (List<Access>)pm.newQuery(query).execute();
				if(access.size()>0){
					request.setAttribute("mensaje","borre todos los accesos");
					RequestDispatcher rd= getServletContext().getRequestDispatcher("/roles/view");
					rd.forward(request,response);
				}else{
					try {
						pm.deletePersistent(r);
						response.sendRedirect("/roles?action=MostrarRoles");
					} catch (Exception e) {
					} finally {
						pm.close();
					}
					//			response.sendRedirect("/roles");
				}
			}	
		}
	}
							}
						}
					}
				}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}	
}
