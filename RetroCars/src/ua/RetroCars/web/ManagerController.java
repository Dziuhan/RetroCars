package ua.RetroCars.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.RetroCars.db.DBManager;
import ua.RetroCars.db.Entity.Order;
/**
 * Controller for manager's part of application
 * 
 *
 */
@WebServlet("/ManagerController")
public class ManagerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(ManagerController.class);

	private DBManager dbManager = DBManager.getInstance();
	public void init() throws ServletException {
		super.init();
		dbManager = DBManager.getInstance();
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String command=request.getParameter("commandManager");
		LOG.trace("command - "+command+" --- request from get"+request);
		
		if(command==null){
			List<Order> orders=dbManager.selectAllOrders();
			request.setAttribute("orders", orders);
			request.getRequestDispatcher("/WEB-INF/jsp/manager/MainManager.jsp").forward(request, response);
		}else if(command.equals("new orders")){
			findOrderByState(request, response, Order.STATE_NEW_ORDER);
		}else if(command.equals("waiting for payment")){
			findOrderByState(request, response, Order.STATE_WAITING_FOR_PAYMENT);
		}else if(command.equals("rejected orders")){
			findOrderByState(request, response, Order.STATE_REJECTED);
		}else if(command.equals("paid orders")){
			findOrderByState(request, response, Order.STATE_PAID);
		}else if(command.equals("crush orders")){
			findOrderByState(request, response, Order.STATE_CRUSH_CAR);
		}else if(command.equals("closed orders")){
			findOrderByState(request, response, Order.STATE_CLOSED);
		}else if(command.equals("close orders")){
			List<Order> orders=dbManager.selectWillCloseOrders();
			request.setAttribute("orders", orders);
			request.setAttribute("commandManager",command);
			request.getRequestDispatcher("/WEB-INF/jsp/manager/MainManager.jsp").forward(request, response);
		}else if(command.equals("all orders")){
			List<Order> orders=dbManager.selectAllOrders();
			request.setAttribute("orders", orders);
			request.setAttribute("commandManager",command);
			request.getRequestDispatcher("/WEB-INF/jsp/manager/MainManager.jsp").forward(request, response);
		}
		
		
		
	}
	
	private void findOrderByState(HttpServletRequest request, HttpServletResponse response,String state)
			throws ServletException, IOException {
		List<Order> orders=dbManager.selectAllOrdersByState(state);
		String command=request.getParameter("commandManager");
		request.setAttribute("orders", orders);
		request.setAttribute("commandManager",command);
		request.getRequestDispatcher("/WEB-INF/jsp/manager/MainManager.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String command=request.getParameter("commandManager");
		LOG.trace("command - "+command+" --- request from post"+request);
		
		String causeOrder=request.getParameter("causeOrderManager");
		Integer idOrder=Integer.parseInt(request.getParameter("idOrderManager"));
		if(command.equals("noAcceptOrder")){
			dbManager.updateRejectOrder(idOrder,causeOrder);
			response.sendRedirect("ManagerController");
		}else if(command.equals("acceptOrder")){			
			dbManager.updateWaitPaymantOrder(idOrder, causeOrder);
			response.sendRedirect("ManagerController");			
		}else if(command.equals("closeOrder")){
			dbManager.updateCloseOrder(idOrder, causeOrder);
			response.sendRedirect("ManagerController");	
			
		}else if(command.equals("crushCarOrder")){
			Double priceCrush= Double.parseDouble(request.getParameter("priceCrush"));
			dbManager.updateCrushCarOrder(idOrder,priceCrush, causeOrder);
			response.sendRedirect("ManagerController");	
			
		}else if(command.equals("payCrushCarOrder")){
			dbManager.updatePayCrushCarOrder(idOrder, causeOrder);
			response.sendRedirect("ManagerController");			
		}

	}

}
