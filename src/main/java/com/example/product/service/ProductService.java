package com.example.product.service;

import java.util.List;

import com.example.product.model.Product;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ProductService {

	@PersistenceContext
	EntityManager em;

	public List<Product> findAll() {
		return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
	}

	public Product findById(Long id) {
		return em.find(Product.class, id);
	}

	@Transactional
	public Product create(Product p) {
		em.persist(p);
		return p;
	}

	@Transactional
	public Product update(Long id, Product data) {
		Product existing = em.find(Product.class, id);
		if (existing != null) {
			existing.setName(data.getName());
			existing.setPrice(data.getPrice());
		}
		return existing;
	}

	@Transactional
	public void delete(Long id) {
		Product p = em.find(Product.class, id);
		if (p != null)
			em.remove(p);
	}
}
