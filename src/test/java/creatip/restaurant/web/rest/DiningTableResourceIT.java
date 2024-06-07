package creatip.restaurant.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import creatip.restaurant.IntegrationTest;
import creatip.restaurant.domain.DiningTable;
import creatip.restaurant.repository.DiningTableRepository;
import creatip.restaurant.service.dto.DiningTableDTO;
import creatip.restaurant.service.mapper.DiningTableMapper;
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
 * Integration tests for the {@link DiningTableResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DiningTableResourceIT {

    private static final Integer DEFAULT_TABLE_NUMBER = 1;
    private static final Integer UPDATED_TABLE_NUMBER = 2;

    private static final Integer DEFAULT_SEATING_CAPACITY = 1;
    private static final Integer UPDATED_SEATING_CAPACITY = 2;

    private static final Integer DEFAULT_TABLE_STATUS = 1;
    private static final Integer UPDATED_TABLE_STATUS = 2;

    private static final String ENTITY_API_URL = "/api/dining-tables";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DiningTableRepository diningTableRepository;

    @Autowired
    private DiningTableMapper diningTableMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiningTableMockMvc;

    private DiningTable diningTable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiningTable createEntity(EntityManager em) {
        DiningTable diningTable = new DiningTable()
            .tableNumber(DEFAULT_TABLE_NUMBER)
            .seatingCapacity(DEFAULT_SEATING_CAPACITY)
            .tableStatus(DEFAULT_TABLE_STATUS);
        return diningTable;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiningTable createUpdatedEntity(EntityManager em) {
        DiningTable diningTable = new DiningTable()
            .tableNumber(UPDATED_TABLE_NUMBER)
            .seatingCapacity(UPDATED_SEATING_CAPACITY)
            .tableStatus(UPDATED_TABLE_STATUS);
        return diningTable;
    }

    @BeforeEach
    public void initTest() {
        diningTable = createEntity(em);
    }

    @Test
    @Transactional
    void createDiningTable() throws Exception {
        int databaseSizeBeforeCreate = diningTableRepository.findAll().size();
        // Create the DiningTable
        DiningTableDTO diningTableDTO = diningTableMapper.toDto(diningTable);
        restDiningTableMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diningTableDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DiningTable in the database
        List<DiningTable> diningTableList = diningTableRepository.findAll();
        assertThat(diningTableList).hasSize(databaseSizeBeforeCreate + 1);
        DiningTable testDiningTable = diningTableList.get(diningTableList.size() - 1);
        assertThat(testDiningTable.getTableNumber()).isEqualTo(DEFAULT_TABLE_NUMBER);
        assertThat(testDiningTable.getSeatingCapacity()).isEqualTo(DEFAULT_SEATING_CAPACITY);
        assertThat(testDiningTable.getTableStatus()).isEqualTo(DEFAULT_TABLE_STATUS);
    }

    @Test
    @Transactional
    void createDiningTableWithExistingId() throws Exception {
        // Create the DiningTable with an existing ID
        diningTable.setId(1L);
        DiningTableDTO diningTableDTO = diningTableMapper.toDto(diningTable);

        int databaseSizeBeforeCreate = diningTableRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiningTableMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diningTableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiningTable in the database
        List<DiningTable> diningTableList = diningTableRepository.findAll();
        assertThat(diningTableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTableNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = diningTableRepository.findAll().size();
        // set the field null
        diningTable.setTableNumber(null);

        // Create the DiningTable, which fails.
        DiningTableDTO diningTableDTO = diningTableMapper.toDto(diningTable);

        restDiningTableMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diningTableDTO))
            )
            .andExpect(status().isBadRequest());

        List<DiningTable> diningTableList = diningTableRepository.findAll();
        assertThat(diningTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSeatingCapacityIsRequired() throws Exception {
        int databaseSizeBeforeTest = diningTableRepository.findAll().size();
        // set the field null
        diningTable.setSeatingCapacity(null);

        // Create the DiningTable, which fails.
        DiningTableDTO diningTableDTO = diningTableMapper.toDto(diningTable);

        restDiningTableMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diningTableDTO))
            )
            .andExpect(status().isBadRequest());

        List<DiningTable> diningTableList = diningTableRepository.findAll();
        assertThat(diningTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTableStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = diningTableRepository.findAll().size();
        // set the field null
        diningTable.setTableStatus(null);

        // Create the DiningTable, which fails.
        DiningTableDTO diningTableDTO = diningTableMapper.toDto(diningTable);

        restDiningTableMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diningTableDTO))
            )
            .andExpect(status().isBadRequest());

        List<DiningTable> diningTableList = diningTableRepository.findAll();
        assertThat(diningTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDiningTables() throws Exception {
        // Initialize the database
        diningTableRepository.saveAndFlush(diningTable);

        // Get all the diningTableList
        restDiningTableMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diningTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].tableNumber").value(hasItem(DEFAULT_TABLE_NUMBER)))
            .andExpect(jsonPath("$.[*].seatingCapacity").value(hasItem(DEFAULT_SEATING_CAPACITY)))
            .andExpect(jsonPath("$.[*].tableStatus").value(hasItem(DEFAULT_TABLE_STATUS)));
    }

    @Test
    @Transactional
    void getDiningTable() throws Exception {
        // Initialize the database
        diningTableRepository.saveAndFlush(diningTable);

        // Get the diningTable
        restDiningTableMockMvc
            .perform(get(ENTITY_API_URL_ID, diningTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(diningTable.getId().intValue()))
            .andExpect(jsonPath("$.tableNumber").value(DEFAULT_TABLE_NUMBER))
            .andExpect(jsonPath("$.seatingCapacity").value(DEFAULT_SEATING_CAPACITY))
            .andExpect(jsonPath("$.tableStatus").value(DEFAULT_TABLE_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingDiningTable() throws Exception {
        // Get the diningTable
        restDiningTableMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDiningTable() throws Exception {
        // Initialize the database
        diningTableRepository.saveAndFlush(diningTable);

        int databaseSizeBeforeUpdate = diningTableRepository.findAll().size();

        // Update the diningTable
        DiningTable updatedDiningTable = diningTableRepository.findById(diningTable.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDiningTable are not directly saved in db
        em.detach(updatedDiningTable);
        updatedDiningTable.tableNumber(UPDATED_TABLE_NUMBER).seatingCapacity(UPDATED_SEATING_CAPACITY).tableStatus(UPDATED_TABLE_STATUS);
        DiningTableDTO diningTableDTO = diningTableMapper.toDto(updatedDiningTable);

        restDiningTableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, diningTableDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(diningTableDTO))
            )
            .andExpect(status().isOk());

        // Validate the DiningTable in the database
        List<DiningTable> diningTableList = diningTableRepository.findAll();
        assertThat(diningTableList).hasSize(databaseSizeBeforeUpdate);
        DiningTable testDiningTable = diningTableList.get(diningTableList.size() - 1);
        assertThat(testDiningTable.getTableNumber()).isEqualTo(UPDATED_TABLE_NUMBER);
        assertThat(testDiningTable.getSeatingCapacity()).isEqualTo(UPDATED_SEATING_CAPACITY);
        assertThat(testDiningTable.getTableStatus()).isEqualTo(UPDATED_TABLE_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingDiningTable() throws Exception {
        int databaseSizeBeforeUpdate = diningTableRepository.findAll().size();
        diningTable.setId(longCount.incrementAndGet());

        // Create the DiningTable
        DiningTableDTO diningTableDTO = diningTableMapper.toDto(diningTable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiningTableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, diningTableDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(diningTableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiningTable in the database
        List<DiningTable> diningTableList = diningTableRepository.findAll();
        assertThat(diningTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDiningTable() throws Exception {
        int databaseSizeBeforeUpdate = diningTableRepository.findAll().size();
        diningTable.setId(longCount.incrementAndGet());

        // Create the DiningTable
        DiningTableDTO diningTableDTO = diningTableMapper.toDto(diningTable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiningTableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(diningTableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiningTable in the database
        List<DiningTable> diningTableList = diningTableRepository.findAll();
        assertThat(diningTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDiningTable() throws Exception {
        int databaseSizeBeforeUpdate = diningTableRepository.findAll().size();
        diningTable.setId(longCount.incrementAndGet());

        // Create the DiningTable
        DiningTableDTO diningTableDTO = diningTableMapper.toDto(diningTable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiningTableMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diningTableDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DiningTable in the database
        List<DiningTable> diningTableList = diningTableRepository.findAll();
        assertThat(diningTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDiningTableWithPatch() throws Exception {
        // Initialize the database
        diningTableRepository.saveAndFlush(diningTable);

        int databaseSizeBeforeUpdate = diningTableRepository.findAll().size();

        // Update the diningTable using partial update
        DiningTable partialUpdatedDiningTable = new DiningTable();
        partialUpdatedDiningTable.setId(diningTable.getId());

        partialUpdatedDiningTable.tableNumber(UPDATED_TABLE_NUMBER).seatingCapacity(UPDATED_SEATING_CAPACITY);

        restDiningTableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiningTable.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDiningTable))
            )
            .andExpect(status().isOk());

        // Validate the DiningTable in the database
        List<DiningTable> diningTableList = diningTableRepository.findAll();
        assertThat(diningTableList).hasSize(databaseSizeBeforeUpdate);
        DiningTable testDiningTable = diningTableList.get(diningTableList.size() - 1);
        assertThat(testDiningTable.getTableNumber()).isEqualTo(UPDATED_TABLE_NUMBER);
        assertThat(testDiningTable.getSeatingCapacity()).isEqualTo(UPDATED_SEATING_CAPACITY);
        assertThat(testDiningTable.getTableStatus()).isEqualTo(DEFAULT_TABLE_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateDiningTableWithPatch() throws Exception {
        // Initialize the database
        diningTableRepository.saveAndFlush(diningTable);

        int databaseSizeBeforeUpdate = diningTableRepository.findAll().size();

        // Update the diningTable using partial update
        DiningTable partialUpdatedDiningTable = new DiningTable();
        partialUpdatedDiningTable.setId(diningTable.getId());

        partialUpdatedDiningTable
            .tableNumber(UPDATED_TABLE_NUMBER)
            .seatingCapacity(UPDATED_SEATING_CAPACITY)
            .tableStatus(UPDATED_TABLE_STATUS);

        restDiningTableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiningTable.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDiningTable))
            )
            .andExpect(status().isOk());

        // Validate the DiningTable in the database
        List<DiningTable> diningTableList = diningTableRepository.findAll();
        assertThat(diningTableList).hasSize(databaseSizeBeforeUpdate);
        DiningTable testDiningTable = diningTableList.get(diningTableList.size() - 1);
        assertThat(testDiningTable.getTableNumber()).isEqualTo(UPDATED_TABLE_NUMBER);
        assertThat(testDiningTable.getSeatingCapacity()).isEqualTo(UPDATED_SEATING_CAPACITY);
        assertThat(testDiningTable.getTableStatus()).isEqualTo(UPDATED_TABLE_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingDiningTable() throws Exception {
        int databaseSizeBeforeUpdate = diningTableRepository.findAll().size();
        diningTable.setId(longCount.incrementAndGet());

        // Create the DiningTable
        DiningTableDTO diningTableDTO = diningTableMapper.toDto(diningTable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiningTableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, diningTableDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(diningTableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiningTable in the database
        List<DiningTable> diningTableList = diningTableRepository.findAll();
        assertThat(diningTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDiningTable() throws Exception {
        int databaseSizeBeforeUpdate = diningTableRepository.findAll().size();
        diningTable.setId(longCount.incrementAndGet());

        // Create the DiningTable
        DiningTableDTO diningTableDTO = diningTableMapper.toDto(diningTable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiningTableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(diningTableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiningTable in the database
        List<DiningTable> diningTableList = diningTableRepository.findAll();
        assertThat(diningTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDiningTable() throws Exception {
        int databaseSizeBeforeUpdate = diningTableRepository.findAll().size();
        diningTable.setId(longCount.incrementAndGet());

        // Create the DiningTable
        DiningTableDTO diningTableDTO = diningTableMapper.toDto(diningTable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiningTableMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(diningTableDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DiningTable in the database
        List<DiningTable> diningTableList = diningTableRepository.findAll();
        assertThat(diningTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDiningTable() throws Exception {
        // Initialize the database
        diningTableRepository.saveAndFlush(diningTable);

        int databaseSizeBeforeDelete = diningTableRepository.findAll().size();

        // Delete the diningTable
        restDiningTableMockMvc
            .perform(delete(ENTITY_API_URL_ID, diningTable.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DiningTable> diningTableList = diningTableRepository.findAll();
        assertThat(diningTableList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
