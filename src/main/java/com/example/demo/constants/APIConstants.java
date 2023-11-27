package com.example.demo.constants;

public class APIConstants {

  public static final String LOGIN_PATH = "/login";
  public static final String REFRESH_TOKEN_PATH = "/refresh-token";
  public static final String USERS_PATH = "/v1/users";
  public static final String SWAGGER_UI_PATH = "/swagger-ui";
  public static final String SWAGGER_RESOURCES_PATH = "/swagger-resources";
  public static final String API_DOCS_PATH = "/v3/api-docs";
  public static final String SECTION_PATH = "/v1/section";
  public static final String AUTH_PATH = "/v1/authenticate";

  public static final String ROOT_ENTRY_POINT = "/";
  public static final String SWAGGER_UI_ENTRY_POINT = APIConstants.SWAGGER_UI_PATH + "/**";
  public static final String SWAGGER_HTML_ENTRY_POINT = APIConstants.SWAGGER_UI_PATH + ".html/**";
  public static final String SWAGGER_RESOURCES_ENTRY_POINT = APIConstants.SWAGGER_RESOURCES_PATH + "/**";
  public static final String API_DOCS_ENTRY_POINT = APIConstants.API_DOCS_PATH + "/**";
  public static final String LOGIN_ENTRY_POINT = APIConstants.AUTH_PATH + APIConstants.LOGIN_PATH;
  public static final String REFRESH_TOKEN_ENTRY_POINT = APIConstants.REFRESH_TOKEN_PATH + "/**";
  public static final String USERS_ENTRY_POINT = APIConstants.USERS_PATH + "/**";
  public static final String ERROR_ENTRY_POINT = "/error";
  public static final String SECTION_ENTRY_POINT = APIConstants.SECTION_PATH + "/**";

}
