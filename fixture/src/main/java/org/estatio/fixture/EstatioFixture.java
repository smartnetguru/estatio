package org.estatio.fixture;

import java.util.Arrays;
import java.util.List;

import org.apache.isis.applib.fixtures.AbstractFixture;
import org.estatio.fixture.asset.PropertiesAndUnitsFixture;
import org.estatio.fixture.geography.GeographyFixture;
import org.estatio.fixture.index.IndexFixture;
import org.estatio.fixture.lease.LeasesFixture;
import org.estatio.fixture.party.PartiesFixture;
import org.estatio.fixture.tax.TaxFixture;


public class EstatioFixture extends AbstractFixture {

    public EstatioFixture() {
    }
    
    @Override
    public void install() {
        
        List<AbstractFixture> fixtures = Arrays.asList(
        getContainer().newTransientInstance(GeographyFixture.class),
        getContainer().newTransientInstance(IndexFixture.class),
        getContainer().newTransientInstance(PartiesFixture.class),
        getContainer().newTransientInstance(PropertiesAndUnitsFixture.class),
        getContainer().newTransientInstance(LeasesFixture.class),
        getContainer().newTransientInstance(TaxFixture.class)
        );
        
        for (AbstractFixture fixture : fixtures) {
            fixture.install(); 
            getContainer().flush();
        }
        
    }

}