package br.com.projeto.produto.produto.repository;

import br.com.projeto.produto.produto.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

//criar um repositorio
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {
}
