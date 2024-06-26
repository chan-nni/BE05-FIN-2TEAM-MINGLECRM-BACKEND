package com.team2final.minglecrm;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ArchitectureTest {

    JavaClasses javaClasses;

    @BeforeEach
    void beforeEach() {
        javaClasses = new ClassFileImporter()
                .withImportOption(new ImportOption.DoNotIncludeTests())
                .importPackages("com.team2final.minglecrm");
    }

    @Test
    @DisplayName("presentation 패키지 안에 있는 클래스들은 api로 끝난다.")
    void apiTest() {

        ArchRule rule = classes()
                .that().resideInAnyPackage("..presentation")
                .should().haveSimpleNameEndingWith("Api");

        rule.check(javaClasses);
    }

    @Test
    @DisplayName("presentation 패키지 안에 있는 클@RestController 애노테이션이 붙어있어야 한다.")
    void apiAnnotatedWithRestControllerTest() {

        ArchRule annotationRule = classes()
                .that().resideInAnyPackage("..presentation")
                .should().beAnnotatedWith(RestController.class);

        annotationRule.check(javaClasses);
    }

    @Test
    @DisplayName("presentation 패키지 안에 있는 클래스들은 api로 끝나고, @RestController 애노테이션이 붙어있어야 한다.")
    void annotatedWithRestControllerAndApiTest() {

        ArchRule rule = classes()
                .that().resideInAnyPackage("..presentation")
                .should().haveSimpleNameEndingWith("Api");

        ArchRule annotationRule = classes()
                .that().resideInAnyPackage("..presentation")
                .should().beAnnotatedWith(RestController.class);

        rule.check(javaClasses);
        annotationRule.check(javaClasses);
    }

    @Test
    @DisplayName("request 패키지 안에 있는 클래스들은 Request 또는 Condition으로 끝나야 한다.")
    void requestAndConditionClassNamingTest() {

        ArchRule rule = classes()
                .that().resideInAnyPackage("..request..")
                .and().areNotMemberClasses()
                .should().haveSimpleNameEndingWith("Request")
                .orShould().haveSimpleNameEndingWith("Condition");

        rule.check(javaClasses);
    }

    @Test
    @DisplayName("request 패키지 안에 있는 클래스들은 Request 또는 Condition으로 끝나야 하며, 내부 빌더 클래스들은 Builder로 끝나야 한다.")
    void requestAndConditionAndBuilderClassNamingTest() {

        ArchRule rule = classes()
                .that().resideInAnyPackage("..request..")
                .should().haveSimpleNameEndingWith("Request")
                .orShould().haveSimpleNameEndingWith("Condition")
                .orShould().haveSimpleNameContaining("Builder");

        rule.check(javaClasses);
    }

    @Test
    @DisplayName("request 패키지 안에 있는 빌더 클래스들은 Builder로 끝나야 한다.")
    void requestBuilderClassNamingTest() {

        ArchRule rule = classes()
                .that().resideInAnyPackage("..request..")
                .and().areMemberClasses()
                .should().haveSimpleNameContaining("Builder");

        rule.check(javaClasses);
    }

    @Test
    @DisplayName("response 패키지 안에 있는 클래스들은 Response로 끝나야 한다.")
    void responseClassNamingTest() {

        ArchRule rule = classes()
                .that().resideInAnyPackage("..response..")
                .and().areNotMemberClasses()
                .should().haveSimpleNameEndingWith("Response");

        rule.check(javaClasses);
    }

    @Test
    @DisplayName("response 패키지 안에 있는 빌더 클래스들은 Builder로 끝나야 한다.")
    void responseBuilderClassNamingTest() {

        ArchRule rule = classes()
                .that().resideInAnyPackage("..response..")
                .and().areMemberClasses()
                .should().haveSimpleNameContaining("Builder");

        rule.check(javaClasses);
    }

    @Test
    @DisplayName("service 패키지 안에 있는 클래스들은 Service로 끝나야 한다.")
    void serviceClassNamingTest() {
        ArchRule rule = classes()
                .that().resideInAnyPackage("..service..")
                .should().haveSimpleNameEndingWith("Service");

        rule.check(javaClasses);
    }

    @Test
    @DisplayName("service 패키지 안에 있는 클래스들은 @Service 애노테이션이 붙어있어야 한다.")
    void serviceAnnotatedWithServiceTest() {

        ArchRule rule = classes()
                .that().resideInAnyPackage("..service..")
                .should().beAnnotatedWith(Service.class);

        rule.check(javaClasses);
    }


    @Test
    @DisplayName("service 패키지 안에 있는 클래스들은 Service로 끝나야 하고, @Service 애노테이션이 붙어있어야 한다.")
    void serviceClassNamingAndAnnotationTest() {

        ArchRule rule = classes()
                .that().resideInAnyPackage("..service..")
                .should().haveSimpleNameEndingWith("Service")
                .andShould().beAnnotatedWith(Service.class);

        rule.check(javaClasses);
    }

    @Test
    @DisplayName("config 패키지 안에 있는 클래스는 Config 또는 Configuration으로 끝나야하고 @Configuration 애너테이션이 붙어있어야 한다.")
    void configTest() {

        ArchRule rule = classes()
                .that().resideInAnyPackage("..config..")
                .and().haveSimpleNameEndingWith("Config")
                .or().haveSimpleNameEndingWith("Configuration")
                .should().beAnnotatedWith(Configuration.class);

        rule.check(javaClasses);
    }


}
