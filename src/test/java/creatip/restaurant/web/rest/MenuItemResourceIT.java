package creatip.restaurant.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import creatip.restaurant.IntegrationTest;
import creatip.restaurant.domain.MenuItem;
import creatip.restaurant.repository.MenuItemRepository;
import creatip.restaurant.service.dto.MenuItemDTO;
import creatip.restaurant.service.mapper.MenuItemMapper;
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
 * Integration tests for the {@link MenuItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MenuItemResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSLATE_KEY = "AAAAAAAAAA";
    private static final String UPDATED_TRANSLATE_KEY = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER_NO = 1;
    private static final Integer UPDATED_ORDER_NO = 2;

    private static final String DEFAULT_FA_ICON = "AAAAAAAAAA";
    private static final String UPDATED_FA_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_ROUTER_LINK = "AAAAAAAAAA";
    private static final String UPDATED_ROUTER_LINK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/menu-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private MenuItemMapper menuItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMenuItemMockMvc;

    private MenuItem menuItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MenuItem createEntity(EntityManager em) {
        MenuItem menuItem = new MenuItem()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .translateKey(DEFAULT_TRANSLATE_KEY)
            .orderNo(DEFAULT_ORDER_NO)
            .faIcon(DEFAULT_FA_ICON)
            .routerLink(DEFAULT_ROUTER_LINK);
        return menuItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MenuItem createUpdatedEntity(EntityManager em) {
        MenuItem menuItem = new MenuItem()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .translateKey(UPDATED_TRANSLATE_KEY)
            .orderNo(UPDATED_ORDER_NO)
            .faIcon(UPDATED_FA_ICON)
            .routerLink(UPDATED_ROUTER_LINK);
        return menuItem;
    }

    @BeforeEach
    public void initTest() {
        menuItem = createEntity(em);
    }

    @Test
    @Transactional
    void createMenuItem() throws Exception {
        int databaseSizeBeforeCreate = menuItemRepository.findAll().size();
        // Create the MenuItem
        MenuItemDTO menuItemDTO = menuItemMapper.toDto(menuItem);
        restMenuItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(menuItemDTO)))
            .andExpect(status().isCreated());

        // Validate the MenuItem in the database
        List<MenuItem> menuItemList = menuItemRepository.findAll();
        assertThat(menuItemList).hasSize(databaseSizeBeforeCreate + 1);
        MenuItem testMenuItem = menuItemList.get(menuItemList.size() - 1);
        assertThat(testMenuItem.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMenuItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMenuItem.getTranslateKey()).isEqualTo(DEFAULT_TRANSLATE_KEY);
        assertThat(testMenuItem.getOrderNo()).isEqualTo(DEFAULT_ORDER_NO);
        assertThat(testMenuItem.getFaIcon()).isEqualTo(DEFAULT_FA_ICON);
        assertThat(testMenuItem.getRouterLink()).isEqualTo(DEFAULT_ROUTER_LINK);
    }

    @Test
    @Transactional
    void createMenuItemWithExistingId() throws Exception {
        // Create the MenuItem with an existing ID
        menuItem.setId(1L);
        MenuItemDTO menuItemDTO = menuItemMapper.toDto(menuItem);

        int databaseSizeBeforeCreate = menuItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMenuItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(menuItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MenuItem in the database
        List<MenuItem> menuItemList = menuItemRepository.findAll();
        assertThat(menuItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuItemRepository.findAll().size();
        // set the field null
        menuItem.setCode(null);

        // Create the MenuItem, which fails.
        MenuItemDTO menuItemDTO = menuItemMapper.toDto(menuItem);

        restMenuItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(menuItemDTO)))
            .andExpect(status().isBadRequest());

        List<MenuItem> menuItemList = menuItemRepository.findAll();
        assertThat(menuItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuItemRepository.findAll().size();
        // set the field null
        menuItem.setName(null);

        // Create the MenuItem, which fails.
        MenuItemDTO menuItemDTO = menuItemMapper.toDto(menuItem);

        restMenuItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(menuItemDTO)))
            .andExpect(status().isBadRequest());

        List<MenuItem> menuItemList = menuItemRepository.findAll();
        assertThat(menuItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMenuItems() throws Exception {
        // Initialize the database
        menuItemRepository.saveAndFlush(menuItem);

        // Get all the menuItemList
        restMenuItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(menuItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].translateKey").value(hasItem(DEFAULT_TRANSLATE_KEY)))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO)))
            .andExpect(jsonPath("$.[*].faIcon").value(hasItem(DEFAULT_FA_ICON)))
            .andExpect(jsonPath("$.[*].routerLink").value(hasItem(DEFAULT_ROUTER_LINK)));
    }

    @Test
    @Transactional
    void getMenuItem() throws Exception {
        // Initialize the database
        menuItemRepository.saveAndFlush(menuItem);

        // Get the menuItem
        restMenuItemMockMvc
            .perform(get(ENTITY_API_URL_ID, menuItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(menuItem.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.translateKey").value(DEFAULT_TRANSLATE_KEY))
            .andExpect(jsonPath("$.orderNo").value(DEFAULT_ORDER_NO))
            .andExpect(jsonPath("$.faIcon").value(DEFAULT_FA_ICON))
            .andExpect(jsonPath("$.routerLink").value(DEFAULT_ROUTER_LINK));
    }

    @Test
    @Transactional
    void getNonExistingMenuItem() throws Exception {
        // Get the menuItem
        restMenuItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMenuItem() throws Exception {
        // Initialize the database
        menuItemRepository.saveAndFlush(menuItem);

        int databaseSizeBeforeUpdate = menuItemRepository.findAll().size();

        // Update the menuItem
        MenuItem updatedMenuItem = menuItemRepository.findById(menuItem.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMenuItem are not directly saved in db
        em.detach(updatedMenuItem);
        updatedMenuItem
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .translateKey(UPDATED_TRANSLATE_KEY)
            .orderNo(UPDATED_ORDER_NO)
            .faIcon(UPDATED_FA_ICON)
            .routerLink(UPDATED_ROUTER_LINK);
        MenuItemDTO menuItemDTO = menuItemMapper.toDto(updatedMenuItem);

        restMenuItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, menuItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(menuItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the MenuItem in the database
        List<MenuItem> menuItemList = menuItemRepository.findAll();
        assertThat(menuItemList).hasSize(databaseSizeBeforeUpdate);
        MenuItem testMenuItem = menuItemList.get(menuItemList.size() - 1);
        assertThat(testMenuItem.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMenuItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMenuItem.getTranslateKey()).isEqualTo(UPDATED_TRANSLATE_KEY);
        assertThat(testMenuItem.getOrderNo()).isEqualTo(UPDATED_ORDER_NO);
        assertThat(testMenuItem.getFaIcon()).isEqualTo(UPDATED_FA_ICON);
        assertThat(testMenuItem.getRouterLink()).isEqualTo(UPDATED_ROUTER_LINK);
    }

    @Test
    @Transactional
    void putNonExistingMenuItem() throws Exception {
        int databaseSizeBeforeUpdate = menuItemRepository.findAll().size();
        menuItem.setId(longCount.incrementAndGet());

        // Create the MenuItem
        MenuItemDTO menuItemDTO = menuItemMapper.toDto(menuItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMenuItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, menuItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(menuItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuItem in the database
        List<MenuItem> menuItemList = menuItemRepository.findAll();
        assertThat(menuItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMenuItem() throws Exception {
        int databaseSizeBeforeUpdate = menuItemRepository.findAll().size();
        menuItem.setId(longCount.incrementAndGet());

        // Create the MenuItem
        MenuItemDTO menuItemDTO = menuItemMapper.toDto(menuItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(menuItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuItem in the database
        List<MenuItem> menuItemList = menuItemRepository.findAll();
        assertThat(menuItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMenuItem() throws Exception {
        int databaseSizeBeforeUpdate = menuItemRepository.findAll().size();
        menuItem.setId(longCount.incrementAndGet());

        // Create the MenuItem
        MenuItemDTO menuItemDTO = menuItemMapper.toDto(menuItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuItemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(menuItemDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MenuItem in the database
        List<MenuItem> menuItemList = menuItemRepository.findAll();
        assertThat(menuItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMenuItemWithPatch() throws Exception {
        // Initialize the database
        menuItemRepository.saveAndFlush(menuItem);

        int databaseSizeBeforeUpdate = menuItemRepository.findAll().size();

        // Update the menuItem using partial update
        MenuItem partialUpdatedMenuItem = new MenuItem();
        partialUpdatedMenuItem.setId(menuItem.getId());

        partialUpdatedMenuItem.name(UPDATED_NAME).translateKey(UPDATED_TRANSLATE_KEY).faIcon(UPDATED_FA_ICON);

        restMenuItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMenuItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMenuItem))
            )
            .andExpect(status().isOk());

        // Validate the MenuItem in the database
        List<MenuItem> menuItemList = menuItemRepository.findAll();
        assertThat(menuItemList).hasSize(databaseSizeBeforeUpdate);
        MenuItem testMenuItem = menuItemList.get(menuItemList.size() - 1);
        assertThat(testMenuItem.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMenuItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMenuItem.getTranslateKey()).isEqualTo(UPDATED_TRANSLATE_KEY);
        assertThat(testMenuItem.getOrderNo()).isEqualTo(DEFAULT_ORDER_NO);
        assertThat(testMenuItem.getFaIcon()).isEqualTo(UPDATED_FA_ICON);
        assertThat(testMenuItem.getRouterLink()).isEqualTo(DEFAULT_ROUTER_LINK);
    }

    @Test
    @Transactional
    void fullUpdateMenuItemWithPatch() throws Exception {
        // Initialize the database
        menuItemRepository.saveAndFlush(menuItem);

        int databaseSizeBeforeUpdate = menuItemRepository.findAll().size();

        // Update the menuItem using partial update
        MenuItem partialUpdatedMenuItem = new MenuItem();
        partialUpdatedMenuItem.setId(menuItem.getId());

        partialUpdatedMenuItem
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .translateKey(UPDATED_TRANSLATE_KEY)
            .orderNo(UPDATED_ORDER_NO)
            .faIcon(UPDATED_FA_ICON)
            .routerLink(UPDATED_ROUTER_LINK);

        restMenuItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMenuItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMenuItem))
            )
            .andExpect(status().isOk());

        // Validate the MenuItem in the database
        List<MenuItem> menuItemList = menuItemRepository.findAll();
        assertThat(menuItemList).hasSize(databaseSizeBeforeUpdate);
        MenuItem testMenuItem = menuItemList.get(menuItemList.size() - 1);
        assertThat(testMenuItem.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMenuItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMenuItem.getTranslateKey()).isEqualTo(UPDATED_TRANSLATE_KEY);
        assertThat(testMenuItem.getOrderNo()).isEqualTo(UPDATED_ORDER_NO);
        assertThat(testMenuItem.getFaIcon()).isEqualTo(UPDATED_FA_ICON);
        assertThat(testMenuItem.getRouterLink()).isEqualTo(UPDATED_ROUTER_LINK);
    }

    @Test
    @Transactional
    void patchNonExistingMenuItem() throws Exception {
        int databaseSizeBeforeUpdate = menuItemRepository.findAll().size();
        menuItem.setId(longCount.incrementAndGet());

        // Create the MenuItem
        MenuItemDTO menuItemDTO = menuItemMapper.toDto(menuItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMenuItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, menuItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(menuItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuItem in the database
        List<MenuItem> menuItemList = menuItemRepository.findAll();
        assertThat(menuItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMenuItem() throws Exception {
        int databaseSizeBeforeUpdate = menuItemRepository.findAll().size();
        menuItem.setId(longCount.incrementAndGet());

        // Create the MenuItem
        MenuItemDTO menuItemDTO = menuItemMapper.toDto(menuItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(menuItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuItem in the database
        List<MenuItem> menuItemList = menuItemRepository.findAll();
        assertThat(menuItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMenuItem() throws Exception {
        int databaseSizeBeforeUpdate = menuItemRepository.findAll().size();
        menuItem.setId(longCount.incrementAndGet());

        // Create the MenuItem
        MenuItemDTO menuItemDTO = menuItemMapper.toDto(menuItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuItemMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(menuItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MenuItem in the database
        List<MenuItem> menuItemList = menuItemRepository.findAll();
        assertThat(menuItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMenuItem() throws Exception {
        // Initialize the database
        menuItemRepository.saveAndFlush(menuItem);

        int databaseSizeBeforeDelete = menuItemRepository.findAll().size();

        // Delete the menuItem
        restMenuItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, menuItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MenuItem> menuItemList = menuItemRepository.findAll();
        assertThat(menuItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
