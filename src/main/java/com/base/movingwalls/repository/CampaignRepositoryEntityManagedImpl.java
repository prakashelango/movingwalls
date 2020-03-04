package com.base.movingwalls.repository;

import com.base.movingwalls.model.campaign.Campaign;
import com.base.movingwalls.model.campaign.CampaignFilter;
import org.apache.log4j.Logger;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CampaignRepositoryEntityManagedImpl implements CampaignRepositoryEntityManaged {

    private static final Logger LOGGER = Logger.getLogger(CampaignRepositoryEntityManagedImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public void refresh(Campaign campaign) {
        em.refresh(campaign);
    }

    public List<Campaign> dummyCampaignData() {

        return Arrays.asList(
                new Campaign("UA Sports Singapore", null, "Published", LocalDateTime.now().minusMonths(2), LocalDateTime.now().minusMonths(1), "Download Report"),
                new Campaign("Nespresso", null, "Processing", LocalDateTime.now().minusDays(25), LocalDateTime.now().minusDays(10), "Available in 2 Days"),
                new Campaign("DBS - 3 Bill Boards", null, "Processing", LocalDateTime.now().minusMonths(4), LocalDateTime.now().minusMonths(2), "Available in 4 Days"),
                new Campaign("Focus Media", null, "Ongoing", LocalDateTime.now().plusMonths(1), LocalDateTime.now().plusMonths(2), "Ending in 23 Days"),
                new Campaign("Rendezvous - Bill Boards", null, "Archived", LocalDateTime.now(), LocalDateTime.now().plusDays(20), "Download Report"),
                new Campaign("Orchard - 3 Bill Boards", null, "Ongoing", LocalDateTime.now().minusMonths(4), LocalDateTime.now().minusMonths(2), "Ending in 23 Days"),
                new Campaign("Nespresso - 3 Bill Boards", null, "Ongoing", LocalDateTime.now().plusMonths(4), LocalDateTime.now().plusMonths(5), "Ending in 23 Days"),
                new Campaign("Bizzare ", null, "Ongoing", LocalDateTime.now().minusMonths(4), LocalDateTime.now().minusMonths(2), "Ending in 23 Days"),
                new Campaign("EA - Sports", null, "Archived", LocalDateTime.now().plusMonths(1), LocalDateTime.now().plusMonths(2), "Download Report"),
                new Campaign("UA Sports Singapore", null, "Ongoing", LocalDateTime.now().minusMonths(4), LocalDateTime.now().minusMonths(2), "Ending in 23 Days"),
                new Campaign("Bill Boards NewYork", null, "Processing", LocalDateTime.now().plusMonths(1), LocalDateTime.now().plusMonths(2), "Available in 6 Days"),
                new Campaign("Hacker Boards", null, "Ongoing", LocalDateTime.now().plusMonths(4), LocalDateTime.now().plusMonths(5), "Ending in 23 Days"),
                new Campaign("Campaign Data", null, "Published", LocalDateTime.now().minusMonths(4), LocalDateTime.now().minusMonths(2), "Download Report"),
                new Campaign("Mars Mission", null, "Processing", LocalDateTime.now().plusMonths(1), LocalDateTime.now().plusMonths(2), "Available in 23 Days"),
                new Campaign("Emperor Boards", null, "Ongoing", LocalDateTime.now().minusMonths(4), LocalDateTime.now().minusMonths(2), "Ending in 23 Days"),
                new Campaign("YRS Bill Boards", null, "Processing", LocalDateTime.now().plusMonths(1), LocalDateTime.now().plusMonths(2), "Available in 23 Days"),
                new Campaign("Zoom Network", null, "Ongoing", LocalDateTime.now().minusMonths(4), LocalDateTime.now().minusMonths(2), "Ending in 23 Days"),
                new Campaign("Mission Campaign", null, "Archived", LocalDateTime.now().minusMonths(4), LocalDateTime.now().minusMonths(2), "Download Report"),
                new Campaign("Hall Of Wars", null, "Ongoing", LocalDateTime.now().minusMonths(4), LocalDateTime.now().minusMonths(2), "Ending in 23 Days"),
                new Campaign("Reactive Push", null, "Published", LocalDateTime.now().minusMonths(4), LocalDateTime.now().minusMonths(2), "Download Reports"),
                new Campaign("Mines - 1 Bill Boards", null, "Ongoing", LocalDateTime.now().plusMonths(4), LocalDateTime.now().plusMonths(5), "Ending in 23 Days"),
                new Campaign("Knowledge Entertainment", null, "Archived", LocalDateTime.now().plusMonths(1), LocalDateTime.now().plusMonths(2), "Dowload Report"),
                new Campaign("Campaign Critical", null, "Ongoing", LocalDateTime.now().minusMonths(4), LocalDateTime.now().minusMonths(2), "Ending in 23 Days"),
                new Campaign("Orchard - 3 Bill Boards", null, "Published", LocalDateTime.now().plusMonths(4), LocalDateTime.now().plusMonths(5), "Download Report"),
                new Campaign("Hall of Fame", null, "Ongoing", LocalDateTime.now().minusMonths(4), LocalDateTime.now().minusMonths(2), "Ending in 23 Days"),
                new Campaign("Nespresso", null, "Processing", LocalDateTime.now().plusMonths(1), LocalDateTime.now().plusMonths(2), "Available in 23 Days"),
                new Campaign("UA Sports", null, "Ongoing", LocalDateTime.now().plusMonths(1), LocalDateTime.now().plusMonths(2), "Ending in 23 Days"),
                new Campaign("Data Campaign", null, "Published", LocalDateTime.now().minusMonths(3), LocalDateTime.now().minusMonths(1), "Download Report"),
                new Campaign("Orchard Campaign", null, "Ongoing", LocalDateTime.now().minusMonths(9), LocalDateTime.now().minusMonths(7), "Ending in 23 Days"),
                new Campaign("Horse Force", null, "Processing", LocalDateTime.now().plusMonths(4), LocalDateTime.now().plusMonths(2), "Available in 23 Days"),
                new Campaign("Electrical Campaign", null, "Ongoing", LocalDateTime.now().minusMonths(4), LocalDateTime.now().minusMonths(2), "Ending in 23 Days"));
    }

    public void createDummyCampaign() {
        dummyCampaignData().stream().forEach(campaign -> {
            em.persist(campaign);
            em.flush();
        });
    }

    public int createIndex() throws InterruptedException {

        final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
        fullTextEntityManager.createIndexer()
                .startAndWait();
        fullTextEntityManager.flushToIndexes();
        int indexSize = fullTextEntityManager.getSearchFactory()
                .getStatistics()
                .getNumberOfIndexedEntities(Campaign.class.getName());
        LOGGER.info("Records Indexed"+indexSize);
        return indexSize;
    }

    private QueryBuilder getQueryBuilder() {

        final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);

        return fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Campaign.class)
                .get();
    }

    public List<Campaign> searchByCampaignData(final CampaignFilter campaignFilter) {
        Optional<List<Campaign>> cam = Optional.ofNullable(campaignFilter.getCampaignStatus())
                .filter(status -> status.length()>0 && !status.isEmpty())
                .map(status -> getQueryBuilder()
                        .keyword()
                        .onFields("status", campaignFilter.getSortby())
                        .matching(status)
                        .createQuery())
                .map(statusQuery -> getJpaQuery(statusQuery, campaignFilter).getResultList());
        System.out.println(cam);

        return Optional.of(campaignFilter.getCampaignSearchKeyWord())
                .map(searchKey -> getQueryBuilder()
                        .keyword()
                        .onFields("name", "duration", "status", "report")
                        .matching(searchKey)
                        .createQuery())
                .map(keywordQuery -> getJpaQuery(keywordQuery, campaignFilter).getResultList())
                .orElseGet(() -> getJpaQuery(getQueryBuilder().all().createQuery(), campaignFilter).getResultList());
    }

    private FullTextQuery getJpaQuery(final Query luceneQuery, final CampaignFilter campaignFilter) {
        return Search.getFullTextEntityManager(em).createFullTextQuery(luceneQuery, Campaign.class)
                .setSort(new Sorter().getSortbyType(campaignFilter.getSortFieldType(),
                        campaignFilter.getSortby(), campaignFilter.getSortbyOrder()))
                .setFirstResult(campaignFilter.getStartPage())
                .setMaxResults(campaignFilter.getTotalPages());
    }
}

class Sorter {
    public Sort getSortbyType(String fieldType, String sortField, String sortOrder) {
        switch (fieldType) {
            case "Number":
                return new Sort(SortField.FIELD_SCORE, new SortField(sortField, SortField.Type.LONG, !sortOrder.equals("asc")));
            case "Date":
                return new Sort(SortField.FIELD_SCORE, new SortField(sortField, SortField.Type.CUSTOM, !sortOrder.equals("asc")));
            default:
                return new Sort(SortField.FIELD_SCORE, new SortField(sortField, SortField.Type.STRING, !sortOrder.equals("asc")));
        }
    }


}