/*
 * © 2017 Stratio Big Data Inc., Sucursal en España. All rights reserved.
 *
 * This software – including all its source code – contains proprietary information of Stratio Big Data Inc., Sucursal en España and may not be revealed, sold, transferred, modified, distributed or otherwise made available, licensed or sublicensed to third parties; nor reverse engineered, disassembled or decompiled, without express written authorization from Stratio Big Data Inc., Sucursal en España.
 */
package com.stratio.tikitakka.columbus.consul.model

import com.stratio.tikitakka.common.model.discovery.ServiceInfo
import play.api.libs.json.Json
import play.api.libs.json.Reads
import play.api.libs.json.Writes

case class Service(
                    ID: Option[String], //Future versions will have ID when it happens remove Option
                    Node: String,
                    Address: String,
                    CreateIndex: Int,
                    ModifyIndex: Int,
                    ServiceAddress: String,
                    ServiceEnableTagOverride: Boolean,
                    ServiceID: String,
                    ServiceName: String,
                    ServicePort: Int,
                    ServiceTags: List[String]
                  ) {

  def toDiscoveryServiceInfo: ServiceInfo =
    ServiceInfo(ServiceID, ServiceName, ServiceAddress, ServicePort, ServiceTags)
}

object Service {

  implicit val reads: Reads[Service] = Json.reads[Service]
  implicit val writes: Writes[Service] = Json.writes[Service]
}