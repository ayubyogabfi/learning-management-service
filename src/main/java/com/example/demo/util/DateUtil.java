package com.example.demo.util;

import com.example.demo.constants.AppConstants;

import java.time.ZonedDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class DateUtil {

  private static final ZoneOffset offset = OffsetDateTime.now(AppConstants.ZONE_ID).getOffset();

  public static Date nowToDate() {
    return Date.from(ZonedDateTime.now(AppConstants.ZONE_ID).toInstant());
  }

  public static Date nowAfterHoursToDate(Long hours) {
    return Date.from(ZonedDateTime.now(AppConstants.ZONE_ID).plusHours(hours).toInstant());
  }

  public static Date nowAfterDaysToDate(Long days) {
    return Date.from(ZonedDateTime.now(AppConstants.ZONE_ID).plusDays(days).toInstant());
  }

  private DateUtil() {}
}
