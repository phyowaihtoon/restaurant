package creatip.restaurant.web.rest;

import creatip.restaurant.repository.AuthorityMenuItemRepository;
import creatip.restaurant.service.AuthorityMenuItemService;
import creatip.restaurant.service.dto.AuthorityMenuItemDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link creatip.restaurant.domain.AuthorityMenuItem}.
 */
@RestController
@RequestMapping("/api/authority-menu-items")
public class AuthorityMenuItemResource {

    private final Logger log = LoggerFactory.getLogger(AuthorityMenuItemResource.class);

    private static final String ENTITY_NAME = "authorityMenuItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuthorityMenuItemService authorityMenuItemService;

    private final AuthorityMenuItemRepository authorityMenuItemRepository;

    public AuthorityMenuItemResource(
        AuthorityMenuItemService authorityMenuItemService,
        AuthorityMenuItemRepository authorityMenuItemRepository
    ) {
        this.authorityMenuItemService = authorityMenuItemService;
        this.authorityMenuItemRepository = authorityMenuItemRepository;
    }

    /**
     * {@code POST  /authority-menu-items} : Create a new authorityMenuItem.
     *
     * @param authorityMenuItemDTO the authorityMenuItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new authorityMenuItemDTO, or with status {@code 400 (Bad Request)} if the authorityMenuItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AuthorityMenuItemDTO> createAuthorityMenuItem(@Valid @RequestBody AuthorityMenuItemDTO authorityMenuItemDTO)
        throws URISyntaxException {
        log.debug("REST request to save AuthorityMenuItem : {}", authorityMenuItemDTO);
        if (authorityMenuItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new authorityMenuItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuthorityMenuItemDTO result = authorityMenuItemService.save(authorityMenuItemDTO);
        return ResponseEntity
            .created(new URI("/api/authority-menu-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /authority-menu-items/:id} : Updates an existing authorityMenuItem.
     *
     * @param id the id of the authorityMenuItemDTO to save.
     * @param authorityMenuItemDTO the authorityMenuItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated authorityMenuItemDTO,
     * or with status {@code 400 (Bad Request)} if the authorityMenuItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the authorityMenuItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AuthorityMenuItemDTO> updateAuthorityMenuItem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AuthorityMenuItemDTO authorityMenuItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AuthorityMenuItem : {}, {}", id, authorityMenuItemDTO);
        if (authorityMenuItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, authorityMenuItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!authorityMenuItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AuthorityMenuItemDTO result = authorityMenuItemService.update(authorityMenuItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, authorityMenuItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /authority-menu-items/:id} : Partial updates given fields of an existing authorityMenuItem, field will ignore if it is null
     *
     * @param id the id of the authorityMenuItemDTO to save.
     * @param authorityMenuItemDTO the authorityMenuItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated authorityMenuItemDTO,
     * or with status {@code 400 (Bad Request)} if the authorityMenuItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the authorityMenuItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the authorityMenuItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AuthorityMenuItemDTO> partialUpdateAuthorityMenuItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AuthorityMenuItemDTO authorityMenuItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AuthorityMenuItem partially : {}, {}", id, authorityMenuItemDTO);
        if (authorityMenuItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, authorityMenuItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!authorityMenuItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AuthorityMenuItemDTO> result = authorityMenuItemService.partialUpdate(authorityMenuItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, authorityMenuItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /authority-menu-items} : get all the authorityMenuItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of authorityMenuItems in body.
     */
    @GetMapping("")
    public List<AuthorityMenuItemDTO> getAllAuthorityMenuItems() {
        log.debug("REST request to get all AuthorityMenuItems");
        return authorityMenuItemService.findAll();
    }

    /**
     * {@code GET  /authority-menu-items/:id} : get the "id" authorityMenuItem.
     *
     * @param id the id of the authorityMenuItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the authorityMenuItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuthorityMenuItemDTO> getAuthorityMenuItem(@PathVariable Long id) {
        log.debug("REST request to get AuthorityMenuItem : {}", id);
        Optional<AuthorityMenuItemDTO> authorityMenuItemDTO = authorityMenuItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(authorityMenuItemDTO);
    }

    /**
     * {@code DELETE  /authority-menu-items/:id} : delete the "id" authorityMenuItem.
     *
     * @param id the id of the authorityMenuItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthorityMenuItem(@PathVariable Long id) {
        log.debug("REST request to delete AuthorityMenuItem : {}", id);
        authorityMenuItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
