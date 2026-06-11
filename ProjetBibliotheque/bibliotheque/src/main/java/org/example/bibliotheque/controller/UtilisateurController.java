package org.example.bibliotheque.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bibliotheque.dto.utilisateur.CreateUtilisateurRequest;
import org.example.bibliotheque.dto.utilisateur.UtilisateurResponse;
import org.example.bibliotheque.entity.Utilisateur;
import org.example.bibliotheque.mapper.UtilisateurMapper;
import org.example.bibliotheque.service.UtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    private final UtilisateurMapper utilisateurMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UtilisateurResponse create(@Valid @RequestBody CreateUtilisateurRequest request) {
        Utilisateur utilisateur = utilisateurService.create(request);
        return utilisateurMapper.toResponse(utilisateur);
    }
    @GetMapping("/{id}")
    public UtilisateurResponse findById(@PathVariable Long id) {
        Utilisateur utilisateur = utilisateurService.findById(id);
        return utilisateurMapper.toResponse(utilisateur);
    }
    @GetMapping
    public List<UtilisateurResponse> findAll() {
        return utilisateurService.findAll()
                .stream()
                .map(utilisateurMapper::toResponse)
                .toList();
    }
    @GetMapping("/by-login/{login}")
    public UtilisateurResponse findByLogin(@PathVariable String login) {
        Utilisateur utilisateur = utilisateurService.findByLogin(login);
        return utilisateurMapper.toResponse(utilisateur);
    }
    @GetMapping("/search")
    public List<UtilisateurResponse> search(@RequestParam String valeur) {
        return utilisateurService.searchByNomOrPrenom(valeur)
                .stream()
                .map(utilisateurMapper::toResponse)
                .toList();
    }
}