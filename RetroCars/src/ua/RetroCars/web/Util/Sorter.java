package ua.RetroCars.web.Util;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.RetroCars.db.Entity.Car;

/**
 * Sort utility
 *
 */
public class Sorter {
	
	/**
	 * Sorts cars at request	
	 */
	public static void sortCars(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String sortCarBy = request.getParameter("sorterCar");
		HttpSession session = request.getSession();
		if (sortCarBy == null&&session.getAttribute("sortCarBy")!=null) {
			sortCarBy=(String) session.getAttribute("sortCarBy");
		}
		if(sortCarBy != null){			
			session.setAttribute("sortCarBy", sortCarBy);
			@SuppressWarnings("unchecked")
			List<Car> list=(List<Car>) session.getAttribute("cars");
			
			switch (sortCarBy) {
			case "byProducer":
				Sorter.sortCarsByProducer(list);
				break;
			case "byProducerReverse":
				Sorter.sortCarsByProducerReverse(list);
				break;
			case "byPrice":
				Sorter.sortCarsByPrice(list);
				break;
			case "byPriceReverse":
				Sorter.sortCarsByPriceReverse(list);
				break;
			case "byRank":
				Sorter.sortCarsByRank(list);
				break;
			case "byRankReverse":
				Sorter.sortCarsByRankReverse(list);
				break;
			}
			//System.out.println(sortCarBy);
		}
	
	}

	
	public static void sortCarsByProducer(List<Car> cars){
		Collections.sort(cars,new Comparator<Car>() {
			@Override
			public int compare(Car c1, Car c2) {				
				return c1.getProducer().compareTo(c2.getProducer());
			}
		});
		
		
	}
	public static void sortCarsByProducerReverse(List<Car> cars){
		Collections.sort(cars,new Comparator<Car>() {
			@Override
			public int compare(Car c1, Car c2) {				
				return c2.getProducer().compareTo(c1.getProducer());
			}
		});		
	}
	
	public static void sortCarsByRank(List<Car> cars){
		Collections.sort(cars,new Comparator<Car>() {
			@Override
			public int compare(Car c1, Car c2) {				
				return c1.getRank().compareTo(c2.getRank());
			}
		});
		
		
	}
	
	public static void sortCarsByRankReverse(List<Car> cars){
		Collections.sort(cars,new Comparator<Car>() {
			@Override
			public int compare(Car c1, Car c2) {				
				return c2.getRank().compareTo(c1.getRank());
			}
		});		
		
	}
	
	public static void sortCarsByPrice(List<Car> cars){
		Collections.sort(cars,new Comparator<Car>() {
			@Override
			public int compare(Car c1, Car c2) {				
				return (int) (c1.getPrice()-c2.getPrice());
			}
		});		
		
	}
	
	public static void sortCarsByPriceReverse(List<Car> cars){
		Collections.sort(cars,new Comparator<Car>() {
			@Override
			public int compare(Car c1, Car c2) {				
				return (int) (c2.getPrice()-c1.getPrice());
			}
		});		
		
	}
	
	

}
