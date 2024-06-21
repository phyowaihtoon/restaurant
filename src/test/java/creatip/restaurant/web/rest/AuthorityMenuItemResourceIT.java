package creatip.restaurant.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import creatip.restaurant.IntegrationTest;
import creatip.restaurant.domain.AuthorityMenuItem;
import creatip.restaurant.repository.AuthorityMenuItemRepository;
import creatip.restaurant.service.dto.AuthorityMenuItemDTO;
import creatip.restaurant.service.mapper.AuthorityMenuItemMapper;
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
 * Integration tests for the {@link AuthorityMenuItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AuthorityMenuItemResourceIT {

    private static final Integer DEFAULT_IS_ALLOW = 0;
    private static final Integer UPDATED_IS_ALLOW = 1;

    private static final Integer DEFAULT_IS_READ = 0;
    private static final Integer UPDATED_IS_READ = 1;

    private static final Integer DEFAULT_IS_WRITE = 0;
    private static final Integer UPDATED_IS_WRITE = 1;

    private static final Integer DEFAULT_IS_DELETE = 0;
    private static final Integer UPDATED_IS_DELETE = 1;

    private static final String ENTITY_API_URL = "/api/authority-menu-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AuthorityMenuItemRepository authorityMenuItemRepository;

    @Autowired
    private AuthorityMenuItemMapper authorityMenuItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAuthorityMenuItemMockMvc;

    private AuthorityMenuItem authorityMenuItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuthorityMenuItem createEntity(EntityManager em) {
        AuthorityMenuItem authorityMenuItem = new AuthorityMenuItem()
            .isAllow(DEFAULT_IS_ALLOW)
            .isRead(DEFAULT_IS_READ)
            .isWrite(DEFAULT_IS_WRITE)
            .isDelete(DEFAULT_IS_DELETE);
        return authorityMenuItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuthorityMenuItem createUpdatedEntity(EntityManager em) {
        AuthorityMenuItem authorityMenuItem = new AuthorityMenuItem()
            .isAllow(UPDATED_IS_ALLOW)
            .isRead(UPDATED_IS_READ)
            .isWrite(UPDATED_IS_WRITE)
            .isDelete(UPDATED_IS_DELETE);
        return authorityMenuItem;
    }

    @BeforeEach
    public void initTest() {
        authorityMenuItem = createEntity(em);
    }

    @Test
    @Transactional
    void createAuthorityMenuItem() throws Exception {
        int databaseSizeBeforeCreate = authorityMenuItemRepository.findAll().size();
        // Create the AuthorityMenuItem
        AuthorityMenuItemDTO authorityMenuItemDTO = authorityMenuItemMapper.toDto(authorityMenuItem);
        restAuthorityMenuItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorityMenuItemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AuthorityMenuItem in the database
        List<AuthorityMenuItem> authorityMenuItemList = authorityMenuItemRepository.findAll();
        assertThat(authorityMenuItemList).hasSize(databaseSizeBeforeCreate + 1);
        AuthorityMenuItem testAuthorityMenuItem = authorityMenuItemList.get(authorityMenuItemList.size() - 1);
        assertThat(testAuthorityMenuItem.getIsAllow()).isEqualTo(DEFAULT_IS_ALLOW);
        assertThat(testAuthorityMenuItem.getIsRead()).isEqualTo(DEFAULT_IS_READ);
        assertThat(testAuthorityMenuItem.getIsWrite()).isEqualTo(DEFAULT_IS_WRITE);
        assertThat(testAuthorityMenuItem.getIsDelete()).isEqualTo(DEFAULT_IS_DELETE);
    }

    @Test
    @Transactional
    void createAuthorityMenuItemWithExistingId() throws Exception {
        // Create the AuthorityMenuItem with an existing ID
        authorityMenuItem.setId(1L);
        AuthorityMenuItemDTO authorityMenuItemDTO = authorityMenuItemMapper.toDto(authorityMenuItem);

        int databaseSizeBeforeCreate = authorityMenuItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuthorityMenuItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorityMenuItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthorityMenuItem in the database
        List<AuthorityMenuItem> authorityMenuItemList = authorityMenuItemRepository.findAll();
        assertThat(authorityMenuItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAuthorityMenuItems() throws Exception {
        // Initialize the database
        authorityMenuItemRepository.saveAndFlush(authorityMenuItem);

        // Get all the authorityMenuItemList
        restAuthorityMenuItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(authorityMenuItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].isAllow").value(hasItem(DEFAULT_IS_ALLOW)))
            .andExpect(jsonPath("$.[*].isRead").value(hasItem(DEFAULT_IS_READ)))
            .andExpect(jsonPath("$.[*].isWrite").value(hasItem(DEFAULT_IS_WRITE)))
            .andExpect(jsonPath("$.[*].isDelete").value(hasItem(DEFAULT_IS_DELETE)));
    }

    @Test
    @Transactional
    void getAuthorityMenuItem() throws Exception {
        // Initialize the database
        authorityMenuItemRepository.saveAndFlush(authorityMenuItem);

        // Get the authorityMenuItem
        restAuthorityMenuItemMockMvc
            .perform(get(ENTITY_API_URL_ID, authorityMenuItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(authorityMenuItem.getId().intValue()))
            .andExpect(jsonPath("$.isAllow").value(DEFAULT_IS_ALLOW))
            .andExpect(jsonPath("$.isRead").value(DEFAULT_IS_READ))
            .andExpect(jsonPath("$.isWrite").value(DEFAULT_IS_WRITE))
            .andExpect(jsonPath("$.isDelete").value(DEFAULT_IS_DELETE));
    }

    @Test
    @Transactional
    void getNonExistingAuthorityMenuItem() throws Exception {
        // Get the authorityMenuItem
        restAuthorityMenuItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAuthorityMenuItem() throws Exception {
        // Initialize the database
        authorityMenuItemRepository.saveAndFlush(authorityMenuItem);

        int databaseSizeBeforeUpdate = authorityMenuItemRepository.findAll().size();

        // Update the authorityMenuItem
        AuthorityMenuItem updatedAuthorityMenuItem = authorityMenuItemRepository.findById(authorityMenuItem.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAuthorityMenuItem are not directly saved in db
        em.detach(updatedAuthorityMenuItem);
        updatedAuthorityMenuItem.isAllow(UPDATED_IS_ALLOW).isRead(UPDATED_IS_READ).isWrite(UPDATED_IS_WRITE).isDelete(UPDATED_IS_DELETE);
        AuthorityMenuItemDTO authorityMenuItemDTO = authorityMenuItemMapper.toDto(updatedAuthorityMenuItem);

        restAuthorityMenuItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, authorityMenuItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorityMenuItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the AuthorityMenuItem in the database
        List<AuthorityMenuItem> authorityMenuItemList = authorityMenuItemRepository.findAll();
        assertThat(authorityMenuItemList).hasSize(databaseSizeBeforeUpdate);
        AuthorityMenuItem testAuthorityMenuItem = authorityMenuItemList.get(authorityMenuItemList.size() - 1);
        assertThat(testAuthorityMenuItem.getIsAllow()).isEqualTo(UPDATED_IS_ALLOW);
        assertThat(testAuthorityMenuItem.getIsRead()).isEqualTo(UPDATED_IS_READ);
        assertThat(testAuthorityMenuItem.getIsWrite()).isEqualTo(UPDATED_IS_WRITE);
        assertThat(testAuthorityMenuItem.getIsDelete()).isEqualTo(UPDATED_IS_DELETE);
    }

    @Test
    @Transactional
    void putNonExistingAuthorityMenuItem() throws Exception {
        int databaseSizeBeforeUpdate = authorityMenuItemRepository.findAll().size();
        authorityMenuItem.setId(longCount.incrementAndGet());

        // Create the AuthorityMenuItem
        AuthorityMenuItemDTO authorityMenuItemDTO = authorityMenuItemMapper.toDto(authorityMenuItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthorityMenuItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, authorityMenuItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorityMenuItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthorityMenuItem in the database
        List<AuthorityMenuItem> authorityMenuItemList = authorityMenuItemRepository.findAll();
        assertThat(authorityMenuItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAuthorityMenuItem() throws Exception {
        int databaseSizeBeforeUpdate = authorityMenuItemRepository.findAll().size();
        authorityMenuItem.setId(longCount.incrementAndGet());

        // Create the AuthorityMenuItem
        AuthorityMenuItemDTO authorityMenuItemDTO = authorityMenuItemMapper.toDto(authorityMenuItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorityMenuItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorityMenuItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthorityMenuItem in the database
        List<AuthorityMenuItem> authorityMenuItemList = authorityMenuItemRepository.findAll();
        assertThat(authorityMenuItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAuthorityMenuItem() throws Exception {
        int databaseSizeBeforeUpdate = authorityMenuItemRepository.findAll().size();
        authorityMenuItem.setId(longCount.incrementAndGet());

        // Create the AuthorityMenuItem
        AuthorityMenuItemDTO authorityMenuItemDTO = authorityMenuItemMapper.toDto(authorityMenuItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorityMenuItemMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(authorityMenuItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AuthorityMenuItem in the database
        List<AuthorityMenuItem> authorityMenuItemList = authorityMenuItemRepository.findAll();
        assertThat(authorityMenuItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAuthorityMenuItemWithPatch() throws Exception {
        // Initialize the database
        authorityMenuItemRepository.saveAndFlush(authorityMenuItem);

        int databaseSizeBeforeUpdate = authorityMenuItemRepository.findAll().size();

        // Update the authorityMenuItem using partial update
        AuthorityMenuItem partialUpdatedAuthorityMenuItem = new AuthorityMenuItem();
        partialUpdatedAuthorityMenuItem.setId(authorityMenuItem.getId());

        partialUpdatedAuthorityMenuItem.isRead(UPDATED_IS_READ).isWrite(UPDATED_IS_WRITE);

        restAuthorityMenuItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuthorityMenuItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuthorityMenuItem))
            )
            .andExpect(status().isOk());

        // Validate the AuthorityMenuItem in the database
        List<AuthorityMenuItem> authorityMenuItemList = authorityMenuItemRepository.findAll();
        assertThat(authorityMenuItemList).hasSize(databaseSizeBeforeUpdate);
        AuthorityMenuItem testAuthorityMenuItem = authorityMenuItemList.get(authorityMenuItemList.size() - 1);
        assertThat(testAuthorityMenuItem.getIsAllow()).isEqualTo(DEFAULT_IS_ALLOW);
        assertThat(testAuthorityMenuItem.getIsRead()).isEqualTo(UPDATED_IS_READ);
        assertThat(testAuthorityMenuItem.getIsWrite()).isEqualTo(UPDATED_IS_WRITE);
        assertThat(testAuthorityMenuItem.getIsDelete()).isEqualTo(DEFAULT_IS_DELETE);
    }

    @Test
    @Transactional
    void fullUpdateAuthorityMenuItemWithPatch() throws Exception {
        // Initialize the database
        authorityMenuItemRepository.saveAndFlush(authorityMenuItem);

        int databaseSizeBeforeUpdate = authorityMenuItemRepository.findAll().size();

        // Update the authorityMenuItem using partial update
        AuthorityMenuItem partialUpdatedAuthorityMenuItem = new AuthorityMenuItem();
        partialUpdatedAuthorityMenuItem.setId(authorityMenuItem.getId());

        partialUpdatedAuthorityMenuItem
            .isAllow(UPDATED_IS_ALLOW)
            .isRead(UPDATED_IS_READ)
            .isWrite(UPDATED_IS_WRITE)
            .isDelete(UPDATED_IS_DELETE);

        restAuthorityMenuItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuthorityMenuItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuthorityMenuItem))
            )
            .andExpect(status().isOk());

        // Validate the AuthorityMenuItem in the database
        List<AuthorityMenuItem> authorityMenuItemList = authorityMenuItemRepository.findAll();
        assertThat(authorityMenuItemList).hasSize(databaseSizeBeforeUpdate);
        AuthorityMenuItem testAuthorityMenuItem = authorityMenuItemList.get(authorityMenuItemList.size() - 1);
        assertThat(testAuthorityMenuItem.getIsAllow()).isEqualTo(UPDATED_IS_ALLOW);
        assertThat(testAuthorityMenuItem.getIsRead()).isEqualTo(UPDATED_IS_READ);
        assertThat(testAuthorityMenuItem.getIsWrite()).isEqualTo(UPDATED_IS_WRITE);
        assertThat(testAuthorityMenuItem.getIsDelete()).isEqualTo(UPDATED_IS_DELETE);
    }

    @Test
    @Transactional
    void patchNonExistingAuthorityMenuItem() throws Exception {
        int databaseSizeBeforeUpdate = authorityMenuItemRepository.findAll().size();
        authorityMenuItem.setId(longCount.incrementAndGet());

        // Create the AuthorityMenuItem
        AuthorityMenuItemDTO authorityMenuItemDTO = authorityMenuItemMapper.toDto(authorityMenuItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthorityMenuItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, authorityMenuItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(authorityMenuItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthorityMenuItem in the database
        List<AuthorityMenuItem> authorityMenuItemList = authorityMenuItemRepository.findAll();
        assertThat(authorityMenuItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAuthorityMenuItem() throws Exception {
        int databaseSizeBeforeUpdate = authorityMenuItemRepository.findAll().size();
        authorityMenuItem.setId(longCount.incrementAndGet());

        // Create the AuthorityMenuItem
        AuthorityMenuItemDTO authorityMenuItemDTO = authorityMenuItemMapper.toDto(authorityMenuItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorityMenuItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(authorityMenuItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthorityMenuItem in the database
        List<AuthorityMenuItem> authorityMenuItemList = authorityMenuItemRepository.findAll();
        assertThat(authorityMenuItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAuthorityMenuItem() throws Exception {
        int databaseSizeBeforeUpdate = authorityMenuItemRepository.findAll().size();
        authorityMenuItem.setId(longCount.incrementAndGet());

        // Create the AuthorityMenuItem
        AuthorityMenuItemDTO authorityMenuItemDTO = authorityMenuItemMapper.toDto(authorityMenuItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorityMenuItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(authorityMenuItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AuthorityMenuItem in the database
        List<AuthorityMenuItem> authorityMenuItemList = authorityMenuItemRepository.findAll();
        assertThat(authorityMenuItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAuthorityMenuItem() throws Exception {
        // Initialize the database
        authorityMenuItemRepository.saveAndFlush(authorityMenuItem);

        int databaseSizeBeforeDelete = authorityMenuItemRepository.findAll().size();

        // Delete the authorityMenuItem
        restAuthorityMenuItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, authorityMenuItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AuthorityMenuItem> authorityMenuItemList = authorityMenuItemRepository.findAll();
        assertThat(authorityMenuItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
