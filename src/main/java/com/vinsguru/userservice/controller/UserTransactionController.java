package com.vinsguru.userservice.controller;

import com.vinsguru.userservice.dto.TransactionRequestDto;
import com.vinsguru.userservice.dto.TransactionResponseDto;
import com.vinsguru.userservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("user/transaction")
public record UserTransactionController(TransactionService transactionService) {

    @PostMapping
    public Mono<TransactionResponseDto> createTransaction(@RequestBody Mono<TransactionRequestDto> requestDtoMono) {
        return requestDtoMono.flatMap(transactionService::createTransaction);
    }

    @GetMapping
    public Mono<ResponseEntity<List<TransactionResponseDto>>> findAllTransactionsByUserId(@RequestParam String userId) {
        return transactionService.findAllTransactionsByUserId(userId)
                .collectList()
                .handle((transactionResponseDtos, synchronousSink) -> {
                   if (transactionResponseDtos.isEmpty()) {
                       synchronousSink.next(ResponseEntity.notFound().build());
                   } else {
                       synchronousSink.next(ResponseEntity.ok(transactionResponseDtos));
                   }
                });
    }

}
