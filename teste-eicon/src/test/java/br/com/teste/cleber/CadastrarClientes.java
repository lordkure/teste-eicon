package br.com.teste.cleber;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.teste.cleber.model.Client;
import br.com.teste.cleber.repository.IClientRepository;

public class CadastrarClientes {

	@Autowired
	private IClientRepository repo;
	
	public CadastrarClientes() {
		String[] names = {"Lavinia Melo", "Gomes Fábio Oliveira Dias", "André Cavalcanti Carvalho", "Julian Barbosa Gomes Fábio Azevedo", 
				"Oliveira Enzo Silva Rodrigues", "Manuela Cunha Almeida", "Felipe Sousa Dias", "Vitoria Gomes Silva", "Marisa Ribeiro Rodrigues"};
		
		for (int i = 0; i < names.length; i++) {
			Client c = new Client();
			c.setCodClient(i+1);
			c.setNameClient(names[i]);
			repo.save(c);
		}
	}
	
	public static void main(String[] args) {		
		new CadastrarClientes();		
	}
	
}
