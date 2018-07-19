package controller.jaula;

import java.io.IOException;
import java.util.*;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserServiceFactory;

import controller.*;
import model.*;

@SuppressWarnings("serial")
public class JaulaControllerIndex extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		if(request.getParameter("leerID")!=null){
			Jaula jaula=pm.getObjectById(Jaula.class,Long.parseLong(request.getParameter("leerID")));
			ArrayList<ElementoJaula> peces=new ArrayList<ElementoJaula>();
			for(Long l:jaula.getPeces()){
				ElementoJaula ele=pm.getObjectById(ElementoJaula.class,l);
				peces.add(ele);
			}
			ArrayList<String> nombres=new ArrayList<String>();
			for(ElementoJaula elemento: peces){
				Pez pez=pm.getObjectById(Pez.class,elemento.getPez());
				nombres.add(pez.getNombre());
			}
			request.setAttribute("nombres", nombres);
			request.setAttribute("peces", peces);
			RequestDispatcher dispatcher=getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/Jaula/index.jsp");
			dispatcher.forward(request, response);
		}
		else{
			RequestDispatcher dispatcher=getServletContext().getRequestDispatcher("/WEB-INF/JSPFiles/Jaula/index.jsp");
			dispatcher.forward(request, response);	
		}
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		doGet(req,resp);
	}
}