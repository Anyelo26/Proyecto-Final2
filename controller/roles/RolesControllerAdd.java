package controller.roles;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

import controller.controller.PMF;
import model.entity.Access;
import model.entity.Resource;
import model.entity.Roles;
import model.entity.Users;
public class RolesControllerAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;
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
						request.setAttribute("mensaje","no se ha registrado");
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/Views/Access/error.jsp");
						dispatcher.forward(request, response);
					}else{
						System.out.println(request.getServletPath());
						query="select from "+Resource.class.getName()+" where url=='"+request.getServletPath()+
								"' && status==true";
						List<Resource> rSearch=(List<Resource>)pm.newQuery(query).execute();
						if(rSearch.isEmpty()){
							request.setAttribute("mensaje","No hay recurso");
							RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/Views/Access/error.jsp");
							dispatcher.forward(request, response);
						}else{
							query="select from "+Access.class.getName()+" where idRole=="+
							uSearch.get(0).getIdRole()+" && idUrl=="+rSearch.get(0).getId()+
							" && status==true";
							List<Access> aSearch=(List<Access>)pm.newQuery(query).execute();
							if(aSearch.isEmpty()){
								request.setAttribute("mensaje","Usted no tiene accesso.");
								RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/Views/Access/error.jsp");
								dispatcher.forward(request, response);
							}else{
		if(request.getParameter("action")!=null){	
			if(request.getParameter("action").equals("roleCreateDo")){
				pm = PMF.get().getPersistenceManager();
				query= "select from " + Roles.class.getName()
						+ " where name == '"+request.getParameter("name")+"'";
				List<Roles> roles = (List<Roles>)pm.newQuery(query).execute();
				if(roles.size()==0){
					boolean estatus=false;
					if(request.getParameter("estatus").equals("Activado")){
						estatus=true;
					}
					SimpleDateFormat date=new SimpleDateFormat("yy/MM/dd");
					Roles r = null;
					try {
						Date fecha=new Date();
						r = new Roles((String)request.getParameter("name"), estatus);
						pm.makePersistent(r);
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
						pm.close();
						if(r!=null){
//							request.setAttribute("RoleId",r.getId());
//							RequestDispatcher dispatcher=getServletContext().getRequestDispatcher("/roles/view");
//							dispatcher.forward(request, response);
							response.sendRedirect("/roles/view?roleId="+r.getId());
						}else{
							request.setAttribute("mensaje", "Error Inesperado");
							RequestDispatcher dispatcher=getServletContext().getRequestDispatcher("/WEB-INF/Views/Roles/add.jsp");
							dispatcher.forward(request, response);
						}
					}
				}else{
					request.setAttribute("mensaje", "Rol ya Existe, No se Permiten Duplicados");
					RequestDispatcher dispatcher=getServletContext().getRequestDispatcher("/WEB-INF/Views/Roles/add.jsp");
					dispatcher.forward(request, response);
				}
			}
		}	
		else{
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/Views/Roles/add.jsp");
			dispatcher.forward(request, response);
		}	
							}
						}
					}
				}	
	}






	//		if(request.getParameter("action")!=null){
	//			if (request.getParameter("action").equals("CrearRol")) {
	//				Date date=new Date();
	//				PersistenceManager pm = PMF.get().getPersistenceManager();
	//				Roles rol= new Roles(request.getParameter("Nombre"),date,Boolean.parseBoolean(request.getParameter("Estado")));
	//				try {
	//					pm.makePersistent(rol);
	//				} finally {
	//					pm.close();
	//				}
	//				response.sendRedirect("/roles?action=MostrarRoles&RolId=" + rol.getId());		
	//			}
	//		}
	//		else{
	//			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/Views/Roles/add.jsp");
	//			dispatcher.forward(request, response);
	//		}	

	//}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
