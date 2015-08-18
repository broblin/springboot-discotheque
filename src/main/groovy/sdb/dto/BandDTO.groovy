package sdb.dto

import sbd.beans.EMusicType

import javax.validation.constraints.NotNull
import java.time.LocalDate

/**
 * Created by benoit on 18/08/15.
 */
class BandDTO {

    Long id

    @NotNull
    String name

    String country

    LocalDate creationDate

    @NotNull
    String categoryName

    @NotNull
    EMusicType musicType
}
