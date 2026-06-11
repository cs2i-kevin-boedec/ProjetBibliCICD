package org.example.bibliotheque.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bibliotheque.dto.emprunt.EmpruntRequest;
import org.example.bibliotheque.dto.emprunt.EmpruntResponse;
import org.example.bibliotheque.entity.Emprunt;
import org.example.bibliotheque.mapper.EmpruntMapper;
import org.example.bibliotheque.service.EmpruntService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emprunts")
@RequiredArgsConstructor
public class EmpruntController {

    private final EmpruntService empruntService;
    private final EmpruntMapper empruntMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmpruntResponse emprunter(@Valid @RequestBody EmpruntRequest request) {
        Emprunt emprunt = empruntService.emprunter(request);
        return empruntMapper.toResponse(emprunt);
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public List<EmpruntResponse> listByUtilisateur(@PathVariable Long utilisateurId) {
        return empruntService.listByUtilisateur(utilisateurId)
                .stream()
                .map(empruntMapper::toResponse)
                .toList();
    }
    @GetMapping("/utilisateur/{utilisateurId}/actifs")
    public List<EmpruntResponse> listActifsByUtilisateur(@PathVariable Long utilisateurId) {
        return empruntService.findActifsByUtilisateur(utilisateurId)
                .stream()
                .map(empruntMapper::toResponse)
                .toList();
    }
}