
package com.oms.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.oms.service.StoreSearchService;
import com.oms.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Store Search", description = "API for searching stores")
@RestController
@RequestMapping("/store")
public class StoreSearchController {

    @Autowired
    StoreSearchService storeSearchService;

    @Autowired
    Logger logger;

    @Operation(
        summary = "Fetch stores by ZIP code",
        description = "Returns a list of stores available in the given ZIP code area",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved stores"),
            @ApiResponse(responseCode = "400", description = "Invalid ZIP code format")
        }
    )
    @GetMapping
    @RequestMapping("/{zipCode}")
    public List<String> fetchStoresByZip(
        @Parameter(description = "5-digit ZIP code to search stores", required = true)
        @Valid @NotBlank @Pattern(regexp = "^\\d{5}$")
        @PathVariable String zipCode) {
        logger.log(this.getClass().getName());
        return storeSearchService.fetchStoresByZipCode(zipCode);
    }

}