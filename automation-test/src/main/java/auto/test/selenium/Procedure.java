package auto.test.selenium;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Procedure {



    private String name;

    List<String> interestedAlarms;

    List<ActionStep> actionSteps;

    public boolean hasInterestIn(String alarm) {
        return CollectionUtils.isNotEmpty(interestedAlarms) && interestedAlarms.contains(alarm);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getInterestedAlarms() {
        return interestedAlarms;
    }

    public void setInterestedAlarms(List<String> interestedAlarms) {
        this.interestedAlarms = interestedAlarms;
    }

    public List<ActionStep> getActionSteps() {
        return actionSteps;
    }

    public void setActionSteps(List<ActionStep> actionSteps) {
        this.actionSteps = actionSteps;
    }

}
