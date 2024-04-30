package br.com.projeto.produto.produto.controllers;

//Classe para fazer o controle da aplicaçao

import br.com.projeto.produto.produto.dtos.ProductRecordDTO;
import br.com.projeto.produto.produto.model.ProductModel;
import br.com.projeto.produto.produto.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    //Fazer o metodo post para cadastrar algo
    @PostMapping("/product")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDTO product) {

        //precisa transformar o dto em um model
        var productModel = new ProductModel(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));

    }

    //fazer um metodo para mostrar todo o conteudo
    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProduct() {
        List<ProductModel> productModelList = productRepository.findAll();

        //criar um link para acessar o metodo que mostra apenas um item
        if (!productModelList.isEmpty()){
            for (ProductModel productModel : productModelList){
                UUID id  = productModel.getIdProduct();
                productModel.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());

            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(productModelList);
    }

    //mostrar apenas um produto
    @GetMapping("/product/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID idProduct) {
        Optional<ProductModel> product = productRepository.findById(idProduct);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found");
        }
        product.get().add(linkTo(methodOn(ProductController.class).getAllProduct()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(product.get());
    }

    //metodo para alterar um conteudo já existente
    @PutMapping("/product/{id}")
    public ResponseEntity<Object> alterProduct(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProductRecordDTO productRecordDTO) {
        Optional<ProductModel> productO = productRepository.findById(id);
        if (productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");

        }
        var product = productO.get().getIdProduct();
        var productModel = new ProductModel(product, productRecordDTO);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    //metodo para deletar
    @DeleteMapping("/product/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> productO = productRepository.findById(id);
        if (!productO.isEmpty()) {
            var produtc = productO.get();
            productRepository.delete(produtc);
            return ResponseEntity.status(HttpStatus.OK).body("delete");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found");
    }


}
