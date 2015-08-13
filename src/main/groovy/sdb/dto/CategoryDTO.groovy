package sdb.dto

import javax.validation.constraints.NotNull

/**
 * Created by benoit on 13/08/15.
 */
class CategoryDTO {
    Long id

    @NotNull
    String name
}
