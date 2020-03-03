package com.base.movingwalls.service.impl.campaign;

import com.base.movingwalls.common.core.FunctionUtils;
import com.base.movingwalls.common.core.Promise;
import com.base.movingwalls.common.core.React;
import com.base.movingwalls.common.core.Reader;
import com.base.movingwalls.model.campaign.CampaignFilter;
import com.base.movingwalls.model.campaign.CampaignInfo;
import com.base.movingwalls.repository.CampaignRepositoryEntityManagedImpl;

import java.util.List;

public class CampaignFacade {

    /**
     * @param campaignFilter
     * @return
     */
    public static Reader<CampaignRepositoryEntityManagedImpl, Promise<List<CampaignInfo>>> searchByCampaignData(final CampaignFilter campaignFilter) {
        return Reader.of(managedImpl -> React.of(() -> campaignFilter)
                .then(impl -> managedImpl.searchByCampaignData(campaignFilter))
                .then(FunctionUtils.asList(CampaignConverter::convertToInfo))
                .getPromise());
    }

    /**
     * @return
     */
    public static Reader<CampaignRepositoryEntityManagedImpl, Promise<List<CampaignInfo>>> findAllCampaignData(final CampaignFilter campaignFilter) {
        return Reader.of(managedImpl -> React.of(() -> managedImpl.fetchAllCampaignData(campaignFilter))
                .then(FunctionUtils.asList(CampaignConverter::convertToInfo))
                .getPromise());
    }


}
