package ru.belonogov.task_service_spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.belonogov.task_service_spring.domain.dto.request.CompanySaveRequest;
import ru.belonogov.task_service_spring.domain.dto.request.CompanyUpdateRequest;
import ru.belonogov.task_service_spring.domain.dto.response.CompanyResponse;
import ru.belonogov.task_service_spring.service.CompanyService;


@RestController
@RequestMapping(
        value = "/company",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE )
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/create")
    public ResponseEntity<CompanyResponse> create(@RequestBody CompanySaveRequest companySaveRequest) {
        CompanyResponse companyResponse = companyService.create(companySaveRequest);

        return new ResponseEntity<>(companyResponse, HttpStatus.CREATED);
    }

    @GetMapping(value =  "/read/{companyName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyResponse> read(@PathVariable String companyName) {
        CompanyResponse companyResponse = companyService.read(companyName);

        return new ResponseEntity<>(companyResponse, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<CompanyResponse> update(CompanyUpdateRequest companyUpdateRequest) {
        CompanyResponse companyResponse = companyService.update(companyUpdateRequest);

        return new ResponseEntity<>(companyResponse, HttpStatus.OK);
    }

    @PostMapping("/delete/{companyId}")
    public void delete(@PathVariable(value = "companyId") Long companyId) {
        companyService.delete(companyId);
    }
}
