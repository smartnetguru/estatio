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
package org.estatio.integtests.currency;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.estatio.dom.currency.Currency;
import org.estatio.dom.currency.CurrencyRepository;
import org.estatio.fixture.currency.CurrencyRepositoryRefData;
import org.estatio.integtests.EstatioIntegrationTest;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyRepositoryTest extends EstatioIntegrationTest {

    @Before
    public void setupData() {
        runFixtureScript(new FixtureScript() {
            @Override
            protected void execute(ExecutionContext executionContext) {
                executionContext.executeChild(this, new CurrencyRepositoryRefData());
            }
        });
    }

    public static class FindCurrency extends CurrencyRepositoryTest {

        @Test
        public void happyCase() throws Exception {
            Currency euro = currencyRepository.findCurrency(CurrencyRepositoryRefData.EUR);
            assertThat(euro.getName()).isEqualTo("Euro");
        }
    }

    public static class FindOrCreateCurrency extends CurrencyRepositoryTest {

        @Test
        public void findCurrency() throws Exception {
            // when
            final Currency currency = currencyRepository.findOrCreateCurrency(CurrencyRepositoryRefData.EUR, "Euro");

            // then
            assertThat(currency.getReference()).isEqualTo(CurrencyRepositoryRefData.EUR);
            assertThat(currency.getName()).isEqualTo("Euro");
        }

        @Test
        public void createCurrency() throws Exception {
            // when
            final Currency currency = currencyRepository.findOrCreateCurrency("TST", "Test currency");

            // then
            assertThat(currency.getReference()).isEqualTo("TST");
            assertThat(currency.getName()).isEqualTo("Test currency");
        }
    }

    @Inject
    CurrencyRepository currencyRepository;
}