package pl.gov.coi.pomocua.ads.transport;

import org.hibernate.validator.constraints.Length;
import pl.gov.coi.pomocua.ads.Location;
import pl.gov.coi.pomocua.ads.materialaid.MaterialAidCategory;
import pl.gov.coi.pomocua.ads.materialaid.MaterialAidOffer;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

import java.time.LocalDate;

import static pl.gov.coi.pomocua.ads.BaseOffer.ALLOWED_TEXT;
import static pl.gov.coi.pomocua.ads.BaseOffer.PHONE_REGEX;

public class TransportOfferDefinitionDTO {
    @NotBlank
    @Length(max = 80)
    @Pattern(regexp = ALLOWED_TEXT)
    public String title;

    @NotBlank
    @Length(max = 2000)
    @Pattern(regexp = ALLOWED_TEXT)
    public String description;


    @NotNull
    @Valid
    public Location origin;

    @NotNull
    @Valid
    public Location destination;

    @NotNull
    @Min(1)
    @Max(99)
    public Integer capacity;

    @NotNull
    public LocalDate transportDate;

    @Length(min = 7, max = 15)
    @Pattern(regexp = PHONE_REGEX)
    public String phoneNumber;

    public void applyTo(TransportOffer transportOffer) {
        transportOffer.title = this.title;
        transportOffer.description = this.description;
        transportOffer.origin = this.origin;
        transportOffer.destination = this.destination;
        transportOffer.capacity = this.capacity;
        transportOffer.transportDate = this.transportDate;
        transportOffer.phoneNumber = phoneNumber;
    }
}
