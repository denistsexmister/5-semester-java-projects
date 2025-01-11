package ua.nure.tsekhmister.commons.form.search;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class SearchByPriceForm {
    @Valid
    @NotNull(message = "You must write some value!")
    @Min(value = 1, message = "You can't get car less than 1!")
    @Max(value = 1_000_000_000L, message = "You can't get car bigger than 1 000 000 000!")
    private BigDecimal searchQuery;

    public SearchByPriceForm() {
    }

    public SearchByPriceForm(BigDecimal searchQuery) {
        this.searchQuery = searchQuery;
    }

    public BigDecimal getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(BigDecimal searchQuery) {
        this.searchQuery = searchQuery;
    }
}
