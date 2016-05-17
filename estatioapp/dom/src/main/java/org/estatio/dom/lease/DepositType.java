package org.estatio.dom.lease;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import org.joda.time.LocalDate;

public enum DepositType {

    VALUE_CAN_CHANGE_IN_TIME_INCLUDING_VAT(true, true) ,
    VALUE_ON_START_INCLUDING_VAT(true, false) ,
    VALUE_CAN_CHANGE_IN_TIME_EXCLUDING_VAT(false, true) ,
    VALUE_ON_START_EXCLUDING_VAT(false, false);

    private boolean includeVat;
    private boolean adjustInTime;

    DepositType(boolean includeVat, boolean adjustInTime){
        this.includeVat = includeVat;
        this.adjustInTime = adjustInTime;
    }

    protected BigDecimal addTaxIfNeeded(BigDecimal netValue, LeaseItem leaseItem, LocalDate date) {
        if (includeVat) {
            return leaseItem.getEffectiveTax().grossFromNet(netValue, date);
        }
        return netValue;
    }

    BigDecimal calculateDepositBase(final LeaseTermForDeposit leaseTermForDeposit, final LocalDate date) {
        BigDecimal currentValue = BigDecimal.ZERO;
        for (LeaseItem leaseItem : leaseTermForDeposit.getLeaseItem().getSourceItems().stream().map(i-> i.getSourceItem()).collect(Collectors.toList())) {
            LocalDate dateToUse = adjustInTime ? date : leaseItem.getStartDate();
            BigDecimal valueForDate = leaseItem.valueForDate(dateToUse);
            currentValue = currentValue.add(addTaxIfNeeded(valueForDate, leaseItem, dateToUse));
        }
        return currentValue;
    }

}
