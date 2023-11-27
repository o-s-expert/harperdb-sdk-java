package expert.os.harperdb;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Abstract class representing a generic operation in HarperDB.
 */
abstract class Operation {

    @JsonProperty
    private final OperationType operation;

    /**
     * Constructs a new Operation with the specified operation type.
     *
     * @param operation The type of the operation.
     */
    Operation(OperationType operation) {
        this.operation = operation;
    }

    /**
     * Gets the type of the operation.
     *
     * @return The operation type.
     */

    public OperationType operation() {
        return operation;
    }
}