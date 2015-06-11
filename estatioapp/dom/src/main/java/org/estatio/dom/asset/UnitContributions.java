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

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.estatio.services.clock.ClockService;

@DomainService(nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
public class UnitContributions {

    @Action(semantics = SemanticsOf.SAFE) // it's not an action but it's safe
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION) // we specify action layout hidden does not work
    //@PropertyLayout(hidden = Where.ALL_TABLES) // so we try to hide it here
    //@Hidden(where = Where.ALL_TABLES) // unfortunately this doesn't work too
    //Prototype doesn't contribute either...
    public BigDecimal calculatedArea(final Property property) {
        return units.sumAreaByPropertyAndActiveOnDate(property, clockService.now());
    }

    @Inject
    Units units;

    @Inject
    ClockService clockService;

}
