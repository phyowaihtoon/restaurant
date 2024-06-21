package creatip.restaurant.web.rest;

import creatip.restaurant.repository.MenuGroupRepository;
import creatip.restaurant.service.MenuGroupService;
import creatip.restaurant.service.dto.MenuGroupDTO;
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
 * REST controller for managing {@link creatip.restaurant.domain.MenuGroup}.
 */
@RestController
@RequestMapping("/api/menu-groups")
public class MenuGroupResource {

    private final Logger log = LoggerFactory.getLogger(MenuGroupResource.class);

    private static final String ENTITY_NAME = "menuGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MenuGroupService menuGroupService;

    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupResource(MenuGroupService menuGroupService, MenuGroupRepository menuGroupRepository) {
        this.menuGroupService = menuGroupService;
        this.menuGroupRepository = menuGroupRepository;
    }

    /**
     * {@code POST  /menu-groups} : Create a new menuGroup.
     *
     * @param menuGroupDTO the menuGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new menuGroupDTO, or with status {@code 400 (Bad Request)} if the menuGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MenuGroupDTO> createMenuGroup(@Valid @RequestBody MenuGroupDTO menuGroupDTO) throws URISyntaxException {
        log.debug("REST request to save MenuGroup : {}", menuGroupDTO);
        if (menuGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new menuGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MenuGroupDTO result = menuGroupService.save(menuGroupDTO);
        return ResponseEntity
            .created(new URI("/api/menu-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /menu-groups/:id} : Updates an existing menuGroup.
     *
     * @param id the id of the menuGroupDTO to save.
     * @param menuGroupDTO the menuGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated menuGroupDTO,
     * or with status {@code 400 (Bad Request)} if the menuGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the menuGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MenuGroupDTO> updateMenuGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MenuGroupDTO menuGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MenuGroup : {}, {}", id, menuGroupDTO);
        if (menuGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, menuGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!menuGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MenuGroupDTO result = menuGroupService.update(menuGroupDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, menuGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /menu-groups/:id} : Partial updates given fields of an existing menuGroup, field will ignore if it is null
     *
     * @param id the id of the menuGroupDTO to save.
     * @param menuGroupDTO the menuGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated menuGroupDTO,
     * or with status {@code 400 (Bad Request)} if the menuGroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the menuGroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the menuGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MenuGroupDTO> partialUpdateMenuGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MenuGroupDTO menuGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MenuGroup partially : {}, {}", id, menuGroupDTO);
        if (menuGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, menuGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!menuGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MenuGroupDTO> result = menuGroupService.partialUpdate(menuGroupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, menuGroupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /menu-groups} : get all the menuGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of menuGroups in body.
     */
    @GetMapping("")
    public List<MenuGroupDTO> getAllMenuGroups() {
        log.debug("REST request to get all MenuGroups");
        return menuGroupService.findAll();
    }

    /**
     * {@code GET  /menu-groups/:id} : get the "id" menuGroup.
     *
     * @param id the id of the menuGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the menuGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MenuGroupDTO> getMenuGroup(@PathVariable Long id) {
        log.debug("REST request to get MenuGroup : {}", id);
        Optional<MenuGroupDTO> menuGroupDTO = menuGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(menuGroupDTO);
    }

    /**
     * {@code DELETE  /menu-groups/:id} : delete the "id" menuGroup.
     *
     * @param id the id of the menuGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuGroup(@PathVariable Long id) {
        log.debug("REST request to delete MenuGroup : {}", id);
        menuGroupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
