package br.com.teste.cleber.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.teste.cleber.model.Client;

public interface IClientRepository extends CrudRepository<Client, Integer>{
	
}
