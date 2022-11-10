package com.example.mobilewebws.ui.response;

/*
 * Copyright 2021 MonetaGo, Inc. All Rights Reserved.
 *
 * This code is copyrighted material that is confidential and proprietary to MonetaGo, Inc.
 * and may not (in whole or in part) be published, publicly displayed, copied, modified or
 * used in any way other than as expressly permitted in a written agreement executed by
 * MonetaGo, Inc. No portion of this code may be used to create derivative works or exploited
 * in any other way without MonetaGo, Inc.'s prior written consent. No portion of this code
 * may be transmitted or redistributed to any person without MonetaGo, Inc.'s prior written
 * consent. This notice may not be deleted or modified without MonetaGo, Inc.'s consent.
 */
public enum ErrorMessages {

  MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields"),
  RECORD_ALREADY_EXISTS("Record already exists"),
  INTERNAL_SERVER_ERROR("Internal server error"),
  NO_RECORD_FOUND("Record with provided id is not found"),
  AUTHENTICATION_FAILED("Authentication failed"),
  COULD_NOT_UPDATE_RECORD("Could not update record"),
  COULD_NOT_DELETE_RECORD("Could not delete record"),
  EMAIL_ADDRESS_NOT_VERIFIED("Email address could not be verified");

  private String errorMessage;

  ErrorMessages(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  /**
   * @return the errorMessage
   */
  public String getErrorMessage() {
    return errorMessage;
  }

  /**
   * @param errorMessage the errorMessage to set
   */
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
