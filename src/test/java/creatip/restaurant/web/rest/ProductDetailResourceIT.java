package creatip.restaurant.web.rest;

import static creatip.restaurant.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import creatip.restaurant.IntegrationTest;
import creatip.restaurant.domain.ProductDetail;
import creatip.restaurant.repository.ProductDetailRepository;
import creatip.restaurant.service.dto.ProductDetailDTO;
import creatip.restaurant.service.mapper.ProductDetailMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProductDetailResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductDetailResourceIT {

    private static final String DEFAULT_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_SIZE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_DINE_IN_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DINE_IN_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TAKE_AWAY_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAKE_AWAY_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DELIVERY_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DELIVERY_PRICE = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/product-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductDetailMockMvc;

    private ProductDetail productDetail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductDetail createEntity(EntityManager em) {
        ProductDetail productDetail = new ProductDetail()
            .size(DEFAULT_SIZE)
            .dineInPrice(DEFAULT_DINE_IN_PRICE)
            .takeAwayPrice(DEFAULT_TAKE_AWAY_PRICE)
            .deliveryPrice(DEFAULT_DELIVERY_PRICE);
        return productDetail;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductDetail createUpdatedEntity(EntityManager em) {
        ProductDetail productDetail = new ProductDetail()
            .size(UPDATED_SIZE)
            .dineInPrice(UPDATED_DINE_IN_PRICE)
            .takeAwayPrice(UPDATED_TAKE_AWAY_PRICE)
            .deliveryPrice(UPDATED_DELIVERY_PRICE);
        return productDetail;
    }

    @BeforeEach
    public void initTest() {
        productDetail = createEntity(em);
    }

    @Test
    @Transactional
    void createProductDetail() throws Exception {
        int databaseSizeBeforeCreate = productDetailRepository.findAll().size();
        // Create the ProductDetail
        ProductDetailDTO productDetailDTO = productDetailMapper.toDto(productDetail);
        restProductDetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDetailDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductDetail in the database
        List<ProductDetail> productDetailList = productDetailRepository.findAll();
        assertThat(productDetailList).hasSize(databaseSizeBeforeCreate + 1);
        ProductDetail testProductDetail = productDetailList.get(productDetailList.size() - 1);
        assertThat(testProductDetail.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testProductDetail.getDineInPrice()).isEqualByComparingTo(DEFAULT_DINE_IN_PRICE);
        assertThat(testProductDetail.getTakeAwayPrice()).isEqualByComparingTo(DEFAULT_TAKE_AWAY_PRICE);
        assertThat(testProductDetail.getDeliveryPrice()).isEqualByComparingTo(DEFAULT_DELIVERY_PRICE);
    }

    @Test
    @Transactional
    void createProductDetailWithExistingId() throws Exception {
        // Create the ProductDetail with an existing ID
        productDetail.setId(1L);
        ProductDetailDTO productDetailDTO = productDetailMapper.toDto(productDetail);

        int databaseSizeBeforeCreate = productDetailRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductDetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductDetail in the database
        List<ProductDetail> productDetailList = productDetailRepository.findAll();
        assertThat(productDetailList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = productDetailRepository.findAll().size();
        // set the field null
        productDetail.setSize(null);

        // Create the ProductDetail, which fails.
        ProductDetailDTO productDetailDTO = productDetailMapper.toDto(productDetail);

        restProductDetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDetailDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProductDetail> productDetailList = productDetailRepository.findAll();
        assertThat(productDetailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDineInPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = productDetailRepository.findAll().size();
        // set the field null
        productDetail.setDineInPrice(null);

        // Create the ProductDetail, which fails.
        ProductDetailDTO productDetailDTO = productDetailMapper.toDto(productDetail);

        restProductDetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDetailDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProductDetail> productDetailList = productDetailRepository.findAll();
        assertThat(productDetailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTakeAwayPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = productDetailRepository.findAll().size();
        // set the field null
        productDetail.setTakeAwayPrice(null);

        // Create the ProductDetail, which fails.
        ProductDetailDTO productDetailDTO = productDetailMapper.toDto(productDetail);

        restProductDetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDetailDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProductDetail> productDetailList = productDetailRepository.findAll();
        assertThat(productDetailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDeliveryPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = productDetailRepository.findAll().size();
        // set the field null
        productDetail.setDeliveryPrice(null);

        // Create the ProductDetail, which fails.
        ProductDetailDTO productDetailDTO = productDetailMapper.toDto(productDetail);

        restProductDetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDetailDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProductDetail> productDetailList = productDetailRepository.findAll();
        assertThat(productDetailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProductDetails() throws Exception {
        // Initialize the database
        productDetailRepository.saveAndFlush(productDetail);

        // Get all the productDetailList
        restProductDetailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productDetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE)))
            .andExpect(jsonPath("$.[*].dineInPrice").value(hasItem(sameNumber(DEFAULT_DINE_IN_PRICE))))
            .andExpect(jsonPath("$.[*].takeAwayPrice").value(hasItem(sameNumber(DEFAULT_TAKE_AWAY_PRICE))))
            .andExpect(jsonPath("$.[*].deliveryPrice").value(hasItem(sameNumber(DEFAULT_DELIVERY_PRICE))));
    }

    @Test
    @Transactional
    void getProductDetail() throws Exception {
        // Initialize the database
        productDetailRepository.saveAndFlush(productDetail);

        // Get the productDetail
        restProductDetailMockMvc
            .perform(get(ENTITY_API_URL_ID, productDetail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productDetail.getId().intValue()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE))
            .andExpect(jsonPath("$.dineInPrice").value(sameNumber(DEFAULT_DINE_IN_PRICE)))
            .andExpect(jsonPath("$.takeAwayPrice").value(sameNumber(DEFAULT_TAKE_AWAY_PRICE)))
            .andExpect(jsonPath("$.deliveryPrice").value(sameNumber(DEFAULT_DELIVERY_PRICE)));
    }

    @Test
    @Transactional
    void getNonExistingProductDetail() throws Exception {
        // Get the productDetail
        restProductDetailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProductDetail() throws Exception {
        // Initialize the database
        productDetailRepository.saveAndFlush(productDetail);

        int databaseSizeBeforeUpdate = productDetailRepository.findAll().size();

        // Update the productDetail
        ProductDetail updatedProductDetail = productDetailRepository.findById(productDetail.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProductDetail are not directly saved in db
        em.detach(updatedProductDetail);
        updatedProductDetail
            .size(UPDATED_SIZE)
            .dineInPrice(UPDATED_DINE_IN_PRICE)
            .takeAwayPrice(UPDATED_TAKE_AWAY_PRICE)
            .deliveryPrice(UPDATED_DELIVERY_PRICE);
        ProductDetailDTO productDetailDTO = productDetailMapper.toDto(updatedProductDetail);

        restProductDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productDetailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDetailDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductDetail in the database
        List<ProductDetail> productDetailList = productDetailRepository.findAll();
        assertThat(productDetailList).hasSize(databaseSizeBeforeUpdate);
        ProductDetail testProductDetail = productDetailList.get(productDetailList.size() - 1);
        assertThat(testProductDetail.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testProductDetail.getDineInPrice()).isEqualByComparingTo(UPDATED_DINE_IN_PRICE);
        assertThat(testProductDetail.getTakeAwayPrice()).isEqualByComparingTo(UPDATED_TAKE_AWAY_PRICE);
        assertThat(testProductDetail.getDeliveryPrice()).isEqualByComparingTo(UPDATED_DELIVERY_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingProductDetail() throws Exception {
        int databaseSizeBeforeUpdate = productDetailRepository.findAll().size();
        productDetail.setId(longCount.incrementAndGet());

        // Create the ProductDetail
        ProductDetailDTO productDetailDTO = productDetailMapper.toDto(productDetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productDetailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductDetail in the database
        List<ProductDetail> productDetailList = productDetailRepository.findAll();
        assertThat(productDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductDetail() throws Exception {
        int databaseSizeBeforeUpdate = productDetailRepository.findAll().size();
        productDetail.setId(longCount.incrementAndGet());

        // Create the ProductDetail
        ProductDetailDTO productDetailDTO = productDetailMapper.toDto(productDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductDetail in the database
        List<ProductDetail> productDetailList = productDetailRepository.findAll();
        assertThat(productDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductDetail() throws Exception {
        int databaseSizeBeforeUpdate = productDetailRepository.findAll().size();
        productDetail.setId(longCount.incrementAndGet());

        // Create the ProductDetail
        ProductDetailDTO productDetailDTO = productDetailMapper.toDto(productDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductDetailMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDetailDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductDetail in the database
        List<ProductDetail> productDetailList = productDetailRepository.findAll();
        assertThat(productDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductDetailWithPatch() throws Exception {
        // Initialize the database
        productDetailRepository.saveAndFlush(productDetail);

        int databaseSizeBeforeUpdate = productDetailRepository.findAll().size();

        // Update the productDetail using partial update
        ProductDetail partialUpdatedProductDetail = new ProductDetail();
        partialUpdatedProductDetail.setId(productDetail.getId());

        partialUpdatedProductDetail
            .dineInPrice(UPDATED_DINE_IN_PRICE)
            .takeAwayPrice(UPDATED_TAKE_AWAY_PRICE)
            .deliveryPrice(UPDATED_DELIVERY_PRICE);

        restProductDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductDetail))
            )
            .andExpect(status().isOk());

        // Validate the ProductDetail in the database
        List<ProductDetail> productDetailList = productDetailRepository.findAll();
        assertThat(productDetailList).hasSize(databaseSizeBeforeUpdate);
        ProductDetail testProductDetail = productDetailList.get(productDetailList.size() - 1);
        assertThat(testProductDetail.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testProductDetail.getDineInPrice()).isEqualByComparingTo(UPDATED_DINE_IN_PRICE);
        assertThat(testProductDetail.getTakeAwayPrice()).isEqualByComparingTo(UPDATED_TAKE_AWAY_PRICE);
        assertThat(testProductDetail.getDeliveryPrice()).isEqualByComparingTo(UPDATED_DELIVERY_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateProductDetailWithPatch() throws Exception {
        // Initialize the database
        productDetailRepository.saveAndFlush(productDetail);

        int databaseSizeBeforeUpdate = productDetailRepository.findAll().size();

        // Update the productDetail using partial update
        ProductDetail partialUpdatedProductDetail = new ProductDetail();
        partialUpdatedProductDetail.setId(productDetail.getId());

        partialUpdatedProductDetail
            .size(UPDATED_SIZE)
            .dineInPrice(UPDATED_DINE_IN_PRICE)
            .takeAwayPrice(UPDATED_TAKE_AWAY_PRICE)
            .deliveryPrice(UPDATED_DELIVERY_PRICE);

        restProductDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductDetail))
            )
            .andExpect(status().isOk());

        // Validate the ProductDetail in the database
        List<ProductDetail> productDetailList = productDetailRepository.findAll();
        assertThat(productDetailList).hasSize(databaseSizeBeforeUpdate);
        ProductDetail testProductDetail = productDetailList.get(productDetailList.size() - 1);
        assertThat(testProductDetail.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testProductDetail.getDineInPrice()).isEqualByComparingTo(UPDATED_DINE_IN_PRICE);
        assertThat(testProductDetail.getTakeAwayPrice()).isEqualByComparingTo(UPDATED_TAKE_AWAY_PRICE);
        assertThat(testProductDetail.getDeliveryPrice()).isEqualByComparingTo(UPDATED_DELIVERY_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingProductDetail() throws Exception {
        int databaseSizeBeforeUpdate = productDetailRepository.findAll().size();
        productDetail.setId(longCount.incrementAndGet());

        // Create the ProductDetail
        ProductDetailDTO productDetailDTO = productDetailMapper.toDto(productDetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productDetailDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductDetail in the database
        List<ProductDetail> productDetailList = productDetailRepository.findAll();
        assertThat(productDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductDetail() throws Exception {
        int databaseSizeBeforeUpdate = productDetailRepository.findAll().size();
        productDetail.setId(longCount.incrementAndGet());

        // Create the ProductDetail
        ProductDetailDTO productDetailDTO = productDetailMapper.toDto(productDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductDetail in the database
        List<ProductDetail> productDetailList = productDetailRepository.findAll();
        assertThat(productDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductDetail() throws Exception {
        int databaseSizeBeforeUpdate = productDetailRepository.findAll().size();
        productDetail.setId(longCount.incrementAndGet());

        // Create the ProductDetail
        ProductDetailDTO productDetailDTO = productDetailMapper.toDto(productDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductDetailMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productDetailDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductDetail in the database
        List<ProductDetail> productDetailList = productDetailRepository.findAll();
        assertThat(productDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductDetail() throws Exception {
        // Initialize the database
        productDetailRepository.saveAndFlush(productDetail);

        int databaseSizeBeforeDelete = productDetailRepository.findAll().size();

        // Delete the productDetail
        restProductDetailMockMvc
            .perform(delete(ENTITY_API_URL_ID, productDetail.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductDetail> productDetailList = productDetailRepository.findAll();
        assertThat(productDetailList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
