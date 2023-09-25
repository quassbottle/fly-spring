package ru.quassbottle.fly.services.crud.impl;

import org.springframework.stereotype.Service;
import ru.quassbottle.fly.entities.Offer;
import ru.quassbottle.fly.repositories.OfferRepository;
import ru.quassbottle.fly.services.crud.OfferService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;

    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public List<Offer> getAll() {
        return this.offerRepository.findAll();
    }

    @Override
    public Offer getById(Long id) {
        return this.offerRepository.findById(id).orElseThrow();
    }

    @Override
    public Offer save(Offer offer) {
        return this.offerRepository.save(offer);
    }

    @Override
    public Offer update(Offer offer, Long offerId) {
        Optional<Offer> candidate = this.offerRepository.findById(offerId);
        if (candidate.isEmpty()) return null;

        Offer offerDb = candidate.get();

        if (Objects.nonNull(offer.getTitle()) && !offer.getTitle().equalsIgnoreCase("")) {
            offerDb.setTitle(offer.getTitle());
        } // update & validate title

        if (Objects.nonNull(offer.getDescription()) && !offer.getDescription().equalsIgnoreCase("")) {
            offerDb.setDescription(offer.getDescription());
        } // update & validate description

        if (offer.getLatitude() >= -90 && offer.getLatitude() <= 90) {
            offerDb.setLatitude(offer.getLatitude());
        } // update & validate latitude

        if (offer.getLongitude() >= -180 && offer.getLongitude() <= 180) {
            offerDb.setLatitude(offer.getLatitude());
        } // update & validate longitude

        offerDb.setHourly(offer.isHourly()); // update isHourly

        if (offer.getPrice() > 0) {
            offerDb.setPrice(offer.getPrice());
        } // update & validate price

        return this.offerRepository.save(offerDb);
    }

    @Override
    public void deleteById(Long id) {
        this.offerRepository.deleteById(id);
    }
}
