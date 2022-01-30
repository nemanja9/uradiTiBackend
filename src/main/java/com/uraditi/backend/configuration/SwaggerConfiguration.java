package com.uraditi.backend.configuration;

//
//
//@Configuration
//@EnableSwagger2
public class SwaggerConfiguration {
//
//    private static final String SECURITY_SCHEME = "Token";
//    private static final String BASE_PACKAGE = "com.daimler";
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build()
//                .produces(Set.of(MediaType.APPLICATION_JSON_VALUE))
//                .securityContexts(singletonList(
//                        SecurityContext.builder()
//                                .securityReferences(
//                                        singletonList(SecurityReference.builder()
//                                                .reference(SECURITY_SCHEME)
//                                                .scopes(new AuthorizationScope[0])
//                                                .build()
//                                        )
//                                )
//                                .build())
//                )
//                .securitySchemes(singletonList(apiKey()))
//                .useDefaultResponseMessages(false)
//                .enable(true)
//                .apiInfo(apiInfo());
//    }
//
//    private ApiKey apiKey() {
//        return new ApiKey(SECURITY_SCHEME, HttpHeaders.AUTHORIZATION, "header");
//    }
//
//    @Bean
//    UiConfiguration uiConfig() {
//        return UiConfigurationBuilder.builder()
//                .validatorUrl("validatorUrl")
//                .tagsSorter(TagsSorter.ALPHA)
//                .defaultModelRendering(ModelRendering.MODEL)
//                .docExpansion(DocExpansion.LIST)
//                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
//                .showExtensions(true)
//                .displayRequestDuration(true)
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("A-ZST Application")
//                .description("Daimler A-ZST Application BE")
//                .version("v1")
//                .build();
//    }

}