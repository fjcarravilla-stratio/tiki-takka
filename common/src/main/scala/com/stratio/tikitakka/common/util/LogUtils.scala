/*
 * © 2017 Stratio Big Data Inc., Sucursal en España. All rights reserved.
 *
 * This software – including all its source code – contains proprietary information of Stratio Big Data Inc., Sucursal en España and may not be revealed, sold, transferred, modified, distributed or otherwise made available, licensed or sublicensed to third parties; nor reverse engineered, disassembled or decompiled, without express written authorization from Stratio Big Data Inc., Sucursal en España.
 */
package com.stratio.tikitakka.common.util

import org.slf4j.{Logger, LoggerFactory}

trait LogUtils {

  val log : Logger = LoggerFactory.getLogger(getClass.getName)

  def logFunction[T](level: LogLevel)(message: String)(f: => T): T = {
    level match {
      case DEBUG => log.debug(message)
      case ERROR => log.error(message)
      case INFO => log.info(message)
      case TRACE => log.trace(message)
      case WARN => log.warn(message)
    }
    f
  }

  sealed trait LogLevel
  case object DEBUG extends LogLevel
  case object ERROR extends LogLevel
  case object INFO extends LogLevel
  case object TRACE extends LogLevel
  case object WARN extends LogLevel

}
