package creatip.restaurant.web.rest;

import creatip.restaurant.repository.AuthorityRepository;
import creatip.restaurant.service.AuthorityService;
import creatip.restaurant.service.dto.AuthorityDTO;
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
 * REST controller for managing {@link creatip.restaurant.domain.Authority}.
 */
@RestController
@RequestMapping("/api/authorities")
public class AuthorityResource {

    private final Logger log = LoggerFactory.getLogger(AuthorityResource.class);

    private static final String ENTITY_NAME = "authority";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuthorityService authorityService;

    private final AuthorityRepository authorityRepository;

    public AuthorityResource(AuthorityService authorityService, AuthorityRepository authorityRepository) {
        this.authorityService = authorityService;
        this.authorityRepository = authorityRepository;
    }

    /**
     * {@code POST  /authorities} : Create a new authority.
     *
     * @param authorityDTO the authorityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new authorityDTO, or with status {@code 400 (Bad Request)} if the authority has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AuthorityDTO> createAuthority(@Valid @RequestBody AuthorityDTO authorityDTO) throws URISyntaxException {
        log.debug("REST request to save Authority : {}", authorityDTO);
        if (authorityDTO.getName() != null) {
            throw new BadRequestAlertException("A new authority cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuthorityDTO result = authorityService.save(authorityDTO);
        return ResponseEntity
            .created(new URI("/api/authorities/" + result.getName()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getName()))
            .body(result);
    }

    /**
     * {@code PUT  /authorities/:name} : Updates an existing authority.
     *
     * @param name the id of the authorityDTO to save.
     * @param authorityDTO the authorityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated authorityDTO,
     * or with status {@code 400 (Bad Request)} if the authorityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the authorityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{name}")
    public ResponseEntity<AuthorityDTO> updateAuthority(
        @PathVariable(value = "name", required = false) final String name,
        @Valid @RequestBody AuthorityDTO authorityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Authority : {}, {}", name, authorityDTO);
        if (authorityDTO.getName() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(name, authorityDTO.getName())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!authorityRepository.existsById(name)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AuthorityDTO result = authorityService.update(authorityDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, authorityDTO.getName()))
            .body(result);
    }

    /**
     * {@code PATCH  /authorities/:name} : Partial updates given fields of an existing authority, field will ignore if it is null
     *
     * @param name the id of the authorityDTO to save.
     * @param authorityDTO the authorityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated authorityDTO,
     * or with status {@code 400 (Bad Request)} if the authorityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the authorityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the authorityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{name}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AuthorityDTO> partialUpdateAuthority(
        @PathVariable(value = "name", required = false) final String name,
        @NotNull @RequestBody AuthorityDTO authorityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Authority partially : {}, {}", name, authorityDTO);
        if (authorityDTO.getName() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(name, authorityDTO.getName())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!authorityRepository.existsById(name)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AuthorityDTO> result = authorityService.partialUpdate(authorityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, authorityDTO.getName())
        );
    }

    /**
     * {@code GET  /authorities} : get all the authorities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of authorities in body.
     */
    @GetMapping("/getall")
    public List<AuthorityDTO> getAllAuthorities() {
        log.debug("REST request to get all Authorities");
        return authorityService.findAll();
    }

    /**
     * {@code GET  /authorities/:id} : get the "id" authority.
     *
     * @param id the id of the authorityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the authorityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuthorityDTO> getAuthority(@PathVariable String id) {
        log.debug("REST request to get Authority : {}", id);
        Optional<AuthorityDTO> authorityDTO = authorityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(authorityDTO);
    }

    /**
     * {@code DELETE  /authorities/:id} : delete the "id" authority.
     *
     * @param id the id of the authorityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthority(@PathVariable String id) {
        log.debug("REST request to delete Authority : {}", id);
        authorityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
