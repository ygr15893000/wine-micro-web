package com.siran.wine.web;


import com.siran.wine.dao.impl.*;
import com.siran.wine.model.Customer;
import com.siran.wine.model.TUser;
import com.siran.wine.service.HelloWorldService;
import com.siran.wine.service.impl.IHelloWordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/welcome")
public class WelcomeController {

	private final Logger logger = LoggerFactory.getLogger(WelcomeController.class);
	private final IHelloWordService helloWorldService;
	@Autowired
	JdbcCustomerDao jdbcCustomerDAO;

	@Autowired
	JdbcRepository jdbcRepository;

	@Autowired
	JdbcRepository2 jdbcRepository2;
	@Autowired
	JdbcRepository3 jdbcRepository3;

	@Autowired
	JdbcBaseDao JdbcBaseDao;
/*
	@Autowired
	TUser2Service tUser2Service;*/
	@Autowired
	public WelcomeController(HelloWorldService helloWorldService) {

		this.helloWorldService = helloWorldService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Map<String, Object> model) {

		logger.debug("index() is executed!");

		model.put("title", helloWorldService.getTitle(""));
		model.put("msg", helloWorldService.getDesc());
		
		return "index";
	}

	@RequestMapping(value = "/hello/{name:.+}", method = RequestMethod.GET)
	public ModelAndView hello(@PathVariable("name") String name) {

		logger.debug("hello() is executed - $name {}", name);

		ModelAndView model = new ModelAndView();
		model.setViewName("index");
		
		model.addObject("title", helloWorldService.getTitle(name));
		model.addObject("msg", helloWorldService.getDesc());
		
		return model;

	}

	@RequestMapping(value = "/findByCustomerId/{id}")
	public @ResponseBody
	Customer findByCustomerId(@PathVariable("id") int id) {

		final Customer customer = jdbcCustomerDAO.findByCustomerId(id);

		return customer;
	}

	@RequestMapping("/findAll3")
	public List<String> findAll3() {
		return jdbcRepository3.findAll();
	}

	@RequestMapping("/findAll4")
	public List<TUser> findAll4() {
		return jdbcRepository3.findAll4();
	}


	@RequestMapping("/getJson")
	public Map getJson() {
		Map map = new HashMap<>();
		map.put("code","1");
		map.put("msg", this.jdbcRepository3.findAll4());
		return map;
	}
	@RequestMapping("/index")
	public ModelAndView testhtml() {
		ModelAndView model = new ModelAndView("index");
		model.addObject("name","value222");
		return model;
	}


	@RequestMapping("/findAll")
	public Map findAll() {
		Map map = new HashMap<>();
		map.put("code", "1");
		map.put("msg", this.JdbcBaseDao.findAll4());
		map.put("data", this.JdbcBaseDao.findAll4());
		return map;
	}

/*	@RequestMapping("/find")
	public Map find() {
		Map map = new HashMap<>();
		map.put("code", "1");
		map.put("msg", this.tUser2Service.find());
		return map;
	}*/
}