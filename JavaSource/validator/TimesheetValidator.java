package validator;

import java.util.List;

import model.TimesheetRow;
import model.TimesheetRowPK;

public class TimesheetValidator {
    public static boolean isValidTimesheet(List<TimesheetRow> rows) {

        for (TimesheetRow row : rows) {
            TimesheetRowPK pk = row.getTimesheetRowPk();
            if (isAnyNull(pk.getProNo(), pk.getStartDate(), pk.getWpid(),
                    row.getState())) {
                return false;
            }
        }

        return true;
    }

    private static boolean isAnyNull(Object... objects) {
        for (Object obj : objects) {
            if (obj == null) {
                return false;
            }
        }

        return true;
    }
}
