package org.example.bibliotheque.controller;

import lombok.RequiredArgsConstructor;
import org.example.bibliotheque.dto.emprunt.EmpruntResponse;
import org.example.bibliotheque.mapper.EmpruntMapper;
import org.example.bibliotheque.service.RetardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/retards")
@RequiredArgsConstructor
public class RetardController {

    private final RetardService retardService;
    private final EmpruntMapper empruntMapper;

    @GetMapping
    public List<EmpruntResponse> listRetards() {
        return retardService.listRetards()
                .stream()
                .map(empruntMapper::toResponse)
                .toList();
    }

    @PostMapping("/relances")
    public void envoyerRelances() {
        retardService.envoyerRelances();
    }
}