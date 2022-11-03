package domainapp.modules.simple;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.apache.isis.testing.fixtures.applib.fixturescripts.FixtureScript;
import org.apache.isis.testing.fixtures.applib.modules.ModuleWithFixtures;

<<<<<<< HEAD
import domainapp.modules.simple.dom.afiliados.SimpleObject;
=======
import domainapp.modules.simple.dom.afiliado.Afiliado;
import domainapp.modules.simple.dom.plan.Plan;
import domainapp.modules.simple.dom.plan.Planes;
>>>>>>> 1dd8206bec68dfc87ae023a152f9dd2feae3f759

@Configuration
@ComponentScan
public class SimpleModule implements ModuleWithFixtures {

    @Override
    public FixtureScript getTeardownFixture() {
        return new FixtureScript() {
            @Override
            protected void execute(ExecutionContext executionContext) {
<<<<<<< HEAD
                repositoryService.removeAll(SimpleObject.class);
=======
                repositoryService.removeAll(Afiliado.class);
>>>>>>> 1dd8206bec68dfc87ae023a152f9dd2feae3f759
            }
        };
    }
}
