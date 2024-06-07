package creatip.restaurant.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import creatip.restaurant.IntegrationTest;
import creatip.restaurant.domain.MenuType;
import creatip.restaurant.repository.MenuTypeRepository;
import creatip.restaurant.service.dto.MenuTypeDTO;
import creatip.restaurant.service.mapper.MenuTypeMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link MenuTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MenuTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/menu-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MenuTypeRepository menuTypeRepository;

    @Autowired
    private MenuTypeMapper menuTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMenuTypeMockMvc;

    private MenuType menuType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MenuType createEntity(EntityManager em) {
        MenuType menuType = new MenuType().name(DEFAULT_NAME);
        return menuType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MenuType createUpdatedEntity(EntityManager em) {
        MenuType menuType = new MenuType().name(UPDATED_NAME);
        return menuType;
    }

    @BeforeEach
    public void initTest() {
        menuType = createEntity(em);
    }

    @Test
    @Transactional
    void createMenuType() throws Exception {
        int databaseSizeBeforeCreate = menuTypeRepository.findAll().size();
        // Create the MenuType
        MenuTypeDTO menuTypeDTO = menuTypeMapper.toDto(menuType);
        restMenuTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(menuTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the MenuType in the database
        List<MenuType> menuTypeList = menuTypeRepository.findAll();
        assertThat(menuTypeList).hasSize(databaseSizeBeforeCreate + 1);
        MenuType testMenuType = menuTypeList.get(menuTypeList.size() - 1);
        assertThat(testMenuType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createMenuTypeWithExistingId() throws Exception {
        // Create the MenuType with an existing ID
        menuType.setId(1L);
        MenuTypeDTO menuTypeDTO = menuTypeMapper.toDto(menuType);

        int databaseSizeBeforeCreate = menuTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMenuTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(menuTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MenuType in the database
        List<MenuType> menuTypeList = menuTypeRepository.findAll();
        assertThat(menuTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuTypeRepository.findAll().size();
        // set the field null
        menuType.setName(null);

        // Create the MenuType, which fails.
        MenuTypeDTO menuTypeDTO = menuTypeMapper.toDto(menuType);

        restMenuTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(menuTypeDTO)))
            .andExpect(status().isBadRequest());

        List<MenuType> menuTypeList = menuTypeRepository.findAll();
        assertThat(menuTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMenuTypes() throws Exception {
        // Initialize the database
        menuTypeRepository.saveAndFlush(menuType);

        // Get all the menuTypeList
        restMenuTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(menuType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getMenuType() throws Exception {
        // Initialize the database
        menuTypeRepository.saveAndFlush(menuType);

        // Get the menuType
        restMenuTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, menuType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(menuType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingMenuType() throws Exception {
        // Get the menuType
        restMenuTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMenuType() throws Exception {
        // Initialize the database
        menuTypeRepository.saveAndFlush(menuType);

        int databaseSizeBeforeUpdate = menuTypeRepository.findAll().size();

        // Update the menuType
        MenuType updatedMenuType = menuTypeRepository.findById(menuType.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMenuType are not directly saved in db
        em.detach(updatedMenuType);
        updatedMenuType.name(UPDATED_NAME);
        MenuTypeDTO menuTypeDTO = menuTypeMapper.toDto(updatedMenuType);

        restMenuTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, menuTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(menuTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the MenuType in the database
        List<MenuType> menuTypeList = menuTypeRepository.findAll();
        assertThat(menuTypeList).hasSize(databaseSizeBeforeUpdate);
        MenuType testMenuType = menuTypeList.get(menuTypeList.size() - 1);
        assertThat(testMenuType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingMenuType() throws Exception {
        int databaseSizeBeforeUpdate = menuTypeRepository.findAll().size();
        menuType.setId(longCount.incrementAndGet());

        // Create the MenuType
        MenuTypeDTO menuTypeDTO = menuTypeMapper.toDto(menuType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMenuTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, menuTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(menuTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuType in the database
        List<MenuType> menuTypeList = menuTypeRepository.findAll();
        assertThat(menuTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMenuType() throws Exception {
        int databaseSizeBeforeUpdate = menuTypeRepository.findAll().size();
        menuType.setId(longCount.incrementAndGet());

        // Create the MenuType
        MenuTypeDTO menuTypeDTO = menuTypeMapper.toDto(menuType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(menuTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuType in the database
        List<MenuType> menuTypeList = menuTypeRepository.findAll();
        assertThat(menuTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMenuType() throws Exception {
        int databaseSizeBeforeUpdate = menuTypeRepository.findAll().size();
        menuType.setId(longCount.incrementAndGet());

        // Create the MenuType
        MenuTypeDTO menuTypeDTO = menuTypeMapper.toDto(menuType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(menuTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MenuType in the database
        List<MenuType> menuTypeList = menuTypeRepository.findAll();
        assertThat(menuTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMenuTypeWithPatch() throws Exception {
        // Initialize the database
        menuTypeRepository.saveAndFlush(menuType);

        int databaseSizeBeforeUpdate = menuTypeRepository.findAll().size();

        // Update the menuType using partial update
        MenuType partialUpdatedMenuType = new MenuType();
        partialUpdatedMenuType.setId(menuType.getId());

        partialUpdatedMenuType.name(UPDATED_NAME);

        restMenuTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMenuType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMenuType))
            )
            .andExpect(status().isOk());

        // Validate the MenuType in the database
        List<MenuType> menuTypeList = menuTypeRepository.findAll();
        assertThat(menuTypeList).hasSize(databaseSizeBeforeUpdate);
        MenuType testMenuType = menuTypeList.get(menuTypeList.size() - 1);
        assertThat(testMenuType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateMenuTypeWithPatch() throws Exception {
        // Initialize the database
        menuTypeRepository.saveAndFlush(menuType);

        int databaseSizeBeforeUpdate = menuTypeRepository.findAll().size();

        // Update the menuType using partial update
        MenuType partialUpdatedMenuType = new MenuType();
        partialUpdatedMenuType.setId(menuType.getId());

        partialUpdatedMenuType.name(UPDATED_NAME);

        restMenuTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMenuType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMenuType))
            )
            .andExpect(status().isOk());

        // Validate the MenuType in the database
        List<MenuType> menuTypeList = menuTypeRepository.findAll();
        assertThat(menuTypeList).hasSize(databaseSizeBeforeUpdate);
        MenuType testMenuType = menuTypeList.get(menuTypeList.size() - 1);
        assertThat(testMenuType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingMenuType() throws Exception {
        int databaseSizeBeforeUpdate = menuTypeRepository.findAll().size();
        menuType.setId(longCount.incrementAndGet());

        // Create the MenuType
        MenuTypeDTO menuTypeDTO = menuTypeMapper.toDto(menuType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMenuTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, menuTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(menuTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuType in the database
        List<MenuType> menuTypeList = menuTypeRepository.findAll();
        assertThat(menuTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMenuType() throws Exception {
        int databaseSizeBeforeUpdate = menuTypeRepository.findAll().size();
        menuType.setId(longCount.incrementAndGet());

        // Create the MenuType
        MenuTypeDTO menuTypeDTO = menuTypeMapper.toDto(menuType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(menuTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuType in the database
        List<MenuType> menuTypeList = menuTypeRepository.findAll();
        assertThat(menuTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMenuType() throws Exception {
        int databaseSizeBeforeUpdate = menuTypeRepository.findAll().size();
        menuType.setId(longCount.incrementAndGet());

        // Create the MenuType
        MenuTypeDTO menuTypeDTO = menuTypeMapper.toDto(menuType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(menuTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MenuType in the database
        List<MenuType> menuTypeList = menuTypeRepository.findAll();
        assertThat(menuTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMenuType() throws Exception {
        // Initialize the database
        menuTypeRepository.saveAndFlush(menuType);

        int databaseSizeBeforeDelete = menuTypeRepository.findAll().size();

        // Delete the menuType
        restMenuTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, menuType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MenuType> menuTypeList = menuTypeRepository.findAll();
        assertThat(menuTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
