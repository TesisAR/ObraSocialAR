package domainapp.modules.simple.types.viajero;

import org.apache.isis.applib.annotation.ParameterLayout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*@Column(length = Lugar.MAX_LEN, allowsNull = "false")
@Property(maxLength = Lugar.MAX_LEN)
@Parameter(maxLength = Lugar.MAX_LEN)*/
@ParameterLayout(named = "Lugar")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Lugar {

   // int MAX_LEN = 40;
}