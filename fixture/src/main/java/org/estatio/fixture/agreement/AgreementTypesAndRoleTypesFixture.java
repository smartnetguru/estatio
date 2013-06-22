package org.estatio.fixture.agreement;


import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.fixtures.AbstractFixture;

import org.estatio.dom.agreement.AgreementRoleType;
import org.estatio.dom.agreement.AgreementType;
import org.estatio.dom.agreement.AgreementTypes;
import org.estatio.dom.financial.BankMandate;
import org.estatio.dom.financial.FinancialConstants;
import org.estatio.dom.lease.LeaseConstants;

public class AgreementTypesAndRoleTypesFixture extends AbstractFixture {

    @Override
    public void install() {
        create(FinancialConstants.AT_MANDATE, FinancialConstants.ART_CREDITOR, FinancialConstants.ART_DEBTOR, FinancialConstants.ART_OWNER);
        create(LeaseConstants.AT_LEASE, LeaseConstants.ART_LANDLORD, LeaseConstants.ART_MANAGER, LeaseConstants.ART_TENANT);
    }

    void create(final String atTitle, final String... artTitles) {
        AgreementType at = AgreementType.create(atTitle, BankMandate.class.getName(), getContainer());
        getContainer().flush();
        at = agreementTypes.find(atTitle);
        for(String artTitle: artTitles) {
            create(artTitle, at, getContainer());
        }
    }

    private static AgreementRoleType create(final String title, final AgreementType appliesTo, final DomainObjectContainer container) {
        final AgreementRoleType agreementRoleType = container.newTransientInstance(AgreementRoleType.class);
        agreementRoleType.setTitle(title);
        agreementRoleType.setAppliesTo(appliesTo);
        container.persist(agreementRoleType);
        return agreementRoleType;
    }

    // //////////////////////////////////////

    private AgreementTypes agreementTypes;
    public void injectAgreementTypes(final AgreementTypes agreementTypes) {
        this.agreementTypes = agreementTypes;
    }




}