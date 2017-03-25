package br.com.teste.cleber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import br.com.teste.cleber.model.Client;
import br.com.teste.cleber.repository.IClientRepository;

@Controller
public class IndexController extends WebMvcConfigurerAdapter{

	@Autowired
	private IClientRepository repo;
	
	@RequestMapping("/")
	public String index(){
		return "index";
	}
	
	@RequestMapping("/addClients")
	public String addClients(){
		String[] names = {"Lavinia Melo", "Gomes Fábio Oliveira Dias", "André Cavalcanti Carvalho", "Julian Barbosa Gomes Fábio Azevedo", 
				"Oliveira Enzo Silva Rodrigues", "Manuela Cunha Almeida", "Felipe Sousa Dias", "Vitoria Gomes Silva", "Marisa Ribeiro Rodrigues"};
		
		for (int i = 0; i < names.length; i++) {
			Client c = new Client();
			c.setCodClient(i+1);
			c.setNameClient(names[i]);
			repo.save(c);
		}
		return "index";
	}
	
	@RequestMapping(value="/modelXML", produces=MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public String modelXML(){
		String model = "<orders>"+
							"<order>"+
								"<controlNumber>999</controlNumber>"+
								"<dateRegister>2017-03-24</dateRegister>"+
								"<productName>Um produto qualquer</productName>"+
								"<productPrice>5.00</productPrice>"+
								"<quantity>5</quantity>"+
								"<client>"+
									"<codClient>2</codClient>"+
								"</client>"+
							"</order>"+
						"</orders>";
		return model;
	}
	
	@RequestMapping(value = "/modelJSON", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String modelJSON(){
		String model = "["+
						    "{"+
						        "\"controlNumber\": 888,"+
						        "\"dateRegister\": \"2017-03-24\","+
						        "\"productName\": \"Um produto qualquer\","+
						        "\"productPrice\": 15,"+
						        "\"quantity\": 1,"+
						        "\"client\": {"+
						            "\"codClient\": 5"+
						        "}"+
						    "}"+
						"]";
		return model;
	}
	
}
