package domainapp.modules.simple.fixture;

<<<<<<< HEAD
=======
import domainapp.modules.simple.dom.afiliado.Afiliado;
>>>>>>> 1dd8206bec68dfc87ae023a152f9dd2feae3f759
import org.apache.isis.applib.services.registry.ServiceRegistry;
import org.apache.isis.testing.fixtures.applib.personas.PersonaWithBuilderScript;
import org.apache.isis.testing.fixtures.applib.personas.PersonaWithFinder;
import org.apache.isis.testing.fixtures.applib.setup.PersonaEnumPersistAll;

<<<<<<< HEAD
import domainapp.modules.simple.dom.afiliados.SimpleObject;
import domainapp.modules.simple.dom.afiliados.SimpleObjects;
=======


import domainapp.modules.simple.dom.afiliado.Afiliado;
import domainapp.modules.simple.dom.afiliado.Afiliados;
>>>>>>> 1dd8206bec68dfc87ae023a152f9dd2feae3f759
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SimpleObject_persona
<<<<<<< HEAD
implements PersonaWithBuilderScript<SimpleObjectBuilder>, PersonaWithFinder<SimpleObject> {
=======
implements PersonaWithBuilderScript<SimpleObjectBuilder>, PersonaWithFinder<Afiliado> {
>>>>>>> 1dd8206bec68dfc87ae023a152f9dd2feae3f759

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
<<<<<<< HEAD
    public SimpleObject findUsing(final ServiceRegistry serviceRegistry) {
        SimpleObjects simpleObjects = serviceRegistry.lookupService(SimpleObjects.class).orElse(null);
        return simpleObjects.findByNameExact(name);
    }

    public static class PersistAll
    extends PersonaEnumPersistAll<SimpleObject_persona, SimpleObject> {
=======
    public Afiliado findUsing(final ServiceRegistry serviceRegistry) {
        Afiliados afiliados = serviceRegistry.lookupService(Afiliados.class).orElse(null);
        return afiliados.findByNameExact(name);
    }

    public static class PersistAll
    extends PersonaEnumPersistAll<SimpleObject_persona, Afiliado> {
>>>>>>> 1dd8206bec68dfc87ae023a152f9dd2feae3f759

        public PersistAll() {
            super(SimpleObject_persona.class);
        }
    }
}
