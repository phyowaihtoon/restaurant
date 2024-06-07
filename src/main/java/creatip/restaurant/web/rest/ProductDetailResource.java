package creatip.restaurant.web.rest;

import creatip.restaurant.repository.ProductDetailRepository;
import creatip.restaurant.service.ProductDetailService;
import creatip.restaurant.service.dto.ProductDetailDTO;
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
 * REST controller for managing {@link creatip.restaurant.domain.ProductDetail}.
 */
@RestController
@RequestMapping("/api/product-details")
public class ProductDetailResource {

    private final Logger log = LoggerFactory.getLogger(ProductDetailResource.class);

    private static final String ENTITY_NAME = "productDetail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductDetailService productDetailService;

    private final ProductDetailRepository productDetailRepository;

    public ProductDetailResource(ProductDetailService productDetailService, ProductDetailRepository productDetailRepository) {
        this.productDetailService = productDetailService;
        this.productDetailRepository = productDetailRepository;
    }

    /**
     * {@code POST  /product-details} : Create a new productDetail.
     *
     * @param productDetailDTO the productDetailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productDetailDTO, or with status {@code 400 (Bad Request)} if the productDetail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProductDetailDTO> createProductDetail(@Valid @RequestBody ProductDetailDTO productDetailDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductDetail : {}", productDetailDTO);
        if (productDetailDTO.getId() != null) {
            throw new BadRequestAlertException("A new productDetail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductDetailDTO result = productDetailService.save(productDetailDTO);
        return ResponseEntity
            .created(new URI("/api/product-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-details/:id} : Updates an existing productDetail.
     *
     * @param id the id of the productDetailDTO to save.
     * @param productDetailDTO the productDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productDetailDTO,
     * or with status {@code 400 (Bad Request)} if the productDetailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDetailDTO> updateProductDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductDetailDTO productDetailDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductDetail : {}, {}", id, productDetailDTO);
        if (productDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductDetailDTO result = productDetailService.update(productDetailDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productDetailDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-details/:id} : Partial updates given fields of an existing productDetail, field will ignore if it is null
     *
     * @param id the id of the productDetailDTO to save.
     * @param productDetailDTO the productDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productDetailDTO,
     * or with status {@code 400 (Bad Request)} if the productDetailDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productDetailDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductDetailDTO> partialUpdateProductDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductDetailDTO productDetailDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductDetail partially : {}, {}", id, productDetailDTO);
        if (productDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductDetailDTO> result = productDetailService.partialUpdate(productDetailDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productDetailDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-details} : get all the productDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productDetails in body.
     */
    @GetMapping("")
    public List<ProductDetailDTO> getAllProductDetails() {
        log.debug("REST request to get all ProductDetails");
        return productDetailService.findAll();
    }

    /**
     * {@code GET  /product-details/:id} : get the "id" productDetail.
     *
     * @param id the id of the productDetailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productDetailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailDTO> getProductDetail(@PathVariable Long id) {
        log.debug("REST request to get ProductDetail : {}", id);
        Optional<ProductDetailDTO> productDetailDTO = productDetailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productDetailDTO);
    }

    /**
     * {@code DELETE  /product-details/:id} : delete the "id" productDetail.
     *
     * @param id the id of the productDetailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductDetail(@PathVariable Long id) {
        log.debug("REST request to delete ProductDetail : {}", id);
        productDetailService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
