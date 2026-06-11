package org.example.bibliotheque.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bibliotheque.dto.ouvrage.LivreCreateRequest;
import org.example.bibliotheque.dto.ouvrage.OuvrageResponse;
import org.example.bibliotheque.dto.ouvrage.OuvrageSearchRequest;
import org.example.bibliotheque.dto.ouvrage.RevueCreateRequest;
import org.example.bibliotheque.entity.Ouvrage;
import org.example.bibliotheque.mapper.OuvrageMapper;
import org.example.bibliotheque.service.OuvrageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ouvrages")
@RequiredArgsConstructor
public class OuvrageController {

    private final OuvrageService ouvrageService;
    private final OuvrageMapper ouvrageMapper;

    @PostMapping("/livres")
    @ResponseStatus(HttpStatus.CREATED)
    public OuvrageResponse createLivre(@Valid @RequestBody LivreCreateRequest request) {
        Ouvrage ouvrage = ouvrageService.createLivre(request);
        return ouvrageMapper.toResponse(ouvrage);
    }

    @PostMapping("/revues")
    @ResponseStatus(HttpStatus.CREATED)
    public OuvrageResponse createRevue(@Valid @RequestBody RevueCreateRequest request) {
        Ouvrage ouvrage = ouvrageService.createRevue(request);
        return ouvrageMapper.toResponse(ouvrage);
    }

    @GetMapping
    public List<OuvrageResponse> findAll() {
        return ouvrageService.findAll()
                .stream()
                .map(ouvrageMapper::toResponse)
                .toList();
    }

    @GetMapping("/search")
    public List<OuvrageResponse> rechercher(OuvrageSearchRequest request) {
        return ouvrageService.rechercher(request)
                .stream()
                .map(ouvrageMapper::toResponse)
                .toList();
    }
    @GetMapping("/{id}")
    public OuvrageResponse findById(@PathVariable Long id) {
        Ouvrage ouvrage = ouvrageService.findById(id);
        return ouvrageMapper.toResponse(ouvrage);
    }
}