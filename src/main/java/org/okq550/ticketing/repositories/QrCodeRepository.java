package org.okq550.ticketing.repositories;

import org.okq550.ticketing.domain.entities.QrCode;
import org.okq550.ticketing.domain.enums.QrCodeStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, UUID> {
    Optional<QrCode> findByTicketIdAndTicketPurchaserId(UUID ticketId, UUID purchasedId);
    Optional<QrCode> findByIdAndStatus(UUID id, QrCodeStatusEnum status);
}
