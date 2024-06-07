package creatip.restaurant.service;

import creatip.restaurant.domain.ProductDetail;
import creatip.restaurant.repository.ProductDetailRepository;
import creatip.restaurant.service.dto.ProductDetailDTO;
import creatip.restaurant.service.mapper.ProductDetailMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link creatip.restaurant.domain.ProductDetail}.
 */
@Service
@Transactional
public class ProductDetailService {

    private final Logger log = LoggerFactory.getLogger(ProductDetailService.class);

    private final ProductDetailRepository productDetailRepository;

    private final ProductDetailMapper productDetailMapper;

    public ProductDetailService(ProductDetailRepository productDetailRepository, ProductDetailMapper productDetailMapper) {
        this.productDetailRepository = productDetailRepository;
        this.productDetailMapper = productDetailMapper;
    }

    /**
     * Save a productDetail.
     *
     * @param productDetailDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductDetailDTO save(ProductDetailDTO productDetailDTO) {
        log.debug("Request to save ProductDetail : {}", productDetailDTO);
        ProductDetail productDetail = productDetailMapper.toEntity(productDetailDTO);
        productDetail = productDetailRepository.save(productDetail);
        return productDetailMapper.toDto(productDetail);
    }

    /**
     * Update a productDetail.
     *
     * @param productDetailDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductDetailDTO update(ProductDetailDTO productDetailDTO) {
        log.debug("Request to update ProductDetail : {}", productDetailDTO);
        ProductDetail productDetail = productDetailMapper.toEntity(productDetailDTO);
        productDetail = productDetailRepository.save(productDetail);
        return productDetailMapper.toDto(productDetail);
    }

    /**
     * Partially update a productDetail.
     *
     * @param productDetailDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductDetailDTO> partialUpdate(ProductDetailDTO productDetailDTO) {
        log.debug("Request to partially update ProductDetail : {}", productDetailDTO);

        return productDetailRepository
            .findById(productDetailDTO.getId())
            .map(existingProductDetail -> {
                productDetailMapper.partialUpdate(existingProductDetail, productDetailDTO);

                return existingProductDetail;
            })
            .map(productDetailRepository::save)
            .map(productDetailMapper::toDto);
    }

    /**
     * Get all the productDetails.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProductDetailDTO> findAll() {
        log.debug("Request to get all ProductDetails");
        return productDetailRepository.findAll().stream().map(productDetailMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one productDetail by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductDetailDTO> findOne(Long id) {
        log.debug("Request to get ProductDetail : {}", id);
        return productDetailRepository.findById(id).map(productDetailMapper::toDto);
    }

    /**
     * Delete the productDetail by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductDetail : {}", id);
        productDetailRepository.deleteById(id);
    }
}
