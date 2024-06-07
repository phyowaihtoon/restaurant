package creatip.restaurant.web.rest;

import creatip.restaurant.repository.DiningTableRepository;
import creatip.restaurant.service.DiningTableService;
import creatip.restaurant.service.dto.DiningTableDTO;
import creatip.restaurant.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link creatip.restaurant.domain.DiningTable}.
 */
@RestController
@RequestMapping("/api/dining-tables")
public class DiningTableResource {

    private final Logger log = LoggerFactory.getLogger(DiningTableResource.class);

    private static final String ENTITY_NAME = "diningTable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiningTableService diningTableService;

    private final DiningTableRepository diningTableRepository;

    public DiningTableResource(DiningTableService diningTableService, DiningTableRepository diningTableRepository) {
        this.diningTableService = diningTableService;
        this.diningTableRepository = diningTableRepository;
    }

    /**
     * {@code POST  /dining-tables} : Create a new diningTable.
     *
     * @param diningTableDTO the diningTableDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new diningTableDTO, or with status {@code 400 (Bad Request)} if the diningTable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DiningTableDTO> createDiningTable(@Valid @RequestBody DiningTableDTO diningTableDTO) throws URISyntaxException {
        log.debug("REST request to save DiningTable : {}", diningTableDTO);
        if (diningTableDTO.getId() != null) {
            throw new BadRequestAlertException("A new diningTable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiningTableDTO result = diningTableService.save(diningTableDTO);
        return ResponseEntity
            .created(new URI("/api/dining-tables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dining-tables/:id} : Updates an existing diningTable.
     *
     * @param id the id of the diningTableDTO to save.
     * @param diningTableDTO the diningTableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diningTableDTO,
     * or with status {@code 400 (Bad Request)} if the diningTableDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the diningTableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DiningTableDTO> updateDiningTable(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DiningTableDTO diningTableDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DiningTable : {}, {}", id, diningTableDTO);
        if (diningTableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diningTableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diningTableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DiningTableDTO result = diningTableService.update(diningTableDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, diningTableDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dining-tables/:id} : Partial updates given fields of an existing diningTable, field will ignore if it is null
     *
     * @param id the id of the diningTableDTO to save.
     * @param diningTableDTO the diningTableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diningTableDTO,
     * or with status {@code 400 (Bad Request)} if the diningTableDTO is not valid,
     * or with status {@code 404 (Not Found)} if the diningTableDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the diningTableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DiningTableDTO> partialUpdateDiningTable(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DiningTableDTO diningTableDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DiningTable partially : {}, {}", id, diningTableDTO);
        if (diningTableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diningTableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diningTableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DiningTableDTO> result = diningTableService.partialUpdate(diningTableDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, diningTableDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dining-tables} : get all the diningTables.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diningTables in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DiningTableDTO>> getAllDiningTables(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of DiningTables");
        Page<DiningTableDTO> page = diningTableService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dining-tables/:id} : get the "id" diningTable.
     *
     * @param id the id of the diningTableDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the diningTableDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DiningTableDTO> getDiningTable(@PathVariable Long id) {
        log.debug("REST request to get DiningTable : {}", id);
        Optional<DiningTableDTO> diningTableDTO = diningTableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(diningTableDTO);
    }

    /**
     * {@code DELETE  /dining-tables/:id} : delete the "id" diningTable.
     *
     * @param id the id of the diningTableDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiningTable(@PathVariable Long id) {
        log.debug("REST request to delete DiningTable : {}", id);
        diningTableService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
