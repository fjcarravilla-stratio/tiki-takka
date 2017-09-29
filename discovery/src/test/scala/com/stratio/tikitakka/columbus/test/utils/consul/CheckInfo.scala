/*
 * © 2017 Stratio Big Data Inc., Sucursal en España. All rights reserved.
 *
 * This software – including all its source code – contains proprietary information of Stratio Big Data Inc., Sucursal en España and may not be revealed, sold, transferred, modified, distributed or otherwise made available, licensed or sublicensed to third parties; nor reverse engineered, disassembled or decompiled, without express written authorization from Stratio Big Data Inc., Sucursal en España.
 */
package com.stratio.tikitakka.columbus.test.utils.consul

import play.api.libs.json.Json
import play.api.libs.json.Reads
import play.api.libs.json.Writes

case class CheckInfo(
                      Node: String,
                      CheckID: String,
                      Name: String,
                      Notes: String,
                      Status: String,
                      ServiceID: String
                      )

object CheckInfo {

  implicit val writer: Writes[CheckInfo] = Json.writes[CheckInfo]
  implicit val reads: Reads[CheckInfo] = Json.reads[CheckInfo]

}
