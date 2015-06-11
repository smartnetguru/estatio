/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
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
package org.estatio.dom.asset;

import java.math.BigDecimal;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;

import org.estatio.services.clock.ClockService;

import static org.assertj.core.api.Assertions.assertThat;

public class UnitContributionsTest {

    public static class CalculatedArea {

        @Rule
        public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(JUnitRuleMockery2.Mode.INTERFACES_AND_CLASSES);

        @Mock
        private Units mockUnits;
        @Mock
        private ClockService mockClockService;

        private Property someProperty = new Property();
        private LocalDate someTime = new LocalDate();
        final BigDecimal theArea = new BigDecimal("123.45");

        private UnitContributions uc;

        @Before
        public void setup() {
            uc = new UnitContributions();
            uc.clockService = mockClockService;
            uc.units = mockUnits;

            context.checking(new Expectations() {{
                allowing(mockClockService).now();
                will(returnValue(someTime));
            }});
        }

        @Test
        public void happyCase() {

            context.checking(new Expectations() {{
                oneOf(mockUnits).sumAreaByPropertyAndActiveOnDate(someProperty, someTime);
                will(returnValue(theArea));
            }});

            assertThat(uc.calculatedArea(someProperty)).isEqualTo(theArea);
        }
    }

}