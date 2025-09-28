package org.okq550.ticketing.domain.dtos.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListPublishedEventResponseDto {
    private UUID id;
    private String name;
    private String start;
    private String end;
    private String venue;
}