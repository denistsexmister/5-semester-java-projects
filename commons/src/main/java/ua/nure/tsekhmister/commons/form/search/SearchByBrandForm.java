package ua.nure.tsekhmister.commons.form.search;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SearchByBrandForm {
    @Valid
    @NotBlank(message = "Write some query to get result!")
    @Size(max = 50, message = "Search query can't be more than 50 characters!")
    private String searchQuery;

    public SearchByBrandForm() {
    }

    public SearchByBrandForm(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }
}
