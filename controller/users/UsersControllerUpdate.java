package controller.users;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.api.server.spi.auth.common.User;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserServiceFactory;

import controller.controller.PMF;
import model.entity.Access;
import model.entity.Resource;
import model.entity.Roles;
import model.entity.Users;

@SuppressWarnings("serial")
public class UsersControllerUpdate extends HttpServlet {
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
					request.setAttribute("mensaje","No existe el recurso ");
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
						if(request.getParameter("action").equals("userEditDo")) {
							Key k=KeyFactory.createKey(Users.class.getSimpleName(),new Long(request.getParameter("userId")));
							Users u = pm.getObjectById(Users.class, k);
							int n=0;
							if(u.getEmail().equals(request.getParameter("email"))){
								n=1;
							}
							query = "select from " + Users.class.getName()
									+ " where email == '"+request.getParameter("email")+"'";
							List<Users> users = (List<Users>)pm.newQuery(query).execute();
							if(!(users.size()==n)){
								request.setAttribute("mensaje", " el usuario ya Existe");
								request.setAttribute("user", u);
								RequestDispatcher dispatcher=getServletContext().getRequestDispatcher("/users/view");
								dispatcher.forward(request, response);
							}else{
							boolean status=false;
							if(request.getParameter("estatus").equals("Activado")){
								status=true;
							}
							boolean gender=false;
							if(request.getParameter("gender").equals("Masculino")){
								gender=true;
							}
							SimpleDateFormat date=new SimpleDateFormat("yy/MM/dd");
							u.setEmail(request.getParameter("email"));
							u.setStatus(status);
							u.setGender(gender);
							u.setIdRole(new Long(request.getParameter("idRole")));
							try {
								u.setCreated(date.parse(request.getParameter("birth")));
							} catch (ParseException e) {
								e.printStackTrace();
							}
							Key k2=KeyFactory.createKey(Roles.class.getSimpleName(),new Long(request.getParameter("idRole")));
							Roles r = pm.getObjectById(Roles.class, k2);
							request.setAttribute("role",r);
							request.setAttribute("user", u);
							request.setAttribute("mensaje","");
							RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/Views/Users/view.jsp");
							dispatcher.forward(request, response);
							}
						}else{
							Key k=KeyFactory.createKey(Users.class.getSimpleName(),new Long(request.getParameter("userId")));
							Users u = pm.getObjectById(Users.class, k);
							request.setAttribute("user", u);
							query= "select from " + Roles.class.getName();
							List<Roles> roles = (List<Roles>)pm.newQuery(query).execute();
							request.setAttribute("roles", roles);
							RequestDispatcher dispatcher=getServletContext().getRequestDispatcher("/WEB-INF/Views/Users/update.jsp");
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