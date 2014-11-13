/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.kenyadq.calculation.rdqa;

import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.ListResult;
import org.openmrs.calculation.result.SimpleResult;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.CalculationUtils;
import org.openmrs.module.kenyacore.report.data.patient.definition.VisitsForPatientDataDefinition;
import org.openmrs.module.reporting.data.DataDefinition;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Calculates the total no of visits for a patient
 */
public class VisitsForAPatientCalculation extends AbstractPatientCalculation {

	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {

		VisitsForPatientDataDefinition definition = new VisitsForPatientDataDefinition();
		CalculationResultMap data = CalculationUtils.evaluateWithReporting(definition, cohort, parameterValues, null, context);
		CalculationResultMap ret = new CalculationResultMap();

		for (Integer ptid : cohort) {
			ListResult result = (ListResult) data.get(ptid);
			List<Visit> visits = CalculationUtils.extractResultValues(result);
			Integer noOfVisits = visits != null && visits.size() > 0? visits.size(): 0;
			ret.put(ptid, new SimpleResult(noOfVisits, this));
		}

		return ret;
	}
}
