package controller.access;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserServiceFactory;

import controller.controller.PMF;
import model.entity.Access;
import model.entity.Resource;
import model.entity.Roles;
import model.entity.Users;

@SuppressWarnings("serial")
public class AccessControllerUpdate extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String query;PersistenceManager pm = PMF.get().getPersistenceManager();
		com.google.appengine.api.users.User uGoogle=UserServiceFactory.getUserService().getCurrentUser();
		if(uGoogle==null){
			request.setAttribute("mensaje","Necesita loguearse");
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
						request.setAttribute("mensaje","no tiene acceso.");
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/Views/Access/error.jsp");
						dispatcher.forward(request, response);
					}else{
						if(request.getParameter("action").equals("accessEditDo")) {
							Key k=KeyFactory.createKey(Access.class.getSimpleName(),new Long(request.getParameter("accessId")));
							Access a = pm.getObjectById(Access.class, k);
							request.setAttribute("access",a);
							boolean status=false;
							if(request.getParameter("estatus").equals("Activado")){
								status=true;
							}
							a.setIdRole(new Long(request.getParameter("idRole")));
							a.setIdUrl(new Long(request.getParameter("idUrl")));
							a.setStatus(status);
							k=KeyFactory.createKey(Roles.class.getSimpleName(),a.getIdRole());
							Roles role = pm.getObjectById(Roles.class, k);
							request.setAttribute("role",role);
							k=KeyFactory.createKey(Resource.class.getSimpleName(),a.getIdUrl());
							Resource recurso = pm.getObjectById(Resource.class, k);
							request.setAttribute("resource",recurso);
							request.setAttribute("mensaje","");
							RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/Views/Access/view.jsp");
							dispatcher.forward(request, response);
						}else{
							Key k=KeyFactory.createKey(Access.class.getSimpleName(),new Long(request.getParameter("accessId")));
							Access a = pm.getObjectById(Access.class, k);
							request.setAttribute("access",a);
							query = "select from " + Roles.class.getName();
							List<Roles> roles = (List<Roles>)pm.newQuery(query).execute();
							query = "select from " + Resource.class.getName();
							List<Resource> recursos = (List<Resource>)pm.newQuery(query).execute();
							request.setAttribute("roles", roles);
							request.setAttribute("recursos", recursos);
							RequestDispatcher dispatcher=getServletContext().getRequestDispatcher("/WEB-INF/Views/Access/update.jsp");
							dispatcher.forward(request, response);
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
