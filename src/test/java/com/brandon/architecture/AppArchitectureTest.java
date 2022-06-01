package com.brandon.architecture;

import com.brandon.OrderApiApplication;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

import javax.persistence.EntityManager;

@AnalyzeClasses(packagesOf = OrderApiApplication.class)
public class AppArchitectureTest {


    // domainLayer is never depend on another layer
    @ArchTest
    public ArchRule domainRule = ArchRuleDefinition.noClasses().that().resideInAPackage("domain")
            .should().accessClassesThat().resideInAPackage("controller")
            .orShould().accessClassesThat().resideInAPackage("service")
            .orShould().accessClassesThat().resideInAPackage("repository")
            .orShould().accessClassesThat().resideInAPackage("util");

    // controllerLayer is never depend on repositoryLayer
    @ArchTest
    public ArchRule controllerRule = ArchRuleDefinition.noClasses().that().haveSimpleNameEndingWith("Controller")
            .should().accessClassesThat().resideInAPackage("repository");

    // batchLayer is never depend on controllerLayer
    @ArchTest
    public ArchRule batchRule = ArchRuleDefinition.noClasses().that().resideInAPackage("batch")
            .should().accessClassesThat().resideInAPackage("controller");

    // serviceLayer is only accessed controllerLayer
    @ArchTest
    public ArchRule serviceRule = ArchRuleDefinition.classes().that().haveSimpleNameEndingWith("Service")
            .should().onlyHaveDependentClassesThat().haveSimpleNameEndingWith("Controller");


    // repositoryLayer is never depend on controllerLayer or serviceLayer
    @ArchTest
    public ArchRule repositoryRule = ArchRuleDefinition.noClasses().that().haveSimpleNameEndingWith("Repository")
            .should().accessClassesThat().resideInAPackage("controller")
            .orShould().accessClassesThat().resideInAPackage("service");

    // EntityManger is only managed repositoryLayer
    @ArchTest
    public ArchRule entityManagerRule = ArchRuleDefinition.noClasses().that().areAssignableTo(EntityManager.class)
            .should().onlyBeAccessed().byClassesThat().haveSimpleNameEndingWith("Repository");

}