package controller.access;
import java.io.IOException;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserServiceFactory;
import model.PMF;
import model.Resource;
import model.AccesoUsuario;
import model.Access;
import model.Role;
public class CreateAccessRedirect extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(AccesoUsuario.dameAcceso(request.getServletPath(),UserServiceFactory.getUserService().getCurrentUser(), 
			  PMF.get().getPersistenceManager(), getServletContext(),request, response)){
			PersistenceManager pm = PMF.get().getPersistenceManager();
			String queryRoles= "select from "+ Role.class.getName() +" where status==true";
			List<Role> roles = (List<Role>)pm.newQuery(queryRoles).execute();
			request.setAttribute("roles",roles);
			String queryRecursos= "select from "+ Resource.class.getName() +" where status==true";
			List<Resource> recursos = (List<Resource>)pm.newQuery(queryRecursos).execute();
			request.setAttribute("recursos",recursos);
			if(request.getParameter("mode").equals("update")){
				Key k = KeyFactory.createKey(Access.class.getSimpleName(),Long.parseLong(request.getParameter("idAcceso")));
				Access acceso = pm.getObjectById(Access.class, k);
				request.setAttribute("acceso", acceso);
	
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/JSPAccess/AccessEdit.jsp");
				dispatcher.forward(request, response);
			}else if(request.getParameter("mode").equals("create")){
	
				System.out.println("redireccionando");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/JSPAccess/AccessCreate.jsp");
				dispatcher.forward(request, response);
			}
		}
	}
}
