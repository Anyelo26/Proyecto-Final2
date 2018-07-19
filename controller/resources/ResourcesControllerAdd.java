package controller.resources;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import com.google.appengine.api.users.UserServiceFactory;

import controller.controller.PMF;
import model.entity.Access;
import model.entity.Resource;
import model.entity.Users;
@SuppressWarnings("serial")
public class ResourcesControllerAdd extends HttpServlet {
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
						if(request.getParameter("action")==null||!request.getParameter("action").equals("resourceCreateDo")){
							RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/Views/Resources/add.jsp");
							dispatcher.forward(request, response);
						}else{
							query= "select from " + Resource.class.getName()
									+ " where url == '"+request.getParameter("url")+"'";
							List<Resource> resources = (List<Resource>)pm.newQuery(query).execute();
							if(resources.size()==0){
								boolean estatus=false;
								if(request.getParameter("estatus").equals("Activado")){
									estatus=true;
								}
								Resource r = null;
								try {
									r = new Resource(
									(String)request.getParameter("url"),
									estatus);
									pm.makePersistent(r);
								} catch (Exception e) {
									e.printStackTrace();
								}finally {
									pm.close();
									if(r!=null){
										request.setAttribute("resourceId",r.getId());
										RequestDispatcher dispatcher=getServletContext().getRequestDispatcher("/resources/view");
										dispatcher.forward(request, response);
									}else{
										request.setAttribute("mensaje", "Error ");
										RequestDispatcher dispatcher=getServletContext().getRequestDispatcher("/WEB-INF/Views/Resources/add.jsp");
										dispatcher.forward(request, response);
									}
								}
							}else{
								request.setAttribute("mensaje", "El recurso ya existe");
								RequestDispatcher dispatcher=getServletContext().getRequestDispatcher("/WEB-INF/Views/Resources/add.jsp");
								dispatcher.forward(request, response);
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
