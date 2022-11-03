package domainapp.webapp.application.services.health;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import org.apache.isis.applib.services.health.Health;
import org.apache.isis.applib.services.health.HealthCheckService;

<<<<<<< HEAD
import domainapp.modules.simple.dom.afiliados.SimpleObjects;
=======
import domainapp.modules.simple.dom.afiliado.Afiliados;
>>>>>>> 1dd8206bec68dfc87ae023a152f9dd2feae3f759

@Service
@Named("domainapp.HealthCheckServiceImpl")
public class HealthCheckServiceImpl implements HealthCheckService {

<<<<<<< HEAD
    private final SimpleObjects simpleObjects;

    @Inject
    public HealthCheckServiceImpl(SimpleObjects simpleObjects) {
        this.simpleObjects = simpleObjects;
=======
    private final Afiliados afiliados;

    @Inject
    public HealthCheckServiceImpl(Afiliados afiliados) {
        this.afiliados = afiliados;
>>>>>>> 1dd8206bec68dfc87ae023a152f9dd2feae3f759
    }

    @Override
    public Health check() {
        try {
<<<<<<< HEAD
            simpleObjects.ping();
=======
           // afiliados.ping();
>>>>>>> 1dd8206bec68dfc87ae023a152f9dd2feae3f759
            return Health.ok();
        } catch (Exception ex) {
            return Health.error(ex);
        }
    }
}
