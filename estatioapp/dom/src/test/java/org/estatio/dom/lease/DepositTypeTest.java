package org.estatio.dom.lease;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.joda.time.LocalDate;
import org.junit.Rule;
import org.junit.Test;

import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;

import org.estatio.dom.tax.Tax;

import static org.assertj.core.api.Assertions.assertThat;

public class DepositTypeTest {

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(JUnitRuleMockery2.Mode.INTERFACES_AND_CLASSES);

    @Mock
    LeaseItem mockLeaseItem;

    @Mock
    Tax mockTax;

    public static class CalculateDepositBase extends DepositTypeTest {

        @Test
        public void value_can_change() throws Exception {

            final LeaseItem leaseItem = new LeaseItem() {
                @Override public List<LeaseItemSource> getSourceItems() {
                    return Arrays.asList(new LeaseItemSource(null, mockLeaseItem));
                }
            };
            final LeaseTermForDeposit leaseTermForDeposit = new LeaseTermForDeposit();
            leaseTermForDeposit.setLeaseItem(leaseItem);

            context.checking(new Expectations() {
                {
                    allowing(mockLeaseItem).valueForDate(with(new LocalDate(2013, 1, 1)));
                    will(returnValue(new BigDecimal("111111.00")));
                    allowing(mockLeaseItem).getStartDate();
                    will(returnValue(new LocalDate(2012, 1, 1)));
                    allowing(mockLeaseItem).valueForDate(with(new LocalDate(2012, 1, 1)));
                    will(returnValue(new BigDecimal("100000.00")));
                }
            });

            assertThat(DepositType.VALUE_CAN_CHANGE_IN_TIME_EXCLUDING_VAT.calculateDepositBase(leaseTermForDeposit, new LocalDate(2013, 1, 1))).isEqualTo(new BigDecimal("111111.00"));
            assertThat(DepositType.VALUE_ON_START_EXCLUDING_VAT.calculateDepositBase(leaseTermForDeposit, new LocalDate(2013, 1, 1))).isEqualTo(new BigDecimal("100000.00"));

        }

        @Test
        public void including_vat() throws Exception {

            final LeaseItem leaseItem = new LeaseItem() {
                @Override public List<LeaseItemSource> getSourceItems() {
                    return Arrays.asList(new LeaseItemSource(null, mockLeaseItem));
                }
            };
            final LeaseTermForDeposit leaseTermForDeposit = new LeaseTermForDeposit();
            leaseTermForDeposit.setLeaseItem(leaseItem);


            context.checking(new Expectations() {
                {
                    allowing(mockLeaseItem).getEffectiveTax();
                    will(returnValue(mockTax));
                    allowing(mockTax).grossFromNet(with(new BigDecimal("100.00")), with(new LocalDate(2013, 1, 1)));
                    will(returnValue(new BigDecimal("121.00")));
                    allowing(mockTax).grossFromNet(with(new BigDecimal("100.00")), with(new LocalDate(2012, 1, 1)));
                    will(returnValue(new BigDecimal("121.00")));
                    allowing(mockLeaseItem).valueForDate(with(new LocalDate(2013, 1, 1)));
                    will(returnValue(new BigDecimal("100.00")));
                    allowing(mockLeaseItem).getStartDate();
                    will(returnValue(new LocalDate(2012, 1, 1)));
                    allowing(mockLeaseItem).valueForDate(with(new LocalDate(2012, 1, 1)));
                    will(returnValue(new BigDecimal("100.00")));
                }
            });

            assertThat(DepositType.VALUE_CAN_CHANGE_IN_TIME_INCLUDING_VAT.calculateDepositBase(leaseTermForDeposit, new LocalDate(2013, 1, 1))).isEqualTo(new BigDecimal("121.00"));
            assertThat(DepositType.VALUE_ON_START_INCLUDING_VAT.calculateDepositBase(leaseTermForDeposit, new LocalDate(2013, 1, 1))).isEqualTo(new BigDecimal("121.00"));

        }


    }
}