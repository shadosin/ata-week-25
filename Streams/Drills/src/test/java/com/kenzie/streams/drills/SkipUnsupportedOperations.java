package com.kenzie.streams.drills;

import static org.junit.jupiter.api.Assumptions.assumeFalse;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

public class SkipUnsupportedOperations implements TestExecutionExceptionHandler {
    @Override
    public void handleTestExecutionException(final ExtensionContext extensionContext, final Throwable throwable)
        throws Throwable {
        assumeFalse(throwable instanceof UnsupportedOperationException);
        // propagate actual failures
        throw throwable;
    }
}
