package com.team2final.minglecrm.reservation.dto.hotel.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class RoomReservationStatisticsResponse {

    private final Integer twentyMale;
    private final Integer twentyFemale;
    private final Integer thirtyMale;
    private final Integer thirtyFemale;
    private final Integer fortyMale;
    private final Integer fortyFemale;
    private final Integer fiftyMale;
    private final Integer fiftyFemale;
    private final Integer overSixtyMale;
    private final Integer overSixtyFemale;

    @QueryProjection
    public RoomReservationStatisticsResponse(
            Integer twentyMale,
            Integer twentyFemale,
            Integer thirtyMale,
            Integer thirtyFemale,
            Integer fortyMale,
            Integer fortyFemale,
            Integer fiftyMale,
            Integer fiftyFemale,
            Integer overSixtyMale,
            Integer overSixtyFemale) {
        this.twentyMale = twentyMale;
        this.twentyFemale = twentyFemale;
        this.thirtyMale = thirtyMale;
        this.thirtyFemale = thirtyFemale;
        this.fortyMale = fortyMale;
        this.fortyFemale = fortyFemale;
        this.fiftyMale = fiftyMale;
        this.fiftyFemale = fiftyFemale;
        this.overSixtyMale = overSixtyMale;
        this.overSixtyFemale = overSixtyFemale;
    }

}
