/*
 * © 2017 Stratio Big Data Inc., Sucursal en España. All rights reserved.
 *
 * This software – including all its source code – contains proprietary information of Stratio Big Data Inc., Sucursal en España and may not be revealed, sold, transferred, modified, distributed or otherwise made available, licensed or sublicensed to third parties; nor reverse engineered, disassembled or decompiled, without express written authorization from Stratio Big Data Inc., Sucursal en España.
 */
package com.stratio.tikitakka.common.util

import scala.util.Try

import com.typesafe.config.ConfigFactory

object ConfigComponent {

  val config = ConfigFactory.load()

  def getString(key: String): Option[String] = Try(config.getString(key)).toOption

  def getString(key: String, default: String): String = getString(key) getOrElse default

  def getInt(key: String): Option[Int] = Try(config.getInt(key)).toOption

  def getInt(key: String, default: Int): Int = getInt(key) getOrElse default

  def getBoolean(key: String): Option[Boolean] = Try(config.getBoolean(key)).toOption

  def getBoolean(key: String, default: Boolean): Boolean = getBoolean(key) getOrElse default

}
