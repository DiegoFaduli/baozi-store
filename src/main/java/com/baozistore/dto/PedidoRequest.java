package com.baozistore.dto;

public record PedidoRequest(Long clienteId, Long produtoId, Integer quantidade) {
}
