package mg.itu.nyantsa.annotation.methods;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Annotation RestApi, applicable aux méthodes
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RestApi {
}
