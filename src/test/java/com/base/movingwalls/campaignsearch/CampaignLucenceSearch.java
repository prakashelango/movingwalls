package com.base.movingwalls.campaignsearch;

import com.base.movingwalls.model.campaign.Campaign;
import com.base.movingwalls.model.campaign.CampaignFilter;
import com.base.movingwalls.repository.CampaignRepositoryEntityManagedImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CampaignLucenceSearch {

    @Autowired(required = true)
    private CampaignRepositoryEntityManagedImpl campaignRepository;

    private List<Campaign> campaigns;

    @Before
    public void setupTestData() {

        campaigns = campaignRepository.dummyCampaignData();
    }


    @Test
    public void testA_whenInitialTestDataInserted_thenSuccess() {
        campaignRepository.createDummyCampaign();

    }

    @Test
    public void testB_whenIndexInitialized_thenCorrectIndexSize() throws InterruptedException {

        int indexSize = campaignRepository.initIndex();

        assertEquals(campaigns.size() - 1, indexSize);
    }

    private CampaignFilter CreateCampaignTestFilter() {
        CampaignFilter filter = new CampaignFilter();
        filter.setSortby("name" );
        filter.setSortbyOrder("asc" );
        return filter;
    }

    @Test
    public void testE_whenKeywordSearchOnName_thenCorrectMatches() {
        List<Campaign> expected = campaigns;
        List<Campaign> results = campaignRepository.searchByCampaignData(CreateCampaignTestFilter());

        assertThat(results, containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void testE_PullAllRecords_thenMatches_with_Index() {
        List<Campaign> expected = campaigns;
        List<Campaign> results = campaignRepository.fetchAllCampaignData(CreateCampaignTestFilter());

        assertThat(results, containsInAnyOrder(expected.toArray()));
    }

}
