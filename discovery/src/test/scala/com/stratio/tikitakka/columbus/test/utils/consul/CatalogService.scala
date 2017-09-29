/*
 * © 2017 Stratio Big Data Inc., Sucursal en España. All rights reserved.
 *
 * This software – including all its source code – contains proprietary information of Stratio Big Data Inc., Sucursal en España and may not be revealed, sold, transferred, modified, distributed or otherwise made available, licensed or sublicensed to third parties; nor reverse engineered, disassembled or decompiled, without express written authorization from Stratio Big Data Inc., Sucursal en España.
 */
package com.stratio.tikitakka.columbus.test.utils.consul

import play.api.libs.json.Json
import play.api.libs.json.Reads
import play.api.libs.json.Writes

case class CatalogService(
                            Datacenter: String,
                            ID: Option[String],
                            Node: String,
                            Address: String,
                            TaggedAddress: Option[Map[String, String]],
                            NodeMeta: Option[Map[String, String]],
                            Service: AgentService,
                            Check: CheckInfo
                            )


object CatalogService {

  implicit val writer: Writes[CatalogService] = Json.writes[CatalogService]
  implicit val reads: Reads[CatalogService] = Json.reads[CatalogService]
}