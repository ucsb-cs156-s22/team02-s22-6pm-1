package edu.ucsb.cs156.example.controllers;

import edu.ucsb.cs156.example.entities.UCSBDiningCommonsMenuItem;
import edu.ucsb.cs156.example.errors.EntityNotFoundException;
import edu.ucsb.cs156.example.repositories.UCSBDiningCommonsMenuItemRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
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


@Api(description = "UCSBDiningCommonsMenuItem")
@RequestMapping("/api/UCSBDiningCommonsMenuItem")
@RestController
@Slf4j
public class UCSBDiningCommonsMenuItemController extends ApiController {

    @Autowired
    UCSBDiningCommonsMenuItemRepository ucsbDiningCommonsMenuItemRepository;

    @ApiOperation(value = "List all the ~very tasty~ ucsb dining commons menu items")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public Iterable<UCSBDiningCommonsMenuItem> allMenuItems() {
        Iterable<UCSBDiningCommonsMenuItem> menuitems = ucsbDiningCommonsMenuItemRepository.findAll();
        return menuitems;
    }

    @ApiOperation(value = "Get a single ucsb dining commons menu item")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("")
    public UCSBDiningCommonsMenuItem getById(
            @ApiParam("id") @RequestParam Long id) {
        UCSBDiningCommonsMenuItem menuitems = ucsbDiningCommonsMenuItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(UCSBDiningCommonsMenuItem.class, id));

        return menuitems;
    }

    @ApiOperation(value = "Create a new ucsb dining commons menu item")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/post")
    public UCSBDiningCommonsMenuItem postMenuItem(
        @ApiParam("diningCommonsCode") @RequestParam String diningCommonsCode,
        @ApiParam("name") @RequestParam String name,
        @ApiParam("station") @RequestParam String station
        )
        {

        UCSBDiningCommonsMenuItem menuitem = new UCSBDiningCommonsMenuItem();
        menuitem.setDiningCommonsCode(diningCommonsCode);
        menuitem.setName(name);
        menuitem.setStation(station);

        UCSBDiningCommonsMenuItem savedMenuItem = ucsbDiningCommonsMenuItemRepository.save(menuitem);

        return savedMenuItem;
    }
    /*
    @ApiOperation(value = "Delete a UCSBDiningCommons")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("")
    public Object deleteCommons(
            @ApiParam("code") @RequestParam String code) {
        UCSBDiningCommons commons = ucsbDiningCommonsRepository.findById(code)
                .orElseThrow(() -> new EntityNotFoundException(UCSBDiningCommons.class, code));

        ucsbDiningCommonsRepository.delete(commons);
        return genericMessage("UCSBDiningCommons with id %s deleted".formatted(code));
    }

    @ApiOperation(value = "Update a single commons")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("")
    public UCSBDiningCommons updateCommons(
            @ApiParam("code") @RequestParam String code,
            @RequestBody @Valid UCSBDiningCommons incoming) {

        UCSBDiningCommons commons = ucsbDiningCommonsRepository.findById(code)
                .orElseThrow(() -> new EntityNotFoundException(UCSBDiningCommons.class, code));


        commons.setName(incoming.getName());  
        commons.setHasSackMeal(incoming.getHasSackMeal());
        commons.setHasTakeOutMeal(incoming.getHasTakeOutMeal());
        commons.setHasDiningCam(incoming.getHasDiningCam());
        commons.setLatitude(incoming.getLatitude());
        commons.setLongitude(incoming.getLongitude());

        ucsbDiningCommonsRepository.save(commons);

        return commons;
    }
    */
}
