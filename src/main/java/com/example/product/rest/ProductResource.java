package com.example.product.rest;

import java.util.List;

import com.example.product.model.Product;
import com.example.product.service.ProductService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

	@Inject
	ProductService service;

	@GET
	public List<Product> getAll() {
		return service.findAll();
	}

	@GET
	@Path("/{id}")
	public Product getOne(@PathParam("id") Long id) {
		return service.findById(id);
	}

	@POST
	public Product create(Product p) {
		return service.create(p);
	}

	@PUT
	@Path("/{id}")
	public Product update(@PathParam("id") Long id, Product p) {
		return service.update(id, p);
	}

	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") Long id) {
		service.delete(id);
	}
}
