package com.example.demo.constants;

public class APIConstants {

  public static final String LOGIN_PATH = "/v1/login";
  public static final String USERS_PATH = "/v1/users";
  public static final String SWAGGER_UI_PATH = "/swagger-ui";
  public static final String SWAGGER_RESOURCES_PATH = "/swagger-resources";
  public static final String API_DOCS_PATH = "/v3/api-docs";
  public static final String REGISTER_ACCOUNT_PATH = "/v1/register-account";

  public static final String ROOT_ENTRY_POINT = "/";
  public static final String SWAGGER_UI_ENTRY_POINT = APIConstants.SWAGGER_UI_PATH + "/**";
  public static final String SWAGGER_HTML_ENTRY_POINT = APIConstants.SWAGGER_UI_PATH + ".html/**";
  public static final String SWAGGER_RESOURCES_ENTRY_POINT = APIConstants.SWAGGER_RESOURCES_PATH + "/**";
  public static final String API_DOCS_ENTRY_POINT = APIConstants.API_DOCS_PATH + "/**";
  public static final String LOGIN_ENTRY_POINT = APIConstants.LOGIN_PATH + "/**";
  public static final String ERROR_ENTRY_POINT = "/error";
}
