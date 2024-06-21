package creatip.restaurant.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import creatip.restaurant.IntegrationTest;
import creatip.restaurant.domain.Authority;
import creatip.restaurant.repository.AuthorityRepository;
import creatip.restaurant.service.dto.AuthorityDTO;
import creatip.restaurant.service.mapper.AuthorityMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AuthorityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AuthorityResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/authorities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{name}";

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private AuthorityMapper authorityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAuthorityMockMvc;

    private Authority authority;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Authority createEntity(EntityManager em) {
        Authority authority = new Authority().description(DEFAULT_DESCRIPTION);
        return authority;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Authority createUpdatedEntity(EntityManager em) {
        Authority authority = new Authority().description(UPDATED_DESCRIPTION);
        return authority;
    }

    @BeforeEach
    public void initTest() {
        authority = createEntity(em);
    }

    @Test
    @Transactional
    void createAuthority() throws Exception {
        int databaseSizeBeforeCreate = authorityRepository.findAll().size();
        // Create the Authority
        AuthorityDTO authorityDTO = authorityMapper.toDto(authority);
        restAuthorityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(authorityDTO)))
            .andExpect(status().isCreated());

        // Validate the Authority in the database
        List<Authority> authorityList = authorityRepository.findAll();
        assertThat(authorityList).hasSize(databaseSizeBeforeCreate + 1);
        Authority testAuthority = authorityList.get(authorityList.size() - 1);
        assertThat(testAuthority.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createAuthorityWithExistingId() throws Exception {
        // Create the Authority with an existing ID
        authority.setName("existing_id");
        AuthorityDTO authorityDTO = authorityMapper.toDto(authority);

        int databaseSizeBeforeCreate = authorityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuthorityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(authorityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Authority in the database
        List<Authority> authorityList = authorityRepository.findAll();
        assertThat(authorityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = authorityRepository.findAll().size();
        // set the field null
        authority.setDescription(null);

        // Create the Authority, which fails.
        AuthorityDTO authorityDTO = authorityMapper.toDto(authority);

        restAuthorityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(authorityDTO)))
            .andExpect(status().isBadRequest());

        List<Authority> authorityList = authorityRepository.findAll();
        assertThat(authorityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAuthorities() throws Exception {
        // Initialize the database
        authority.setName(UUID.randomUUID().toString());
        authorityRepository.saveAndFlush(authority);

        // Get all the authorityList
        restAuthorityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=name,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].name").value(hasItem(authority.getName())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getAuthority() throws Exception {
        // Initialize the database
        authority.setName(UUID.randomUUID().toString());
        authorityRepository.saveAndFlush(authority);

        // Get the authority
        restAuthorityMockMvc
            .perform(get(ENTITY_API_URL_ID, authority.getName()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.name").value(authority.getName()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingAuthority() throws Exception {
        // Get the authority
        restAuthorityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAuthority() throws Exception {
        // Initialize the database
        authority.setName(UUID.randomUUID().toString());
        authorityRepository.saveAndFlush(authority);

        int databaseSizeBeforeUpdate = authorityRepository.findAll().size();

        // Update the authority
        Authority updatedAuthority = authorityRepository.findById(authority.getName()).orElseThrow();
        // Disconnect from session so that the updates on updatedAuthority are not directly saved in db
        em.detach(updatedAuthority);
        updatedAuthority.description(UPDATED_DESCRIPTION);
        AuthorityDTO authorityDTO = authorityMapper.toDto(updatedAuthority);

        restAuthorityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, authorityDTO.getName())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorityDTO))
            )
            .andExpect(status().isOk());

        // Validate the Authority in the database
        List<Authority> authorityList = authorityRepository.findAll();
        assertThat(authorityList).hasSize(databaseSizeBeforeUpdate);
        Authority testAuthority = authorityList.get(authorityList.size() - 1);
        assertThat(testAuthority.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingAuthority() throws Exception {
        int databaseSizeBeforeUpdate = authorityRepository.findAll().size();
        authority.setName(UUID.randomUUID().toString());

        // Create the Authority
        AuthorityDTO authorityDTO = authorityMapper.toDto(authority);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthorityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, authorityDTO.getName())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Authority in the database
        List<Authority> authorityList = authorityRepository.findAll();
        assertThat(authorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAuthority() throws Exception {
        int databaseSizeBeforeUpdate = authorityRepository.findAll().size();
        authority.setName(UUID.randomUUID().toString());

        // Create the Authority
        AuthorityDTO authorityDTO = authorityMapper.toDto(authority);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Authority in the database
        List<Authority> authorityList = authorityRepository.findAll();
        assertThat(authorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAuthority() throws Exception {
        int databaseSizeBeforeUpdate = authorityRepository.findAll().size();
        authority.setName(UUID.randomUUID().toString());

        // Create the Authority
        AuthorityDTO authorityDTO = authorityMapper.toDto(authority);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(authorityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Authority in the database
        List<Authority> authorityList = authorityRepository.findAll();
        assertThat(authorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAuthorityWithPatch() throws Exception {
        // Initialize the database
        authority.setName(UUID.randomUUID().toString());
        authorityRepository.saveAndFlush(authority);

        int databaseSizeBeforeUpdate = authorityRepository.findAll().size();

        // Update the authority using partial update
        Authority partialUpdatedAuthority = new Authority();
        partialUpdatedAuthority.setName(authority.getName());

        restAuthorityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuthority.getName())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuthority))
            )
            .andExpect(status().isOk());

        // Validate the Authority in the database
        List<Authority> authorityList = authorityRepository.findAll();
        assertThat(authorityList).hasSize(databaseSizeBeforeUpdate);
        Authority testAuthority = authorityList.get(authorityList.size() - 1);
        assertThat(testAuthority.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateAuthorityWithPatch() throws Exception {
        // Initialize the database
        authority.setName(UUID.randomUUID().toString());
        authorityRepository.saveAndFlush(authority);

        int databaseSizeBeforeUpdate = authorityRepository.findAll().size();

        // Update the authority using partial update
        Authority partialUpdatedAuthority = new Authority();
        partialUpdatedAuthority.setName(authority.getName());

        partialUpdatedAuthority.description(UPDATED_DESCRIPTION);

        restAuthorityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuthority.getName())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuthority))
            )
            .andExpect(status().isOk());

        // Validate the Authority in the database
        List<Authority> authorityList = authorityRepository.findAll();
        assertThat(authorityList).hasSize(databaseSizeBeforeUpdate);
        Authority testAuthority = authorityList.get(authorityList.size() - 1);
        assertThat(testAuthority.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingAuthority() throws Exception {
        int databaseSizeBeforeUpdate = authorityRepository.findAll().size();
        authority.setName(UUID.randomUUID().toString());

        // Create the Authority
        AuthorityDTO authorityDTO = authorityMapper.toDto(authority);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthorityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, authorityDTO.getName())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(authorityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Authority in the database
        List<Authority> authorityList = authorityRepository.findAll();
        assertThat(authorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAuthority() throws Exception {
        int databaseSizeBeforeUpdate = authorityRepository.findAll().size();
        authority.setName(UUID.randomUUID().toString());

        // Create the Authority
        AuthorityDTO authorityDTO = authorityMapper.toDto(authority);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(authorityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Authority in the database
        List<Authority> authorityList = authorityRepository.findAll();
        assertThat(authorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAuthority() throws Exception {
        int databaseSizeBeforeUpdate = authorityRepository.findAll().size();
        authority.setName(UUID.randomUUID().toString());

        // Create the Authority
        AuthorityDTO authorityDTO = authorityMapper.toDto(authority);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorityMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(authorityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Authority in the database
        List<Authority> authorityList = authorityRepository.findAll();
        assertThat(authorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAuthority() throws Exception {
        // Initialize the database
        authority.setName(UUID.randomUUID().toString());
        authorityRepository.saveAndFlush(authority);

        int databaseSizeBeforeDelete = authorityRepository.findAll().size();

        // Delete the authority
        restAuthorityMockMvc
            .perform(delete(ENTITY_API_URL_ID, authority.getName()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Authority> authorityList = authorityRepository.findAll();
        assertThat(authorityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
