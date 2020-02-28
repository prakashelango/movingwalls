package com.base.movingwalls.campaignsearch;

import com.base.movingwalls.model.campaign.Campaign;
import com.base.movingwalls.repository.CampaignRepositoryEntityManaged;
import com.base.movingwalls.service.impl.dao.CampaignDao;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CampaignLucenceSearch {

    @Autowired
    private CampaignRepositoryEntityManaged campaignRepository;

    @Autowired
    private CampaignDao dao;

    @PersistenceContext
    private EntityManager entityManager;

    private List<Campaign> campaigns;

    @Before
    public void setupTestData() {

        campaigns = Arrays.asList(new Campaign("UA Sports Singapore", "01 APR - 31 APR 2019", "Published", LocalDateTime.now(), LocalDateTime.now(), "Download"),
                new Campaign("Req Singapore", "01 APR - 31 APR 2019", "Published", LocalDateTime.now(), LocalDateTime.now(), "Download"),
                new Campaign("Billionaire Singapore", "01 APR - 31 APR 2019", "Published", LocalDateTime.now(), LocalDateTime.now(), "Download"),
                new Campaign("Bizarre Singapore", "01 APR - 31 APR 2019", "Published", LocalDateTime.now(), LocalDateTime.now(), "Download"),
                new Campaign("KA sports Singapore", "01 APR - 31 APR 2019", "Published", LocalDateTime.now(), LocalDateTime.now(), "Download"),
                new Campaign("BZ Million Singapore", "01 APR - 31 APR 2019", "Published", LocalDateTime.now(), LocalDateTime.now(), "Download"));
    }

    @Commit
    @Test
    public void testA_whenInitialTestDataInserted_thenSuccess() {

        for (int i = 0; i < campaigns.size() - 1; i++) {
            entityManager.persist(campaigns.get(i));
        }
    }

    @Test
    public void testB_whenIndexInitialized_thenCorrectIndexSize() throws InterruptedException {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        fullTextEntityManager.createIndexer()
                .startAndWait();
        int indexSize = fullTextEntityManager.getSearchFactory()
                .getStatistics()
                .getNumberOfIndexedEntities(Campaign.class.getName());

        assertEquals(campaigns.size() - 1, indexSize);
    }

    @Commit
    @Test
    public void testC_whenAdditionalTestDataInserted_thenSuccess() {

        entityManager.persist(campaigns.get(campaigns.size() - 1));
    }

    @Test
    public void testD_whenAdditionalTestDataInserted_thenIndexUpdatedAutomatically() {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        int indexSize = fullTextEntityManager.getSearchFactory()
                .getStatistics()
                .getNumberOfIndexedEntities(Campaign.class.getName());

        assertEquals(campaigns.size(), indexSize);
    }

    @Test
    public void testE_whenKeywordSearchOnName_thenCorrectMatches() {
        List<Campaign> expected = Arrays.asList(campaigns.get(0), campaigns.get(1), campaigns.get(2));
        List<Campaign> results = dao.searchProductNameByKeywordQuery("iphone");

        assertThat(results, containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void testF_whenFuzzySearch_thenCorrectMatches() {
        List<Campaign> expected = Arrays.asList(campaigns.get(0), campaigns.get(1), campaigns.get(2));
        List<Campaign> results = dao.searchProductNameByFuzzyQuery("iPhaen");

        assertThat(results, containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void testG_whenWildcardSearch_thenCorrectMatches() {
        List<Campaign> expected = Arrays.asList(campaigns.get(4), campaigns.get(5), campaigns.get(6));
        List<Campaign> results = dao.searchProductNameByWildcardQuery("6*");

        assertThat(results, containsInAnyOrder(expected.toArray()));

    }

    @Test
    public void testH_whenPhraseSearch_thenCorrectMatches() {
        List<Campaign> expected = Arrays.asList(campaigns.get(2));
        List<Campaign> results = dao.searchProductDescriptionByPhraseQuery("with wireless charging");

        assertThat(results, containsInAnyOrder(expected.toArray()));

    }

    @Test
    public void testI_whenSimpleQueryStringSearch_thenCorrectMatches() {
        List<Campaign> expected = Arrays.asList(campaigns.get(0), campaigns.get(1));
        List<Campaign> results = dao.searchProductNameAndDescriptionBySimpleQueryStringQuery("Aple~2 + \"iPhone X\" + (256 | 128)");

        assertThat(results, containsInAnyOrder(expected.toArray()));

    }

    @Test
    public void testJ_whenRangeSearch_thenCorrectMatches() {
        List<Campaign> expected = Arrays.asList(campaigns.get(0), campaigns.get(1), campaigns.get(2), campaigns.get(3));
        List<Campaign> results = dao.searchProductNameByRangeQuery(64, 256);

        assertThat(results, containsInAnyOrder(expected.toArray()));

    }

    @Test
    public void testK_whenMoreLikeThisSearch_thenCorrectMatchesInOrder() {
        List<Campaign> expected = campaigns;
        List<Object[]> resultsWithScore = dao.searchProductNameByMoreLikeThisQuery(campaigns.get(0));
        List<Campaign> results = new LinkedList<Campaign>();

        for (Object[] resultWithScore : resultsWithScore) {
            results.add((Campaign) resultWithScore[0]);
        }

        assertThat(results, contains(expected.toArray()));

    }

    @Test
    public void testL_whenKeywordSearchOnNameAndDescription_thenCorrectMatches() {
        List<Campaign> expected = Arrays.asList(campaigns.get(0), campaigns.get(1), campaigns.get(2));
        List<Campaign> results = dao.searchProductNameAndDescriptionByKeywordQuery("iphone");

        assertThat(results, containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void testM_whenMoreLikeThisSearchOnProductNameAndDescription_thenCorrectMatchesInOrder() {
        List<Campaign> expected = campaigns;
        List<Object[]> resultsWithScore = dao.searchProductNameAndDescriptionByMoreLikeThisQuery(campaigns.get(0));
        List<Campaign> results = new LinkedList<>();

        for (Object[] resultWithScore : resultsWithScore) {
            results.add((Campaign) resultWithScore[0]);
        }

        assertThat(results, contains(expected.toArray()));
    }

    @Test
    public void testN_whenCombinedSearch_thenCorrectMatches() {
        List<Campaign> expected = Arrays.asList(campaigns.get(1), campaigns.get(2));
        List<Campaign> results = dao.searchProductNameAndDescriptionByCombinedQuery("apple", 64, 128, "face id", "samsung");

        assertThat(results, containsInAnyOrder(expected.toArray()));
    }
}
