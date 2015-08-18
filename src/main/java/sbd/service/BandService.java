package sbd.service;

import org.springframework.data.domain.Page;
import sbd.domain.Band;
import sdb.dto.BandDTO;

/**
 * Created by benoit on 18/08/15.
 */
public interface BandService {
    Band create(BandDTO Band);

    Band update(BandDTO Band);

    Band get(Long id);

    Band delete(Long id);

    Page<Band> findAll(Integer pageNumber, Integer pageSize);
}
