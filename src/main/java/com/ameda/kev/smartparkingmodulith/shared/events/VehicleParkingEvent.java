package com.ameda.kev.smartparkingmodulith.shared.events;

import com.ameda.kev.smartparkingmodulith.shared.domain.VehicleEventObj;
import org.jilt.Builder;

import java.time.Instant;
import java.util.UUID;

/**
 * Author: kev.Ameda
 */

@Builder
public record VehicleParkingEvent(UUID eventPublicId,
                                  VehicleEventObj vehicleEventObj) {
}
