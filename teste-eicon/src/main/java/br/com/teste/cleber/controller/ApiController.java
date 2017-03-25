package br.com.teste.cleber.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.teste.cleber.model.Order;
import br.com.teste.cleber.repository.IApiRepository;
import br.com.teste.cleber.util.EDiscounts;
import br.com.teste.cleber.wrapper.OrderWrapper;

@Controller
@ResponseBody
public class ApiController {
	
	private Logger logger = Logger.getLogger(getClass());
	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private IApiRepository repo;
	
	@RequestMapping(value="/filterOrder", method=RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, 
			produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<OrderWrapper> getOrderByFilter(@RequestBody OrderWrapper order){
		try {
			
			List<OrderWrapper> orders = new ArrayList<OrderWrapper>();
			
			if (order == null){
				return null;
			}
			
			if (order.getControlNumber() != null) {
				OrderWrapper wrapper = generateWrapper(repo.findOrdersByNumberControl(order.getControlNumber()));
				orders.add(wrapper);
			}else if (order.getDateRegister() != null) {
				List<Order> os = repo.findOrdersByDate(formatDate.parse(order.getDateRegister()));
				for (Order o : os) {
					OrderWrapper wrapper = generateWrapper(o);
					orders.add(wrapper);
				}
			}
			
			return orders;
			
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	@RequestMapping(value="/filterOrder", method=RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<OrderWrapper> getOrderByFilter(){
		try {
			List<OrderWrapper> orders = new ArrayList<OrderWrapper>();
			Iterable<Order> iorders = repo.findAll();
			for (Order o : iorders) {				
				OrderWrapper wrapper = generateWrapper(o);
				orders.add(wrapper);
			}
			return orders;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	// cadastra novas ordens
	@RequestMapping(value="/addOrder", method=RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, 
			produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public String addOrder(@RequestBody List<OrderWrapper> orders){
		try {
			
			List<OrderWrapper> listOrders = new ArrayList<OrderWrapper>();
			
			if ( orders.isEmpty() || orders.size() > 10 ) {
				return "Você deve enviar ao menos 1(hum) pedido e não mais que 10(dez) pedidos por vez";
			}
			
			// verifico se já existe no banco
			for (OrderWrapper order : orders) {
				if ( existOrder(order.getControlNumber()) ) {
					listOrders.add(order);
				}
			}
			
			// se existe algum cadastrado, para o processo e solicito correção
			if ( !listOrders.isEmpty() ) {
				String numbersOrders = "";
				for (OrderWrapper order : listOrders) {
					numbersOrders += " " + order.toString();
				}
				return "O(s) seguinte(s) pedido(s) já está(ão) cadastrados: " + numbersOrders + ", corrija para tentar novamente";
			}
			
			for (OrderWrapper order : orders) {
				
				if ( order.getDateRegister() == null ) {
					order.setDateRegister(formatDate.format(new Date(Calendar.getInstance().getTimeInMillis())));
				}
				if ( order.getQuantity() == null ) {
					order.setQuantity(1);
				}
				double total = calculateTotalPrice(order.getQuantity(), order.getProductPrice());
				order.setTotalPrice(total);
				Order o = new Order();
				o.setClient(order.getClient());
				o.setControlNumber(order.getControlNumber());
				o.setDateRegister(formatDate.parse(order.getDateRegister()));
				o.setProductName(order.getProductName());
				o.setProductPrice(order.getProductPrice());
				o.setQuantity(order.getQuantity());
				o.setTotalPrice(order.getTotalPrice());
				repo.save(o);
			}
			
			return "Pedidos cadastrados com sucesso!";
			
		} catch (Exception e) {
			logger.error(e);
			return e.getMessage();
		}
	}
	
	public String updateOrder(){
		return null;
	}
	
	public String deleteOrder(){
		return null;
	}
	
	// calcula o valor total dos pedidos
	private double calculateTotalPrice(int quantityProduct, double unitPrice){
		double totalValue = -1;
		try {
			if (quantityProduct >= EDiscounts.DISCOUNT_5_PERCENT.quantityTotal && quantityProduct < EDiscounts.DISCOUNT_10_PERCENT.quantityTotal) {
				totalValue = calculatePercent(quantityProduct, unitPrice, EDiscounts.DISCOUNT_5_PERCENT.discountValue);
			}else if (quantityProduct >= EDiscounts.DISCOUNT_10_PERCENT.quantityTotal) {
				totalValue = calculatePercent(quantityProduct, unitPrice, EDiscounts.DISCOUNT_10_PERCENT.discountValue);
			}else{
				totalValue = calculatePercent(quantityProduct, unitPrice, 0);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return totalValue;
	}
	
	// calcula a porcentagem baseado na quantidade de valor total
	private double calculatePercent(int quantityProduct, double unitPrice, int percent) throws Exception{
		try {
			if ( percent == 0 ) {
				return (quantityProduct * unitPrice);
			}
			double totalValue = (quantityProduct * unitPrice);
			return (totalValue - ((totalValue * percent)/100));
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	// verifica se já existe pedido cadastrado
	private boolean existOrder(int numberControl){
		boolean isExist = false;
		try {
			isExist = repo.exists(numberControl);
		} catch (Exception e) {
			logger.error(e);
		}
		return isExist;
	}
	
	// gera o wrapper de retorno
	private OrderWrapper generateWrapper(Order order) {
		String date = formatDate.format(order.getDateRegister());
		OrderWrapper wrapper = new OrderWrapper();
		wrapper.setClient(order.getClient());
		wrapper.setControlNumber(order.getControlNumber());
		wrapper.setDateRegister(date);
		wrapper.setProductName(order.getProductName());
		wrapper.setProductPrice(order.getProductPrice());
		wrapper.setQuantity(order.getQuantity());
		wrapper.setTotalPrice(order.getTotalPrice());
		return wrapper;
	}
	
}
