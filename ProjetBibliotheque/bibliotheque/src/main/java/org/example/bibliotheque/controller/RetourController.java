package org.example.bibliotheque.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bibliotheque.dto.emprunt.EmpruntResponse;
import org.example.bibliotheque.dto.retour.RetourRequest;
import org.example.bibliotheque.entity.Emprunt;
import org.example.bibliotheque.mapper.EmpruntMapper;
import org.example.bibliotheque.service.RetourService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/retours")
@RequiredArgsConstructor
public class RetourController {

    private final RetourService retourService;
    private final EmpruntMapper empruntMapper;

    @PostMapping
    public EmpruntResponse retourner(@Valid @RequestBody RetourRequest request) {
        Emprunt emprunt = retourService.retourner(request);
        return empruntMapper.toResponse(emprunt);
    }
}