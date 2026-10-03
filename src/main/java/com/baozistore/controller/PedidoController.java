package com.baozistore.controller;

import com.baozistore.dto.PedidoRequest;
import com.baozistore.model.Cliente;
import com.baozistore.model.Pedido;
import com.baozistore.model.Produto;
import com.baozistore.repository.ClienteRepository;
import com.baozistore.repository.PedidoRepository;
import com.baozistore.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoController(PedidoRepository pedidoRepository,
                            ClienteRepository clienteRepository,
                            ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody PedidoRequest request) {
        if (request.clienteId() == null || request.produtoId() == null) {
            return ResponseEntity.badRequest().body("Informe clienteId e produtoId.");
        }
        if (request.quantidade() == null || request.quantidade() <= 0) {
            return ResponseEntity.badRequest().body("A quantidade deve ser maior que zero.");
        }

        Optional<Cliente> cliente = clienteRepository.findById(request.clienteId());
        if (cliente.isEmpty()) {
            return ResponseEntity.badRequest().body("Cliente nao encontrado.");
        }

        Optional<Produto> produto = produtoRepository.findById(request.produtoId());
        if (produto.isEmpty()) {
            return ResponseEntity.badRequest().body("Produto nao encontrado.");
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente.get());
        pedido.setProduto(produto.get());
        pedido.setQuantidade(request.quantidade());

        Pedido salvo = pedidoRepository.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody PedidoRequest request) {
        Optional<Pedido> existente = pedidoRepository.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Pedido pedido = existente.get();

        if (request.clienteId() != null) {
            Optional<Cliente> cliente = clienteRepository.findById(request.clienteId());
            if (cliente.isEmpty()) {
                return ResponseEntity.badRequest().body("Cliente nao encontrado.");
            }
            pedido.setCliente(cliente.get());
        }

        if (request.produtoId() != null) {
            Optional<Produto> produto = produtoRepository.findById(request.produtoId());
            if (produto.isEmpty()) {
                return ResponseEntity.badRequest().body("Produto nao encontrado.");
            }
            pedido.setProduto(produto.get());
        }

        if (request.quantidade() != null) {
            if (request.quantidade() <= 0) {
                return ResponseEntity.badRequest().body("A quantidade deve ser maior que zero.");
            }
            pedido.setQuantidade(request.quantidade());
        }

        return ResponseEntity.ok(pedidoRepository.save(pedido));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        if (!pedidoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pedidoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
