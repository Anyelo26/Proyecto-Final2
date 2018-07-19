package controller.peces;
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

import model.entity.Access;
import model.entity.Pez;
import model.entity.Resource;
import model.entity.Users;
import controller.controller.PMF;
public class UpdatePez  extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String query;PersistenceManager pm = PMF.get().getPersistenceManager();
		com.google.appengine.api.users.User uGoogle=UserServiceFactory.getUserService().getCurrentUser();
		if(uGoogle==null){
			request.setAttribute("mensaje","Necesita Loguearse");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/Views/Access/error.jsp");
			dispatcher.forward(request, response);
		}else{
			query="select from "+Users.class.getName()+" where email=='"+uGoogle.getEmail()+
					"' && status==true";
			List<Users> uSearch=(List<Users>)pm.newQuery(query).execute();
			if(uSearch.isEmpty()){
				request.setAttribute("mensaje","no esta registrado");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/Views/Access/error.jsp");
				dispatcher.forward(request, response);
			}else{
				System.out.println(request.getServletPath());
				query="select from "+Resource.class.getName()+" where url=='"+request.getServletPath()+
						"' && status==true";
				List<Resource> rSearch=(List<Resource>)pm.newQuery(query).execute();
				if(rSearch.isEmpty()){
					request.setAttribute("mensaje","No existe el recurso");
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/Views/Access/error.jsp");
					dispatcher.forward(request, response);
				}else{
					query="select from "+Access.class.getName()+" where idRole=="+
							uSearch.get(0).getIdRole()+" && idUrl=="+rSearch.get(0).getId()+
							" && status==true";
					List<Access> aSearch=(List<Access>)pm.newQuery(query).execute();
					if(aSearch.isEmpty()){
						request.setAttribute("mensaje","no tiene acceso");
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/Views/Access/error.jsp");
						dispatcher.forward(request, response);
					}else{
						if (request.getParameter("action").equals("Actualizar")) {
							Key k = KeyFactory.createKey(Pez.class.getSimpleName(),new Long(request.getParameter("PezId")).longValue());
							Pez a = pm.getObjectById(Pez.class, k);
							request.setAttribute("Pez", a);
							RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/Views/Peces/Update.jsp");
							dispatcher.forward(request, response);
						}
						if (request.getParameter("action").equals("Update")) {
							Pez pez = pm.getObjectById(Pez.class, Long.parseLong((request.getParameter("PezId"))));
							pez.setFamilia(request.getParameter("Familia"));
							pez.setGenero(request.getParameter("Genero"));
							pez.setNombre(request.getParameter("Nombre"));
							pez.setOrden(request.getParameter("Orden"));
							pez.setPrecio(request.getParameter("Precio"));
							pez.setStock(request.getParameter("Stock"));
							response.sendRedirect("/peces?action=PezLookup");
						}}}}}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
