package edu.ucsb.cs156.example.controllers;

import edu.ucsb.cs156.example.entities.MenuItemReview;
import edu.ucsb.cs156.example.errors.EntityNotFoundException;
import edu.ucsb.cs156.example.repositories.MenuItemReviewRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
<<<<<<< HEAD
=======

>>>>>>> efc3fe6 (added controller for MenuItemReview)
import java.time.LocalDateTime;

@Api(description = "MenuItemReview")
@RequestMapping("/api/MenuItemReview")
@RestController
@Slf4j
public class MenuItemReviewController extends ApiController {

    @Autowired
    MenuItemReviewRepository menuItemReviewRepository;

    @ApiOperation(value = "List all menu item reviews")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public Iterable<MenuItemReview> allReviews() {
        Iterable<MenuItemReview> reviews = menuItemReviewRepository.findAll();
        return reviews;
    }

    @ApiOperation(value = "Get a single review")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("")
    public MenuItemReview getById(
            @ApiParam("id") @RequestParam Long id) {
        MenuItemReview menuItemReview = menuItemReviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MenuItemReview.class, id));

        return menuItemReview;
    }

    @ApiOperation(value = "Create a new review")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/post")
    public MenuItemReview postMenuItemReview(
        @ApiParam("itemId") @RequestParam Long itemId,
        @ApiParam("reviewerEmail") @RequestParam String reviewerEmail,
        @ApiParam("stars") @RequestParam int stars,
        @ApiParam("commnets") @RequestParam String comments,
        @ApiParam("date (in iso format, e.g. YYYY-mm-ddTHH:MM:SS; see https://en.wikipedia.org/wiki/ISO_8601)") @RequestParam("localDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateReviewed)
        throws JsonProcessingException {
    
    log.info("dateReviewed={}", dateReviewed);

    MenuItemReview menuItemReview = new MenuItemReview();
    menuItemReview.setItemId(itemId);
    menuItemReview.setReviewerEmail(reviewerEmail);
    menuItemReview.setStars(stars);
    menuItemReview.setComments(comments);
    menuItemReview.setDateReviewed(dateReviewed);

    MenuItemReview savedMenuItemReview = menuItemReviewRepository.save(menuItemReview);

    return savedMenuItemReview;
}

@ApiOperation(value = "Delete a MenuItemReview")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("")
    public Object deleteMenuItemReview(
            @ApiParam("id") @RequestParam Long id) {
        MenuItemReview menuItemReview = menuItemReviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MenuItemReview.class, id));

        menuItemReviewRepository.delete(menuItemReview);
        return genericMessage("MenuItemReview with id %s deleted".formatted(id));
    }

    @ApiOperation(value = "Update a review")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("")
    public MenuItemReview updateMenuItemReview(
            @ApiParam("id") @RequestParam Long id,
            @RequestBody @Valid MenuItemReview incoming) {

        MenuItemReview menuItemReview = menuItemReviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MenuItemReview.class, id));

        menuItemReview.setItemId(incoming.getItemId());
        menuItemReview.setReviewerEmail(incoming.getReviewerEmail());
        menuItemReview.setStars(incoming.getStars());
        menuItemReview.setComments(incoming.getComments());
        menuItemReview.setDateReviewed(incoming.getDateReviewed());

        menuItemReviewRepository.save(menuItemReview);

        return menuItemReview;

        }
}