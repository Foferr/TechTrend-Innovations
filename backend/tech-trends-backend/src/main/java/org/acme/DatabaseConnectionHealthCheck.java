package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import javax.sql.DataSource;
import java.sql.Connection;

@Readiness
@ApplicationScoped
public class DatabaseConnectionHealthCheck implements HealthCheck {

    @Inject
    DataSource dataSource;

    @Override
    public HealthCheckResponse call() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(10)) {
                return HealthCheckResponse.up("Database connection health check: SUCCESSFUL");
            }
        } catch (Exception e) {
            // log the exception details
        }
        return HealthCheckResponse.down("Database connection health check: FAILED");
    }
}
