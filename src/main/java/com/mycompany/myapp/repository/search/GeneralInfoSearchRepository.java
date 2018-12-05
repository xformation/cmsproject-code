package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.GeneralInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the GeneralInfo entity.
 */
public interface GeneralInfoSearchRepository extends ElasticsearchRepository<GeneralInfo, Long> {
}
