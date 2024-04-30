package br.com.projeto.produto.produto.model;

import br.com.projeto.produto.produto.dtos.ProductRecordDTO;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

//essa classe será a tabela no banco de dados
@Entity
@Table(name = "TABELA_PRODUTO")
public class ProductModel extends RepresentationModel<ProductModel> {

    //os atributos será as colunas da tabela
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idProduct;
    private String name;
    private Double value;

    public ProductModel(ProductRecordDTO product) {
        this.name = product.name();
        this.value = product.value();
    }
    public ProductModel(){}

    public ProductModel(UUID product, ProductRecordDTO productRecordDTO) {
        this.idProduct = product;
        this.name = productRecordDTO.name();
        this.value = productRecordDTO.value();
    }


    public UUID getIdProduct() {
        return idProduct;
    }

    public String getName() {
        return name;
    }

    public Double getValue() {
        return value;
    }
}
