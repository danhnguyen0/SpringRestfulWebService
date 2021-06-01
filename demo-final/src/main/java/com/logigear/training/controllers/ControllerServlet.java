package com.logigear.training.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.logigear.training.entities.Employee;
import com.logigear.training.repositories.EmployeeRepository;
@Controller
public class ControllerServlet extends HttpServlet {
	private EmployeeRepository repository;
	private static final long serialVersionUID = 1L;
	
	@RequestMapping("/")
	public String indexPage() {
		return "listEmployee";
	}
	
	public void init() {
		String username = getServletContext().getInitParameter("username");
		String password = getServletContext().getInitParameter("password");
		String fullName = getServletContext().getInitParameter("fullName");
		String role = getServletContext().getInitParameter("role");
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String action = request.getServletPath();
		
		switch(action) {
		default:
			listEmployee(request,response);
			break;
		}
	}
	
	public void listEmployee(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		List<Employee> list = repository.getAll();
		request.setAttribute("employee", list);
		RequestDispatcher dispatcher = request.getRequestDispatcher("listEmployee.jsp");
		dispatcher.forward(request, response);
	}
	
	public void showEmployee(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("updateEmployee.jsp");
		dispatcher.forward(request, response);
	}
	
	public void updateEmployee(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String username = request.getParameter("username");
		Employee employee = repository.getEmployeeByUsername(username);
		request.setAttribute("employee", employee);
		RequestDispatcher dispatcher = request.getRequestDispatcher("updateEmployee.jsp");
		dispatcher.forward(request, response);
	}
	
	public void addEmployee(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String fullName = request.getParameter("fullName");
		String role = request.getParameter("role");
		
		Employee employee = new Employee(username, password, fullName, role);
		repository.add(employee);
		response.sendRedirect("list");
	}
}
