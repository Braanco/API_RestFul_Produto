package br.com.projeto.produto.produto.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//criar uma classe record, para receber os valores
public record ProductRecordDTO(@NotBlank String name,@NotNull Double value) {
}
