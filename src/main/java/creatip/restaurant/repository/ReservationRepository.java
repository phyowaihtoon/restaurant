package creatip.restaurant.repository;

import creatip.restaurant.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {}
