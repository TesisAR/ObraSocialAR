package domainapp.modules.simple.types.plan;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.jdo.annotations.Column;

import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;

@Column(length = FechaVencimiento.MAX_LEN, allowsNull = "false")
@Property(maxLength = FechaVencimiento.MAX_LEN)
@Parameter(maxLength = FechaVencimiento.MAX_LEN)
@ParameterLayout(named = "FechaVencimiento")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface FechaVencimiento {

    int MAX_LEN = 40;
}