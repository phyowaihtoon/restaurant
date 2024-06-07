package creatip.restaurant.service.impl;

import creatip.restaurant.domain.DiningTable;
import creatip.restaurant.repository.DiningTableRepository;
import creatip.restaurant.service.DiningTableService;
import creatip.restaurant.service.dto.DiningTableDTO;
import creatip.restaurant.service.mapper.DiningTableMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link creatip.restaurant.domain.DiningTable}.
 */
@Service
@Transactional
public class DiningTableServiceImpl implements DiningTableService {

    private final Logger log = LoggerFactory.getLogger(DiningTableServiceImpl.class);

    private final DiningTableRepository diningTableRepository;

    private final DiningTableMapper diningTableMapper;

    public DiningTableServiceImpl(DiningTableRepository diningTableRepository, DiningTableMapper diningTableMapper) {
        this.diningTableRepository = diningTableRepository;
        this.diningTableMapper = diningTableMapper;
    }

    @Override
    public DiningTableDTO save(DiningTableDTO diningTableDTO) {
        log.debug("Request to save DiningTable : {}", diningTableDTO);
        DiningTable diningTable = diningTableMapper.toEntity(diningTableDTO);
        diningTable = diningTableRepository.save(diningTable);
        return diningTableMapper.toDto(diningTable);
    }

    @Override
    public DiningTableDTO update(DiningTableDTO diningTableDTO) {
        log.debug("Request to update DiningTable : {}", diningTableDTO);
        DiningTable diningTable = diningTableMapper.toEntity(diningTableDTO);
        diningTable = diningTableRepository.save(diningTable);
        return diningTableMapper.toDto(diningTable);
    }

    @Override
    public Optional<DiningTableDTO> partialUpdate(DiningTableDTO diningTableDTO) {
        log.debug("Request to partially update DiningTable : {}", diningTableDTO);

        return diningTableRepository
            .findById(diningTableDTO.getId())
            .map(existingDiningTable -> {
                diningTableMapper.partialUpdate(existingDiningTable, diningTableDTO);

                return existingDiningTable;
            })
            .map(diningTableRepository::save)
            .map(diningTableMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DiningTableDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DiningTables");
        return diningTableRepository.findAll(pageable).map(diningTableMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DiningTableDTO> findOne(Long id) {
        log.debug("Request to get DiningTable : {}", id);
        return diningTableRepository.findById(id).map(diningTableMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DiningTable : {}", id);
        diningTableRepository.deleteById(id);
    }
}
