package domainapp.webapp.custom.restapi;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.apache.isis.applib.services.iactnlayer.InteractionContext;
import org.apache.isis.applib.services.iactnlayer.InteractionService;
import org.apache.isis.applib.services.user.UserMemento;
import org.apache.isis.applib.services.xactn.TransactionalProcessor;

import lombok.RequiredArgsConstructor;

<<<<<<< HEAD
import domainapp.modules.simple.dom.afiliados.SimpleObject;
import domainapp.modules.simple.dom.afiliados.SimpleObjects;
=======
import domainapp.modules.simple.dom.afiliado.Afiliado;
import domainapp.modules.simple.dom.afiliado.Afiliados;
>>>>>>> 1dd8206bec68dfc87ae023a152f9dd2feae3f759

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Inject})
class CustomController {

    private final InteractionService interactionService;
    private final TransactionalProcessor transactionalProcessor;
<<<<<<< HEAD
    private final SimpleObjects simpleObjects;

    @GetMapping("/custom/simpleObjects")
    List<SimpleObject> all() {
        return call("sven", simpleObjects::listAll)
                .orElse(Collections.<SimpleObject>emptyList());
=======
    private final Afiliados afiliados;

    @GetMapping("/custom/afiliados")
    List<Afiliado> all() {
        return call("sven", afiliados::listAll)
                .orElse(Collections.<Afiliado>emptyList());
>>>>>>> 1dd8206bec68dfc87ae023a152f9dd2feae3f759
    }

    private <T> Optional<T> call(
            final String username,
            final Callable<T> callable) {

        return interactionService.call(
                InteractionContext.ofUserWithSystemDefaults(UserMemento.ofName(username)),
                () -> transactionalProcessor.callWithinCurrentTransactionElseCreateNew(callable))
                .optionalElseFail(); // re-throws exception that has occurred, if any
    }

}
