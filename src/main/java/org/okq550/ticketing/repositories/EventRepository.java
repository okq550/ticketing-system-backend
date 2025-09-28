package org.okq550.ticketing.repositories;

import org.okq550.ticketing.domain.entities.Event;
import org.okq550.ticketing.domain.enums.EventStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    Page<Event> findByOrganizerId(UUID organizerId, Pageable pageable);
    Optional<Event> findByOrganizerIdAndId(UUID organizerId, UUID id);
    Page<Event> findByStatus(EventStatusEnum status, Pageable pageable);

    @Query(value = "SELECT * FROM events WHERE " +
            "status = 'PUBLISHED' AND " +
            "to_tsvector('english', COALESCE(name, '') || ' ' || COALESCE(venue, '')) " +
            "@@ plainto_tsquery('english', :searchTerm)",
            countQuery = "SELECT count(*) FROM events WHERE " +
                    "status = 'PUBLISHED' AND " +
                    "to_tsvector('english', COALESCE(name, '') || ' ' || COALESCE(venue, '')) " +
                    "@@ plainto_tsquery('english', :searchTerm)",
            nativeQuery = true)
    Page<Event> searchEvents(@Param("searchTerm") String searchTerm, Pageable pageable);

    Optional<Event> findByIdAndStatus(UUID id, EventStatusEnum status);

//    @Query(value = "SELECT * FROM events WHERE events.name LIKE '%:searchTerm%' " +
//            "OR events.venue LIKE '%:searchTerm%'",
//        countQuery = "SELECT COUNT(*) FROM events WHERE events.name LIKE '%:searchTerm%' " +
//            "OR events.venue LIKE '%:searchTerm%'", nativeQuery = true)
//    Page<Event> searchByStatus(@Param("searchTerm") String searchTerm, @Param("status") String status, Pageable pageable);
}
