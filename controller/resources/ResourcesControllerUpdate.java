package controller.resources;
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
import model.entity.Users;
@SuppressWarnings("serial")
public class ResourcesControllerUpdate extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String query;PersistenceManager pm = PMF.get().getPersistenceManager();
		com.google.appengine.api.users.User uGoogle=UserServiceFactory.getUserService().getCurrentUser();
		if(uGoogle==null){
			request.setAttribute("mensaje","necesita loguearse");
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
						request.setAttribute("mensaje"," no tiene acceso.");
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/Views/Access/error.jsp");
						dispatcher.forward(request, response);
					}else{
						if(request.getParameter("action").equals("resourcesEditDo")) {
							Key k=KeyFactory.createKey("Resource",new Long(request.getParameter("resourceId")));
							Resource r = pm.getObjectById(Resource.class, k);
							int n=0;
							if(r.getUrl().equals(request.getParameter("url"))){
								n=1;
							}
							query = "select from " + Resource.class.getName()
									+ " where url == '"+request.getParameter("url")+"'";
							List<Resource> recursos = (List<Resource>)pm.newQuery(query).execute();
							if(!(recursos.size()==n)){
								request.setAttribute("mensaje", "Recurso ya Existente");
								request.setAttribute("recurso", r);
								RequestDispatcher dispatcher=getServletContext().getRequestDispatcher("/resources/view");
								dispatcher.forward(request, response);
							}else{
							boolean status=false;
							if(request.getParameter("estatus").equals("Activado")){
								status=true;
							}
							r.setUrl(request.getParameter("url"));
							r.setStatus(status);
							request.setAttribute("resource", r);
							request.setAttribute("mensaje","");
							RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/Views/Resources/view.jsp");
							dispatcher.forward(request, response);
							}
						}else{
							Key k=KeyFactory.createKey("Resource",new Long(request.getParameter("resourceId")));
							Resource r = pm.getObjectById(Resource.class, k);
							request.setAttribute("resource", r);
							RequestDispatcher dispatcher=getServletContext().getRequestDispatcher("/WEB-INF/Views/Resources/update.jsp");
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