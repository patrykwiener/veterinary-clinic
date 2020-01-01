package pl.polsl.veterynaryclinic.validator.worker;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.polsl.veterynaryclinic.model.nondatabase.WorkerReport;

@Component
public class WorkerReportValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return WorkerReport.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        WorkerReport workerReport = (WorkerReport) target;
        validateDate(workerReport, errors);
    }

    private void validateDate(WorkerReport workerReport, Errors errors) {
        if (workerReport.getStartDate().after(workerReport.getEndDate())) {
            errors.rejectValue("endDate", "error.endDate", "Data końcowa nie może być wcześniejsza niż data początkowa!");
        }
    }
}
