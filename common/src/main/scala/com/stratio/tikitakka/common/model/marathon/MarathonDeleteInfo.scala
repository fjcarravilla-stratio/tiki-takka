/*
 * © 2017 Stratio Big Data Inc., Sucursal en España. All rights reserved.
 *
 * This software – including all its source code – contains proprietary information of Stratio Big Data Inc., Sucursal en España and may not be revealed, sold, transferred, modified, distributed or otherwise made available, licensed or sublicensed to third parties; nor reverse engineered, disassembled or decompiled, without express written authorization from Stratio Big Data Inc., Sucursal en España.
 */
package com.stratio.tikitakka.common.model.marathon

import play.api.libs.json.Json
import play.api.libs.json.Reads
import play.api.libs.json.Writes

case class MarathonDeleteInfo(version: String, deploymentId: String)

object MarathonDeleteInfo {
  implicit val reads:Reads[MarathonDeleteInfo] = Json.reads[MarathonDeleteInfo]
  implicit val writes:Writes[MarathonDeleteInfo] = Json.writes[MarathonDeleteInfo]
}
