package ru.itmo.wp.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Метод страницы, помеченный этой аннотацией, отвечает JSON-ом, а не HTML-шаблоном.
 * Содержимое view сериализуется в JSON; RedirectException превращается в {"redirect": "..."},
 * ValidationException — в {"error": "..."}.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Json {
}
