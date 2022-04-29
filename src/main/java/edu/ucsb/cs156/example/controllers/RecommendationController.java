package edu.ucsb.cs156.example.controllers;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.ucsb.cs156.example.entities.Recommendation;
import edu.ucsb.cs156.example.errors.EntityNotFoundException;
import edu.ucsb.cs156.example.repositories.RecommendationRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;

@Api(description = "Recommendation")
@RequestMapping("/api/recommendation")
@RestController
@Slf4j
public class RecommendationController extends ApiController {

    @Autowired
    RecommendationRepository recommendationRepository;

    @ApiOperation(value = "List all recommendations")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public Iterable<Recommendation> allRecommendations() {
        Iterable<Recommendation> recommendation = recommendationRepository.findAll();
        return recommendation;
    }

    @ApiOperation(value = "Get a single recommendation")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("")
    public Recommendation getById(
            @ApiParam("id") @RequestParam Long id) {
        Recommendation recommendation = recommendationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Recommendation.class, id));

        return recommendation;
    }

    @ApiOperation(value = "Create a new recommendation")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/post")
    public Recommendation postRecommendation(
        @ApiParam("requesterEmail") @RequestParam String requesterEmail,
        @ApiParam("professorEmail") @RequestParam String professorEmail,
        @ApiParam("explanation") @RequestParam String explanation,
        @ApiParam("date requested (in iso format, e.g. YYYY-mm-ddTHH:MM:SS; see https://en.wikipedia.org/wiki/ISO_8601)") @RequestParam("dateRequested") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateRequested,
        @ApiParam("date needed (in iso format, e.g. YYYY-mm-ddTHH:MM:SS; see https://en.wikipedia.org/wiki/ISO_8601)") @RequestParam("dateNeeded") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateNeeded,
        @ApiParam("done") @RequestParam boolean done
        )
        {

        log.info("dateRequested={}", dateRequested);
        log.info("dateNeeded={}", dateNeeded);

        Recommendation recommendation = new Recommendation();
        recommendation.setRequesterEmail(requesterEmail);
        recommendation.setProfessorEmail(professorEmail);
        recommendation.setExplanation(explanation);
        recommendation.setDateRequested(dateRequested);
        recommendation.setDateNeeded(dateNeeded);
        recommendation.setDone(done);

        Recommendation savedRecommendation = recommendationRepository.save(recommendation);

        return savedRecommendation;
    }

    // @ApiOperation(value = "Delete a Recommendation")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // @DeleteMapping("")
    // public Object deleteRecommendation(
    //         @ApiParam("id") @RequestParam Long id) {
    //     Recommendation recommendation = recommendationRepository.findById(id)
    //             .orElseThrow(() -> new EntityNotFoundException(Recommendation.class, id));

    //     recommendationRepository.delete(recommendation);
    //     return genericMessage("Recommendation with id %s deleted".formatted(id));
    // }

    @ApiOperation(value = "Update a single recommendation")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("")
    public Recommendation updateRecommendation(
            @ApiParam("id") @RequestParam Long id,
            @RequestBody @Valid Recommendation incoming) {
        log.info("incoming = {}", incoming);
        Recommendation recommendation = recommendationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Recommendation.class, id));

        log.info("recommendation = {}", recommendation);
        recommendation.setRequesterEmail(incoming.getRequesterEmail());  
        recommendation.setProfessorEmail(incoming.getProfessorEmail());
        recommendation.setExplanation(incoming.getExplanation());
        recommendation.setDateRequested(incoming.getDateRequested());
        recommendation.setDateNeeded(incoming.getDateNeeded());
        recommendation.setDone(incoming.getDone());
        log.info("recommendation after = {}", recommendation);
        recommendationRepository.save(recommendation);

        return recommendation;
    }
}  