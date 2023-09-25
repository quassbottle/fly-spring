package ru.quassbottle.fly.controllers.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.quassbottle.fly.entities.Offer;
import ru.quassbottle.fly.services.crud.OfferService;

import java.util.List;

@RestController
@RequestMapping("/api/service/offers")
public class OfferController { // todo: rework logic, add validation, authorization layers
    private final OfferService offerService;

    @Autowired
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public List<Offer> getAllOffers() {
        return this.offerService.getAll();
    }

    @GetMapping("{id}")
    public Offer getOfferById(@PathVariable long id) {
        return this.offerService.getById(id);
    }

    @PostMapping
    public Offer saveOffer(@RequestBody Offer offer) {
        return this.offerService.save(offer);
    }

    @PutMapping("{id}")
    public Offer updateOffer(@RequestBody Offer offer, @PathVariable long id) {
        return this.offerService.update(offer, id);
    }

    @DeleteMapping("{id}")
    public String deleteOffer(@PathVariable long id) {
        this.offerService.deleteById(id);
        return "Deleted successfully";
    }
}
