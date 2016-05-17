/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.estatio.dom.lease;

import java.math.BigDecimal;

import org.joda.time.LocalDate;
import org.junit.Test;

import org.estatio.dom.valuetypes.AbstractInterval;
import org.estatio.dom.valuetypes.LocalDateInterval;

import static org.assertj.core.api.Assertions.assertThat;

public class LeaseTermForDepositTest {

    public static class ValueForDate extends LeaseTermForDepositTest {

        @Test
        public void date_outside_interval_returns_zero() throws Exception {

            final LeaseTermForDeposit leaseTermForDeposit = new LeaseTermForDeposit() {
                @Override public LocalDateInterval getInterval() {
                    return new LocalDateInterval(new LocalDate(2013, 1, 1), new LocalDate(2014, 1, 1), AbstractInterval.IntervalEnding.EXCLUDING_END_DATE);
                }
                @Override public BigDecimal getCalculatedDepositValue() {
                    return new BigDecimal("123.45");
                }
            };

            assertThat(leaseTermForDeposit.valueForDate(new LocalDate(2013, 1, 1))).isEqualTo(new BigDecimal("123.45"));
            assertThat(leaseTermForDeposit.valueForDate(new LocalDate(2012, 12, 31))).isEqualTo(new BigDecimal("0.00"));
            assertThat(leaseTermForDeposit.valueForDate(new LocalDate(2014, 1, 1))).isEqualTo(new BigDecimal("0.00"));

        }

        @Test
        public void manual_overrides_calculated() throws Exception {
            //Given
            final LeaseTermForDeposit leaseTermForDeposit = new LeaseTermForDeposit() {
                @Override public LocalDateInterval getInterval() {
                    return new LocalDateInterval(new LocalDate(2013, 1, 1), new LocalDate(2014, 1, 1), AbstractInterval.IntervalEnding.EXCLUDING_END_DATE);
                }
                @Override public BigDecimal getCalculatedDepositValue() {
                    return new BigDecimal("123.45");
                }
                @Override public BigDecimal getManualDepositValue() {
                    return new BigDecimal("123.46");
                }
            };
            
            //When,Then
            assertThat(leaseTermForDeposit.valueForDate(new LocalDate(2013, 1, 1))).isEqualTo(new BigDecimal("123.46"));
        }

    }

    public static class CopyValuesTo extends LeaseTermForDepositTest {

        @Test
        public void values_are_copied_correctly() throws Exception {
            // Given
            LeaseTermForDeposit from = new LeaseTermForDeposit();
            from.setDepositType(DepositType.VALUE_CAN_CHANGE_IN_TIME_EXCLUDING_VAT);
            from.setExcludedAmount(new BigDecimal("803.12"));
            from.setFraction(Fraction.M1);
            LeaseTermForDeposit to = new LeaseTermForDeposit();

            // When
            from.copyValuesTo(to);

            //Then
            assertThat(from.getDepositType()).isEqualTo(to.getDepositType());
            assertThat(from.getFraction()).isEqualTo(to.getFraction());
            assertThat(from.getExcludedAmount()).isEqualTo(to.getExcludedAmount());

        }
    }

    public static class VerifyUntil extends LeaseTermForDepositTest {

        @Test
        public void xxx() throws Exception {
            
        }
        
    }
    
    

 }
