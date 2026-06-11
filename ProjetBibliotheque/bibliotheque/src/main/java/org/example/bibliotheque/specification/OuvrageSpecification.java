package org.example.bibliotheque.specification;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.bibliotheque.dto.ouvrage.OuvrageSearchRequest;
import org.example.bibliotheque.entity.Livre;
import org.example.bibliotheque.entity.Ouvrage;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class OuvrageSpecification {

    private OuvrageSpecification() {
    }

    public static Specification<Ouvrage> withCriteria(OuvrageSearchRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getTitre() != null && !request.getTitre().isBlank()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("titre")),
                        "%" + request.getTitre().toLowerCase() + "%"
                ));
            }

            if (request.getTheme() != null && !request.getTheme().isBlank()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("theme")),
                        "%" + request.getTheme().toLowerCase() + "%"
                ));
            }

            if ((request.getAuteur() != null && !request.getAuteur().isBlank())
                    || request.getAnneePublication() != null) {

                Root<Livre> livreRoot = criteriaBuilder.treat(root, Livre.class);

                if (request.getAuteur() != null && !request.getAuteur().isBlank()) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(livreRoot.get("auteur")),
                            "%" + request.getAuteur().toLowerCase() + "%"
                    ));
                }

                if (request.getAnneePublication() != null) {
                    predicates.add(criteriaBuilder.equal(
                            livreRoot.get("anneePublication"),
                            request.getAnneePublication()
                    ));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}