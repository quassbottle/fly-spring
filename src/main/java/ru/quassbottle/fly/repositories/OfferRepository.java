package ru.quassbottle.fly.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.quassbottle.fly.entities.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
}
