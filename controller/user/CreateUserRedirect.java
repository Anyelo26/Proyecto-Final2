package controller.user;

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
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

import model.AccesoUsuario;
import model.Account;
import model.ControlaMenu;
import model.PMF;
import model.Role;



public class CreateUserRedirect extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
			PersistenceManager pm = PMF.get().getPersistenceManager();
			//------------------------------Menu
			ControlaMenu.construyeMenu(pm, UserServiceFactory.getUserService().getCurrentUser(), request);
			
			String query= "select from "+Role.class.getName()+" where status ==true";
			List<Role> roles = (List<Role>)pm.newQuery(query).execute();
			
			if(request.getParameter("mode").equals("update")){
				
				User usuarioGoogle =UserServiceFactory.getUserService().getCurrentUser(); 
				if(usuarioGoogle!=null){
					Key k = KeyFactory.createKey(Account.class.getSimpleName(),Long.parseLong(request.getParameter("idUsuario")));
					Account usuario = pm.getObjectById(Account.class, k);
					
					String queryUsuarioLogeado ="select from "+Account.class.getCanonicalName()+" where correo=='"+usuarioGoogle.getEmail()+"'";
					List<Account> UsuarioLogeado=(List<Account>)pm.newQuery(queryUsuarioLogeado).execute();
					if(usuario.getCorreo().equalsIgnoreCase(usuarioGoogle.getEmail())){
						request.setAttribute("userGoogle", UsuarioLogeado.get(0));
						request.setAttribute("usuario", usuario);
						request.setAttribute("roles", roles);
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/JSPUser/UserEdit.jsp");
						dispatcher.forward(request, response);
					}else{
						
						String query2="select from "+Account.class.getName() +" where correo=='"+usuarioGoogle.getEmail()+"'";
						
						List<Account> users= (List<Account>)pm.newQuery(query2).execute();
						String queryRole="select from "+Role.class.getName()+" where id=="+users.get(0).getIdRole();
						List<Role> roles2 =(List<Role>)pm.newQuery(queryRole).execute();
				
						//SI ES QUE EL QUE QUIERE REALIZAR UNA EDICION ES ADMINISTRADOR, ENTONCES SI SE LE DA PASO
						if(roles2.get(0).getNombre().equalsIgnoreCase("Administrador")){
							request.setAttribute("userGoogle",  UsuarioLogeado.get(0));
							request.setAttribute("usuario", usuario);
							request.setAttribute("roles", roles);
							RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/JSPUser/UserEdit.jsp");
							dispatcher.forward(request, response);
						}else{
							RequestDispatcher rp= getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/UserDenied/NoPermitido.jsp");
							rp.forward(request, response);	
						}
					}
				}else{
					RequestDispatcher rp= getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/UserDenied/NoLogin.jsp");
					rp.forward(request, response);		
				}
				
			
				
			}else if(request.getParameter("mode").equals("create")){

				if(AccesoUsuario.dameAcceso(request.getServletPath(),UserServiceFactory.getUserService().getCurrentUser(), 
						  PMF.get().getPersistenceManager(), getServletContext(),request, response)){
					request.setAttribute("roles", roles);
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/JSPUser/UserCreate.jsp");
					dispatcher.forward(request, response);
				}
			}else if(request.getParameter("mode").equals("register")){
				
				request.setAttribute("correo", request.getParameter("correo"));
				request.setAttribute("roles", roles);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/JSPUser/UserCreate.jsp");
				dispatcher.forward(request, response);
			}
	}
}
