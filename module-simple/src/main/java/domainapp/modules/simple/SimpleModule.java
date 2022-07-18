package domainapp.modules.simple;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.apache.isis.testing.fixtures.applib.fixturescripts.FixtureScript;
import org.apache.isis.testing.fixtures.applib.modules.ModuleWithFixtures;

import domainapp.modules.simple.dom.afiliados.Afiliado;
import domainapp.modules.planes.dom.Plan;


@Configuration
@ComponentScan
public class SimpleModule implements ModuleWithFixtures {

    @Override
    public FixtureScript getTeardownFixture() {
        return new FixtureScript() {
            @Override
            protected void execute(ExecutionContext executionContext) {
                repositoryService.removeAll(Afiliado.class);
                repositoryService.removeAll(Plan.class);
            }
        };
    }
}
