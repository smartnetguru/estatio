package org.estatio.dom.geography;

import javax.jdo.annotations.DiscriminatorStrategy;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Title;

import org.estatio.dom.EstatioRefDataObject;
import org.estatio.dom.WithReference;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Discriminator(strategy = DiscriminatorStrategy.CLASS_NAME)
public abstract class Geography extends EstatioRefDataObject implements WithReference<Geography> {

    private String reference;

    /**
     * As per ISO standards for <a href="http://www.commondatahub.com/live/geography/country/iso_3166_country_codes">countries</a> and
     * <a href="http://www.commondatahub.com/live/geography/state_province_region/iso_3166_2_state_codes">states</a>.
     */
    @MemberOrder(sequence = "1")
    public String getReference() {
        return reference;
    }

    public void setReference(final String reference) {
        this.reference = reference;
    }

    
    
    private String name;

    @Title
    @MemberOrder(sequence = "2")
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    

    // {{ Comparable impl
    @Override
    public int compareTo(Geography other) {
        return ORDERING_BY_REFERENCE.compare(this, other);
    }
    // }}
    
}
