package dominogame.model;

import java.time.LocalDateTime;

/**
 * The CurrentDateAndTime class represents the current date and time.
 */
public class CurrentDateAndTime {

    private LocalDateTime currentDateTime = LocalDateTime.now();

    /**
     * Retrieves the current date and time.
     *
     * @return The current date and time.
     */
    public LocalDateTime getCurrentDateTime() {
        return currentDateTime;
    }
}
