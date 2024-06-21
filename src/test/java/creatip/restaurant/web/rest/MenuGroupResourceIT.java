package creatip.restaurant.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import creatip.restaurant.IntegrationTest;
import creatip.restaurant.domain.MenuGroup;
import creatip.restaurant.repository.MenuGroupRepository;
import creatip.restaurant.service.dto.MenuGroupDTO;
import creatip.restaurant.service.mapper.MenuGroupMapper;
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
 * Integration tests for the {@link MenuGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MenuGroupResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_IS_CHILD = 0;
    private static final Integer UPDATED_IS_CHILD = 1;

    private static final Long DEFAULT_PARENT_ID = 1L;
    private static final Long UPDATED_PARENT_ID = 2L;

    private static final String DEFAULT_TRANSLATE_KEY = "AAAAAAAAAA";
    private static final String UPDATED_TRANSLATE_KEY = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER_NO = 1;
    private static final Integer UPDATED_ORDER_NO = 2;

    private static final String DEFAULT_FA_ICON = "AAAAAAAAAA";
    private static final String UPDATED_FA_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_ROUTER_LINK = "AAAAAAAAAA";
    private static final String UPDATED_ROUTER_LINK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/menu-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MenuGroupRepository menuGroupRepository;

    @Autowired
    private MenuGroupMapper menuGroupMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMenuGroupMockMvc;

    private MenuGroup menuGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MenuGroup createEntity(EntityManager em) {
        MenuGroup menuGroup = new MenuGroup()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .isChild(DEFAULT_IS_CHILD)
            .parentId(DEFAULT_PARENT_ID)
            .translateKey(DEFAULT_TRANSLATE_KEY)
            .orderNo(DEFAULT_ORDER_NO)
            .faIcon(DEFAULT_FA_ICON)
            .routerLink(DEFAULT_ROUTER_LINK);
        return menuGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MenuGroup createUpdatedEntity(EntityManager em) {
        MenuGroup menuGroup = new MenuGroup()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .isChild(UPDATED_IS_CHILD)
            .parentId(UPDATED_PARENT_ID)
            .translateKey(UPDATED_TRANSLATE_KEY)
            .orderNo(UPDATED_ORDER_NO)
            .faIcon(UPDATED_FA_ICON)
            .routerLink(UPDATED_ROUTER_LINK);
        return menuGroup;
    }

    @BeforeEach
    public void initTest() {
        menuGroup = createEntity(em);
    }

    @Test
    @Transactional
    void createMenuGroup() throws Exception {
        int databaseSizeBeforeCreate = menuGroupRepository.findAll().size();
        // Create the MenuGroup
        MenuGroupDTO menuGroupDTO = menuGroupMapper.toDto(menuGroup);
        restMenuGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(menuGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the MenuGroup in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeCreate + 1);
        MenuGroup testMenuGroup = menuGroupList.get(menuGroupList.size() - 1);
        assertThat(testMenuGroup.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMenuGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMenuGroup.getIsChild()).isEqualTo(DEFAULT_IS_CHILD);
        assertThat(testMenuGroup.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testMenuGroup.getTranslateKey()).isEqualTo(DEFAULT_TRANSLATE_KEY);
        assertThat(testMenuGroup.getOrderNo()).isEqualTo(DEFAULT_ORDER_NO);
        assertThat(testMenuGroup.getFaIcon()).isEqualTo(DEFAULT_FA_ICON);
        assertThat(testMenuGroup.getRouterLink()).isEqualTo(DEFAULT_ROUTER_LINK);
    }

    @Test
    @Transactional
    void createMenuGroupWithExistingId() throws Exception {
        // Create the MenuGroup with an existing ID
        menuGroup.setId(1L);
        MenuGroupDTO menuGroupDTO = menuGroupMapper.toDto(menuGroup);

        int databaseSizeBeforeCreate = menuGroupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMenuGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(menuGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MenuGroup in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuGroupRepository.findAll().size();
        // set the field null
        menuGroup.setCode(null);

        // Create the MenuGroup, which fails.
        MenuGroupDTO menuGroupDTO = menuGroupMapper.toDto(menuGroup);

        restMenuGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(menuGroupDTO)))
            .andExpect(status().isBadRequest());

        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuGroupRepository.findAll().size();
        // set the field null
        menuGroup.setName(null);

        // Create the MenuGroup, which fails.
        MenuGroupDTO menuGroupDTO = menuGroupMapper.toDto(menuGroup);

        restMenuGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(menuGroupDTO)))
            .andExpect(status().isBadRequest());

        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMenuGroups() throws Exception {
        // Initialize the database
        menuGroupRepository.saveAndFlush(menuGroup);

        // Get all the menuGroupList
        restMenuGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(menuGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isChild").value(hasItem(DEFAULT_IS_CHILD)))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].translateKey").value(hasItem(DEFAULT_TRANSLATE_KEY)))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO)))
            .andExpect(jsonPath("$.[*].faIcon").value(hasItem(DEFAULT_FA_ICON)))
            .andExpect(jsonPath("$.[*].routerLink").value(hasItem(DEFAULT_ROUTER_LINK)));
    }

    @Test
    @Transactional
    void getMenuGroup() throws Exception {
        // Initialize the database
        menuGroupRepository.saveAndFlush(menuGroup);

        // Get the menuGroup
        restMenuGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, menuGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(menuGroup.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.isChild").value(DEFAULT_IS_CHILD))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.intValue()))
            .andExpect(jsonPath("$.translateKey").value(DEFAULT_TRANSLATE_KEY))
            .andExpect(jsonPath("$.orderNo").value(DEFAULT_ORDER_NO))
            .andExpect(jsonPath("$.faIcon").value(DEFAULT_FA_ICON))
            .andExpect(jsonPath("$.routerLink").value(DEFAULT_ROUTER_LINK));
    }

    @Test
    @Transactional
    void getNonExistingMenuGroup() throws Exception {
        // Get the menuGroup
        restMenuGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMenuGroup() throws Exception {
        // Initialize the database
        menuGroupRepository.saveAndFlush(menuGroup);

        int databaseSizeBeforeUpdate = menuGroupRepository.findAll().size();

        // Update the menuGroup
        MenuGroup updatedMenuGroup = menuGroupRepository.findById(menuGroup.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMenuGroup are not directly saved in db
        em.detach(updatedMenuGroup);
        updatedMenuGroup
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .isChild(UPDATED_IS_CHILD)
            .parentId(UPDATED_PARENT_ID)
            .translateKey(UPDATED_TRANSLATE_KEY)
            .orderNo(UPDATED_ORDER_NO)
            .faIcon(UPDATED_FA_ICON)
            .routerLink(UPDATED_ROUTER_LINK);
        MenuGroupDTO menuGroupDTO = menuGroupMapper.toDto(updatedMenuGroup);

        restMenuGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, menuGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(menuGroupDTO))
            )
            .andExpect(status().isOk());

        // Validate the MenuGroup in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeUpdate);
        MenuGroup testMenuGroup = menuGroupList.get(menuGroupList.size() - 1);
        assertThat(testMenuGroup.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMenuGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMenuGroup.getIsChild()).isEqualTo(UPDATED_IS_CHILD);
        assertThat(testMenuGroup.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testMenuGroup.getTranslateKey()).isEqualTo(UPDATED_TRANSLATE_KEY);
        assertThat(testMenuGroup.getOrderNo()).isEqualTo(UPDATED_ORDER_NO);
        assertThat(testMenuGroup.getFaIcon()).isEqualTo(UPDATED_FA_ICON);
        assertThat(testMenuGroup.getRouterLink()).isEqualTo(UPDATED_ROUTER_LINK);
    }

    @Test
    @Transactional
    void putNonExistingMenuGroup() throws Exception {
        int databaseSizeBeforeUpdate = menuGroupRepository.findAll().size();
        menuGroup.setId(longCount.incrementAndGet());

        // Create the MenuGroup
        MenuGroupDTO menuGroupDTO = menuGroupMapper.toDto(menuGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMenuGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, menuGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(menuGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuGroup in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMenuGroup() throws Exception {
        int databaseSizeBeforeUpdate = menuGroupRepository.findAll().size();
        menuGroup.setId(longCount.incrementAndGet());

        // Create the MenuGroup
        MenuGroupDTO menuGroupDTO = menuGroupMapper.toDto(menuGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(menuGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuGroup in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMenuGroup() throws Exception {
        int databaseSizeBeforeUpdate = menuGroupRepository.findAll().size();
        menuGroup.setId(longCount.incrementAndGet());

        // Create the MenuGroup
        MenuGroupDTO menuGroupDTO = menuGroupMapper.toDto(menuGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuGroupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(menuGroupDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MenuGroup in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMenuGroupWithPatch() throws Exception {
        // Initialize the database
        menuGroupRepository.saveAndFlush(menuGroup);

        int databaseSizeBeforeUpdate = menuGroupRepository.findAll().size();

        // Update the menuGroup using partial update
        MenuGroup partialUpdatedMenuGroup = new MenuGroup();
        partialUpdatedMenuGroup.setId(menuGroup.getId());

        partialUpdatedMenuGroup.code(UPDATED_CODE).name(UPDATED_NAME).parentId(UPDATED_PARENT_ID).orderNo(UPDATED_ORDER_NO);

        restMenuGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMenuGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMenuGroup))
            )
            .andExpect(status().isOk());

        // Validate the MenuGroup in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeUpdate);
        MenuGroup testMenuGroup = menuGroupList.get(menuGroupList.size() - 1);
        assertThat(testMenuGroup.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMenuGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMenuGroup.getIsChild()).isEqualTo(DEFAULT_IS_CHILD);
        assertThat(testMenuGroup.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testMenuGroup.getTranslateKey()).isEqualTo(DEFAULT_TRANSLATE_KEY);
        assertThat(testMenuGroup.getOrderNo()).isEqualTo(UPDATED_ORDER_NO);
        assertThat(testMenuGroup.getFaIcon()).isEqualTo(DEFAULT_FA_ICON);
        assertThat(testMenuGroup.getRouterLink()).isEqualTo(DEFAULT_ROUTER_LINK);
    }

    @Test
    @Transactional
    void fullUpdateMenuGroupWithPatch() throws Exception {
        // Initialize the database
        menuGroupRepository.saveAndFlush(menuGroup);

        int databaseSizeBeforeUpdate = menuGroupRepository.findAll().size();

        // Update the menuGroup using partial update
        MenuGroup partialUpdatedMenuGroup = new MenuGroup();
        partialUpdatedMenuGroup.setId(menuGroup.getId());

        partialUpdatedMenuGroup
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .isChild(UPDATED_IS_CHILD)
            .parentId(UPDATED_PARENT_ID)
            .translateKey(UPDATED_TRANSLATE_KEY)
            .orderNo(UPDATED_ORDER_NO)
            .faIcon(UPDATED_FA_ICON)
            .routerLink(UPDATED_ROUTER_LINK);

        restMenuGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMenuGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMenuGroup))
            )
            .andExpect(status().isOk());

        // Validate the MenuGroup in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeUpdate);
        MenuGroup testMenuGroup = menuGroupList.get(menuGroupList.size() - 1);
        assertThat(testMenuGroup.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMenuGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMenuGroup.getIsChild()).isEqualTo(UPDATED_IS_CHILD);
        assertThat(testMenuGroup.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testMenuGroup.getTranslateKey()).isEqualTo(UPDATED_TRANSLATE_KEY);
        assertThat(testMenuGroup.getOrderNo()).isEqualTo(UPDATED_ORDER_NO);
        assertThat(testMenuGroup.getFaIcon()).isEqualTo(UPDATED_FA_ICON);
        assertThat(testMenuGroup.getRouterLink()).isEqualTo(UPDATED_ROUTER_LINK);
    }

    @Test
    @Transactional
    void patchNonExistingMenuGroup() throws Exception {
        int databaseSizeBeforeUpdate = menuGroupRepository.findAll().size();
        menuGroup.setId(longCount.incrementAndGet());

        // Create the MenuGroup
        MenuGroupDTO menuGroupDTO = menuGroupMapper.toDto(menuGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMenuGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, menuGroupDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(menuGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuGroup in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMenuGroup() throws Exception {
        int databaseSizeBeforeUpdate = menuGroupRepository.findAll().size();
        menuGroup.setId(longCount.incrementAndGet());

        // Create the MenuGroup
        MenuGroupDTO menuGroupDTO = menuGroupMapper.toDto(menuGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(menuGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuGroup in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMenuGroup() throws Exception {
        int databaseSizeBeforeUpdate = menuGroupRepository.findAll().size();
        menuGroup.setId(longCount.incrementAndGet());

        // Create the MenuGroup
        MenuGroupDTO menuGroupDTO = menuGroupMapper.toDto(menuGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuGroupMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(menuGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MenuGroup in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMenuGroup() throws Exception {
        // Initialize the database
        menuGroupRepository.saveAndFlush(menuGroup);

        int databaseSizeBeforeDelete = menuGroupRepository.findAll().size();

        // Delete the menuGroup
        restMenuGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, menuGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
