package ua.RetroCars.web.Util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.RetroCars.db.DBManager;

/**
 * Filter utility  
 *
 */
public class Filter {
	private static DBManager dbManager=DBManager.getInstance();
	/**
	 * Filters cars at request and transfers selected car's producers and ranks	
	 */
	public static void filterCar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();		
		String[] producers = request.getParameterValues("producerFilter");
		String[] ranks = request.getParameterValues("rankFilter");	
	
		if (producers == null && ranks==null) {
			session.setAttribute("cars", dbManager.selectAllCars());
		} else {
			session.setAttribute("cars",
					dbManager.selectCarsByProducersRanks(producers,ranks));		
		}
		
		session.removeAttribute("producersForJsp");
		session.removeAttribute("ranksForJsp");
		
		if(producers!=null){
			StringBuilder producersForJsp=new StringBuilder(); 
			for(String s:producers){
				producersForJsp.append(","+s+",");
			}
			//System.out.println(producersForJsp.toString());
			session.setAttribute("producersForJsp", producersForJsp.toString());
		}
		if(ranks!=null){
			StringBuilder ranksForJsp=new StringBuilder(); 
			for(String s:ranks){
				ranksForJsp.append(","+s+",");
			}
			//System.out.println(ranksForJsp);
			session.setAttribute("ranksForJsp", ranksForJsp.toString());
		}		
		Sorter.sortCars(request, response);		
	}

}
