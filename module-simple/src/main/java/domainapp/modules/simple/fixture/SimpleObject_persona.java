package domainapp.modules.simple.fixture;

import domainapp.modules.simple.dom.afiliado.Afiliado;
import org.apache.isis.applib.services.registry.ServiceRegistry;
import org.apache.isis.testing.fixtures.applib.personas.PersonaWithBuilderScript;
import org.apache.isis.testing.fixtures.applib.personas.PersonaWithFinder;
import org.apache.isis.testing.fixtures.applib.setup.PersonaEnumPersistAll;



import domainapp.modules.simple.dom.afiliado.Afiliado;
import domainapp.modules.simple.dom.afiliado.Afiliados;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SimpleObject_persona
implements PersonaWithBuilderScript<SimpleObjectBuilder>, PersonaWithFinder<Afiliado> {

    FOO("Foo"),
    BAR("Bar"),
    BAZ("Baz"),
    FRODO("Frodo"),
    FROYO("Froyo"),
    FIZZ("Fizz"),
    BIP("Bip"),
    BOP("Bop"),
    BANG("Bang"),
    BOO("Boo");

    private final String name;

    @Override
    public SimpleObjectBuilder builder() {
        return new SimpleObjectBuilder().setName(name);
    }

    @Override
    public Afiliado findUsing(final ServiceRegistry serviceRegistry) {
        Afiliados afiliados = serviceRegistry.lookupService(Afiliados.class).orElse(null);
        return afiliados.findByNameExact(name);
    }

    public static class PersistAll
    extends PersonaEnumPersistAll<SimpleObject_persona, Afiliado> {

        public PersistAll() {
            super(SimpleObject_persona.class);
        }
    }
}
