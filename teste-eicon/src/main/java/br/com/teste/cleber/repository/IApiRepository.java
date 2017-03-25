package br.com.teste.cleber.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.teste.cleber.model.Order;

public interface IApiRepository extends CrudRepository<Order, Integer>{
	
	@Query(value = "SELECT * FROM orders WHERE date_register = ?1", nativeQuery = true)
	public List<Order> findOrdersByDate(Date date);
	
	@Query(value = "SELECT * FROM orders WHERE control_number = ?1", nativeQuery = true)
	public Order findOrdersByNumberControl(Integer numControl);
	
}
