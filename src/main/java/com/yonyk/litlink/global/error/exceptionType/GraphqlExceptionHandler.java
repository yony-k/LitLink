package com.yonyk.litlink.global.error.exceptionType;

import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.yonyk.litlink.global.error.CustomException;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GraphqlExceptionHandler {

  @GraphQlExceptionHandler
  public GraphQLError handleCustomException(CustomException ex, DataFetchingEnvironment env) {
    return GraphqlErrorBuilder.newError()
        .message(ex.getMessage())
        .path(env.getExecutionStepInfo().getPath())
        .location(env.getField().getSourceLocation())
        .errorType(ErrorType.BAD_REQUEST)
        .build();
  }
}
